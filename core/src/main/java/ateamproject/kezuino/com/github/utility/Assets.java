package ateamproject.kezuino.com.github.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.HashMap;
import java.util.logging.FileHandler;

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
        font = new BitmapFont();
        font.setColor(Color.YELLOW);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setScale(0.9f);
        fonts.put("fps", font);

        // Textures.
        manager.load("textures/foreground/pactale.png", Texture.class);
        manager.load("textures/foreground/enemy.png", Texture.class);
        manager.load("textures/foreground/projectile.png", Texture.class);
        manager.load("textures/foreground/bigObject.png", Texture.class);
        manager.load("textures/foreground/smallObject.png", Texture.class);
        manager.load("textures/foreground/item.png", Texture.class);

        // Sounds.
        //manager.load("sounds/Background.mp3", Sound.class); //Takes a long time..
        //manager.load("sounds/Defeat.wav", Sound.class); //Takes a long time..
        // Wait for assets to load.
        manager.finishLoading();
    }

    /**
     * Gets the resource from the {@link AssetManager} or null if not found.
     *
     * @param asset Name of the resource to find.
     * @return Resource from the {@link AssetManager} or null if not found
     */
    public static <T> T get(String asset, Class<T> type) {
        if (manager.isLoaded(asset, type)) {
            return manager.get(asset, type);
        }
        return null;
    }

    /**
     * Releases the resources hold by assets.
     */
    public static void dispose() {
        manager.dispose();
    }
}
