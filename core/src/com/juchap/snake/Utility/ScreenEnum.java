package com.juchap.snake.Utility;

import com.juchap.snake.Screens.AbstractScreen;
import com.juchap.snake.Screens.GameScreen;
import com.juchap.snake.Screens.MainMenuScreen;


public enum ScreenEnum {
    MAIN_MENU {
        public AbstractScreen getScreen() {
            return new MainMenuScreen();
        }
    },
    GAME {
        public AbstractScreen getScreen() {
            return new GameScreen();
        }
    };

    public abstract AbstractScreen getScreen();
}
