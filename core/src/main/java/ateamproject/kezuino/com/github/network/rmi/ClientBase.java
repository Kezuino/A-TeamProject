/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.Game;
import ateamproject.kezuino.com.github.network.packet.packets.PacketKick;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.UUID;

/**
 * @author Kez and Jules
 */
public class ClientBase extends UnicastRemoteObject implements IProtocolClient {
    private transient Client client;
    private transient IProtocolServer server;

    protected ClientBase(Client client) throws RemoteException {
        this.client = client;
    }

    public IProtocolServer getServer() throws RemoteException {
        return this.server;
    }

    public void setServer(IProtocolServer server) {
        this.server = server;
    }

    @Override
    public void creatureMove(int creatureID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public boolean drop(String reason) throws RemoteException {
        System.out.println("Kicked by server: " + reason);
        return true;
    }
}
