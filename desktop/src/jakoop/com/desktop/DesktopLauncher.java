package jakoop.com.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import jakoop.com.InvadersGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Pintxo Pote Invaders";
		config.height = 720;
		config.width = 1280;
		new LwjglApplication(new InvadersGame(), config);
	}
}
