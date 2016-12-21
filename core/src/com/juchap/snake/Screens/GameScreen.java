package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AfterAction;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.utils.Timer;
import com.juchap.snake.GameScene.Food;
import com.juchap.snake.GameScene.GameUI;
import com.juchap.snake.GameScene.Snake;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Utility.InputManager;
import com.juchap.snake.Utility.ScreenEnum;
import com.juchap.snake.Utility.ScreenManager;
import com.juchap.snake.Utility.SoundManager;
import com.juchap.snake.Utility.VibrationManager;


public class GameScreen extends AbstractScreen {

	public GameScreen() {
		super();

		pausedInputs = new InputPause();
		inputMultiplexer = new InputMultiplexer(this, (InputManager.isSwipe()) ? new GestureDetector(new InputSwipe()) : new InputTouch());
		Gdx.input.setInputProcessor(inputMultiplexer);

		isPaused = false;
	}

	@Override
	public void buildStage() {
		gameUI = new GameUI();

		int centerX = (int)(Math.floor((Gdx.graphics.getWidth() / GlobalVars.UNIT_SIZE) * (1.0/2.0)) * GlobalVars.UNIT_SIZE) + GlobalVars.GRID_OFFSET_X;
		int centerY = (int)(Math.floor((Gdx.graphics.getHeight() / GlobalVars.UNIT_SIZE) * (3.0/7.0)) * GlobalVars.UNIT_SIZE) + GlobalVars.GRID_OFFSET_Y;
		snake = new Snake(centerX, centerY);

		food = new Food();
		food.spawnFood();

		updatePosInterval = INTERVAL_EASY;
		Timer.schedule(new MoveSnake(), updatePosInterval, updatePosInterval);
		Timer.instance().start();
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		snake.render();
		gameUI.render();
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
			Timer.instance().clear();
			ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
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
						ScreenManager.getInstance().showScreen(ScreenEnum.GAME_OVER, gameUI.getScore());
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
			Vector2 pos = snake.getHeadPos();
			int distX = (int) (Gdx.input.getX() - pos.x);
			int distY = (int) (Gdx.graphics.getHeight() - Gdx.input.getY() - pos.y);

			if (snake.getDir().x == 0 && snake.getDir().y != 0)
				snake.setDir((int) Math.signum(distX), 0);
			else if (snake.getDir().y == 0 && snake.getDir().x != 0)
				snake.setDir(0, (int) Math.signum(distY));
			else {
				if (Math.abs(distX) >= Math.abs(distY)) {
					snake.setDir((int) Math.signum(distX), 0);
				} else {
					snake.setDir(0, (int) Math.signum(distY));
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

				if (Math.abs(this.deltaX) >= distToTravel && snake.getDir().x == 0) {
					snake.setDir((int) Math.signum(this.deltaX), 0);
					moved = true;
					return true;
				} else if (Math.abs(this.deltaY) >= distToTravel && snake.getDir().y == 0) {
					snake.setDir(0, (int) Math.signum(-this.deltaY));
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

	private class InputPause extends InputAdapter {
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
				Gdx.input.setInputProcessor(inputMultiplexer);
				isPaused = false;
				Timer.instance().start();
				return true;
			}
			return false;
		}
	}

	private class MoveSnake extends Timer.Task {
		@Override
		public void run() {
			snake.move();
			snake.tryEat();

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


	////////////////////////////////////////////////////////////////////////////////////////////////
	/// VARIABLES
	////////////////////////////////////////////////////////////////////////////////////////////////

	private final float INTERVAL_EASY = 0.125f;

	private Snake snake;
	private GameUI gameUI;
	private Food food;

	private float updatePosInterval;

	private InputMultiplexer inputMultiplexer;
	private InputPause pausedInputs;
	private boolean isPaused;
}
