/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csci499;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.util.ScreenType;
import com.util.GameType;

/**
 *
 * @author tmcdaniel
 */
public class StartScreen extends AbstractScreen implements Screen {


    private GameStage stage;
    //buttons for splitting flow - local game or multiplayer
    //private ImageButton localButton;
    //private ImageButton hostButton;
   // private ImageButton clientButton;

    //private Table uiTable;

    public StartScreen() {

        super();
        stage = new GameStage(super.mgr, "startScene"); //TODO add these scene names to a lookup table
        //set up UI widgets
       // localButton = new ImageButton(skin.get("localButton", ImageButton.ImageButtonStyle.class)); //need to add these resources to the TextureAtlas
        //localButton.addListener(new ClickListener() { public void clicked(InputEvent e, float x, float y) {startLocal();}}); //register click listener
        //clientButton = new ImageButton(skin.get("clientButton", ImageButton.ImageButtonStyle.class)); //need to add these resources to the TextureAtlas
        //clientButton.addListener(new ClickListener() {public void clicked(InputEvent e, float x, float y) { startMulti(GameType.CLIENT);}}); //register click listener
        //hostButton = new ImageButton(skin.get("hostButton", ImageButton.ImageButtonStyle.class)); //need to add these resources to the TextureAtlas
        //hostButton.addListener(new ClickListener() {public void clicked(InputEvent e, float x, float y) { startMulti(GameType.HOST); }}); //register click listener

        //table for UI widgets
        //uiTable = new Table();
        //super.rootTable.add(uiTable);

        //uiTable.setSkin(super.skin);
        //uiTable.center(); //center horz
        //uiTable.pad(uiTable.getParent().getHeight() / 2); //center vert in root table
        //uiTable.padTop(Value.percentHeight(0.33f));
        //uiTable.add(new Label("LOCAL GAME", skin, "default")).fill().padRight(Value.percentWidth(0.33f, uiTable)); //replace default when assets added
        //uiTable.add(new Label("PEER-TO-PEER GAME", skin, "default")).fill(); //replace default styleName when asset added
        //uiTable.row(); //add a row before we put the buttons in!
        //uiTable.add(localButton).fill().padRight(Value.percentWidth(0.33f)); //add buttons
        //uiTable.add(clientButton).fill();
        //uiTable.add(hostButton).fill();

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



    //called for local button
    private void startLocal() {
    this.setChangeScreen(ScreenType.LOCAL);
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