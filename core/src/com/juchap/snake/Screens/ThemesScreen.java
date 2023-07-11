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

public class ThemesScreen extends AbstractScreen {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public ThemesScreen() {
        Gdx.input.setInputProcessor(this);

        _themeIconSize = GlobalVars.GRID_WIDTH / 4.f;

        initButtonSkin();
        initGlyphs();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void buildStage() {
        _theme1UpTexture = new Texture(Gdx.files.internal(THEME_1_UP_PATH));
        Drawable theme1UpDrawable = new TextureRegionDrawable(new TextureRegion(_theme1UpTexture));
        _theme1DownTexture = new Texture(Gdx.files.internal(THEME_1_DOWN_PATH));
        Drawable theme1DownDrawable = new TextureRegionDrawable(new TextureRegion(_theme1DownTexture));
        final ImageButton theme1Button = new ImageButton(theme1UpDrawable, theme1DownDrawable);
        theme1Button.getImageCell().expand().fill();
        theme1Button.setPosition(_column1X, _row1Y);
        theme1Button.setSize(_themeIconSize, _themeIconSize);
        theme1Button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ColorManager.setColorTheme(ColorManager.THEME_1);
                ScreenManager.getInstance().showScreen(ScreenEnum.THEMES);
            }
        });
        this.addActor(theme1Button);

        _theme2UpTexture = new Texture(Gdx.files.internal(THEME_2_UP_PATH));
        Drawable theme2UpDrawable = new TextureRegionDrawable(new TextureRegion(_theme2UpTexture));
        _theme2DownTexture = new Texture(Gdx.files.internal(THEME_2_DOWN_PATH));
        Drawable theme2DownDrawable = new TextureRegionDrawable(new TextureRegion(_theme2DownTexture));
        final ImageButton theme2Button = new ImageButton(theme2UpDrawable, theme2DownDrawable);
        theme2Button.getImageCell().expand().fill();
        theme2Button.setPosition(_column2X, _row1Y);
        theme2Button.setSize(_themeIconSize, _themeIconSize);
        theme2Button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ColorManager.setColorTheme(ColorManager.THEME_2);
                ScreenManager.getInstance().showScreen(ScreenEnum.THEMES);
            }
        });
        this.addActor(theme2Button);

        _theme3UpTexture = new Texture(Gdx.files.internal(THEME_3_UP_PATH));
        Drawable theme3UpDrawable = new TextureRegionDrawable(new TextureRegion(_theme3UpTexture));
        _theme3DownTexture = new Texture(Gdx.files.internal(THEME_3_DOWN_PATH));
        Drawable theme3DownDrawable = new TextureRegionDrawable(new TextureRegion(_theme3DownTexture));
        final ImageButton theme3Button = new ImageButton(theme3UpDrawable, theme3DownDrawable);
        theme3Button.getImageCell().expand().fill();
        theme3Button.setPosition(_column1X, _row2Y);
        theme3Button.setSize(_themeIconSize, _themeIconSize);
        theme3Button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ColorManager.setColorTheme(ColorManager.THEME_3);
                ScreenManager.getInstance().showScreen(ScreenEnum.THEMES);
            }
        });
        this.addActor(theme3Button);

        _theme4UpTexture = new Texture(Gdx.files.internal(THEME_4_UP_PATH));
        Drawable theme4UpDrawable = new TextureRegionDrawable(new TextureRegion(_theme4UpTexture));
        _theme4DownTexture = new Texture(Gdx.files.internal(THEME_4_DOWN_PATH));
        Drawable theme4DownDrawable = new TextureRegionDrawable(new TextureRegion(_theme4DownTexture));
        final ImageButton theme4Button = new ImageButton(theme4UpDrawable, theme4DownDrawable);
        theme4Button.getImageCell().expand().fill();
        theme4Button.setPosition(_column2X, _row2Y);
        theme4Button.setSize(_themeIconSize, _themeIconSize);
        theme4Button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ColorManager.setColorTheme(ColorManager.THEME_4);
                ScreenManager.getInstance().showScreen(ScreenEnum.THEMES);
            }
        });
        this.addActor(theme4Button);

        _theme5UpTexture = new Texture(Gdx.files.internal(THEME_5_UP_PATH));
        Drawable theme5UpDrawable = new TextureRegionDrawable(new TextureRegion(_theme5UpTexture));
        _theme5DownTexture = new Texture(Gdx.files.internal(THEME_5_DOWN_PATH));
        Drawable theme5DownDrawable = new TextureRegionDrawable(new TextureRegion(_theme5DownTexture));
        final ImageButton theme5Button = new ImageButton(theme5UpDrawable, theme5DownDrawable);
        theme5Button.getImageCell().expand().fill();
        theme5Button.setPosition(_column1X, _row3Y);
        theme5Button.setSize(_themeIconSize, _themeIconSize);
        theme5Button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ColorManager.setColorTheme(ColorManager.THEME_5);
                ScreenManager.getInstance().showScreen(ScreenEnum.THEMES);
            }
        });
        this.addActor(theme5Button);

        _theme6UpTexture = new Texture(Gdx.files.internal(THEME_6_UP_PATH));
        Drawable theme6UpDrawable = new TextureRegionDrawable(new TextureRegion(_theme6UpTexture));
        _theme6DownTexture = new Texture(Gdx.files.internal(THEME_6_DOWN_PATH));
        Drawable theme6DownDrawable = new TextureRegionDrawable(new TextureRegion(_theme6DownTexture));
        final ImageButton theme6Button = new ImageButton(theme6UpDrawable, theme6DownDrawable);
        theme6Button.getImageCell().expand().fill();
        theme6Button.setPosition(_column2X, _row3Y);
        theme6Button.setSize(_themeIconSize, _themeIconSize);
        theme6Button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ColorManager.setColorTheme(ColorManager.THEME_6);
                ScreenManager.getInstance().showScreen(ScreenEnum.THEMES);
            }
        });
        this.addActor(theme6Button);

        float exitButtonWidth = (GlobalVars.GRID_WIDTH / 3.f);
        float exitButtonHeight = (GlobalVars.GRID_WIDTH / 8.f);
        float exitButtonX = GlobalVars.CENTER_X - (exitButtonWidth / 2.f);
        float exitButtonY = _theme5Y - exitButtonHeight - (6 * GlobalVars.PADDING_Y);
        final TextButton exitButton = new TextButton(EXIT, _exitSkin);
        exitButton.setSize(exitButtonWidth, exitButtonHeight);
        exitButton.setPosition(exitButtonX, exitButtonY);
        exitButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().showScreen(ScreenEnum.OPTIONS);
            }
        });
        this.addActor(exitButton);
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

        _theme1UpTexture.dispose();
        _theme1DownTexture.dispose();
        _theme2UpTexture.dispose();
        _theme2DownTexture.dispose();
        _theme3UpTexture.dispose();
        _theme3DownTexture.dispose();
        _theme4UpTexture.dispose();
        _theme4DownTexture.dispose();
        _theme5UpTexture.dispose();
        _theme5DownTexture.dispose();
        _theme6UpTexture.dispose();
        _theme6DownTexture.dispose();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean keyDown(int keycode) {
        if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK) ) {
            ScreenManager.getInstance().showScreen(ScreenEnum.OPTIONS);
        }
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void drawText() {
        _spriteBatch.begin();
        BitmapFont fontLarge = FontManager.fontLarge(ColorManager.getFrontColor());
        fontLarge.draw(_spriteBatch, _titleText, _titleX, _titleY);

        BitmapFont fontSmall = FontManager.fontSmall(ColorManager.getFrontColor());
        fontSmall.draw(_spriteBatch, _theme1Text, _theme1X, _theme1Y);
        fontSmall.draw(_spriteBatch, _theme2Text, _theme2X, _theme2Y);
        fontSmall.draw(_spriteBatch, _theme3Text, _theme3X, _theme3Y);
        fontSmall.draw(_spriteBatch, _theme4Text, _theme4X, _theme4Y);
        fontSmall.draw(_spriteBatch, _theme5Text, _theme5X, _theme5Y);
        fontSmall.draw(_spriteBatch, _theme6Text, _theme6X, _theme6Y);
        _spriteBatch.end();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void initButtonSkin() {
        // Init
        BitmapFont fontMedium = FontManager.fontMedium(ColorManager.getFrontColor());
        _exitSkin = new Skin();
        _exitSkin.add(DEFAULT, fontMedium);

        // Create texture
        Pixmap pixmap = new Pixmap(GlobalVars.UNIT_SIZE, GlobalVars.UNIT_SIZE, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture pixmapTex = new Texture(pixmap);
        _exitSkin.add(BACKGROUND, pixmapTex);
        pixmap.dispose();

        // Create exit button style
        TextButton.TextButtonStyle exitButtonStyle = new TextButton.TextButtonStyle();
        exitButtonStyle.up = _exitSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        exitButtonStyle.down = _exitSkin.newDrawable(BACKGROUND, ColorManager.getFrontAltColor());
        exitButtonStyle.checked = _exitSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        exitButtonStyle.over = _exitSkin.newDrawable(BACKGROUND, ColorManager.getFrontColor());
        exitButtonStyle.font = _exitSkin.getFont(DEFAULT);
        exitButtonStyle.fontColor = ColorManager.getBackColor();
        _exitSkin.add(DEFAULT, exitButtonStyle);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void initGlyphs() {
        _column1X = GlobalVars.CENTER_X - _themeIconSize - (2.f * GlobalVars.UNIT_SIZE);
        _column2X = GlobalVars.CENTER_X + (2.f * GlobalVars.UNIT_SIZE);

        BitmapFont fontSmall = FontManager.fontSmall(ColorManager.getFrontColor());

        _row2Y = GlobalVars.CENTER_Y - (_themeIconSize / 2.f);
        _theme3Text = new GlyphLayout();
        _theme3Text.setText(fontSmall, THEME_3);
        _theme3X = _column1X + (_themeIconSize / 2.f) - (_theme3Text.width / 2.f);
        _theme3Y = _row2Y - (_theme3Text.height / 2.f);
        _theme4Text = new GlyphLayout();
        _theme4Text.setText(fontSmall, THEME_4);
        _theme4X = _column2X + (_themeIconSize / 2.f) - (_theme4Text.width / 2.f);
        _theme4Y = _theme3Y;

        _row3Y = _theme3Y - _themeIconSize - (2.f * GlobalVars.PADDING_Y);
        _theme5Text = new GlyphLayout();
        _theme5Text.setText(fontSmall, THEME_5);
        _theme5X = _column1X + (_themeIconSize / 2.f) - (_theme5Text.width / 2.f);
        _theme5Y = _row3Y - (_theme5Text.height / 2.f);
        _theme6Text = new GlyphLayout();
        _theme6Text.setText(fontSmall, THEME_6);
        _theme6X = _column2X + (_themeIconSize / 2.f) - (_theme6Text.width / 2.f);
        _theme6Y = _theme5Y;

        _theme1Text = new GlyphLayout();
        _theme1Text.setText(fontSmall, THEME_1);
        _theme1X = _column1X + (_themeIconSize / 2.f) - (_theme1Text.width / 2.f);
        _theme1Y = _row2Y + _themeIconSize + (2.f * GlobalVars.PADDING_Y);
        _theme2Text = new GlyphLayout();
        _theme2Text.setText(fontSmall, THEME_2);
        _theme2X = _column2X + (_themeIconSize / 2.f) - (_theme2Text.width / 2.f);
        _theme2Y = _theme1Y;
        _row1Y = _theme1Y + (_theme1Text.height / 2.f);

        BitmapFont fontLarge = FontManager.fontLarge(ColorManager.getFrontColor());
        _titleText = new GlyphLayout();
        _titleText.setText(fontLarge, THEMES);
        _titleX = GlobalVars.CENTER_X - (_titleText.width / 2.f);
        _titleY = _row1Y + _themeIconSize + _titleText.height + (6.f * GlobalVars.PADDING_Y);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String THEMES = "THEMES";
    private static final String THEME_1 = "RETRO";
    private static final String THEME_2 = "CLASSIC";
    private static final String THEME_3 = "BEACH";
    private static final String THEME_4 = "URBAN";
    private static final String THEME_5 = "SMILE";
    private static final String THEME_6 = "VELOUR";
    private static final String EXIT = "EXIT";
    private static final String THEME_1_UP_PATH = "Textures/Theme1Up.png";
    private static final String THEME_1_DOWN_PATH = "Textures/Theme1Down.png";
    private static final String THEME_2_UP_PATH = "Textures/Theme2Up.png";
    private static final String THEME_2_DOWN_PATH = "Textures/Theme2Down.png";
    private static final String THEME_3_UP_PATH = "Textures/Theme3Up.png";
    private static final String THEME_3_DOWN_PATH = "Textures/Theme3Down.png";
    private static final String THEME_4_UP_PATH = "Textures/Theme4Up.png";
    private static final String THEME_4_DOWN_PATH = "Textures/Theme4Down.png";
    private static final String THEME_5_UP_PATH = "Textures/Theme5Up.png";
    private static final String THEME_5_DOWN_PATH = "Textures/Theme5Down.png";
    private static final String THEME_6_UP_PATH = "Textures/Theme6Up.png";
    private static final String THEME_6_DOWN_PATH = "Textures/Theme6Down.png";
    private static final String DEFAULT = "default";
    private static final String BACKGROUND = "background";

    private Skin _exitSkin;
    private GlyphLayout _titleText;
    private GlyphLayout _theme1Text;
    private GlyphLayout _theme2Text;
    private GlyphLayout _theme3Text;
    private GlyphLayout _theme4Text;
    private GlyphLayout _theme5Text;
    private GlyphLayout _theme6Text;
    private Texture _theme1UpTexture;
    private Texture _theme1DownTexture;
    private Texture _theme2UpTexture;
    private Texture _theme2DownTexture;
    private Texture _theme3UpTexture;
    private Texture _theme3DownTexture;
    private Texture _theme4UpTexture;
    private Texture _theme4DownTexture;
    private Texture _theme5UpTexture;
    private Texture _theme5DownTexture;
    private Texture _theme6UpTexture;
    private Texture _theme6DownTexture;

    private float _column1X;
    private float _column2X;
    private float _theme1X;
    private float _theme2X;
    private float _theme3X;
    private float _theme4X;
    private float _theme5X;
    private float _theme6X;
    private float _theme1Y;
    private float _theme2Y;
    private float _theme3Y;
    private float _theme4Y;
    private float _theme5Y;
    private float _theme6Y;
    private float _titleX;
    private float _titleY;
    private float _row1Y;
    private float _row2Y;
    private float _row3Y;
    private final float _themeIconSize;
}