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
import com.uwsoft.editor.renderer.actor.LabelItem;
import com.uwsoft.editor.renderer.script.SimpleButtonScript;

/**
 *
 * @author tmcdaniel
 */
public class GameScreen extends AbstractScreen implements Screen {

    private GameStage stage;
    private CompositeItem root;
    private GameStateInterface gameState;

    private ImageItem[][] cardItems;
    private ImageItem[][] wallItems;
    private ImageItem[][] castleItems;
    private ImageItem[] contextItems;

    private ImageItem nextButton;
    private LabelItem monsterCounter;
    private LabelItem[] playerNames;
    private LabelItem[] playerScores;
    private LabelItem otherPlayerLabel;

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
        //access modifiable view layer components
        root = stage.sceneLoader.getRoot();
        contextItems = new ImageItem[] {root.getImageById("orderDiscard"),
                root.getImageById("orderTrade"),
                root.getImageById("orderAttack")};

        monsterCounter = root.getLabelById("monstersRemainingNum");

        cardItems = new ImageItem[][] {{root.getImageById("playerCard0"), root.getImageById("playerCard1"),
                root.getImageById("playerCard2"), root.getImageById("playerCard3"),root.getImageById("playerCard4"),
                root.getImageById("playerCard5")}, {root.getImageById("otherCard0"), root.getImageById("otherCard1"),
                root.getImageById("otherCard2"), root.getImageById("otherCard3"), root.getImageById("otherCard4"),
                root.getImageById("otherCard5")}};

        wallItems = new ImageItem[][] {{root.getImageById("wall_red1"), root.getImageById("wall_red2"),
                root.getImageById("wall_green1"), root.getImageById("wall_green2"),root.getImageById("wall_blue1"),
                root.getImageById("wall_blue2")}, {root.getImageById("wallRubble_red1"), root.getImageById("wallRubble_red2"),
                root.getImageById("wallRubble_green1"), root.getImageById("wallRubble_green2"), root.getImageById("wallRubble_blue1"),
                root.getImageById("wallRubble_blue2")}, {root.getImageById("wallCleared_red1"), root.getImageById("wallCleared_red2"),
                root.getImageById("wallCleared_green1"), root.getImageById("wallCleared_green2"), root.getImageById("wallCleared_blue1"),
                root.getImageById("wallCleared_green2")}};

        castleItems = new ImageItem[][] {{root.getImageById("castle_red1"), root.getImageById("castle_red2"),
                root.getImageById("castle_green1"), root.getImageById("castle_green2"),root.getImageById("castle_blue1"),
                root.getImageById("castle_blue2")}, {root.getImageById("castleRubble_red1"), root.getImageById("castleRubble_red2"),
                root.getImageById("castleRubble_green1"), root.getImageById("castleRubble_green2"), root.getImageById("castleRubble_blue1"),
                root.getImageById("castleRubble_blue2")}, {root.getImageById("castleCleared_red1"), root.getImageById("castleCleared_red2"),
                root.getImageById("castleCleared_green1"), root.getImageById("castleCleared_green2"), root.getImageById("castleCleared_blue1"),
                root.getImageById("castleCleared_green2")}};


        playerNames = new LabelItem[] {root.getLabelById("mainPlayersName"), root.getLabelById("otherPlayersName")};

        playerScores = new LabelItem[] {root.getLabelById("playerScore"), root.getLabelById("otherScore")};

        otherPlayerLabel = root.getLabelById("playerNumber");

        nextButton = stage.sceneLoader.getRoot().getImageById("nextButton");
        nextButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                nextButton();
            }
        });

        initializeBoard();

        }


    public void initializeBoard() {

        for (ImageItem context: contextItems) {
            context.addAction(Actions.moveBy(-context.getWidth(), 0));
            context.getColor().a=0; //make them invisible to start with, and off screen
        }
        monsterCounter.setText(String.valueOf(gameState.getNumMonsterTokens()));

        playerNames[0].setText(gameState.getPlayerName(gameState.getCurrentPlayer()));
        if (gameState.getPlayers() > 1) {
            playerNames[1].setText(gameState.getPlayerName(gameState.getCurrentPlayer() + 1 % 6));
        }

        
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

    private void nextButton() {
        //button handler for next hand button
    }
}