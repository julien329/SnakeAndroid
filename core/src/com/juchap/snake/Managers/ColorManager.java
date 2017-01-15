package com.juchap.snake.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.IntMap;


public class ColorManager {

    public static void initManager() {
        colorPrefs = Gdx.app.getPreferences(PREFS_NAME);

        if(colorPrefs.contains(KEY_NAME) && false)
            loadFromPrefs();
        else {
            colorTheme = THEME_CLASSIC;
            saveToPrefs();
        }

        initColorThemes();
    }

    private static void loadFromPrefs() {
        colorTheme = colorPrefs.getInteger(KEY_NAME);
    }

    private static void saveToPrefs() {
        colorPrefs.putInteger(KEY_NAME, colorTheme);
        colorPrefs.flush();
    }

    private static void initColorThemes() {
        backColors = new IntMap<Color>();
        backAltColors = new IntMap<Color>();
        frontColors = new IntMap<Color>();
        frontAltColors = new IntMap<Color>();
        snakeHeadColors = new IntMap<Color>();
        snakeBodyColors = new IntMap<Color>();
        foodColors = new IntMap<Color>();

        addTheme(THEME_RETRO, Color.BLACK, Color.DARK_GRAY, Color.WHITE, Color.LIGHT_GRAY, Color.GREEN, Color.FOREST, Color.RED);
        addTheme(THEME_CLASSIC, CLASSIC_LIGHT_GREEN, CLASSIC_LIGHT_GREEN_ALT, CLASSIC_DARK_GREEN, CLASSIC_DARK_GREEN_ALT, CLASSIC_DARK_GREEN, CLASSIC_DARK_GREEN_ALT, Color.RED);
    }

    private static void addTheme(int themeId, Color backColor, Color backAltColor, Color frontColor, Color frontAltColor, Color snakeHeadColor, Color snakeBodyColor, Color foodColor) {
        backColors.put(themeId, backColor);
        backAltColors.put(themeId, backAltColor);
        frontColors.put(themeId, frontColor);
        frontAltColors.put(themeId, frontAltColor);
        snakeHeadColors.put(themeId, snakeHeadColor);
        snakeBodyColors.put(themeId, snakeBodyColor);
        foodColors.put(themeId, foodColor);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static Color getBackColor() { return backColors.get(colorTheme); }
    public static Color getBackAltColor() { return backAltColors.get(colorTheme); }
    public static Color getFrontColor() { return frontColors.get(colorTheme); }
    public static Color getFrontAltColor() { return frontAltColors.get(colorTheme); }
    public static Color getSnakeHeadColor() { return snakeHeadColors.get(colorTheme); }
    public static Color getSnakeBodyColor() { return snakeBodyColors.get(colorTheme); }
    public static Color getFoodColor() { return foodColors.get(colorTheme); }

    public static void setColortheme(int theme) { colorTheme = theme; }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String KEY_NAME = "Theme";
    private static final String PREFS_NAME = "UIColor";
    private static final int THEME_RETRO = 0;
    private static final int THEME_CLASSIC = 1;
    private static final Color CLASSIC_LIGHT_GREEN = new Color(155/255f, 188/255f, 15/255f, 1f);
    private static final Color CLASSIC_LIGHT_GREEN_ALT = new Color(126/255f, 151/255f, 12/255f, 1f);
    private static final Color CLASSIC_DARK_GREEN = new Color(15/255f, 56/255f, 15/255f, 1f);
    private static final Color CLASSIC_DARK_GREEN_ALT = new Color(26/255f, 97/255f, 26/255f, 1f);

    private static Preferences colorPrefs;
    private static IntMap<Color> backColors;
    private static IntMap<Color> backAltColors;
    private static IntMap<Color> frontColors;
    private static IntMap<Color> frontAltColors;
    private static IntMap<Color> snakeHeadColors;
    private static IntMap<Color> snakeBodyColors;
    private static IntMap<Color> foodColors;

    private static int colorTheme;
}
