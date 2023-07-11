package com.juchap.snake.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import java.util.HashMap;

public class HighScoreManager {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static void initManager() {
        _scorePrefs = Gdx.app.getPreferences(PREFS_NAME);
        _highScores = new HashMap<>();

        if (_scorePrefs.get().isEmpty()) {
            // Init table with zeros
            for (String difficulty : DIFFICULTY_LEVELS) {
                for (int i = 0; i < TABLE_SIZE; i++) {
                    final String keyVal = difficulty + i;
                    _highScores.put(keyVal, 0);
                }
            }

            saveToPrefs();
        }
        else {
            loadFromPrefs();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static void loadFromPrefs() {
        _highScores.clear();
        for (String difficulty : DIFFICULTY_LEVELS) {
            for (int i = 0; i < TABLE_SIZE; i++) {
                String keyVal = difficulty + i;
                _highScores.put(keyVal, _scorePrefs.getInteger(keyVal));
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static void saveToPrefs() {
        for (String difficulty : DIFFICULTY_LEVELS) {
            for (int i = 0; i < TABLE_SIZE; i++) {
                String keyVal = difficulty + i;
                _scorePrefs.putInteger(keyVal, _highScores.get(keyVal));
            }
        }
        _scorePrefs.flush();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static int getScore(int rank, int difficulty) {
        return _highScores.get(DIFFICULTY_LEVELS[difficulty] + rank);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static void addScore(int newScore, int difficulty) {
        for (int i = 0; i < TABLE_SIZE; i++) {
            String keyVal = DIFFICULTY_LEVELS[difficulty] + i;
            if (_highScores.get(keyVal) < newScore) {
                for (int j = TABLE_SIZE - 1; j > i; j--) {
                    _highScores.put(DIFFICULTY_LEVELS[difficulty] + j, _highScores.get(DIFFICULTY_LEVELS[difficulty] + (j - 1)));
                }
                _highScores.put(keyVal, newScore);
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

    private static HashMap<String, Integer> _highScores;
    private static Preferences _scorePrefs;
}
