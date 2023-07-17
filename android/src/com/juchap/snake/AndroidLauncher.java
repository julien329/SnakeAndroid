package com.juchap.snake;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.AuthenticationResult;
import com.google.android.gms.games.GamesSignInClient;
import com.google.android.gms.games.PlayGames;
import com.google.android.gms.games.PlayGamesSdk;
import com.google.android.gms.tasks.Task;
import com.juchap.snake.Services.PlayServices;

public class AndroidLauncher extends AndroidApplication implements PlayServices {

	////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		_offlineData = new OfflineData(getContext(), BuildConfig.APPLICATION_ID, Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID));

		PlayGamesSdk.initialize(this);

		GamesSignInClient gamesSignInClient = PlayGames.getGamesSignInClient(this);
		gamesSignInClient.isAuthenticated().addOnCompleteListener(this::validateSignIn);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useImmersiveMode = true;
		config.useCompass = false;
		config.useAccelerometer = false;

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		initialize(new MySnakeGame(this), config);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	public void validateSignIn(Task<AuthenticationResult> authenticationTask) {
		_isSignedIn = (authenticationTask.isSuccessful() && authenticationTask.getResult().isAuthenticated());

		if (_isSignedIn) {
			_offlineData.sendOnline(this);
		}

		Gdx.app.log("MainActivity", "Log in status:" + _isSignedIn);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	public void signIn() {
		GamesSignInClient gamesSignInClient = PlayGames.getGamesSignInClient(this);
		gamesSignInClient.signIn().addOnCompleteListener(this::validateSignIn);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void rateGame() {
		try {
			Intent rateIntent = new Intent(Intent.ACTION_VIEW);
			rateIntent.setData(Uri.parse(STORE_URL + getPackageName()));
			rateIntent.setPackage("com.android.vending");

			startActivity(rateIntent);
		}
		// Open from browser if google play store not found
		catch (ActivityNotFoundException e) {
			Intent rateIntent = new Intent(Intent.ACTION_VIEW);
			rateIntent.setData(Uri.parse(STORE_URL + getPackageName()));

			startActivity(rateIntent);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void unlockAchievement(String achievement) {
		_offlineData.saveAchievementUnlock(achievement);
		_offlineData.sendOnline(this);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void incrementAchievement(String achievement, int amount) {
		_offlineData.saveAchievementIncrement(achievement, amount);
		_offlineData.sendOnline(this);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void submitScore(String leaderboard, int score) {
		_offlineData.saveScore(leaderboard, score);
		_offlineData.sendOnline(this);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void showAchievements() {
		if (isSignedIn()) {
			PlayGames.getAchievementsClient(this).getAchievementsIntent().addOnSuccessListener(intent -> {
				startActivityForResult(intent, RC_ACHIEVEMENT_UI);
			});
		}
		else {
			signIn();
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void showScores() {
		if (isSignedIn()) {
			PlayGames.getLeaderboardsClient(this).getAllLeaderboardsIntent().addOnSuccessListener(intent -> {
				startActivityForResult(intent, RC_LEADERBOARD_UI);
			});
		}
		else {
			signIn();
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected void onResume () {
		super.onResume();

		// Fix black screen when coming back to app (with non continuous rendering)
		Gdx.graphics.requestRendering();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void shareApp() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType(SHARE_TYPE);
		intent.putExtra(Intent.EXTRA_TEXT, SHARE_EXTRA + STORE_URL + getPackageName());

		Intent shareIntent = Intent.createChooser(intent, SHARE_WITH);
		startActivity(shareIntent);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	/// GET / SET
	////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public boolean isSignedIn() { return _isSignedIn; }

	////////////////////////////////////////////////////////////////////////////////////////////////
	/// VARIABLES
	////////////////////////////////////////////////////////////////////////////////////////////////

	private static final int RC_ACHIEVEMENT_UI = 9003;
	private static final int RC_LEADERBOARD_UI = 9004;
	private static final String SHARE_WITH = "Share with";
	private static final String SHARE_EXTRA = "Hey! Try this Retro Snake game, it's just like the good old classic :\n\n";
	private static final String SHARE_TYPE = "text/plain";
	private static final String STORE_URL = "https://play.google.com/store/apps/details?id=";

	private OfflineData _offlineData;
	private boolean _isSignedIn = false;
}
