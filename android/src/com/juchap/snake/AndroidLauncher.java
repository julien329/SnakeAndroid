package com.juchap.snake;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.juchap.snake.Services.PlayServices;


public class AndroidLauncher extends AndroidApplication implements PlayServices {

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		offlineData = new OfflineData(getContext(), BuildConfig.APPLICATION_ID, Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID));

		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(true);
		GameHelperListener gameHelperListener = new GameHelperListener();
		gameHelper.setup(gameHelperListener);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useCompass = false;
		config.useAccelerometer = false;
		config.useWakelock = true;
		initialize(new MySnakeGame(this), config);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		gameHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void signIn() {
		if(isNetworkAvailable()) {
			try {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						gameHelper.beginUserInitiatedSignIn();
					}
				});
			} catch (Exception e) {
				Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
			}
		}
	}

	@Override
	public void signOut() {
		try {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					gameHelper.signOut();
				}
			});
		}
		catch (Exception e) {
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void rateGame() {
		String str = "market://details?id=" + getContext().getPackageName();
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
	}

	@Override
	public void unlockAchievement(String achievement) {
		offlineData.saveAchievementUnlock(achievement);
		if(isSignedIn())
			offlineData.sendOnline(gameHelper.getApiClient(), this);
	}

	@Override
	public void incrementAchievement(String achievement, int amount) {
		offlineData.saveAchievementIncrement(achievement, amount);
		if(isSignedIn())
			offlineData.sendOnline(gameHelper.getApiClient(), this);
	}

	@Override
	public void submitScore(String leaderboard, int score) {
		offlineData.saveScore(leaderboard, score);
		if (isSignedIn())
			offlineData.sendOnline(gameHelper.getApiClient(), this);
	}

	@Override
	public void showAchievements() {
		if (isSignedIn()) {
			offlineData.sendOnline(gameHelper.getApiClient(), this);
			startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), requestCode);
		}
		else
			signIn();
	}

	@Override
	public void showScores() {
		if (isSignedIn()) {
			offlineData.sendOnline(gameHelper.getApiClient(), this);
			startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(gameHelper.getApiClient()), requestCode);
		}
		else
			signIn();
	}

	@Override
	public boolean isSignedIn() {
		return gameHelper.isSignedIn();
	}

	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}


	////////////////////////////////////////////////////////////////////////////////////////////////
	/// CLASSES
	////////////////////////////////////////////////////////////////////////////////////////////////

	private class GameHelperListener implements GameHelper.GameHelperListener {
		@Override
		public void onSignInFailed(){ }

		@Override
		public void onSignInSucceeded(){ }
	};


	////////////////////////////////////////////////////////////////////////////////////////////////
	/// VARIABLES
	////////////////////////////////////////////////////////////////////////////////////////////////

	private OfflineData offlineData;
	private GameHelper gameHelper;
	private final static int requestCode = 1;
}
