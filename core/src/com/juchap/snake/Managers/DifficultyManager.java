package com.juchap.snake.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.juchap.snake.Utility.GlobalStrings;

public class DifficultyManager {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static void initManager() {
        _difficultyPrefs = Gdx.app.getPreferences(PREFS_NAME);

        if (_difficultyPrefs.contains(KEY_NAME)) {
            loadFromPrefs();
        }
        else {
            _difficultyLevel = EASY;
            saveToPrefs();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static void loadFromPrefs() {
        _difficultyLevel = _difficultyPrefs.getInteger(KEY_NAME);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static void saveToPrefs() {
        _difficultyPrefs.putInteger(KEY_NAME, _difficultyLevel);
        _difficultyPrefs.flush();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static int getDifficulty() { return _difficultyLevel; }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static float getInterval() {
        if (_difficultyLevel == EASY) {
            return INTERVAL_EASY;
        }
        else if (_difficultyLevel == MEDIUM) {
            return INTERVAL_MEDIUM;
        }
        else if (_difficultyLevel == HARD) {
            return INTERVAL_HARD;
        }

        return -1;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static String getLeaderboard() {
        if (_difficultyLevel == EASY) {
            return GlobalStrings.LEADERBOARD_EASY;
        }
        else if (_difficultyLevel == MEDIUM) {
            return GlobalStrings.LEADERBOARD_MEDIUM;
        }
        else if(_difficultyLevel == HARD) {
            return GlobalStrings.LEADERBOARD_HARD;
        }

        return null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static void setDifficulty(int level) {
        _difficultyLevel = level;
        saveToPrefs();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String KEY_NAME = "difficultyLevel";
    private static final String PREFS_NAME = "Difficulty";
    public static final int EASY = 0;
    public static final int MEDIUM = 1;
    public static final int HARD = 2;
    private static final float INTERVAL_EASY = 0.125f;
    private static final float INTERVAL_MEDIUM = 0.1f;
    private static final float INTERVAL_HARD = 0.075f;

    private static Preferences _difficultyPrefs;

    private static int _difficultyLevel;
}
