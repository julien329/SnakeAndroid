package com.juchap.snake.GameScene;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.juchap.snake.Screens.GameScreen;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Utility.ScreenManager;


public class Snake {

    public Snake(int posX, int posY) {
        bodyParts_ = new ArrayList<BodyPart>();
        bodyParts_.add(new BodyPart(posX, posY, GlobalVars.UNIT_SIZE, Color.GREEN));
    }

    public void render() {
        for (BodyPart part : bodyParts_) {
            part.render();
        }
    }

    public void move() {
        BodyPart part = bodyParts_.get(0);
        part.setPos((int)(part.getPos().x + dirX_ * GlobalVars.UNIT_SIZE), (int)(part.getPos().y + dirY_ * GlobalVars.UNIT_SIZE));

        while (part.getPrevious() != null) {
            part = part.getPrevious();
            part.setPos((int)(part.getNext().getLastPos().x), (int)(part.getNext().getLastPos().y));
        }
    }

    public boolean checkCollisions() {
        int maxX = GlobalVars.GRID_OFFSET_X + GlobalVars.GRID_WIDTH - (2 * GlobalVars.UNIT_SIZE);
        int maxY = GlobalVars.GRID_OFFSET_Y + GlobalVars.GAME_GRID_HEIGHT - (2 * GlobalVars.UNIT_SIZE);

        BodyPart head = bodyParts_.get(0);
        if(head.getPos().x < (GlobalVars.GRID_OFFSET_X + GlobalVars.UNIT_SIZE) || head.getPos().x > maxX)
            return true;
        if(head.getPos().y < (GlobalVars.GRID_OFFSET_X + GlobalVars.UNIT_SIZE) || head.getPos().y > maxY)
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
        Food food = ((GameScreen) ScreenManager.getInstance().getScreen()).getFood();

        if(head.getPos().x == food.getPos().x && head.getPos().y == food.getPos().y) {
            BodyPart last = bodyParts_.get(bodyParts_.size() - 1);
            BodyPart newPart = new BodyPart((int)(last.getLastPos().x), (int)(last.getLastPos().y), GlobalVars.UNIT_SIZE, Color.FOREST);
            newPart.setNext(last);
            last.setPrevious(newPart);
            bodyParts_.add(newPart);
            food.spawnFood();
            ((GameScreen) ScreenManager.getInstance().getScreen()).getGameUI().addScore();
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
}
