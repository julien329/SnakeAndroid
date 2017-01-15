package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.juchap.snake.Managers.ColorManager;
import com.juchap.snake.Utility.GlobalVars;


public abstract class AbstractScreen extends Stage implements Screen {

    protected AbstractScreen() {
        super();
        uiRenderer = new ShapeRenderer();
    }

    public abstract void buildStage();

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        uiRenderer.begin(ShapeRenderer.ShapeType.Filled);
        uiRenderer.setColor(ColorManager.getBackColor());
        uiRenderer.rect(GlobalVars.GRID_OFFSET_X, GlobalVars.GRID_OFFSET_Y, GlobalVars.GRID_WIDTH, GlobalVars.GRID_HEIGHT);
        uiRenderer.end();

        super.act(delta);
        super.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        uiRenderer.dispose();
    }

    @Override
    public void resize(int width, int height) {
        getViewport().update(width, height, true);
    }

    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    protected ShapeRenderer uiRenderer;
}



