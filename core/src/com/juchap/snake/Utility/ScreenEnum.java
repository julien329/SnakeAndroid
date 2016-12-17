package com.juchap.snake.Utility;

import com.juchap.snake.Screens.AbstractScreen;
import com.juchap.snake.Screens.GameOverScreen;
import com.juchap.snake.Screens.GameScreen;
import com.juchap.snake.Screens.MainMenuScreen;


public enum ScreenEnum {
    MAIN_MENU {
        public AbstractScreen getScreen(Object... params) {
            return new MainMenuScreen();
        }
    },
    GAME {
        public AbstractScreen getScreen(Object... params) {
            return new GameScreen();
        }
    },
    GAME_OVER {
        public AbstractScreen getScreen(Object... params) {
            return new GameOverScreen((Integer)params[0]);
        }
    };

    public abstract AbstractScreen getScreen(Object... params);
}
