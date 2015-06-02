/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.network.packet.packets.PacketGetClans;
import ateamproject.kezuino.com.github.network.packet.packets.PacketGetLobbies;
import ateamproject.kezuino.com.github.network.rmi.Client;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.util.ArrayList;

import java.util.List;

/**
 * @author Fatih
 */
public class LobbyListScreen extends BaseScreen {

    private Table scrollTable;
    TextField lobbyname;
    private boolean clanGame;

    public LobbyListScreen(com.badlogic.gdx.Game game, boolean clanGame) {
        super(game);
        this.clanGame = clanGame;

        createGui();
    }

    private void createGui() {
        // Back to main menu.
        TextButton btnBack = new TextButton("Terug", skin);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });
        btnBack.setPosition(10, stage.getHeight() - btnBack.getHeight() - 10);
        this.stage.addActor(btnBack);

        // Create game button.
        TextButton btnCreateGame = new TextButton("Maak spel", skin);
        btnCreateGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Dialog d = new Dialog("Lobby Name", skin);
                lobbyname = new TextField("", skin);

                SelectBox<Object> clanDropdown = null;
                String dropDownResult = "";
                if (clanGame) {
                    Client client = Client.getInstance();
                    PacketGetClans packet = new PacketGetClans();
                    
                    client.send(packet);
                    ArrayList<String> listclans = packet.getResult();
                    
                    
                    Object[] arrayClans = new Object[listclans.size()];
                    for (int i = 0; i < listclans.size(); i++) {
                        
                        arrayClans[i] = new Label(listclans.get(i), skin);
                        
                    }
                    clanDropdown = new SelectBox<Object>(skin);
                    
                    clanDropdown.setItems(arrayClans);

                    clanDropdown.setSelectedIndex(0);
                    d.add(clanDropdown);

                    dropDownResult = clanDropdown.getSelected().toString();
                }

                TextButton btnsubmit = new TextButton("Maken", skin);
                lobbyname.setSize(150, 30);
                d.add(lobbyname);
                d.add(btnsubmit);

                final String dropDownResultFinal = dropDownResult;
                btnsubmit.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        d.hide();
                        if (clanGame) {
                            
                            game.setScreen(new LobbyScreen(game, lobbyname.getText(), dropDownResultFinal));
                        } else {
                            game.setScreen(new LobbyScreen(game, lobbyname.getText(), null));
                        }
                    }
                });
                d.show(stage);
            }
        });

        // Create game button
        btnCreateGame.setPosition(stage.getWidth() - btnCreateGame.getWidth() - 10, stage.getHeight() - btnCreateGame.getHeight() - 10);
        this.stage.addActor(btnCreateGame);

        // table headers
        TextField lb1 = new TextField("Lobby naam", skin);
        lb1.setDisabled(true);
        TextField lb2 = new TextField("Host", skin);
        lb2.setDisabled(true);
        TextField lb3 = new TextField("Deelnemers", skin);
        lb3.setDisabled(true);

        Pixmap pm1 = new Pixmap(1, 1, Pixmap.Format.RGB565);
        pm1.setColor(Color.GREEN);
        pm1.fill();

        // add headers to table
        scrollTable = new Table(skin);
        scrollTable.add(lb1);
        scrollTable.columnDefaults(0);
        scrollTable.add(lb2);
        scrollTable.columnDefaults(1);
        scrollTable.add(lb3);
        scrollTable.columnDefaults(2);

        // set table position
        scrollTable.row();
        scrollTable.setColor(com.badlogic.gdx.graphics.Color.BLUE);
        final ScrollPane scroller = new ScrollPane(scrollTable);
        scroller.sizeBy(200, 400);
        scroller.setColor(com.badlogic.gdx.graphics.Color.BLUE);
        final Table table = new Table();
        table.setFillParent(false);
        table.add(scroller).fill().expand();
        table.setSize(stage.getWidth(), stage.getHeight() - btnCreateGame.getHeight());
        table.setColor(com.badlogic.gdx.graphics.Color.BLUE);

        // get all host from the server and put in the table
        fillHostTable();

        float x = stage.getWidth() / 2 - table.getWidth() / 2;
        float y = stage.getHeight() - table.getHeight() - btnCreateGame.getHeight() - 20;

        table.setPosition(x, y);
        this.stage.addActor(table);

    }

    private void fillHostTable() {
        List<PacketGetLobbies.GetLobbiesData> hostList = null;

        Client client = Client.getInstance();
        PacketGetLobbies packet;
        if (this.clanGame) {
            packet = new PacketGetLobbies(true, client.getId());
        } else {
            packet = new PacketGetLobbies(false, client.getId());
        }
        client.send(packet);
        hostList = packet.getResult();

        if (!hostList.isEmpty()) {
            for (PacketGetLobbies.GetLobbiesData game : hostList) {
                TextField lb1 = new TextField(game.name, skin);
                lb1.setDisabled(true);
                TextField lb2 = new TextField(game.hostName, skin);
                lb2.setDisabled(true);
                TextField lb3 = new TextField(Integer.toString(game.membersCount), skin);
                lb3.setDisabled(true);
                TextButton btnJoin = new TextButton("Join", skin);
                btnJoin.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
//                    PacketJoinLobby packet = new PacketJoinLobby(game.lobbyId);
//                    Client.getInstance().send(packet);
                        LobbyListScreen.this.game.setScreen(new LobbyScreen(LobbyListScreen.this.game, game.lobbyId));
                    }
                });

                btnJoin.setDisabled(true);

                scrollTable.add(lb1);
                scrollTable.columnDefaults(0);
                scrollTable.add(lb2);
                scrollTable.columnDefaults(1);
                scrollTable.add(lb3);
                scrollTable.columnDefaults(2);
                scrollTable.add(btnJoin);
                scrollTable.columnDefaults(3);

                scrollTable.row();
            }
        }
    }
}
