/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.protocol;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Kez and Jules
 */
public interface IServer extends Remote {
    public boolean login(String username, String password) throws RemoteException;
    public void logout() throws RemoteException;
}
