package ATeamProject.Kezuino.com.github.SingePlayer.desktop;

import ATeamProject.Kezuino.com.github.PactaleGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new PactaleGame(), config);
	}
}
