package ateamproject.kezuino.com.github;

import ateamproject.kezuino.com.github.render.screens.GameScreen;
import ateamproject.kezuino.com.github.render.screens.HighscoreScreen;
import com.badlogic.gdx.Game;

public class PactaleGame extends Game {

    @Override
    public void create() {
        this.setScreen(new HighscoreScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }
}
