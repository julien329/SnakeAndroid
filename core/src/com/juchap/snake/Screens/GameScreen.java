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
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Managers.InputManager;
import com.juchap.snake.Utility.ScreenEnum;
import com.juchap.snake.Utility.ScreenManager;
import com.juchap.snake.Managers.SoundManager;
import com.juchap.snake.Utility.GlobalStrings;
import com.juchap.snake.Managers.VibrationManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GameScreen extends AbstractScreen {

	public GameScreen() {
		super();

		isPaused = false;

		freeSpaces = new ArrayList<Vector2>();
		for(int i = GlobalVars.LEFT + GlobalVars.UNIT_SIZE ; i < GlobalVars.RIGHT - GlobalVars.UNIT_SIZE; i += GlobalVars.UNIT_SIZE) {
			for(int j = GlobalVars.BOTTOM + GlobalVars.UNIT_SIZE ; j < GlobalVars.GAME_GRID_TOP - GlobalVars.UNIT_SIZE; j += GlobalVars.UNIT_SIZE) {
				freeSpaces.add(new Vector2(i, j));
			}
		}
	}

	@Override
	public void buildStage() {
		gameUI = new GameUI(uiRenderer, spriteBatch);

		int centerX = GlobalVars.LEFT + (GlobalVars.GRID_WIDTH / 2);
		int centerY = GlobalVars.BOTTOM + ((int)Math.floor((0.5f * GlobalVars.GAME_GRID_HEIGHT) / GlobalVars.UNIT_SIZE) * GlobalVars.UNIT_SIZE);
		freeSpaces.remove(new Vector2(centerX, centerY));
		snake = new Snake(centerX, centerY);

		food = new Food();
		food.spawnFood(freeSpaces);

		float updatePosInterval = DifficultyManager.getInterval();
		Timer.schedule(new MoveSnake(), updatePosInterval, updatePosInterval);
		Timer.instance().start();

        setupInputs();
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		snake.render();
		food.render();
		gameUI.render();
		drawBorders();

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

		if (controlPad != null) {
            for (ControlButton control : controlPad) {
                control.eventTouchUp();
            }
        }
	}

	private void setupInputs() {
        this.pausedInputs = new InputPaused();

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
                centerY = (GlobalVars.BOTTOM + GlobalVars.UNIT_SIZE) + ((GlobalVars.CENTER_Y - (GlobalVars.BOTTOM + GlobalVars.UNIT_SIZE)) / 2);
            }
            else {
                topPos = GlobalVars.GAME_GRID_TOP - (2 * GlobalVars.UNIT_SIZE);
                centerY = (GlobalVars.BOTTOM + GlobalVars.UNIT_SIZE) + ((GlobalVars.GAME_GRID_TOP - (GlobalVars.BOTTOM + GlobalVars.UNIT_SIZE)) / 2);
            }

            final float[] leftVertices = new float[]{ leftPos, bottomPos + deltaPos, leftPos, topPos - deltaPos, GlobalVars.CENTER_X - (GlobalVars.UNIT_SIZE / 2), centerY };
            final float[] rightVertices = new float[]{ rightPos, bottomPos + deltaPos, rightPos, topPos - deltaPos, GlobalVars.CENTER_X + (GlobalVars.UNIT_SIZE / 2), centerY };
            final float[] upVertices = new float[]{ leftPos + deltaPos, topPos, rightPos - deltaPos, topPos, GlobalVars.CENTER_X, centerY + (GlobalVars.UNIT_SIZE / 2) };
            final float[] downVertices = new float[]{ leftPos + deltaPos, bottomPos, rightPos - deltaPos, bottomPos, GlobalVars.CENTER_X, centerY - (GlobalVars.UNIT_SIZE / 2) };

            final ControlButton leftButton = new ControlButton(uiRenderer, snake, ControlButton.LEFT, leftVertices);
            final ControlButton rightButton = new ControlButton(uiRenderer, snake, ControlButton.RIGHT, rightVertices);
            final ControlButton upButton = new ControlButton(uiRenderer, snake, ControlButton.UP, upVertices);
            final ControlButton downButton = new ControlButton(uiRenderer, snake, ControlButton.DOWN, downVertices);

            this.addActor(leftButton);
            this.addActor(rightButton);
            this.addActor(upButton);
            this.addActor(downButton);

            this.controlPad = Arrays.asList(leftButton, rightButton, upButton, downButton);
        }

        this.inputMultiplexer = new InputMultiplexer(this, inputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }


	////////////////////////////////////////////////////////////////////////////////////////////////
	/// CLASSES
	////////////////////////////////////////////////////////////////////////////////////////////////

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
        private final float distToTravel = GlobalVars.GRID_WIDTH / 32.0f;
    }

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

    private class InputControlPad extends InputAdapter {
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            Vector2 inputPos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            for (ControlButton control : controlPad) {
                control.eventTouchDown(inputPos);
            }
            return true;
        }
        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            for (ControlButton control : controlPad) {
                control.eventTouchUp();
            }
            return false;
        }
    }

	private class InputPaused extends InputAdapter {
		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            this.isInputDown = true;
			this.downX = Gdx.input.getX();
            this.downY = Gdx.input.getY();
			return true;
		}
        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		    if (isInputDown) {
		        this.isInputDown = false;

                final float deltaX = Math.abs(this.downX - Gdx.input.getX());
                final float deltaY = Math.abs(this.downY - Gdx.input.getY());
                final float dist = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

                if (dist <= maxTravelDist) {
                    Gdx.input.setInputProcessor(inputMultiplexer);
                    isPaused = false;
                    Timer.instance().start();
                }
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

		private boolean isInputDown = false;
        private float downX = 0;
        private float downY = 0;
        private final float maxTravelDist = GlobalVars.GRID_WIDTH / 16.f;
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
	private List<ControlButton> controlPad;

	private boolean isPaused;
}