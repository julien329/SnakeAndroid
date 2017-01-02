package com.juchap.snake.GameScene;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Color;
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
        part.setPos(part.getPosX() + dirX * GlobalVars.UNIT_SIZE, part.getPosY() + dirY * GlobalVars.UNIT_SIZE);

        while (part.getPrevious() != null) {
            part = part.getPrevious();
            part.setPos(part.getNext().getLastPosX(), part.getNext().getLastPosY());
        }
        
        dirChanged = false;
    }

    public boolean checkCollisions() {
        int maxX = GlobalVars.GRID_OFFSET_X + GlobalVars.GRID_WIDTH - (2 * GlobalVars.UNIT_SIZE);
        int maxY = GlobalVars.GRID_OFFSET_Y + GlobalVars.GAME_GRID_HEIGHT - (2 * GlobalVars.UNIT_SIZE);

        BodyPart head = bodyParts.get(0);
        if(head.getPosX() < (GlobalVars.GRID_OFFSET_X + GlobalVars.UNIT_SIZE) || head.getPosX() > maxX)
            return true;
        if(head.getPosY() < (GlobalVars.GRID_OFFSET_Y + GlobalVars.UNIT_SIZE) || head.getPosY() > maxY)
            return true;

        BodyPart body = head.getPrevious();
        while(body != null) {
            if(body.getPosX() == head.getPosX() && body.getPosY() == head.getPosY())
                return true;
            body = body.getPrevious();
        }

        return false;
    }

    public boolean tryEat() {
        BodyPart head = bodyParts.get(0);
        Food food = ((GameScreen) ScreenManager.getInstance().getScreen()).getFood();

        if(head.getPosX() == food.getPosX() && head.getPosY() == food.getPosY()) {
            BodyPart last = bodyParts.get(bodyParts.size() - 1);
            BodyPart newPart = new BodyPart(last.getLastPosX(), last.getLastPosY(), Color.FOREST);
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

    public int getDirX() { return dirX; }
    public int getDirY() { return dirY; }
    public int getHeadPosX() { return bodyParts.get(0).getPosX(); }
    public int getHeadPosY() { return bodyParts.get(0).getPosY(); }
    public int getEndLastPosX() { return bodyParts.get(bodyParts.size() - 1).getLastPosX(); }
    public int getEndLastPosY() { return bodyParts.get(bodyParts.size() - 1).getLastPosY(); }
    public ArrayList<BodyPart> getBodyParts() { return bodyParts; }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private ArrayList<BodyPart> bodyParts;

    private int dirX;
    private int dirY;
    private boolean dirChanged;
}
