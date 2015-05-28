/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.network.IClient;
import ateamproject.kezuino.com.github.network.rmi.Client;
import ateamproject.kezuino.com.github.network.Game;
import ateamproject.kezuino.com.github.network.packet.packets.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Fatih
 */
public class LobbyScreen extends BaseScreen {

    private Client client;
    private String lobbyName;
    private UUID lobbyId;
    private Dictionary<UUID, String> members;

    private boolean isHost;

    private Table scrollTable;

    // host constructor
    public LobbyScreen(com.badlogic.gdx.Game game, String lobbyname) {
        super(game);
        this.lobbyName = lobbyname;
        this.isHost = true;

        try {
            client = Client.getInstance(game);
        } catch (RemoteException ex) {
            Logger.getLogger(LobbyScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

        PacketCreateLobby p = new PacketCreateLobby(this.lobbyName, client.getId());
        client.send(p);
        this.lobbyId = p.getResult();

        refreshGui();
    }

    // member constructor
    public LobbyScreen(com.badlogic.gdx.Game game, UUID lobbyId) {
        super(game);
        this.lobbyId = lobbyId;
        this.isHost = false;

        try {
            client = Client.getInstance(game);
        } catch (RemoteException ex) {
            Logger.getLogger(LobbyScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

        // get lobby information en fill gui
        PacketJoinLobby packet = new PacketJoinLobby(this.lobbyId, client.getId());
        client.send(packet);
        PacketJoinLobby.PacketJoinLobbyData lob = packet.getResult();
        this.lobbyName = lob.lobbyName;
        this.members = lob.members;

        refreshGui();
    }

    public void refreshGui() {
        // quit lobby
        if (isHost) {
            TextButton btnQuitLobby = new TextButton("Sluit Lobby", skin);
            btnQuitLobby.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    btnQuitLobby.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            // quit lobby 
                            PacketQuitLobby packet = new PacketQuitLobby(lobbyId);
                            client.send(packet);
                            boolean succeeded = packet.getResult();
                            if (succeeded) {
                                game.setScreen(new LobbyListScreen(game, true));
                            } else {
                                System.out.println("something went wrong quiting the lobby!");
                            }

                        }
                    });
                }
            });
            // Create game button
            btnQuitLobby.setPosition(stage.getWidth() - btnQuitLobby.getWidth() - 10, stage.getHeight() - btnQuitLobby.getHeight() - 10);
            this.stage.addActor(btnQuitLobby);
        } else {
            TextButton btnLeaveLobby = new TextButton("Verlaat Lobby", skin);
            btnLeaveLobby.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    btnLeaveLobby.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            //leave lobby
                            PacketLeaveLobby packet = new PacketLeaveLobby(lobbyId, client.getId());
                            client.send(packet);
                            boolean succeeded = packet.getResult();
                            if (succeeded) {
                                game.setScreen(new LobbyListScreen(game, true));
                            } else {
                                System.out.println("something went wrong when leaveing the lobby!");
                            }
                        }
                    });
                }
            });
            // Create game button
            btnLeaveLobby.setPosition(stage.getWidth() - btnLeaveLobby.getWidth() - 10, stage.getHeight() - btnLeaveLobby.getHeight() - 10);
            this.stage.addActor(btnLeaveLobby);
        }

        scrollTable = new Table();

        TextField memberNameHeader = new TextField("Member name", skin);
        memberNameHeader.setDisabled(true);

        scrollTable.add(memberNameHeader);
        scrollTable.columnDefaults(0);
        scrollTable.row();

        // set table position
        scrollTable.row();
        scrollTable.setColor(com.badlogic.gdx.graphics.Color.BLUE);
        final ScrollPane scroller = new ScrollPane(scrollTable);
        scroller.sizeBy(200, 400);
        scroller.setColor(com.badlogic.gdx.graphics.Color.BLUE);
        final Table table = new Table();
        table.setFillParent(false);
        table.add(scroller).fill().expand();
        table.setSize(stage.getWidth(), stage.getHeight());
        table.setColor(com.badlogic.gdx.graphics.Color.BLUE);

        Label lobby = new Label("Lobby name : " + lobbyName, skin);
        lobby.setSize(200, 30);
        lobby.setPosition(0, stage.getHeight() - lobby.getHeight());
        /*
         Label lobbyid = new Label("UUID : " + curLobby.getId(), skin);
         lobbyid.setSize(200, 30);
         lobbyid.setPosition(0, 30);

         Label clients = new Label("clients : " + curLobby.getClients().size() + "/8", skin);
         clients.setSize(200, 30);
         clients.setPosition(0, 60);
         */
        if (this.members != null) {

            for (String membername : Collections.list(this.members.elements())) {
                TextField lblmember = new TextField(membername.toString(), skin);
                lblmember.setDisabled(true);

                scrollTable.add(lblmember);
                scrollTable.row();
            }
        }

        stage.addActor(lobby);

        float x = stage.getWidth() / 2 - table.getWidth() / 2;
        float y = stage.getHeight() - table.getHeight() - 30;

        table.setPosition(x, y);
        this.stage.addActor(table);

        //stage.addActor(lobbyid);
        // stage.addActor(clients);
    }
}
