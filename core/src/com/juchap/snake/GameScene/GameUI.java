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
        scoreTextY = (GlobalVars.GRID_HEIGHT + GlobalVars.GAME_GRID_HEIGHT - GlobalVars.UNIT_SIZE) / 2;
        scorePointsX = GlobalVars.GRID_OFFSET_X + GlobalVars.GRID_WIDTH - (GlobalVars.GRID_HEIGHT - GlobalVars.GAME_GRID_HEIGHT) - (2 * GlobalVars.UNIT_SIZE);

        initButtonSkin();
        pauseButton = new TextButton("||", buttonSkin);
        pauseButton.setPosition(scoreDividerX + GlobalVars.UNIT_SIZE, topGameBorderY + GlobalVars.UNIT_SIZE);
        pauseButton.addListener(new pauseButtonListener());
        pauseButton.getLabel().setFontScale(0.75f, 0.75f);
        ScreenManager.getInstance().getScreen().addActor(pauseButton);
    }

    private void initButtonSkin() {
        // Init
        BitmapFont font = FontManager.fontLarge(Color.WHITE);
        buttonSkin = new Skin();
        buttonSkin.add("default", font);

        // Create texture
        Pixmap pixmap = new Pixmap(rightBorderX - scoreDividerX - GlobalVars.UNIT_SIZE, topBorderY - topGameBorderY - GlobalVars.UNIT_SIZE, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        buttonSkin.add("background",new Texture(pixmap));
        pixmap.dispose();

        // Create button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = buttonSkin.newDrawable("background", Color.BLACK);
        textButtonStyle.down = buttonSkin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = buttonSkin.newDrawable("background", Color.BLACK);
        textButtonStyle.over = buttonSkin.newDrawable("background", Color.BLACK);
        textButtonStyle.font = buttonSkin.getFont("default");
        textButtonStyle.fontColor = Color.WHITE;
        buttonSkin.add("default", textButtonStyle);
    }

    public void render() {
        // Draw screen borders
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

        // Prepare score text GlyphLayout
        BitmapFont font = FontManager.fontCustom(Color.WHITE, 48);
        GlyphLayout scoreText = new GlyphLayout();
        scoreText.setText(font, "SCORE");
        GlyphLayout scorePoints = new GlyphLayout();
        scorePoints.setText(font, String.valueOf(score));
        GlyphLayout pause = new GlyphLayout();
        pause.setText(font, "||");

        // Draw score text
        batch.begin();
        font.draw(batch, scoreText, leftBorderX + (2 * GlobalVars.UNIT_SIZE), scoreTextY + (scoreText.height / 2));
        font.draw(batch, scorePoints, scorePointsX - scorePoints.width, scoreTextY + (scorePoints.height / 2));
        batch.end();
    }

    public void renderPause() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        border.begin(ShapeRenderer.ShapeType.Filled);
        border.setColor(0, 0, 0, 0.75f);
        border.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        border.end();

        BitmapFont font = FontManager.fontLarge(Color.WHITE);
        GlyphLayout pauseText = new GlyphLayout();
        pauseText.setText(font, "PAUSED");

        batch.begin();
        font.draw(batch, pauseText, (Gdx.graphics.getWidth() / 2) - (pauseText.width / 2), (Gdx.graphics.getHeight() / 2) + (pauseText.height / 2));
        batch.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
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

    private ShapeRenderer border;
    private int score;
    private SpriteBatch batch;
    private TextButton pauseButton;
    private Skin buttonSkin;

    private int leftBorderX;
    private int rightBorderX;
    private int topBorderY;
    private int bottomBorderY;
    private int topGameBorderY;
    private int scoreDividerX;
    private int scoreTextY;
    private int scorePointsX;
}
