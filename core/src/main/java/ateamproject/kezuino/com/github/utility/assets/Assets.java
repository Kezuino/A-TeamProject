package ateamproject.kezuino.com.github.utility.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {

    public static AssetManager manager;

    public static void create() {
        manager = new AssetManager();
        manager.setLoader(BitmapFont.class, new FreeTypeFontLoader(new InternalFileHandleResolver()));
        load();
    }

    private static void load() {
        // Fonts.
        manager.load("fonts/opensans.ttf", BitmapFont.class);

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
        if (manager == null) return null;
        if (manager.isLoaded(asset, type)) {
            return manager.get(asset, type);
        }
        return null;
    }

    public static Sound playSound(String asset) {
        Sound sound = Assets.get(asset, Sound.class);
        if (sound != null) {
            sound.play();
        }
        return sound;
    }

    public static Sound loopSound(String asset) {
        Sound sound = Assets.get(asset, Sound.class);
        if (sound != null) {
            sound.loop();
        }
        return sound;
    }

    /**
     * Releases the resources hold by assets.
     */
    public static void dispose() {
        manager.dispose();
    }

    public static BitmapFont getFont(String asset) {
        return get(asset, BitmapFont.class);
    }
}
