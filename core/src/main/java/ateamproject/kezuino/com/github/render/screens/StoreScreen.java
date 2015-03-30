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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 *
 * @author Sven
 */
public class StoreScreen implements Screen {

    private Game game;
    private Stage stage;

    public StoreScreen(Game game) {
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        // TODO: Redesign login screen to match documentation.
        TextButton btnBack = new TextButton("Terug", skin);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // game.setScreen(new HomeScreen(game));
            }
        });
        btnBack.setSize(200, 40);
        btnBack.setPosition(stage.getWidth() / 2 - btnBack.getWidth() / 2, stage.getHeight() / 4 - btnBack.getHeight() / 4);

        Label lblStore = new Label("Winkel", skin);
        lblStore.setColor(Color.YELLOW);
        lblStore.setPosition(stage.getWidth() / 2 - btnBack.getWidth() / 2, stage.getHeight() / 150 / 100 - btnBack.getHeight() / 150 / 100);
        
        

        stage.addActor(btnBack);
        stage.addActor(lblStore);
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
