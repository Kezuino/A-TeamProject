package ateamproject.kezuino.com.github.multiplayer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

import ateamproject.kezuino.com.github.protocol.*;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements IServer {
    private static Server currentInstance;

    protected Server() throws RemoteException {

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
            //stockMarket = new MockEffectsStockMarket();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("server", this);
        }
        catch(RemoteException ex) {
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
}
