package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.mail.MailAccount;
import ateamproject.kezuino.com.github.network.Game;
import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.packets.PacketCreateLobby;
import ateamproject.kezuino.com.github.network.packet.packets.PacketHeartbeat;
import ateamproject.kezuino.com.github.network.packet.packets.PacketHighScore;
import ateamproject.kezuino.com.github.network.packet.packets.PacketKick;
import ateamproject.kezuino.com.github.network.packet.packets.PacketLoginAuthenticate;
import ateamproject.kezuino.com.github.network.packet.packets.PacketLoginUserExists;
import ateamproject.kezuino.com.github.render.screens.ClanManagementScreen;

import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends ateamproject.kezuino.com.github.network.Server<Client> {

    private static Server instance;
    protected ServerBase rmi;
    private Connection connect = null;

    public Server() throws RemoteException {
        super();
        rmi = new ServerBase(this);

        if (!makeConnection()) {
            System.out.println("Can't make databaseconnection!");
        }
    }

    public boolean makeConnection() {
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

    public static Server getInstance() throws RemoteException {
        if (instance == null) {
            instance = new Server();
        }

        return instance;
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

            if (updateThread != null) {
                updateThread.interrupt();
            }
            updateThread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    while (isUpdating) {
                        // Check if all clients are still active.
                        for (Client c : getClients()) {
                            System.out.println(c.getSecondsFromLastPacket());
                        }

                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            updateThread.start();
        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(ServerBase.class.getName()).log(Level.SEVERE, ex.getMessage());
        }

        System.out.println("Server started");
    }

    @Override
    public void stop() {
        try {
            System.out.println("Server stopped");

            // TODO: Notify clients.
            unregisterPackets();

            UnicastRemoteObject.unexportObject(rmi, true);
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

        Packet.registerFunc(PacketLoginAuthenticate.class, (packet) -> {
            System.out.print("Login request received for account: " + packet.getUsername());

            if (MailAccount.isValid(packet.getUsername(), packet.getPassword())) {//check if user is valid
                // Register client on server.
                try {
                    Client client = new Client();
                    clients.put(client.getId(), client);
                    System.out.println(" .. login accepted. " + clients.size() + " clients total.");
                    // Tell client what his id is.
                    return client.getId();

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
           // TODO: Check if email and password work while logging into the mail provider.
            System.out.print("Login request received for account: " + packet.getClanName());

            //get current score
            PreparedStatement preparedStatement;
            ResultSet resultSet = null;
            ResultSet resultSetWithClans;

            try {
                preparedStatement = connect.prepareStatement("SELECT `Score` FROM `clan` WHERE Name = ?");
                preparedStatement.setString(1, packet.getClanName());
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                int id = resultSet.getInt("Score");

                if (id > packet.getScore()) {
                    preparedStatement = connect.prepareStatement("UPDATE `clan` SET `Score` = ? WHERE `Name` = ?");
                    preparedStatement.setInt(1, packet.getScore());
                    preparedStatement.setString(2, packet.getClanName());
                    resultSet = preparedStatement.executeQuery();
                }
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(ClanManagementScreen.class.getName()).log(Level.SEVERE, null, ex);
            }

            return false;
        });
        
        Packet.registerFunc(PacketLoginUserExists.class, (packet) -> {
            System.out.print("Checking if the following user exists: " + packet.getEmail());
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            int count = 0;
        
            try {
                preparedStatement = connect.prepareStatement("SELECT COUNT(*) as amount FROM account WHERE Email = ?");
                preparedStatement.setString(1, packet.getEmail());
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                count = resultSet.getInt("amount");
                    return true;
            } catch (SQLException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (count == 1) {
                return true;
            } else {
                return false;
            }
        });

        Packet.registerAction(PacketCreateLobby.class, (p) -> {
            Game game = new Game(p.getLobbyname(), p.getSender());
            games.put(game.getId(), game);
        });
        /*
         Packet.registerAction(PacketCreateLobby.class, (p) -> {
            
         // getRmi().createLobby(p.getLobbyname(), p.getSender());
         //Game game = new Game(p.getLobbyname(), p.getSender());
         //games.put(game.getId(), game);
         });*/

        Packet.registerAction(PacketHeartbeat.class, packet -> System.out.println("Heartbeat received from: " + packet.getSender()));
    }
}
