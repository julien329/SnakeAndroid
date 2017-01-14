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
import com.badlogic.gdx.utils.StringBuilder;
import com.juchap.snake.Managers.ColorManager;
import com.juchap.snake.Managers.FontManager;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Managers.HighScoreManager;
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
        difficultyIndex = 0;

        initButtonSkin();
        arrowLeftButton = new TextButton(ARROW_LEFT, arrowSkin);
        arrowRightButton = new TextButton(ARROW_RIGHT, arrowSkin);

        initGlyphs();
    }

    @Override
    public void buildStage() {
        BitmapFont medium = FontManager.fontMedium(ColorManager.getFrontColor());
        GlyphLayout arrowText = new GlyphLayout();
        arrowText.setText(medium, ARROW_LEFT);

        float buttonWidth = arrowText.width;
        float buttonHeight = arrowText.height;
        int buttonPosY = (int)(Gdx.graphics.getHeight() - buttonHeight) / 2;

        // ArrowLeft button
        int buttonPosX = GlobalVars.GRID_OFFSET_X + GlobalVars.UNIT_SIZE;
        arrowLeftButton.setSize(buttonWidth, buttonHeight);
        arrowLeftButton.setPosition(buttonPosX, buttonPosY);
        arrowLeftButton.setVisible(false);
        arrowLeftButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                arrowRightButton.setVisible(true);
                difficultyIndex--;
                if (difficultyIndex <= 0){
                    difficultyIndex = 0;
                    arrowLeftButton.setVisible(false);
                }
                initGlyphs();
            };
        });
        this.addActor(arrowLeftButton);

        // ArrowRight button
        buttonPosX = GlobalVars.GRID_OFFSET_X + GlobalVars.GRID_WIDTH - GlobalVars.UNIT_SIZE - (int)buttonWidth;
        arrowRightButton.setSize(buttonWidth, buttonHeight);
        arrowRightButton.setPosition(buttonPosX, buttonPosY);
        arrowRightButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                arrowLeftButton.setVisible(true);
                difficultyIndex++;
                if (difficultyIndex >= DIFFICULTY_LEVELS.length - 1){
                    difficultyIndex = DIFFICULTY_LEVELS.length - 1;
                    arrowRightButton.setVisible(false);
                }
                initGlyphs();
            };
        });
        this.addActor(arrowRightButton);

        // Exit button
        buttonWidth = (Gdx.graphics.getWidth() / 3);
        buttonHeight = (Gdx.graphics.getWidth() / 8);
        buttonPosY = GlobalVars.GRID_OFFSET_Y + GlobalVars.UNIT_SIZE + 4 * GlobalVars.PADDING_Y;
        buttonPosX = (int)(Gdx.graphics.getWidth() - buttonWidth) / 2;
        final TextButton exitButton = new TextButton(EXIT, exitSkin);
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
        BitmapFont fontCustom = FontManager.fontCustom(ColorManager.getFrontColor(), 56);
        fontCustom.draw(batch, highScoreText, highScoreX, highScoreY);

        BitmapFont fontMedium = FontManager.fontMedium(ColorManager.getFrontColor());
        fontMedium.draw(batch, difficultyText, difficultyTextX, difficultyTextY);
        for(int i = 0; i < TABLE_SIZE; i++) {
            fontMedium.draw(batch, entriesText[i], entriesX, entriesY[i]);
        }
        batch.end();
    }

    private StringBuilder formatScore(int score) {
        String scoreText = String.valueOf(score);
        StringBuilder scoreFormatted = new StringBuilder();
        for(int i = 0; i < 4 - scoreText.length(); i++) {
            scoreFormatted.append(ZERO);
        }
        return scoreFormatted.append(scoreText);
    }

    private void initButtonSkin() {
        // Init
        BitmapFont fontMedium = FontManager.fontMedium(ColorManager.getFrontColor());
        arrowSkin = new Skin();
        exitSkin = new Skin();
        arrowSkin.add(DEFAULT, fontMedium);
        exitSkin.add(DEFAULT, fontMedium);

        // Create texture
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 8, Pixmap.Format.RGB888);
        pixmap.setColor(ColorManager.getFrontColor());
        pixmap.fill();
        arrowSkin.add(BACKGROUND, new Texture(pixmap));
        exitSkin.add(BACKGROUND, new Texture(pixmap));
        pixmap.dispose();

        // Create button style
        TextButton.TextButtonStyle arrowButtonStyle = new TextButton.TextButtonStyle();
        arrowButtonStyle.up = arrowSkin.newDrawable(BACKGROUND, ColorManager.getBackColor());
        arrowButtonStyle.down = arrowSkin.newDrawable(BACKGROUND, ColorManager.getBackColor());
        arrowButtonStyle.checked = arrowSkin.newDrawable(BACKGROUND, ColorManager.getBackColor());
        arrowButtonStyle.over = arrowSkin.newDrawable(BACKGROUND, ColorManager.getBackColor());
        arrowButtonStyle.font = arrowSkin.getFont(DEFAULT);
        arrowButtonStyle.fontColor = ColorManager.getFrontColor();
        arrowButtonStyle.downFontColor = ColorManager.getFrontAltColor();
        arrowSkin.add(DEFAULT, arrowButtonStyle);

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
        BitmapFont fontCustom = FontManager.fontCustom(ColorManager.getFrontColor(), 56);
        highScoreText = new GlyphLayout();
        highScoreText.setText(fontCustom, HIGH_SCORE);
        highScoreX = (int)(Gdx.graphics.getWidth() - highScoreText.width) / 2;
        highScoreY = (9 * Gdx.graphics.getHeight()) / 10;

        BitmapFont fontMedium = FontManager.fontMedium(ColorManager.getFrontColor());
        difficultyText = new GlyphLayout();
        difficultyText.setText(fontMedium, DIFFICULTY_LEVELS[difficultyIndex]);
        difficultyTextX = (int)(Gdx.graphics.getWidth() - difficultyText.width) / 2;
        difficultyTextY = highScoreY - (12 * GlobalVars.PADDING_Y);

        int textPosY = difficultyTextY - (2 * GlobalVars.PADDING_Y);
        entriesY = new int[TABLE_SIZE];
        entriesText = new GlyphLayout[TABLE_SIZE];
        for(int i = 0; i < TABLE_SIZE; i++) {
            StringBuilder entryText = new StringBuilder(RANKS[i]).append(SPACE4).append(formatScore(HighScoreManager.getScore(i, difficultyIndex)));
            entriesText[i] = new GlyphLayout();
            entriesText[i].setText(fontMedium, entryText);
            textPosY -= (2.5 * GlobalVars.PADDING_Y);
            entriesY[i] = textPosY;
        }
        entriesX = (int)(Gdx.graphics.getWidth() - entriesText[0].width) / 2;
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

    private final TextButton arrowLeftButton;
    private final TextButton arrowRightButton;
    private ShapeRenderer borders;
    private SpriteBatch batch;
    private Skin arrowSkin;
    private Skin exitSkin;
    private GlyphLayout[] entriesText;
    private GlyphLayout highScoreText;
    private GlyphLayout difficultyText;

    private int leftBorderX;
    private int rightBorderX;
    private int topBorderY;
    private int bottomBorderY;
    private int difficultyIndex;
    private int highScoreX;
    private int highScoreY;
    private int difficultyTextX;
    private int difficultyTextY;
    private int entriesX;
    private int[] entriesY;
}
