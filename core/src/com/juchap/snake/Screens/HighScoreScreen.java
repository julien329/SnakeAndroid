package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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


public class HighScoreScreen extends AbstractScreen {

    public HighScoreScreen() {
        super();
        Gdx.input.setInputProcessor(this);

        borders = new ShapeRenderer();
        batch = new SpriteBatch();

        leftBorderX = GlobalVars.GRID_OFFSET_X;
        rightBorderX = GlobalVars.GRID_OFFSET_X + GlobalVars.GRID_WIDTH - GlobalVars.UNIT_SIZE;
        bottomBorderY = GlobalVars.GRID_OFFSET_Y;
        topBorderY = GlobalVars.GRID_OFFSET_Y + GlobalVars.GRID_HEIGHT - GlobalVars.UNIT_SIZE;
    }

    @Override
    public void buildStage() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        drawText();
        drawBorders();
    }

    @Override
    public void dispose() {
        super.dispose();
        borders.dispose();
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
        BitmapFont fontTitle = FontManager.fontCustom(Color.WHITE, 54);
        GlyphLayout highScoreText = new GlyphLayout();
        highScoreText.setText(fontTitle, "HIGH SCORES");
        float textPosY = ( 4 * Gdx.graphics.getHeight()) / 5;

        batch.begin();
        fontTitle.draw(batch, highScoreText, (Gdx.graphics.getWidth() - highScoreText.width) / 2, textPosY);
        batch.end();

        textPosY -= (6 * GlobalVars.UNIT_SIZE);
        BitmapFont fontScores = FontManager.fontMedium(Color.WHITE);

        for(int i = 0; i < TABLE_SIZE; i++) {
            GlyphLayout score = new GlyphLayout();
            String scoreText = formatScore(HighScoreManager.getScore(i));
            score.setText(fontScores, RANKS[i] + "    " + scoreText);
            textPosY -= (2 * GlobalVars.UNIT_SIZE);

            batch.begin();
            fontScores.draw(batch, score, (Gdx.graphics.getWidth() - score.width) / 2, textPosY);
            batch.end();
        }

        BitmapFont fontSmall = FontManager.fontSmall(Color.WHITE);
        GlyphLayout continueText = new GlyphLayout();
        continueText.setText(fontSmall, "TOUCH TO RETURN TO MENU");

        batch.begin();
        fontSmall.draw(batch, continueText, (Gdx.graphics.getWidth() - continueText.width) / 2, GlobalVars.GRID_OFFSET_Y + 2 * GlobalVars.UNIT_SIZE + continueText.height);
        batch.end();
    }

    private String formatScore(int score) {
        String scoreText = String.valueOf(score);
        String zeros = "";

        for(int i = 0; i < 4 - scoreText.length(); i++) {
            zeros += "0";
        }

        return zeros + scoreText;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final int TABLE_SIZE = 9;
    private static final String[] RANKS = {"1ST", "2ND", "3RD", "4TH", "5TH", "6TH", "7TH", "8TH", "9TH"};

    private ShapeRenderer borders;
    private SpriteBatch batch;

    private int leftBorderX;
    private int rightBorderX;
    private int topBorderY;
    private int bottomBorderY;
}
