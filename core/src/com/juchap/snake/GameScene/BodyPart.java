package com.juchap.snake.GameScene;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
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
        lastposX = this.posX;
        lastposY = this.posY;
        this.posX = posX;
        this.posY = posY;
    }

    public Vector2 getPos() { return new Vector2(posX, posY); }
    public Vector2 getLastPos() { return new Vector2(lastposX, lastposY); }
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
    private int lastposX;
    private int lastposY;
}
