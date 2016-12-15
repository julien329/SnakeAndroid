package com.juchap.snake.Utility;

import com.juchap.snake.Screens.AbstractScreen;


public enum ScreenEnum {
    MAIN_MENU {
        public com.juchap.snake.Screens.AbstractScreen getScreen() {
            //return new MainMenuScreen();
            return null;
        }
    },
    GAME {
        public com.juchap.snake.Screens.AbstractScreen getScreen() {
            return new com.juchap.snake.Screens.GameScreen();
        }
    };

    public abstract AbstractScreen getScreen();
}
