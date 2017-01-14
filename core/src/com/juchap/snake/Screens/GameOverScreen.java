package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.Timer;
import com.juchap.snake.Managers.ColorManager;
import com.juchap.snake.Managers.DifficultyManager;
import com.juchap.snake.Managers.FontManager;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Managers.HighScoreManager;
import com.juchap.snake.Utility.ScreenEnum;
import com.juchap.snake.Utility.ScreenManager;
import com.juchap.snake.Utility.GlobalStrings;


public class GameOverScreen extends AbstractScreen {

    public GameOverScreen(Integer score) {
        super();
        Gdx.input.setInputProcessor(this);

        borders = new ShapeRenderer();
        batch = new SpriteBatch();

        leftBorderX = GlobalVars.GRID_OFFSET_X;
        rightBorderX = GlobalVars.GRID_OFFSET_X + GlobalVars.GRID_WIDTH - GlobalVars.UNIT_SIZE;
        bottomBorderY = GlobalVars.GRID_OFFSET_Y;
        topBorderY = GlobalVars.GRID_OFFSET_Y + GlobalVars.GRID_HEIGHT - GlobalVars.UNIT_SIZE;

        HighScoreManager.addScore(score, DifficultyManager.getDifficulty());
        this.score = score;
        this.best = HighScoreManager.getScore(0, DifficultyManager.getDifficulty());

        canTouch = false;

        checkAchievements(score, DifficultyManager.getDifficulty());

        if(score > 0 && score < MAX_SCORE)
            ScreenManager.getInstance().submitScore(DifficultyManager.getLeaderboard(), score);

        initGlyphs();
    }

    @Override
    public void buildStage() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                canTouch = true;
            }
        }, WAIT_TIME);
        Timer.instance().start();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        drawBorders();
        drawText();
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
            Timer.instance().clear();
            ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(canTouch) {
            Timer.instance().clear();
            ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
        }
        return true;
    }

    private void drawBorders() {
        // Draw screen borders
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
        BitmapFont fontLarge = FontManager.fontLarge(ColorManager.getFrontColor());
        fontLarge.draw(batch, gameOverText, gameOverX, gameOverY);

        BitmapFont fontMedium = FontManager.fontMedium(ColorManager.getFrontColor());
        fontMedium.draw(batch, scoreText, scoreX, scoreY);
        fontMedium.draw(batch, bestScoreText, bestScoreX, bestScoreY);
        fontMedium.draw(batch, difficultyText, difficultyX, difficultyY);

        if(canTouch) {
            BitmapFont fontSmall = FontManager.fontSmall(ColorManager.getFrontColor());
            fontSmall.draw(batch, continueText, continueX, continueY);

        }
        batch.end();
    }

    private void checkAchievements(int score, int difficulty) {
        ScreenManager playServices = ScreenManager.getInstance();

        // Increment score achievements
        if (score > 0) {
            playServices.incrementAchievement(GlobalStrings.ACHIEVEMENT_BITE, score);
            playServices.incrementAchievement(GlobalStrings.ACHIEVEMENT_SNACK, score);
            playServices.incrementAchievement(GlobalStrings.ACHIEVEMENT_MEAL, score);
            playServices.incrementAchievement(GlobalStrings.ACHIEVEMENT_FEAST, score);
            playServices.incrementAchievement(GlobalStrings.ACHIEVEMENT_GLUTONY, score);
        }

        // Increment nb game played achievements
        playServices.incrementAchievement(GlobalStrings.ACHIEVEMENT_SLOW_DOWN, 1);
        playServices.incrementAchievement(GlobalStrings.ACHIEVEMENT_LOOKING_GOOD, 1);
        playServices.incrementAchievement(GlobalStrings.ACHIEVEMENT_MY_MAN, 1);

        // Check for difficulty achievementss
        if (difficulty == DifficultyManager.EASY)
            playServices.unlockAchievement(GlobalStrings.ACHIEVEMENT_SCAREDY_CAT);
        else if (difficulty == DifficultyManager.MEDIUM)
            playServices.unlockAchievement(GlobalStrings.ACHIEVEMENT_ADVENTUROUS);
        else if (difficulty == DifficultyManager.HARD)
            playServices.unlockAchievement(GlobalStrings.ACHIEVEMENT_PRESUMPTUOUS);

        // Check for score milestone achievements
        if(score == 0)
            playServices.unlockAchievement(GlobalStrings.ACHIEVEMENT_PRESUMATURE);
        if(score >= 50)
            playServices.unlockAchievement(GlobalStrings.ACHIEVEMENT_WORM);
        if(score >= 100)
            playServices.unlockAchievement(GlobalStrings.ACHIEVEMENT_BOA);
        if(score >= 150)
            playServices.unlockAchievement(GlobalStrings.ACHIEVEMENT_PYTHON);
        if(score >= 200)
            playServices.unlockAchievement(GlobalStrings.ACHIEVEMENT_ANACONDA);
    }

    private void initGlyphs() {
        BitmapFont fontLarge = FontManager.fontLarge(ColorManager.getFrontColor());
        gameOverText = new GlyphLayout();
        gameOverText.setText(fontLarge, GAME_OVER);
        gameOverX = (int)(Gdx.graphics.getWidth() - gameOverText.width) / 2;
        gameOverY = (int)((2.0f * Gdx.graphics.getHeight() / 3.0f) + gameOverText.height / 2);

        BitmapFont fontMedium = FontManager.fontMedium(ColorManager.getFrontColor());
        scoreText = new GlyphLayout();
        scoreText.setText(fontMedium, new StringBuilder(SCORE).append(score));
        scoreX = (int)(Gdx.graphics.getWidth() - scoreText.width) / 2;
        scoreY = gameOverY - 4 * GlobalVars.PADDING_Y - (int)scoreText.height;
        bestScoreText = new GlyphLayout();
        bestScoreText.setText(fontMedium, new StringBuilder(BEST).append(best));
        bestScoreX = (int)(Gdx.graphics.getWidth() - bestScoreText.width) / 2;
        bestScoreY = scoreY - GlobalVars.PADDING_Y - (int)bestScoreText.height;
        difficultyText = new GlyphLayout();
        difficultyText.setText(fontMedium, DIFFICULTY_LEVELS[DifficultyManager.getDifficulty()]);
        difficultyX = (int)(Gdx.graphics.getWidth() - difficultyText.width) / 2;
        difficultyY = bestScoreY - 4 * GlobalVars.PADDING_Y - (int)difficultyText.height;

        BitmapFont fontSmall = FontManager.fontSmall(ColorManager.getFrontColor());
        continueText = new GlyphLayout();
        continueText.setText(fontSmall, RETURN);
        continueX = (int)(Gdx.graphics.getWidth() - continueText.width) / 2;
        continueY = GlobalVars.GRID_OFFSET_Y + 2 * GlobalVars.UNIT_SIZE + (int)continueText.height;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final int WAIT_TIME = 1;
    private static final int MAX_SCORE = 2048;
    private static final String GAME_OVER = "GAME OVER";
    private static final String SCORE = "SCORE ";
    private static final String BEST = "BEST ";
    private static final String RETURN = "TOUCH TO RETURN TO MENU";
    private static final String[] DIFFICULTY_LEVELS = { "EASY", "MEDIUM", "HARD" };

    private ShapeRenderer borders;
    private SpriteBatch batch;
    private GlyphLayout gameOverText;
    private GlyphLayout scoreText;
    private GlyphLayout bestScoreText;
    private GlyphLayout difficultyText;
    private GlyphLayout continueText;

    private int leftBorderX;
    private int rightBorderX;
    private int topBorderY;
    private int bottomBorderY;
    private int gameOverX;
    private int gameOverY;
    private int scoreX;
    private int scoreY;
    private int bestScoreX;
    private int bestScoreY;
    private int difficultyX;
    private int difficultyY;
    private int continueX;
    private int continueY;
    private int score;
    private int best;
    private boolean canTouch;
}
