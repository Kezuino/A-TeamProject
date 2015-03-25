package ateamproject.kezuino.com.github;

import ateamproject.kezuino.com.github.pathfinding.GameObjectPathfinding;
import ateamproject.kezuino.com.github.render.IRenderer;
import ateamproject.kezuino.com.github.render.orthographic.GameRenderer;
import ateamproject.kezuino.com.github.render.screens.GameScreen;
import ateamproject.kezuino.com.github.render.screens.LoginScreen;
import ateamproject.kezuino.com.github.singleplayer.GameSession;
import ateamproject.kezuino.com.github.singleplayer.Map;
import ateamproject.kezuino.com.github.utility.Assets;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class PactaleGame extends Game {

    @Override
    public void create() {
        //this.setScreen(new LoginScreen(this));
        GameObjectPathfinding g = new GameObjectPathfinding();
        GameSession gs = new GameSession(10);
        gs.getMap().getNode(6, 5).setWall();
        gs.getMap().getNode(5, 5).setWall();
        gs.getMap().getNode(4, 5).setWall();
        gs.getMap().getNode(3, 5).setWall();
        gs.getMap().getNode(8, 5).setWall();
        g.generatePath(gs.getMap().getNode(7, 7), gs.getMap().getNode(8, 2));

    }

    @Override
    public void render() {
        super.render();
    }
}
