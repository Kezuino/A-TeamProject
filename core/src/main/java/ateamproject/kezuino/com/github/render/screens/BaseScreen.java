/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author Fatih
 */
public abstract class BaseScreen implements Screen {

    protected Viewport viewport;
    protected Camera camera;

    protected Game game;
    protected Stage stage;

    protected Skin skin;
    protected InputMultiplexer inputs;

    public BaseScreen(Game game) {
        // Bootstrap screen.
        inputs = new InputMultiplexer();
        stage = new Stage();

        // Bootstrap game objects.
        this.game = game;

        // Bootstrap skin.
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Bootstrap input.
        inputs.addProcessor(stage);
        Gdx.input.setInputProcessor(inputs);

        // Bootstrap view.
        camera = stage.getCamera();
        viewport = new FitViewport(stage.getWidth(), stage.getHeight(), camera);
        stage.setViewport(viewport);
    }

    @Override
    public void show() {
        if (game == null)
            throw new UnsupportedOperationException("Game must be set. Did you forget to call super(game) in the constructor of this screen?");
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }


}