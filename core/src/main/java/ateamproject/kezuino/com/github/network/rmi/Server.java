package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.packet.Packet;
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

public class Server extends ateamproject.kezuino.com.github.network.Server {
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

    @Override
    public void registerPackets() {
        Packet.registerAction(PacketKick.class, (packet) -> System.out.println("Kicked player for reason: " + packet.getReason()));
        Packet.registerFunc(PacketLogin.class, (packet) -> {
            // TODO: Check if email and password work while logging into the mail provider.
            System.out.println("Login request received for account: " + packet.getUsername());

            // We don't like this person!
            if (packet.getUsername().equals("Anton")) {
                return null;
            }

            // Register client on server.
            UUID publicId = UUID.randomUUID();
            try {
                clients.put(publicId, new Client());
            } catch (RemoteException e) {
                e.printStackTrace();
                return null;
            }

            // Tell client what his id is.
            return publicId;
        });
    }
}
