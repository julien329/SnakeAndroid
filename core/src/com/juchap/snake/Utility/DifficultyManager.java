package com.juchap.snake.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class DifficultyManager {

    public static void initManager() {
        difficultyPrefs = Gdx.app.getPreferences(PREFS_NAME);

        if(difficultyPrefs.contains(KEY_NAME))
            loadFromPrefs();
        else {
            difficultyLevel = EASY;
            saveToPrefs();
        }
    }

    private static void loadFromPrefs() {
        difficultyLevel = difficultyPrefs.getInteger(KEY_NAME);
    }

    private static void saveToPrefs() {
        difficultyPrefs.putInteger(KEY_NAME, difficultyLevel);
        difficultyPrefs.flush();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static int getDifficulty() { return difficultyLevel; }

    public static float getInterval() {
        float interval = -1;

        if(difficultyLevel == EASY)
            interval = INTERVAL_EASY;
        else if(difficultyLevel == MEDIUM)
            interval = INTERVAL_MEDIUM;
        else if(difficultyLevel == HARD)
            interval = INTERVAL_HARD;

        return interval;
    }

    public static String getLeaderboard() {
        String leaderboard = null;

        if(difficultyLevel == EASY)
            leaderboard = StringManager.LEADERBOARD_EASY;
        else if(difficultyLevel == MEDIUM)
            leaderboard = StringManager.LEADERBOARD_MEDIUM;
        else if(difficultyLevel == HARD)
            leaderboard = StringManager.LEADERBOARD_HARD;

        return leaderboard;
    }

    public static void setDifficulty(int level) {
        difficultyLevel = level;
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

    private static Preferences difficultyPrefs;

    private static int difficultyLevel;
}
