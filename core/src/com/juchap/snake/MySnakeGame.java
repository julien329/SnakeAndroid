package com.juchap.snake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.juchap.snake.Services.PlayServices;
import com.juchap.snake.Utility.DifficultyManager;
import com.juchap.snake.Utility.FontManager;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Utility.HighScoreManager;
import com.juchap.snake.Utility.InputManager;
import com.juchap.snake.Utility.ScreenEnum;
import com.juchap.snake.Utility.ScreenManager;
import com.juchap.snake.Utility.SoundManager;
import com.juchap.snake.Utility.VibrationManager;


public class MySnakeGame extends Game {

    public MySnakeGame(PlayServices playServices) {
        this.playServices = playServices;
    }

    @Override
    public void create () {
        GlobalVars.initVars();
        DifficultyManager.initManager();
        InputManager.initManager();
        VibrationManager.initManager();
        SoundManager.initManager();
        HighScoreManager.initManager();
        FontManager.initManager();

        Gdx.input.setCatchBackKey(true);
        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen( ScreenEnum.SPLASH );

        new Thread(new Runnable() {
            @Override
            public void run() {
                FontManager.createAllFont();
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        FontManager.loadAllFont();
                        playServices.signIn();
                        ScreenManager.getInstance().showScreen( ScreenEnum.MAIN_MENU );
                    }
                });
            }
        }).start();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public PlayServices getPlayServices() { return playServices; }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private PlayServices playServices;
}
