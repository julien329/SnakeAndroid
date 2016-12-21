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
import com.juchap.snake.Utility.FontManager;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Utility.ScreenEnum;
import com.juchap.snake.Utility.ScreenManager;
import com.juchap.snake.Utility.SoundManager;
import com.juchap.snake.Utility.VibrationManager;


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
        titleY = (int)(5 * Gdx.graphics.getHeight()) / 6;
        textY = titleY - (int)(12 * GlobalVars.PADDING_Y);

        controlIndex = 0;
        difficultyIndex = 0;

        initGlyphs();
        initButtonSkin();
    }

    @Override
    public void buildStage() {
        int buttonPosY = (int)(textY - separatorText.height - (changeValueText.height - separatorText.height) / 2);
        int buttonPosX = (int)((Gdx.graphics.getWidth() / 2) + 4 * separatorText.width + controlsValueText.width);
        TextButton controlsButton = new TextButton(CHANGE_VALUE, buttonSkin);
        controlsButton.setSize(changeValueText.width, changeValueText.height);
        controlsButton.setPosition(buttonPosX, buttonPosY);
        controlsButton.padBottom(changeValueText.height / 6);
        controlsButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controlIndex = (controlIndex + 1) % CONTROL_TYPES.length;
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
        borders.setColor(Color.WHITE);
        borders.rect(leftBorderX, bottomBorderY, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT);
        borders.rect(leftBorderX, bottomBorderY, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        borders.rect(leftBorderX, topBorderY, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        borders.rect(rightBorderX, bottomBorderY, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT);
        borders.end();
    }

    private void drawText() {
        BitmapFont fontTitle = FontManager.fontLarge(Color.WHITE);
        float textPosY = titleY;

        batch.begin();
        fontTitle.draw(batch, optionsText, (Gdx.graphics.getWidth() - optionsText.width) / 2, textPosY);
        batch.end();

        BitmapFont fontText = FontManager.fontCustom(Color.WHITE, 24);
        controlsValueText.setText(fontText, CONTROL_TYPES[controlIndex]);
        difficultyValueText.setText(fontText, DIFFICULTY_LEVELS[difficultyIndex]);
        int centerX = (Gdx.graphics.getWidth() / 2);
        textPosY = textY;

        batch.begin();
        fontText.draw(batch, controlsText, centerX - controlsText.width - 2 * separatorText.width, textPosY);
        fontText.draw(batch, controlsValueText, centerX + 4 * separatorText.width, textPosY);

        textPosY -= (3 * separatorText.height);
        fontText.draw(batch, difficultyText, centerX - difficultyText.width - 2 * separatorText.width, textPosY);
        fontText.draw(batch, difficultyValueText, centerX + 4 * separatorText.width, textPosY);

        textPosY -= (3 * separatorText.height);
        fontText.draw(batch, soundsText, centerX - soundsText.width - 2 * separatorText.width, textPosY);

        textPosY -= (3 * separatorText.height);
        fontText.draw(batch, vibrationsText, centerX - vibrationsText.width - 2 * separatorText.width, textPosY);
        batch.end();
    }

    private void initButtonSkin() {
        // Init
        BitmapFont font = FontManager.fontMedium(Color.WHITE);
        buttonSkin = new Skin();
        checkBoxSkin = new Skin();
        exitSkin = new Skin();
        buttonSkin.add("default", font);
        checkBoxSkin.add("default", font);
        exitSkin.add("default", font);

        // Create texture
        Pixmap pixmap = new Pixmap((int)changeValueText.width, (int)changeValueText.height, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        buttonSkin.add("background",new Texture(pixmap));
        checkBoxSkin.add("background",new Texture(pixmap));
        exitSkin.add("background",new Texture(pixmap));
        pixmap.dispose();

        // Create button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = buttonSkin.newDrawable("background", Color.BLACK);
        textButtonStyle.down = buttonSkin.newDrawable("background", Color.BLACK);
        textButtonStyle.checked = buttonSkin.newDrawable("background", Color.BLACK);
        textButtonStyle.over = buttonSkin.newDrawable("background", Color.BLACK);
        textButtonStyle.font = buttonSkin.getFont("default");
        textButtonStyle.fontColor = Color.WHITE;
        textButtonStyle.downFontColor = Color.LIGHT_GRAY;
        buttonSkin.add("default", textButtonStyle);

        // Create checkBox style
        TextButton.TextButtonStyle checkBoxStyle = new TextButton.TextButtonStyle();
        checkBoxStyle.up = buttonSkin.newDrawable("background", Color.WHITE);
        checkBoxStyle.down = buttonSkin.newDrawable("background", Color.WHITE);
        checkBoxStyle.checked = buttonSkin.newDrawable("background", Color.WHITE);
        checkBoxStyle.over = buttonSkin.newDrawable("background", Color.WHITE);
        checkBoxStyle.font = buttonSkin.getFont("default");
        checkBoxStyle.fontColor = Color.BLACK;
        checkBoxStyle.overFontColor = Color.GRAY;
        checkBoxStyle.checkedFontColor = Color.WHITE;
        checkBoxSkin.add("default", checkBoxStyle);

        // Create exit button style
        TextButton.TextButtonStyle exitButtonStyle = new TextButton.TextButtonStyle();
        exitButtonStyle.up = buttonSkin.newDrawable("background", Color.WHITE);
        exitButtonStyle.down = buttonSkin.newDrawable("background", Color.LIGHT_GRAY);
        exitButtonStyle.checked = buttonSkin.newDrawable("background", Color.WHITE);
        exitButtonStyle.over = buttonSkin.newDrawable("background", Color.WHITE);
        exitButtonStyle.font = buttonSkin.getFont("default");
        exitButtonStyle.fontColor = Color.BLACK;
        exitSkin.add("default", exitButtonStyle);
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

        BitmapFont fontTitle = FontManager.fontLarge(Color.WHITE);
        optionsText.setText(fontTitle, TITLE);

        BitmapFont fontButton = FontManager.fontMedium(Color.WHITE);
        changeValueText.setText(fontButton, CHANGE_VALUE);
        checkedText.setText(fontButton, CHECK_MARK);

        BitmapFont fontText = FontManager.fontCustom(Color.WHITE, 24);
        separatorText.setText(fontText, SEPARATOR);
        controlsText.setText(fontText, OPTION_ENTRIES[0]);
        difficultyText.setText(fontText, OPTION_ENTRIES[1]);
        soundsText.setText(fontText, OPTION_ENTRIES[2]);
        vibrationsText.setText(fontText, OPTION_ENTRIES[3]);
        difficultyValueText.setText(fontText, DIFFICULTY_LEVELS[0]);
        controlsValueText.setText(fontText, CONTROL_TYPES[0]);
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
    private int titleY;
    private int textY;
}