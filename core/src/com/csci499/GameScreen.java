/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csci499;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.IBaseItem;
import com.uwsoft.editor.renderer.actor.ImageItem;

/**
 *
 * @author tmcdaniel
 */
public class GameScreen extends AbstractScreen implements Screen {

    private GameStage stage;
    private CompositeItem root;
    private GameStateInterface gameState;
    private CompositeItem[][] cardItems;
    private ImageItem[] contextItems;
    private CompositeItem progressItem;
    private CompositeItem[] monsterItems;
    public GameScreen() {
        super();
        try {
            stage = new GameStage("gameScreen");
        } catch (NullPointerException e) {
            System.err.println("ERR -- GAMESCREEN -- () -- SCENE/SCENE ASSETS NOT FOUND");
        }

        try {
            gameState = TerrorGDXGame.gameState;
        } catch (NullPointerException e) {
            System.err.println("ERR -- GAMESCREEN -- () -- GAMESTATE NOT INITALIZED");
        }

        root = stage.sceneLoader.getRoot();
        contextItems = new ImageItem[] {(ImageItem)root.getItemById("orderDiscard"),
                (ImageItem) root.getItemById("orderTrade"),
                (ImageItem) root.getItemById("orderAttack")};

        for (ImageItem context: contextItems) {
        context.addAction(Actions.moveBy(-context.getWidth(), 0));
        }






        }


    public void initializeBoard() {

        /*
            for (int i = 0; i < cardButtons.length; i++) {
            java.awt.Component[] comps = handPanels[i].getComponents();

            for (int j = 0; j < cardButtons[i].length; j++) {
                if (comps[j] instanceof javax.swing.JButton) {
                    cardButtons[i][j] = (javax.swing.JButton) comps[j];
                }
            }
        }
        for (int k = 0; k < cardButtons.length; k++) {
            for (int l = 0; l < cardButtons[k].length; l++) {
                System.out.println("Card Button:" + " " + cardButtons[k][l]);
            }
        }

        for (int m = 0; m < players.length; m++) {
            handLabels[m].setText(players[m].substring(0, Math.min(8, players[m].length())).trim());
        }

        //monsterProgBar.setMaximum(gameState.getUnplayedMonsters());

        this.updateGame();

        if (networkGame) {
            phaseTitleLabel1.setIcon(p2pGame);
            if (!isHost) {
                netProcess();
            } else {
                phaseTitleLabel2.setIcon(activeNow);
            }
        } else {

        }

         */
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
}