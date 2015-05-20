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
import java.util.ArrayList;

/**
 *
 * @author Kez and Jules
 */
public class Client extends UnicastRemoteObject implements IProtocolClient {
    private static transient Client currentInstance;
    private transient IProtocolServer server;
    //private transient Registry reg;

    protected Client() throws RemoteException {

    }

    public void start() {
        System.out.println("Client starting...");
        
        try {
            //this.reg = LocateRegistry.getRegistry("darkhellentertainment.com", Registry.REGISTRY_PORT);
            this.server = (IProtocolServer) Naming.lookup("//127.0.0.1/server");
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
    
    public boolean createLobby(String lobbyName, String host) throws RemoteException
    {
        return this.server.createLobby(lobbyName,host);
    }
    
    public ArrayList<Lobby> getLobbies() throws RemoteException
    {
        return this.server.GetLobbies();
    }
}
