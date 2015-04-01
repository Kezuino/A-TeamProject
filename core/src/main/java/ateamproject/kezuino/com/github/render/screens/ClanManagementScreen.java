/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

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

        stage.addActor(tfClannaam);
        stage.addActor(tfGebruikersnaam);
        stage.addActor(btnGebruikerToevoegen);
        stage.addActor(lbgebuikernaam);
        stage.addActor(btnClanToevoegen);
        stage.addActor(lbClannaam);
        stage.addActor(btnTerug);
    }
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render(delta);
    }
}
