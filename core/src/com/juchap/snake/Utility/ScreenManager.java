package com.juchap.snake.Utility;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.juchap.snake.MySnakeGame;
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

    public void initialize(MySnakeGame game) {
        this.game = game;
        Gdx.graphics.setContinuousRendering(false);
    }

    public void showScreen(ScreenEnum screenEnum, Object... params) {
        Screen oldScreen = game.getScreen();

        currentScreen = screenEnum.getScreen(params);
        currentScreen.buildStage();
        game.setScreen(currentScreen);

        if (oldScreen != null)
            oldScreen.dispose();
    }

    public void signIn() { game.getPlayServices().signIn(); }
    public void signOut() { game.getPlayServices().signOut(); }
    public void rateGame() { game.getPlayServices().rateGame(); }
    public void unlockAchievement(String achievement) { game.getPlayServices().unlockAchievement(achievement); }
    public void incrementAchievement(String achievement, int amount) { game.getPlayServices().incrementAchievement(achievement, amount); }
    public void submitScore(String difficulty, int score) { game.getPlayServices().submitScore(difficulty, score); }
    public void showAchievements() { game.getPlayServices().showAchievements(); }
    public void showScores() { game.getPlayServices().showScores(); }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public AbstractScreen getScreen() { return currentScreen; }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static ScreenManager instance;
    private AbstractScreen currentScreen;
    private MySnakeGame game;
}
