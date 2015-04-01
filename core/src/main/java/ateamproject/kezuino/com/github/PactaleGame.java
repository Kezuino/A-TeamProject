package ateamproject.kezuino.com.github;

import ateamproject.kezuino.com.github.render.screens.ClanManagementScreen;
import ateamproject.kezuino.com.github.render.screens.GameScreen;
import ateamproject.kezuino.com.github.render.screens.StoreScreen;
import com.badlogic.gdx.Game;

public class PactaleGame extends Game {

    @Override
    public void create() {
        this.setScreen(new ClanManagementScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }
}
