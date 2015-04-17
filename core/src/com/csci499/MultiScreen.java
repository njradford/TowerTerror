/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csci499;

import com.badlogic.gdx.Screen;

/**
 *
 * @author tmcdaniel
 */
public class MultiScreen extends AbstractScreen implements Screen {
    private GameStage stage;


    public MultiScreen(boolean isHosting) {
        super();
        if (isHosting) {
            stage = new GameStage(mgr, "hostScene");
        } else {
            stage = new GameStage(mgr, "clientScene");
        }
    }
        //draw loop function
        @Override
        public void render(float delta) {
            super.render(delta); //clears screen
        }

        @Override
        public void dispose() {
            super.dispose();
            stage.dispose();
        }

        //note: called on setScreen
        @Override
        public void show() {
            super.show();  //sets change boolean to false
            super.inputMultiplexer.addProcessor(stage);
            //   Gdx.input.setInputProcessor(stage); //stage will receive inputs, including clicks, that will be delegated through table to buttons
        }

        //note: called on minimize/lose focus
        @Override
        public void pause() {
            super.pause();
            super.inputMultiplexer.removeProcessor(stage);
        }

        //note: called on restore
        @Override
        public void resume() {
            super.resume();
            super.inputMultiplexer.addProcessor(stage);
        }

        @Override
        public void hide() {
            super.inputMultiplexer.removeProcessor(stage);
        }
        //called on resize - we can experiment with different viewport types
        @Override
        public void resize(int width, int height) {
            super.resize(width, height);
        }

    protected String getPlayerName() {
        return null;
    }
    protected String getIP() {
        return null;
    }
    protected String getPort() {
        return null;
    }

    }




