package com.juchap.snake.GameScene;

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
        freeSpaces = ((GameScreen)ScreenManager.getInstance().getScreen()).getFreeSpaces();
        random = new Random();
    }

    public void spawnFood() {
        Vector2 pos = freeSpaces.get(random.nextInt(freeSpaces.size()));

        posX = (int)pos.x;
        posY = (int)pos.y;
    }

    public void render() {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.RED);
        shape.rect(posX, posY, GlobalVars.UNIT_SIZE, GlobalVars.UNIT_SIZE);
        shape.end();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public Vector2 getPos() { return new Vector2(posX, posY); }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private ShapeRenderer shape;
    private ArrayList<Vector2> freeSpaces;
    private Random random;

    private int posX;
    private int posY;
}
