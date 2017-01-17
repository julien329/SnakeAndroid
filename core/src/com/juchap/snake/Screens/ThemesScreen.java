package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.juchap.snake.Utility.ScreenEnum;
import com.juchap.snake.Utility.ScreenManager;


public class ThemesScreen extends AbstractScreen {

    public ThemesScreen() {
        super();
        Gdx.input.setInputProcessor(this);

        initButtonSkin();
        initGlyphs();
    }

    @Override
    public void buildStage() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        drawText();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK) ) {
            ScreenManager.getInstance().showScreen(ScreenEnum.OPTIONS);
        }
        return false;
    }

    private void drawText() {
        spriteBatch.begin();

        spriteBatch.end();
    }

    private void initButtonSkin() {

    }

    private void initGlyphs() {

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

}

