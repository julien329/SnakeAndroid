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

    public void setNext(BodyPart next) { this.next = next; }
    public void setPrevious(BodyPart previous) { this.previous = previous; }
    public void setPos(int posX, int posY) {
        lastPosX = this.posX;
        lastPosY = this.posY;
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public int getLastPosX() { return lastPosX; }
    public int getLastPosY() { return lastPosY; }
    public BodyPart getPrevious() { return previous; }
    public BodyPart getNext() { return next; }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private BodyPart next;
    private BodyPart previous;
    private ShapeRenderer shape;
    private Color color;

    private int posX;
    private int posY;
    private int lastPosX;
    private int lastPosY;
}
