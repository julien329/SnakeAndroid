package com.juchap.snake;

import android.app.Activity;
import android.content.SharedPreferences;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.juchap.snake.Encrypting.AESObfuscator;
import com.juchap.snake.Encrypting.PreferenceObfuscator;
import android.content.Context;


public class OfflineData {

    public OfflineData(Context context, String appId, String deviceId) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        AESObfuscator obfuscator = new AESObfuscator(RANDOM_BYTES, appId, deviceId);
        encriptedPrefs = new PreferenceObfuscator(prefs, obfuscator);
    }

    public void saveScore(String leaderboard, int score) {
        int localScore = Integer.parseInt(encriptedPrefs.getString(leaderboard, DEFAULT_INT));
        if(score > localScore) {
            encriptedPrefs.putString(leaderboard, String.valueOf(score));
            encriptedPrefs.commit();
        }
    }

    public void saveAchievementUnlock(String achievement) {
        encriptedPrefs.putString(achievement, TRUE);
        encriptedPrefs.commit();
    }

    public void saveAchievementIncrement(String achievement, int value) {
        String localData = encriptedPrefs.getString(achievement, DEFAULT_INT);
        int progress = (localData.equals(DEFAULT_INT)) ? DEFAULT_VALUE : Integer.parseInt(localData);
        progress += value;

        encriptedPrefs.putString(achievement, String.valueOf(progress));
        encriptedPrefs.commit();
    }

    public void sendOnline(GoogleApiClient googleApiClient, Activity activity) {
        // Send scores online
        sendScoreOnline(googleApiClient, activity.getString(R.string.leaderboard_easy));
        sendScoreOnline(googleApiClient, activity.getString(R.string.leaderboard_medium));
        sendScoreOnline(googleApiClient, activity.getString(R.string.leaderboard_hard));

        // Send achievement unlocks online
        sendAchievementUnlockOnline(googleApiClient, activity.getString(R.string.achievement_scaredy_cat));
        sendAchievementUnlockOnline(googleApiClient, activity.getString(R.string.achievement_adventurous));
        sendAchievementUnlockOnline(googleApiClient, activity.getString(R.string.achievement_presumptuous));
        sendAchievementUnlockOnline(googleApiClient, activity.getString(R.string.achievement_premature));
        sendAchievementUnlockOnline(googleApiClient, activity.getString(R.string.achievement_worm));
        sendAchievementUnlockOnline(googleApiClient, activity.getString(R.string.achievement_boa));
        sendAchievementUnlockOnline(googleApiClient, activity.getString(R.string.achievement_python));
        sendAchievementUnlockOnline(googleApiClient, activity.getString(R.string.achievement_anaconda));

        // Send achivement progress online
        sendAchievementIncrementOnline(googleApiClient, activity.getString(R.string.achievement_bite));
        sendAchievementIncrementOnline(googleApiClient, activity.getString(R.string.achievement_snack));
        sendAchievementIncrementOnline(googleApiClient, activity.getString(R.string.achievement_meal));
        sendAchievementIncrementOnline(googleApiClient, activity.getString(R.string.achievement_feast));
        sendAchievementIncrementOnline(googleApiClient, activity.getString(R.string.achievement_gluttony));
        sendAchievementIncrementOnline(googleApiClient, activity.getString(R.string.achievement_slow_down));
        sendAchievementIncrementOnline(googleApiClient, activity.getString(R.string.achievement_looking_good));
        sendAchievementIncrementOnline(googleApiClient, activity.getString(R.string.achievement_my_man));

        encriptedPrefs.commit();
    }

    private void sendScoreOnline(GoogleApiClient googleApiClient, String leaderboard) {
        String score = encriptedPrefs.getString(leaderboard, DEFAULT_INT);
        if(!score.equals(DEFAULT_INT)) {
            Games.Leaderboards.submitScore(googleApiClient, leaderboard, Integer.valueOf(score));
            encriptedPrefs.putString(leaderboard, DEFAULT_INT);
        }
    }

    private void sendAchievementUnlockOnline(GoogleApiClient googleApiClient, String achievement) {
        String isUnlocked = encriptedPrefs.getString(achievement, DEFAULT_BOOL);
        if(isUnlocked.equals(TRUE)) {
            Games.Achievements.unlock(googleApiClient, achievement);
            encriptedPrefs.putString(achievement, DEFAULT_BOOL);
        }
    }

    private void sendAchievementIncrementOnline(GoogleApiClient googleApiClient, String achievement) {
        String progress = encriptedPrefs.getString(achievement, DEFAULT_INT);
        if(!progress.equals(DEFAULT_INT)) {
            Games.Achievements.increment(googleApiClient, achievement, Integer.valueOf(progress));
            encriptedPrefs.putString(achievement, DEFAULT_INT);
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final byte[] RANDOM_BYTES = { 16, 74, 71, -80, 32, 101, -47, 72, 117, -14, 0, -29, 70, 65, -12, 74 };
    private static final String SHARED_PREFS = "OfflineData";
    private static final String DEFAULT_INT = "-1";
    private static final String DEFAULT_BOOL = "false";
    private static final String TRUE = "true";;
    private static final int DEFAULT_VALUE = 0;

    private PreferenceObfuscator encriptedPrefs;
}
