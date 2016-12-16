package com.juchap.snake.GameScene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.juchap.snake.Utility.FontManager;
import com.juchap.snake.Utility.GlobalVars;


public class GameUI {

    public GameUI() {
        score = 0;
        border = new ShapeRenderer();
        batch = new SpriteBatch();
        font = FontManager.audimatMonoB(64);
    }

    public void render() {
        // Draw screen borders
        border.begin(ShapeRenderer.ShapeType.Filled);
        border.setColor(Color.WHITE);
        // Screen Borders
        border.rect(GlobalVars.GRID_OFFSET_X, GlobalVars.GRID_OFFSET_Y, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT);
        border.rect(GlobalVars.GRID_OFFSET_X, GlobalVars.GRID_OFFSET_Y, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        border.rect(GlobalVars.GRID_OFFSET_X, GlobalVars.GRID_OFFSET_Y + GlobalVars.GRID_HEIGHT - GlobalVars.UNIT_SIZE, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        border.rect(GlobalVars.GRID_OFFSET_X + GlobalVars.GRID_WIDTH - GlobalVars.UNIT_SIZE, GlobalVars.GRID_OFFSET_Y, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT);
        // Score Divider
        border.rect(GlobalVars.GRID_OFFSET_X, GlobalVars.GRID_OFFSET_Y + GlobalVars.GAME_GRID_HEIGHT - GlobalVars.UNIT_SIZE, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        border.end();

        // Prepare score text GlyphLayout
        GlyphLayout scoreText = new GlyphLayout();
        scoreText.setText(font, "SCORE ");
        GlyphLayout score = new GlyphLayout();
        score.setText(font, String.valueOf(score));

        // Draw score text
        batch.begin();
        font.draw(batch, scoreText, (GlobalVars.GRID_WIDTH - scoreText.width - score.width) / 2, ((GlobalVars.GRID_HEIGHT + GlobalVars.GAME_GRID_HEIGHT - GlobalVars.UNIT_SIZE) / 2) + scoreText.height / 2);
        font.draw(batch, String.valueOf(score), (GlobalVars.GRID_WIDTH - scoreText.width - score.width) / 2 + scoreText.width, ((GlobalVars.GRID_HEIGHT + GlobalVars.GAME_GRID_HEIGHT - GlobalVars.UNIT_SIZE) / 2) + score.height / 2);
        batch.end();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void addScore() { score++; }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private ShapeRenderer border;
    private int score;
    private BitmapFont font;
    private SpriteBatch batch;
}
