/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.network.packet.packets.*;
import ateamproject.kezuino.com.github.network.rmi.Client;
import ateamproject.kezuino.com.github.singleplayer.GameSession;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Fatih
 */
public class LobbyScreen extends BaseScreen {

    private Client client;
    private String lobbyName;
    private String clanName;
    private UUID lobbyId;
    private Map<UUID, String> members = new HashMap<>();

    private boolean isHost;

    private Table scrollTable;

    // host constructor
    public LobbyScreen(com.badlogic.gdx.Game game, String lobbyname, String clanName) {
        super(game);
        this.lobbyName = lobbyname;
        this.isHost = true;
        this.client = Client.getInstance();
        this.clanName = clanName;

        PacketCreateLobby p = new PacketCreateLobby(this.lobbyName);
        client.send(p);
        this.lobbyId = p.getResult();

        // Add itself to the list.
        this.members.put(Client.getInstance().getPublicId(), Client.getInstance().getUsername());

        // TODO: Add control for selecting maps.
        PacketLobbySetDetails.Data data = new PacketLobbySetDetails.Data();
        data.setMap("2");
        client.send(new PacketLobbySetDetails(data));

        createGui();
    }

    // member constructor
    public LobbyScreen(com.badlogic.gdx.Game game, UUID lobbyId) {
        super(game);
        this.lobbyId = lobbyId;
        this.isHost = false;

        client = Client.getInstance();

        // Get lobby information and fill gui.
        PacketJoinLobby packet = new PacketJoinLobby(this.lobbyId, client.getId());
        client.send(packet);

        PacketJoinLobby.PacketJoinLobbyData lob = packet.getResult();
        this.lobbyName = lob.getLobbyName();
        this.members = lob.getMembers();

        createGui();
    }

    public void createGui() {
        // Lobby verlaten.
        TextButton btnQuitLobby = new TextButton(isHost ? "Sluit Lobby" : "Leave lobby", skin);
        btnQuitLobby.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Quit lobby
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

        btnQuitLobby.setPosition(stage.getWidth() - btnQuitLobby.getWidth() - 10, stage.getHeight() - btnQuitLobby.getHeight() - 10);
        this.stage.addActor(btnQuitLobby);

        // Player list.
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
                client.send(new PacketLaunchGame());
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
            for (String membername : members.values()) {
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
