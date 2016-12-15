package com.juchap.snake;

import com.badlogic.gdx.Game;
import com.juchap.snake.Utility.FontManager;
import com.juchap.snake.Utility.GlobalVars;


public class MySnakeGame extends Game {
    @Override
    public void create () {
        FontManager.initAllFonts();
        GlobalVars.initVars();

        com.juchap.snake.Utility.ScreenManager.getInstance().initialize(this);
        com.juchap.snake.Utility.ScreenManager.getInstance().showScreen( com.juchap.snake.Utility.ScreenEnum.GAME );
    }
}
