package com.csci499.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.csci499.TerrorGDXGame;
import com.csci499.TestAdapter;
import com.util.PlatformType;

public class AndroidLauncher extends AndroidApplication {

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new TestAdapter(PlatformType.ANDROID, "gameScreen"), config);
	}
}
