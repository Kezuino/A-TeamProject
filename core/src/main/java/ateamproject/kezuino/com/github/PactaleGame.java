package ateamproject.kezuino.com.github;

import ateamproject.kezuino.com.github.render.screens.LoginScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class PactaleGame extends Game {

    @Override
    public void create() {
        this.setScreen(new LoginScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        Gdx.app.debug("CLOSE", "Game is closing..");
        super.dispose();
    }
}
