package com.csci499;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL30;
import com.util.GameType;
import com.util.Platform;

/**
 * Created by tmcdaniel on 4/7/2015.
 */
public class TestAdapter extends ApplicationAdapter {

    public static final String TITLE = "Tower Terror";
    public static final int WIDTH = 2400, HEIGHT = 1440; // used later to set window size

    private TestStage stage;
    private InputMultiplexer inputMultiplexer;

    protected GameType gameType;
    private GameState gameState; //TODO: Make variable of type GameStateInterface after adding required methods to interface

    private final NetworkHandler p2p;
    private final Platform platform; //we may need this later

    public TestAdapter(Platform platform) {
        this.platform = platform;
        gameType = GameType.UNKNOWN;
        p2p = new NetworkHandler();
    }

    @Override
    public void create() {
        stage = new TestStage();
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);


    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

}
