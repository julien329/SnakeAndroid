package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.juchap.snake.Managers.ColorManager;
import com.juchap.snake.Managers.FontManager;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Utility.ScreenEnum;
import com.juchap.snake.Utility.ScreenManager;


public class MainMenuScreen extends AbstractScreen {

    public MainMenuScreen() {
        super();
        Gdx.input.setInputProcessor(this);
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

        achievementTexture = new Texture(Gdx.files.internal(ACHIEVEMENT_TEXTURE));
        Drawable achievementDrawableUp = new TextureRegionDrawable(new TextureRegion(achievementTexture));
        final ImageButton achievementButton = new ImageButton(achievementDrawableUp);
        achievementButton.getImage().setColor(ColorManager.getFrontColor());
        achievementButton.getImageCell().expand().fill();
        achievementButton.setPosition((Gdx.graphics.getWidth() - iconSize) / 2 - (2 * iconSize), buttonPosY);
        achievementButton.setSize(iconSize, iconSize);
        achievementButton.addListener( new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event,  float x, float y, int pointer, int button) {
                achievementButton.getImage().setColor(ColorManager.getFrontAltColor());
                Gdx.graphics.setContinuousRendering(true);
                return true;
            }
            @Override
            public void touchUp(InputEvent event,  float x, float y, int pointer, int button) {
                achievementButton.getImage().setColor(ColorManager.getFrontColor());
                Gdx.graphics.setContinuousRendering(false);
                if(x >= 0 && x < achievementButton.getWidth() && y >= 0 && y < achievementButton.getHeight()) {
                    if(System.currentTimeMillis() - lastActivityTime > ACTIVITY_MIN_INTERVAL)
                        ScreenManager.getInstance().showAchievements();
                }
            }
        });
        this.addActor(achievementButton);

        leaderboardTexture = new Texture(Gdx.files.internal(LEADERBOARD_TEXTURE));
        Drawable leaderboardDrawableUp = new TextureRegionDrawable(new TextureRegion(leaderboardTexture));
        final ImageButton leaderboardButton = new ImageButton(leaderboardDrawableUp);
        leaderboardButton.getImage().setColor(ColorManager.getFrontColor());
        leaderboardButton.getImageCell().expand().fill();
        leaderboardButton.setPosition((Gdx.graphics.getWidth() - iconSize) / 2 - (Gdx.graphics.getWidth() / 12), buttonPosY);
        leaderboardButton.setSize(iconSize, iconSize);
        leaderboardButton.addListener( new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event,  float x, float y, int pointer, int button) {
                leaderboardButton.getImage().setColor(ColorManager.getFrontAltColor());
                Gdx.graphics.setContinuousRendering(true);
                return true;
            }
            @Override
            public void touchUp(InputEvent event,  float x, float y, int pointer, int button) {
                leaderboardButton.getImage().setColor(ColorManager.getFrontColor());
                Gdx.graphics.setContinuousRendering(false);
                if(x >= 0 && x < leaderboardButton.getWidth() && y >= 0 && y < leaderboardButton.getHeight()) {
                    if(System.currentTimeMillis() - lastActivityTime > ACTIVITY_MIN_INTERVAL)
                        ScreenManager.getInstance().showScores();
                }

            }
        });
        this.addActor(leaderboardButton);

        rateTexture = new Texture(Gdx.files.internal(RATE_TEXTURE));
        Drawable rateDrawableUp = new TextureRegionDrawable(new TextureRegion(rateTexture));
        final ImageButton rateButton = new ImageButton(rateDrawableUp);
        rateButton.getImage().setColor(ColorManager.getFrontColor());
        rateButton.getImageCell().expand().fill();
        rateButton.setPosition((Gdx.graphics.getWidth() - iconSize) / 2 + (Gdx.graphics.getWidth()) / 12, buttonPosY);
        rateButton.setSize(iconSize, iconSize);
        rateButton.addListener( new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event,  float x, float y, int pointer, int button) {
                Gdx.graphics.setContinuousRendering(true);
                rateButton.getImage().setColor(ColorManager.getFrontAltColor());
                return true;
            }
            @Override
            public void touchUp(InputEvent event,  float x, float y, int pointer, int button) {
                rateButton.getImage().setColor(ColorManager.getFrontColor());
                Gdx.graphics.setContinuousRendering(false);
                if(x >= 0 && x < rateButton.getWidth() && y >= 0 && y < rateButton.getHeight()) {
                    if(System.currentTimeMillis() - lastActivityTime > ACTIVITY_MIN_INTERVAL)
                        ScreenManager.getInstance().rateGame();
                }

            }
        });
        this.addActor(rateButton);

        shareTexture = new Texture(Gdx.files.internal(SHARE_TEXTURE));
        Drawable shareDrawableUp = new TextureRegionDrawable(new TextureRegion(shareTexture));
        final ImageButton shareButton = new ImageButton(shareDrawableUp);
        shareButton.getImage().setColor(ColorManager.getFrontColor());
        shareButton.getImageCell().expand().fill();
        shareButton.setPosition((Gdx.graphics.getWidth() - iconSize) / 2 + (2 * iconSize), buttonPosY);
        shareButton.setSize(iconSize, iconSize);
        shareButton.addListener( new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event,  float x, float y, int pointer, int button) {
                Gdx.graphics.setContinuousRendering(true);
                shareButton.getImage().setColor(ColorManager.getFrontAltColor());
                return true;
            }
            @Override
            public void touchUp(InputEvent event,  float x, float y, int pointer, int button) {
                shareButton.getImage().setColor(ColorManager.getFrontColor());
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
        drawText();
    }

    @Override
    public void dispose() {
        super.dispose();
        achievementTexture.dispose();
        leaderboardTexture.dispose();
        rateTexture.dispose();
        shareTexture.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK) ) {
            Gdx.app.exit();
        }
        return false;
    }

    private void drawText() {
        spriteBatch.begin();
        BitmapFont fontLarge = FontManager.fontLarge(ColorManager.getFrontColor());
        fontLarge.draw(spriteBatch, titleText1, (Gdx.graphics.getWidth() - titleText1.width) / 2, Gdx.graphics.getHeight() - (3 * GlobalVars.UNIT_SIZE));

        BitmapFont fontCustom = FontManager.fontCustom(ColorManager.getFrontColor(), 108);
        fontCustom.draw(spriteBatch, titleText2, (Gdx.graphics.getWidth() - titleText2.width) / 2, Gdx.graphics.getHeight() - titleText1.height - (4 * GlobalVars.UNIT_SIZE));
        spriteBatch.end();
    }

    private void initButtonSkin() {
        // Init
        BitmapFont fontMedium = FontManager.fontMedium(ColorManager.getBackColor());
        buttonSkin = new Skin();
        buttonSkin.add(DEFAULT, fontMedium);

        // Create texture
        Pixmap pixmap = new Pixmap(buttonWidth, buttonHeight, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        buttonSkin.add(BACKGROUND, new Texture(pixmap));
        pixmap.dispose();

        // Create button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = buttonSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        textButtonStyle.down = buttonSkin.newDrawable(BACKGROUND, ColorManager.getFrontAltColor());
        textButtonStyle.checked = buttonSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        textButtonStyle.over = buttonSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        textButtonStyle.font = buttonSkin.getFont(DEFAULT);
        textButtonStyle.fontColor = ColorManager.getBackColor();
        buttonSkin.add(DEFAULT, textButtonStyle);
    }

    private void initGlyphs() {
        BitmapFont fontLarge = FontManager.fontLarge(ColorManager.getFrontColor());
        titleText1 = new GlyphLayout();
        titleText1.setText(fontLarge, TITLE1);

        BitmapFont fontCustom = FontManager.fontCustom(ColorManager.getFrontColor(), 108);
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
    private static final String ACHIEVEMENT_TEXTURE = "Textures/achievements_up.png";
    private static final String LEADERBOARD_TEXTURE = "Textures/leaderboards_up.png";
    private static final String RATE_TEXTURE = "Textures/rate_up.png";
    private static final String SHARE_TEXTURE = "Textures/share_up.png";
    private static final int ACTIVITY_MIN_INTERVAL = 100;

    private Skin buttonSkin;
    private GlyphLayout titleText1;
    private GlyphLayout titleText2;
    private Texture achievementTexture;
    private Texture leaderboardTexture;
    private Texture rateTexture;
    private Texture shareTexture;

    private int buttonWidth;
    private int buttonHeight;
    private long lastActivityTime;
}
