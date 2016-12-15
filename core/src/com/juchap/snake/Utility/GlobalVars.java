package com.juchap.snake.Utility;

import com.badlogic.gdx.Gdx;


public class GlobalVars {

    public static void initVars() {
        UNIT_SIZE = Gdx.graphics.getWidth() / 32;
        GRID_WIDTH = Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() % UNIT_SIZE);
        GRID_HEIGHT = Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() % UNIT_SIZE);
        GRID_OFFSET_X = (Gdx.graphics.getWidth() % UNIT_SIZE) / 2;
        GRID_OFFSET_Y = (Gdx.graphics.getHeight() % UNIT_SIZE) / 2;
        GAME_GRID_HEIGHT = (int)(Math.floor((Gdx.graphics.getHeight() / UNIT_SIZE) * (5.0/6.0)) * UNIT_SIZE) + GAME_GRID_HEIGHT;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static int UNIT_SIZE;
    public static int GRID_WIDTH;
    public static int GRID_HEIGHT;
    public static int GRID_OFFSET_X;
    public static int GRID_OFFSET_Y;
    public static int GAME_GRID_HEIGHT;


}
