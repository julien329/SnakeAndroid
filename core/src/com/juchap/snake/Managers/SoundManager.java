package com.juchap.snake.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static void initManager() {
        _eatSound = Gdx.audio.newSound(Gdx.files.internal(EAT_PATH));
        _activateSound = Gdx.audio.newSound(Gdx.files.internal(ACTIVATE_PATH));
        _gameOverSound = Gdx.audio.newSound(Gdx.files.internal(GAME_OVER_PATH));

        _soundPrefs = Gdx.app.getPreferences(PREFS_NAME);

        if (_soundPrefs.contains(KEY_NAME)) {
            loadFromPrefs();
        }
        else {
            _soundEnabled = true;
            saveToPrefs();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static void loadFromPrefs() {
        _soundEnabled = _soundPrefs.getBoolean(KEY_NAME);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static void saveToPrefs() {
        _soundPrefs.putBoolean(KEY_NAME, _soundEnabled);
        _soundPrefs.flush();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static void playEat() {
        playSound(_eatSound);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static void playActivate() {
        playSound(_activateSound);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static void playGameOver() {
        playSound(_gameOverSound);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static void playSound(Sound sound) {
        if (!_soundEnabled) {
            // Muted
            return;
        }

        sound.play();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static void setState(boolean isEnabled) {
        _soundEnabled = isEnabled;
        saveToPrefs();
    }

    public static boolean getState() { return _soundEnabled; }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String PREFS_NAME = "Sound";
    private static final String KEY_NAME = "isEnabled";
    private static final String EAT_PATH = "Sounds/Eat.wav";
    private static final String ACTIVATE_PATH = "Sounds/ActivateSounds.wav";
    private static final String GAME_OVER_PATH = "Sounds/GameOver.wav";

    private static Preferences _soundPrefs;
    private static Sound _eatSound;
    private static Sound _activateSound;
    private static Sound _gameOverSound;

    private static boolean _soundEnabled;
}
