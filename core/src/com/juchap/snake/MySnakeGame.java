package com.juchap.snake;

import com.badlogic.gdx.Game;
import com.juchap.snake.Utility.FontManager;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Utility.ScreenEnum;
import com.juchap.snake.Utility.ScreenManager;


public class MySnakeGame extends Game {
    @Override
    public void create () {
        FontManager.initAllFonts();
        GlobalVars.initVars();

        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen( ScreenEnum.MAIN_MENU );
    }
}
