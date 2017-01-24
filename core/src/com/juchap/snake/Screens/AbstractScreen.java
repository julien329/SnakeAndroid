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

    protected AbstractScreen() {
        super();
        uiRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        leftBorderX = GlobalVars.GRID_OFFSET_X;
        rightBorderX = GlobalVars.GRID_OFFSET_X + GlobalVars.GRID_WIDTH - GlobalVars.UNIT_SIZE;
        bottomBorderY = GlobalVars.GRID_OFFSET_Y;
        topBorderY = GlobalVars.GRID_OFFSET_Y + GlobalVars.GRID_HEIGHT - GlobalVars.UNIT_SIZE;
    }

    public abstract void buildStage();

    protected void drawBorders() {
        uiRenderer.begin(ShapeRenderer.ShapeType.Filled);
        uiRenderer.setColor(ColorManager.getFrontColor());
        uiRenderer.rect(leftBorderX, bottomBorderY, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT);
        uiRenderer.rect(leftBorderX, bottomBorderY, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        uiRenderer.rect(leftBorderX, topBorderY, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        uiRenderer.rect(rightBorderX, bottomBorderY, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT);
        uiRenderer.end();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        uiRenderer.begin(ShapeRenderer.ShapeType.Filled);
        uiRenderer.setColor(ColorManager.getBackColor());
        uiRenderer.rect(GlobalVars.GRID_OFFSET_X, GlobalVars.GRID_OFFSET_Y, GlobalVars.GRID_WIDTH, GlobalVars.GRID_HEIGHT);
        uiRenderer.end();

        drawBorders();

        super.act(delta);
        super.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        uiRenderer.dispose();
        spriteBatch.dispose();
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
    protected SpriteBatch spriteBatch;

    private int leftBorderX;
    private int rightBorderX;
    private int topBorderY;
    private int bottomBorderY;
}



