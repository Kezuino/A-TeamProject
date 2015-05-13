/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Kez and Jules
 */
public interface IProtocol extends Remote {
    public abstract void creatureMove(int creatureID) throws RemoteException;
}
