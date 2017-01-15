package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

        borders = new ShapeRenderer();
        batch = new SpriteBatch();

        leftBorderX = GlobalVars.GRID_OFFSET_X;
        rightBorderX = GlobalVars.GRID_OFFSET_X + GlobalVars.GRID_WIDTH - GlobalVars.UNIT_SIZE;
        bottomBorderY = GlobalVars.GRID_OFFSET_Y;
        topBorderY = GlobalVars.GRID_OFFSET_Y + GlobalVars.GRID_HEIGHT - GlobalVars.UNIT_SIZE;

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
            };
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
            };
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
            };
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
            };
        });
        this.addActor(vibrationsButton);

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
            };
        });
        this.addActor(exitButton);
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

    private void drawBorders() {
        // Draw screen borders
        borders.begin(ShapeRenderer.ShapeType.Filled);
        borders.setColor(ColorManager.getFrontColor());
        borders.rect(leftBorderX, bottomBorderY, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT);
        borders.rect(leftBorderX, bottomBorderY, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        borders.rect(leftBorderX, topBorderY, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        borders.rect(rightBorderX, bottomBorderY, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT);
        borders.end();
    }

    private void drawText() {
        batch.begin();
        BitmapFont fontLarge = FontManager.fontLarge(ColorManager.getFrontColor());
        fontLarge.draw(batch, optionsText, optionX, optionY);

        BitmapFont fontCustom = FontManager.fontCustom(ColorManager.getFrontColor(), 24);
        fontCustom.draw(batch, controlsText, controlsX, controlsY);
        fontCustom.draw(batch, controlsValueText, valuesX, controlsY);
        fontCustom.draw(batch, difficultyText, difficultyX, difficultyY);
        fontCustom.draw(batch, difficultyValueText, valuesX, difficultyY);
        fontCustom.draw(batch, soundsText, soundsX, soundsY);
        fontCustom.draw(batch, vibrationsText, vibrationsX, vibrationsY);
        batch.end();
    }

    private void initButtonSkin() {
        // Init
        BitmapFont fontMedium = FontManager.fontMedium(ColorManager.getFrontColor());
        buttonSkin = new Skin();
        checkBoxSkin = new Skin();
        exitSkin = new Skin();
        buttonSkin.add(DEFAULT, fontMedium);
        checkBoxSkin.add(DEFAULT, fontMedium);
        exitSkin.add(DEFAULT, fontMedium);

        // Create texture
        Pixmap pixmap = new Pixmap((int)changeValueText.width, (int)changeValueText.height, Pixmap.Format.RGB888);
        pixmap.setColor(ColorManager.getFrontColor());
        pixmap.fill();
        buttonSkin.add(BACKGROUND, new Texture(pixmap));
        checkBoxSkin.add(BACKGROUND, new Texture(pixmap));
        exitSkin.add(BACKGROUND, new Texture(pixmap));
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
        difficultyValueText.setText(fontCustom, DIFFICULTY_LEVELS[difficultyIndex]);
        controlsValueText.setText(fontCustom, CONTROL_TYPES[controlIndex]);

        int centerX = (Gdx.graphics.getWidth() / 2);
        optionX = (int)(Gdx.graphics.getWidth() - optionsText.width) / 2;
        optionY = (5 * Gdx.graphics.getHeight()) / 6;
        valuesX = (int)(centerX + 4 * separatorText.width);
        controlsX = (int)(centerX - controlsText.width - 2 * separatorText.width);
        controlsY = optionY - 12 * GlobalVars.PADDING_Y;
        difficultyX = (int)(centerX - difficultyText.width - 2 * separatorText.width);
        difficultyY = (int)(controlsY - 3 * separatorText.height);
        soundsX = (int)(centerX - soundsText.width - 2 * separatorText.width);
        soundsY = (int)(difficultyY - 3 * separatorText.height);
        vibrationsX = (int)(centerX - vibrationsText.width - 2 * separatorText.width);
        vibrationsY = (int)(soundsY - 3 * separatorText.height);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String[] OPTION_ENTRIES = new String[] { "CONTROLS  ", "DIFFICULTY", "SOUNDS    ", "VIBRATIONS"};
    private static final String[] CONTROL_TYPES = new String[] { "SWIPE ", "TOUCH " };
    private static final String[] DIFFICULTY_LEVELS = new String[] { "EASY  ", "MEDIUM", "HARD  " };
    private static final String CHANGE_VALUE = " > ";
    private static final String SEPARATOR = " ";
    private static final String TITLE = "OPTIONS";
    private static final String CHECK_MARK = "x";
    private static final String EXIT = "EXIT";
    private static final String DEFAULT = "default";
    private static final String BACKGROUND = "background";

    private ShapeRenderer borders;
    private SpriteBatch batch;
    private Skin buttonSkin;
    private Skin checkBoxSkin;
    private Skin exitSkin;
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

    private int controlIndex;
    private int difficultyIndex;
    private int leftBorderX;
    private int rightBorderX;
    private int topBorderY;
    private int bottomBorderY;
    private int optionX;
    private int optionY;
    private int valuesX;
    private int controlsX;
    private int controlsY;
    private int difficultyX;
    private int difficultyY;
    private int soundsX;
    private int soundsY;
    private int vibrationsX;
    private int vibrationsY;
}