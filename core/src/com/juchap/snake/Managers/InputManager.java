package com.juchap.snake.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class InputManager {

    public static void initManager() {
        inputPrefs = Gdx.app.getPreferences(PREFS_NAME);

        if(inputPrefs.contains(KEY_NAME))
            loadFromPrefs();
        else {
            inputType = TYPE_SWIPE;
            saveToPrefs();
        }
    }

    private static void loadFromPrefs() {
        inputType = inputPrefs.getInteger(KEY_NAME);
    }

    private static void saveToPrefs() {
        inputPrefs.putInteger(KEY_NAME, inputType);
        inputPrefs.flush();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void setType(int type) {
        inputType = type;
        saveToPrefs();
    }

    public static boolean isSwipe() { return inputType == TYPE_SWIPE; }
    public static boolean isTouch() { return inputType == TYPE_TOUCH; }
    public static int getType() { return inputType; }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String PREFS_NAME = "Input";
    private static final String KEY_NAME = "type";
    private static final int TYPE_SWIPE = 0;
    private static final int TYPE_TOUCH = 1;

    private static Preferences inputPrefs;

    private static int inputType;
}
