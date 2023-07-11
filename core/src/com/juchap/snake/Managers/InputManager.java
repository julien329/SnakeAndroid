package com.juchap.snake.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class InputManager {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static void initManager() {
        _inputPrefs = Gdx.app.getPreferences(PREFS_NAME);

        if (_inputPrefs.contains(KEY_NAME)) {
            loadFromPrefs();
        }
        else {
            _inputType = TYPE_SWIPE;
            saveToPrefs();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static void loadFromPrefs() {
        _inputType = _inputPrefs.getInteger(KEY_NAME);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static void saveToPrefs() {
        _inputPrefs.putInteger(KEY_NAME, _inputType);
        _inputPrefs.flush();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static void setType(int type) {
        _inputType = type;
        saveToPrefs();
    }

    public static boolean isSwipe() { return _inputType == TYPE_SWIPE; }
    public static boolean isTouch() { return _inputType == TYPE_TOUCH; }
    public static boolean isDpad() { return _inputType == TYPE_DPAD; }
    public static boolean isHalfDpad() { return _inputType == TYPE_HALF_DPAD; }
    public static int getType() { return _inputType; }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String PREFS_NAME = "Input";
    private static final String KEY_NAME = "type";
    private static final int TYPE_SWIPE = 0;
    private static final int TYPE_TOUCH = 1;
    private static final int TYPE_DPAD = 2;
    private static final int TYPE_HALF_DPAD = 3;

    private static Preferences _inputPrefs;

    private static int _inputType;
}
