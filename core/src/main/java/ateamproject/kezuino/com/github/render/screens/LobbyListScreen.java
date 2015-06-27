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
public class LobbyListScreen extends BaseScreen implements RefreshableScreen {

    TextField lobbyname;
    private Table scrollTable;
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

                        arrayClans[i] = listclans.get(i);

                    }
                    clanDropdown = new SelectBox<>(skin);

                    clanDropdown.setItems(arrayClans);

                    clanDropdown.setSelectedIndex(0);
                    d.add(clanDropdown);
                }

                TextButton btnsubmit = new TextButton("Maken", skin);
                lobbyname.setSize(150, 30);

                d.add(lobbyname);
                d.add(btnsubmit);

                final SelectBox dropDownResultFinal = clanDropdown;
                btnsubmit.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        d.hide();
                        if (!lobbyname.getText().equals("")) {
                            if (clanGame) {
                                game.setScreen(new LobbyScreen(game, lobbyname.getText(), dropDownResultFinal.getSelected().toString()));
                            } else {
                                game.setScreen(new LobbyScreen(game, lobbyname.getText(), null));
                            }
                        }
                    }
                });
                d.show(stage);
            }
        });

        // Create game button
        btnCreateGame.setPosition(stage.getWidth() - btnCreateGame.getWidth() - 10, stage.getHeight() - btnCreateGame.getHeight() - 10);
        this.stage.addActor(btnCreateGame);

        Pixmap pm1 = new Pixmap(1, 1, Pixmap.Format.RGB565);
        pm1.setColor(Color.GREEN);
        pm1.fill();

        // set table position
        scrollTable = new Table(skin);
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

    public void fillHostTable() {
        this.scrollTable.clear();

        // table headers
        TextField lb1 = new TextField("Lobby naam", skin);
        lb1.setDisabled(true);
        TextField lb2 = new TextField("Host", skin);
        lb2.setDisabled(true);
        TextField lb3 = new TextField("Deelnemers", skin);
        lb3.setDisabled(true);

        // add headers to table
        scrollTable.add(lb1);
        scrollTable.columnDefaults(0);
        scrollTable.add(lb2);
        scrollTable.columnDefaults(1);
        scrollTable.add(lb3);
        scrollTable.columnDefaults(2);
        scrollTable.row();

        List<PacketGetLobbies.GetLobbiesData> hostList;

        Client client = Client.getInstance();
        PacketGetLobbies packet = new PacketGetLobbies(this.clanGame, client.getId());
        client.send(packet);
        hostList = packet.getResult();

        if (hostList != null && !hostList.isEmpty()) {
            for (PacketGetLobbies.GetLobbiesData game : hostList) {
                TextField lbName = new TextField(game.name, skin);
                lbName.setDisabled(true);
                TextField lbNameHost = new TextField(game.hostName, skin);
                lbNameHost.setDisabled(true);
                TextField lbAmountMembers = new TextField(Integer.toString(game.membersCount), skin);
                lbAmountMembers.setDisabled(true);
                TextButton btnJoin = new TextButton("Join", skin);
                btnJoin.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        LobbyListScreen.this.game.setScreen(new LobbyScreen(LobbyListScreen.this.game, game.lobbyId));
                    }
                });

                btnJoin.setDisabled(true);

                scrollTable.add(lbName);
                scrollTable.columnDefaults(0);
                scrollTable.add(lbNameHost);
                scrollTable.columnDefaults(1);
                scrollTable.add(lbAmountMembers);
                scrollTable.columnDefaults(2);
                scrollTable.add(btnJoin);
                scrollTable.columnDefaults(3);

                scrollTable.row();
            }
        }
    }

    @Override
    public void refresh() {
        this.fillHostTable();
    }
}
