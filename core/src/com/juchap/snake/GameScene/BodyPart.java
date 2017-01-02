package com.juchap.snake.GameScene;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.juchap.snake.Utility.GlobalVars;


public class BodyPart {

    public BodyPart(int posX, int posY, Color color) {
        shape = new ShapeRenderer();
        this.posX = posX;
        this.posY = posY;
        this.color = color;
    }

    public void render() {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(color);
        shape.rect(posX, posY, GlobalVars.UNIT_SIZE, GlobalVars.UNIT_SIZE);
        shape.end();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void setPos(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() { return posX; }
    public int getPosY() { return posY; }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private ShapeRenderer shape;
    private Color color;

    private int posX;
    private int posY;
}
