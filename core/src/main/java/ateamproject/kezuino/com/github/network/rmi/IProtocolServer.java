/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.rmi;

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
    void login(String username) throws RemoteException;

    void logout() throws RemoteException;

    Lobby createLobby(String LobbyName, String host) throws RemoteException;

    List<Lobby> getLobbies() throws RemoteException;

    Lobby getLobbyById(UUID lobbyId) throws RemoteException;   
    
    Lobby joinLobby(UUID lobbyId,String client) throws RemoteException;



    ArrayList<String> clanFillTable(String emailadres) throws RemoteException;

    boolean clanCreateClan(String clanName, String emailaddress) throws RemoteException;

    InvitationType clanGetInvitation(String clanName, String emailaddress) throws RemoteException;

    ClanFunctions.ManagementType getManagement(String clanName, String emailaddress) throws RemoteException;

    String getPersons(String clanName) throws RemoteException;

    boolean handleInvitation(InvitationType invite, String clanName, String emailAddress, String nameOfInvitee) throws RemoteException;

    boolean handleManagement(ClanFunctions.ManagementType manage, String clanName, String emailaddress) throws RemoteException;

    String getUsername(String emailaddress) throws RemoteException;

    String getEmail(String username) throws RemoteException;

    boolean setUsername(String name, String emailaddress) throws RemoteException;

}
