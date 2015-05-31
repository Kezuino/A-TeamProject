/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.packet.packets.PacketClientJoined;
import ateamproject.kezuino.com.github.network.packet.packets.PacketClientLeft;
import ateamproject.kezuino.com.github.network.packet.packets.PacketKick;
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

    protected ClientBase(Client client) throws RemoteException{
        this.client = client;
    }

    public IProtocolServer getServer() throws RemoteException {
        return this.server;
    }

    public void setServer(IProtocolServer server) {
        this.server = server;
    }


    @Override
    public boolean drop(PacketKick.KickReasonType kick, String reason) throws RemoteException {
        PacketKick packet = new PacketKick(kick, reason);
        this.client.send(packet);
        return packet.getResult();
    }

    @Override
    public void clientJoined(UUID id, String username) throws RemoteException {
        client.send(new PacketClientJoined(id, username));
    }

    @Override
    public void clientLeft(UUID clientThatLeft, String username) throws RemoteException {
        client.send(new PacketClientLeft(clientThatLeft, username));
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
