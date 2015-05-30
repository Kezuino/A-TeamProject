package ateamproject.kezuino.com.github;

import ateamproject.kezuino.com.github.render.screens.LoginScreen;
import com.badlogic.gdx.Game;

public class PactaleGame extends Game {

    @Override
    public void create() {
        this.setScreen(new LoginScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }
}
