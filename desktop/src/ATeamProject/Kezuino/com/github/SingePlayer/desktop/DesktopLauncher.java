package ATeamProject.Kezuino.com.github.SingePlayer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ATeamProject.Kezuino.com.github.SingePlayer.PactaleGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new PactaleGame(), config);
	}
}
