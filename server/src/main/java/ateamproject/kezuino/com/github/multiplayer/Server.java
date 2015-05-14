package ateamproject.kezuino.com.github.multiplayer;

import ateamproject.kezuino.com.github.network.rmi.IProtocolServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements IProtocolServer {
    private transient static Server currentInstance;

    protected Server() throws RemoteException {
        super(Registry.REGISTRY_PORT);
    }
    
    public static Server instance() throws RemoteException {
        if(currentInstance == null) {
            currentInstance = new Server();
        }
        
        return currentInstance;
    }

    public void start() {

        // TODO: Startup RMI.
        System.out.println("Starting server..");
        
        try {
            System.out.println(Registry.REGISTRY_PORT);
            Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            registry.bind("server", this);
        }
        catch(RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
      
        System.out.println("Server started");
    }

    @Override
    public boolean login(String username, String password) {
        System.out.println("Data plxors");
        
        return true;
    }

    @Override
    public void logout() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void creatureMove(int creatureID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
