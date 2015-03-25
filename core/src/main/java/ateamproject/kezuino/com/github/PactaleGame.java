package ateamproject.kezuino.com.github;

import ateamproject.kezuino.com.github.pathfinding.GameObjectPathfinding;
import ateamproject.kezuino.com.github.render.IRenderer;
import ateamproject.kezuino.com.github.render.orthographic.GameRenderer;
import ateamproject.kezuino.com.github.render.screens.LoginScreen;
import ateamproject.kezuino.com.github.singleplayer.GameSession;
import ateamproject.kezuino.com.github.singleplayer.Map;
import ateamproject.kezuino.com.github.utility.Assets;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class PactaleGame extends Game {

    private IRenderer renderer;
    private GameSession session;

    @Override
    public void create() {
        //this.setScreen(new LoginScreen(this));
        GameObjectPathfinding g = new GameObjectPathfinding();
        GameSession gs = new GameSession(5);
        gs.getMap().getNode(0, 1).setWall();
        gs.getMap().getNode(1, 1).setWall();
        gs.getMap().getNode(0, 2).setWall();
        gs.getMap().getNode(0, 3).setWall();
        gs.getMap().getNode(2, 1).setWall();
        gs.getMap().getNode(3, 1).setWall();
        gs.getMap().getNode(3, 2).setWall();
        gs.getMap().getNode(1, 4).setWall();
        g.generatePath(gs.getMap().getNode(1, 3), gs.getMap().getNode(0, 0));

    }

    @Override
    public void render() {
        //  update();
        //  draw();
        super.render();
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

        if (renderer != null) {
            renderer.render();
        }
    }
}
