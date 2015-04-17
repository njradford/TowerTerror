/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csci499;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.util.ScreenType;

/**
 *
 * @author tmcdaniel
 */
public class LocalScreen extends AbstractScreen implements Screen {
    private static final int MAXPLAYERS = 6;
   // private Table uiTable;
   // private ImageButton startButton;
   // private TextArea[] nameAreas;
    private GameStage stage;
    private String[] playerNames;

    public LocalScreen() {

        super();
        stage = new GameStage(super.mgr, "localScene");

        //set up UI widgets
 //       startButton = new ImageButton(super.skin, "localButton"); //need to add these resources to the TextureAtlas
 //       startButton.addListener(new ClickListener() {
   //         public void clicked(InputEvent e, float x, float y) {
 //               startGame();
 //           }
 //       }); //register click listener

        //table for UI widgets
 //       uiTable = new Table();
 //       super.rootTable.add(uiTable);

     //   uiTable.setSkin(skin);
      //  uiTable.center(); //center horz
      //  uiTable.pad(uiTable.getParent().getHeight() / 2); //center vert?
      //  uiTable.row();
      //  uiTable.add(new Label("Enter Player Names", skin, "default")).left().expandX(); //replace default when assets added
       // uiTable.row();

 //       nameAreas = new TextArea[MAXPLAYERS];
   //     for (int i = 0; i < nameAreas.length; i++) {
     //       nameAreas[i] = new TextArea("default", super.skin); //add to skin
       //     nameAreas[i].setText("");
         //   if (i % 2 == 0) {
           //     uiTable.add(nameAreas[i]).uniform().pad(Value.zero, Value.percentWidth(0.33f),Value.zero, Value.percentWidth(.033f));
            //} else {
             //   uiTable.add(nameAreas[i]).uniform();
              //  uiTable.row();
           // }
       // }

        //uiTable.add(startButton).colspan(uiTable.getColumns()).padTop(Value.percentHeight(0.1f)); //add button

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




    private void startGame() {
        super.setChangeScreen(ScreenType.GAME);

    }

    protected String[] getPlayers() {
        return playerNames;
    }


}