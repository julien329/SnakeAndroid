package com.juchap.snake.Utility;

import com.badlogic.gdx.Gdx;


public class GlobalVars {

    public static void initVars(int navBarHeight) {
        UNIT_SIZE = Gdx.graphics.getWidth() / 32;

        final int height = Gdx.graphics.getHeight() - navBarHeight;
        final int GRID_OFFSET_X = (Gdx.graphics.getWidth() % UNIT_SIZE) / 2;
        final int GRID_OFFSET_Y = (height % UNIT_SIZE) / 2;

        GRID_HEIGHT = height - (height % UNIT_SIZE);
        GRID_WIDTH = Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() % UNIT_SIZE);
        PADDING_Y = GRID_HEIGHT / 64;

        BOTTOM = navBarHeight + GRID_OFFSET_Y;
        TOP = BOTTOM + GRID_HEIGHT;
        LEFT = GRID_OFFSET_X;
        RIGHT = LEFT + GRID_WIDTH;
        CENTER_X = LEFT + (GRID_WIDTH / 2);
        CENTER_Y = BOTTOM + (GRID_HEIGHT / 2);

        GAME_GRID_HEIGHT = (int)(Math.floor((height / UNIT_SIZE) * (5.f/6.f)) * UNIT_SIZE) + UNIT_SIZE;
        GAME_GRID_TOP = BOTTOM + GAME_GRID_HEIGHT;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static int UNIT_SIZE;
    public static int GRID_WIDTH;
    public static int GRID_HEIGHT;
    public static int GAME_GRID_HEIGHT;
    public static int CENTER_Y;
    public static int CENTER_X;
    public static int TOP;
    public static int BOTTOM;
    public static int LEFT;
    public static int RIGHT;
    public static int GAME_GRID_TOP;
    public static int PADDING_Y;
}
