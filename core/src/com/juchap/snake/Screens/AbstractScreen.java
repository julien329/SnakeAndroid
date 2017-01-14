package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.juchap.snake.Managers.ColorManager;


public abstract class AbstractScreen extends Stage implements Screen {

    protected AbstractScreen() {
        super();
    }

    public abstract void buildStage();

    @Override
    public void render(float delta) {
        Color backColor = ColorManager.getBackColor();
        Gdx.gl.glClearColor(backColor.r, backColor.g, backColor.b, backColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.act(delta);
        super.draw();
    }

    @Override
    public void resize(int width, int height) {
        getViewport().update(width, height, true);
    }

    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
}



