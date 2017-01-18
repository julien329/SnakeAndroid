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
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Managers.InputManager;
import com.juchap.snake.Utility.ScreenEnum;
import com.juchap.snake.Utility.ScreenManager;
import com.juchap.snake.Managers.SoundManager;
import com.juchap.snake.Managers.VibrationManager;


public class OptionScreen extends AbstractScreen {

    public OptionScreen() {
        super();
        Gdx.input.setInputProcessor(this);
        controlIndex = InputManager.getType();
        difficultyIndex = DifficultyManager.getDifficulty();

        initGlyphs();
        initButtonSkin();
    }

    @Override
    public void buildStage() {
        int buttonPosY = (int)(controlsY - separatorText.height - (changeValueText.height - separatorText.height) / 2);
        int buttonPosX = (int)((Gdx.graphics.getWidth() / 2) + 4 * separatorText.width + controlsValueText.width);
        TextButton controlsButton = new TextButton(CHANGE_VALUE, buttonSkin);
        controlsButton.setSize(changeValueText.width, changeValueText.height);
        controlsButton.setPosition(buttonPosX, buttonPosY);
        controlsButton.padBottom(changeValueText.height / 6);
        controlsButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controlIndex = (controlIndex + 1) % CONTROL_TYPES.length;
                controlsValueText.setText(FontManager.fontCustom(ColorManager.getFrontColor(), 24), CONTROL_TYPES[controlIndex]);
                InputManager.setType(controlIndex);
            }
        });
        this.addActor(controlsButton);

        buttonPosY -= (3 * separatorText.height);
        buttonPosX = (int)((Gdx.graphics.getWidth() / 2) + 4 * separatorText.width + difficultyValueText.width);
        TextButton difficultyButton = new TextButton(CHANGE_VALUE, buttonSkin);
        difficultyButton.setSize(changeValueText.width, changeValueText.height);
        difficultyButton.setPosition(buttonPosX, buttonPosY);
        difficultyButton.padBottom(changeValueText.height / 6);
        difficultyButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                difficultyIndex = (difficultyIndex + 1) % DIFFICULTY_LEVELS.length;
                difficultyValueText.setText(FontManager.fontCustom(ColorManager.getFrontColor(), 24), DIFFICULTY_LEVELS[difficultyIndex]);
                DifficultyManager.setDifficulty(difficultyIndex);
            }
        });
        this.addActor(difficultyButton);

        buttonPosY -= (3 * separatorText.height);
        buttonPosX = (int)((Gdx.graphics.getWidth() / 2) + 4 * separatorText.width);
        final TextButton soundsButton = new TextButton(CHECK_MARK, checkBoxSkin);
        soundsButton.setSize(checkedText.height, checkedText.height);
        soundsButton.setPosition(buttonPosX, buttonPosY);
        soundsButton.padBottom(checkedText.height / 6);
        soundsButton.setChecked(!SoundManager.getState());
        soundsButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundManager.setState(!soundsButton.isChecked());
                SoundManager.playActivate();
            }
        });
        this.addActor(soundsButton);

        buttonPosY -= (3 * separatorText.height);
        buttonPosX = (int)((Gdx.graphics.getWidth() / 2) + 4 * separatorText.width);
        final TextButton vibrationsButton = new TextButton(CHECK_MARK, checkBoxSkin);
        vibrationsButton.setSize(checkedText.height, checkedText.height);
        vibrationsButton.setPosition(buttonPosX, buttonPosY);
        vibrationsButton.padBottom(checkedText.height / 6);
        vibrationsButton.setChecked(!VibrationManager.getState());
        vibrationsButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                VibrationManager.setState(!vibrationsButton.isChecked());
                VibrationManager.vibrateMedium();
            }
        });
        this.addActor(vibrationsButton);

        buttonPosY -= (3 * separatorText.height);
        buttonPosX = (int)((Gdx.graphics.getWidth() / 2) + 4 * separatorText.width);
        final TextButton themesButton = new TextButton(CHANGE_THEME, themesSkin);
        themesButton.setSize(Gdx.graphics.getWidth() / 4, checkedText.height);
        themesButton.setPosition(buttonPosX, buttonPosY);
        themesButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().showScreen(ScreenEnum.THEMES);
            }
        });
        this.addActor(themesButton);

        float buttonWidth = (Gdx.graphics.getWidth() / 3);
        float buttonHeight = (Gdx.graphics.getWidth() / 8);
        buttonPosY -= (12 * GlobalVars.PADDING_Y);
        buttonPosX = (int)(Gdx.graphics.getWidth() - buttonWidth) / 2;
        TextButton exitButton = new TextButton(EXIT, exitSkin);
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

    @Override
    public void render(float delta) {
        super.render(delta);
        drawText();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK) ) {
            ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
        }
        return false;
    }

    private void drawText() {
        spriteBatch.begin();
        BitmapFont fontLarge = FontManager.fontLarge(ColorManager.getFrontColor());
        fontLarge.draw(spriteBatch, optionsText, optionX, optionY);

        BitmapFont fontCustom = FontManager.fontCustom(ColorManager.getFrontColor(), 24);
        fontCustom.draw(spriteBatch, controlsText, itemsX, controlsY);
        fontCustom.draw(spriteBatch, controlsValueText, valuesX, controlsY);
        fontCustom.draw(spriteBatch, difficultyText, itemsX, difficultyY);
        fontCustom.draw(spriteBatch, difficultyValueText, valuesX, difficultyY);
        fontCustom.draw(spriteBatch, soundsText, itemsX, soundsY);
        fontCustom.draw(spriteBatch, vibrationsText, itemsX, vibrationsY);
        fontCustom.draw(spriteBatch, themesText, itemsX, themesY);
        spriteBatch.end();
    }

    private void initButtonSkin() {
        // Init
        BitmapFont fontMedium = FontManager.fontMedium(ColorManager.getFrontColor());
        BitmapFont fontSmall = FontManager.fontSmall(ColorManager.getFrontColor());
        buttonSkin = new Skin();
        checkBoxSkin = new Skin();
        exitSkin = new Skin();
        themesSkin = new Skin();
        buttonSkin.add(DEFAULT, fontMedium);
        checkBoxSkin.add(DEFAULT, fontMedium);
        exitSkin.add(DEFAULT, fontMedium);
        themesSkin.add(DEFAULT, fontSmall);

        // Create texture
        Pixmap pixmap = new Pixmap((int)changeValueText.width, (int)changeValueText.height, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture pixmapTex = new Texture(pixmap);
        buttonSkin.add(BACKGROUND, pixmapTex);
        checkBoxSkin.add(BACKGROUND, pixmapTex);
        exitSkin.add(BACKGROUND, pixmapTex);
        themesSkin.add(BACKGROUND, pixmapTex);
        pixmap.dispose();

        // Create button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = buttonSkin.newDrawable(BACKGROUND, ColorManager.getBackColor());
        textButtonStyle.down = buttonSkin.newDrawable(BACKGROUND, ColorManager.getBackColor());
        textButtonStyle.checked = buttonSkin.newDrawable(BACKGROUND, ColorManager.getBackColor());
        textButtonStyle.over = buttonSkin.newDrawable(BACKGROUND, ColorManager.getBackColor());
        textButtonStyle.font = buttonSkin.getFont(DEFAULT);
        textButtonStyle.fontColor = ColorManager.getFrontColor();
        textButtonStyle.downFontColor = ColorManager.getFrontAltColor();
        buttonSkin.add(DEFAULT, textButtonStyle);

        // Create checkBox style
        TextButton.TextButtonStyle checkBoxStyle = new TextButton.TextButtonStyle();
        checkBoxStyle.up = checkBoxSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        checkBoxStyle.down = checkBoxSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        checkBoxStyle.checked = checkBoxSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        checkBoxStyle.over = checkBoxSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        checkBoxStyle.font = checkBoxSkin.getFont(DEFAULT);
        checkBoxStyle.fontColor = ColorManager.getBackColor();
        checkBoxStyle.overFontColor = ColorManager.getFrontAltColor();
        checkBoxStyle.checkedFontColor = ColorManager.getFrontColor();
        checkBoxSkin.add(DEFAULT, checkBoxStyle);

        // Create exit button style
        TextButton.TextButtonStyle exitButtonStyle = new TextButton.TextButtonStyle();
        exitButtonStyle.up = exitSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        exitButtonStyle.down = exitSkin.newDrawable(BACKGROUND, ColorManager.getFrontAltColor());
        exitButtonStyle.checked = exitSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        exitButtonStyle.over = exitSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        exitButtonStyle.font = exitSkin.getFont(DEFAULT);
        exitButtonStyle.fontColor = ColorManager.getBackColor();
        exitSkin.add(DEFAULT, exitButtonStyle);

        // Create exit button style
        TextButton.TextButtonStyle themesButtonStyle = new TextButton.TextButtonStyle();
        themesButtonStyle.up = themesSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        themesButtonStyle.down = themesSkin.newDrawable(BACKGROUND, ColorManager.getFrontAltColor());
        themesButtonStyle.checked = themesSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        themesButtonStyle.over = themesSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        themesButtonStyle.font = themesSkin.getFont(DEFAULT);
        themesButtonStyle.fontColor = ColorManager.getBackColor();
        themesSkin.add(DEFAULT, themesButtonStyle);
    }

    private void initGlyphs() {
        optionsText = new GlyphLayout();
        separatorText = new GlyphLayout();
        checkedText = new GlyphLayout();
        changeValueText = new GlyphLayout();
        controlsText = new GlyphLayout();
        difficultyText = new GlyphLayout();
        soundsText = new GlyphLayout();
        vibrationsText = new GlyphLayout();
        themesText = new GlyphLayout();
        controlsValueText = new GlyphLayout();
        difficultyValueText = new GlyphLayout();

        BitmapFont fontLarge = FontManager.fontLarge(ColorManager.getFrontColor());
        optionsText.setText(fontLarge, TITLE);

        BitmapFont fontMedium = FontManager.fontMedium(ColorManager.getFrontColor());
        changeValueText.setText(fontMedium, CHANGE_VALUE);
        checkedText.setText(fontMedium, CHECK_MARK);

        BitmapFont fontCustom = FontManager.fontCustom(ColorManager.getFrontColor(), 24);
        separatorText.setText(fontCustom, SEPARATOR);
        controlsText.setText(fontCustom, OPTION_ENTRIES[0]);
        difficultyText.setText(fontCustom, OPTION_ENTRIES[1]);
        soundsText.setText(fontCustom, OPTION_ENTRIES[2]);
        vibrationsText.setText(fontCustom, OPTION_ENTRIES[3]);
        themesText.setText(fontCustom, OPTION_ENTRIES[4]);
        difficultyValueText.setText(fontCustom, DIFFICULTY_LEVELS[difficultyIndex]);
        controlsValueText.setText(fontCustom, CONTROL_TYPES[controlIndex]);

        int centerX = (Gdx.graphics.getWidth() / 2);
        optionX = (int)(Gdx.graphics.getWidth() - optionsText.width) / 2;
        optionY = (5 * Gdx.graphics.getHeight()) / 6;
        valuesX = (int)(centerX + 4 * separatorText.width);
        itemsX = (int)(centerX - controlsText.width - 2 * separatorText.width);
        controlsY = optionY - 12 * GlobalVars.PADDING_Y;
        difficultyY = (int)(controlsY - 3 * separatorText.height);
        soundsY = (int)(difficultyY - 3 * separatorText.height);
        vibrationsY = (int)(soundsY - 3 * separatorText.height);
        themesY = (int)(vibrationsY - 3 * separatorText.height);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String[] OPTION_ENTRIES = new String[] { "CONTROLS  ", "DIFFICULTY", "SOUNDS    ", "VIBRATIONS", "THEME     "};
    private static final String[] CONTROL_TYPES = new String[] { "SWIPE ", "TOUCH " };
    private static final String[] DIFFICULTY_LEVELS = new String[] { "EASY  ", "MEDIUM", "HARD  " };
    private static final String CHANGE_VALUE = " > ";
    private static final String SEPARATOR = " ";
    private static final String TITLE = "OPTIONS";
    private static final String CHECK_MARK = "x";
    private static final String CHANGE_THEME = "SELECT";
    private static final String EXIT = "EXIT";
    private static final String DEFAULT = "default";
    private static final String BACKGROUND = "background";

    private Skin buttonSkin;
    private Skin checkBoxSkin;
    private Skin exitSkin;
    private Skin themesSkin;
    private GlyphLayout separatorText;
    private GlyphLayout changeValueText;
    private GlyphLayout checkedText;
    private GlyphLayout controlsText;
    private GlyphLayout controlsValueText;
    private GlyphLayout difficultyText;
    private GlyphLayout difficultyValueText;
    private GlyphLayout soundsText;
    private GlyphLayout vibrationsText;
    private GlyphLayout optionsText;
    private GlyphLayout themesText;

    private int controlIndex;
    private int difficultyIndex;
    private int optionX;
    private int optionY;
    private int valuesX;
    private int itemsX;
    private int controlsY;
    private int difficultyY;
    private int soundsY;
    private int vibrationsY;
    private int themesY;
}