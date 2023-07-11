package com.juchap.snake.Utility;

import com.badlogic.gdx.Gdx;

public class GlobalVars {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static void initVars() {
        int height = Gdx.graphics.getHeight();
        int width = Gdx.graphics.getWidth();
        final float ratio = (float)height / width;

        if (ratio < PROPER_RATIO) {
            width = (int)(height / PROPER_RATIO);
        }
        else if (ratio > PROPER_RATIO) {
            height = (int)(width * PROPER_RATIO);
        }

        UNIT_SIZE = width / 32;

        final int GRID_OFFSET_X = ((Gdx.graphics.getWidth() - width) + (width % UNIT_SIZE)) / 2;
        final int GRID_OFFSET_Y = ((Gdx.graphics.getHeight() - height) + (height % UNIT_SIZE)) / 2;

        GRID_HEIGHT = height - (height % UNIT_SIZE);
        GRID_WIDTH = width - (width % UNIT_SIZE);
        PADDING_Y = GRID_HEIGHT / 64;

        BOTTOM = GRID_OFFSET_Y;
        TOP = BOTTOM + GRID_HEIGHT;
        LEFT = GRID_OFFSET_X;
        RIGHT = LEFT + GRID_WIDTH;
        CENTER_X = LEFT + (GRID_WIDTH / 2);
        CENTER_Y = BOTTOM + (GRID_HEIGHT / 2);

        GAME_GRID_TOP = TOP - (8 * UNIT_SIZE);
        GAME_GRID_HEIGHT = GAME_GRID_TOP - BOTTOM;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final float PROPER_RATIO = 16.f/9.f;

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
