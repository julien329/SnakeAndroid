package com.juchap.snake;

import com.badlogic.gdx.Game;
import com.juchap.snake.Utility.FontManager;


public class MySnakeGame extends Game {
    @Override
    public void create () {
        FontManager.initAllFonts();

        com.juchap.snake.Utility.ScreenManager.getInstance().initialize(this);
        com.juchap.snake.Utility.ScreenManager.getInstance().showScreen( com.juchap.snake.Utility.ScreenEnum.GAME );
    }
}
