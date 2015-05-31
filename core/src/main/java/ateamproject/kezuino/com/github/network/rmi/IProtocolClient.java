/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.packet.packets.PacketKick;

import java.rmi.RemoteException;
import java.util.UUID;

/**
 * @author Kez and Jules
 */
public interface IProtocolClient extends IProtocol {
    /**
     * Drops this {@link IProtocolClient} so that it no longer listens to the {@link IProtocolServer} and gracefully stops with whatever multiplayer task it was doing.
     *
     * @param read Reason why the {@link IProtocolClient} was dropped.
     * @return True if {@link IProtocolClient} was dropped. False otherwise.
     * @throws RemoteException
     */
    boolean drop(PacketKick.KickReasonType kick, String read) throws RemoteException;

    /**
     * Notifies the {@link IProtocolClient} that a new {@link IProtocolClient} has joined the session.
     *
     * @param id       Public id of the new {@link IProtocolClient}.
     * @param username Username of the new {@link IProtocolClient}.
     * @throws RemoteException
     */
    void clientJoined(UUID id, String username) throws RemoteException;

    /**
     * Notifies the {@link IProtocolClient} that a {@link IProtocolClient} has left the session.
     * @param clientThatLeft Public id that left.
     * @param username Username of the {@link IProtocolClient} that left.
     */
    void clientLeft(UUID clientThatLeft, String username) throws RemoteException;
}
