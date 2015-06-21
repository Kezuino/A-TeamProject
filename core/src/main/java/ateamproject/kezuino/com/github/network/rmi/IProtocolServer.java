/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.IClientInfo;
import ateamproject.kezuino.com.github.network.packet.enums.InvitationType;
import ateamproject.kezuino.com.github.network.packet.enums.ManagementType;
import ateamproject.kezuino.com.github.network.packet.packets.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Kez and Jules
 */
public interface IProtocolServer extends IProtocol {

    PacketLoginAuthenticate.ReturnData login(String email, String password, IProtocolClient client) throws RemoteException;

    boolean doesUserExists(String email) throws RemoteException;

    boolean loginCreateUser(UUID sender, String username, String email) throws RemoteException;

    void heartbeat(UUID client) throws RemoteException;

    UUID createLobby(UUID sender, String LobbyName, String clan) throws RemoteException;

    List<PacketGetLobbies.GetLobbiesData> getLobbies(UUID sender, boolean isClanGame) throws RemoteException;
    
    java.util.Map<UUID, String> getLobbyMembers(UUID Lobbyid) throws RemoteException;

    PacketJoinLobby.PacketJoinLobbyData joinLobby(UUID sender, UUID lobbyId) throws RemoteException;

    boolean leaveLobby(UUID sender) throws RemoteException;
    
    boolean kickClientFromLobby(UUID lobbyid, UUID member) throws RemoteException;
            

    /**
     * Kicks the {@link IClientInfo} from any lobby it is currently in.
     *
     * @param sender
     * @param client
     * @param reasonType
     * @param message
     * @return
     * @throws RemoteException
     */
    boolean kickClient(UUID sender, UUID client, PacketKick.KickReasonType reasonType, String message) throws RemoteException;

    ArrayList<String> clanFillTable(String emailadres) throws RemoteException;

    ArrayList<String> getClans(UUID client) throws RemoteException;

    void setClans(UUID client) throws RemoteException;


    boolean createClan(UUID sender, String clanName) throws RemoteException;

    ArrayList<String> getKickInformation(UUID sender) throws RemoteException;

    InvitationType clanGetInvitation(UUID sender, String clanName) throws RemoteException;

    ManagementType getManagement(UUID sender, String clanName) throws RemoteException;

    String getPeople(String clanName) throws RemoteException;

    boolean handleInvitation(InvitationType invite, String clanName, String emailAddress, String nameOfInvitee) throws RemoteException;

    boolean handleManagement(ManagementType manage, String clanName, String emailaddress) throws RemoteException;

    String getUsername(String emailaddress) throws RemoteException;

    void setKickInformation(UUID sender,UUID getPersonToVoteFor) throws RemoteException;

    String getEmail(UUID Sender) throws RemoteException;

    boolean setUsername(String name, String emailaddress, UUID sender) throws RemoteException;

    boolean setScore(String clanName, int score) throws RemoteException;

    void logout(UUID sender) throws RemoteException;

    void setLobbyDetails(UUID sender, PacketLobbySetDetails.Data data) throws RemoteException;

    PacketLobbySetDetails.Data getLobbyDetails(UUID sender) throws RemoteException;

    void setLoadStatus(UUID sender, PacketSetLoadStatus.LoadStatus status) throws RemoteException;

    void setLoadStatus(UUID sender, PacketSetLoadStatus.LoadStatus status, int progress, int maxProgress) throws RemoteException;

    /**
     * Searches the {@link ateamproject.kezuino.com.github.network.Game} list for the {@link ateamproject.kezuino.com.github.network.Game} that the {@link UUID sender} is in.
     * If no {@link ateamproject.kezuino.com.github.network.Game} was found, the {@link ateamproject.kezuino.com.github.network.Game} will not be started.
     *
     * @param sender {@link UUID} that sended this message.
     * @throws RemoteException If RMI fails to connect.
     */
    void launchGame(UUID sender) throws RemoteException;
    
        /**
     * Searches the {@link ateamproject.kezuino.com.github.network.Game} list for the {@link ateamproject.kezuino.com.github.network.Game} that the {@link UUID sender} is in.
     * If no {@link ateamproject.kezuino.com.github.network.Game} was found, the {@link ateamproject.kezuino.com.github.network.Game} will not be started.
     *
     * @param sender {@link UUID} that sended this message.
     * @throws RemoteException If RMI fails to connect.
     */
    void launchRetryGame(UUID sender) throws RemoteException;

    List<PacketGetGameClients.Data> getGameClients(UUID sender) throws RemoteException;
}
