package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
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

		Gdx.input.setInputProcessor(new InputManager());

		timer = new Timer();
		updatePosInterval = INTERVAL_EASY;
		timer.scheduleTask(new MoveSnake(), updatePosInterval, updatePosInterval);
	}

	@Override
	public void buildStage() {
		gameUI = new GameUI();

		int centerX = (int)(Math.floor((Gdx.graphics.getWidth() / GlobalVars.UNIT_SIZE) * (1.0/2.0)) * GlobalVars.UNIT_SIZE) + GlobalVars.GRID_OFFSET_X;
		int centerY = (int)(Math.floor((Gdx.graphics.getHeight() / GlobalVars.UNIT_SIZE) * (3.0/7.0)) * GlobalVars.UNIT_SIZE) + GlobalVars.GRID_OFFSET_Y;
		snake = new Snake(centerX, centerY);

		food = new Food();
		food.spawnFood();
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		snake.render();
		gameUI.render();
		food.render();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	private void gameOver() {
		timer.clear();
		ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
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

	public class MoveSnake extends Timer.Task {
		@Override
		public void run() {
			snake.move();
			snake.tryEat();

			if(snake.checkCollisions())
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
	private Timer timer;
}
