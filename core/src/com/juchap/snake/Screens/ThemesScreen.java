package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

    public ThemesScreen() {
        super();
        Gdx.input.setInputProcessor(this);
        themeIconSize = GlobalVars.GRID_WIDTH / 4;

        initButtonSkin();
        initGlyphs();
    }

    @Override
    public void buildStage() {
        theme1UpTexture = new Texture(Gdx.files.internal(THEME_1_UP_PATH));
        Drawable theme1UpDrawable = new TextureRegionDrawable(new TextureRegion(theme1UpTexture));
        theme1DownTexture = new Texture(Gdx.files.internal(THEME_1_DOWN_PATH));
        Drawable theme1DownDrawable = new TextureRegionDrawable(new TextureRegion(theme1DownTexture));
        final ImageButton theme1Button = new ImageButton(theme1UpDrawable, theme1DownDrawable);
        theme1Button.getImageCell().expand().fill();
        theme1Button.setPosition(column1X, row1Y);
        theme1Button.setSize(themeIconSize, themeIconSize);
        theme1Button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ColorManager.setColorTheme(ColorManager.THEME_1);
                ScreenManager.getInstance().showScreen(ScreenEnum.THEMES);
            }
        });
        this.addActor(theme1Button);

        theme2UpTexture = new Texture(Gdx.files.internal(THEME_2_UP_PATH));
        Drawable theme2UpDrawable = new TextureRegionDrawable(new TextureRegion(theme2UpTexture));
        theme2DownTexture = new Texture(Gdx.files.internal(THEME_2_DOWN_PATH));
        Drawable theme2DownDrawable = new TextureRegionDrawable(new TextureRegion(theme2DownTexture));
        final ImageButton theme2Button = new ImageButton(theme2UpDrawable, theme2DownDrawable);
        theme2Button.getImageCell().expand().fill();
        theme2Button.setPosition(column2X, row1Y);
        theme2Button.setSize(themeIconSize, themeIconSize);
        theme2Button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ColorManager.setColorTheme(ColorManager.THEME_2);
                ScreenManager.getInstance().showScreen(ScreenEnum.THEMES);
            }
        });
        this.addActor(theme2Button);

        theme3UpTexture = new Texture(Gdx.files.internal(THEME_3_UP_PATH));
        Drawable theme3UpDrawable = new TextureRegionDrawable(new TextureRegion(theme3UpTexture));
        theme3DownTexture = new Texture(Gdx.files.internal(THEME_3_DOWN_PATH));
        Drawable theme3DownDrawable = new TextureRegionDrawable(new TextureRegion(theme3DownTexture));
        final ImageButton theme3Button = new ImageButton(theme3UpDrawable, theme3DownDrawable);
        theme3Button.getImageCell().expand().fill();
        theme3Button.setPosition(column1X, row2Y);
        theme3Button.setSize(themeIconSize, themeIconSize);
        theme3Button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ColorManager.setColorTheme(ColorManager.THEME_3);
                ScreenManager.getInstance().showScreen(ScreenEnum.THEMES);
            }
        });
        this.addActor(theme3Button);

        theme4UpTexture = new Texture(Gdx.files.internal(THEME_4_UP_PATH));
        Drawable theme4UpDrawable = new TextureRegionDrawable(new TextureRegion(theme4UpTexture));
        theme4DownTexture = new Texture(Gdx.files.internal(THEME_4_DOWN_PATH));
        Drawable theme4DownDrawable = new TextureRegionDrawable(new TextureRegion(theme4DownTexture));
        final ImageButton theme4Button = new ImageButton(theme4UpDrawable, theme4DownDrawable);
        theme4Button.getImageCell().expand().fill();
        theme4Button.setPosition(column2X, row2Y);
        theme4Button.setSize(themeIconSize, themeIconSize);
        theme4Button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ColorManager.setColorTheme(ColorManager.THEME_4);
                ScreenManager.getInstance().showScreen(ScreenEnum.THEMES);
            }
        });
        this.addActor(theme4Button);

        theme5UpTexture = new Texture(Gdx.files.internal(THEME_5_UP_PATH));
        Drawable theme5UpDrawable = new TextureRegionDrawable(new TextureRegion(theme5UpTexture));
        theme5DownTexture = new Texture(Gdx.files.internal(THEME_5_DOWN_PATH));
        Drawable theme5DownDrawable = new TextureRegionDrawable(new TextureRegion(theme5DownTexture));
        final ImageButton theme5Button = new ImageButton(theme5UpDrawable, theme5DownDrawable);
        theme5Button.getImageCell().expand().fill();
        theme5Button.setPosition(column1X, row3Y);
        theme5Button.setSize(themeIconSize, themeIconSize);
        theme5Button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ColorManager.setColorTheme(ColorManager.THEME_5);
                ScreenManager.getInstance().showScreen(ScreenEnum.THEMES);
            }
        });
        this.addActor(theme5Button);

        theme6UpTexture = new Texture(Gdx.files.internal(THEME_6_UP_PATH));
        Drawable theme6UpDrawable = new TextureRegionDrawable(new TextureRegion(theme6UpTexture));
        theme6DownTexture = new Texture(Gdx.files.internal(THEME_6_DOWN_PATH));
        Drawable theme6DownDrawable = new TextureRegionDrawable(new TextureRegion(theme6DownTexture));
        final ImageButton theme6Button = new ImageButton(theme6UpDrawable, theme6DownDrawable);
        theme6Button.getImageCell().expand().fill();
        theme6Button.setPosition(column2X, row3Y);
        theme6Button.setSize(themeIconSize, themeIconSize);
        theme6Button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ColorManager.setColorTheme(ColorManager.THEME_6);
                ScreenManager.getInstance().showScreen(ScreenEnum.THEMES);
            }
        });
        this.addActor(theme6Button);

        float exitButtonWidth = (GlobalVars.GRID_WIDTH / 3);
        float exitButtonHeight = (GlobalVars.GRID_WIDTH / 8);
        float exitButtonX = GlobalVars.CENTER_X - (exitButtonWidth / 2);
        float exitButtonY = theme5Y - exitButtonHeight - (6 * GlobalVars.PADDING_Y);
        final TextButton exitButton = new TextButton(EXIT, exitSkin);
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

    @Override
    public void render(float delta) {
        super.render(delta);
        drawText();
    }

    @Override
    public void dispose() {
        super.dispose();
        theme1UpTexture.dispose();
        theme1DownTexture.dispose();
        theme2UpTexture.dispose();
        theme2DownTexture.dispose();
        theme3UpTexture.dispose();
        theme3DownTexture.dispose();
        theme4UpTexture.dispose();
        theme4DownTexture.dispose();
        theme5UpTexture.dispose();
        theme5DownTexture.dispose();
        theme6UpTexture.dispose();
        theme6DownTexture.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK) ) {
            ScreenManager.getInstance().showScreen(ScreenEnum.OPTIONS);
        }
        return false;
    }

    private void drawText() {
        spriteBatch.begin();
        BitmapFont fontLarge = FontManager.fontLarge(ColorManager.getFrontColor());
        fontLarge.draw(spriteBatch, titleText, titleX, titleY);

        BitmapFont fontSmall = FontManager.fontSmall(ColorManager.getFrontColor());
        fontSmall.draw(spriteBatch, theme1Text, theme1X, theme1Y);
        fontSmall.draw(spriteBatch, theme2Text, theme2X, theme2Y);
        fontSmall.draw(spriteBatch, theme3Text, theme3X, theme3Y);
        fontSmall.draw(spriteBatch, theme4Text, theme4X, theme4Y);
        fontSmall.draw(spriteBatch, theme5Text, theme5X, theme5Y);
        fontSmall.draw(spriteBatch, theme6Text, theme6X, theme6Y);
        spriteBatch.end();
    }

    private void initButtonSkin() {
        // Init
        BitmapFont fontMedium = FontManager.fontMedium(ColorManager.getFrontColor());
        exitSkin = new Skin();
        exitSkin.add(DEFAULT, fontMedium);

        // Create texture
        Pixmap pixmap = new Pixmap(GlobalVars.UNIT_SIZE, GlobalVars.UNIT_SIZE, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture pixmapTex = new Texture(pixmap);
        exitSkin.add(BACKGROUND, pixmapTex);
        pixmap.dispose();

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
        column1X = GlobalVars.CENTER_X - themeIconSize - (2 * GlobalVars.UNIT_SIZE);
        column2X = GlobalVars.CENTER_X + (2 * GlobalVars.UNIT_SIZE);

        BitmapFont fontLarge = FontManager.fontLarge(ColorManager.getFrontColor());
        titleText = new GlyphLayout();
        titleText.setText(fontLarge, THEMES);
        titleX = GlobalVars.CENTER_X - (titleText.width / 2);
        titleY = GlobalVars.TOP - GlobalVars.UNIT_SIZE - (4 * GlobalVars.PADDING_Y);

        BitmapFont fontSmall = FontManager.fontSmall(ColorManager.getFrontColor());
        row1Y = titleY - titleText.height - themeIconSize - (5 * GlobalVars.PADDING_Y);
        theme1Text = new GlyphLayout();
        theme1Text.setText(fontSmall, THEME_1);
        theme1X = column1X + (themeIconSize / 2) - (theme1Text.width / 2);
        theme1Y = row1Y - (theme1Text.height / 2);
        theme2Text = new GlyphLayout();
        theme2Text.setText(fontSmall, THEME_2);
        theme2X = column2X + (themeIconSize / 2) - (theme2Text.width / 2);
        theme2Y = theme1Y;

        row2Y = theme1Y - themeIconSize - (2 * GlobalVars.PADDING_Y);
        theme3Text = new GlyphLayout();
        theme3Text.setText(fontSmall, THEME_3);
        theme3X = column1X + (themeIconSize / 2) - (theme3Text.width / 2);
        theme3Y = row2Y - (theme3Text.height / 2);
        theme4Text = new GlyphLayout();
        theme4Text.setText(fontSmall, THEME_4);
        theme4X = column2X + (themeIconSize / 2) - (theme4Text.width / 2);
        theme4Y = theme3Y;

        row3Y = theme3Y - themeIconSize - (2 * GlobalVars.PADDING_Y);
        theme5Text = new GlyphLayout();
        theme5Text.setText(fontSmall, THEME_5);
        theme5X = column1X + (themeIconSize / 2) - (theme5Text.width / 2);
        theme5Y = row3Y - (theme5Text.height / 2);
        theme6Text = new GlyphLayout();
        theme6Text.setText(fontSmall, THEME_6);
        theme6X = column2X + (themeIconSize / 2) - (theme6Text.width / 2);
        theme6Y = theme5Y;
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

    private Skin exitSkin;
    private GlyphLayout titleText;
    private GlyphLayout theme1Text;
    private GlyphLayout theme2Text;
    private GlyphLayout theme3Text;
    private GlyphLayout theme4Text;
    private GlyphLayout theme5Text;
    private GlyphLayout theme6Text;
    private Texture theme1UpTexture;
    private Texture theme1DownTexture;
    private Texture theme2UpTexture;
    private Texture theme2DownTexture;
    private Texture theme3UpTexture;
    private Texture theme3DownTexture;
    private Texture theme4UpTexture;
    private Texture theme4DownTexture;
    private Texture theme5UpTexture;
    private Texture theme5DownTexture;
    private Texture theme6UpTexture;
    private Texture theme6DownTexture;

    private float column1X;
    private float column2X;
    private float theme1X;
    private float theme2X;
    private float theme3X;
    private float theme4X;
    private float theme5X;
    private float theme6X;
    private float theme1Y;
    private float theme2Y;
    private float theme3Y;
    private float theme4Y;
    private float theme5Y;
    private float theme6Y;
    private float titleX;
    private float titleY;
    private float row1Y;
    private float row2Y;
    private float row3Y;
    private int themeIconSize;
}