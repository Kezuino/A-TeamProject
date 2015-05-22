/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.UUID;

/**
 * @author Kez and Jules
 */
public class Client extends UnicastRemoteObject implements IProtocolClient {
    private static transient Client currentInstance;
    private transient IProtocolServer server;
    //private transient Registry reg;

    protected Client() throws RemoteException {
        System.setProperty("pactales.client.servername", "localhost");
        System.setProperty("pactales.client.serverobject", "server");
    }


    public void start() {
        System.out.println("Client starting...");

        try {
            String rmiHost = System.getProperty("pactales.client.servername");
            String rmiObject = System.getProperty("pactales.client.serverobject");

            this.server = (IProtocolServer) Naming.lookup(String.format("//%s/%s", rmiHost, rmiObject));
        } catch (RemoteException | NotBoundException | MalformedURLException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("Client started");
    }
    
    public static Client getInstance() throws RemoteException {
        if(currentInstance == null) {
            currentInstance = new Client();
        }
        
        return currentInstance;
    }
    
    public IProtocolServer getConnection() throws RemoteException {
        return this.server;
    }

    @Override
    public void creatureMove(int creatureID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public UUID createLobby(String lobbyName, String host) throws RemoteException {
        return this.server.createLobby(lobbyName, host);
    }

    public List<Lobby> getLobbies() throws RemoteException {
        return this.server.getLobbies();
    }

    public Lobby getLobbyById(UUID lobbyId) throws RemoteException {
        return this.server.getLobbyById(lobbyId);
    }
}
