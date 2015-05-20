/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.network.rmi.Client;
import com.badlogic.gdx.Game;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fatih
 */
public class LobbyScreen extends BaseScreen {

    public LobbyScreen(Game game, boolean isHost) {
        super(game);
        
        if (isHost) {
            // if the user is the host create host
            createHost();
        }
    }
    
    public void createHost()
    {
        // create GUID for the host/lobby
        // open lobby wiht the current user as the host
        // set InGame boolean on server to false so it will be listed in the hostlist screen
        
        Client client;
        try {
            client = Client.getInstance();
            boolean curLobby =  client.createLobby("mylobby", "host");
            System.out.println(curLobby);
        } catch (RemoteException ex) {
            Logger.getLogger(LobbyScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
