package com.pdm.fallingstuff.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pdm.fallingstuff.FallingStuffGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Falling Stuff";
		config.width = 800;
		config.height = 600;
		new LwjglApplication(new FallingStuffGame(), config);
	}
}
