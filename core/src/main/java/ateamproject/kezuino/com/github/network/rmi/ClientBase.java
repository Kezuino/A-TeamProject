/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.packet.packets.*;
import ateamproject.kezuino.com.github.singleplayer.ItemType;
import ateamproject.kezuino.com.github.singleplayer.Node;
import ateamproject.kezuino.com.github.utility.game.Direction;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.math.Vector2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
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
        PacketKick packet = new PacketKick(kick, reason, null, new UUID[] { null });
        client.send(packet);
        return packet.getResult();
    }

    @Override
    public void clientJoined(UUID id, String username) throws RemoteException {
        client.send(new PacketClientJoined(id, username, null));
    }

    @Override
    public void clientLeft(UUID clientThatLeft, String username) throws RemoteException {
        client.send(new PacketClientLeft(clientThatLeft, username, null));
    }

    @Override
    public void loadGame(String mapName, boolean isMaster, int playerLimit, int level) throws RemoteException {
        client.send(new PacketLoadGame(mapName, isMaster, playerLimit, level, null));
    }

    @Override
    public void setLobbyDetails(PacketLobbySetDetails.Data data) throws RemoteException {
        PacketLobbyGetDetails packet = new PacketLobbyGetDetails();
        packet.setResult(data);
        client.send(packet);
    }

    @Override
    public void screenRefresh(Class<?> refreshableScreen) throws RemoteException {
        client.send(new PacketScreenUpdate(refreshableScreen, null, new UUID[] { null }));
    }

    @Override
    public void requestCompleted(String requestId, int progress) throws RemoteException {
        PacketUpdateProgress packet = new PacketUpdateProgress(requestId, progress, null);
        client.send(packet);
    }

    @Override
    public void launchGame(boolean paused) throws RemoteException {
        // Null, null is needed so this client doesn't automatically assign itself as sender (see Packet.send).
        PacketLaunchGame packet = new PacketLaunchGame(paused, null, new UUID[] { null });
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
        PacketCreateItem packet = new PacketCreateItem(itemId, type, position, null);
        client.send(packet);
    }
    
    @Override
    public void removeItem(UUID sender, UUID itemId, ItemType itemType) {
        PacketRemoveItem packet = new PacketRemoveItem(itemId, itemType, sender);
        client.send(packet);
    }
    
    @Override
    public void changeScore(UUID sender, PacketScoreChanged.ManipulationType manipulationType, int change) throws RemoteException {
        PacketScoreChanged packet = new PacketScoreChanged(change, manipulationType, sender, new UUID[] { null });
        client.send(packet);
    }

    @Override
    public void balloonMessage(UUID sender, String typeName, Vector2 position, UUID followTarget) throws RemoteException {
        if (position == null && followTarget == null) {
            throw new IllegalArgumentException("Either position or followTarget must not be null.");
        }
        PacketBalloonMessage packet;

        if (followTarget != null) {
            packet = new PacketBalloonMessage(typeName, followTarget, null);
        } else {
            packet = new PacketBalloonMessage(typeName, position, null);
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

    @Override
    public void setAIPath(UUID sender, UUID objectId, Vector2 position, GraphPath<Node> nodes) throws RemoteException {
        PacketSetAIPath packet = new PacketSetAIPath(objectId, nodes, position, sender, new UUID[] { null });
        client.send(packet);
    }


    @Override
    public Map<String, Integer> getHighscores(UUID sender) throws RemoteException {
        PacketGetHighscores packet = new PacketGetHighscores(sender);
        client.send(packet);
        return packet.getResult();
    }
}
