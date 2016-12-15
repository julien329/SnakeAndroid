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

		timer_ = new Timer();
		updatePosInterval_ = INTERVAL_EASY;
		timer_.scheduleTask(new MoveSnake(), updatePosInterval_, updatePosInterval_);
	}

	@Override
	public void buildStage() {
		gameUI_ = new GameUI();

		int centerX = (int)(Math.floor((Gdx.graphics.getWidth() / GlobalVars.UNIT_SIZE) * (1.0/2.0)) * GlobalVars.UNIT_SIZE) + GlobalVars.GRID_OFFSET_X;
		int centerY = (int)(Math.floor((Gdx.graphics.getHeight() / GlobalVars.UNIT_SIZE) * (3.0/7.0)) * GlobalVars.UNIT_SIZE) + GlobalVars.GRID_OFFSET_Y;
		snake_ = new Snake(centerX, centerY);

		food_ = new Food();
		food_.spawnFood();
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		snake_.render();
		gameUI_.render();
		food_.render();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	private void gameOver() {
		timer_.clear();
		ScreenManager.getInstance().showScreen(ScreenEnum.GAME);
	}


	////////////////////////////////////////////////////////////////////////////////////////////////
	/// CLASSES
	////////////////////////////////////////////////////////////////////////////////////////////////

	public class InputManager extends InputAdapter {
		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			Vector2 pos = snake_.getHeadPos();
			int distX = (int) (Gdx.input.getX() - pos.x);
			int distY = (int) (Gdx.graphics.getHeight() - Gdx.input.getY() - pos.y);

			if (snake_.getDir().x == 0 && snake_.getDir().y != 0)
				snake_.setDir((int) Math.signum(distX), 0);
			else if (snake_.getDir().y == 0 && snake_.getDir().x != 0)
				snake_.setDir(0, (int) Math.signum(distY));
			else {
				if (Math.abs(distX) >= Math.abs(distY)) {
					snake_.setDir((int) Math.signum(distX), 0);
				} else {
					snake_.setDir(0, (int) Math.signum(distY));
				}
			}

			return true;
		}
	}

	public class MoveSnake extends Timer.Task {
		@Override
		public void run() {
			snake_.move();
			snake_.tryEat();

			if(snake_.checkCollisions())
				gameOver();
		}
	}


	////////////////////////////////////////////////////////////////////////////////////////////////
	/// GET / SET
	////////////////////////////////////////////////////////////////////////////////////////////////

	public Snake getSnake() { return snake_; }
	public GameUI getGameUI() { return gameUI_; }
	public Food getFood() { return food_; }


	////////////////////////////////////////////////////////////////////////////////////////////////
	/// VARIABLES
	////////////////////////////////////////////////////////////////////////////////////////////////

	private static final float INTERVAL_EASY = 0.125f;

	private Snake snake_;
	private GameUI gameUI_;
	private Food food_;

	private float updatePosInterval_;
	private Timer timer_;
}
