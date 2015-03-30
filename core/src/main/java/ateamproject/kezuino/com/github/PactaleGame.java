package ateamproject.kezuino.com.github;

import ateamproject.kezuino.com.github.render.screens.GameScreen;
import com.badlogic.gdx.Game;

public class PactaleGame extends Game {

    @Override
    public void create() {
        this.setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }
}
