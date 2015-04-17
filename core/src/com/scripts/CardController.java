package com.scripts;

/**
 * Created by tmcdaniel on 4/16/2015.
 */

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;

/**
 * Created by tmcdaniel on 04/15/2015.
 */
public class CardController implements IScript {

    private Overlap2DStage stage;
    private CompositeItem item;

    private Vector2 initialCoordinates;

    public CardController(Overlap2DStage stage) {
        this.stage = stage;
    }

    @Override
    public void init(CompositeItem item) {
        this.item = item;

        //set origin at ctr?
        item.setOrigin(Align.center);

        //setting initial position
        initialCoordinates = new Vector2(item.getX(), item.getY());
    }

    @Override
    public void act(float delta) {
        // Check for control events
    }

    public CompositeItem getActor() {
        return item;
    }

    @Override
    public void dispose() {

    }
}