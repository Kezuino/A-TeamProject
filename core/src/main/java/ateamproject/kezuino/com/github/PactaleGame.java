package ateamproject.kezuino.com.github;

import ateamproject.kezuino.com.github.render.IRenderer;
import ateamproject.kezuino.com.github.render.orthographic.GameRenderer;
import ateamproject.kezuino.com.github.singleplayer.GameSession;
import ateamproject.kezuino.com.github.utility.Assets;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class PactaleGame extends ApplicationAdapter {
    private IRenderer renderer;
    private GameSession session;

    @Override
    public void create() {
        Assets.create();
        session = new GameSession(20);
        renderer = new GameRenderer(session.getMap());
    }

    @Override
    public void render() {
        update();
        draw();
    }

    /**
     * Executes the game logic but doesn't render it.
     */
    public void update() {

    }

    /**
     * Draws the current state of the game logic.
     */
    public void draw() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (renderer != null) renderer.render();
    }
}
