package com.juchap.snake;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;


public class Snake {

    public Snake(int posX, int posY, int screenOffsetX, int screenOffsetY, int gameHeight, int size) {
        bodyParts_ = new ArrayList<BodyPart>();
        partSize_ = size;
        screenOffsetX_ = screenOffsetX;
        screenOffsetY_ = screenOffsetY;
        gameHeight_ = gameHeight;

        bodyParts_.add(new BodyPart(posX, posY, size, Color.GREEN));
    }

    public void render() {
        for (BodyPart part : bodyParts_) {
            part.render();
        }
    }

    public void move() {
        BodyPart part = bodyParts_.get(0);
        part.setPos((int)(part.getPos().x + dirX_ * partSize_), (int)(part.getPos().y + dirY_ * partSize_));

        while (part.getPrevious() != null) {
            part = part.getPrevious();
            part.setPos((int)(part.getNext().getLastPos().x), (int)(part.getNext().getLastPos().y));
        }
    }

    public boolean checkCollisions() {
        int maxX = ((Gdx.graphics.getWidth() / partSize_) - 1) * partSize_;
        int maxY = gameHeight_;

        BodyPart head = bodyParts_.get(0);
        if(head.getPos().x < (screenOffsetX_ + partSize_) || head.getPos().x > maxX)
            return true;
        if(head.getPos().y < (screenOffsetY_ + partSize_) || head.getPos().y > maxY)
            return true;

        BodyPart body = head.getPrevious();
        while(body != null) {
            if(body.getPos().x == head.getPos().x && body.getPos().y == head.getPos().y)
                return true;
            body = body.getPrevious();
        }

        return false;
    }

    public void tryEat() {
        BodyPart head = bodyParts_.get(0);
        Food food = GameManager.getInstance().getFood();

        if(head.getPos().x == food.getPos().x && head.getPos().y == food.getPos().y) {
            BodyPart last = bodyParts_.get(bodyParts_.size() - 1);
            BodyPart newPart = new BodyPart((int)(last.getLastPos().x), (int)(last.getLastPos().y), partSize_, Color.FOREST);
            newPart.setNext(last);
            last.setPrevious(newPart);
            bodyParts_.add(newPart);
            food.spawnFood();
            GameManager.getInstance().getGameUI().addScore();
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void setDir(int dirX, int dirY) {
        dirX_ = dirX;
        dirY_ = dirY;
    }

    public Vector2 getDir() { return new Vector2(dirX_, dirY_); }
    public Vector2 getHeadPos() { return bodyParts_.get(0).getPos(); }
    public ArrayList<BodyPart> getBodyParts() { return bodyParts_; }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private ArrayList<BodyPart> bodyParts_;

    private int dirX_;
    private int dirY_;
    private int partSize_;
    private int screenOffsetX_;
    private int screenOffsetY_;
    private int gameHeight_;
}
