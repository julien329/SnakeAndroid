package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;
import com.juchap.snake.GameScene.BodyPart;
import com.juchap.snake.GameScene.Snake;
import com.juchap.snake.Utility.GlobalVars;

import java.util.ArrayList;


public class SplashScreen extends AbstractScreen {

    public SplashScreen() {
        super();
        Gdx.input.setInputProcessor(this);

        borders = new ShapeRenderer();

        leftBorderX = GlobalVars.GRID_OFFSET_X;
        rightBorderX = GlobalVars.GRID_OFFSET_X + GlobalVars.GRID_WIDTH - GlobalVars.UNIT_SIZE;
        bottomBorderY = GlobalVars.GRID_OFFSET_Y;
        topBorderY = GlobalVars.GRID_OFFSET_Y + GlobalVars.GRID_HEIGHT - GlobalVars.UNIT_SIZE;

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
        drawBorders();
        snake.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        borders.dispose();
        Timer.instance().clear();
    }

    private void drawBorders() {
        // Draw screen borders
        borders.begin(ShapeRenderer.ShapeType.Filled);
        borders.setColor(Color.WHITE);
        borders.rect(leftBorderX, bottomBorderY, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT);
        borders.rect(leftBorderX, bottomBorderY, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        borders.rect(leftBorderX, topBorderY, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        borders.rect(rightBorderX, bottomBorderY, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT);
        borders.end();
    }

    private void setSnake() {
        trajectoryIndex = 0;
        int partX = (Gdx.graphics.getWidth() - GlobalVars.UNIT_SIZE) / 2 - 2 * GlobalVars.UNIT_SIZE;
        int partY = (Gdx.graphics.getHeight() + GlobalVars.UNIT_SIZE) / 2 + 2 * GlobalVars.UNIT_SIZE;
        snake = new Snake(partX, partY);

        ArrayList<BodyPart> snakeParts = snake.getBodyParts();

        for(int i = 0; i < SNAKE_BUILD_X.length; i++) {
            partX += GlobalVars.UNIT_SIZE * SNAKE_BUILD_X[i];
            partY += GlobalVars.UNIT_SIZE * SNAKE_BUILD_Y[i];

            BodyPart last = snakeParts.get(snakeParts.size() - 1);
            BodyPart newPart = new BodyPart(partX, partY, Color.FOREST);
            newPart.setNext(last);
            last.setPrevious(newPart);
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

    private ShapeRenderer borders;
    private Snake snake;

    private final int[] SNAKE_BUILD_X = new int[] { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0 };
    private final int[] SNAKE_BUILD_Y = new int[] {-1,-1,-1,-1, 0, 0, 0, 0, 1, 1, 1, 1 };
    private final int[] SNAKE_TRAJECTORY_X = new int[] {1, 1, 1, 1, 0, 0, 0, 0,-1,-1,-1,-1, 0, 0, 0, 0 };
    private final int[] SNAKE_TRAJECTORY_Y = new int[] {0, 0, 0, 0,-1,-1,-1,-1, 0, 0, 0, 0, 1, 1, 1, 1 };
    private final float MOVE_INTERVAL = 0.125f;

    private int trajectoryIndex;

    private int leftBorderX;
    private int rightBorderX;
    private int topBorderY;
    private int bottomBorderY;
}
