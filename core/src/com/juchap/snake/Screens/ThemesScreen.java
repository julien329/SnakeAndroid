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
        themeIconSize = Gdx.graphics.getWidth() / 4;
        centerX = Gdx.graphics.getWidth() / 2;

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
                ColorManager.setColorTheme(ColorManager.THEME_RETRO);
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
                ColorManager.setColorTheme(ColorManager.THEME_CLASSIC);
                ScreenManager.getInstance().showScreen(ScreenEnum.THEMES);
            }
        });
        this.addActor(theme2Button);

        float exitButtonWidth = (Gdx.graphics.getWidth() / 3);
        float exitButtonHeight = (Gdx.graphics.getWidth() / 8);
        float exitButtonX = (int)(Gdx.graphics.getWidth() - exitButtonWidth) / 2;
        float exitButtonY = theme5Y - exitButtonHeight - 6 * GlobalVars.PADDING_Y;
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
        drawThemeIcons();
    }

    @Override
    public void dispose() {
        super.dispose();
        theme1UpTexture.dispose();
        theme1DownTexture.dispose();
        theme2UpTexture.dispose();
        theme2DownTexture.dispose();
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

    private void drawThemeIcons() {
        uiRenderer.begin(ShapeRenderer.ShapeType.Filled);
        uiRenderer.setColor(ColorManager.getFrontColor());
        uiRenderer.rect(column1X, row2Y, themeIconSize, themeIconSize);
        uiRenderer.rect(column2X, row2Y, themeIconSize, themeIconSize);
        uiRenderer.rect(column1X, row3Y, themeIconSize, themeIconSize);
        uiRenderer.rect(column2X, row3Y, themeIconSize, themeIconSize);
        uiRenderer.end();
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
        column1X = centerX - themeIconSize - 2 * GlobalVars.UNIT_SIZE;
        column2X = centerX + 2 * GlobalVars.UNIT_SIZE;

        BitmapFont fontLarge = FontManager.fontLarge(ColorManager.getFrontColor());
        titleText = new GlyphLayout();
        titleText.setText(fontLarge, THEMES);
        titleX = (int)(Gdx.graphics.getWidth() - titleText.width) / 2;
        titleY = Gdx.graphics.getHeight() - GlobalVars.UNIT_SIZE - 4 * GlobalVars.PADDING_Y;

        BitmapFont fontSmall = FontManager.fontSmall(ColorManager.getFrontColor());
        row1Y = titleY - (int)titleText.height - themeIconSize - 5 * GlobalVars.PADDING_Y;
        theme1Text = new GlyphLayout();
        theme1Text.setText(fontSmall, THEME_1);
        theme1X = column1X + (themeIconSize / 2) - (int)(theme1Text.width / 2);
        theme1Y = row1Y - (int)(theme1Text.height / 2);
        theme2Text = new GlyphLayout();
        theme2Text.setText(fontSmall, THEME_2);
        theme2X = column2X + (themeIconSize / 2) - (int)(theme2Text.width / 2);
        theme2Y = theme1Y;

        row2Y = theme1Y - themeIconSize - 2 * GlobalVars.PADDING_Y;
        theme3Text = new GlyphLayout();
        theme3Text.setText(fontSmall, THEME_3);
        theme3X = column1X + (themeIconSize / 2) - (int)(theme3Text.width / 2);
        theme3Y = row2Y - (int)(theme3Text.height / 2);
        theme4Text = new GlyphLayout();
        theme4Text.setText(fontSmall, THEME_4);
        theme4X = column2X + (themeIconSize / 2) - (int)(theme4Text.width / 2);
        theme4Y = theme3Y;

        row3Y = theme3Y - themeIconSize - 2 * GlobalVars.PADDING_Y;
        theme5Text = new GlyphLayout();
        theme5Text.setText(fontSmall, THEME_5);
        theme5X = column1X + (themeIconSize / 2) - (int)(theme5Text.width / 2);
        theme5Y = row3Y - (int)(theme5Text.height / 2);
        theme6Text = new GlyphLayout();
        theme6Text.setText(fontSmall, THEME_6);
        theme6X = column2X + (themeIconSize / 2) - (int)(theme6Text.width / 2);
        theme6Y = theme5Y;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String THEMES = "THEMES";
    private static final String THEME_1 = "RETRO";
    private static final String THEME_2 = "CLASSIC";
    private static final String THEME_3 = "THEME_3";
    private static final String THEME_4 = "THEME_4";
    private static final String THEME_5 = "THEME_5";
    private static final String THEME_6 = "THEME_6";
    private static final String EXIT = "EXIT";
    private static final String THEME_1_UP_PATH = "Textures/Theme1Up.png";
    private static final String THEME_1_DOWN_PATH = "Textures/Theme1Down.png";
    private static final String THEME_2_UP_PATH = "Textures/Theme2Up.png";
    private static final String THEME_2_DOWN_PATH = "Textures/Theme2Down.png";
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

    private int centerX;
    private int column1X;
    private int column2X;
    private int theme1X;
    private int theme2X;
    private int theme3X;
    private int theme4X;
    private int theme5X;
    private int theme6X;
    private int theme1Y;
    private int theme2Y;
    private int theme3Y;
    private int theme4Y;
    private int theme5Y;
    private int theme6Y;
    private int titleX;
    private int titleY;
    private int row1Y;
    private int row2Y;
    private int row3Y;
    private int themeIconSize;
}

