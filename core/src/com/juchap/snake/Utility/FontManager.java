package com.juchap.snake.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import java.util.HashMap;


public class FontManager {

    public static void initAllFonts() {
        fonts = new HashMap<String, BitmapFont>();
        initFont(audimatMonoB, Color.WHITE, 64);
    }

    private static void initFont(String fontName, Color color, int size) {
        FreeTypeFontGenerator.setMaxTextureSize(FreeTypeFontGenerator.NO_MAXIMUM);
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/" + fontName + ".ttf"));;
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();

        float densityIndependentSize = size * Gdx.graphics.getDensity();
        int scaledSize = Math.round(densityIndependentSize );
        fontParameters.color = color;
        fontParameters.size = scaledSize;

        fonts.put(fontName, fontGenerator.generateFont(fontParameters));
        fontGenerator.dispose();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static BitmapFont audimatMonoB_64(){
        return fonts.get(audimatMonoB);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static HashMap<String, BitmapFont> fonts;
    private static String audimatMonoB = "audimatMonoB";
}
