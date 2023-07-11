package com.juchap.snake.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.IntMap;

public class ColorManager {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static void initManager() {
        _colorPrefs = Gdx.app.getPreferences(PREFS_NAME);

        if (_colorPrefs.contains(KEY_NAME)) {
            loadFromPrefs();
        }
        else {
            _colorTheme = THEME_1;
            saveToPrefs();
        }

        initColorThemes();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static void loadFromPrefs() {
        _colorTheme = _colorPrefs.getInteger(KEY_NAME);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static void saveToPrefs() {
        _colorPrefs.putInteger(KEY_NAME, _colorTheme);
        _colorPrefs.flush();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static void initColorThemes() {
        _backColors = new IntMap<>();
        _backAltColors = new IntMap<>();
        _frontColors = new IntMap<>();
        _frontAltColors = new IntMap<>();
        _snakeHeadColors = new IntMap<>();
        _snakeBodyColors = new IntMap<>();
        _foodColors = new IntMap<>();

        addTheme(THEME_1, Color.BLACK, Color.DARK_GRAY, Color.WHITE, Color.LIGHT_GRAY, Color.GREEN, Color.FOREST, Color.RED);
        addTheme(THEME_2, THEME2_LIGHT_GREEN, THEME2_LIGHT_GREEN_ALT, THEME2_DARK_GREEN, THEME2_DARK_GREEN_ALT, THEME2_DARK_GREEN, THEME2_DARK_GREEN_ALT, THEME2_DARK_GREEN);
        addTheme(THEME_3, THEME3_BLUE, THEME3_BLUE_ALT, Color.ORANGE, THEME3_ORANGE_ALT, Color.LIGHT_GRAY, THEME3_GRAY_ALT, Color.YELLOW);
        addTheme(THEME_4, Color.LIGHT_GRAY, Color.GRAY, THEME4_BLUE, THEME4_BLUE_ALT, THEME4_YELLOW, THEME4_YELLOW_ALT, Color.FOREST);
        addTheme(THEME_5, THEME5_PURPLE, THEME5_PURPLE_ALT, THEME5_GREEN, THEME5_GREEN_ALT, Color.WHITE, Color.LIGHT_GRAY, Color.YELLOW);
        addTheme(THEME_6, THEME6_BURGUNDY, THEME6_BURGUNDY_ALT, THEME6_BEIGE, THEME6_BEIGE_ALT, THEME6_GREEN, THEME6_GREEN_ALT, THEME6_BLUE);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static void addTheme(int themeId, Color backColor, Color backAltColor, Color frontColor, Color frontAltColor, Color snakeHeadColor, Color snakeBodyColor, Color foodColor) {
        _backColors.put(themeId, backColor);
        _backAltColors.put(themeId, backAltColor);
        _frontColors.put(themeId, frontColor);
        _frontAltColors.put(themeId, frontAltColor);
        _snakeHeadColors.put(themeId, snakeHeadColor);
        _snakeBodyColors.put(themeId, snakeBodyColor);
        _foodColors.put(themeId, foodColor);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static Color getBackColor() { return _backColors.get(_colorTheme); }
    public static Color getBackAltColor() { return _backAltColors.get(_colorTheme); }
    public static Color getFrontColor() { return _frontColors.get(_colorTheme); }
    public static Color getFrontAltColor() { return _frontAltColors.get(_colorTheme); }
    public static Color getSnakeHeadColor() { return _snakeHeadColors.get(_colorTheme); }
    public static Color getSnakeBodyColor() { return _snakeBodyColors.get(_colorTheme); }
    public static Color getFoodColor() { return _foodColors.get(_colorTheme); }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static void setColorTheme(int theme) {
        _colorTheme = theme;
        saveToPrefs();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static final int THEME_1 = 0;
    public static final int THEME_2 = 1;
    public static final int THEME_3 = 2;
    public static final int THEME_4 = 3;
    public static final int THEME_5 = 4;
    public static final int THEME_6 = 5;
    private static final String KEY_NAME = "Theme";
    private static final String PREFS_NAME = "UIColor";
    private static final Color THEME2_LIGHT_GREEN =         new Color(155/255f, 188/255f,  15/255f, 1f);
    private static final Color THEME2_LIGHT_GREEN_ALT =     new Color(126/255f, 151/255f,  12/255f, 1f);
    private static final Color THEME2_DARK_GREEN =          new Color( 15/255f,  56/255f,  15/255f, 1f);
    private static final Color THEME2_DARK_GREEN_ALT =      new Color( 26/255f,  97/255f,  26/255f, 1f);
    private static final Color THEME3_BLUE =                new Color(  7/255f,  84/255f, 178/255f, 1f);
    private static final Color THEME3_BLUE_ALT =            new Color( 10/255f, 115/255f, 235/255f, 1f);
    private static final Color THEME3_ORANGE_ALT =          new Color(204/255f, 133/255f,   0/255f, 1f);
    private static final Color THEME3_GRAY_ALT =            new Color(150/255f, 150/255f, 150/255f, 1f);
    private static final Color THEME4_BLUE =                new Color(  0/255f,  84/255f, 148/255f, 1f);
    private static final Color THEME4_BLUE_ALT =            new Color(  0/255f,  49/255f, 100/255f, 1f);
    private static final Color THEME4_YELLOW =              new Color(253/255f, 194/255f,  30/255f, 1f);
    private static final Color THEME4_YELLOW_ALT =          new Color(208/255f, 160/255f,  14/255f, 1f);
    private static final Color THEME5_PURPLE =              new Color( 77/255f,  46/255f,  99/255f, 1f);
    private static final Color THEME5_PURPLE_ALT =          new Color(108/255f,  64/255f, 140/255f, 1f);
    private static final Color THEME5_GREEN =               new Color( 70/255f, 205/255f,  90/255f, 1f);
    private static final Color THEME5_GREEN_ALT =           new Color( 44/255f, 160/255f,  61/255f, 1f);
    private static final Color THEME6_BEIGE =               new Color(216/255f, 205/255f, 199/255f, 1f);
    private static final Color THEME6_BEIGE_ALT =           new Color(188/255f, 169/255f, 159/255f, 1f);
    private static final Color THEME6_BURGUNDY =            new Color(118/255f,  50/255f,  63/255f, 1f);
    private static final Color THEME6_BURGUNDY_ALT =        new Color(157/255f,  67/255f,  84/255f, 1f);
    private static final Color THEME6_GREEN =               new Color( 91/255f, 118/255f,  96/255f, 1f);
    private static final Color THEME6_GREEN_ALT =           new Color(117/255f, 149/255f, 123/255f, 1f);
    private static final Color THEME6_BLUE =                new Color(119/255f, 158/255f, 203/255f, 1f);

    private static Preferences _colorPrefs;
    private static IntMap<Color> _backColors;
    private static IntMap<Color> _backAltColors;
    private static IntMap<Color> _frontColors;
    private static IntMap<Color> _frontAltColors;
    private static IntMap<Color> _snakeHeadColors;
    private static IntMap<Color> _snakeBodyColors;
    private static IntMap<Color> _foodColors;

    private static int _colorTheme;
}
