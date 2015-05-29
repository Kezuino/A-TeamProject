package ateamproject.kezuino.com.github.network;

import ateamproject.kezuino.com.github.network.packet.IPacketSender;
import ateamproject.kezuino.com.github.network.packet.Packet;

import java.util.*;

public abstract class Server<TClient extends IClient> implements IServer, IPacketSender, AutoCloseable {

    /**
     * Total time in seconds that are allowed to pass before a {@link IClient} is automatically dropped.
     */
    protected double clientTimeout;

    protected Dictionary<UUID, Game> games;

    /**
     * Thread for updating the {@link Server} state. Is executed in a while loop with a {@link Thread#sleep(long)} of 10 milliseconds.
     * Stop the thread by calling {@link #stop()}.
     */
    protected Thread updateThread;
    protected boolean isUpdating;
    /**
     * Gets or sets all {@link IClient clients} currently connected to this
     * {@link Server}.
     */
    protected Dictionary<UUID, TClient> clients;

    public Server() {
        this.games = new Hashtable<>();
        this.clients = new Hashtable<>();
        this.isUpdating = true;
        this.clientTimeout = 30; // Default 30 seconds before client is dropped for all servers.

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
     * Gets the total time in seconds that are allowed to pass before a {@link IClient} is automatically dropped.
     *
     * @return Total time in seconds that are allowed to pass before a {@link IClient} is automatically dropped.
     */
    public double getClientTimeout() {
        return clientTimeout;
    }

    /**
     * Sets the total time in seconds that are allowed to pass before a {@link IClient} is automatically dropped.
     *
     * @param clientTimeout Total time in seconds that are allowed to pass before a {@link IClient} is automatically dropped.
     */
    public void setClientTimeout(double clientTimeout) {
        this.clientTimeout = clientTimeout;
    }

    /**
     * Returns a copy of all the {@link Game games} that are on the server.
     *
     * @return A copy of all the {@link Game games} that are on the server.
     */
    public List<Game> getGames() {
        return Collections.list(games.elements());
    }

    /**
     * Removes a {@link Game} from this {@link Server} if it exists. Does nothing otherwise.
     *
     * @param gameId {@link UUID} of the {@link Game} to removed from the {@link Server}.
     * @return Removed {@link Game} or null if no {@link Game} could be removed with the {@link UUID}.
     */
    public Game removeGame(UUID gameId) {
        return games.remove(gameId);
    }


    /**
     * Removes the {@link TClient} from all references in this {@link Server}.
     *
     * @param c {@link TClient} to remove.
     * @return Removed {@link TClient}.
     */
    public TClient removeClient(TClient c) {
        return clients.remove(c);
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
     * @param lobbyId {@link UUID} of the {@link Game} to get.
     * @return {@link Game} that was found or null.
     */
    public Game getGame(UUID lobbyId) {
        if (lobbyId == null) throw new IllegalArgumentException("Parameter lobbyId must not be null.");
        return games.get(lobbyId);
    }

    /**
     * Gets all {@link IClient clients} currently connected to this
     * {@link Server}.
     *
     * @return All {@link IClient clients} currently connected to this
     * {@link Server}.
     */
    public List<TClient> getClients() {
        return Collections.list(clients.elements());
    }

    /**
     * Gets the {@link IClient} based on the private id. Can be null.
     *
     * @param privateId Private id of the {@link IClient}.
     * @return {@link IClient} based on the private id. Can be null.
     */
    public IClient getClient(UUID privateId) {
        return clients.get(privateId);
    }

    /**
     * Gets the {@link IClient} based on the public id.
     *
     * @param publicId Public id of the {@link IClient}.
     * @return {@link IClient} based on the public id.
     */
    public IClient getClientFromPublic(UUID publicId) {
        // Do not simplify code. Needs to be high-performance.
        for (IClient client : getClients()) {
            if (client.getPublicId().equals(publicId)) {
                return client;
            }
        }
        return null;
    }

    /**
     * Unregisters all the {@link Packet packets} for this domain/process.
     */
    @Override
    public void unregisterPackets() {
        Packet.unregisterAll();
    }

    @Override
    public double getSecondsInactive() {
        throw new UnsupportedOperationException("Server doesn't need this method. It's only useful for clients.");
    }

    /**
     * Gracefully closes the {@link Server} by stopped all activity by it.
     */
    @Override
    public final void close() {
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
