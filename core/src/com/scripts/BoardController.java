package com.scripts;

/**
 * Created by tmcdaniel on 4/16/2015.
 */

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;

/**
 * Created by tmcdaniel on 04/15/2015.
 */
public class BoardController implements IScript {

    private Overlap2DStage stage;
    private CompositeItem board;

    private Vector2 initialCoordinates;

    public BoardController(Overlap2DStage stage) {
        this.stage = stage;
    }

    @Override
    public void init(CompositeItem board) {
        this.board = board;

        board.addListener(new ClickListener() {
            public boolean touchDown() {
            return true;
            }
            public void touchUp() {
                //if board sector

                //if castle sector

            }
        });

        //set origin at TL
        board.setOrigin(Align.topLeft);

        //setting initial position
        initialCoordinates = new Vector2(board.getX(), board.getY());
    }

    @Override
    public void act(float delta) {
        // Check for control events
    }

    public CompositeItem getActor() {
        return board;
    }


    @Override
    public void dispose() {

    }
}