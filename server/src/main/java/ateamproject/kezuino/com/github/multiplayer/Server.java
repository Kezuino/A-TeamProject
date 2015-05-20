package ateamproject.kezuino.com.github.multiplayer;

import ateamproject.kezuino.com.github.network.rmi.IProtocolServer;
import ateamproject.kezuino.com.github.render.screens.ClanFunctions;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements IProtocolServer {
    private transient static Server currentInstance;
    private ClanFunctions clanFunctions;

    protected Server() throws RemoteException {
        super(Registry.REGISTRY_PORT);
        clanFunctions = new ClanFunctions();
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

    @Override
    public ArrayList<String> clanFillTable(String emailadres) throws RemoteException {
        return clanFunctions.fillTable(emailadres);
    }

    @Override
    public boolean clanCreateClan(String clanName, String emailaddress) throws RemoteException {
        return clanFunctions.createClan(clanName, emailaddress);
    }


    @Override
    public ClanFunctions.invitationType clanGetInvitation(String clanName, String emailaddress) throws RemoteException {
        return clanFunctions.getInvitation(clanName, emailaddress);
    }

    @Override
    public ClanFunctions.managementType getManagement(String clanName, String emailaddress) throws RemoteException {
        return clanFunctions.getManagement(clanName, emailaddress);
    }

    @Override
    public String getPersons(String clanName) throws RemoteException {
       return clanFunctions.getPersons(clanName);
    }

    @Override
    public boolean handleInvitation(ClanFunctions.invitationType invite, String clanName, String emailAddress, String nameOfInvitee) throws RemoteException {
       return clanFunctions.handleInvitation(invite, clanName, emailAddress, nameOfInvitee);
    }

    @Override
    public boolean handleManagement(ClanFunctions.managementType manage, String clanName, String emailaddress) throws RemoteException {
        return clanFunctions.handleManagement(manage, clanName, emailaddress);
    }

    @Override
    public String getUsername(String emailaddress) throws RemoteException {
       return clanFunctions.getUsername(emailaddress);
    }

    @Override
    public String getEmail(String username) throws RemoteException {
        return clanFunctions.getEmail(username);
    }

    @Override
    public boolean setUsername(String name, String emailaddress) throws RemoteException {
       return clanFunctions.setUsername(name, emailaddress);
    }
}
