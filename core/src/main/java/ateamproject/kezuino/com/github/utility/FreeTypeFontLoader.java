package ateamproject.kezuino.com.github.utility;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;

class FreeTypeFontLoader extends SynchronousAssetLoader<BitmapFont, FreeTypeFontLoader.FreeTypeFontParameters> {

    public FreeTypeFontLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public BitmapFont load(AssetManager assetManager, String fileName, FileHandle file, FreeTypeFontParameters parameter) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(file);

        if (parameter == null) {
            parameter = new FreeTypeFontParameters();
            parameter.fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.fontParameters.color = Color.BLACK;
            parameter.fontParameters.size = 24;
        }
        return generator.generateFont(parameter.fontParameters);
    }

    static public class FreeTypeFontParameters extends AssetLoaderParameters<BitmapFont> {

        public FreeTypeFontGenerator.FreeTypeFontParameter fontParameters;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, FreeTypeFontParameters parameter) {
        return null;
    }

}