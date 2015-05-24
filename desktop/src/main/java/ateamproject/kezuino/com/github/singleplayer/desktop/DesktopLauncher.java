package ateamproject.kezuino.com.github.singleplayer.desktop;

import ateamproject.kezuino.com.github.PactaleGame;
import ateamproject.kezuino.com.github.network.rmi.Client;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.foregroundFPS = 60;
        cfg.backgroundFPS = 30;
        cfg.useGL30 = false;

        //cfg.samples = 8;
            
        //frameRateTest(cfg);
        PactaleGame game = new PactaleGame();
        new LwjglApplication(game, cfg);

        try {
            Client client = Client.getInstance(game);
            client.start();
        } catch (RemoteException ex) {
            Logger.getLogger(DesktopLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void frameRateTest(LwjglApplicationConfiguration cfg) {
        cfg.foregroundFPS = 0;
        cfg.backgroundFPS = 0;
        cfg.width = 1280;
        cfg.height = 720;
        cfg.fullscreen = false;
        cfg.forceExit = true;
        cfg.vSyncEnabled = false;
    }
}
