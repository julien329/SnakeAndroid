package com.juchap.snake.Managers;

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
import com.juchap.snake.Utility.GlobalVars;

import java.util.HashMap;

public class FontManager {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static void initManager() {
        _fonts = new HashMap<>();
        _scaleFactor = 0.0025f * GlobalVars.GRID_WIDTH;

        _scaledSmall = Math.round(SMALL * _scaleFactor);
        _scaledMedium = Math.round(MEDIUM * _scaleFactor);
        _scaledLarge = Math.round(LARGE * _scaleFactor);

        _audimatHandle = Gdx.files.internal(AUDIMAT_MONO_B_TTF);

        _prepareFontDataTasks = new Array<>();
        _createFontTextureTasks = new Array<>();
        _saveFontBitmapTasks = new Array<>();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static void createAllFont() {
        if (allFilesExist()) {
            // Fonts already all saved on device
            return;
        }

        prepareFontsData();

        Gdx.app.postRunnable(() -> {
            synchronized(_lock) {
                createFontsTextures();
                _lock.notify();
            }
        });

        while (!_createFontTextureTasks.isEmpty())
        {
            try {
                synchronized(_lock) {
                    // Wait for createFontTexture tasks to be done on the main thread
                    _lock.wait();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        saveFontsBitmap();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static void loadAllFont() {
        loadFont(AUDIMAT_MONO_B, _scaledSmall);
        loadFont(AUDIMAT_MONO_B, _scaledMedium);
        loadFont(AUDIMAT_MONO_B, _scaledLarge);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static boolean allFilesExist() {
        FileHandle handleSmall = Gdx.files.local(fontDir + _scaledSmall + "_" + AUDIMAT_MONO_B + FNT_TYPE);
        FileHandle handleMedium = Gdx.files.local(fontDir + _scaledMedium + "_" + AUDIMAT_MONO_B + FNT_TYPE);
        FileHandle handleLarge = Gdx.files.local(fontDir + _scaledLarge + "_" + AUDIMAT_MONO_B + FNT_TYPE);

        return (handleSmall.exists() && handleMedium.exists() && handleLarge.exists());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static void prepareFontsData() {
        _prepareFontDataTasks.add(new FontGenerationTask(_audimatHandle, AUDIMAT_MONO_B, _scaledSmall));
        _prepareFontDataTasks.add(new FontGenerationTask(_audimatHandle, AUDIMAT_MONO_B, _scaledMedium));
        _prepareFontDataTasks.add(new FontGenerationTask(_audimatHandle, AUDIMAT_MONO_B, _scaledLarge));

        for (FontGenerationTask fontGenerationTask : _prepareFontDataTasks)
        {
            if (!fontGenerationTask.prepareFontData()) {
                // Already saved on device
                continue;
            }

            _createFontTextureTasks.add(fontGenerationTask);
        }

        _prepareFontDataTasks.clear();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static void createFontsTextures() {
        for (FontGenerationTask fontGenerationTask : _createFontTextureTasks)
        {
            fontGenerationTask.createFontTextures();
            _saveFontBitmapTasks.add(fontGenerationTask);
        }

        _createFontTextureTasks.clear();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static void saveFontsBitmap() {
        for (FontGenerationTask fontGenerationTask : _saveFontBitmapTasks)
        {
            fontGenerationTask.saveFontBitmap();
        }

        _saveFontBitmapTasks.clear();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static void loadFont(String fontName, int scaledSize) {
        FileHandle savedFileHandle = Gdx.files.local(fontDir + scaledSize + "_" + fontName + FNT_TYPE);
        BitmapFont font = new BitmapFont(savedFileHandle);
        _fonts.put(scaledSize + "_" + fontName, font);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
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
    /// FontPreparation
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static class FontGenerationTask {

        ////////////////////////////////////////////////////////////////////////////////////////////
        public FontGenerationTask(FileHandle fontFile, String fontName, int scaledSize) {
            _fontFile = fontFile;
            _fontName = fontName;
            _scaledSize = scaledSize;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        public boolean prepareFontData() {
            FileHandle savedFileHandle = Gdx.files.local(fontDir + _scaledSize + "_" + _fontName + FNT_TYPE);
            if (savedFileHandle.exists()) {
                // Already exists
                return false;
            }

            FreeTypeFontGenerator.setMaxTextureSize(FreeTypeFontGenerator.NO_MAXIMUM);
            FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(_fontFile);

            PixmapPacker packer = new PixmapPacker(512, 512, Pixmap.Format.RGBA8888, 2, false);

            _fontParameters = new FreeTypeFontParameter();
            _fontParameters.color = Color.WHITE;
            _fontParameters.size = _scaledSize;
            _fontParameters.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
            _fontParameters.packer = packer;

            _fontData = fontGenerator.generateData(_fontParameters);
            _textureData = new Array<>();

            for (PixmapPacker.Page page : packer.getPages()) {
                PixmapTextureData pixmapTextureData = new PixmapTextureData(page.getPixmap(), page.getPixmap().getFormat(), false, false, true);
                _textureData.add(pixmapTextureData);
            }

            fontGenerator.dispose();

            return true;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        public void createFontTextures() {
            _textureRegions = new Array<>();

            for (PixmapTextureData textureData : _textureData) {
                // Need to be done on the main thread (libgdx opengl)
                Texture texture = new Texture(textureData) {
                    @Override
                    public void dispose() {
                        super.dispose();

                        // Dispose of the texture data when disposing the texture
                        getTextureData().consumePixmap().dispose();
                    }
                };

                texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

                _textureRegions.add(new TextureRegion(texture));
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        public void saveFontBitmap() {
            BitmapFont font = new BitmapFont(_fontData, _textureRegions, false);
            saveFontToFile(font, _fontName, _scaledSize, _fontParameters.packer);

            _fontParameters.packer.dispose();
        }

        private FreeTypeFontGenerator.FreeTypeFontParameter _fontParameters;
        private FreeTypeFontGenerator.FreeTypeBitmapFontData _fontData;
        private Array<PixmapTextureData> _textureData;
        private Array<TextureRegion> _textureRegions;

        private final FileHandle _fontFile;
        private final String _fontName;
        private final int _scaledSize;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// GET / SET
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static BitmapFont fontSmall(Color color) { return fontCustom(color, SMALL); }
    public static BitmapFont fontMedium(Color color) { return fontCustom(color, MEDIUM); }
    public static BitmapFont fontLarge(Color color) { return fontCustom(color, LARGE); }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static BitmapFont fontCustom(Color color, int fontSize) {
        final int scaledSize = Math.round(fontSize * _scaleFactor);

        int referenceSize;
        if (scaledSize <= _scaledSmall) {
            referenceSize = _scaledSmall;
        }
        else if (scaledSize <= _scaledMedium) {
            referenceSize = _scaledMedium;
        }
        else {
            referenceSize = _scaledLarge;
        }

        BitmapFont font = _fonts.get(referenceSize + "_" + AUDIMAT_MONO_B);
        font.getData().setScale((float)scaledSize/referenceSize, (float)scaledSize/referenceSize);
        font.setColor(color);

        return font;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String fontDir = "bitmapFonts/";
    private static final String AUDIMAT_MONO_B_TTF = "Fonts/audimatMonoB.ttf";
    private static final String AUDIMAT_MONO_B = "audimatMonoB";
    private static final String FNT_TYPE = ".fnt";
    private static final int SMALL = 16;
    private static final int MEDIUM = 32;
    private static final int LARGE = 64;

    private static Array<FontGenerationTask> _prepareFontDataTasks;
    private static Array<FontGenerationTask> _createFontTextureTasks;
    private static Array<FontGenerationTask> _saveFontBitmapTasks;

    private static final Object _lock = new Object();

    private static HashMap<String, BitmapFont> _fonts;
    private static FileHandle _audimatHandle;

    private static int _scaledSmall;
    private static int _scaledMedium;
    private static int _scaledLarge;
    private static float _scaleFactor;
}
