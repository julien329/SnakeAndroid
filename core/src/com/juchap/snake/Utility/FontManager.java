package com.juchap.snake.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import java.util.HashMap;


public class FontManager {

    public static void initAllFonts() {
        fonts = new HashMap<String, BitmapFont>();

        initFont(audimatMonoB, Color.BLACK, 32);
        initFont(audimatMonoB, Color.WHITE, 64);
        audimatMonoBSize = 64;
    }

    private static void initFont(String fontName, Color color, int size) {
        FreeTypeFontGenerator.setMaxTextureSize(FreeTypeFontGenerator.NO_MAXIMUM);
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/" + fontName + ".ttf"));;
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();

        float densityIndependentSize = size * Gdx.graphics.getDensity();
        int scaledSize = Math.round(densityIndependentSize );
        fontParameters.color = color;
        fontParameters.size = scaledSize;

        fonts.put(fontName + "_" + color.toString(), fontGenerator.generateFont(fontParameters));
        fontGenerator.dispose();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static BitmapFont audimatMonoB(float size) {
        BitmapFont font = fonts.get(audimatMonoB + "_" + Color.WHITE.toString());
        font.getData().setScale(size/audimatMonoBSize, size/audimatMonoBSize);
        return font;
    }

    public static BitmapFont menuButtons() {
        return fonts.get(audimatMonoB + "_" + Color.BLACK.toString());
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static HashMap<String, BitmapFont> fonts;
    private static String audimatMonoB = "audimatMonoB";
    private static float audimatMonoBSize;
}
