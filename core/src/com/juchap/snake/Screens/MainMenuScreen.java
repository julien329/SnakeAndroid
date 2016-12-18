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


public class MainMenuScreen extends AbstractScreen {

    public MainMenuScreen() {
        super();
        Gdx.input.setInputProcessor(this);

        borders = new ShapeRenderer();
        batch = new SpriteBatch();

        leftBorderX = GlobalVars.GRID_OFFSET_X;
        rightBorderX = GlobalVars.GRID_OFFSET_X + GlobalVars.GRID_WIDTH - GlobalVars.UNIT_SIZE;
        bottomBorderY = GlobalVars.GRID_OFFSET_Y;
        topBorderY = GlobalVars.GRID_OFFSET_Y + GlobalVars.GRID_HEIGHT - GlobalVars.UNIT_SIZE;

        buttonWidth = Gdx.graphics.getWidth() / 2;
        buttonHeight = Gdx.graphics.getHeight() / 10;

        initButtonSkin();
    }

    @Override
    public void buildStage() {
        int buttonPosY = Gdx.graphics.getHeight() / 2;
        TextButton playButton = new TextButton("PLAY", buttonSkin);
        playButton.setPosition((Gdx.graphics.getWidth() - buttonWidth) / 2, buttonPosY);
        playButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().showScreen(ScreenEnum.GAME);
            };
        });
        this.addActor(playButton);

        buttonPosY -= (buttonHeight + 2 * GlobalVars.UNIT_SIZE);
        TextButton highScoresButton = new TextButton("HIGH SCORES", buttonSkin);
        highScoresButton.setPosition((Gdx.graphics.getWidth() - buttonWidth) / 2, buttonPosY);
        highScoresButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().showScreen(ScreenEnum.HIGH_SCORE);
            };
        });
        this.addActor(highScoresButton);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        drawBorders();
        drawTitle();
    }

    @Override
    public void dispose() {
        super.dispose();
        borders.dispose();
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK) ) {
            Gdx.app.exit();
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

    private void drawTitle() {
        // Prepare text GlyphLayout
        BitmapFont titleFont = FontManager.fontCustom(Color.WHITE, 128);
        GlyphLayout titleText = new GlyphLayout();
        titleText.setText(titleFont, "SNAKE");

        // Draw score text
        batch.begin();
        titleFont.draw(batch, titleText, (Gdx.graphics.getWidth() - titleText.width) / 2, Gdx.graphics.getHeight() - (3 * GlobalVars.UNIT_SIZE));
        batch.end();
    }

    private void initButtonSkin() {
        // Init
        BitmapFont font = FontManager.fontMedium(Color.BLACK);
        buttonSkin = new Skin();
        buttonSkin.add("default", font);

        // Create texture
        Pixmap pixmap = new Pixmap(buttonWidth, buttonHeight, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        buttonSkin.add("background",new Texture(pixmap));
        pixmap.dispose();

        // Create button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = buttonSkin.newDrawable("background", Color.WHITE);
        textButtonStyle.down = buttonSkin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.checked = buttonSkin.newDrawable("background", Color.WHITE);
        textButtonStyle.over = buttonSkin.newDrawable("background", Color.WHITE);
        textButtonStyle.font = buttonSkin.getFont("default");
        textButtonStyle.fontColor = Color.BLACK;
        buttonSkin.add("default", textButtonStyle);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private ShapeRenderer borders;
    private SpriteBatch batch;
    private Skin buttonSkin;

    private int leftBorderX;
    private int rightBorderX;
    private int topBorderY;
    private int bottomBorderY;

    private int buttonWidth;
    private int buttonHeight;
}
