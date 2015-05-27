package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.Game;
import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.packets.PacketCreateLobby;
import ateamproject.kezuino.com.github.network.packet.packets.PacketHeartbeat;
import ateamproject.kezuino.com.github.network.packet.packets.PacketKick;
import ateamproject.kezuino.com.github.network.packet.packets.PacketLogin;

import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends ateamproject.kezuino.com.github.network.Server<Client> {

    private static Server instance;
    protected ServerBase rmi;

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
     * Executes the given {@link Packet} based on the registered function or action.
     * For RMI, the packet executes a RMI function directly.
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

        Packet.registerFunc(PacketLogin.class, (packet) -> {
            // TODO: Check if email and password work while logging into the mail provider.
            System.out.print("Login request received for account: " + packet.getUsername());

            // Register client on server.
            UUID publicId = UUID.randomUUID();
            try {
                clients.put(publicId, new Client());
                System.out.println(" .. login accepted. " + clients.size() + " clients total.");

            } catch (RemoteException e) {
                System.out.println(" .. login denied.");
                e.printStackTrace();
                return null;
            }

            // Tell client what his id is.
            return publicId;
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
