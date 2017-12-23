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


public class ControlButton extends Actor {

    public ControlButton(ShapeRenderer renderer, Snake snake, int direction, float[] vertices) {
        this.renderer = renderer;
        this.snake = snake;
        this.direction = direction;
        this.vertices = vertices;
        this.triangle = new Polygon(vertices);
        this.backColor = ColorManager.getBackColor();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(backColor);
        renderer.triangle(vertices[0], vertices[1], vertices[2], vertices[3], vertices[4], vertices[5]);
        renderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
        Gdx.gl.glLineWidth(1.f);

        batch.begin();
    }

    public void eventTouchDown(Vector2 inputPos) {
        if (triangle.contains(inputPos)) {
            backColor = ColorManager.getBackAltColor();

            switch (direction) {
                case LEFT:
                    if (snake.getDirX() == 0) {
                        snake.setDir(-1, 0);
                    }
                    break;
                case RIGHT:
                    if (snake.getDirX() == 0) {
                        snake.setDir(1, 0);
                    }
                    break;
                case UP:
                    if (snake.getDirY() == 0) {
                        snake.setDir(0, 1);
                    }
                    break;
                case DOWN:
                    if (snake.getDirY() == 0) {
                        snake.setDir(0, -1);
                    }
                    break;
            }
        }
    }

    public void eventTouchUp() {
        backColor = ColorManager.getBackColor();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    private ShapeRenderer renderer;
    private Color backColor;
    private Polygon triangle;
    private Snake snake;

    private float[] vertices;
    private int direction;
}