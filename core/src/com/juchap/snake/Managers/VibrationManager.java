package com.juchap.snake.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class VibrationManager {

    public static void initManager() {
        vibrationPrefs = Gdx.app.getPreferences(PREFS_NAME);

        if(vibrationPrefs.contains(KEY_NAME))
            loadFromPrefs();
        else {
            vibrationEnabled = true;
            saveToPrefs();
        }
    }

    private static void loadFromPrefs() {
        vibrationEnabled = vibrationPrefs.getBoolean(KEY_NAME);
    }

    private static void saveToPrefs() {
        vibrationPrefs.putBoolean(KEY_NAME, vibrationEnabled);
        vibrationPrefs.flush();
    }

    public static void vibrateShort() {
        vibrate(SHORT);
    }

    public static void vibrateMedium() {
        vibrate(MEDIUM);
    }

    public static void vibrateLong() {
        vibrate(LONG);
    }

    private static void vibrate(int duration) {
        if(vibrationEnabled)
            Gdx.input.vibrate(duration);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void setState(boolean isEnabled) {
        vibrationEnabled = isEnabled;
        saveToPrefs();
    }

    public static boolean getState() { return vibrationEnabled; }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String PREFS_NAME = "Vibration";
    private static final String KEY_NAME = "isEnabled";
    private static final int SHORT = 128;
    private static final int MEDIUM = 258;
    public static final int LONG = 512;

    private static Preferences vibrationPrefs;

    private static boolean vibrationEnabled;
}
