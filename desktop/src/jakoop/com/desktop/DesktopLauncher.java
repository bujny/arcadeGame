package jakoop.com.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import jakoop.com.InvadersGame;
import jakoop.com.Resources;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Pintxo Pote Invaders";
		config.height = 720;
		config.width = 1280;
		config.resizable = false; 
		config.addIcon(Resources.ICON_128, FileType.Internal);
		config.addIcon(Resources.ICON_32, FileType.Internal);
		config.addIcon(Resources.ICON_16, FileType.Internal);
		new LwjglApplication(new InvadersGame(), config);
	}
}
