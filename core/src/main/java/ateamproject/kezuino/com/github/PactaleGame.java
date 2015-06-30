package ateamproject.kezuino.com.github;

import ateamproject.kezuino.com.github.network.rmi.Client;
import ateamproject.kezuino.com.github.render.screens.LoginScreen;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PactaleGame extends Game {

    @Override
    public void create() {
        File file = new File("out.txt");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            PrintStream ps = new PrintStream(fos);
            System.setErr(ps);
            System.setOut(ps);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PactaleGame.class.getName()).log(Level.SEVERE, null, ex);
        }

        Assets.create();
        this.setScreen(new LoginScreen(this));

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        Gdx.app.debug("APP", "Game is closing..");
        Client.getInstance().stop();
        Gdx.app.debug("APP", "Network client stopped.");
        super.dispose();
        Gdx.app.debug("APP", "Game is closed.");
    }
}
