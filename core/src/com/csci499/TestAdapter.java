package com.csci499;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL30;
import com.util.GameType;
import com.util.PlatformType;

/**
 * Created by tmcdaniel on 4/7/2015.
 */
public class TestAdapter extends ApplicationAdapter {

    public static final String TITLE = "Tower Terror";
    public static final int WIDTH = 1270, HEIGHT = 720; // used later to set window size

    private GameStage stage;
    private InputMultiplexer inputMultiplexer;
    private GameResourceManager mgr;
    protected GameType gameType;
    private GameState gameState; //TODO: Make variable of type GameStateInterface after adding required methods to interface

    private final PlatformType platformType; //we may need this later
    private final String testScene;

    public TestAdapter(PlatformType platformType, String testScene) {
        this.platformType = platformType;
        gameType = GameType.UNKNOWN;
        this.testScene = testScene;
    }

    @Override
    public void create() {
        mgr = new GameResourceManager();
        mgr.initPlatformerResources();
        try {
            stage = new GameStage(mgr, testScene);
        } catch (NullPointerException e) {
            System.err.println("ERR -- TESTADAPTER -- CREATE -- SCENE NAME NOT RECOGNIZED");
        }
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
    //called on resize - we can experiment with different viewport types
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }


}
