package ateamproject.kezuino.com.github.singleplayer.desktop;

import ateamproject.kezuino.com.github.PactaleGame;
import ateamproject.kezuino.com.github.singleplayer.GameSession;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.foregroundFPS = 60;
        cfg.backgroundFPS = 30;
        cfg.samples = 8;

		new LwjglApplication(new PactaleGame(), cfg);
	}
}
