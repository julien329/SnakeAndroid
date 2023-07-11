package com.juchap.snake;

import android.content.SharedPreferences;
import android.content.Context;

import com.google.android.gms.games.PlayGames;
import com.juchap.snake.Encrypting.AESObfuscator;
import com.juchap.snake.Encrypting.PreferenceObfuscator;

public class OfflineData {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public OfflineData(Context context, String appId, String deviceId) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        AESObfuscator obfuscator = new AESObfuscator(RANDOM_BYTES, appId, deviceId);
        _encryptedPrefs = new PreferenceObfuscator(prefs, obfuscator);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void saveScore(String leaderboard, int score) {
        int localScore = Integer.parseInt(_encryptedPrefs.getString(leaderboard, DEFAULT_INT));

        if (score > localScore) {
            _encryptedPrefs.putString(leaderboard, String.valueOf(score));
            _encryptedPrefs.commit();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void saveAchievementUnlock(String achievement) {
        _encryptedPrefs.putString(achievement, TRUE);
        _encryptedPrefs.commit();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void saveAchievementIncrement(String achievement, int value) {
        String localData = _encryptedPrefs.getString(achievement, DEFAULT_INT);
        int progress = (localData.equals(DEFAULT_INT)) ? DEFAULT_VALUE : Integer.parseInt(localData);
        progress += value;

        _encryptedPrefs.putString(achievement, String.valueOf(progress));
        _encryptedPrefs.commit();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void sendOnline(AndroidLauncher playGames) {
        if (!playGames.isSignedIn()) {
            // Not logged in
            return;
        }

        // Send scores online
        sendScoreOnline(playGames, playGames.getString(R.string.leaderboard_easy));
        sendScoreOnline(playGames, playGames.getString(R.string.leaderboard_medium));
        sendScoreOnline(playGames, playGames.getString(R.string.leaderboard_hard));

        // Send achievement unlocks online
        sendAchievementUnlockOnline(playGames, playGames.getString(R.string.achievement_scaredy_cat));
        sendAchievementUnlockOnline(playGames, playGames.getString(R.string.achievement_adventurous));
        sendAchievementUnlockOnline(playGames, playGames.getString(R.string.achievement_presumptuous));
        sendAchievementUnlockOnline(playGames, playGames.getString(R.string.achievement_premature));
        sendAchievementUnlockOnline(playGames, playGames.getString(R.string.achievement_worm));
        sendAchievementUnlockOnline(playGames, playGames.getString(R.string.achievement_boa));
        sendAchievementUnlockOnline(playGames, playGames.getString(R.string.achievement_python));
        sendAchievementUnlockOnline(playGames, playGames.getString(R.string.achievement_anaconda));
        sendAchievementUnlockOnline(playGames, playGames.getString(R.string.achievement_i_surrender));

        // Send achievement progress online
        sendAchievementIncrementOnline(playGames, playGames.getString(R.string.achievement_bite));
        sendAchievementIncrementOnline(playGames, playGames.getString(R.string.achievement_snack));
        sendAchievementIncrementOnline(playGames, playGames.getString(R.string.achievement_meal));
        sendAchievementIncrementOnline(playGames, playGames.getString(R.string.achievement_feast));
        sendAchievementIncrementOnline(playGames, playGames.getString(R.string.achievement_gluttony));
        sendAchievementIncrementOnline(playGames, playGames.getString(R.string.achievement_slow_down));
        sendAchievementIncrementOnline(playGames, playGames.getString(R.string.achievement_looking_good));
        sendAchievementIncrementOnline(playGames, playGames.getString(R.string.achievement_my_man));

        _encryptedPrefs.commit();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void sendScoreOnline(AndroidLauncher playGames, String leaderboard) {
        String score = _encryptedPrefs.getString(leaderboard, DEFAULT_INT);

        if (!score.equals(DEFAULT_INT)) {
            PlayGames.getLeaderboardsClient(playGames).submitScore(leaderboard, Integer.parseInt(score));
            _encryptedPrefs.putString(leaderboard, DEFAULT_INT);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void sendAchievementUnlockOnline(AndroidLauncher playGames, String achievement) {
        String isUnlocked = _encryptedPrefs.getString(achievement, DEFAULT_BOOL);

        if (isUnlocked.equals(TRUE)) {
            PlayGames.getAchievementsClient(playGames).unlock(achievement);
            _encryptedPrefs.putString(achievement, DEFAULT_BOOL);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void sendAchievementIncrementOnline(AndroidLauncher playGames, String achievement) {
        String progress = _encryptedPrefs.getString(achievement, DEFAULT_INT);

        if (!progress.equals(DEFAULT_INT)) {
            PlayGames.getAchievementsClient(playGames).increment(achievement, Integer.parseInt(progress));
            _encryptedPrefs.putString(achievement, DEFAULT_INT);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final byte[] RANDOM_BYTES = { 16, 74, 71, -80, 32, 101, -47, 72, 117, -14, 0, -29, 70, 65, -12, 74 };
    private static final String SHARED_PREFS = "OfflineData";
    private static final String DEFAULT_INT = "-1";
    private static final String DEFAULT_BOOL = "false";
    private static final String TRUE = "true";
    private static final int DEFAULT_VALUE = 0;

    private final PreferenceObfuscator _encryptedPrefs;
}
