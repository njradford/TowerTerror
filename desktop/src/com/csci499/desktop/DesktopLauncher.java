package com.csci499.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.csci499.TerrorGDXGame;
import com.csci499.TestAdapter;
import com.util.PlatformType;

public class DesktopLauncher {
	private static final boolean SCENE_TESTING = true; //switch this boolean to true for testing scenes, false to launch game
    private static final String SCENE_TO_TEST = "startScene"; //put the scene name you want to test here
    public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width= TestAdapter.WIDTH; // sets window width
        config.height=TestAdapter.HEIGHT;  // sets window height
        if (SCENE_TESTING) {
            new LwjglApplication(new TestAdapter(PlatformType.DESKTOP, SCENE_TO_TEST), config);
        } else {
            new LwjglApplication(new TerrorGDXGame(PlatformType.DESKTOP), config);
        }
	}
}
