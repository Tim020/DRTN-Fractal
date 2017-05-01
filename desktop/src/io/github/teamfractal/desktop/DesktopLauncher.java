/**
 * SEPR project inherited from DRTN.
 * Any changes are marked by preceding comments.
 * 
 * Executables availabe at: https://seprated.github.io/Assessment4/Executables.zip
**/
package io.github.teamfractal.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.github.teamfractal.RoboticonQuest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Duck-Related Roboticon Quest";
		config.addIcon("icon.png", Files.FileType.Internal);
		config.backgroundFPS = 1;
		config.vSyncEnabled = true;
		config.width = 1024;
		config.height = 512;
		config.resizable = false;

		new LwjglApplication(new RoboticonQuest(), config);
	}
}
