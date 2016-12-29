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
		gameHelper.setup(new GameHelperListener());

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
		try {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(MARKET + getContext().getPackageName())));
		}
		catch (Exception e) {
			Gdx.app.log("MainActivity", "Market intent failed: " + e.getMessage() + ".");
		}
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
			startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), REQUEST_ACHIEVEMENTS);
		}
		else
			signIn();
	}

	@Override
	public void showScores() {
		if (isSignedIn()) {
			offlineData.sendOnline(gameHelper.getApiClient(), this);
			startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(gameHelper.getApiClient()), REQUEST_SCORES);
		}
		else
			signIn();
	}

	@Override
	public void shareApp() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SEND);
		intent.setType(SHARE_TYPE);
		intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
		intent.putExtra(Intent.EXTRA_TEXT, SHARE_EXTRA + LINK);
		startActivity(Intent.createChooser(intent, SHARE_WITH));
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

	private static final int REQUEST_SCORES = 1;
	private static final int REQUEST_ACHIEVEMENTS = 2;
	private static final String SHARE_WITH = "Share with";
	private static final String SHARE_EXTRA = "Try this Retro Snake game, it's very fun :\n\n";
	private static final String SHARE_TYPE = "text/plain";
	private static final String MARKET = "market://details?id=";
	private static final String LINK = "https://play.google.com/store/apps/details?id=com.juchap.snake";

	private OfflineData offlineData;
	private GameHelper gameHelper;
}
