package ateamproject.kezuino.com.github;

import ateamproject.kezuino.com.github.render.screens.BaseScreen;
import ateamproject.kezuino.com.github.render.screens.LoginScreen;
import ateamproject.kezuino.com.github.utility.graphics.DialogHelper;
import com.badlogic.gdx.Game;

public class PactaleGame extends Game {

    @Override
    public void create() {
        this.setScreen(new LoginScreen(this));
        DialogHelper.setGame(this);
        DialogHelper.setSkin(((BaseScreen)this.getScreen()).getSkin());
    }

    @Override
    public void render() {
        super.render();
    }
}
