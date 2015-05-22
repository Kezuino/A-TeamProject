/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.network.rmi.Client;
import ateamproject.kezuino.com.github.network.rmi.Lobby;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.rmi.RemoteException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Fatih
 */
public class LobbyScreen extends BaseScreen {

    Lobby curLobby;
    private String lobbyName;
    private UUID lobbyId;

    // host constructor
    public LobbyScreen(Game game, String lobbyname) {
        super(game);
        this.lobbyName = lobbyname;

        createLobby();

        refreshGui();
    }

    // member constructor
    public LobbyScreen(Game game, UUID lobbyId) {
        super(game);

        // get lobby information en fill gui
        this.lobbyId = lobbyId;
        loadLobby();
        refreshGui();
    }

    public void createLobby() {
        try {
            Client client = Client.getInstance();
            this.lobbyId = client.createLobby(this.lobbyName, "host");
        } catch (RemoteException ex) {
            Logger.getLogger(LobbyScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadLobby() {
        try {
            Client client = Client.getInstance();
            Lobby curLobby = client.getLobbyById(lobbyId);
            this.lobbyName = curLobby.getLobbyName();
        } catch (RemoteException ex) {
            Logger.getLogger(LobbyScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void refreshGui() {
        Label lobby = new Label(this.lobbyName, skin);
        lobby.setSize(200, 30);
        lobby.setPosition(50, stage.getHeight() - lobby.getHeight());

        Label lobbyid = new Label("UUID : " + this.lobbyId.toString(), skin);
        lobby.setSize(200, 30);
        lobby.setPosition(0, 0);

        stage.addActor(lobby);
        stage.addActor(lobbyid);

    }
}
