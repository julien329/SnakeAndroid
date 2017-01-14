package com.juchap.snake.GameScene;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Color;
import com.juchap.snake.Managers.ColorManager;
import com.juchap.snake.Screens.GameScreen;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Utility.ScreenManager;
import com.juchap.snake.Managers.SoundManager;
import com.juchap.snake.Managers.VibrationManager;


public class Snake {

    public Snake(int posX, int posY) {
        bodyParts = new ArrayList<BodyPart>();
        bodyParts.add(new BodyPart(posX, posY, ColorManager.getSnakeHeadColor()));
    }

    public void render() {
        for (BodyPart part : bodyParts) {
            part.render();
        }
    }

    public void move() {
        BodyPart tail = bodyParts.get(bodyParts.size() - 1);
        lastPosX = tail.getPosX();
        lastPosY = tail.getPosY();

        BodyPart head = bodyParts.get(0);
        int lastHeadposX = head.getPosX();
        int lastHeadposY = head.getPosY();
        head.setPos(lastHeadposX + dirX * GlobalVars.UNIT_SIZE, lastHeadposY + dirY * GlobalVars.UNIT_SIZE);

        if (tail != head) {
            bodyParts.remove(bodyParts.size() - 1);
            tail.setPos(lastHeadposX, lastHeadposY);
            bodyParts.add(1, tail);
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

        BodyPart body;
        for(int i = 1; i < bodyParts.size(); i++) {
            body = bodyParts.get(i);
            if(body.getPosX() == head.getPosX() && body.getPosY() == head.getPosY())
                return true;
        }

        return false;
    }

    public boolean tryEat() {
        BodyPart head = bodyParts.get(0);
        Food food = ((GameScreen) ScreenManager.getInstance().getScreen()).getFood();

        if(head.getPosX() == food.getPosX() && head.getPosY() == food.getPosY()) {
            BodyPart newPart = new BodyPart(lastPosX, lastPosY, ColorManager.getSnakeBodyColor());
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
    public int getLastPosX() { return lastPosX; }
    public int getLastPosY() { return lastPosY; }
    public ArrayList<BodyPart> getBodyParts() { return bodyParts; }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private ArrayList<BodyPart> bodyParts;

    private int dirX;
    private int dirY;
    private int lastPosX;
    private int lastPosY;
    private boolean dirChanged;
}
