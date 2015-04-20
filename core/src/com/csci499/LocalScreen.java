/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csci499;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.util.ScreenType;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.IBaseItem;
import com.uwsoft.editor.renderer.script.SimpleButtonScript;


/**
 *
 * @author tmcdaniel
 */
public class LocalScreen extends AbstractScreen implements Screen {
    private static final int MAXPLAYERS = 6;
    private String[] playerNames;

    private GameStage stage;

    public LocalScreen() {
        super();
        try {
            stage = new GameStage("localScene");

        } catch (NullPointerException e) {
            System.err.println("ERR -- LOCALSCREEN -- () -- SCENE NAME NOT RECOGNIZED");
        }

        SimpleButtonScript startButton = SimpleButtonScript.selfInit(stage.sceneLoader.getRoot().getCompositeById("beginButton"));
        startButton.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                startGame();
            }
        });

    }

    //draw loop function
    @Override
    public void render(float delta) {
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


    private void startGame() {

        setChangeScreen(ScreenType.GAME);

    }

    public String[] getPlayers() {
        for(IBaseItem item: stage.sceneLoader.getRoot().getItems()) {
            System.out.println(item.getClass().toString());

            }
        return playerNames;
    }


}