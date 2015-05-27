package ateamproject.kezuino.com.github.network;

import ateamproject.kezuino.com.github.network.packet.IPacketSender;
import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.render.screens.ClanManagementScreen;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Server<TClient extends IClient> implements IServer, IPacketSender {

    protected Dictionary<UUID, Game> games;
    /**
     * Thread used for constantly synchronizing all the clients and
     * automatically dropping inactive ones.
     */
    protected Thread updateThread;
    protected boolean isUpdating;
    protected double secondsFromLastUpdate;
    private Connection connect = null;

    /**
     * Gets or sets all {@link IClient clients} currently connected to this
     * {@link Server}.
     */
    protected Dictionary<UUID, TClient> clients;

    public Server() {
        this.games = new Hashtable<>();
        this.clients = new Hashtable<>();
        this.secondsFromLastUpdate = System.nanoTime();
        this.isUpdating = true;
        
        if (!makeConnection()) {
            System.out.println("Can't make databaseconnection!");
        }
    }
    
    public boolean makeConnection(){
          try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");

            // Setup the connection with the DB
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/pactales", "root", "");

            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ClanManagementScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public List<Game> getGames() {
        return Collections.list(games.elements());
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
        //return this.getClients().stream().filter(c -> c.getId().equals(publicId)).findFirst().orElse(null);

        for (IClient client : getClients()) {
            if (client.getId().equals(publicId)) {
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
    public double getSecondsFromLastPacket() {
        return (System.nanoTime() - secondsFromLastUpdate) / 1000000000.0;
    }
}
