package com.juchap.snake.GameScene;

import com.badlogic.gdx.math.Vector2;
import com.juchap.snake.Managers.ColorManager;
import com.juchap.snake.Managers.SoundManager;
import com.juchap.snake.Managers.VibrationManager;
import com.juchap.snake.Screens.GameScreen;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Utility.ScreenManager;

import java.util.ArrayList;

public class Snake {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public Snake(int posX, int posY) {
        _bodyParts = new ArrayList<>();
        _bodyParts.add(new BodyPart(posX, posY, ColorManager.getSnakeHeadColor()));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void render() {
        for (BodyPart part : _bodyParts) {
            part.render();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void move() {
        BodyPart tail = _bodyParts.get(_bodyParts.size() - 1);
        _lastPosX = tail.getPosX();
        _lastPosY = tail.getPosY();

        BodyPart head = _bodyParts.get(0);
        int lastHeadPosX = head.getPosX();
        int lastHeadPosY = head.getPosY();
        head.setPos(lastHeadPosX + _dirX * GlobalVars.UNIT_SIZE, lastHeadPosY + _dirY * GlobalVars.UNIT_SIZE);

        if (tail != head) {
            _bodyParts.remove(_bodyParts.size() - 1);
            tail.setPos(lastHeadPosX, lastHeadPosY);
            _bodyParts.add(1, tail);
        }

        _dirChanged = false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean checkCollisions() {
        int minX = GlobalVars.LEFT + GlobalVars.UNIT_SIZE;
        int maxX = GlobalVars.RIGHT - (2 * GlobalVars.UNIT_SIZE);
        int minY = GlobalVars.BOTTOM + GlobalVars.UNIT_SIZE;
        int maxY = GlobalVars.GAME_GRID_TOP - (2 * GlobalVars.UNIT_SIZE);

        BodyPart head = _bodyParts.get(0);
        if (head.getPosX() < minX || head.getPosX() > maxX) {
            return true;
        }
        if (head.getPosY() < minY || head.getPosY() > maxY) {
            return true;
        }

        for (int i = 1; i < _bodyParts.size(); i++) {
            BodyPart body = _bodyParts.get(i);
            if (body.getPosX() == head.getPosX() && body.getPosY() == head.getPosY()) {
                return true;
            }
        }

        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean tryEat() {
        BodyPart head = _bodyParts.get(0);
        GameScreen gameScreen = (GameScreen) ScreenManager.getInstance().getScreen();
        Food food = gameScreen.getFood();
        ArrayList<Vector2> freeSpaces = gameScreen.getFreeSpaces();

        if (head.getPosX() == food.getPosX() && head.getPosY() == food.getPosY()) {
            BodyPart newPart = new BodyPart(_lastPosX, _lastPosY, ColorManager.getSnakeBodyColor());
            _bodyParts.add(newPart);
            food.spawnFood(freeSpaces);
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

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void setDir(int dirX, int dirY) {
        if (_dirChanged) {
            // Change only once per frame
            return;
        }

        _dirX = dirX;
        _dirY = dirY;
        _dirChanged = true;
    }

    public int getDirX() { return _dirX; }
    public int getDirY() { return _dirY; }
    public int getHeadPosX() { return _bodyParts.get(0).getPosX(); }
    public int getHeadPosY() { return _bodyParts.get(0).getPosY(); }
    public int getLastPosX() { return _lastPosX; }
    public int getLastPosY() { return _lastPosY; }
    public ArrayList<BodyPart> getBodyParts() { return _bodyParts; }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private final ArrayList<BodyPart> _bodyParts;

    private int _dirX;
    private int _dirY;
    private int _lastPosX;
    private int _lastPosY;
    private boolean _dirChanged;
}
