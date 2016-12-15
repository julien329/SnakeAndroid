package com.juchap.snake.Utility;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;


public class ScreenManager {

    private ScreenManager() {
        super();
    }

    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public void initialize(Game game) {
        this.game = game;
    }


    public void showScreen(ScreenEnum screenEnum) {
        Screen oldScreen = game.getScreen();

        currentScreen = screenEnum.getScreen();
        currentScreen.buildStage();
        game.setScreen(currentScreen);

        if (oldScreen != null)
            oldScreen.dispose();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public com.juchap.snake.Screens.AbstractScreen getScreen() { return currentScreen; }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static ScreenManager instance;
    private com.juchap.snake.Screens.AbstractScreen currentScreen;
    private Game game;
}
