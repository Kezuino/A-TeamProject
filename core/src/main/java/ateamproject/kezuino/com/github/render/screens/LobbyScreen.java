/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.network.packet.packets.PacketCreateLobby;
import ateamproject.kezuino.com.github.network.packet.packets.PacketJoinLobby;
import ateamproject.kezuino.com.github.network.packet.packets.PacketLeaveLobby;
import ateamproject.kezuino.com.github.network.rmi.Client;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Dictionary;
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
            client = Client.getInstance();
        } catch (RemoteException ex) {
            Logger.getLogger(LobbyScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

        PacketCreateLobby p = new PacketCreateLobby(this.lobbyName);
        client.send(p);
        this.lobbyId = p.getResult();

        createGui();
    }

    // member constructor
    public LobbyScreen(com.badlogic.gdx.Game game, UUID lobbyId) {
        super(game);
        this.lobbyId = lobbyId;
        this.isHost = false;

        try {
            client = Client.getInstance();
        } catch (RemoteException ex) {
            Logger.getLogger(LobbyScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Get lobby information and fill gui.
        PacketJoinLobby packet = new PacketJoinLobby(this.lobbyId, client.getId());
        client.send(packet);
        PacketJoinLobby.PacketJoinLobbyData lob = packet.getResult();
        this.lobbyName = lob.lobbyName;
        this.members = lob.members;

        createGui();
    }

    public void createGui() {
        // Lobby verlaten.
        TextButton btnQuitLobby = new TextButton(isHost ? "Sluit Lobby" : "Leave lobby", skin);
        btnQuitLobby.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // quit lobby
                PacketLeaveLobby packet = new PacketLeaveLobby();
                client.send(packet);
                boolean succeeded = packet.getResult();
                if (succeeded) {
                    game.setScreen(new LobbyListScreen(game, true));
                } else {
                    new Dialog("Error", skin) {
                        {
                            text("Could not leave lobby.");
                            button("Oke");
                        }
                    }.show(stage);
                }
            }
        });

        // Create game button
        btnQuitLobby.setPosition(stage.getWidth() - btnQuitLobby.getWidth() - 10, stage.getHeight() - btnQuitLobby.getHeight() - 10);
        this.stage.addActor(btnQuitLobby);

        scrollTable = new Table();

        TextField memberNameHeader = new TextField("Member name", skin);
        memberNameHeader.setDisabled(true);

        scrollTable.add(memberNameHeader);
        scrollTable.columnDefaults(0);
        scrollTable.row();

        // Run game button.
        TextButton btnRunGame = new TextButton(isHost ? "Spel starten" : "Ready", skin);
        btnRunGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Start loading the game.
                game.setScreen(new GameScreen(game));
            }
        });
        btnRunGame.setPosition(stage.getWidth() - btnQuitLobby.getWidth() - 10 - btnRunGame.getWidth() - 10, stage.getHeight() - btnRunGame.getHeight() - 10);
        if (isHost) {
            this.stage.addActor(btnRunGame);
        }

        // Create member table.
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

        if (this.members != null) {

            for (String membername : Collections.list(this.members.elements())) {
                TextField lblmember = new TextField(membername, skin);
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
    }
}
