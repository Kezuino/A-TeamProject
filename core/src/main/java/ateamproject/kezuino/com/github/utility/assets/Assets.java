package ateamproject.kezuino.com.github.utility.assets;

import ateamproject.kezuino.com.github.utility.FilenameUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import jdk.nashorn.internal.codegen.CompilationException;

import java.util.HashMap;

public class Assets {

    public static AssetManager manager;
    private static HashMap<String, Music> musicInstances;

    static {
        manager = new AssetManager();
        musicInstances = new HashMap<>();

        // Add loaders to the ContentManager.
        manager.setLoader(BitmapFont.class, new FreeTypeFontLoader(new InternalFileHandleResolver()));
    }

    public static void create() {
        loadFonts();

        // Load all the skin assets.
        load();
    }

    private static void loadFonts() {
        manager.load("fonts/opensans.ttf", BitmapFont.class);
    }

    private static void load() {

        // Textures.
        manager.load("textures/pactale.png", Texture.class);
        manager.load("textures/enemy.png", Texture.class);
        manager.load("textures/projectile.png", Texture.class);
        manager.load("textures/bigObject.png", Texture.class);
        manager.load("textures/smallObject.png", Texture.class);
        manager.load("textures/item.png", Texture.class);
        manager.load("textures/portal.png", Texture.class);


        // Sounds.
        //manager.load("sounds/menu.mp3", Sound.class); //Takes a long time..
        //manager.load("sounds/defeat.wav", Sound.class); //Takes a long time..
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

    public static Music getMusicStream(String fileName) {
        if (fileName == null || fileName.isEmpty()) return null;
        String asset = "audio/music/" + fileName;

        Music music;

        // If music exists in cache, retrieve it.
        if (musicInstances.containsKey(fileName)) {
            music = musicInstances.get(fileName);
            if (music == null) {
                musicInstances.remove(fileName);
            } else {
                return music;
            }
        }

        // Music not yet in cache, create a new music stream.
        FileHandle file = Gdx.files.internal(asset);
        music = Gdx.audio.newMusic(file);

        if (music != null) {
            musicInstances.put(fileName, music);
        }

        return music;
    }


    public static ShaderProgram getShaderProgram(String shaderName) {
        ShaderProgram.pedantic = false;
        String assetName = FilenameUtils.getFileNameWithoutExtension(shaderName);
        ShaderProgram shader = new ShaderProgram(Gdx.files.internal("shaders/vertex/" + assetName + ".vsh").readString(), Gdx.files.internal("shaders/fragment/" + assetName + ".fsh").readString());
        if (!shader.isCompiled()) {
            System.out.println(shader.getLog());
        }
        return shader;
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
