package com.juchap.snake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.juchap.snake.Utility.FontManager;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Utility.HighScoreManager;
import com.juchap.snake.Utility.ScreenEnum;
import com.juchap.snake.Utility.ScreenManager;
import com.juchap.snake.Utility.SoundManager;
import com.juchap.snake.Utility.VibrationManager;


public class MySnakeGame extends Game {
    @Override
    public void create () {
        GlobalVars.initVars();
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
                        ScreenManager.getInstance().showScreen( ScreenEnum.MAIN_MENU );
                    }
                });
            }
        }).start();
    }
}
