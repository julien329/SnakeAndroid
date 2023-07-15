package com.juchap.snake.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.Timer;
import com.juchap.snake.Managers.ColorManager;
import com.juchap.snake.Managers.DifficultyManager;
import com.juchap.snake.Managers.FontManager;
import com.juchap.snake.Managers.HighScoreManager;
import com.juchap.snake.Utility.GlobalStrings;
import com.juchap.snake.Utility.GlobalVars;
import com.juchap.snake.Utility.ScreenEnum;
import com.juchap.snake.Utility.ScreenManager;

public class GameOverScreen extends AbstractScreen {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public GameOverScreen(Integer score) {
        Gdx.input.setInputProcessor(this);

        _score = score;
        _best = HighScoreManager.getScore(0, DifficultyManager.getDifficulty());
        _canTouch = false;

        HighScoreManager.addScore(score, DifficultyManager.getDifficulty());
        checkAchievements(DifficultyManager.getDifficulty());

        if (score > 0 && score < MAX_SCORE) {
            ScreenManager.getInstance().submitScore(DifficultyManager.getLeaderboard(), score);
        }

        initGlyphs();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void buildStage() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                _canTouch = true;
            }
        }, WAIT_TIME);
        Timer.instance().start();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void render(float delta) {
        super.render(delta);
        drawText();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean keyDown(int keycode) {
        if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK) ) {
            Timer.instance().clear();
            ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
        }
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (_canTouch) {
            Timer.instance().clear();
            ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
        }
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void drawText() {
        _spriteBatch.begin();
        BitmapFont fontLarge = FontManager.fontLarge(ColorManager.getFrontColor());
        fontLarge.draw(_spriteBatch, _gameOverText, _gameOverX, _gameOverY);

        BitmapFont fontMedium = FontManager.fontMedium(ColorManager.getFrontColor());
        fontMedium.draw(_spriteBatch, _scoreText, _scoreX, _scoreY);
        fontMedium.draw(_spriteBatch, _bestScoreText, _bestScoreX, _bestScoreY);
        fontMedium.draw(_spriteBatch, _difficultyText, _difficultyX, _difficultyY);

        if (_canTouch) {
            BitmapFont fontSmall = FontManager.fontSmall(ColorManager.getFrontColor());
            fontSmall.draw(_spriteBatch, _continueText, _continueX, _continueY);

        }
        _spriteBatch.end();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void checkAchievements(int difficulty) {
        ScreenManager playServices = ScreenManager.getInstance();

        // Increment score achievements
        if (_score > 0) {
            playServices.incrementAchievement(GlobalStrings.ACHIEVEMENT_BITE, _score);
            playServices.incrementAchievement(GlobalStrings.ACHIEVEMENT_SNACK, _score);
            playServices.incrementAchievement(GlobalStrings.ACHIEVEMENT_MEAL, _score);
            playServices.incrementAchievement(GlobalStrings.ACHIEVEMENT_FEAST, _score);
            playServices.incrementAchievement(GlobalStrings.ACHIEVEMENT_GLUTONY, _score);
        }

        // Increment nb game played achievements
        playServices.incrementAchievement(GlobalStrings.ACHIEVEMENT_SLOW_DOWN, 1);
        playServices.incrementAchievement(GlobalStrings.ACHIEVEMENT_LOOKING_GOOD, 1);
        playServices.incrementAchievement(GlobalStrings.ACHIEVEMENT_MY_MAN, 1);

        // Check for difficulty achievements
        if (difficulty == DifficultyManager.EASY) {
            playServices.unlockAchievement(GlobalStrings.ACHIEVEMENT_SCAREDY_CAT);
        }
        else if (difficulty == DifficultyManager.MEDIUM) {
            playServices.unlockAchievement(GlobalStrings.ACHIEVEMENT_ADVENTUROUS);
        }
        else if (difficulty == DifficultyManager.HARD) {
            playServices.unlockAchievement(GlobalStrings.ACHIEVEMENT_PRESUMPTUOUS);
        }

        // Check for score milestone achievements
        if (_score == 0) {
            playServices.unlockAchievement(GlobalStrings.ACHIEVEMENT_PRESUMATURE);
        }
        if (_score >= 50) {
            playServices.unlockAchievement(GlobalStrings.ACHIEVEMENT_WORM);
        }
        if (_score >= 100) {
            playServices.unlockAchievement(GlobalStrings.ACHIEVEMENT_BOA);
        }
        if (_score >= 150) {
            playServices.unlockAchievement(GlobalStrings.ACHIEVEMENT_PYTHON);
        }
        if (_score >= 200) {
            playServices.unlockAchievement(GlobalStrings.ACHIEVEMENT_ANACONDA);
        }
    }

    private void initGlyphs() {
        BitmapFont fontLarge = FontManager.fontLarge(ColorManager.getFrontColor());
        _gameOverText = new GlyphLayout();
        _gameOverText.setText(fontLarge, GAME_OVER);
        _gameOverX = GlobalVars.CENTER_X - (_gameOverText.width / 2.f);
        _gameOverY = GlobalVars.BOTTOM + ((2.f / 3.f) * GlobalVars.GRID_HEIGHT);

        BitmapFont fontMedium = FontManager.fontMedium(ColorManager.getFrontColor());
        _scoreText = new GlyphLayout();
        _scoreText.setText(fontMedium, new StringBuilder(SCORE).append(_score));
        _scoreX = GlobalVars.CENTER_X - (_scoreText.width / 2.f);
        _scoreY = _gameOverY - _gameOverText.height - (4.f * GlobalVars.PADDING_Y);
        _bestScoreText = new GlyphLayout();
        _bestScoreText.setText(fontMedium, new StringBuilder(BEST).append(_best));
        _bestScoreX = GlobalVars.CENTER_X - (_bestScoreText.width / 2.f);
        _bestScoreY = _scoreY - _scoreText.height - (0.5f * GlobalVars.PADDING_Y);
        _difficultyText = new GlyphLayout();
        _difficultyText.setText(fontMedium, DIFFICULTY_LEVELS[DifficultyManager.getDifficulty()]);
        _difficultyX = GlobalVars.CENTER_X - (_difficultyText.width / 2.f);
        _difficultyY = _bestScoreY - _bestScoreText.height - (4.f * GlobalVars.PADDING_Y);

        BitmapFont fontSmall = FontManager.fontSmall(ColorManager.getFrontColor());
        _continueText = new GlyphLayout();
        _continueText.setText(fontSmall, RETURN);
        _continueX = GlobalVars.CENTER_X - (_continueText.width / 2.f);
        _continueY = GlobalVars.BOTTOM + (2.f * GlobalVars.UNIT_SIZE) + _continueText.height;
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

    private GlyphLayout _gameOverText;
    private GlyphLayout _scoreText;
    private GlyphLayout _bestScoreText;
    private GlyphLayout _difficultyText;
    private GlyphLayout _continueText;

    private float _gameOverX;
    private float _gameOverY;
    private float _scoreX;
    private float _scoreY;
    private float _bestScoreX;
    private float _bestScoreY;
    private float _difficultyX;
    private float _difficultyY;
    private float _continueX;
    private float _continueY;
    private final int _score;
    private final int _best;
    private boolean _canTouch;
}
