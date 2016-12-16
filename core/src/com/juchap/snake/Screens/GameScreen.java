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
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Utility.ScreenEnum;
import com.juchap.snake.Utility.ScreenManager;


public class GameScreen extends AbstractScreen {

	public GameScreen() {
		super();

		pausedInputs = new PausedInputs();
		inputMultiplexer = new InputMultiplexer(this, new GestureDetector(new GestureManager()));
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
		Timer.instance().clear();
	}

	@Override
	public boolean keyUp(int keycode) {
		if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK) ) {
			Timer.instance().stop();
			ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
			return true;
		}
		return false;
	}

	private void gameOver() {
		Timer.instance().stop();
		ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
	}

	public void	pauseGame() {
		Timer.instance().stop();
		isPaused = true;
		Gdx.input.setInputProcessor(pausedInputs);
	}


	////////////////////////////////////////////////////////////////////////////////////////////////
	/// CLASSES
	////////////////////////////////////////////////////////////////////////////////////////////////

	public class InputManager extends InputAdapter {
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

	public class GestureManager extends GestureDetector.GestureAdapter {
		@Override
		public boolean fling(float velocityX, float velocityY, int button) {
			if(Math.abs(velocityX) >= Math.abs(velocityY) && snake.getDir().x == 0) {
				snake.setDir((int) Math.signum(velocityX), 0);
				return true;
			}
			else if (Math.abs(velocityX) < Math.abs(velocityY) && snake.getDir().y == 0){
				snake.setDir(0, (int) Math.signum(-velocityY));
				return true;
			}
			return false;
		}
	}

	public class PausedInputs extends InputAdapter {
		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			Gdx.input.setInputProcessor(inputMultiplexer);
			isPaused = false;
			Timer.instance().start();
			return true;
		}
	}

	public class MoveSnake extends Timer.Task {
		@Override
		public void run() {
			snake.move();
			snake.tryEat();

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

	private static final float INTERVAL_EASY = 0.125f;

	private Snake snake;
	private GameUI gameUI;
	private Food food;

	private float updatePosInterval;

	private InputMultiplexer inputMultiplexer;
	private PausedInputs pausedInputs;
	private boolean isPaused;
}
