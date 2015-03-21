package ateamproject.kezuino.com.github;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PactaleGame extends ApplicationAdapter {
    /**
     * If false, game will stop and cannot be restarted.
     */
    boolean isRunning = true;

    SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
    }

    @Override
    public void render() {

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
        Gdx.gl.glClearColor(1, 10, 10, 5);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        // TODO: Draw game.
        batch.end();
    }
}
