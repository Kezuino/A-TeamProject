/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.render.IRenderer;
import ateamproject.kezuino.com.github.render.debug.DebugLayers;
import ateamproject.kezuino.com.github.render.debug.DebugRenderManager;
import ateamproject.kezuino.com.github.singleplayer.GameSession;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.*;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Fatih
 */
public abstract class BaseScreen implements Screen {

    private static GameSession session;
    private final List<IRenderer> renderers;
    protected boolean clearOnRender;
    protected Color clearOnRenderColor;
    protected Music backgroundMusic;
    protected Viewport viewport;
    protected Camera camera;
    protected Game game;
    protected Stage stage;
    protected Skin skin;
    protected InputMultiplexer inputs;

    public BaseScreen(Game game) {
        skin = Assets.getSkin("uiskin.json");

        // Bootstrap screen.
        renderers = new ArrayList<>();
        inputs = new InputMultiplexer();
        stage = new Stage();
        clearOnRender = true;
        clearOnRenderColor = Color.valueOf("ffffcc");

        // Bootstrap game objects.
        this.game = game;

        // Bootstrap input.
        inputs.addProcessor(stage);
        inputs.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.F2:
                        Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height, !Gdx.graphics.isFullscreen());
                        break;
                    case Input.Keys.F12:
                        // Reset the screen.
                        try {
                            Constructor<? extends BaseScreen> constructor = BaseScreen.this.getClass().getConstructor(Game.class);

                            final BaseScreen[] screen = {null};
                            // Reload content.
                            Assets.reloadSkin(false, () -> {
                                try {
                                    screen[0] = constructor.newInstance(game);
                                    game.setScreen(screen[0]);
                                } catch (InstantiationException | IllegalAccessException | InvocationTargetException ignored) {
                                }

                                if (screen[0] != null) {
                                    // Show dialog.
                                    new Dialog("Reload", screen[0].skin) {
                                        {
                                            text("Skin was reloaded.");
                                            button("Oke");
                                        }
                                    }.show(screen[0].stage);
                                }
                            });
                        } catch (NoSuchMethodException e) {
                            // Show dialog.
                            new Dialog("Error", skin) {
                                {
                                    text("Couldn't reload the skin.");
                                    button("Oke");
                                }
                            }.show(stage);
                        }
                        return true;
                }
                return super.keyDown(keycode);
            }
        });
        Gdx.input.setInputProcessor(inputs);

        // Bootstrap view.
        camera = stage.getCamera();
        viewport = new FitViewport(stage.getWidth(), stage.getHeight(), camera);
        stage.setViewport(viewport);
    }

    public static GameSession getSession() {
        return session;
    }

    public static void setSession(GameSession session) {
        BaseScreen.session = session;
    }

    public Stage getStage() {
        return stage;
    }

    public Skin getSkin() {
        return skin;
    }

    @Override
    public void show() {
        if (game == null) {
            throw new UnsupportedOperationException("Game must be set. Did you forget to call super(game) in the constructor of this screen?");
        }
        for (IRenderer renderer : renderers) {
            renderer.active();
        }
        if (backgroundMusic != null) {
            if (!backgroundMusic.isLooping()) {
                backgroundMusic.setLooping(true);
            }
            if (!backgroundMusic.isPlaying()) {
                backgroundMusic.play();
            }
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
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void pause() {
        if (backgroundMusic != null) {
            backgroundMusic.pause();
        }
    }

    @Override
    public void resume() {
        if (backgroundMusic != null) {
            backgroundMusic.play();
        }
    }

    @Override
    public void hide() {
        if (backgroundMusic != null) {
            backgroundMusic.pause();
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
        }
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

    /**
     * Removes a specific {@link IRenderer} from this {@link Screen}.
     *
     * @param renderer {@link IRenderer} to remove.
     * @param <T>      Type of instance to remove that extends from {@link IRenderer}.
     */
    public <T extends IRenderer> void removeRenderer(T renderer) {
        renderers.remove(renderer);
    }

    /**
     * Removes all {@link IRenderer Renderers} from this {@link Screen}.
     */
    public void clearRenderers() {
        renderers.clear();
    }
}
