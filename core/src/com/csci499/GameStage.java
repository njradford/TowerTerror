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
    private static boolean first = true;

private GameResourceManager mgr;

    public GameStage(String sceneName) {
        super(new ExtendViewport(TerrorGDXGame.mgr.currentResolution.width, TerrorGDXGame.mgr.currentResolution.height));
        mgr = TerrorGDXGame.mgr;
        initSceneLoader(mgr);
        sceneLoader.setResolution(mgr.currentResolution.name);
        sceneLoader.loadScene(sceneName);
        addActor(sceneLoader.getRoot());

    }


}
