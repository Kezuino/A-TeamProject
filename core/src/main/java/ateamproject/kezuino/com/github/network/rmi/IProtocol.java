/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.packet.packets.PacketScoreChanged;
import ateamproject.kezuino.com.github.singleplayer.ItemType;
import ateamproject.kezuino.com.github.utility.game.Direction;
import com.badlogic.gdx.math.Vector2;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

/**
 * @author Kez and Jules
 */
public interface IProtocol extends Remote {
    void playerSetDirection(UUID sender, Direction direction) throws RemoteException;

    void playerSetPosition(UUID sender, Vector2 position) throws RemoteException;

    void shootProjectile(UUID sender) throws RemoteException;

    void createObject(UUID sender, String type, Vector2 position, Direction direction, float speed, UUID newObjectId, int color, int index) throws RemoteException;

    void createItem(UUID sender, UUID itemId, ItemType type, Vector2 position) throws RemoteException;
    
    void removeItem(UUID sender, UUID itemId, ItemType itemType) throws RemoteException;
    
    void changeScore(UUID sender, PacketScoreChanged.ManipulationType manipulationType, int change) throws RemoteException;

    void balloonMessage(UUID sender, String typeName, Vector2 position, UUID followTarget) throws RemoteException;
}
