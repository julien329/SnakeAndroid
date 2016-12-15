package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.juchap.snake.GameScene.Food;
import com.juchap.snake.GameScene.GameUI;
import com.juchap.snake.GameScene.Snake;


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
		PART_SIZE = Gdx.graphics.getWidth() / 38;
		screenOffsetX_ = (Gdx.graphics.getWidth() % PART_SIZE) / 2;
		screenOffsetY_ = (Gdx.graphics.getHeight() % PART_SIZE) / 2;

		gameUI_ = new GameUI(screenOffsetX_, screenOffsetY_, PART_SIZE, Color.WHITE);

		int centerX = (int)(Math.floor((Gdx.graphics.getWidth() / PART_SIZE) * (1.0/2.0)) * PART_SIZE) + screenOffsetX_;
		int centerY = (int)(Math.floor((Gdx.graphics.getHeight() / PART_SIZE) * (3.0/7.0)) * PART_SIZE) + screenOffsetY_;
		snake_ = new Snake(centerX, centerY, screenOffsetX_, screenOffsetY_, gameUI_.getGameHeight(), PART_SIZE);

		food_ = new Food(screenOffsetX_, screenOffsetY_, gameUI_.getGameHeight(), PART_SIZE, Color.RED);
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
		com.juchap.snake.Utility.ScreenManager.getInstance().showScreen(com.juchap.snake.Utility.ScreenEnum.GAME);
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

	private int PART_SIZE;
	private static final float INTERVAL_EASY = 0.125f;

	private Snake snake_;
	private GameUI gameUI_;
	private Food food_;

	private float updatePosInterval_;
	private int screenOffsetX_;
	private int screenOffsetY_;
	private Timer timer_;
}
