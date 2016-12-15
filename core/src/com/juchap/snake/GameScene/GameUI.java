package com.juchap.snake.GameScene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.juchap.snake.Utility.FontManager;


public class GameUI {

    public GameUI(int offsetX, int offsetY, int borderSize, Color borderColor) {
        offsetX_ = offsetX;
        offsetY_ = offsetY;
        borderSize_ = borderSize;
        borderColor_ = borderColor;

        gameHeight_ = (int)(Math.floor((Gdx.graphics.getHeight() / borderSize) * (5.0/6.0)) * borderSize);
        width_ = Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() % borderSize);
        height_ = Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() % borderSize);
        score_ = 0;
        border_ = new ShapeRenderer();
        batch_ = new SpriteBatch();

        font_ = FontManager.audimatMonoB();
    }

    public void render() {
        // Draw screen borders
        border_.begin(ShapeRenderer.ShapeType.Filled);
        border_.setColor(borderColor_.r, borderColor_.g, borderColor_.b, borderColor_.a);
        // Screen Borders
        border_.rect(offsetX_, offsetY_, borderSize_, height_);
        border_.rect(offsetX_, offsetY_, width_, borderSize_);
        border_.rect(offsetX_, height_ - borderSize_+ offsetY_, width_, borderSize_);
        border_.rect(width_ - borderSize_ + offsetX_, offsetY_, borderSize_, height_);
        // Score Divider
        border_.rect(offsetX_, offsetY_ + gameHeight_, width_, borderSize_);
        border_.end();

        // Prepare score text GlyphLayout
        GlyphLayout scoreText = new GlyphLayout();
        scoreText.setText(font_, "SCORE ");
        GlyphLayout score = new GlyphLayout();
        score.setText(font_, String.valueOf(score_));

        // Draw score text
        batch_.begin();
        font_.draw(batch_, scoreText, (width_ - scoreText.width - score.width) / 2, ((height_ + gameHeight_) / 2) + scoreText.height / 2);
        font_.draw(batch_, String.valueOf(score_), (width_ - scoreText.width - score.width) / 2 + scoreText.width, ((height_ + gameHeight_) / 2) + score.height / 2);
        batch_.end();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public int getGameHeight() {return gameHeight_; }

    public void addScore() { score_++; }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private ShapeRenderer border_;

    private int width_;
    private int height_;
    private int offsetX_;
    private int offsetY_;
    private int gameHeight_;
    private int borderSize_;
    private Color borderColor_;

    private int score_;
    private BitmapFont font_;
    SpriteBatch batch_;
}
