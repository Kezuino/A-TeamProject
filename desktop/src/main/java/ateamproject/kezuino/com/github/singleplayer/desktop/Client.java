/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.singleplayer.desktop;

import ateamproject.kezuino.com.github.rmi.IProtocolClient;
import ateamproject.kezuino.com.github.rmi.IProtocolServer;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Kez and Jules
 */
public class Client extends UnicastRemoteObject implements IProtocolClient {
    private static transient Client currentInstance;
    private transient IProtocolServer server;
    private transient Registry reg;

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
            this.reg = LocateRegistry.getRegistry("darkhellentertainment.com", Registry.REGISTRY_PORT);
            this.server = (IProtocolServer) this.reg.lookup("rmi://darkhellentertainment.com:1099/server");
            boolean loggedOn = this.server.login("test", "test");
            
            if(loggedOn) {
                System.out.println("Player is logged on");
            }
        } catch (RemoteException | NotBoundException ex) {
            System.out.println(ex.getMessage());
        }
        
        System.out.println("Client started");
    }

    @Override
    public void creatureMove(int creatureID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
