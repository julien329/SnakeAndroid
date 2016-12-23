package com.juchap.snake.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;


public class SoundManager {

    public static void initManager() {
        eatSound = Gdx.audio.newSound(Gdx.files.internal(EAT_PATH));
        activateSound = Gdx.audio.newSound(Gdx.files.internal(ACTIVATE_PATH));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal(GAME_OVER_PATH));

        soundPrefs = Gdx.app.getPreferences(PREFS_NAME);

        if(soundPrefs.contains(KEY_NAME))
            loadFromPrefs();
        else {
            soundEnabled = true;
            saveToPrefs();
        }
    }

    private static void loadFromPrefs() {
        soundEnabled = soundPrefs.getBoolean(KEY_NAME);
    }

    private static void saveToPrefs() {
        soundPrefs.putBoolean(KEY_NAME, soundEnabled);
        soundPrefs.flush();
    }

    public static void playEat() {
        playSound(eatSound);
    }

    public static void playActivate() {
        playSound(activateSound);
    }

    public static void playGameOver() {
        playSound(gameOverSound);
    }

    private static void playSound(Sound sound) {
        if(soundEnabled)
            sound.play();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void setState(boolean isEnabled) {
        soundEnabled = isEnabled;
        saveToPrefs();
    }

    public static boolean getState() { return soundEnabled; }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String PREFS_NAME = "Sound";
    private static final String KEY_NAME = "isEnabled";
    private static final String EAT_PATH = "Sounds/Eat.wav";
    private static final String ACTIVATE_PATH = "Sounds/ActivateSounds.wav";
    private static final String GAME_OVER_PATH = "Sounds/GameOver.wav";

    private static Preferences soundPrefs;
    private static Sound eatSound;
    private static Sound activateSound;
    private static Sound gameOverSound;

    private static boolean soundEnabled;
}
