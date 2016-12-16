package com.juchap.snake.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.utils.Array;
import com.juchap.snake.Tools.BitmapFontWriter;

import java.util.HashMap;


public class FontManager {

    public static void initAllFonts() {
        fonts = new HashMap<String, BitmapFont>();

        scaledSmall = Math.round(SMALL * Gdx.graphics.getDensity());
        scaledMedium = Math.round(MEDIUM * Gdx.graphics.getDensity());
        scaledLarge = Math.round(LARGE * Gdx.graphics.getDensity());

        FileHandle audimatHandle = Gdx.files.internal(AUDIMAT_MONO_B_TTF);
        initFont(audimatHandle, AUDIMAT_MONO_B, scaledSmall);
        initFont(audimatHandle, AUDIMAT_MONO_B, scaledMedium);
        initFont(audimatHandle, AUDIMAT_MONO_B, scaledLarge);
    }

    private static void initFont(FileHandle fontFile, String fontName, int scaledSize) {
        BitmapFont font = null;
        FileHandle savedFileHandle = Gdx.files.local(fontDir + scaledSize + "_" + fontName + FNT_TYPE).exists() ? Gdx.files.local(fontDir + scaledSize + "_" + fontName + FNT_TYPE) : null;

        if(savedFileHandle != null)
            font = new BitmapFont(savedFileHandle);
        else
            font = createFont(fontFile, fontName, scaledSize);

        fonts.put(scaledSize + "_" + fontName, font);
    }

    private static BitmapFont createFont(FileHandle fontFile, String fontName, int scaledSize)  {
        FreeTypeFontGenerator.setMaxTextureSize(FreeTypeFontGenerator.NO_MAXIMUM);
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(fontFile);

        PixmapPacker packer = new PixmapPacker(512, 512, Pixmap.Format.RGBA8888, 2, false);

        FreeTypeFontParameter fontParameters = new FreeTypeFontParameter();
        fontParameters.color = Color.WHITE;
        fontParameters.size = scaledSize;
        fontParameters.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
        fontParameters.packer = packer;

        FreeTypeFontGenerator.FreeTypeBitmapFontData fontData = fontGenerator.generateData(fontParameters);
        Array<PixmapPacker.Page> pages = packer.getPages();
        Array<TextureRegion> texRegions = new Array<TextureRegion>();
        for (int i = 0; i < pages.size; i++) {
            PixmapPacker.Page p = pages.get(i);
            Texture tex = new Texture(
                    new PixmapTextureData(p.getPixmap(), p.getPixmap().getFormat(), false, false, true)) {
                @Override
                public void dispose() {
                    super.dispose();
                    getTextureData().consumePixmap().dispose();
                }
            };
            tex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            texRegions.add(new TextureRegion(tex));
        }

        BitmapFont font = new BitmapFont(fontData, texRegions, false);
        saveFontToFile(font, fontName, scaledSize, packer);

        fontGenerator.dispose();
        packer.dispose();

        return font;
    }

    private static void saveFontToFile(BitmapFont font, String fontName, int scaledSize, PixmapPacker packer) {
        FileHandle fontFile = Gdx.files.local(fontDir + scaledSize + "_" + fontName + FNT_TYPE);
        FileHandle pixmapDir = Gdx.files.local(fontDir + scaledSize + "_" + fontName);
        BitmapFontWriter.setOutputFormat(BitmapFontWriter.OutputFormat.Text);

        String[] pageRefs = BitmapFontWriter.writePixmaps(packer.getPages(), pixmapDir, fontName);
        for (int i = 0; i < pageRefs.length; i++) {
            pageRefs[i] = scaledSize + "_" + fontName + "/" + pageRefs[i];
        }

        BitmapFontWriter.writeFont(font.getData(), pageRefs, fontFile, new BitmapFontWriter.FontInfo(fontName, scaledSize), 1, 1);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static BitmapFont fontSmall(Color color) {
        return fontCustom(color, SMALL);
    }

    public static BitmapFont fontMedium(Color color) {
        return fontCustom(color, MEDIUM);
    }

    public static BitmapFont fontLarge(Color color) {
        return fontCustom(color, LARGE);
    }

    public static BitmapFont fontCustom(Color color, int fontSize) {
        int scaledSize = Math.round(fontSize * Gdx.graphics.getDensity());
        int referenceSize = -1;

        if(scaledSize < (scaledSmall + scaledMedium) / 2)
            referenceSize = scaledSmall;
        else if (scaledSize > (scaledLarge + scaledMedium) / 2)
            referenceSize = scaledLarge;
        else {
            referenceSize = scaledMedium;
        }

        BitmapFont font = fonts.get(referenceSize + "_" + AUDIMAT_MONO_B);
        font.getData().setScale(scaledSize/referenceSize, scaledSize/referenceSize);
        font.setColor(color);

        return font;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static HashMap<String, BitmapFont> fonts;
    private static int scaledSmall;
    private static int scaledMedium;
    private static int scaledLarge;

    private static final String fontDir = "bitmapFonts/";
    private static final String AUDIMAT_MONO_B_TTF = "Fonts/audimatMonoB.ttf";
    private static final String AUDIMAT_MONO_B = "audimatMonoB";
    private static final String FNT_TYPE = ".fnt";
    private static final int SMALL = 16;
    private static final int MEDIUM = 32;
    private static final int LARGE = 64;

}
