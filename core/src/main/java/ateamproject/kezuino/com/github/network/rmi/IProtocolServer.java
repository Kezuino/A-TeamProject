/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.rmi;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Kez and Jules
 */
public interface IProtocolServer extends IProtocol {
    void login(String username) throws RemoteException;
    void logout() throws RemoteException;
    
    boolean createLobby(String LobbyName,String host) throws RemoteException;
    ArrayList<Lobby> GetLobbies() throws RemoteException;
}
