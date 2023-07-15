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
import com.juchap.snake.Managers.ColorManager;
import com.juchap.snake.Managers.DifficultyManager;
import com.juchap.snake.Managers.FontManager;
import com.juchap.snake.Managers.InputManager;
import com.juchap.snake.Managers.SoundManager;
import com.juchap.snake.Managers.VibrationManager;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Utility.ScreenEnum;
import com.juchap.snake.Utility.ScreenManager;

public class OptionScreen extends AbstractScreen {

    ///////////////////////////////////////////////////////////////////////////////////////////////
    public OptionScreen() {
        Gdx.input.setInputProcessor(this);

        _controlIndex = InputManager.getType();
        _difficultyIndex = DifficultyManager.getDifficulty();

        initGlyphs();
        initButtonSkin();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void buildStage() {
        float buttonPosY = _controlsY - _separatorText.height - (_changeValueText.height - _separatorText.height) / 2;
        float buttonPosX = GlobalVars.CENTER_X + (4.f * _separatorText.width) + _controlsValueText.width;
        TextButton controlsButton = new TextButton(CHANGE_VALUE, _buttonSkin);
        controlsButton.setSize(_changeValueText.width, _changeValueText.height);
        controlsButton.setPosition(buttonPosX, buttonPosY);
        controlsButton.padBottom(_changeValueText.height / 6.f);
        controlsButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                _controlIndex = (_controlIndex + 1) % CONTROL_TYPES.length;
                _controlsValueText.setText(FontManager.fontCustom(ColorManager.getFrontColor(), 24), CONTROL_TYPES[_controlIndex]);
                InputManager.setType(_controlIndex);
            }
        });
        this.addActor(controlsButton);

        buttonPosY -= (3.f * _separatorText.height);
        buttonPosX = GlobalVars.CENTER_X + (4.f * _separatorText.width) + _difficultyValueText.width;
        TextButton difficultyButton = new TextButton(CHANGE_VALUE, _buttonSkin);
        difficultyButton.setSize(_changeValueText.width, _changeValueText.height);
        difficultyButton.setPosition(buttonPosX, buttonPosY);
        difficultyButton.padBottom(_changeValueText.height / 6.f);
        difficultyButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                _difficultyIndex = (_difficultyIndex + 1) % DIFFICULTY_LEVELS.length;
                _difficultyValueText.setText(FontManager.fontCustom(ColorManager.getFrontColor(), 24), DIFFICULTY_LEVELS[_difficultyIndex]);
                DifficultyManager.setDifficulty(_difficultyIndex);
            }
        });
        this.addActor(difficultyButton);

        buttonPosY -= (3.f * _separatorText.height);
        buttonPosX = GlobalVars.CENTER_X + (4.f * _separatorText.width);
        final TextButton soundsButton = new TextButton(CHECK_MARK, _checkBoxSkin);
        soundsButton.setSize(_checkedText.height, _checkedText.height);
        soundsButton.setPosition(buttonPosX, buttonPosY);
        soundsButton.padBottom(_checkedText.height / 6.f);
        soundsButton.setChecked(!SoundManager.getState());
        soundsButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundManager.setState(!soundsButton.isChecked());
                SoundManager.playActivate();
            }
        });
        this.addActor(soundsButton);

        buttonPosY -= (3.f * _separatorText.height);
        buttonPosX = GlobalVars.CENTER_X + (4.f * _separatorText.width);
        final TextButton vibrationsButton = new TextButton(CHECK_MARK, _checkBoxSkin);
        vibrationsButton.setSize(_checkedText.height, _checkedText.height);
        vibrationsButton.setPosition(buttonPosX, buttonPosY);
        vibrationsButton.padBottom(_checkedText.height / 6);
        vibrationsButton.setChecked(!VibrationManager.getState());
        vibrationsButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                VibrationManager.setState(!vibrationsButton.isChecked());
                VibrationManager.vibrateMedium();
            }
        });
        this.addActor(vibrationsButton);

        buttonPosY -= (3.f * _separatorText.height);
        buttonPosX = GlobalVars.CENTER_X + (4.f * _separatorText.width);
        final TextButton themesButton = new TextButton(CHANGE_THEME, _themesSkin);
        themesButton.setSize(GlobalVars.GRID_WIDTH / 4.f, _checkedText.height);
        themesButton.setPosition(buttonPosX, buttonPosY);
        themesButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().showScreen(ScreenEnum.THEMES);
            }
        });
        this.addActor(themesButton);

        float buttonWidth = (GlobalVars.GRID_WIDTH / 3.f);
        float buttonHeight = (GlobalVars.GRID_WIDTH / 8.f);
        buttonPosY -= (12.f * GlobalVars.PADDING_Y);
        buttonPosX = GlobalVars.CENTER_X - (buttonWidth / 2.f);
        TextButton exitButton = new TextButton(EXIT, _exitSkin);
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

    ///////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void render(float delta) {
        super.render(delta);
        drawText();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean keyDown(int keycode) {
        if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK) ) {
            ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void drawText() {
        _spriteBatch.begin();
        BitmapFont fontLarge = FontManager.fontLarge(ColorManager.getFrontColor());
        fontLarge.draw(_spriteBatch, _optionsText, _optionX, _optionY);

        BitmapFont fontCustom = FontManager.fontCustom(ColorManager.getFrontColor(), 24);
        fontCustom.draw(_spriteBatch, _controlsText, _itemsX, _controlsY);
        fontCustom.draw(_spriteBatch, _controlsValueText, _valuesX, _controlsY);
        fontCustom.draw(_spriteBatch, _difficultyText, _itemsX, _difficultyY);
        fontCustom.draw(_spriteBatch, _difficultyValueText, _valuesX, _difficultyY);
        fontCustom.draw(_spriteBatch, _soundsText, _itemsX, _soundsY);
        fontCustom.draw(_spriteBatch, _vibrationsText, _itemsX, _vibrationsY);
        fontCustom.draw(_spriteBatch, _themesText, _itemsX, _themesY);
        _spriteBatch.end();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void initButtonSkin() {
        // Init
        BitmapFont fontMedium = FontManager.fontMedium(ColorManager.getFrontColor());
        BitmapFont fontSmall = FontManager.fontSmall(ColorManager.getFrontColor());
        _buttonSkin = new Skin();
        _checkBoxSkin = new Skin();
        _exitSkin = new Skin();
        _themesSkin = new Skin();
        _buttonSkin.add(DEFAULT, fontMedium);
        _checkBoxSkin.add(DEFAULT, fontMedium);
        _exitSkin.add(DEFAULT, fontMedium);
        _themesSkin.add(DEFAULT, fontSmall);

        // Create texture
        Pixmap pixmap = new Pixmap((int) _changeValueText.width, (int) _changeValueText.height, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture pixmapTex = new Texture(pixmap);
        _buttonSkin.add(BACKGROUND, pixmapTex);
        _checkBoxSkin.add(BACKGROUND, pixmapTex);
        _exitSkin.add(BACKGROUND, pixmapTex);
        _themesSkin.add(BACKGROUND, pixmapTex);
        pixmap.dispose();

        // Create button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = _buttonSkin.newDrawable(BACKGROUND, ColorManager.getBackColor());
        textButtonStyle.down = _buttonSkin.newDrawable(BACKGROUND, ColorManager.getBackColor());
        textButtonStyle.checked = _buttonSkin.newDrawable(BACKGROUND, ColorManager.getBackColor());
        textButtonStyle.over = _buttonSkin.newDrawable(BACKGROUND, ColorManager.getBackColor());
        textButtonStyle.font = _buttonSkin.getFont(DEFAULT);
        textButtonStyle.fontColor = ColorManager.getFrontColor();
        textButtonStyle.downFontColor = ColorManager.getFrontAltColor();
        _buttonSkin.add(DEFAULT, textButtonStyle);

        // Create checkBox style
        TextButton.TextButtonStyle checkBoxStyle = new TextButton.TextButtonStyle();
        checkBoxStyle.up = _checkBoxSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        checkBoxStyle.down = _checkBoxSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        checkBoxStyle.checked = _checkBoxSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        checkBoxStyle.over = _checkBoxSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        checkBoxStyle.font = _checkBoxSkin.getFont(DEFAULT);
        checkBoxStyle.fontColor = ColorManager.getBackColor();
        checkBoxStyle.overFontColor = ColorManager.getFrontAltColor();
        checkBoxStyle.checkedFontColor = ColorManager.getFrontColor();
        _checkBoxSkin.add(DEFAULT, checkBoxStyle);

        // Create exit button style
        TextButton.TextButtonStyle exitButtonStyle = new TextButton.TextButtonStyle();
        exitButtonStyle.up = _exitSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        exitButtonStyle.down = _exitSkin.newDrawable(BACKGROUND, ColorManager.getFrontAltColor());
        exitButtonStyle.checked = _exitSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        exitButtonStyle.over = _exitSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        exitButtonStyle.font = _exitSkin.getFont(DEFAULT);
        exitButtonStyle.fontColor = ColorManager.getBackColor();
        _exitSkin.add(DEFAULT, exitButtonStyle);

        // Create exit button style
        TextButton.TextButtonStyle themesButtonStyle = new TextButton.TextButtonStyle();
        themesButtonStyle.up = _themesSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        themesButtonStyle.down = _themesSkin.newDrawable(BACKGROUND, ColorManager.getFrontAltColor());
        themesButtonStyle.checked = _themesSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        themesButtonStyle.over = _themesSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        themesButtonStyle.font = _themesSkin.getFont(DEFAULT);
        themesButtonStyle.fontColor = ColorManager.getBackColor();
        _themesSkin.add(DEFAULT, themesButtonStyle);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void initGlyphs() {
        _optionsText = new GlyphLayout();
        _separatorText = new GlyphLayout();
        _checkedText = new GlyphLayout();
        _changeValueText = new GlyphLayout();
        _controlsText = new GlyphLayout();
        _difficultyText = new GlyphLayout();
        _soundsText = new GlyphLayout();
        _vibrationsText = new GlyphLayout();
        _themesText = new GlyphLayout();
        _controlsValueText = new GlyphLayout();
        _difficultyValueText = new GlyphLayout();

        BitmapFont fontLarge = FontManager.fontLarge(ColorManager.getFrontColor());
        _optionsText.setText(fontLarge, TITLE);

        BitmapFont fontMedium = FontManager.fontMedium(ColorManager.getFrontColor());
        _changeValueText.setText(fontMedium, CHANGE_VALUE);
        _checkedText.setText(fontMedium, CHECK_MARK);

        BitmapFont fontCustom = FontManager.fontCustom(ColorManager.getFrontColor(), 24);
        _separatorText.setText(fontCustom, SEPARATOR);
        _controlsText.setText(fontCustom, OPTION_ENTRIES[0]);
        _difficultyText.setText(fontCustom, OPTION_ENTRIES[1]);
        _soundsText.setText(fontCustom, OPTION_ENTRIES[2]);
        _vibrationsText.setText(fontCustom, OPTION_ENTRIES[3]);
        _themesText.setText(fontCustom, OPTION_ENTRIES[4]);
        _difficultyValueText.setText(fontCustom, DIFFICULTY_LEVELS[_difficultyIndex]);
        _controlsValueText.setText(fontCustom, CONTROL_TYPES[_controlIndex]);

        _optionX = GlobalVars.CENTER_X - (_optionsText.width / 2.f);
        _optionY = GlobalVars.BOTTOM + ((5.f / 6.f) * GlobalVars.GRID_HEIGHT);
        _valuesX = GlobalVars.CENTER_X + (4.f * _separatorText.width);
        _itemsX = GlobalVars.CENTER_X - _controlsText.width - (2.f * _separatorText.width);
        _controlsY = _optionY - (12.f * GlobalVars.PADDING_Y);
        _difficultyY = _controlsY - (3.f * _separatorText.height);
        _soundsY = _difficultyY - (3.f * _separatorText.height);
        _vibrationsY = _soundsY - (3.f * _separatorText.height);
        _themesY = _vibrationsY - (3.f * _separatorText.height);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String[] OPTION_ENTRIES = new String[] { "CONTROLS  ", "DIFFICULTY", "SOUNDS    ", "VIBRATIONS", "THEME     "};
    private static final String[] CONTROL_TYPES = new String[] { "SWIPE ", "TOUCH ", "F-DPAD", "H-DPAD" };
    private static final String[] DIFFICULTY_LEVELS = new String[] { "EASY  ", "MEDIUM", "HARD  " };
    private static final String CHANGE_VALUE = " > ";
    private static final String SEPARATOR = " ";
    private static final String TITLE = "OPTIONS";
    private static final String CHECK_MARK = "x";
    private static final String CHANGE_THEME = "SELECT";
    private static final String EXIT = "EXIT";
    private static final String DEFAULT = "default";
    private static final String BACKGROUND = "background";

    private Skin _buttonSkin;
    private Skin _checkBoxSkin;
    private Skin _exitSkin;
    private Skin _themesSkin;
    private GlyphLayout _separatorText;
    private GlyphLayout _changeValueText;
    private GlyphLayout _checkedText;
    private GlyphLayout _controlsText;
    private GlyphLayout _controlsValueText;
    private GlyphLayout _difficultyText;
    private GlyphLayout _difficultyValueText;
    private GlyphLayout _soundsText;
    private GlyphLayout _vibrationsText;
    private GlyphLayout _optionsText;
    private GlyphLayout _themesText;

    private int _controlIndex;
    private int _difficultyIndex;
    private float _optionX;
    private float _optionY;
    private float _valuesX;
    private float _itemsX;
    private float _controlsY;
    private float _difficultyY;
    private float _soundsY;
    private float _vibrationsY;
    private float _themesY;
}