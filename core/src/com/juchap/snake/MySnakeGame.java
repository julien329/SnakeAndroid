package com.juchap.snake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.juchap.snake.Managers.ColorManager;
import com.juchap.snake.Managers.DifficultyManager;
import com.juchap.snake.Managers.FontManager;
import com.juchap.snake.Managers.HighScoreManager;
import com.juchap.snake.Managers.InputManager;
import com.juchap.snake.Managers.SoundManager;
import com.juchap.snake.Managers.VibrationManager;
import com.juchap.snake.Services.PlayServices;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Utility.ScreenEnum;
import com.juchap.snake.Utility.ScreenManager;

public class MySnakeGame extends Game {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public MySnakeGame(PlayServices playServices) {
        _playServices = playServices;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void create() {
        GlobalVars.initVars();
        ColorManager.initManager();
        DifficultyManager.initManager();
        InputManager.initManager();
        VibrationManager.initManager();
        SoundManager.initManager();
        HighScoreManager.initManager();
        FontManager.initManager();

        Gdx.input.setCatchKey(Input.Keys.BACK, true);

        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen( ScreenEnum.SPLASH );

        new Thread(() -> {
            FontManager.createAllFont();

            Gdx.app.postRunnable(() -> {
                FontManager.loadAllFont();
                ScreenManager.getInstance().showScreen( ScreenEnum.MAIN_MENU );
            });
        }).start();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public PlayServices getPlayServices() { return _playServices; }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private final PlayServices _playServices;
}