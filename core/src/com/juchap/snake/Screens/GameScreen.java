package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.juchap.snake.GameScene.Food;
import com.juchap.snake.GameScene.GameUI;
import com.juchap.snake.GameScene.Snake;
import com.juchap.snake.Managers.DifficultyManager;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Managers.InputManager;
import com.juchap.snake.Utility.ScreenEnum;
import com.juchap.snake.Utility.ScreenManager;
import com.juchap.snake.Managers.SoundManager;
import com.juchap.snake.Utility.GlobalStrings;
import com.juchap.snake.Managers.VibrationManager;
import java.util.ArrayList;


public class GameScreen extends AbstractScreen {

	public GameScreen() {
		super();

		pausedInputs = new InputPaused();
		inputMultiplexer = new InputMultiplexer(this, (InputManager.isSwipe()) ? new GestureDetector(new InputSwipe()) : new InputTouch());
		Gdx.input.setInputProcessor(inputMultiplexer);

		isPaused = false;

		freeSpaces = new ArrayList<Vector2>();
		for(int i = GlobalVars.GRID_OFFSET_X + GlobalVars.UNIT_SIZE ; i < GlobalVars.GRID_OFFSET_X + GlobalVars.GRID_WIDTH - GlobalVars.UNIT_SIZE; i += GlobalVars.UNIT_SIZE) {
			for(int j = GlobalVars.GRID_OFFSET_Y + GlobalVars.UNIT_SIZE ; j < GlobalVars.GAME_GRID_HEIGHT - GlobalVars.UNIT_SIZE; j += GlobalVars.UNIT_SIZE) {
				freeSpaces.add(new Vector2(i, j));
			}
		}
	}

	@Override
	public void buildStage() {
		gameUI = new GameUI(uiRenderer, spriteBatch);

		int centerX = (int)(Math.floor((Gdx.graphics.getWidth() / GlobalVars.UNIT_SIZE) * (1.0/2.0)) * GlobalVars.UNIT_SIZE) + GlobalVars.GRID_OFFSET_X;
		int centerY = (int)(Math.floor((Gdx.graphics.getHeight() / GlobalVars.UNIT_SIZE) * (3.0/7.0)) * GlobalVars.UNIT_SIZE) + GlobalVars.GRID_OFFSET_Y;
		freeSpaces.remove(new Vector2(centerX, centerY));
		snake = new Snake(centerX, centerY);

		food = new Food();
		food.spawnFood();

		float updatePosInterval = DifficultyManager.getInterval();
		Timer.schedule(new MoveSnake(), updatePosInterval, updatePosInterval);
		Timer.instance().start();
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		gameUI.render();
		snake.render();
		food.render();

		if(isPaused)
			gameUI.renderPause();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public boolean keyUp(int keycode) {
		if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK) ) {
			pauseGame();
			return true;
		}
		return false;
	}

	@Override
	public void pause() {
		pauseGame();
	}

	private void gameOver() {
		Timer.instance().clear();
		VibrationManager.vibrateLong();
		SoundManager.playGameOver();

		new Thread(new Runnable() {
			@Override
			public void run() {
				long time = System.currentTimeMillis();
				while (System.currentTimeMillis() < time + VibrationManager.LONG){}
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						int score = (snake.getBodyParts().size() - 1 == gameUI.getScore()) ? gameUI.getScore() : 0;
						ScreenManager.getInstance().showScreen(ScreenEnum.GAME_OVER, score);
					}
				});
			}
		}).start();
	}

	public void	pauseGame() {
		Timer.instance().stop();
		isPaused = true;
		Gdx.input.setInputProcessor(pausedInputs);
	}


	////////////////////////////////////////////////////////////////////////////////////////////////
	/// CLASSES
	////////////////////////////////////////////////////////////////////////////////////////////////

	private class InputTouch extends InputAdapter {
		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			int distX = Gdx.input.getX() - snake.getHeadPosX();
			int distY = Gdx.graphics.getHeight() - Gdx.input.getY() - snake.getHeadPosY();

			if (snake.getDirX() == 0 && snake.getDirY() != 0)
				snake.setDir((int)Math.signum(distX), 0);
			else if (snake.getDirY() == 0 && snake.getDirX() != 0)
				snake.setDir(0, (int)Math.signum(distY));
			else {
				if (Math.abs(distX) >= Math.abs(distY)) {
					snake.setDir((int)Math.signum(distX), 0);
				} else {
					snake.setDir(0, (int)Math.signum(distY));
				}
			}
			return true;
		}
	}

	private class InputSwipe extends GestureDetector.GestureAdapter {
		@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
			deltaX = deltaY = 0;
			moved = false;
			return true;
		}
		@Override
		public boolean pan(float x, float y, float deltaX, float deltaY) {
			if(!moved) {
				this.deltaX += deltaX;
				this.deltaY += deltaY;

				if (Math.abs(this.deltaX) >= distToTravel && snake.getDirX() == 0) {
					snake.setDir((int)Math.signum(this.deltaX), 0);
					moved = true;
					return true;
				} else if (Math.abs(this.deltaY) >= distToTravel && snake.getDirY() == 0) {
					snake.setDir(0, (int)Math.signum(-this.deltaY));
					moved = true;
					return true;
				}
			}
			return false;
		}

		private float deltaX = 0;
		private float deltaY = 0;
		private boolean moved = false;
		private final float distToTravel = Gdx.graphics.getWidth() / 32.0f;
	}

	private class InputPaused extends InputAdapter {
		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			Gdx.input.setInputProcessor(inputMultiplexer);
			isPaused = false;
			Timer.instance().start();
			return true;
		}
		@Override
		public boolean keyUp(int keycode) {
			if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK) ) {
				Timer.instance().clear();
				ScreenManager.getInstance().unlockAchievement(GlobalStrings.ACHIEVEMENT_I_SURRENDER);
				ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
				return true;
			}
			return false;
		}
	}

	private class MoveSnake extends Timer.Task {
		@Override
		public void run() {
			snake.move();
			freeSpaces.remove(new Vector2(snake.getHeadPosX(), snake.getHeadPosY()));
			if (!snake.tryEat() && !(snake.getDirX() == 0 && snake.getDirY() == 0))
				freeSpaces.add(new Vector2(snake.getLastPosX(), snake.getLastPosY()));

			Gdx.graphics.requestRendering();

			if (snake.checkCollisions())
				gameOver();
		}
	}


	////////////////////////////////////////////////////////////////////////////////////////////////
	/// GET / SET
	////////////////////////////////////////////////////////////////////////////////////////////////

	public Snake getSnake() { return snake; }
	public GameUI getGameUI() { return gameUI; }
	public Food getFood() { return food; }
	public ArrayList<Vector2> getFreeSpaces() { return freeSpaces; }


	////////////////////////////////////////////////////////////////////////////////////////////////
	/// VARIABLES
	////////////////////////////////////////////////////////////////////////////////////////////////

	private Snake snake;
	private GameUI gameUI;
	private Food food;
	private InputMultiplexer inputMultiplexer;
	private InputPaused pausedInputs;
	private ArrayList<Vector2> freeSpaces;

	private boolean isPaused;
}
