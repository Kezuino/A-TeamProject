package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.IServer;
import ateamproject.kezuino.com.github.network.rmi.IProtocolServer;

import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements IServer, IProtocolServer {
    private transient static Server currentInstance;
    
     private ArrayList<Lobby> lobbysList;

    protected Server() throws RemoteException {
        super(Registry.REGISTRY_PORT);
        
        lobbysList = new ArrayList<>();
    }
    
    public static Server getInstance() throws RemoteException {
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
    
    public void stop() {
        try {
            System.out.println("Server stopped");
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
            System.out.println(ex.getMessage());
            
            
            
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void login(String username) {
        System.out.println(username + " has logged in");
    }

    @Override
    public void logout() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void creatureMove(int creatureID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean createLobby(String LobbyName,String host) throws RemoteException {
         // add lobby to games array
        Lobby newLobby = new Lobby(LobbyName, host);
        lobbysList.add(newLobby);
        
        System.out.println("game lobby size :"+lobbysList.size());
        return true;
        
    }

    @Override
    public ArrayList<Lobby> GetLobbies() throws RemoteException {
        return lobbysList;
    }
}
