/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.render.IRenderer;
import ateamproject.kezuino.com.github.render.debug.DebugLayers;
import ateamproject.kezuino.com.github.render.debug.DebugRenderManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fatih
 */
public abstract class BaseScreen implements Screen {

    protected boolean clearOnRender;
    protected Color clearOnRenderColor;
    protected Music backgroundMusic;
    protected Viewport viewport;
    protected Camera camera;

    protected Game game;
    protected Stage stage;

    protected Skin skin;
    protected InputMultiplexer inputs;
    private List<IRenderer> renderers;

    public BaseScreen(Game game) {
        // Bootstrap screen.
        renderers = new ArrayList<>();
        inputs = new InputMultiplexer();
        stage = new Stage();
        clearOnRender = true;
        clearOnRenderColor = Color.BLACK;

        // Bootstrap game objects.
        this.game = game;

        // Bootstrap skin.
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Bootstrap input.
        inputs.addProcessor(stage);
        Gdx.input.setInputProcessor(inputs);

        // Bootstrap view.
        camera = stage.getCamera();
        viewport = new StretchViewport(stage.getWidth(), stage.getHeight(), camera);
        stage.setViewport(viewport);
    }

    @Override
    public void show() {
        if (game == null)
            throw new UnsupportedOperationException("Game must be set. Did you forget to call super(game) in the constructor of this screen?");
        for (IRenderer renderer : renderers) {
            renderer.active();
        }
        if (backgroundMusic != null) {
            if (!backgroundMusic.isLooping()) backgroundMusic.setLooping(true);
            if (!backgroundMusic.isPlaying()) backgroundMusic.play();
        }
    }

    @Override
    public void render(float delta) {
        if (clearOnRender) {
            Gdx.gl.glClearColor(clearOnRenderColor.r, clearOnRenderColor.g, clearOnRenderColor.b, clearOnRenderColor.a);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }

        for (IRenderer renderer : renderers) {
            renderer.render();
        }

        stage.act(delta);
        stage.draw();

        DebugRenderManager.render(DebugLayers.UI);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        if (backgroundMusic != null) backgroundMusic.pause();
    }

    @Override
    public void resume() {
        if (backgroundMusic != null) backgroundMusic.play();
    }

    @Override
    public void hide() {
        if (backgroundMusic != null) backgroundMusic.pause();
    }

    @Override
    public void dispose() {
        if (backgroundMusic != null) backgroundMusic.dispose();
    }

    /**
     * Adds the {@link IRenderer} to the list of renderers.
     *
     * @param renderer {@link IRenderer} to add to the list.
     * @param <T>      Any class that implements the interface {@link IRenderer} to add to the list.
     * @return {@link IRenderer} that was added for chaining purposes.
     */
    public <T extends IRenderer> T addRenderer(T renderer) {
        renderers.add(renderer);
        return renderer;
    }

}