package com.csci499;

import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.SceneLoader;
/**
 * Created by tmcdaniel on 4/7/2015.
 */
public class TestStage extends Overlap2DStage {

    public TestStage() {
        this.sceneLoader = new SceneLoader();
        this.initSceneLoader();
        sceneLoader.loadScene("MainScene");
        this.addActor(sceneLoader.getRoot());

    }
}
