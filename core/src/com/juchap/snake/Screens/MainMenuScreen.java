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

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public MainMenuScreen() {
        Gdx.input.setInputProcessor(this);

        _buttonWidth = GlobalVars.GRID_WIDTH / 2.f;
        _buttonHeight = GlobalVars.GRID_HEIGHT / 10.f;

        _lastActivityTime = 0;

        initButtonSkin();
        initGlyphs();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void buildStage() {
        float buttonPosY = GlobalVars.CENTER_Y;
        TextButton playButton = new TextButton(PLAY, _buttonSkin);
        playButton.setPosition(GlobalVars.CENTER_X - (_buttonWidth / 2.f), buttonPosY);
        playButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().showScreen(ScreenEnum.GAME);
            }
        });
        this.addActor(playButton);

        buttonPosY -= (_buttonHeight + GlobalVars.UNIT_SIZE);
        TextButton highScoresButton = new TextButton(HIGH_SCORES, _buttonSkin);
        highScoresButton.setPosition(GlobalVars.CENTER_X - (_buttonWidth / 2.f), buttonPosY);
        highScoresButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().showScreen(ScreenEnum.HIGH_SCORE);
            }
        });
        this.addActor(highScoresButton);

        buttonPosY -= (_buttonHeight + GlobalVars.UNIT_SIZE);
        TextButton optionsButton = new TextButton(OPTIONS, _buttonSkin);
        optionsButton.setPosition(GlobalVars.CENTER_X - (_buttonWidth / 2.f), buttonPosY);
        optionsButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().showScreen(ScreenEnum.OPTIONS);
            }
        });
        this.addActor(optionsButton);

        buttonPosY = GlobalVars.BOTTOM + (3f * GlobalVars.UNIT_SIZE);
        float iconSize = GlobalVars.GRID_WIDTH / 8f;

        _achievementTexture = new Texture(Gdx.files.internal(ACHIEVEMENT_TEXTURE));
        Drawable achievementDrawable = new TextureRegionDrawable(new TextureRegion(_achievementTexture));
        final ImageButton achievementButton = new ImageButton(achievementDrawable);
        achievementButton.getImage().setColor(ColorManager.getFrontColor());
        achievementButton.getImageCell().expand().fill();
        achievementButton.setPosition(GlobalVars.CENTER_X - (iconSize / 2.f) - (2.f * iconSize), buttonPosY);
        achievementButton.setSize(iconSize, iconSize);
        achievementButton.addListener( new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event,  float x, float y, int pointer, int button) {
                achievementButton.getImage().setColor(ColorManager.getFrontAltColor());
                return true;
            }
            @Override
            public void touchUp(InputEvent event,  float x, float y, int pointer, int button) {
                achievementButton.getImage().setColor(ColorManager.getFrontColor());
                if (x >= 0 && x < achievementButton.getWidth() && y >= 0 && y < achievementButton.getHeight()) {
                    if (System.currentTimeMillis() - _lastActivityTime > ACTIVITY_MIN_INTERVAL) {
                        ScreenManager.getInstance().showAchievements();
                    }
                }
            }
        });
        this.addActor(achievementButton);

        _leaderboardTexture = new Texture(Gdx.files.internal(LEADERBOARD_TEXTURE));
        Drawable leaderboardDrawable = new TextureRegionDrawable(new TextureRegion(_leaderboardTexture));
        final ImageButton leaderboardButton = new ImageButton(leaderboardDrawable);
        leaderboardButton.getImage().setColor(ColorManager.getFrontColor());
        leaderboardButton.getImageCell().expand().fill();
        leaderboardButton.setPosition(GlobalVars.CENTER_X - (iconSize / 2.f) - (GlobalVars.GRID_WIDTH / 12.f), buttonPosY);
        leaderboardButton.setSize(iconSize, iconSize);
        leaderboardButton.addListener( new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event,  float x, float y, int pointer, int button) {
                leaderboardButton.getImage().setColor(ColorManager.getFrontAltColor());
                return true;
            }
            @Override
            public void touchUp(InputEvent event,  float x, float y, int pointer, int button) {
                leaderboardButton.getImage().setColor(ColorManager.getFrontColor());
                if (x >= 0 && x < leaderboardButton.getWidth() && y >= 0 && y < leaderboardButton.getHeight()) {
                    if (System.currentTimeMillis() - _lastActivityTime > ACTIVITY_MIN_INTERVAL) {
                        ScreenManager.getInstance().showScores();
                    }
                }

            }
        });
        this.addActor(leaderboardButton);

        _rateTexture = new Texture(Gdx.files.internal(RATE_TEXTURE));
        Drawable rateDrawable = new TextureRegionDrawable(new TextureRegion(_rateTexture));
        final ImageButton rateButton = new ImageButton(rateDrawable);
        rateButton.getImage().setColor(ColorManager.getFrontColor());
        rateButton.getImageCell().expand().fill();
        rateButton.setPosition(GlobalVars.CENTER_X - (iconSize / 2.f) + (GlobalVars.GRID_WIDTH / 12.f), buttonPosY);
        rateButton.setSize(iconSize, iconSize);
        rateButton.addListener( new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event,  float x, float y, int pointer, int button) {
                rateButton.getImage().setColor(ColorManager.getFrontAltColor());
                return true;
            }
            @Override
            public void touchUp(InputEvent event,  float x, float y, int pointer, int button) {
                rateButton.getImage().setColor(ColorManager.getFrontColor());
                if (x >= 0 && x < rateButton.getWidth() && y >= 0 && y < rateButton.getHeight()) {
                    if (System.currentTimeMillis() - _lastActivityTime > ACTIVITY_MIN_INTERVAL) {
                        ScreenManager.getInstance().rateGame();
                    }
                }

            }
        });
        this.addActor(rateButton);

        _shareTexture = new Texture(Gdx.files.internal(SHARE_TEXTURE));
        Drawable shareDrawable = new TextureRegionDrawable(new TextureRegion(_shareTexture));
        final ImageButton shareButton = new ImageButton(shareDrawable);
        shareButton.getImage().setColor(ColorManager.getFrontColor());
        shareButton.getImageCell().expand().fill();
        shareButton.setPosition(GlobalVars.CENTER_X - (iconSize / 2.f) + (2.f * iconSize), buttonPosY);
        shareButton.setSize(iconSize, iconSize);
        shareButton.addListener( new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event,  float x, float y, int pointer, int button) {
                shareButton.getImage().setColor(ColorManager.getFrontAltColor());
                return true;
            }
            @Override
            public void touchUp(InputEvent event,  float x, float y, int pointer, int button) {
                shareButton.getImage().setColor(ColorManager.getFrontColor());
                if (x >= 0 && x < shareButton.getWidth() && y >= 0 && y < shareButton.getHeight()) {
                    if (System.currentTimeMillis() - _lastActivityTime > ACTIVITY_MIN_INTERVAL) {
                        ScreenManager.getInstance().shareApp();
                    }
                }

            }
        });
        this.addActor(shareButton);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void resume() {
        _lastActivityTime = System.currentTimeMillis();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void render(float delta) {
        super.render(delta);
        drawText();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void dispose() {
        super.dispose();

        _achievementTexture.dispose();
        _leaderboardTexture.dispose();
        _rateTexture.dispose();
        _shareTexture.dispose();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean keyDown(int keycode) {
        if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK) ) {
            Gdx.app.exit();
        }
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void drawText() {
        _spriteBatch.begin();
        BitmapFont fontLarge = FontManager.fontLarge(ColorManager.getFrontColor());
        fontLarge.draw(_spriteBatch, _titleText1, GlobalVars.CENTER_X - (_titleText1.width / 2.f), GlobalVars.TOP - (3.f * GlobalVars.UNIT_SIZE));

        BitmapFont fontCustom = FontManager.fontCustom(ColorManager.getFrontColor(), 108);
        fontCustom.draw(_spriteBatch, _titleText2, GlobalVars.CENTER_X - (_titleText2.width / 2.f), GlobalVars.TOP - _titleText1.height - (4.f * GlobalVars.UNIT_SIZE));
        _spriteBatch.end();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void initButtonSkin() {
        // Init
        BitmapFont fontMedium = FontManager.fontMedium(ColorManager.getBackColor());
        _buttonSkin = new Skin();
        _buttonSkin.add(DEFAULT, fontMedium);

        // Create texture
        Pixmap pixmap = new Pixmap((int)_buttonWidth, (int)_buttonHeight, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        _buttonSkin.add(BACKGROUND, new Texture(pixmap));
        pixmap.dispose();

        // Create button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = _buttonSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        textButtonStyle.down = _buttonSkin.newDrawable(BACKGROUND, ColorManager.getFrontAltColor());
        textButtonStyle.checked = _buttonSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        textButtonStyle.over = _buttonSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        textButtonStyle.font = _buttonSkin.getFont(DEFAULT);
        textButtonStyle.fontColor = ColorManager.getBackColor();
        _buttonSkin.add(DEFAULT, textButtonStyle);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void initGlyphs() {
        BitmapFont fontLarge = FontManager.fontLarge(ColorManager.getFrontColor());
        _titleText1 = new GlyphLayout();
        _titleText1.setText(fontLarge, TITLE1);

        BitmapFont fontCustom = FontManager.fontCustom(ColorManager.getFrontColor(), 108);
        _titleText2 = new GlyphLayout();
        _titleText2.setText(fontCustom, TITLE2);
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
    private static final String ACHIEVEMENT_TEXTURE = "Textures/achievements.png";
    private static final String LEADERBOARD_TEXTURE = "Textures/leaderboards.png";
    private static final String RATE_TEXTURE = "Textures/rate.png";
    private static final String SHARE_TEXTURE = "Textures/share.png";
    private static final int ACTIVITY_MIN_INTERVAL = 100;

    private Skin _buttonSkin;
    private GlyphLayout _titleText1;
    private GlyphLayout _titleText2;
    private Texture _achievementTexture;
    private Texture _leaderboardTexture;
    private Texture _rateTexture;
    private Texture _shareTexture;

    private final float _buttonWidth;
    private final float _buttonHeight;
    private long _lastActivityTime;
}
