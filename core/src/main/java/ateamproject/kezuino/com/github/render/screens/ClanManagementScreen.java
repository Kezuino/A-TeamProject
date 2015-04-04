/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * @author David
 */
public class ClanManagementScreen extends BaseScreen {

    public ClanManagementScreen(Game game) {
        super(game);

        TextButton btnGebruikerToevoegen = new TextButton("Gebruiker toevoegen:", skin);
        TextField tfGebruikersnaam = new TextField("", skin);
        Label lbgebuikernaam = new Label("Gebruikersnaam", skin);

        TextButton btnClanToevoegen = new TextButton("Clan toevoegen:", skin);
        TextField tfClannaam = new TextField("", skin);
        Label lbClannaam = new Label("Clan naam", skin);

        TextButton btnTerug = new TextButton("Terug", skin);
        btnTerug.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });
        
        ScrollPane spClanControl = new ScrollPane(btnTerug, skin);

        btnGebruikerToevoegen.setSize(200, 40);
        btnGebruikerToevoegen.setPosition(420, stage.getHeight() - 50);

        lbgebuikernaam.setSize(200, 40);
        lbgebuikernaam.setPosition(40, stage.getHeight() - 50);

        tfGebruikersnaam.setSize(200, 40);
        tfGebruikersnaam.setPosition(220, stage.getHeight() - 50);


        tfClannaam.setSize(200, 40);
        tfClannaam.setPosition(220, stage.getHeight() - 100);

        btnClanToevoegen.setSize(200, 40);
        btnClanToevoegen.setPosition(420, stage.getHeight() - 100);

        lbClannaam.setSize(200, 40);
        lbClannaam.setPosition(40, stage.getHeight() - 100);

        btnTerug.setSize(200, 40);
        btnTerug.setPosition(stage.getWidth() / 2 - 50, 50);
        
        spClanControl.setSize(200,40);
        
        stage.addActor(spClanControl);
        stage.addActor(tfClannaam);
        stage.addActor(tfGebruikersnaam);
        stage.addActor(btnGebruikerToevoegen);
        stage.addActor(lbgebuikernaam);
        stage.addActor(btnClanToevoegen);
        stage.addActor(lbClannaam);
        stage.addActor(btnTerug);
        
        
        TextButton btn1 = new TextButton("clan naam", skin);
        TextButton btn2 = new TextButton("clan uitnodingen", skin);
        TextButton btn3 = new TextButton("Clan verwijderen", skin);
        TextButton btn4 = new TextButton("personen", skin);
        
        
        
        

        
        Pixmap pm1 = new Pixmap(1, 1, Format.RGB565);
        pm1.setColor(Color.GREEN);
        pm1.fill();

        final Table scrollTable = new Table();
        scrollTable.add(btn1);
        scrollTable.columnDefaults(0);
        scrollTable.add(btn2);
        scrollTable.columnDefaults(1);
        scrollTable.add(btn3);
        scrollTable.columnDefaults(2);
        scrollTable.add(btn4);
        scrollTable.columnDefaults(3);
        scrollTable.row();
        scrollTable.setColor(com.badlogic.gdx.graphics.Color.BLUE);
        final ScrollPane scroller = new ScrollPane(scrollTable);
        scroller.sizeBy(200, 400);
        scroller.setColor(com.badlogic.gdx.graphics.Color.BLUE);
        final Table table = new Table();
        table.setFillParent(false);
        table.add(scroller).fill().expand();
        table.setSize(stage.getWidth(), 200);
        table.setColor(com.badlogic.gdx.graphics.Color.BLUE);
        float xOfLoginButton = stage.getWidth() / 2 - table.getWidth() / 2;
        float yOfLoginButton = stage.getHeight() / 2 - table.getHeight() / 2;
        
        table.setPosition(xOfLoginButton, yOfLoginButton);
        this.stage.addActor(table);
    }
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render(delta);
    }
}
