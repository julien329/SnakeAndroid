package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.juchap.snake.Managers.ColorManager;
import com.juchap.snake.Utility.GlobalVars;

public abstract class AbstractScreen extends Stage implements Screen {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    protected AbstractScreen() {
        _uiRenderer = new ShapeRenderer();
        _spriteBatch = new SpriteBatch();
        _leftBorderX = GlobalVars.LEFT;
        _rightBorderX = GlobalVars.RIGHT - GlobalVars.UNIT_SIZE;
        _bottomBorderY = GlobalVars.BOTTOM;
        _topBorderY = GlobalVars.TOP - GlobalVars.UNIT_SIZE;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public abstract void buildStage();

    ////////////////////////////////////////////////////////////////////////////////////////////////
    protected void drawBorders() {
        _uiRenderer.begin(ShapeRenderer.ShapeType.Filled);
        _uiRenderer.setColor(ColorManager.getFrontColor());
        _uiRenderer.rect(_leftBorderX, _bottomBorderY, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT);
        _uiRenderer.rect(_leftBorderX, _bottomBorderY, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        _uiRenderer.rect(_leftBorderX, _topBorderY, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        _uiRenderer.rect(_rightBorderX, _bottomBorderY, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT);
        _uiRenderer.end();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _uiRenderer.begin(ShapeRenderer.ShapeType.Filled);
        _uiRenderer.setColor(ColorManager.getBackColor());
        _uiRenderer.rect(GlobalVars.LEFT, GlobalVars.BOTTOM, GlobalVars.GRID_WIDTH, GlobalVars.GRID_HEIGHT);
        _uiRenderer.end();

        drawBorders();

        super.act(delta);
        super.draw();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void dispose() {
        super.dispose();
        _uiRenderer.dispose();
        _spriteBatch.dispose();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void resize(int width, int height) {
        getViewport().update(width, height, true);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void resume() {
        Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.requestRendering();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override public void show() {
        Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.requestRendering();
    }

    @Override public void hide() {}
    @Override public void pause() {}

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    protected ShapeRenderer _uiRenderer;
    protected SpriteBatch _spriteBatch;

    private final int _leftBorderX;
    private final int _rightBorderX;
    private final int _topBorderY;
    private final int _bottomBorderY;
}



