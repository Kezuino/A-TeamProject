/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.rmi;

import java.rmi.RemoteException;

/**
 * @author Kez and Jules
 */
public interface IProtocolClient extends IProtocol {
    /**
     * Drops this {@link IProtocolClient} so that it no longer listens to the {@link IProtocolServer} and gracefully stops with whatever multiplayer task it was doing.
     *
     * @param read Reason why the {@link IProtocolClient} was dropped.
     * @return True if {@link IProtocolClient} was dropped. False otherwise.
     */
    boolean drop(String read) throws RemoteException;
}
