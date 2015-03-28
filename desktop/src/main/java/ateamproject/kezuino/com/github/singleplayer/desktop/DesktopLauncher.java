package ateamproject.kezuino.com.github.singleplayer.desktop;

import ateamproject.kezuino.com.github.PactaleGame;
import ateamproject.kezuino.com.github.pathfinding.GameObjectPathfinding;
import ateamproject.kezuino.com.github.singleplayer.GameSession;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.foregroundFPS = 60;
        cfg.backgroundFPS = 30;
        cfg.samples = 8;
        
            GameObjectPathfinding p = new GameObjectPathfinding();
            GameSession g = new GameSession();
            g.setMap(3);
            g.getMap().getNode(0, 1).setWall();
            g.getMap().getNode(1, 1).setWall();
            p.generatePath(g.getMap().getNode(0, 0), g.getMap().getNode(2, 2));

		new LwjglApplication(new PactaleGame(), cfg);
	}
}
