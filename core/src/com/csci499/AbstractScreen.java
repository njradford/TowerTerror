package com.csci499;
/**
 * Created by tmcdaniel on 3/24/2015.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.SerializationException;
import com.util.ScreenType;
import com.uwsoft.editor.renderer.Overlap2DStage;

import java.util.HashMap;

/**
 * AbstractScreen class - Abstract class to ensure all Screens contain basic members for flow control
 */
public abstract class AbstractScreen implements Screen {


    //flow control
    private boolean change;
    private ScreenType changeScreen;


  // protected Table fader;

  private boolean assetsSet = false;


    /**
     * AbstractScreen constructor: Can't actually be instantiated, sets flow values
     */
    AbstractScreen() {
        if (!assetsSet) {

        }
        assetsSet = true;

      /*  fader = new Table();
        fader.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        fader.setColor(Color.BLACK);*/

        change=false; //setting change to true signals to Game to change screen
        changeScreen = ScreenType.UNKNOWN; //changeScreen signals to Game which screen to change to

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
    public ScreenType getChangeScreen() {
        return changeScreen;
    }

    public void setChangeScreen(ScreenType changeScreen){
        this.changeScreen = changeScreen;
        change = true;
    }

    public abstract Overlap2DStage getStage();




    //note: called on setScreen
    @Override
    public void show() {
        change = false;
    }

    //WE MAY NEED TO USE THE FOLLOWING LATER, TO APPLY BEHAVIOR TO ALL SCREENS

    //draw loop function
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
    }
    @Override
    public abstract void dispose();
    //note: called on minimize/lose focus
    @Override
    public abstract void pause();
    //note: called on restore
    @Override
    public abstract void resume();
    @Override
    public abstract void hide();
    //called on resize - we can experiment with different viewport types
    @Override
    public abstract void resize(int width, int height);

   /* protected void fadeIn() {
        fader.addAction(Actions.sequence(Actions.fadeOut(1), Actions.run(new Runnable() {
            public void run() {

            }
        })));

    }
    protected void fadeOut() {
       fader.addAction(Actions.fadeIn(0.5f));
    }*/
}

