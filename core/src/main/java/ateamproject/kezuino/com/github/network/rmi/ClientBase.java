/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.rmi;

import com.badlogic.gdx.math.Vector2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
    public boolean drop(String reason) throws RemoteException {

        return true;
    }

    @Override
    public void gameObjectSetDirection(UUID sender, UUID objectId) throws RemoteException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void gameObjectSetPosition(UUID sender, UUID objectId, Vector2 position) {
        throw new UnsupportedOperationException();
    }
}
