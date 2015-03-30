/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.render.IRenderer;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * @author David
 */
public class ClanManagementScreen implements Screen {
    private Game game;
    private Stage stage;
    
    public ClanManagementScreen(Game game){
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        // TODO: Redesign login screen to match documentation.
        TextButton btnGebruikerToevoegen = new TextButton("Gebruiker toevoegen:", skin);
        TextField tfGebruikersnaam = new TextField("", skin);
        Label lbgebuikernaam = new Label("Gebruikersnaam", skin);
        
        TextButton btnClanToevoegen = new TextButton("Clan toevoegen:", skin);
        TextField tfClannaam = new TextField("", skin);
        Label lbClannaam = new Label("Clan naam", skin);
        
        TextButton btnTerug = new TextButton("Terug", skin);
       

        btnGebruikerToevoegen.setSize(200, 40);
        btnGebruikerToevoegen.setPosition(420,stage.getHeight() -50);
        
        lbgebuikernaam.setSize(200, 40);
        lbgebuikernaam.setPosition(40,stage.getHeight() -50);
        
        tfGebruikersnaam.setSize(200, 40);
        tfGebruikersnaam.setPosition(220,stage.getHeight() -50);
        
        
        tfClannaam.setSize(200, 40);
        tfClannaam.setPosition(220,stage.getHeight() -100);
        
        btnClanToevoegen.setSize(200, 40);
        btnClanToevoegen.setPosition(420,stage.getHeight() -100);
        
        lbClannaam.setSize(200, 40);
        lbClannaam.setPosition(40,stage.getHeight() -100);
        
        btnTerug.setSize(200, 40);
        btnTerug.setPosition(stage.getWidth()/2 - 50,50);
        
        stage.addActor(tfClannaam);
        stage.addActor(tfGebruikersnaam);
        stage.addActor(btnGebruikerToevoegen);
        stage.addActor(lbgebuikernaam);
        stage.addActor(btnClanToevoegen);
        stage.addActor(lbClannaam);
        stage.addActor(btnTerug);
    }
    
    @Override
    public void show() {
        // Initialize screen here.
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Reset controls of this screen to align with new resolution.
    }

    @Override
    public void pause() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resume() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void hide() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
