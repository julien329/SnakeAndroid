package com.juchap.snake.Utility;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.juchap.snake.Screens.AbstractScreen;


public class ScreenManager {

    private ScreenManager() {
        super();
    }

    public static ScreenManager getInstance() {
        if (instance == null)
            instance = new ScreenManager();

        return instance;
    }

    public void initialize(Game game) {
        this.game = game;
    }

    public void showScreen(ScreenEnum screenEnum, Object... params) {
        Screen oldScreen = game.getScreen();

        currentScreen = screenEnum.getScreen(params);
        currentScreen.buildStage();
        game.setScreen(currentScreen);

        if (oldScreen != null)
            oldScreen.dispose();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public AbstractScreen getScreen() { return currentScreen; }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static ScreenManager instance;
    private AbstractScreen currentScreen;
    private Game game;
}
