package com.juchap.snake.GameScene;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.juchap.snake.Managers.ColorManager;
import com.juchap.snake.Utility.GlobalVars;

import java.util.ArrayList;
import java.util.Random;

public class Food {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public Food() {
        _shape = new ShapeRenderer();
        _random = new Random();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void spawnFood(ArrayList<Vector2> freeSpaces) {
        Vector2 newPos = freeSpaces.get(_random.nextInt(freeSpaces.size()));
        _posX = (int)newPos.x;
        _posY = (int)newPos.y;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void render() {
        _shape.begin(ShapeRenderer.ShapeType.Filled);
        _shape.setColor(ColorManager.getFoodColor());
        _shape.rect(_posX, _posY, GlobalVars.UNIT_SIZE, GlobalVars.UNIT_SIZE);
        _shape.end();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public int getPosX() { return _posX; }
    public int getPosY() { return _posY; }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private final ShapeRenderer _shape;
    private final Random _random;

    private int _posX;
    private int _posY;
}