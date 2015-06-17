package ateamproject.kezuino.com.github.network;

import ateamproject.kezuino.com.github.network.packet.IPacketSender;
import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketManager;
import ateamproject.kezuino.com.github.network.packet.packets.PacketClientLeft;
import ateamproject.kezuino.com.github.network.packet.packets.PacketKick;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class Server<TClient extends IClientInfo> implements INetworkComponent, IPacketSender, AutoCloseable {
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
     * Thread for updating the {@link Server} state. Is executed in a while loop with a {@link Thread#sleep(long)} of 10 milliseconds.
     * Stop the thread by calling {@link #stop()}.
     */
    protected Thread updateThread;

    protected boolean isUpdating;
    /**
     * Gets or sets all {@link IClientInfo clients} currently connected to this {@link Server}.
     */
    protected LinkedHashMap<UUID, TClient> clients;

    public Server() {
        this.games = new LinkedHashMap<>();
        this.clients = new LinkedHashMap<>();
        this.isUpdating = true;
        this.clientTimeout = 30; // Default 30 seconds before client is dropped for all servers.
        this.packets = new PacketManager();
        this.executor = Executors.newCachedThreadPool();

        // Start updating thread.
        startUpdating();
    }

    /**
     * Starts running the update loop.
     */
    protected void startUpdating() {
        if (updateThread != null && updateThread.isAlive()) return;

        updateThread = new Thread(() -> {
            while (!updateThread.isInterrupted()) {
                update();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
        });
        updateThread.start();
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
        send(new PacketKick(PacketKick.KickReasonType.LOBBY, "Lobby closed.", game.getClientsAsArray()));

        // Unregister client on game.
        for (UUID clientId : game.getClients()) {
            IClientInfo client = getClient(clientId);
            client.setGame(null);
        }

        // Remove the game.
        return games.remove(gameId);
    }


    /**
     * Removes the {@link TClient} from all references in this {@link Server}.
     *
     * @param privateId Private id of the {@link TClient} to remove.
     * @return Removed {@link TClient}.
     */
    public TClient removeClient(UUID privateId) {
        // Remove client from game.
        IClientInfo client = getClient(privateId);
        client.setGame(null);

        // Update/remove game from server.
        Game lobby = getGameFromClientId(privateId);
        if (lobby != null) {
            lobby.getClients().remove(privateId);

            // Close lobby if no more clients or client was host.
            if (lobby.getClients().size() == 0) {
                games.remove(lobby.getId());
            } else if (lobby.getHostId().equals(privateId)) {
                removeGame(lobby.getId());
            } else {
                // Notify other clients that someone left.
                PacketClientLeft packet = new PacketClientLeft(client.getPublicId(), client.getUsername(), lobby.getClientsAsArray());
                send(packet);
            }
        }

        // Remove client from server.
        return clients.remove(privateId);
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

    /**
     * Gets the {@link IClientInfo} based on the private id. Can be null.
     *
     * @param privateId Private id of the {@link IClientInfo}.
     * @return {@link IClientInfo} based on the private id. Can be null.
     */
    public TClient getClient(UUID privateId) {
        return clients.get(privateId);
    }

    /**
     * Gets the {@link TClient} based on the public id.
     *
     * @param publicId Public id of the {@link IClientInfo}.
     * @return {@link TClient} based on the public id.
     */
    public TClient getClientFromPublic(UUID publicId) {
        // Do not simplify code. Needs to be high-performance.
        for (TClient client : getClients()) {
            if (client.getPublicId().equals(publicId)) {
                return client;
            }
        }
        return null;
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
        if (executor != null) executor.shutdown();
        if (updateThread != null) {
            updateThread.interrupt();
            while (updateThread.isAlive()) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    @Override
    public void stop() {
        close();
    }

}