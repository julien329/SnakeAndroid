package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.juchap.snake.GameScene.ControlButton;
import com.juchap.snake.GameScene.Food;
import com.juchap.snake.GameScene.GameUI;
import com.juchap.snake.GameScene.Snake;
import com.juchap.snake.Managers.DifficultyManager;
import com.juchap.snake.Managers.InputManager;
import com.juchap.snake.Managers.SoundManager;
import com.juchap.snake.Managers.VibrationManager;
import com.juchap.snake.Utility.GlobalStrings;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Utility.ScreenEnum;
import com.juchap.snake.Utility.ScreenManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameScreen extends AbstractScreen {

	////////////////////////////////////////////////////////////////////////////////////////////////
	public GameScreen() {
		_isPaused = false;

		_freeSpaces = new ArrayList<>();
		for (int i = GlobalVars.LEFT + GlobalVars.UNIT_SIZE ; i < GlobalVars.RIGHT - GlobalVars.UNIT_SIZE; i += GlobalVars.UNIT_SIZE) {
			for (int j = GlobalVars.BOTTOM + GlobalVars.UNIT_SIZE ; j < GlobalVars.GAME_GRID_TOP - GlobalVars.UNIT_SIZE; j += GlobalVars.UNIT_SIZE) {
				_freeSpaces.add(new Vector2(i, j));
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void buildStage() {
		_gameUI = new GameUI(_uiRenderer, _spriteBatch);

		int centerX = GlobalVars.LEFT + (16 * GlobalVars.UNIT_SIZE);
		int centerY = GlobalVars.BOTTOM + ((int)Math.floor((0.5f * GlobalVars.GAME_GRID_HEIGHT) / GlobalVars.UNIT_SIZE) * GlobalVars.UNIT_SIZE);
		_freeSpaces.remove(new Vector2(centerX, centerY));
		_snake = new Snake(centerX, centerY);

		_food = new Food();
		_food.spawnFood(_freeSpaces);

		float updatePosInterval = DifficultyManager.getInterval();
		Timer.schedule(new MoveSnake(), updatePosInterval, updatePosInterval);
		Timer.instance().start();

		setupInputs();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void render(float delta) {
		super.render(delta);

		_snake.render();
		_food.render();
		_gameUI.render();

		drawBorders();

		if (_isPaused) {
			_gameUI.renderPause();
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public boolean keyUp(int keycode) {
		if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK) ) {
			pauseGame();
			return true;
		}
		return false;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void pause() {
		pauseGame();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	private void gameOver() {
		Timer.instance().clear();
		VibrationManager.vibrateLong();
		SoundManager.playGameOver();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(VibrationManager.LONG);
				} catch (InterruptedException e) { throw new RuntimeException(e); }

				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						int score = (_snake.getBodyParts().size() - 1 == _gameUI.getScore()) ? _gameUI.getScore() : 0;
						ScreenManager.getInstance().showScreen(ScreenEnum.GAME_OVER, score);
					}
				});
			}
		}).start();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	public void	pauseGame() {
		Timer.instance().stop();
		_isPaused = true;
		Gdx.input.setInputProcessor(_pausedInputs);

		if (_controlPad != null) {
			for (ControlButton control : _controlPad) {
				control.eventTouchUp();
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	private void setupInputs() {
		_pausedInputs = new InputPaused();

		InputProcessor inputProcessor = this;
		if (InputManager.isSwipe()) {
			inputProcessor = new GestureDetector(new InputSwipe());
		}
		else if (InputManager.isTouch()) {
			inputProcessor =  new InputTouch();
		}
		else if (InputManager.isDpad() || InputManager.isHalfDpad()) {
			inputProcessor = new InputControlPad();

			final float leftPos = GlobalVars.LEFT + (2 * GlobalVars.UNIT_SIZE);
			final float rightPos = GlobalVars.RIGHT - (2 * GlobalVars.UNIT_SIZE);
			final float bottomPos = GlobalVars.BOTTOM + (2 * GlobalVars.UNIT_SIZE);
			final float deltaPos = GlobalVars.UNIT_SIZE / 2.f;
			float topPos, centerY;

			if (InputManager.isHalfDpad()) {
				topPos = GlobalVars.CENTER_Y - (2 * GlobalVars.UNIT_SIZE);
				centerY = (GlobalVars.BOTTOM + GlobalVars.UNIT_SIZE) + ((GlobalVars.CENTER_Y - (GlobalVars.BOTTOM + GlobalVars.UNIT_SIZE)) / 2.f);
			}
			else {
				topPos = GlobalVars.GAME_GRID_TOP - (2 * GlobalVars.UNIT_SIZE);
				centerY = (GlobalVars.BOTTOM + GlobalVars.UNIT_SIZE) + ((GlobalVars.GAME_GRID_TOP - (GlobalVars.BOTTOM + GlobalVars.UNIT_SIZE)) / 2.f);
			}

			final float[] leftVertices = new float[]{ leftPos, bottomPos + deltaPos, leftPos, topPos - deltaPos, GlobalVars.CENTER_X - (GlobalVars.UNIT_SIZE / 2.f), centerY };
			final float[] rightVertices = new float[]{ rightPos, bottomPos + deltaPos, rightPos, topPos - deltaPos, GlobalVars.CENTER_X + (GlobalVars.UNIT_SIZE / 2.f), centerY };
			final float[] upVertices = new float[]{ leftPos + deltaPos, topPos, rightPos - deltaPos, topPos, GlobalVars.CENTER_X, centerY + (GlobalVars.UNIT_SIZE / 2.f) };
			final float[] downVertices = new float[]{ leftPos + deltaPos, bottomPos, rightPos - deltaPos, bottomPos, GlobalVars.CENTER_X, centerY - (GlobalVars.UNIT_SIZE / 2.f) };

			final ControlButton leftButton = new ControlButton(_uiRenderer, _snake, ControlButton.LEFT, leftVertices);
			final ControlButton rightButton = new ControlButton(_uiRenderer, _snake, ControlButton.RIGHT, rightVertices);
			final ControlButton upButton = new ControlButton(_uiRenderer, _snake, ControlButton.UP, upVertices);
			final ControlButton downButton = new ControlButton(_uiRenderer, _snake, ControlButton.DOWN, downVertices);

			addActor(leftButton);
			addActor(rightButton);
			addActor(upButton);
			addActor(downButton);

			_controlPad = Arrays.asList(leftButton, rightButton, upButton, downButton);
		}

		_inputMultiplexer = new InputMultiplexer(this, inputProcessor);
		Gdx.input.setInputProcessor(_inputMultiplexer);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	/// CLASSES
	////////////////////////////////////////////////////////////////////////////////////////////////

	private class InputSwipe extends GestureDetector.GestureAdapter {
		@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
			_deltaX = _deltaY = 0;
			_moved = false;
			return true;
		}

		@Override
		public boolean pan(float x, float y, float deltaX, float deltaY) {
			if (_moved) {
				return false;
			}

			_deltaX += deltaX;
			_deltaY += deltaY;

			if (Math.abs(_deltaX) >= DIST_TO_TRAVEL && _snake.getDirX() == 0) {
				_snake.setDir((int)Math.signum(_deltaX), 0);
				_moved = true;
				return true;
			} else if (Math.abs(_deltaY) >= DIST_TO_TRAVEL && _snake.getDirY() == 0) {
				_snake.setDir(0, (int)Math.signum(-_deltaY));
				_moved = true;
				return true;
			}

			return false;
		}

		private float _deltaX = 0;
		private float _deltaY = 0;
		private boolean _moved = false;

		private final float DIST_TO_TRAVEL = GlobalVars.GRID_WIDTH / 32.0f;
	}

	private class InputTouch extends InputAdapter {
		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			int distX = Gdx.input.getX() - _snake.getHeadPosX();
			int distY = Gdx.graphics.getHeight() - Gdx.input.getY() - _snake.getHeadPosY();
			int signX = (int) Math.signum(distX);
			int signY = (int) Math.signum(distY);

			if (_snake.getDirX() == 0 && _snake.getDirY() != 0) {
				if (signX != 0) {
					_snake.setDir(signX, 0);
				}
			}
			else if (_snake.getDirY() == 0 && _snake.getDirX() != 0) {
				if (signY != 0) {
					_snake.setDir(0, signY);
				}
			}
			else if (_snake.getDirX() == 0 && _snake.getDirY() == 0) {
				if (Math.abs(distX) >= Math.abs(distY)) {
					_snake.setDir(signX, 0);
				} else {
					_snake.setDir(0, signY);
				}
			}
			return true;
		}
	}

	private class InputControlPad extends InputAdapter {
		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			Vector2 inputPos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
			for (ControlButton control : _controlPad) {
				control.eventTouchDown(inputPos);
			}
			return true;
		}
		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			for (ControlButton control : _controlPad) {
				control.eventTouchUp();
			}
			return false;
		}
	}

	private class InputPaused extends InputAdapter {
		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			_isInputDown = true;
			_downX = Gdx.input.getX();
			_downY = Gdx.input.getY();
			return true;
		}
		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			if (!_isInputDown) {
				return true;
			}

			_isInputDown = false;

			final float deltaX = Math.abs(_downX - Gdx.input.getX());
			final float deltaY = Math.abs(_downY - Gdx.input.getY());
			final float dist = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

			if (dist <= MAX_TRAVEL_DIST) {
				Gdx.input.setInputProcessor(_inputMultiplexer);
				_isPaused = false;
				Timer.instance().start();
			}

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

		private boolean _isInputDown = false;
		private float _downX = 0;
		private float _downY = 0;

		private final float MAX_TRAVEL_DIST = GlobalVars.GRID_WIDTH / 16.f;
	}

	private class MoveSnake extends Timer.Task {
		@Override
		public void run() {
			_snake.move();
			_freeSpaces.remove(new Vector2(_snake.getHeadPosX(), _snake.getHeadPosY()));
			if (!_snake.tryEat() && !(_snake.getDirX() == 0 && _snake.getDirY() == 0)) {
				_freeSpaces.add(new Vector2(_snake.getLastPosX(), _snake.getLastPosY()));
			}

			Gdx.graphics.requestRendering();

			if (_snake.checkCollisions()) {
				gameOver();
			}
		}
	}


	////////////////////////////////////////////////////////////////////////////////////////////////
	/// GET / SET
	////////////////////////////////////////////////////////////////////////////////////////////////

	public Snake getSnake() { return _snake; }
	public GameUI getGameUI() { return _gameUI; }
	public Food getFood() { return _food; }
	public ArrayList<Vector2> getFreeSpaces() { return _freeSpaces; }


	////////////////////////////////////////////////////////////////////////////////////////////////
	/// VARIABLES
	////////////////////////////////////////////////////////////////////////////////////////////////

	private Snake _snake;
	private GameUI _gameUI;
	private Food _food;
	private InputMultiplexer _inputMultiplexer;
	private InputPaused _pausedInputs;
	private final ArrayList<Vector2> _freeSpaces;
	private List<ControlButton> _controlPad;

	private boolean _isPaused;
}