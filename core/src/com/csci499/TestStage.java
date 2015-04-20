package com.csci499;

import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.uwsoft.editor.renderer.Overlap2DStage;

/**
 * Created by tmcdaniel on 4/7/2015.
 */
public class TestStage extends Overlap2DStage {

private GameResourceManager mgr;

    public TestStage(String sceneName) {
        super(new ExtendViewport(TestAdapter.mgr.currentResolution.width, TestAdapter.mgr.currentResolution.height));
        mgr = TestAdapter.mgr;
        initSceneLoader(mgr);
        sceneLoader.setResolution(mgr.currentResolution.name);
        sceneLoader.loadScene(sceneName);
        addActor(sceneLoader.getRoot());

    }


}
