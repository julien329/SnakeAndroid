package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;
import com.juchap.snake.Utility.DifficultyManager;
import com.juchap.snake.Utility.FontManager;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Utility.HighScoreManager;
import com.juchap.snake.Utility.ScreenEnum;
import com.juchap.snake.Utility.ScreenManager;
import com.juchap.snake.Utility.StringManager;


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

        if(score > 0 && score < 2048)
            ScreenManager.getInstance().submitScore(DifficultyManager.getLeaderboard(), score);
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
        borders.setColor(Color.WHITE);
        borders.rect(leftBorderX, bottomBorderY, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT);
        borders.rect(leftBorderX, bottomBorderY, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        borders.rect(leftBorderX, topBorderY, GlobalVars.GRID_WIDTH, GlobalVars.UNIT_SIZE);
        borders.rect(rightBorderX, bottomBorderY, GlobalVars.UNIT_SIZE, GlobalVars.GRID_HEIGHT);
        borders.end();
    }

    private void drawText() {
        BitmapFont fontLarge = FontManager.fontLarge(Color.WHITE);
        GlyphLayout gameOverText = new GlyphLayout();
        gameOverText.setText(fontLarge, "GAME OVER");
        float gameOverY = (2.0f * Gdx.graphics.getHeight() / 3.0f);

        batch.begin();
        fontLarge.draw(batch, gameOverText, (Gdx.graphics.getWidth() - gameOverText.width) / 2, gameOverY + (gameOverText.height / 2));
        batch.end();

        BitmapFont fontMedium = FontManager.fontMedium(Color.WHITE);
        GlyphLayout scoreText = new GlyphLayout();
        scoreText.setText(fontMedium, "SCORE " + score);
        float ScoreY = gameOverY - 4 * GlobalVars.PADDING_Y - scoreText.height;
        GlyphLayout bestScoreText = new GlyphLayout();
        bestScoreText.setText(fontMedium, "BEST " + best);
        float bestScoreY = ScoreY - GlobalVars.PADDING_Y - bestScoreText.height;
        GlyphLayout difficultyText = new GlyphLayout();
        difficultyText.setText(fontMedium, DIFFICULTY_LEVELS[DifficultyManager.getDifficulty()]);
        float difficultyY = bestScoreY - 4 * GlobalVars.PADDING_Y - difficultyText.height;

        batch.begin();
        fontMedium.draw(batch, scoreText, (Gdx.graphics.getWidth() - scoreText.width) / 2, ScoreY);
        fontMedium.draw(batch, bestScoreText, (Gdx.graphics.getWidth() - bestScoreText.width) / 2, bestScoreY);
        fontMedium.draw(batch, difficultyText, (Gdx.graphics.getWidth() - difficultyText.width) / 2, difficultyY);
        batch.end();

        if(canTouch) {
            BitmapFont fontSmall = FontManager.fontSmall(Color.WHITE);
            GlyphLayout continueText = new GlyphLayout();
            continueText.setText(fontSmall, "TOUCH TO RETURN TO MENU");

            batch.begin();
            fontSmall.draw(batch, continueText, (Gdx.graphics.getWidth() - continueText.width) / 2, GlobalVars.GRID_OFFSET_Y + 2 * GlobalVars.UNIT_SIZE + continueText.height);
            batch.end();
        }
    }

    private void checkAchievements(int score, int difficulty) {
        ScreenManager playServices = ScreenManager.getInstance();

        // Increment score achievements
        if (score > 0) {
            playServices.incrementAchievement(StringManager.ACHIEVEMENT_BITE, score);
            playServices.incrementAchievement(StringManager.ACHIEVEMENT_SNACK, score);
            playServices.incrementAchievement(StringManager.ACHIEVEMENT_MEAL, score);
            playServices.incrementAchievement(StringManager.ACHIEVEMENT_FEAST, score);
            playServices.incrementAchievement(StringManager.ACHIEVEMENT_GLUTONY, score);
        }

        // Increment nb game played achievements
        playServices.incrementAchievement(StringManager.ACHIEVEMENT_SLOW_DOWN, 1);
        playServices.incrementAchievement(StringManager.ACHIEVEMENT_LOOKING_GOOD, 1);
        playServices.incrementAchievement(StringManager.ACHIEVEMENT_MY_MAN, 1);

        // Check for difficulty achievementss
        if (difficulty == DifficultyManager.EASY)
            playServices.unlockAchievement(StringManager.ACHIEVEMENT_SCAREDY_CAT);
        else if (difficulty == DifficultyManager.MEDIUM)
            playServices.unlockAchievement(StringManager.ACHIEVEMENT_ADVENTUROUS);
        else if (difficulty == DifficultyManager.HARD)
            playServices.unlockAchievement(StringManager.ACHIEVEMENT_PRESUMPTUOUS);

        // Check for score milestone achievements
        if(score == 0)
            playServices.unlockAchievement(StringManager.ACHIEVEMENT_PRESUMATURE);
        if(score >= 50)
            playServices.unlockAchievement(StringManager.ACHIEVEMENT_WORM);
        if(score >= 100)
            playServices.unlockAchievement(StringManager.ACHIEVEMENT_BOA);
        if(score >= 250)
            playServices.unlockAchievement(StringManager.ACHIEVEMENT_PYTHON);
        if(score >= 500)
            playServices.unlockAchievement(StringManager.ACHIEVEMENT_ANACONDA);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final int WAIT_TIME = 1;
    private static final String[] DIFFICULTY_LEVELS = { "EASY", "MEDIUM", "HARD" };

    private ShapeRenderer borders;
    private SpriteBatch batch;

    private int leftBorderX;
    private int rightBorderX;
    private int topBorderY;
    private int bottomBorderY;
    private int score;
    private int best;
    private boolean canTouch;
}
