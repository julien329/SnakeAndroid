package com.juchap.snake.GameScene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.juchap.snake.Screens.GameScreen;
import com.juchap.snake.Utility.FontManager;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Utility.ScreenManager;


public class GameUI {

    public GameUI() {
        score = 0;
        border = new ShapeRenderer();
        batch = new SpriteBatch();

        leftBorderX = GlobalVars.GRID_OFFSET_X;
        rightBorderX = GlobalVars.GRID_OFFSET_X + GlobalVars.GRID_WIDTH - GlobalVars.UNIT_SIZE;
        bottomBorderY = GlobalVars.GRID_OFFSET_Y;
        topBorderY = GlobalVars.GRID_OFFSET_Y + GlobalVars.GRID_HEIGHT - GlobalVars.UNIT_SIZE;
        topGameBorderY = GlobalVars.GRID_OFFSET_Y + GlobalVars.GAME_GRID_HEIGHT - GlobalVars.UNIT_SIZE;
        scoreDividerX = GlobalVars.GRID_OFFSET_X + GlobalVars.GRID_WIDTH - (GlobalVars.GRID_HEIGHT - GlobalVars.GAME_GRID_HEIGHT) - GlobalVars.UNIT_SIZE;

        initButtonSkin();
        pauseButton = new TextButton(PAUSE_SYMBOL, buttonSkin);
        pauseButton.setPosition(scoreDividerX + GlobalVars.UNIT_SIZE, topGameBorderY + GlobalVars.UNIT_SIZE);
        pauseButton.addListener(new pauseButtonListener());
        pauseButton.getLabel().setFontScale(0.75f, 0.75f);
        ScreenManager.getInstance().getScreen().addActor(pauseButton);

        initGlyphs();
    }

    private void initButtonSkin() {
        // Init
        BitmapFont font = FontManager.fontLarge(Color.WHITE);
        buttonSkin = new Skin();
        buttonSkin.add(DEFAULT, font);

        // Create texture
        Pixmap pixmap = new Pixmap(rightBorderX - scoreDividerX - GlobalVars.UNIT_SIZE, topBorderY - topGameBorderY - GlobalVars.UNIT_SIZE, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        buttonSkin.add(BACKGROUND, new Texture(pixmap));
        pixmap.dispose();

        // Create button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = buttonSkin.newDrawable(BACKGROUND, Color.BLACK);
        textButtonStyle.down = buttonSkin.newDrawable(BACKGROUND, Color.DARK_GRAY);
        textButtonStyle.checked = buttonSkin.newDrawable(BACKGROUND, Color.BLACK);
        textButtonStyle.over = buttonSkin.newDrawable(BACKGROUND, Color.BLACK);
        textButtonStyle.font = buttonSkin.getFont(DEFAULT);
        textButtonStyle.fontColor = Color.WHITE;
        buttonSkin.add(DEFAULT, textButtonStyle);
    }

    public void render() {
        border.begin(ShapeRenderer.ShapeType.Filled);
        border.setColor(Color.WHITE);
        // Screen Borders
        border.rect(leftBorderX, bottomBorderY, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT);
        border.rect(leftBorderX, bottomBorderY, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        border.rect(leftBorderX, topBorderY, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        border.rect(rightBorderX, bottomBorderY, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT);
        // Score Divider
        border.rect(leftBorderX, topGameBorderY, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        border.rect(scoreDividerX, topGameBorderY, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT - GlobalVars.GAME_GRID_HEIGHT);
        border.end();

        // Draw score text
        BitmapFont font = FontManager.fontCustom(Color.WHITE, 48);
        scorePoints.setText(font, String.valueOf(score));
        batch.begin();
        font.draw(batch, scoreText, scoreTextX, scoreY);
        font.draw(batch, scorePoints, scorePointsX - scorePoints.width, scoreY);
        batch.end();
    }

    public void renderPause() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        border.begin(ShapeRenderer.ShapeType.Filled);
        border.setColor(0, 0, 0, 0.75f);
        border.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        border.end();

        batch.begin();
        BitmapFont fontLarge = FontManager.fontLarge(Color.WHITE);
        fontLarge.draw(batch, pauseText, pauseTextX, pauseTextY);

        BitmapFont fontSmall = FontManager.fontSmall(Color.WHITE);
        fontSmall.draw(batch, continueText, continueTextX, continueTextY);
        batch.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void initGlyphs() {
        BitmapFont fontLarge = FontManager.fontLarge(Color.WHITE);
        pauseText = new GlyphLayout();
        pauseText.setText(fontLarge, PAUSED);
        pauseTextX = (int)(Gdx.graphics.getWidth() - pauseText.width) / 2;
        pauseTextY = (int)(Gdx.graphics.getHeight() + pauseText.height) / 2;

        BitmapFont fontSmall = FontManager.fontSmall(Color.WHITE);
        continueText = new GlyphLayout();
        continueText.setText(fontSmall, CONTINUE);
        continueTextX = (int)(Gdx.graphics.getWidth() - continueText.width) / 2;
        continueTextY = pauseTextY - (int)pauseText.height - GlobalVars.UNIT_SIZE;

        BitmapFont fontCustom = FontManager.fontCustom(Color.WHITE, 48);
        scoreText = new GlyphLayout();
        scoreText.setText(fontCustom, SCORE);
        scorePoints = new GlyphLayout();
        scorePoints.setText(fontCustom, SCORE_INIT);
        scoreTextX = leftBorderX + (2 * GlobalVars.UNIT_SIZE);
        scorePointsX = GlobalVars.GRID_OFFSET_X + GlobalVars.GRID_WIDTH - (GlobalVars.GRID_HEIGHT - GlobalVars.GAME_GRID_HEIGHT) - (2 * GlobalVars.UNIT_SIZE);
        scoreY = (int)(GlobalVars.GRID_HEIGHT + GlobalVars.GAME_GRID_HEIGHT - GlobalVars.UNIT_SIZE + scoreText.height) / 2 ;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// CLASSES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private class pauseButtonListener extends InputListener {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            return true;
        };
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if(x >= 0 && x < pauseButton.getWidth() && y >= 0 && y < pauseButton.getHeight())
                ((GameScreen) ScreenManager.getInstance().getScreen()).pauseGame();
        };
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void addScore() { score++; }
    public int getScore() { return score; }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String PAUSE_SYMBOL = "||";
    private static final String DEFAULT = "default";
    private static final String BACKGROUND = "background";
    private static final String PAUSED = "PAUSED";
    private static final String CONTINUE = "TOUCH TO CONTINUE";
    private static final String SCORE = "SCORE";
    private static final String SCORE_INIT = "0";

    private ShapeRenderer border;
    private SpriteBatch batch;
    private TextButton pauseButton;
    private Skin buttonSkin;
    private GlyphLayout pauseText;
    private GlyphLayout continueText;
    private GlyphLayout scoreText;
    private GlyphLayout scorePoints;

    private int score;
    private int leftBorderX;
    private int rightBorderX;
    private int topBorderY;
    private int bottomBorderY;
    private int topGameBorderY;
    private int scoreDividerX;
    private int scoreY;
    private int scoreTextX;
    private int scorePointsX;
    private int pauseTextX;
    private int pauseTextY;
    private int continueTextX;
    private int continueTextY;
}
