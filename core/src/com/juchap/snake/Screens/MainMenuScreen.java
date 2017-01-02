package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
        lastActivityTime = 0;

        initButtonSkin();
        initGlyphs();
    }

    @Override
    public void buildStage() {
        int buttonPosY = Gdx.graphics.getHeight() / 2;
        TextButton playButton = new TextButton(PLAY, buttonSkin);
        playButton.setPosition((Gdx.graphics.getWidth() - buttonWidth) / 2, buttonPosY);
        playButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().showScreen(ScreenEnum.GAME);
            };
        });
        this.addActor(playButton);

        buttonPosY -= (buttonHeight + GlobalVars.UNIT_SIZE);
        TextButton highScoresButton = new TextButton(HIGH_SCORES, buttonSkin);
        highScoresButton.setPosition((Gdx.graphics.getWidth() - buttonWidth) / 2, buttonPosY);
        highScoresButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().showScreen(ScreenEnum.HIGH_SCORE);
            };
        });
        this.addActor(highScoresButton);

        buttonPosY -= (buttonHeight + GlobalVars.UNIT_SIZE);
        TextButton optionsButton = new TextButton(OPTIONS, buttonSkin);
        optionsButton.setPosition((Gdx.graphics.getWidth() - buttonWidth) / 2, buttonPosY);
        optionsButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().showScreen(ScreenEnum.OPTIONS);
            };
        });
        this.addActor(optionsButton);

        buttonPosY = 3 * GlobalVars.UNIT_SIZE;
        int iconSize = Gdx.graphics.getWidth() / 8;

        achievementTextureUp = new Texture(Gdx.files.internal(ACHIEVEMENT_UP));
        Drawable achievementDrawableUp = new TextureRegionDrawable(new TextureRegion(achievementTextureUp));
        achievementTextureDown = new Texture(Gdx.files.internal(ACHIEVEMENT_DOWN));
        Drawable achievementDrawableDown = new TextureRegionDrawable(new TextureRegion(achievementTextureDown));
        final ImageButton achievementButton = new ImageButton(achievementDrawableUp, achievementDrawableDown);
        achievementButton.getImageCell().expand().fill();
        achievementButton.setPosition((Gdx.graphics.getWidth() - iconSize) / 2 - (2 * iconSize), buttonPosY);
        achievementButton.setSize(iconSize, iconSize);
        achievementButton.addListener( new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event,  float x, float y, int pointer, int button) {
                Gdx.graphics.setContinuousRendering(true);
                return true;
            }
            @Override
            public void touchUp(InputEvent event,  float x, float y, int pointer, int button) {
                Gdx.graphics.setContinuousRendering(false);
                if(x >= 0 && x < achievementButton.getWidth() && y >= 0 && y < achievementButton.getHeight()) {
                    if(System.currentTimeMillis() - lastActivityTime > ACTIVITY_MIN_INTERVAL)
                        ScreenManager.getInstance().showAchievements();
                }
            }
        });
        this.addActor(achievementButton);

        leaderboardTextureUp = new Texture(Gdx.files.internal(LEADERBOARD_UP));
        Drawable leaderboardDrawableUp = new TextureRegionDrawable(new TextureRegion(leaderboardTextureUp));
        leaderboardTextureDown = new Texture(Gdx.files.internal(LEADERBOARD_DOWN));
        Drawable leaderboardDrawableDown = new TextureRegionDrawable(new TextureRegion(leaderboardTextureDown));
        final ImageButton leaderboardButton = new ImageButton(leaderboardDrawableUp, leaderboardDrawableDown);
        leaderboardButton.getImageCell().expand().fill();
        leaderboardButton.setPosition((Gdx.graphics.getWidth() - iconSize) / 2 - (Gdx.graphics.getWidth() / 12), buttonPosY);
        leaderboardButton.setSize(iconSize, iconSize);
        leaderboardButton.addListener( new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event,  float x, float y, int pointer, int button) {
                Gdx.graphics.setContinuousRendering(true);
                return true;
            }
            @Override
            public void touchUp(InputEvent event,  float x, float y, int pointer, int button) {
                Gdx.graphics.setContinuousRendering(false);
                if(x >= 0 && x < leaderboardButton.getWidth() && y >= 0 && y < leaderboardButton.getHeight()) {
                    if(System.currentTimeMillis() - lastActivityTime > ACTIVITY_MIN_INTERVAL)
                        ScreenManager.getInstance().showScores();
                }

            }
        });
        this.addActor(leaderboardButton);

        rateTextureUp = new Texture(Gdx.files.internal(RATE_UP));
        Drawable rateDrawableUp = new TextureRegionDrawable(new TextureRegion(rateTextureUp));
        rateTextureDown = new Texture(Gdx.files.internal(RATE_DOWN));
        Drawable rateDrawableDown = new TextureRegionDrawable(new TextureRegion(rateTextureDown));
        final ImageButton rateButton = new ImageButton(rateDrawableUp, rateDrawableDown);
        rateButton.getImageCell().expand().fill();
        rateButton.setPosition((Gdx.graphics.getWidth() - iconSize) / 2 + (Gdx.graphics.getWidth()) / 12, buttonPosY);
        rateButton.setSize(iconSize, iconSize);
        rateButton.addListener( new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event,  float x, float y, int pointer, int button) {
                Gdx.graphics.setContinuousRendering(true);
                return true;
            }
            @Override
            public void touchUp(InputEvent event,  float x, float y, int pointer, int button) {
                Gdx.graphics.setContinuousRendering(false);
                if(x >= 0 && x < rateButton.getWidth() && y >= 0 && y < rateButton.getHeight()) {
                    if(System.currentTimeMillis() - lastActivityTime > ACTIVITY_MIN_INTERVAL)
                        ScreenManager.getInstance().rateGame();
                }

            }
        });
        this.addActor(rateButton);

        shareTextureUp = new Texture(Gdx.files.internal(SHARE_UP));
        Drawable shareDrawableUp = new TextureRegionDrawable(new TextureRegion(shareTextureUp));
        shareTextureDown = new Texture(Gdx.files.internal(SHARE_DOWN));
        Drawable shareDrawableDown = new TextureRegionDrawable(new TextureRegion(shareTextureDown));
        final ImageButton shareButton = new ImageButton(shareDrawableUp, shareDrawableDown);
        shareButton.getImageCell().expand().fill();
        shareButton.setPosition((Gdx.graphics.getWidth() - iconSize) / 2 + (2 * iconSize), buttonPosY);
        shareButton.setSize(iconSize, iconSize);
        shareButton.addListener( new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event,  float x, float y, int pointer, int button) {
                Gdx.graphics.setContinuousRendering(true);
                return true;
            }
            @Override
            public void touchUp(InputEvent event,  float x, float y, int pointer, int button) {
                Gdx.graphics.setContinuousRendering(false);
                if(x >= 0 && x < shareButton.getWidth() && y >= 0 && y < shareButton.getHeight()) {
                    if(System.currentTimeMillis() - lastActivityTime > ACTIVITY_MIN_INTERVAL)
                        ScreenManager.getInstance().shareApp();
                }

            }
        });
        this.addActor(shareButton);
    }

    @Override
    public void resume() {
        lastActivityTime = System.currentTimeMillis();
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
        achievementTextureUp.dispose();
        achievementTextureDown.dispose();
        leaderboardTextureUp.dispose();
        leaderboardTextureDown.dispose();
        rateTextureUp.dispose();
        rateTextureDown.dispose();
        shareTextureUp.dispose();
        shareTextureDown.dispose();
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
        batch.begin();
        BitmapFont fontLarge = FontManager.fontLarge(Color.WHITE);
        fontLarge.draw(batch, titleText1, (Gdx.graphics.getWidth() - titleText1.width) / 2, Gdx.graphics.getHeight() - (3 * GlobalVars.UNIT_SIZE));

        BitmapFont fontCustom = FontManager.fontCustom(Color.WHITE, 108);
        fontCustom.draw(batch, titleText2, (Gdx.graphics.getWidth() - titleText2.width) / 2, Gdx.graphics.getHeight() - titleText1.height - (4 * GlobalVars.UNIT_SIZE));
        batch.end();
    }

    private void initButtonSkin() {
        // Init
        BitmapFont fontMedium = FontManager.fontMedium(Color.BLACK);
        buttonSkin = new Skin();
        buttonSkin.add(DEFAULT, fontMedium);

        // Create texture
        Pixmap pixmap = new Pixmap(buttonWidth, buttonHeight, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        buttonSkin.add(BACKGROUND,new Texture(pixmap));
        pixmap.dispose();

        // Create button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = buttonSkin.newDrawable(BACKGROUND, Color.WHITE);
        textButtonStyle.down = buttonSkin.newDrawable(BACKGROUND, Color.LIGHT_GRAY);
        textButtonStyle.checked = buttonSkin.newDrawable(BACKGROUND, Color.WHITE);
        textButtonStyle.over = buttonSkin.newDrawable(BACKGROUND, Color.WHITE);
        textButtonStyle.font = buttonSkin.getFont(DEFAULT);
        textButtonStyle.fontColor = Color.BLACK;
        buttonSkin.add(DEFAULT, textButtonStyle);
    }

    private void initGlyphs() {
        BitmapFont fontLarge = FontManager.fontLarge(Color.WHITE);
        titleText1 = new GlyphLayout();
        titleText1.setText(fontLarge, TITLE1);

        BitmapFont fontCustom = FontManager.fontCustom(Color.WHITE, 108);
        titleText2 = new GlyphLayout();
        titleText2.setText(fontCustom, TITLE2);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String TITLE1 = "RETRO";
    private static final String TITLE2 = "SNAKE";
    private static final String PLAY = "PLAY";
    private static final String HIGH_SCORES = "HIGH SCORES";
    private static final String OPTIONS = "OPTIONS";
    private static final String BACKGROUND = "background";
    private static final String DEFAULT = "default";
    private static final String ACHIEVEMENT_UP = "Textures/achievements_up.png";
    private static final String ACHIEVEMENT_DOWN = "Textures/achievements_down.png";
    private static final String LEADERBOARD_UP = "Textures/leaderboards_up.png";
    private static final String LEADERBOARD_DOWN = "Textures/leaderboards_down.png";
    private static final String RATE_UP = "Textures/rate_up.png";
    private static final String RATE_DOWN = "Textures/rate_down.png";
    private static final String SHARE_UP = "Textures/share_up.png";
    private static final String SHARE_DOWN = "Textures/share_down.png";
    private static final int ACTIVITY_MIN_INTERVAL = 100;

    private ShapeRenderer borders;
    private SpriteBatch batch;
    private Skin buttonSkin;
    private GlyphLayout titleText1;
    private GlyphLayout titleText2;
    private Texture achievementTextureUp;
    private Texture achievementTextureDown;
    private Texture leaderboardTextureUp;
    private Texture leaderboardTextureDown;
    private Texture rateTextureUp;
    private Texture rateTextureDown;
    private Texture shareTextureUp;
    private Texture shareTextureDown;

    private int leftBorderX;
    private int rightBorderX;
    private int topBorderY;
    private int bottomBorderY;
    private int buttonWidth;
    private int buttonHeight;
    private long lastActivityTime;
}
