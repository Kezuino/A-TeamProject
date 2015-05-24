/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.Game;
import ateamproject.kezuino.com.github.network.IClient;
import ateamproject.kezuino.com.github.network.packet.packets.PacketKick;
import ateamproject.kezuino.com.github.render.screens.ClanFunctions;
import ateamproject.kezuino.com.github.render.screens.ClanFunctions.InvitationType;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Kez and Jules
 */
public interface IProtocolServer extends IProtocol {
    UUID login(String email, String password) throws RemoteException;

    void heartbeat(UUID client) throws RemoteException;

    Game createLobby(String LobbyName, UUID host) throws RemoteException;

    List<Game> getLobbies() throws RemoteException;

    Game getLobbyById(UUID lobbyId) throws RemoteException;

    Game joinLobby(UUID lobbyId, UUID client) throws RemoteException;

    /**
     * Requests all connected {@link IClient clients} to stop and closes the lobby.
     *
     * @param lobbyId
     * @return
     * @throws RemoteException
     */
    boolean quitLobby(UUID lobbyId) throws RemoteException;

    /**
     * Kicks the {@link IClient} from any lobby it is currently in.
     *
     * @param client
     * @return
     * @throws RemoteException
     */
    boolean kickClient(UUID client, PacketKick.KickReasonType reasonType, String message) throws RemoteException;

    ArrayList<String> clanFillTable(String emailadres) throws RemoteException;

    boolean clanCreateClan(String clanName, String emailaddress) throws RemoteException;

    InvitationType clanGetInvitation(String clanName, String emailaddress) throws RemoteException;

    ClanFunctions.ManagementType getManagement(String clanName, String emailaddress) throws RemoteException;

    String getPeople(String clanName) throws RemoteException;

    boolean handleInvitation(InvitationType invite, String clanName, String emailAddress, String nameOfInvitee) throws RemoteException;

    boolean handleManagement(ClanFunctions.ManagementType manage, String clanName, String emailaddress) throws RemoteException;

    String getUsername(String emailaddress) throws RemoteException;

    String getEmail(String username) throws RemoteException;

    boolean setUsername(String name, String emailaddress) throws RemoteException;

}
