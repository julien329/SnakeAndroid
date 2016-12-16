package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
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

        initButtonSkin();
    }

    @Override
    public void buildStage() {
        playButton = new TextButton("PLAY", buttonSkin);
        playButton.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 2);
        playButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().showScreen(ScreenEnum.GAME);
            };
        });
        this.addActor(playButton);
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

    private void drawBorders() {
        // Draw screen borders
        borders.begin(ShapeRenderer.ShapeType.Filled);
        borders.setColor(Color.WHITE);
        borders.rect(GlobalVars.GRID_OFFSET_X, GlobalVars.GRID_OFFSET_Y, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT);
        borders.rect(GlobalVars.GRID_OFFSET_X, GlobalVars.GRID_OFFSET_Y, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        borders.rect(GlobalVars.GRID_OFFSET_X, GlobalVars.GRID_OFFSET_Y + GlobalVars.GRID_HEIGHT - GlobalVars.UNIT_SIZE, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        borders.rect(GlobalVars.GRID_OFFSET_X + GlobalVars.GRID_WIDTH - GlobalVars.UNIT_SIZE, GlobalVars.GRID_OFFSET_Y, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT);
        borders.end();
    }

    private void drawTitle() {
        // Prepare text GlyphLayout
        BitmapFont titleFont = FontManager.audimatMonoB(128);
        GlyphLayout titleText = new GlyphLayout();
        titleText.setText(titleFont, "SNAKE");

        // Draw score text
        batch.begin();
        titleFont.draw(batch, titleText, (GlobalVars.GRID_WIDTH - titleText.width) / 2, Gdx.graphics.getHeight() - (3 * GlobalVars.UNIT_SIZE));
        batch.end();
    }

    private void initButtonSkin() {
        // Init
        BitmapFont font = FontManager.menuButtons();
        buttonSkin = new Skin();
        buttonSkin.add("default", font);

        // Create texture
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 10, Pixmap.Format.RGB888);
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
        buttonSkin.add("default", textButtonStyle);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private ShapeRenderer borders;
    private SpriteBatch batch;
    private TextButton playButton;
    private Skin buttonSkin;
}
