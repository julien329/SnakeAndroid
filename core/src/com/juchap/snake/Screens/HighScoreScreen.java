package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.juchap.snake.Utility.FontManager;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Utility.HighScoreManager;
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
    }

    @Override
    public void buildStage() {
        BitmapFont medium = FontManager.fontMedium(Color.WHITE);
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
        BitmapFont fontTitle = FontManager.fontCustom(Color.WHITE, 56);
        GlyphLayout highScoreText = new GlyphLayout();
        highScoreText.setText(fontTitle, "HIGH SCORES");
        float textPosY = ( 9 * Gdx.graphics.getHeight()) / 10;

        batch.begin();
        fontTitle.draw(batch, highScoreText, (Gdx.graphics.getWidth() - highScoreText.width) / 2, textPosY);
        batch.end();

        textPosY -= (12 * GlobalVars.PADDING_Y);

        BitmapFont fontScores = FontManager.fontMedium(Color.WHITE);
        GlyphLayout difficultyText= new GlyphLayout();
        difficultyText.setText(fontScores, DIFFICULTY_LEVELS[difficultyIndex]);

        batch.begin();
        fontScores.draw(batch, difficultyText, (Gdx.graphics.getWidth() - difficultyText.width) / 2, textPosY);
        batch.end();

        textPosY -= (2 * GlobalVars.PADDING_Y);

        for(int i = 0; i < TABLE_SIZE; i++) {
            GlyphLayout score = new GlyphLayout();
            String scoreText = formatScore(HighScoreManager.getScore(i, difficultyIndex));
            score.setText(fontScores, RANKS[i] + "    " + scoreText);
            textPosY -= (2.5 * GlobalVars.PADDING_Y);

            batch.begin();
            fontScores.draw(batch, score, (Gdx.graphics.getWidth() - score.width) / 2, textPosY);
            batch.end();
        }
    }

    private String formatScore(int score) {
        String scoreText = String.valueOf(score);
        String zeros = "";

        for(int i = 0; i < 4 - scoreText.length(); i++) {
            zeros += "0";
        }

        return zeros + scoreText;
    }

    private void initButtonSkin() {
        // Init
        BitmapFont font = FontManager.fontMedium(Color.WHITE);
        arrowSkin = new Skin();
        exitSkin = new Skin();
        arrowSkin.add("default", font);
        exitSkin.add("default", font);

        // Create texture
        Pixmap pixmap = new Pixmap((int)Gdx.graphics.getWidth() / 3, (int)Gdx.graphics.getHeight() / 8, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        arrowSkin.add("background",new Texture(pixmap));
        exitSkin.add("background",new Texture(pixmap));
        pixmap.dispose();

        // Create button style
        TextButton.TextButtonStyle arrowButtonStyle = new TextButton.TextButtonStyle();
        arrowButtonStyle.up = arrowSkin.newDrawable("background", Color.BLACK);
        arrowButtonStyle.down = arrowSkin.newDrawable("background", Color.BLACK);
        arrowButtonStyle.checked = arrowSkin.newDrawable("background", Color.BLACK);
        arrowButtonStyle.over = arrowSkin.newDrawable("background", Color.BLACK);
        arrowButtonStyle.font = arrowSkin.getFont("default");
        arrowButtonStyle.fontColor = Color.WHITE;
        arrowButtonStyle.downFontColor = Color.LIGHT_GRAY;
        arrowSkin.add("default", arrowButtonStyle);

        // Create exit button style
        TextButton.TextButtonStyle exitButtonStyle = new TextButton.TextButtonStyle();
        exitButtonStyle.up = exitSkin.newDrawable("background", Color.WHITE);
        exitButtonStyle.down = exitSkin.newDrawable("background", Color.LIGHT_GRAY);
        exitButtonStyle.checked = exitSkin.newDrawable("background", Color.WHITE);
        exitButtonStyle.over = exitSkin.newDrawable("background", Color.WHITE);
        exitButtonStyle.font = exitSkin.getFont("default");
        exitButtonStyle.fontColor = Color.BLACK;
        exitSkin.add("default", exitButtonStyle);
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

    final TextButton arrowLeftButton;
    final TextButton arrowRightButton;
    private ShapeRenderer borders;
    private SpriteBatch batch;
    private Skin arrowSkin;
    private Skin exitSkin;

    private int leftBorderX;
    private int rightBorderX;
    private int topBorderY;
    private int bottomBorderY;
    private int difficultyIndex;
}
