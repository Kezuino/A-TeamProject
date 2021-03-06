package ateamproject.kezuino.com.github;

import ateamproject.kezuino.com.github.network.rmi.Client;
import ateamproject.kezuino.com.github.render.screens.LoginScreen;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class PactaleGame extends Game {
    @Override
    public void create() {
        Assets.setSkin(Gdx.app.getPreferences("user").getString("skin"), true, null);
        this.setScreen(new LoginScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        Gdx.app.debug("APP", "Saving settings..");
        Gdx.app.getPreferences("user").flush();
        Gdx.app.debug("APP", "Settings saved.");

        Gdx.app.debug("APP", "Game is closing..");
        Client.getInstance().stop();
        Gdx.app.debug("APP", "Network client stopped.");
        super.dispose();
        Gdx.app.debug("APP", "Game has closed.");
    }
}
