package ateamproject.kezuino.com.github.singleplayer.desktop;

import ateamproject.kezuino.com.github.PactaleGame;
import ateamproject.kezuino.com.github.singleplayer.GameSession;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.foregroundFPS = 60;
        cfg.backgroundFPS = 30;

        //cfg.samples = 8;

        //frameRateTest(cfg);

        new LwjglApplication(new PactaleGame(), cfg);
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
