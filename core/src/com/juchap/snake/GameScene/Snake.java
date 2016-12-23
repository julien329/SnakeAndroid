package com.juchap.snake.GameScene;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.juchap.snake.Screens.GameScreen;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Utility.ScreenManager;
import com.juchap.snake.Utility.SoundManager;
import com.juchap.snake.Utility.VibrationManager;


public class Snake {

    public Snake(int posX, int posY) {
        bodyParts = new ArrayList<BodyPart>();
        bodyParts.add(new BodyPart(posX, posY, Color.GREEN));
    }

    public void render() {
        for (BodyPart part : bodyParts) {
            part.render();
        }
    }

    public void move() {
        BodyPart part = bodyParts.get(0);
        part.setPos((int)part.getPos().x + dirX * GlobalVars.UNIT_SIZE, (int)part.getPos().y + dirY * GlobalVars.UNIT_SIZE);

        while (part.getPrevious() != null) {
            part = part.getPrevious();
            part.setPos((int)(part.getNext().getLastPos().x), (int)(part.getNext().getLastPos().y));
        }
        
        dirChanged = false;
    }

    public boolean checkCollisions() {
        int maxX = GlobalVars.GRID_OFFSET_X + GlobalVars.GRID_WIDTH - (2 * GlobalVars.UNIT_SIZE);
        int maxY = GlobalVars.GRID_OFFSET_Y + GlobalVars.GAME_GRID_HEIGHT - (2 * GlobalVars.UNIT_SIZE);

        BodyPart head = bodyParts.get(0);
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

    public boolean tryEat() {
        BodyPart head = bodyParts.get(0);
        Food food = ((GameScreen) ScreenManager.getInstance().getScreen()).getFood();

        if(head.getPos().x == food.getPos().x && head.getPos().y == food.getPos().y) {
            BodyPart last = bodyParts.get(bodyParts.size() - 1);
            BodyPart newPart = new BodyPart((int)(last.getLastPos().x), (int)(last.getLastPos().y), Color.FOREST);
            newPart.setNext(last);
            last.setPrevious(newPart);
            bodyParts.add(newPart);
            food.spawnFood();
            ((GameScreen) ScreenManager.getInstance().getScreen()).getGameUI().addScore();

            VibrationManager.vibrateShort();
            SoundManager.playEat();
            return true;
        }

        return false;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void setDir(int dirX, int dirY) {
        if(!dirChanged) {
            this.dirX = dirX;
            this.dirY = dirY;
            dirChanged = true;
        }
    }

    public Vector2 getDir() { return new Vector2(dirX, dirY); }
    public Vector2 getHeadPos() { return bodyParts.get(0).getPos(); }
    public Vector2 getEndLastPos() { return bodyParts.get(bodyParts.size() - 1).getLastPos(); }
    public ArrayList<BodyPart> getBodyParts() { return bodyParts; }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private ArrayList<BodyPart> bodyParts;
    private int dirX;
    private int dirY;
    private boolean dirChanged;
}
