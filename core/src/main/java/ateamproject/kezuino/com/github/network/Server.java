package ateamproject.kezuino.com.github.network;

import ateamproject.kezuino.com.github.network.packet.Packet;

import java.util.*;

public abstract class Server implements IServer {
    protected Dictionary<UUID, Game> games;
    /**
     * Gets or sets all {@link IClient clients} currently connected to this {@link Server}.
     */
    protected Dictionary<UUID, IClient> clients;

    public Server() {
        games = new Hashtable<>();
        clients = new Hashtable<>();
    }

    public List<Game> getGames() {
        return Collections.list(games.elements());
    }

    /**
     * Gets all {@link IClient clients} currently connected to this {@link Server}.
     *
     * @return All {@link IClient clients} currently connected to this {@link Server}.
     */
    public List<IClient> getClients() {
        return Collections.list(clients.elements());
    }

    public void send(Packet packet) {
        throw new UnsupportedOperationException("Send isn't allowed for the RMI server. Use Packet.execute instead.");
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
        for (IClient client : getClients()) {
            if (client.getId().equals(publicId)) {
                return client;
            }
        }
        return null;
    }

    @Override
    public void unregisterPackets() {
        Packet.unregisterAll();
    }
}
