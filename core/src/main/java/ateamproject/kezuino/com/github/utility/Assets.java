package ateamproject.kezuino.com.github.utility;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.HashMap;

public class Assets {
    public static AssetManager manager;
    public static HashMap<String, BitmapFont> fonts;

    public static void create() {
        manager = new AssetManager();
        fonts = new HashMap<>();
        load();
    }

    private static void load() {
        // Fonts.
        BitmapFont font = new BitmapFont();
        font.setColor(Color.BLACK);
        fonts.put("default", font);

        // Textures.
        manager.load("nodes/wall.png", Texture.class);
        manager.load("nodes/floor.png", Texture.class);

        // Wait for assets to load.
        manager.finishLoading();
    }

    /**
     * Releases the resources hold by assets.
     */
    public static void dispose() {
        manager.dispose();
    }
}
