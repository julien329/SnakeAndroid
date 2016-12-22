package com.juchap.snake.GameScene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.juchap.snake.Screens.GameScreen;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Utility.ScreenManager;
import java.util.Random;
import java.util.ArrayList;


public class Food {

    public Food() {
        shape = new ShapeRenderer();
    }

    public void spawnFood() {
        int maxX = (Gdx.graphics.getWidth() / GlobalVars.UNIT_SIZE) - 2;
        int maxY = (GlobalVars.GAME_GRID_HEIGHT / GlobalVars.UNIT_SIZE) - 2;

        Random random = new Random();
        int randX, randY;

        do {
            randX = (random.nextInt(maxX) + 1) * GlobalVars.UNIT_SIZE + GlobalVars.GRID_OFFSET_X;
            randY = (random.nextInt(maxY) + 1) * GlobalVars.UNIT_SIZE + GlobalVars.GRID_OFFSET_Y;
        } while (!authorizedPos(randX, randY));

        posX = randX;
        posY = randY;
    }

    public void render() {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.RED);
        shape.rect(posX, posY, GlobalVars.UNIT_SIZE, GlobalVars.UNIT_SIZE);
        shape.end();
    }

    private boolean authorizedPos(int posX, int posY) {
        ArrayList<BodyPart> bodyParts = ((GameScreen) ScreenManager.getInstance().getScreen()).getSnake().getBodyParts();
        for(BodyPart part : bodyParts) {
            if(posX == part.getPos().x && posY == part.getPos().y)
                return false;
        }
        return true;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public Vector2 getPos() { return new Vector2(posX, posY); }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private ShapeRenderer shape;

    private int posX;
    private int posY;
}
