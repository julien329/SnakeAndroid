package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.juchap.snake.GameScene.BodyPart;
import com.juchap.snake.GameScene.Snake;
import com.juchap.snake.Managers.ColorManager;
import com.juchap.snake.Managers.FontManager;
import com.juchap.snake.Utility.GlobalVars;
import java.util.ArrayList;


public class SplashScreen extends AbstractScreen {

    public SplashScreen() {
        super();
        Gdx.input.setInputProcessor(this);
        setSnake();
    }

    @Override
    public void buildStage() {
        Timer.schedule(new MoveSnake(), MOVE_INTERVAL, MOVE_INTERVAL);
        Timer.instance().start();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if(showSnake) {
            snake.render();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        Timer.instance().clear();
    }

    private void setSnake() {
        trajectoryIndex = 0;
        showSnake = !FontManager.allFilesExist();

        int partX = GlobalVars.CENTER_X - (2 * GlobalVars.UNIT_SIZE);
        int partY = GlobalVars.CENTER_Y + (2 * GlobalVars.UNIT_SIZE);
        snake = new Snake(partX, partY);

        ArrayList<BodyPart> snakeParts = snake.getBodyParts();

        for(int i = 0; i < SNAKE_BUILD_X.length; i++) {
            partX += GlobalVars.UNIT_SIZE * SNAKE_BUILD_X[i];
            partY += GlobalVars.UNIT_SIZE * SNAKE_BUILD_Y[i];

            BodyPart newPart = new BodyPart(partX, partY, ColorManager.getSnakeBodyColor());
            snakeParts.add(newPart);
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// CLASSES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private class MoveSnake extends Timer.Task {
        @Override
        public void run() {
            snake.setDir(SNAKE_TRAJECTORY_X[trajectoryIndex], SNAKE_TRAJECTORY_Y[trajectoryIndex]);
            snake.move();

            trajectoryIndex = (trajectoryIndex + 1) % SNAKE_TRAJECTORY_X.length;
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final int[] SNAKE_BUILD_X = new int[] { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0 };
    private static final int[] SNAKE_BUILD_Y = new int[] {-1,-1,-1,-1, 0, 0, 0, 0, 1, 1, 1, 1 };
    private static final int[] SNAKE_TRAJECTORY_X = new int[] {1, 1, 1, 1, 0, 0, 0, 0,-1,-1,-1,-1, 0, 0, 0, 0 };
    private static final int[] SNAKE_TRAJECTORY_Y = new int[] {0, 0, 0, 0,-1,-1,-1,-1, 0, 0, 0, 0, 1, 1, 1, 1 };
    private static final float MOVE_INTERVAL = 0.125f;

    private Snake snake;

    private boolean showSnake;
    private int trajectoryIndex;
}