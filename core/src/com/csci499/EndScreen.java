/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csci499;

import com.badlogic.gdx.Screen;
import com.uwsoft.editor.renderer.Overlap2DStage;

/**
 *
 * @author tmcdaniel
 */
public class EndScreen extends AbstractScreen implements Screen {

    private GameStage stage;

    public EndScreen(boolean isVictory) {

        super();
        try {
            if (isVictory) {
                stage = new GameStage("winScene");
            } else {
                stage = new GameStage("loseScene");
            }
        } catch (NullPointerException e) {
            System.err.println("ERR -- ENDSCREEN -- () -- SCENE NAME NOT RECOGNIZED");

        }

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

}
