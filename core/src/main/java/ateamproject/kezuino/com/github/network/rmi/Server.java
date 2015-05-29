package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.mail.MailAccount;
import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.packets.*;
import ateamproject.kezuino.com.github.render.screens.ClanManagementScreen;
import ateamproject.kezuino.com.github.singleplayer.ClanFunctions;
import ateamproject.kezuino.com.github.utility.io.Database;

import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends ateamproject.kezuino.com.github.network.Server<Client> {

    private static Server instance;
    protected ServerBase rmi;
    private ClanFunctions clanFunctions = new ClanFunctions();

    public Server() throws RemoteException {
        super();
        rmi = new ServerBase(this);
    }

    public static Server getInstance() throws RemoteException {
        if (instance == null) {
            instance = new Server();
        }

        return instance;
    }

    @Override
    public void update() {
        for (Client c : getClients()) {
            if (c.getSecondsInactive() >= clientTimeout) {
                removeClient(c);
                System.out.printf("Removed client %s from server.", c.getPrivateId().toString());
            }
        }
    }

    public ServerBase getRmi() {
        return rmi;
    }

    @Override
    public void start() {
        System.out.println("Starting server..");

        try {
            registerPackets();

            System.out.println(Registry.REGISTRY_PORT);
            Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            registry.bind("server", rmi);

        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(ServerBase.class.getName()).log(Level.SEVERE, ex.getMessage());
        }

        System.out.println("Server started");
    }

    @Override
    public void stop() {
        super.stop();

        try {
            // TODO: Notify clients that server is stopping.


            unregisterPackets();

            UnicastRemoteObject.unexportObject(rmi, true);

            System.out.println("Server stopped");
        } catch (NoSuchObjectException ex) {
            System.out.println(ex.getMessage());

            Logger.getLogger(ServerBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Executes the given {@link Packet} based on the registered function or
     * action. For RMI, the packet executes a RMI function directly.
     *
     * @param packet {@link Packet} to send to the {@link Client}.
     */
    @Override
    public void send(Packet packet) {
        Packet.execute(packet);
    }

    @Override
    public void registerPackets() {
        Packet.registerFunc(PacketKick.class, (packet) -> {
            // Drop client from wherever.
            this.getClientFromPublic(packet.getSender());

            return true;
        });

        Packet.registerFunc(PacketCreateClan.class, (packet) -> {
            System.out.print("Create clan packet received");
            return clanFunctions.createClan(packet.getClanName(), packet.getEmailadres());
        });

        Packet.registerFunc(PacketFillTable.class, (packet) -> clanFunctions.fillTable(packet.getEmailadres()));

        Packet.registerFunc(PacketGetEmail.class, (packet) -> clanFunctions.getEmail(packet.getUsername()));

        Packet.registerFunc(PacketGetHasConnection.class, (packet) -> clanFunctions.getHasConnection());

        Packet.registerFunc(PacketGetInvitation.class, (packet) -> clanFunctions.getInvitation(packet.getClanName(), packet
                .getEmailadres()));

        Packet.registerFunc(PacketGetManagement.class, (packet) -> clanFunctions.getManagement(packet.getClanName(), packet
                .getEmailadres()));

        Packet.registerFunc(PacketGetPeople.class, (packet) -> clanFunctions.getPeople(packet.getClanName()));

        Packet.registerFunc(PacketGetUsername.class, (packet) -> clanFunctions.getUsername(packet.getEmailadres()));

        Packet.registerFunc(PacketHandleInvitation.class, (packet) -> clanFunctions.handleInvitation(packet.getInvite(), packet
                .getClanName(), packet.getEmailadres(), packet
                .getNameOfInvitee()));

        Packet.registerFunc(PacketHandleManagement.class, (packet) -> clanFunctions.handleManagement(packet.getManage(), packet
                .getClanName(), packet.getEmailadres()));

        Packet.registerFunc(PacketSetUsername.class, (packet) -> clanFunctions.setUsername(packet.getName(), packet.getEmailaddress()));

        Packet.registerFunc(PacketLoginAuthenticate.class, (packet) -> {
            System.out.print("Login request received for account: " + packet.getUsername());

            if (MailAccount.isValid(packet.getUsername(), packet.getPassword())) {//check if user is valid
                // Register client on server.
                try {
                    Client client = new Client();
                    clients.put(client.getPrivateId(), client);
                    System.out.println(" .. login accepted. " + clients.size() + " clients total.");

                    // Tell client what his id is.
                    return client.getPrivateId();

                } catch (RemoteException e) {
                    System.out.println(" .. login is valid but registering failed!.");
                    e.printStackTrace();
                    return null;
                }
            }

            System.out.println(" .. login credentials not valid.");
            return null;
        });

        Packet.registerFunc(PacketHighScore.class, (packet) -> {
            try {
                ResultSet resultSet = Database.getInstance()
                                              .query("SELECT `Score` FROM `clan` WHERE Name = ?", packet
                                                      .getClanName());
                resultSet.next();
                int id = resultSet.getInt("Score");

                if (id > packet.getScore()) {
                    return Database.getInstance()
                                   .update("UPDATE `clan` SET `Score` = ? WHERE `Name` = ?", packet
                                           .getScore(), packet.getClanName()) > -1;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClanManagementScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        });

        Packet.registerFunc(PacketLoginCreateNewUser.class, (packet) -> {
            System.out.print("Trying to create following user in database: " + packet.getUsername());
            ResultSet resultSet = null;
            int count = 0;

            try {
                resultSet = Database.getInstance().query("SELECT COUNT(*) as amount FROM account WHERE Name = ?", packet.getUsername());
                resultSet.next();
                count = resultSet.getInt("amount");

                if (count == 1) {
                    System.out.print("Following user does already exists: " + packet.getUsername());
                } else {
                    int result = Database.getInstance().update("INSERT INTO account(Name,Email) VALUES(?,?)", packet.getUsername(), packet.getEmail());
                    if (result > 0) {
                        System.out.print("Adding following user to database: " + packet.getUsername());
                        return true;
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

            return false;
        });

        Packet.registerFunc(PacketLoginUserExists.class, (packet) -> {
            System.out.print("Checking if the following user exists: " + packet.getEmail());
            ResultSet resultSet = null;
            int count = 0;

            try {
                resultSet = Database.getInstance().query("SELECT COUNT(*) as amount FROM account WHERE Email = ?", packet.getEmail());
                resultSet.next();
                count = resultSet.getInt("amount");
            } catch (SQLException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

            return count == 1;
        });


        Packet.registerAction(PacketHeartbeat.class, packet -> System.out.println("Heartbeat received from: " + packet.getSender()));
    }


}
