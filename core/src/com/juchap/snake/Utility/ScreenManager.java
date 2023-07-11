package com.juchap.snake.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.juchap.snake.MySnakeGame;
import com.juchap.snake.Screens.AbstractScreen;

public class ScreenManager {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private ScreenManager() { }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static ScreenManager getInstance() {
        if (_instance == null) {
            _instance = new ScreenManager();
        }

        return _instance;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void initialize(MySnakeGame game) {
        _game = game;
        Gdx.graphics.setContinuousRendering(false);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void showScreen(ScreenEnum screenEnum, Object... params) {
        Screen oldScreen = _game.getScreen();

        _currentScreen = screenEnum.getScreen(params);
        _currentScreen.buildStage();

        _game.setScreen(_currentScreen);

        if (oldScreen != null) {
            oldScreen.dispose();
        }
    }

    public void rateGame() { _game.getPlayServices().rateGame(); }
    public void unlockAchievement(String achievement) { _game.getPlayServices().unlockAchievement(achievement); }
    public void incrementAchievement(String achievement, int amount) { _game.getPlayServices().incrementAchievement(achievement, amount); }
    public void submitScore(String difficulty, int score) { _game.getPlayServices().submitScore(difficulty, score); }
    public void showAchievements() { _game.getPlayServices().showAchievements(); }
    public void showScores() { _game.getPlayServices().showScores(); }
    public void shareApp() { _game.getPlayServices().shareApp(); }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public AbstractScreen getScreen() { return _currentScreen; }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static ScreenManager _instance;
    private AbstractScreen _currentScreen;
    private MySnakeGame _game;
}
