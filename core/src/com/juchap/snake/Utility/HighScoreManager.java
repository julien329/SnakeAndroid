package com.juchap.snake.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import java.util.ArrayList;


public class HighScoreManager {

    public static void initHighScore() {
        boolean exist = true;
        scorePrefs = Gdx.app.getPreferences(PREFS_NAME);

        highScores = new ArrayList<Integer>();
        for (int i = 0; i < TABLE_SIZE; i++) {
            highScores.add(0);
            if(!scorePrefs.contains(String.valueOf(i)))
                exist = false;
        }

        if(exist)
            loadFromPrefs();
        else
            saveToPrefs();
    }

    private static void loadFromPrefs() {
        highScores.clear();
        for (int i = 0; i < TABLE_SIZE; i++) {
            highScores.add(i, scorePrefs.getInteger(String.valueOf(i)));
        }
    }

    private static void saveToPrefs() {
        for (int i = 0; i < TABLE_SIZE; i++) {
            scorePrefs.putInteger(String.valueOf(i), highScores.get(i));
        }
        scorePrefs.flush();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static int getBest() {
        return highScores.get(0);
    }

    public static void addScore(int newScore) {
        for (int i = 0; i < TABLE_SIZE; i++) {
            if(highScores.get(i) < newScore) {
                highScores.add(i, newScore);
                highScores.remove(highScores.size() - 1);
            }
        }
        saveToPrefs();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static ArrayList<Integer> highScores;
    private static Preferences scorePrefs;

    private static final int TABLE_SIZE = 10;
    private static final String PREFS_NAME = "HighScore";
}
