package com.juchap.snake.GameScene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.juchap.snake.Utility.GlobalVars;

public class BodyPart {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public BodyPart(int posX, int posY, Color color) {
        _shape = new ShapeRenderer();
        _posX = posX;
        _posY = posY;
        _color = color;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void render() {
        _shape.begin(ShapeRenderer.ShapeType.Filled);
        _shape.setColor(_color);
        _shape.rect(_posX, _posY, GlobalVars.UNIT_SIZE, GlobalVars.UNIT_SIZE);
        _shape.end();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void setPos(int posX, int posY) {
        _posX = posX;
        _posY = posY;
    }

    public int getPosX() { return _posX; }
    public int getPosY() { return _posY; }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private final ShapeRenderer _shape;
    private final Color _color;

    private int _posX;
    private int _posY;
}
