package com.juchap.snake;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;

import java.util.ArrayList;

public class Food {

    public Food(int screenOffsetX, int screenOffsetY, int gameHeight, int size, Color color) {
        shape_ = new ShapeRenderer();
        size_ = size;
        gameHeight_ = gameHeight;
        color_ = color;
        screenOffsetX_ = screenOffsetX;
        screenOffsetY_ = screenOffsetY;
    }

    public void spawnFood() {
        int maxX = (Gdx.graphics.getWidth() / size_) - 2;
        int maxY = (gameHeight_ / size_) - 2;

        Random random = new Random();
        int randX, randY;

        do {
            randX = (random.nextInt(maxX) + 1) * size_ + screenOffsetX_;
            randY = (random.nextInt(maxY) + 1) * size_ + screenOffsetY_;
        } while (!authorizedPos(randX, randY));

        posX_ = randX;
        posY_ = randY;
    }

    public void render() {
        shape_.begin(ShapeRenderer.ShapeType.Filled);
        shape_.setColor(color_.r, color_.g, color_.b, color_.a);
        shape_.rect(posX_, posY_, size_, size_);
        shape_.end();
    }

    private boolean authorizedPos(int posX, int posY) {
        ArrayList<BodyPart> bodyParts = GameManager.getInstance().getSnake().getBodyParts();
        for(BodyPart part : bodyParts) {
            if(posX == part.getPos().x && posY == part.getPos().y)
                return false;
        }
        return true;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public Vector2 getPos() { return new Vector2(posX_, posY_); }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private ShapeRenderer shape_;

    private Color color_;
    private int posX_;
    private int posY_;
    private int size_;
    private int screenOffsetX_;
    private int screenOffsetY_;
    private int gameHeight_;
}
