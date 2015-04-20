/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csci499;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.util.ScreenType;
import com.util.GameType;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.script.SimpleButtonScript;

/**
 *
 * @author tmcdaniel
 */
public class StartScreen extends AbstractScreen implements Screen {

    private GameStage stage;
    public StartScreen() {

        super();
        try {
            stage = new GameStage("startScene");
        } catch (NullPointerException e) {
            System.err.println("ERR -- STARTSCREEN -- () -- SCENE NAME NOT RECOGNIZED");
        }
        SimpleButtonScript startButton = SimpleButtonScript.selfInit(stage.sceneLoader.getRoot().getCompositeById("startButton"));
        startButton.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                startLocal();
            }
        });
        SimpleButtonScript clientButton = SimpleButtonScript.selfInit(stage.sceneLoader.getRoot().getCompositeById("clientButton"));
        clientButton.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                startMulti(GameType.CLIENT);
            }
        });
        SimpleButtonScript hostButton = SimpleButtonScript.selfInit(stage.sceneLoader.getRoot().getCompositeById("hostButton"));
        hostButton.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                startMulti(GameType.HOST);
            }
        });

    }


    //draw loop function
    @Override
    public void render(float delta){
            super.render(delta); //clears screen
            stage.act();
            stage.draw();

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    //note: called on setScreen
    @Override
    public void show() {
        super.show();  //sets change boolean to false
        TerrorGDXGame.inputMultiplexer.addProcessor(stage);
    }

    //note: called on minimize/lose focus
    @Override
    public void pause() {
        TerrorGDXGame.inputMultiplexer.removeProcessor(stage);
    }

    //note: called on restore
    @Override
    public void resume() {
        TerrorGDXGame.inputMultiplexer.addProcessor(stage);
    }

    @Override
    public void hide() {
        TerrorGDXGame.inputMultiplexer.removeProcessor(stage);
    }
    //called on resize - we can experiment with different viewport types

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }
    @Override
    public Overlap2DStage getStage() {
    return stage;
    }
    //called for local button
    private void startLocal() {
   //     stage.addAction(Actions.sequence(Actions.fadeOut(1), Actions.run(new Runnable() {
    //        public void run() {
                setChangeScreen(ScreenType.LOCAL);
     //       }
      //  })));

    }

    //called for multi button
    private void startMulti(GameType options) {
    switch(options) {
        case CLIENT:
            this.setChangeScreen(ScreenType.CLIENTS);
        case HOST:
            this.setChangeScreen(ScreenType.HOSTS);
        default:
            System.err.println("ERROR -- STARTSCREEN -- STARTMULTI -- UNKNOWN HOSTING VALUE");
            throw new IllegalArgumentException("ERROR -- STARTSCREEN -- STARTMULTI -- UNKNOWN HOSTING VALUE");

        }

    }
}