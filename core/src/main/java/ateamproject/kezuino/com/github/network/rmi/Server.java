package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.IServer;
import ateamproject.kezuino.com.github.network.rmi.IProtocolServer;
import ateamproject.kezuino.com.github.render.screens.ClanFunctions;

import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.UUID;

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
        } catch (RemoteException | AlreadyBoundException ex) {
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
    public Lobby createLobby(String LobbyName,String host) throws RemoteException {
         // add lobby to games array
        Lobby newLobby = new Lobby(LobbyName, host);
        lobbysList.add(newLobby);
        
        System.out.println("Lobby: "+ newLobby.getLobbyName() + " - id "+ newLobby.getLobbyId() + " CREATED !");
        
       return newLobby;
        
    }

    @Override
    public List<Lobby> getLobbies() throws RemoteException {
        return this.lobbysList;
    }

    @Override
    public Lobby getLobbyById(UUID lobbyId) throws RemoteException {
        for (Lobby lobby : lobbysList) {
            if (lobby.getLobbyId().equals(lobbyId)) {
                return lobby;
            }
        }
        return null;
    }
    
    @Override
    public Lobby joinLobby(UUID lobbyId,String client) throws RemoteException {
        for (Lobby lobby : lobbysList) {
            if (lobby.getLobbyId().equals(lobbyId)) {
                if (lobby.addMember(client)) {
                    return lobby;
                }
            }
        }
        return null;
    }
    

    @Override
    public ArrayList<String> clanFillTable(String emailadres) throws RemoteException {
        return null;
    }

    @Override
    public boolean clanCreateClan(String clanName, String emailaddress) throws RemoteException {
        return false;
    }

    @Override
    public ClanFunctions.InvitationType clanGetInvitation(String clanName, String emailaddress) throws RemoteException {
        return null;
    }

    @Override
    public ClanFunctions.ManagementType getManagement(String clanName, String emailaddress) throws RemoteException {
        return null;
    }

    @Override
    public String getPersons(String clanName) throws RemoteException {
        return null;
    }

    @Override
    public boolean handleInvitation(ClanFunctions.InvitationType invite, String clanName, String emailAddress, String nameOfInvitee) throws RemoteException {
        return false;
    }

    @Override
    public boolean handleManagement(ClanFunctions.ManagementType manage, String clanName, String emailaddress) throws RemoteException {
        return false;
    }

    @Override
    public String getUsername(String emailaddress) throws RemoteException {
        return null;
    }

    @Override
    public String getEmail(String username) throws RemoteException {
        return null;
    }

    @Override
    public boolean setUsername(String name, String emailaddress) throws RemoteException {
        return false;
    }

    
}
