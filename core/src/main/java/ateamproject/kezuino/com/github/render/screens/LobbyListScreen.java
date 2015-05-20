/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.network.rmi.Client;
import ateamproject.kezuino.com.github.network.rmi.Lobby;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fatih
 */
public class LobbyListScreen extends BaseScreen{

    
    private final Table scrollTable;
    private boolean clanGame;
    private LobbyFunctions lf; // should be on server for rmi
    
    public LobbyListScreen(Game game, boolean clanGame) {
        super(game);
        this.clanGame = clanGame;
        this.lf = new LobbyFunctions();
        
        scrollTable = new Table();
        RefreshGui();
        
    }
    
    private void RefreshGui()
    {
        scrollTable.clear();
        
        TextButton btnCreateGame = new TextButton("Maak spel", skin);
        btnCreateGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LobbyScreen(game,true));
            }
        });
        btnCreateGame.setPosition( stage.getWidth() - btnCreateGame.getWidth() -10,stage.getHeight() - btnCreateGame.getHeight() -10);
        this.stage.addActor(btnCreateGame);
         
        TextField lb1 = new TextField("Lobby naam", skin);
        lb1.setDisabled(true);
        TextField lb2 = new TextField("Host", skin);
        lb2.setDisabled(true);
        TextField lb3 = new TextField("Deelnemers", skin);
        lb3.setDisabled(true);
        //TextField lb4 = new TextField("Join", skin);
        //lb4.setDisabled(false);
        
        Pixmap pm1 = new Pixmap(1, 1, Pixmap.Format.RGB565);
        pm1.setColor(Color.GREEN);
        pm1.fill();

        scrollTable.add(lb1);
        scrollTable.columnDefaults(0);
        scrollTable.add(lb2);
        scrollTable.columnDefaults(1);
        scrollTable.add(lb3);
        scrollTable.columnDefaults(2);
        //scrollTable.add(lb4);
        scrollTable.columnDefaults(3);
        
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
        FillHostTable();
        
        
        float x = stage.getWidth() / 2 - table.getWidth() / 2;
        float y = stage.getHeight() - table.getHeight() - btnCreateGame.getHeight() -20;
        
        table.setPosition(x, y);
        this.stage.addActor(table);
        
    }
    
    private void FillHostTable()
    {
        ArrayList<Lobby> hostList = new ArrayList<>();
        
        Client client;
        try {
            client = Client.instance();
            hostList = client.getLobbies();
        } catch (RemoteException ex) {
            Logger.getLogger(LobbyListScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
                
                
        /*
        
        if (!clanGame) {
             hostList = lf.getRandomHostList();
        }
        else
        {
             hostList = lf.getClanHostList();
        }
       */
        
        for (int i = 0; i < hostList.size(); i++) {
           
            TextField lb1 = new TextField(hostList.get(i).getLobbyName(), skin);
            lb1.setDisabled(true);
            TextField lb2 = new TextField(hostList.get(i).getLobbyId().toString(), skin);
            lb2.setDisabled(true);
            TextField lb3 = new TextField(Integer.toString(hostList.get(i).getMembers().size()), skin);
            lb3.setDisabled(true);
            TextButton btnJoin = new TextButton("Join", skin);
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
