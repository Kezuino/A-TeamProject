/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.packet.packets.*;
import ateamproject.kezuino.com.github.render.screens.RefreshableScreen;
import ateamproject.kezuino.com.github.singleplayer.ItemType;
import ateamproject.kezuino.com.github.utility.game.Direction;
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
    public void loadGame(String mapName, boolean isMaster, int playerLimit) throws RemoteException {
        client.send(new PacketLoadGame(mapName, isMaster, playerLimit));
    }

    @Override
    public void setLobbyDetails(PacketLobbySetDetails.Data data) throws RemoteException {
        PacketLobbyGetDetails packet = new PacketLobbyGetDetails();
        packet.setResult(data);
        client.send(packet);
    }

    @Override
    public void screenRefresh(Class<?> refreshableScreen) throws RemoteException {
        client.send(new PacketScreenUpdate(refreshableScreen));
    }

    @Override
    public void kickPopupRefresh() throws RemoteException {
        client.send(new PacketKickPopupRefresh());
    }

    @Override
    public void requestCompleted(String requestId, int progress) throws RemoteException {
        PacketRequestCompleted packet = new PacketRequestCompleted(requestId, progress);
        client.send(packet);
    }

    @Override
    public void launchGame(boolean paused) throws RemoteException {
        // Null, null is needed so this client doesn't automatically assign itself as sender (see Packet.send).
        PacketLaunchGame packet = new PacketLaunchGame(paused, null, null);
        client.send(packet);
    }

    @Override
    public void createObject(UUID sender, String type, Vector2 position, Direction direction, float speed, UUID newObjectId, int color, int index) throws RemoteException {
        PacketCreateGameObject packet = new PacketCreateGameObject(type, position, direction, speed, newObjectId, color, sender);
        packet.setIndex(index);
        client.send(packet);
    }

    @Override
    public void createItem(UUID sender, UUID itemId, ItemType type, Vector2 position) {
        PacketCreateItem packet = new PacketCreateItem(itemId, type, position);
        client.send(packet);
    }

    @Override
    public void balloonMessage(UUID sender, String typeName, Vector2 position, UUID followTarget) throws RemoteException {
        if (position == null && followTarget == null) {
            throw new IllegalArgumentException("Either position or followTarget must not be null.");
        }
        PacketBalloonMessage packet;

        // Null, null is needed so this client doesn't automatically assign itself as sender (see Packet.send).
        if (followTarget != null) {
            packet = new PacketBalloonMessage(typeName, followTarget, null, null);
        } else {
            packet = new PacketBalloonMessage(typeName, position, null, null);
        }
        client.send(packet);
    }

    @Override
    public void playerSetDirection(UUID sender, Direction direction) throws RemoteException {
        PacketPlayerSetDirection packet = new PacketPlayerSetDirection(direction, sender);
        client.send(packet);
    }

    @Override
    public void playerSetPosition(UUID sender, Vector2 position) throws RemoteException {
        PacketPlayerSetPosition packet = new PacketPlayerSetPosition(position, sender);
        client.send(packet);
    }

    @Override
    public void shootProjectile(UUID sender) throws RemoteException {
        PacketShootProjectile packet = new PacketShootProjectile(sender);
        client.send(packet);
    }
}
