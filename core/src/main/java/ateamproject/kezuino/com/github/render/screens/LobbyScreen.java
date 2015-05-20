/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.network.rmi.Client;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fatih
 */
public class LobbyScreen extends BaseScreen {

    public LobbyScreen(Game game, boolean isHost,String lobbyname) {
        super(game);
        
        if (isHost) {
            // if the user is the host create host
            createLobby(lobbyname);
        }
        
        Label lobby = new Label(lobbyname, skin);
        lobby.setSize(200, 30);
        lobby.setPosition(50,stage.getHeight()-lobby.getHeight());
        stage.addActor(lobby);
        
    }
    
    public void createLobby(String lobbyname)
    {
        Client client;
        try {
            client = Client.getInstance();
            boolean curLobby =  client.createLobby(lobbyname, "host");
            System.out.println(curLobby);
        } catch (RemoteException ex) {
            Logger.getLogger(LobbyScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
