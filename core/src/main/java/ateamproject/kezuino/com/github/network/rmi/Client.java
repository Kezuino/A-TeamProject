/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.rmi.IProtocolClient;
import ateamproject.kezuino.com.github.network.rmi.IProtocolServer;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
    
    public static Client instance() throws RemoteException {
        if(currentInstance == null) {
            currentInstance = new Client();
        }
        
        return currentInstance;
    }

    public void start() {
        System.out.println("Client starting...");
        
        try {
            String rmiHost = System.getProperty("pactales.client.servername");
            String rmiObject = System.getProperty("pactales.client.serverobject");

            this.server = (IProtocolServer) Naming.lookup(String.format("//%s/%s", rmiHost, rmiObject));
            boolean loggedOn = this.server.login("test", "test");
            
            if(loggedOn) {
                System.out.println("Player is logged on");
            }
        } catch (RemoteException | NotBoundException | MalformedURLException ex) {
            
            System.out.println(ex.getMessage());
            System.out.println(ex.toString());
        }
        
        System.out.println("Client started");
    }

    @Override
    public void creatureMove(int creatureID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
