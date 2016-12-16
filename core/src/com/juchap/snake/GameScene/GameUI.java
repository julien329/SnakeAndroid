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
        score_ = 0;
        border_ = new ShapeRenderer();
        batch_ = new SpriteBatch();
        font_ = FontManager.audimatMonoB(64);
    }

    public void render() {
        // Draw screen borders
        border_.begin(ShapeRenderer.ShapeType.Filled);
        border_.setColor(Color.WHITE);
        // Screen Borders
        border_.rect(GlobalVars.GRID_OFFSET_X, GlobalVars.GRID_OFFSET_Y, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT);
        border_.rect(GlobalVars.GRID_OFFSET_X, GlobalVars.GRID_OFFSET_Y, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        border_.rect(GlobalVars.GRID_OFFSET_X, GlobalVars.GRID_OFFSET_Y + GlobalVars.GRID_HEIGHT - GlobalVars.UNIT_SIZE, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        border_.rect(GlobalVars.GRID_OFFSET_X + GlobalVars.GRID_WIDTH - GlobalVars.UNIT_SIZE, GlobalVars.GRID_OFFSET_Y, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT);
        // Score Divider
        border_.rect(GlobalVars.GRID_OFFSET_X, GlobalVars.GRID_OFFSET_Y + GlobalVars.GAME_GRID_HEIGHT - GlobalVars.UNIT_SIZE, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        border_.end();

        // Prepare score text GlyphLayout
        GlyphLayout scoreText = new GlyphLayout();
        scoreText.setText(font_, "SCORE ");
        GlyphLayout score = new GlyphLayout();
        score.setText(font_, String.valueOf(score_));

        // Draw score text
        batch_.begin();
        font_.draw(batch_, scoreText, (GlobalVars.GRID_WIDTH - scoreText.width - score.width) / 2, ((GlobalVars.GRID_HEIGHT + GlobalVars.GAME_GRID_HEIGHT - GlobalVars.UNIT_SIZE) / 2) + scoreText.height / 2);
        font_.draw(batch_, String.valueOf(score_), (GlobalVars.GRID_WIDTH - scoreText.width - score.width) / 2 + scoreText.width, ((GlobalVars.GRID_HEIGHT + GlobalVars.GAME_GRID_HEIGHT - GlobalVars.UNIT_SIZE) / 2) + score.height / 2);
        batch_.end();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void addScore() { score_++; }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private ShapeRenderer border_;
    private int score_;
    private BitmapFont font_;
    private SpriteBatch batch_;
}
