package com.csci499;

import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.SceneLoader;
/**
 * Created by tmcdaniel on 4/7/2015.
 */
public class TestStage extends Overlap2DStage {
    GameResourceManager mgr;
    public TestStage(GameResourceManager mgr) {
        super(new ExtendViewport(mgr.stageWidth, mgr.currentResolution.height));
        this.mgr = mgr;
        // This will create SceneLoader instance and configure all things like default resource manager, physics e.t.c
        initSceneLoader(mgr);
        super.sceneLoader.setResolution(mgr.currentResolution.name);
        super.sceneLoader = new SceneLoader();
        this.initSceneLoader();
        super.sceneLoader.loadScene("MainScene");
        this.addActor(super.sceneLoader.getRoot());

    }
}
