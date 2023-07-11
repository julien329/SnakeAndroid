package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.StringBuilder;
import com.juchap.snake.Managers.ColorManager;
import com.juchap.snake.Managers.FontManager;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Managers.HighScoreManager;
import com.juchap.snake.Utility.ScreenEnum;
import com.juchap.snake.Utility.ScreenManager;

public class HighScoreScreen extends AbstractScreen {

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public HighScoreScreen() {
        Gdx.input.setInputProcessor(this);

        _difficultyIndex = 0;

        initButtonSkin();

        _arrowLeftButton = new TextButton(ARROW_LEFT, _arrowSkin);
        _arrowRightButton = new TextButton(ARROW_RIGHT, _arrowSkin);

        initGlyphs();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void buildStage() {
        BitmapFont medium = FontManager.fontMedium(ColorManager.getFrontColor());
        GlyphLayout arrowText = new GlyphLayout();
        arrowText.setText(medium, ARROW_LEFT);

        float buttonWidth = arrowText.width;
        float buttonHeight = arrowText.height;
        float buttonPosY = GlobalVars.CENTER_Y - (buttonHeight / 2);

        // ArrowLeft button
        float buttonPosX = GlobalVars.LEFT + GlobalVars.UNIT_SIZE;
        _arrowLeftButton.setSize(buttonWidth, buttonHeight);
        _arrowLeftButton.setPosition(buttonPosX, buttonPosY);
        _arrowLeftButton.setVisible(false);
        _arrowLeftButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                _arrowRightButton.setVisible(true);
                _difficultyIndex--;
                if (_difficultyIndex <= 0){
                    _difficultyIndex = 0;
                    _arrowLeftButton.setVisible(false);
                }
                initGlyphs();
            }
        });
        this.addActor(_arrowLeftButton);

        // ArrowRight button
        buttonPosX = GlobalVars.RIGHT - GlobalVars.UNIT_SIZE - buttonWidth;
        _arrowRightButton.setSize(buttonWidth, buttonHeight);
        _arrowRightButton.setPosition(buttonPosX, buttonPosY);
        _arrowRightButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                _arrowLeftButton.setVisible(true);
                _difficultyIndex++;
                if (_difficultyIndex >= DIFFICULTY_LEVELS.length - 1){
                    _difficultyIndex = DIFFICULTY_LEVELS.length - 1;
                    _arrowRightButton.setVisible(false);
                }
                initGlyphs();
            }
        });
        this.addActor(_arrowRightButton);

        // Exit button
        buttonWidth = (GlobalVars.GRID_WIDTH / 3.f);
        buttonHeight = (GlobalVars.GRID_WIDTH / 8.f);
        buttonPosY = GlobalVars.BOTTOM + GlobalVars.UNIT_SIZE + (4 * GlobalVars.PADDING_Y);
        buttonPosX = GlobalVars.CENTER_X - (buttonWidth / 2);
        final TextButton exitButton = new TextButton(EXIT, _exitSkin);
        exitButton.setSize(buttonWidth, buttonHeight);
        exitButton.setPosition(buttonPosX, buttonPosY);
        exitButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
            }
        });
        this.addActor(exitButton);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void render(float delta) {
        super.render(delta);
        drawText();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean keyDown(int keycode) {
        if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK)) {
            ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
        }
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void drawText() {
        _spriteBatch.begin();
        BitmapFont fontCustom = FontManager.fontCustom(ColorManager.getFrontColor(), 56);
        fontCustom.draw(_spriteBatch, _highScoreText, _highScoreX, _highScoreY);

        BitmapFont fontMedium = FontManager.fontMedium(ColorManager.getFrontColor());
        fontMedium.draw(_spriteBatch, _difficultyText, _difficultyTextX, _difficultyTextY);
        for(int i = 0; i < TABLE_SIZE; i++) {
            fontMedium.draw(_spriteBatch, _entriesText[i], _entriesX, _entriesY[i]);
        }
        _spriteBatch.end();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private StringBuilder formatScore(int score) {
        String scoreText = String.valueOf(score);
        StringBuilder scoreFormatted = new StringBuilder();
        for (int i = 0; i < 4 - scoreText.length(); i++) {
            scoreFormatted.append(ZERO);
        }
        return scoreFormatted.append(scoreText);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void initButtonSkin() {
        // Init
        BitmapFont fontMedium = FontManager.fontMedium(ColorManager.getFrontColor());
        _arrowSkin = new Skin();
        _exitSkin = new Skin();
        _arrowSkin.add(DEFAULT, fontMedium);
        _exitSkin.add(DEFAULT, fontMedium);

        // Create texture
        Pixmap pixmap = new Pixmap(GlobalVars.GRID_WIDTH / 3, GlobalVars.GRID_HEIGHT / 8, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        _arrowSkin.add(BACKGROUND, new Texture(pixmap));
        _exitSkin.add(BACKGROUND, new Texture(pixmap));
        pixmap.dispose();

        // Create button style
        TextButton.TextButtonStyle arrowButtonStyle = new TextButton.TextButtonStyle();
        arrowButtonStyle.up = _arrowSkin.newDrawable(BACKGROUND, ColorManager.getBackColor());
        arrowButtonStyle.down = _arrowSkin.newDrawable(BACKGROUND, ColorManager.getBackColor());
        arrowButtonStyle.checked = _arrowSkin.newDrawable(BACKGROUND, ColorManager.getBackColor());
        arrowButtonStyle.over = _arrowSkin.newDrawable(BACKGROUND, ColorManager.getBackColor());
        arrowButtonStyle.font = _arrowSkin.getFont(DEFAULT);
        arrowButtonStyle.fontColor = ColorManager.getFrontColor();
        arrowButtonStyle.downFontColor = ColorManager.getFrontAltColor();
        _arrowSkin.add(DEFAULT, arrowButtonStyle);

        // Create exit button style
        TextButton.TextButtonStyle exitButtonStyle = new TextButton.TextButtonStyle();
        exitButtonStyle.up = _exitSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        exitButtonStyle.down = _exitSkin.newDrawable(BACKGROUND, ColorManager.getFrontAltColor());
        exitButtonStyle.checked = _exitSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        exitButtonStyle.over = _exitSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        exitButtonStyle.font = _exitSkin.getFont(DEFAULT);
        exitButtonStyle.fontColor = ColorManager.getBackColor();
        _exitSkin.add(DEFAULT, exitButtonStyle);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void initGlyphs() {
        BitmapFont fontCustom = FontManager.fontCustom(ColorManager.getFrontColor(), 56);
        _highScoreText = new GlyphLayout();
        _highScoreText.setText(fontCustom, HIGH_SCORE);
        _highScoreX = GlobalVars.CENTER_X - (_highScoreText.width / 2.f);
        _highScoreY = GlobalVars.BOTTOM + (0.9f * GlobalVars.GRID_HEIGHT);

        BitmapFont fontMedium = FontManager.fontMedium(ColorManager.getFrontColor());
        _difficultyText = new GlyphLayout();
        _difficultyText.setText(fontMedium, DIFFICULTY_LEVELS[_difficultyIndex]);
        _difficultyTextX = GlobalVars.CENTER_X - (_difficultyText.width / 2.f);
        _difficultyTextY = _highScoreY - (12.f * GlobalVars.PADDING_Y);

        float textPosY = _difficultyTextY - (2.f * GlobalVars.PADDING_Y);
        _entriesY = new float[TABLE_SIZE];
        _entriesText = new GlyphLayout[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; i++) {
            StringBuilder entryText = new StringBuilder(RANKS[i]).append(SPACE4).append(formatScore(HighScoreManager.getScore(i, _difficultyIndex)));
            _entriesText[i] = new GlyphLayout();
            _entriesText[i].setText(fontMedium, entryText);
            textPosY -= (2.5 * GlobalVars.PADDING_Y);
            _entriesY[i] = textPosY;
        }
        _entriesX = GlobalVars.CENTER_X - (_entriesText[0].width / 2.f);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final int TABLE_SIZE = 9;
    private static final String[] RANKS = {"1ST", "2ND", "3RD", "4TH", "5TH", "6TH", "7TH", "8TH", "9TH"};
    private static final String[] DIFFICULTY_LEVELS = new String[] { "EASY", "MEDIUM", "HARD" };
    private static final String ARROW_RIGHT = " > ";
    private static final String ARROW_LEFT = " < ";
    private static final String EXIT = "EXIT";
    private static final String SPACE4 = "    ";
    private static final String HIGH_SCORE = "HIGH SCORES";
    private static final String DEFAULT = "default";
    private static final String BACKGROUND = "background";
    private static final String ZERO = "0";

    private final TextButton _arrowLeftButton;
    private final TextButton _arrowRightButton;
    private Skin _arrowSkin;
    private Skin _exitSkin;
    private GlyphLayout[] _entriesText;
    private GlyphLayout _highScoreText;
    private GlyphLayout _difficultyText;

    private int _difficultyIndex;
    private float _highScoreX;
    private float _highScoreY;
    private float _difficultyTextX;
    private float _difficultyTextY;
    private float _entriesX;
    private float[] _entriesY;
}
