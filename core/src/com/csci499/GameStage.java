package com.csci499;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.SceneLoader;
/**
 * Created by tmcdaniel on 4/7/2015.
 */
public class GameStage extends Overlap2DStage {
    GameResourceManager mgr;
    public GameStage(GameResourceManager mgr, String sceneName) {
        super(new ExtendViewport(mgr.stageWidth, mgr.currentResolution.height));
        this.mgr = mgr;
        initSceneLoader(mgr);
        sceneLoader.setResolution(mgr.currentResolution.name);
        sceneLoader.loadScene(sceneName);
        this.addActor(sceneLoader.getRoot());

    }


}
