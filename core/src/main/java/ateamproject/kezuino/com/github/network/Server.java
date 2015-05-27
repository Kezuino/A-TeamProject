package ateamproject.kezuino.com.github.network;

import ateamproject.kezuino.com.github.network.packet.IPacketSender;
import ateamproject.kezuino.com.github.network.packet.Packet;
import java.util.*;

public abstract class Server<TClient extends IClient> implements IServer, IPacketSender {
    protected Dictionary<UUID, Game> games;
    /**
     * Thread used for constantly synchronizing all the clients and automatically dropping inactive ones.
     */
    protected Thread updateThread;
    protected boolean isUpdating;
    protected double secondsFromLastUpdate;

    /**
     * Gets or sets all {@link IClient clients} currently connected to this {@link Server}.
     */
    protected Dictionary<UUID, TClient> clients;

    public Server() {
        this.games = new Hashtable<>();
        this.clients = new Hashtable<>();
        this.secondsFromLastUpdate = System.nanoTime();
        this.isUpdating = true;
    }

    public List<Game> getGames() {
        return Collections.list(games.elements());
    }

    /**
     * Gets all {@link IClient clients} currently connected to this {@link Server}.
     *
     * @return All {@link IClient clients} currently connected to this {@link Server}.
     */
    public List<TClient> getClients() {
        return Collections.list(clients.elements());
    }

    public void send(Packet packet) {
        Packet.execute(packet);
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
        return this.getClients().stream().filter(c -> c.getId().equals(publicId)).findFirst().orElse(null);

        /*for (IClient client : getClients()) {
            if (client.getId().equals(publicId)) {
                return client;
            }
        }
        return null;*/
    }

    /**
     * Unregisters all the {@link Packet packets} for this domain/process.
     */
    @Override
    public void unregisterPackets() {
        Packet.unregisterAll();
    }

    @Override
    public double getSecondsFromLastPacket() {
        return (System.nanoTime() - secondsFromLastUpdate) / 1000000000.0;
    }
}
