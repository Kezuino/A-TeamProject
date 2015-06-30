package ateamproject.kezuino.com.github.network;

import ateamproject.kezuino.com.github.admin.input.command.CommandDefinitionBuilder;
import ateamproject.kezuino.com.github.admin.input.command.Console;
import ateamproject.kezuino.com.github.admin.input.command.IAdministrable;
import ateamproject.kezuino.com.github.network.packet.IPacketSender;
import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketManager;
import ateamproject.kezuino.com.github.network.packet.packets.PacketKick;
import ateamproject.kezuino.com.github.network.packet.packets.PacketScreenUpdate;
import ateamproject.kezuino.com.github.render.screens.LobbyListScreen;
import com.badlogic.gdx.Gdx;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public abstract class Server<TClient extends IClientInfo> implements INetworkComponent, IPacketSender, IAdministrable<Server> {
    /**
     * Service for executing code on multiple threads.
     */
    protected ExecutorService executor;

    /**
     * Manager for registering {@link Packet} actions to.
     */
    protected PacketManager packets;

    /**
     * Total time in seconds that are allowed to pass before a {@link IClientInfo} is automatically dropped.
     */
    protected double clientTimeout;

    /**
     * {@link Game Games} that are currently active on this {@link Server}.
     */
    protected LinkedHashMap<UUID, Game> games;
    /**
     * {@link Console} used for administration of this {@link Server}.
     */
    protected Console<Server> console;
    /**
     * Thread for updating the {@link Server} state. Is executed in a while loop with a {@link Thread#sleep(long)} of 10 milliseconds.
     * Stop the thread by calling {@link #stop()}.
     */
    protected boolean isUpdating;
    protected boolean isRunning;
    /**
     * Gets or sets all {@link IClientInfo clients} currently connected to this {@link Server}.
     */
    protected LinkedHashMap<UUID, TClient> clients;
    private Scanner input;
    private PrintStream output;

    public Server() {
        this.isRunning = true;
        this.games = new LinkedHashMap<>();
        this.clients = new LinkedHashMap<>();
        this.isUpdating = true;
        this.clientTimeout = 10; // Default 10 seconds before client is dropped for all servers.
        this.packets = new PacketManager();
        this.executor = Executors.newCachedThreadPool();

        // Start updating thread.
        startUpdating();
    }

    /**
     * Starts running the update loop.
     */
    protected void startUpdating() {
        executor.submit(() -> {
            while (!executor.isShutdown()) {
                update();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
            }
        });
    }

    /**
     * {@link Server} specific work to do on another thread.
     */
    public abstract void update();

    /**
     * Gets the total time in seconds that are allowed to pass before a {@link IClientInfo} is automatically dropped.
     *
     * @return Total time in seconds that are allowed to pass before a {@link IClientInfo} is automatically dropped.
     */
    public double getClientTimeout() {
        return clientTimeout;
    }

    /**
     * Sets the total time in seconds that are allowed to pass before a {@link IClientInfo} is automatically dropped.
     *
     * @param clientTimeout Total time in seconds that are allowed to pass before a {@link IClientInfo} is automatically dropped.
     */
    public void setClientTimeout(double clientTimeout) {
        this.clientTimeout = clientTimeout;
    }

    /**
     * Returns a copy of all the {@link Game games} that are on the server.
     *
     * @return A copy of all the {@link Game games} that are on the server.
     */
    public LinkedHashMap<UUID, Game> getGames() {
        return games;
    }

    /**
     * Do not modify this list! It's a copy.
     *
     * @return
     */
    public ArrayList<Game> getGamesAsList() {
        return new ArrayList<>(games.values());
    }

    @Override
    public void send(Packet packet) {
        if (!packet.getClass().getSimpleName().contains("Heartbeat")) {
            if (packet.getSender() == null) {
                //System.out.println("NET: Outgoing: " + packet);
            } else {
                //System.out.println("NET: Incoming: " + packet);
            }
        }

        packets.execute(packet);
    }

    /**
     * Removes a {@link Game} from this {@link Server} if it exists and notifies all connected users that the {@link Game} has closed.
     * Does nothing otherwise.
     *
     * @param gameId {@link UUID} of the {@link Game} to removed from the {@link Server}.
     * @return Removed {@link Game} or null if no {@link Game} could be removed with the {@link UUID}.
     */
    public Game removeGame(UUID gameId) {
        Game game = games.get(gameId);
        if (game == null) return null;

        // Notify all connected clients that the game is closing.
        send(new PacketKick(PacketKick.KickReasonType.GAME, "Lobby is gesloten.", null, game
                .getClients()
                .stream()
//                .filter(c -> !c
//                        .equals(game.getHostId()))
                .map(c -> getClient(c).getPublicId())
                .toArray(UUID[]::new)));

        // Unregister client on game.
        for (UUID clientId : game.getClients()) {
            IClientInfo client = getClient(clientId);
            client.setGame(null);
        }

        Game removedGame = games.remove(gameId);

        // Update lobbylistscreen for all clients.
        if (removedGame != null) {
            PacketScreenUpdate tmp = new PacketScreenUpdate(LobbyListScreen.class, null, this.getClients().stream().map(IClientInfo::getPublicId).toArray(UUID[]::new));
            send(tmp);
        }

        // Remove the game.
        return removedGame;
    }

    /**
     * Adds a {@link Game} to this {@link Server}.
     *
     * @param game {@link Game} to add to this {@link Server}.
     */
    public void addGame(Game game) {
        games.put(game.getId(), game);
    }

    /**
     * Gets the {@link Game} assosicated with the given id.
     *
     * @param gameId {@link UUID} of the {@link Game} to get.
     * @return {@link Game} that was found or null.
     */
    public Game getGame(UUID gameId) {
        if (gameId == null) throw new IllegalArgumentException("Parameter gameId must not be null.");
        return games.get(gameId);
    }

    /**
     * Gets all {@link IClientInfo clients} currently connected to this
     * {@link Server}.
     *
     * @return All {@link IClientInfo clients} currently connected to this
     * {@link Server}.
     */
    public List<TClient> getClients() {
        return new ArrayList<>(clients.values());
    }

    public void removeClient(UUID privateId) {
        this.clients.remove(privateId);
    }

    /**
     * Gets the {@link IClientInfo} based on the private id. Can be null.
     *
     * @param privateId Private id of the {@link IClientInfo}.
     * @return {@link IClientInfo} based on the private id. Can be null.
     */
    public TClient getClient(UUID privateId) {
        if (privateId == null) throw new IllegalArgumentException("Parameter privateId must not be null.");

        TClient client = clients.get(privateId);
//        if (client == null) {
//            if (getClientFromPublic(privateId) != null) {
//                throw new IllegalArgumentException("Possible public id was given to getClient.");
//            }
//        }
        return client;
    }

    /**
     * Gets the {@link TClient} based on the public id.
     *
     * @param publicId Public id of the {@link IClientInfo}.
     * @return {@link TClient} based on the public id.
     */
    public TClient getClientFromPublic(UUID publicId) {
        if (publicId == null) throw new IllegalArgumentException("Parameter publicId must not be null.");

        // Do not simplify code. Needs to be high-performance.
        TClient result = null;
        for (TClient client : getClients()) {
            if (client.getPublicId().equals(publicId)) {
                result = client;
                break;
            }
        }

//        if (result == null) {
//            if (getClient(publicId) != null) {
//                throw new IllegalArgumentException("Possible private id was given to getClientFromPublic.");
//            }
//        }

        return result;
    }

    /**
     * Gets the {@link Game} that the {@link TClient} is currently in. Or null if {@link TClient} is not currently in a game / lobby.
     *
     * @param sender Private id of the {@link TClient} to find.
     * @return {@link Game} that the {@link TClient} is currently in. Or null if {@link TClient} is not currently in a game / lobby.
     */
    public synchronized Game getGameFromClientId(UUID sender) {
        TClient client = getClient(sender);
        if (client == null) return null;
        return client.getGame();
    }

    /**
     * Gracefully closes the {@link Server} by stopped all activity by it.
     */
    @Override
    public final void close() {
        this.isRunning = false;
        if (console != null) console.close();
        if (executor != null) {
            executor.shutdownNow();
        }
    }

    @Override
    public void stop() {
        close();
    }

    public Scanner getInput() {
        return input;
    }

    @Override
    public void setInput(InputStream input) {
        if (this.input != null) this.input.close();
        this.input = new Scanner(input);
    }

    public PrintStream getOutput() {
        return output;
    }

    @Override
    public void setOutput(OutputStream out) {
        if (this.output != null) this.output.close();
        this.output = new PrintStream(out);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Console<Server> createConsole(InputStream in, OutputStream out) {
        if (this.console != null) this.console.close();

        IAdministrable.super.createConsole(in, out);
        Console<Server> console = new Console<>(this, in, out);
        this.console = console;

        // Register default commands.
        console.registerCommand(CommandDefinitionBuilder.create("exit")
                                        .setDescription("Closes the server.")
                                        .get(), (sender, cmd) -> {
            sender.stop();
            return true;
        });

        console.registerCommand(CommandDefinitionBuilder.create("help")
                                        .setDescription("Shows the available commands.")
                                        .get(), (sender, cmd) -> {
            console.getOut().println("Available commands: ");
            for (String s : console.getCommands()) {
                console.getOut().println(s);
            }
            return true;
        });

        console.registerCommand(CommandDefinitionBuilder.create("clients")
                                        .setDescription("Retrieves the clients currently on the server.")
                                        .get(), (sender, cmd) -> {
            List<TClient> clients = getClients();
            if (clients.size() == 0) {
                console.getOut().println("No clients on server.");
            } else {
                console.getOut().println("Clients on server:");
                for (TClient client : clients) {
                    console.getOut().println('\t' + client.toString());
                }
            }
            return true;
        });

        console.registerCommand(CommandDefinitionBuilder.create("games")
                                        .setDescription("Shows all active games with their clients.")
                                        .get(), (sender, cmd) -> {
            LinkedHashMap<UUID, Game> games = getGames();
            if (games.size() == 0) {
                console.getOut().println("No games on server.");
            } else {
                console.getOut().println("Games on server:");
                for (Game game : games.values()) {
                    console.getOut().println('\t' + game.toString());
                    for (UUID clientId : game.getClients()) {
                        TClient client = getClient(clientId);
                        console.getOut().println("\t\t" + client);
                    }
                }
            }
            return true;
        });
        return console;
    }
}