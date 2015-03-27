package com.csci499;
/**
 * Created by tmcdaniel on 3/24/2015.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.util.NextScreen;

/**
 * AbstractScreen class - Abstract class to ensure all Screens contain basic members for flow control
 */
public abstract class AbstractScreen implements Screen {

    //flow control
    private boolean change;
    private NextScreen changeScreen;

    //stage for start screen, and table for positioning buttons
    protected Stage stage;
    protected Table rootTable; //root table to fill screen

    private static boolean assetsSet = false;
    //see https://github.com/libgdx/libgdx/wiki/Skin
    private static TextureAtlas legacyAtlas;
    protected static Skin skin;

    /**
     * AbstractScreen constructor: Can't actually be instantiated, sets flow values
     */
    AbstractScreen() {
        if (!assetsSet) {
            try{
                legacyAtlas = new TextureAtlas(Gdx.files.internal("legacy-packed/pack.atlas"));
                skin = new Skin(Gdx.files.internal("skin/legacy-skin.json"), legacyAtlas);
            } catch(SerializationException|GdxRuntimeException e) {
                System.err.println("ERROR -- ABSTRACTSCREEN -- CONSTRUCTOR -- ASSET LOAD FAILURE");
                e.printStackTrace();
            }
            assetsSet = true;
        }

        change=false; //setting change to true signals to Game to change screen
        changeScreen = NextScreen.UNKNOWN; //changeScreen signals to Game which screen to change to

        //set up tables
        rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.setBackground(skin.getDrawable("titleScreen"));

        //instantiate stage
        stage = new Stage(new ExtendViewport(TerrorGDXGame.WIDTH, TerrorGDXGame.HEIGHT));
        stage.addActor(rootTable); //add assets to stage

        //comment this out to turn off debug
        stage.setDebugAll(true);

    }
    /**
     * getChange method - return true to signal for a screen change.
     */
    public boolean getChange() {
        return change;
    }

    /**
     * getChangeScreen method - returns the screen the game should move to next
     */
    public NextScreen getChangeScreen() {
        return changeScreen;
    }

    public void setChangeScreen(NextScreen changeScreen){
        this.changeScreen = changeScreen;
        change = true;
    }

    //draw loop function
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT|GL30.GL_DEPTH_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    //note: called on setScreen
    @Override
    public void show() {
        change=false;
        Gdx.input.setInputProcessor(stage); //stage will receive inputs, including clicks, that will be delegated through table to buttons
    }

    //note: called on minimize/lose focus
    @Override
    public void pause() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //note: called on restore
    @Override
    public void resume() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void hide() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //called on resize - we can experiment with different viewport types
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

}

