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
import com.juchap.snake.Managers.ColorManager;
import com.juchap.snake.Screens.GameScreen;
import com.juchap.snake.Managers.FontManager;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Utility.ScreenManager;

public class GameUI {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public GameUI(ShapeRenderer uiRenderer, SpriteBatch spriteBatch) {
        _score = 0;
        _uiRenderer = uiRenderer;
        _batch = spriteBatch;

        _leftBorderX = GlobalVars.LEFT;
        _rightBorderX = GlobalVars.RIGHT - GlobalVars.UNIT_SIZE;
        _topBorderY = GlobalVars.TOP - GlobalVars.UNIT_SIZE;
        _topGameBorderY = GlobalVars.GAME_GRID_TOP - GlobalVars.UNIT_SIZE;
        _scoreDividerX = GlobalVars.RIGHT - (GlobalVars.GRID_HEIGHT - GlobalVars.GAME_GRID_HEIGHT) - GlobalVars.UNIT_SIZE;

        initButtonSkin();
        _pauseButton = new TextButton(PAUSE_SYMBOL, _buttonSkin);
        _pauseButton.setPosition(_scoreDividerX + GlobalVars.UNIT_SIZE, _topGameBorderY + GlobalVars.UNIT_SIZE);
        _pauseButton.addListener(new pauseButtonListener());
        _pauseButton.getLabel().setFontScale(0.75f, 0.75f);
        ScreenManager.getInstance().getScreen().addActor(_pauseButton);

        initGlyphs();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void initButtonSkin() {
        // Init
        BitmapFont font = FontManager.fontLarge(ColorManager.getFrontColor());
        _buttonSkin = new Skin();
        _buttonSkin.add(DEFAULT, font);

        // Create texture
        Pixmap pixmap = new Pixmap(_rightBorderX - _scoreDividerX - GlobalVars.UNIT_SIZE, _topBorderY - _topGameBorderY - GlobalVars.UNIT_SIZE, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        _buttonSkin.add(BACKGROUND, new Texture(pixmap));
        pixmap.dispose();

        // Create button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = _buttonSkin.newDrawable(BACKGROUND, ColorManager.getBackColor());
        textButtonStyle.down = _buttonSkin.newDrawable(BACKGROUND, ColorManager.getBackAltColor());
        textButtonStyle.checked = _buttonSkin.newDrawable(BACKGROUND, ColorManager.getBackColor());
        textButtonStyle.over = _buttonSkin.newDrawable(BACKGROUND, ColorManager.getBackColor());
        textButtonStyle.font = _buttonSkin.getFont(DEFAULT);
        textButtonStyle.fontColor = ColorManager.getFrontColor();
        _buttonSkin.add(DEFAULT, textButtonStyle);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void render() {
        _uiRenderer.begin(ShapeRenderer.ShapeType.Filled);
        _uiRenderer.setColor(ColorManager.getFrontColor());
        // Score Divider
        _uiRenderer.rect(_leftBorderX, _topGameBorderY, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        _uiRenderer.rect(_scoreDividerX, _topGameBorderY, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT - GlobalVars.GAME_GRID_HEIGHT);
        _uiRenderer.end();

        // Draw score text
        BitmapFont font = FontManager.fontCustom(ColorManager.getFrontColor(), 48);
        _scorePoints.setText(font, String.valueOf(_score));
        _batch.begin();
        font.draw(_batch, _scoreText, _scoreTextX, _scoreY);
        font.draw(_batch, _scorePoints, _scorePointsX - _scorePoints.width, _scoreY);
        _batch.end();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void renderPause() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        _uiRenderer.begin(ShapeRenderer.ShapeType.Filled);
        _uiRenderer.setColor(0, 0, 0, 0.75f);
        _uiRenderer.rect(GlobalVars.LEFT, GlobalVars.BOTTOM, GlobalVars.GRID_WIDTH, GlobalVars.GRID_HEIGHT);
        _uiRenderer.end();

        _batch.begin();
        BitmapFont fontLarge = FontManager.fontLarge(Color.WHITE);
        fontLarge.draw(_batch, _pauseText, _pauseTextX, _pauseTextY);

        BitmapFont fontSmall = FontManager.fontSmall(Color.WHITE);
        fontSmall.draw(_batch, _continueText, _continueTextX, _continueTextY);
        _batch.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void initGlyphs() {
        BitmapFont fontLarge = FontManager.fontLarge(Color.WHITE);
        _pauseText = new GlyphLayout();
        _pauseText.setText(fontLarge, PAUSED);
        _pauseTextX = GlobalVars.CENTER_X - (_pauseText.width / 2.f);
        _pauseTextY = GlobalVars.CENTER_Y + (_pauseText.height / 2.f);

        BitmapFont fontSmall = FontManager.fontSmall(Color.WHITE);
        _continueText = new GlyphLayout();
        _continueText.setText(fontSmall, CONTINUE);
        _continueTextX = GlobalVars.CENTER_X - (_continueText.width / 2.f);
        _continueTextY = _pauseTextY - _pauseText.height - GlobalVars.UNIT_SIZE;

        BitmapFont fontCustom = FontManager.fontCustom(ColorManager.getFrontColor(), 48);
        _scoreText = new GlyphLayout();
        _scoreText.setText(fontCustom, SCORE);
        _scorePoints = new GlyphLayout();
        _scorePoints.setText(fontCustom, SCORE_INIT);
        _scoreTextX = _leftBorderX + (2 * GlobalVars.UNIT_SIZE);
        _scorePointsX = GlobalVars.RIGHT - (GlobalVars.GRID_HEIGHT - GlobalVars.GAME_GRID_HEIGHT) - (2 * GlobalVars.UNIT_SIZE);
        _scoreY = (GlobalVars.TOP + GlobalVars.GAME_GRID_TOP - GlobalVars.UNIT_SIZE + _scoreText.height) / 2.f ;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// CLASSES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private class pauseButtonListener extends InputListener {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (x >= 0 && x < _pauseButton.getWidth() && y >= 0 && y < _pauseButton.getHeight()) {
                ((GameScreen) ScreenManager.getInstance().getScreen()).pauseGame();
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void addScore() { _score++; }
    public int getScore() { return _score; }

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

    private final ShapeRenderer _uiRenderer;
    private final SpriteBatch _batch;
    private final TextButton _pauseButton;
    private Skin _buttonSkin;
    private GlyphLayout _pauseText;
    private GlyphLayout _continueText;
    private GlyphLayout _scoreText;
    private GlyphLayout _scorePoints;

    private final int _leftBorderX;
    private final int _rightBorderX;
    private final int _topBorderY;
    private final int _topGameBorderY;
    private final int _scoreDividerX;
    private float _scoreY;
    private int _score;
    private int _scoreTextX;
    private int _scorePointsX;
    private float _pauseTextX;
    private float _pauseTextY;
    private float _continueTextX;
    private float _continueTextY;
}
