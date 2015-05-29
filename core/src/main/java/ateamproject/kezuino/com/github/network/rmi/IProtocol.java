/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.rmi;

import com.badlogic.gdx.math.Vector2;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

/**
 *
 * @author Kez and Jules
 */
public interface IProtocol extends Remote {
    void gameObjectSetDirection(UUID sender, UUID objectId) throws RemoteException;

    void gameObjectSetPosition(UUID sender, UUID objectId, Vector2 position) throws RemoteException;
}
