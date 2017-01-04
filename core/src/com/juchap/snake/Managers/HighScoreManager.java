package com.juchap.snake.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import java.util.HashMap;


public class HighScoreManager {

    public static void initManager() {
        boolean exist = true;
        scorePrefs = Gdx.app.getPreferences(PREFS_NAME);

        highScores = new HashMap<String, Integer>();
        for (String difficulty : DIFFICULTY_LEVELS) {
            for (int i = 0; i < TABLE_SIZE; i++) {
                String keyVal = difficulty + String.valueOf(i);
                highScores.put(keyVal, 0);
                if (!scorePrefs.contains(keyVal))
                    exist = false;
            }
        }

        if(exist)
            loadFromPrefs();
        else
            saveToPrefs();
    }

    private static void loadFromPrefs() {
        highScores.clear();
        for (String difficulty : DIFFICULTY_LEVELS) {
            for (int i = 0; i < TABLE_SIZE; i++) {
                String keyVal = difficulty + String.valueOf(i);
                highScores.put(keyVal, scorePrefs.getInteger(keyVal));
            }
        }
    }

    private static void saveToPrefs() {
        for (String difficulty : DIFFICULTY_LEVELS) {
            for (int i = 0; i < TABLE_SIZE; i++) {
                String keyVal = difficulty + String.valueOf(i);
                scorePrefs.putInteger(keyVal, highScores.get(keyVal));
            }
        }
        scorePrefs.flush();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static int getScore(int rank, int difficulty) {
        return highScores.get(DIFFICULTY_LEVELS[difficulty] + String.valueOf(rank));
    }

    public static void addScore(int newScore, int difficulty) {
        for (int i = 0; i < TABLE_SIZE; i++) {
            String keyVal = DIFFICULTY_LEVELS[difficulty] + String.valueOf(i);
            if(highScores.get(keyVal) < newScore) {
                for (int j = TABLE_SIZE - 1; j > i; j--) {
                    highScores.put(DIFFICULTY_LEVELS[difficulty] + String.valueOf(j), highScores.get(DIFFICULTY_LEVELS[difficulty] + String.valueOf(j - 1)));
                }
                highScores.put(keyVal, newScore);
                break;
            }
        }
        saveToPrefs();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final int TABLE_SIZE = 9;
    private static final String PREFS_NAME = "HighScore";
    private static final String[] DIFFICULTY_LEVELS = {"E", "M", "H"};

    private static HashMap<String, Integer> highScores;
    private static Preferences scorePrefs;
}
