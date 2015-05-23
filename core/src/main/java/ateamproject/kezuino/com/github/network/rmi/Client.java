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
import java.util.HashMap;
import java.util.UUID;

/**
 * @author Kez and Jules
 */
public class Client extends ateamproject.kezuino.com.github.network.Client {
    private static Client instance;
    protected ClientBase rmi;

    protected Client() throws RemoteException {
        super();

        System.setProperty("pactales.client.servername", "localhost");
        System.setProperty("pactales.client.serverobject", "server");
        rmi = new ClientBase(this);
    }

    /**
     * Only use on client-side!
     *
     * @return {@link Client} instance. Creates a new {@link Client} if it doesn't exist.
     * @throws RemoteException
     */
    public static Client getInstance() throws RemoteException {
        if (instance == null) {
            instance = new Client();
        }

        return instance;
    }

    public ClientBase getRmi() {
        return rmi;
    }

    public void start() {
        System.out.println("Client starting...");

        try {
            String rmiHost = System.getProperty("pactales.client.servername");
            String rmiObject = System.getProperty("pactales.client.serverobject");

            this.rmi.setServer((IProtocolServer) Naming.lookup(String.format("//%s/%s", rmiHost, rmiObject)));
        } catch (RemoteException | NotBoundException | MalformedURLException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("Client started");
    }
}
