package com.juchap.snake;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;


public class BodyPart {

    public BodyPart(int posX, int posY, int size, Color color) {
        shape_ = new ShapeRenderer();
        posX_ = posX;
        posY_ = posY;
        size_ = size;
        color_ = color;
    }

    public void render() {
        shape_.begin(ShapeRenderer.ShapeType.Filled);
        shape_.setColor(color_.r, color_.g, color_.b, color_.a);
        shape_.rect(posX_, posY_, size_, size_);
        shape_.end();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void setNext(BodyPart next) { next_ = next; }
    public void setPrevious(BodyPart previous) { previous_ = previous; }
    public void setPos(int posX, int posY) {
        lastPosX_ = posX_;
        lastPosY_ = posY_;
        posX_ = posX;
        posY_ = posY;
    }

    public Vector2 getPos() { return new Vector2(posX_, posY_); }
    public Vector2 getLastPos() { return new Vector2(lastPosX_, lastPosY_); }
    public BodyPart getPrevious() { return previous_; }
    public BodyPart getNext() { return next_; }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private BodyPart next_;
    private BodyPart previous_;
    private ShapeRenderer shape_;

    private Color color_;
    private int posX_;
    private int posY_;
    private int lastPosX_;
    private int lastPosY_;
    private int size_;
}
