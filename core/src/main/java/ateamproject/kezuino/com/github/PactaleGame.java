package ateamproject.kezuino.com.github;

import ateamproject.kezuino.com.github.render.IRenderer;
import ateamproject.kezuino.com.github.render.orthographic.GameRenderer;
import ateamproject.kezuino.com.github.render.screens.GameScreen;
import ateamproject.kezuino.com.github.render.screens.LoginScreen;
import ateamproject.kezuino.com.github.render.screens.StoreScreen;
import ateamproject.kezuino.com.github.singleplayer.GameSession;
import ateamproject.kezuino.com.github.utility.Assets;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class PactaleGame extends Game {

    @Override
    public void create() {
        this.setScreen(new StoreScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }
}
