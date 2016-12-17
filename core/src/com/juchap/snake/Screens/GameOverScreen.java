package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.juchap.snake.Utility.FontManager;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Utility.HighScoreManager;
import com.juchap.snake.Utility.ScreenEnum;
import com.juchap.snake.Utility.ScreenManager;


public class GameOverScreen extends AbstractScreen {

    public GameOverScreen(Integer score) {
        super();
        Gdx.input.setInputProcessor(this);

        borders = new ShapeRenderer();
        batch = new SpriteBatch();

        leftBorderX = GlobalVars.GRID_OFFSET_X;
        rightBorderX = GlobalVars.GRID_OFFSET_X + GlobalVars.GRID_WIDTH - GlobalVars.UNIT_SIZE;
        bottomBorderY = GlobalVars.GRID_OFFSET_Y;
        topBorderY = GlobalVars.GRID_OFFSET_Y + GlobalVars.GRID_HEIGHT - GlobalVars.UNIT_SIZE;

        HighScoreManager.addScore(score);
        this.score = score;
        this.best = HighScoreManager.getBest();
    }

    @Override
    public void buildStage() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        drawBorders();
        drawText();
    }

    @Override
    public void dispose() {
        super.dispose();
        borders.dispose();
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK) ) {
            ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
        }
        return false;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
        return true;
    }

    private void drawBorders() {
        // Draw screen borders
        borders.begin(ShapeRenderer.ShapeType.Filled);
        borders.setColor(Color.WHITE);
        borders.rect(leftBorderX, bottomBorderY, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT);
        borders.rect(leftBorderX, bottomBorderY, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        borders.rect(leftBorderX, topBorderY, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        borders.rect(rightBorderX, bottomBorderY, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT);
        borders.end();
    }

    private void drawText() {
        BitmapFont fontLarge = FontManager.fontLarge(Color.WHITE);
        GlyphLayout gameOverText = new GlyphLayout();
        gameOverText.setText(fontLarge, "GAME OVER");
        float gameOverY = (2.0f * Gdx.graphics.getHeight() / 3.0f);

        batch.begin();
        fontLarge.draw(batch, gameOverText, (Gdx.graphics.getWidth() - gameOverText.width) / 2, gameOverY + (gameOverText.height / 2));
        batch.end();

        BitmapFont fontMedium = FontManager.fontMedium(Color.WHITE);
        GlyphLayout scoreText = new GlyphLayout();
        scoreText.setText(fontMedium, "SCORE " + score);
        float ScoreY = gameOverY - 4 * GlobalVars.UNIT_SIZE - scoreText.height;
        GlyphLayout bestScoreText = new GlyphLayout();
        bestScoreText.setText(fontMedium, "BEST " + best);
        float bestScoreY = ScoreY - GlobalVars.UNIT_SIZE - bestScoreText.height;

        batch.begin();
        fontMedium.draw(batch, scoreText, (Gdx.graphics.getWidth() - scoreText.width) / 2, ScoreY);
        fontMedium.draw(batch, bestScoreText, (Gdx.graphics.getWidth() - bestScoreText.width) / 2, bestScoreY);
        batch.end();

        BitmapFont fontSmall = FontManager.fontSmall(Color.WHITE);
        GlyphLayout continueText = new GlyphLayout();
        continueText.setText(fontSmall, "TOUCH TO RETURN TO MENU");

        batch.begin();
        fontSmall.draw(batch, continueText, (Gdx.graphics.getWidth() - continueText.width) / 2, GlobalVars.GRID_OFFSET_Y + 2 * GlobalVars.UNIT_SIZE + continueText.height);
        batch.end();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private ShapeRenderer borders;
    private SpriteBatch batch;

    private int leftBorderX;
    private int rightBorderX;
    private int topBorderY;
    private int bottomBorderY;

    private int score;
    private int best;
}
