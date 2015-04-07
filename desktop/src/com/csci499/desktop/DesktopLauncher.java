package com.csci499.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.csci499.TerrorGDXGame;
import com.csci499.TestAdapter;
import com.util.Platform;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width= TestAdapter.WIDTH; // sets window width
        config.height=TestAdapter.HEIGHT;  // sets window height
		new LwjglApplication(new TestAdapter(Platform.DESKTOP), config);
	}
}
