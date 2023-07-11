package com.juchap.snake.GameScene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.juchap.snake.Managers.ColorManager;
import com.juchap.snake.Utility.GlobalVars;

public class ControlButton extends Actor {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public ControlButton(ShapeRenderer renderer, Snake snake, int direction, float[] vertices) {
        _renderer = renderer;
        _snake = snake;
        _direction = direction;
        _vertices = vertices;
        _triangle = new Polygon(vertices);
        _backColor = ColorManager.getBackAltColor();

        calculateIconVertices();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        _renderer.begin(ShapeRenderer.ShapeType.Filled);
        _renderer.setColor(_backColor);
        _renderer.triangle(_iconVertices[0], _iconVertices[1], _iconVertices[2], _iconVertices[3], _iconVertices[4], _iconVertices[5]);
        _renderer.end();

        Gdx.gl.glLineWidth(5.f);
        _renderer.begin(ShapeRenderer.ShapeType.Line);
        _renderer.setColor(_backColor);
        _renderer.line(_vertices[0], _vertices[1], _vertices[2], _vertices[3]);
        _renderer.line(_vertices[2], _vertices[3], _vertices[4], _vertices[5]);
        _renderer.line(_vertices[4], _vertices[5], _vertices[0], _vertices[1]);
        _renderer.end();
        Gdx.gl.glLineWidth(1.f);

        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void eventTouchDown(Vector2 inputPos) {
        if (_triangle.contains(inputPos)) {
            _backColor = ColorManager.getFrontColor();

            switch (_direction) {
                case LEFT:
                    if (_snake.getDirX() == 0) {
                        _snake.setDir(-1, 0);
                    }
                    break;
                case RIGHT:
                    if (_snake.getDirX() == 0) {
                        _snake.setDir(1, 0);
                    }
                    break;
                case UP:
                    if (_snake.getDirY() == 0) {
                        _snake.setDir(0, 1);
                    }
                    break;
                case DOWN:
                    if (_snake.getDirY() == 0) {
                        _snake.setDir(0, -1);
                    }
                    break;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void eventTouchUp() {
        _backColor = ColorManager.getBackAltColor();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void calculateIconVertices() {
        Vector2 vertexA, vertexB, vertexC, center;

        switch (_direction) {
            case LEFT:
                center = new Vector2((_vertices[2] + _vertices[4]) / 2.f, (_vertices[1] + _vertices[3]) / 2.f);
                vertexA = new Vector2(center.x + GlobalVars.UNIT_SIZE, center.y + GlobalVars.UNIT_SIZE);
                vertexB = new Vector2(center.x + GlobalVars.UNIT_SIZE, center.y - GlobalVars.UNIT_SIZE);
                vertexC = new Vector2(center.x - GlobalVars.UNIT_SIZE, center.y);
                _iconVertices = new float[]{ vertexA.x, vertexA.y, vertexB.x, vertexB.y, vertexC.x, vertexC.y };
                break;
            case RIGHT:
                center = new Vector2((_vertices[2] + _vertices[4]) / 2.f, (_vertices[1] + _vertices[3]) / 2.f);
                vertexA = new Vector2(center.x - GlobalVars.UNIT_SIZE, center.y - GlobalVars.UNIT_SIZE);
                vertexB = new Vector2(center.x - GlobalVars.UNIT_SIZE, center.y + GlobalVars.UNIT_SIZE);
                vertexC = new Vector2(center.x + GlobalVars.UNIT_SIZE, center.y);
                _iconVertices = new float[]{ vertexA.x, vertexA.y, vertexB.x, vertexB.y, vertexC.x, vertexC.y };
                break;
            case UP:
                center = new Vector2((_vertices[0] + _vertices[2]) / 2.f, (_vertices[3] + _vertices[5]) / 2.f);
                vertexA = new Vector2(center.x + GlobalVars.UNIT_SIZE, center.y - GlobalVars.UNIT_SIZE);
                vertexB = new Vector2(center.x - GlobalVars.UNIT_SIZE, center.y - GlobalVars.UNIT_SIZE);
                vertexC = new Vector2(center.x, center.y + GlobalVars.UNIT_SIZE);
                _iconVertices = new float[]{ vertexA.x, vertexA.y, vertexB.x, vertexB.y, vertexC.x, vertexC.y };
                break;
            case DOWN:
                center = new Vector2((_vertices[0] + _vertices[2]) / 2.f, (_vertices[3] + _vertices[5]) / 2.f);
                vertexA = new Vector2(center.x - GlobalVars.UNIT_SIZE, center.y + GlobalVars.UNIT_SIZE);
                vertexB = new Vector2(center.x + GlobalVars.UNIT_SIZE, center.y + GlobalVars.UNIT_SIZE);
                vertexC = new Vector2(center.x, center.y - GlobalVars.UNIT_SIZE);
                _iconVertices = new float[]{ vertexA.x, vertexA.y, vertexB.x, vertexB.y, vertexC.x, vertexC.y };
                break;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    private final ShapeRenderer _renderer;
    private final Polygon _triangle;
    private final Snake _snake;
    private Color _backColor;

    private final float[] _vertices;
    private float[] _iconVertices;
    private final int _direction;
}