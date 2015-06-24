package ateamproject.kezuino.com.github;

import ateamproject.kezuino.com.github.render.screens.LoginScreen;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import java.nio.file.Paths;

public class PactaleGame extends Game {

    @Override
    public void create() {
        Assets.create();
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
