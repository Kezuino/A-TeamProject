package ateamproject.kezuino.com.github.utility.assets;

import ateamproject.kezuino.com.github.utility.io.FilenameUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.HashMap;

public class Assets {
    public static final String AUDIO_SOUND_DIR = "audio/sound/";
    public static final String AUDIO_MUSIC_DIR = "audio/music/";
    public static final String FONTS_DIR = "fonts/";
    public static final String SKINS_DIR ="skins/";

    public static AssetManager manager;
    private static HashMap<String, Music> musicInstances;

    static {
        manager = new AssetManager();
        musicInstances = new HashMap<>();

        // Add loaders to the ContentManager.
        manager.setLoader(BitmapFont.class, new FreeTypeFontLoader(new InternalFileHandleResolver()));        
    }

    /**
     * Loads the fonts and textures used by the {@link ateamproject.kezuino.com.github.PactaleGame}.
     */
    public static void create() {
        loadFonts();

        // Load all the skin assets.
        load();
    }

    /**
     * Loads all the fonts used throughout the {@link ateamproject.kezuino.com.github.PactaleGame}.
     */
    private static void loadFonts() {
        manager.load("fonts/opensans.ttf", BitmapFont.class);
    }

    /**
     * Loads all the basic textures required for the {@link ateamproject.kezuino.com.github.PactaleGame}.
     */
    private static void load() {
        // Textures (only load those that aren't loaded by the TmxMapLoader).
        manager.load("textures/projectile.png", Texture.class);
        manager.load("textures/portal.png", Texture.class);
        manager.load("textures/pactale.png", Texture.class);
        manager.load("textures/enemy.png", Texture.class);

        // Sounds (Only short clips that are mainly used for interaction/action effects).
        manager.load(AUDIO_SOUND_DIR + "defeat.wav", Sound.class);
        manager.load(AUDIO_SOUND_DIR + "portal_shot.mp3", Sound.class);
        manager.load(AUDIO_SOUND_DIR + "portal_hit.mp3", Sound.class);
        manager.load(AUDIO_SOUND_DIR + "enemy_eat.mp3", Sound.class);

        // Music (Do not load music. Music is streamed when needed.) See getMusicStream.
        
        //Skins
        //manager.load(SKINS_DIR + "pacskin.json",FileHandle.class);

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
        if (asset == null || asset.isEmpty())
            throw new IllegalArgumentException("Parameter asset must not be null or empty.");
        FileHandle file = Gdx.files.internal(asset);
        if (!file.exists()) throw new NullPointerException(String.format("Asset '%s' could not be found.", asset));
        if (manager == null) return null;
        if (!manager.isLoaded(asset, type)) {
            manager.load(asset, type);
            manager.finishLoading();
        }
        return manager.get(asset, type);
    }

    /**
     * Loads a {@link Sound} from the assets folder and plays it.
     *
     * @param asset Name of {@link Sound} to search for in the assets folder.
     * @return {@link Sound} from the assets folder and plays it.
     */
    public static Sound playSound(String asset) {
        Sound sound = Assets.get(AUDIO_SOUND_DIR + asset, Sound.class);
        if (sound != null) {
            sound.play();
        }
        return sound;
    }

    /**
     * Loads a {@link Sound} from the assets folder and loops it.
     *
     * @param asset Name of {@link Sound} to search for in the assets folder.
     * @return {@link Sound} from the assets folder and loops it.
     */
    public static Sound loopSound(String asset) {
        Sound sound = Assets.get(AUDIO_SOUND_DIR + asset, Sound.class);
        if (sound != null) {
            sound.loop();
        }
        return sound;
    }

    /**
     * Creates a {@link Music Musicstream} to stream {@link Music} while it's playing.
     *
     * @param fileName Name of {@link Music} to search for in the assets folder.
     * @return {@link Music Musicstream} to stream {@link Music} while it's playing
     */
    public static Music getMusicStream(String fileName) {
        if (fileName == null || fileName.isEmpty()) return null;
        String asset = AUDIO_MUSIC_DIR + fileName;

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


    /**
     * Gets the vertex and fragement shaders and creates a new {@link ShaderProgram}.
     *
     * @param shaderName Name of the shader to search for in the assets folder.
     * @return {@link ShaderProgram} that has been created by the two shader files.
     */
    public static ShaderProgram getShaderProgram(String shaderName) {
        ShaderProgram.pedantic = false;
        String assetName = FilenameUtils.getFileNameWithoutExtension(shaderName);

        FileHandle vertexFile = Gdx.files.internal("shaders/vertex/" + assetName + ".vsh");
        FileHandle fragmentFile = Gdx.files.internal("shaders/fragment/" + assetName + ".fsh");
        if (!vertexFile.exists() || !fragmentFile.exists()) {
            throw new GdxRuntimeException("Couldn't find shader files.");
        }

        ShaderProgram shader = new ShaderProgram(vertexFile.readString(), fragmentFile.readString());
        if (!shader.isCompiled()) {
            System.out.println(shader.getLog());
        }
        return shader;
    }

    /**
     * Releases the resources hold by assets. Cannot be reloaded.
     */
    public static void dispose() {
        manager.dispose();
    }

    /**
     * Returns the {@link BitmapFont} that was loaded in the {@link AssetManager} with the given name.
     *
     * @param asset Name of the {@link BitmapFont} to get.
     * @return {@link BitmapFont} if it was found. Or null.
     */
    public static BitmapFont getFont(String asset) {
        return get(FONTS_DIR + asset, BitmapFont.class);
    }

    /**
     * Loads the {@link Texture} for the {@link ateamproject.kezuino.com.github.utility.game.balloons.BalloonMessage}.
     *
     * @param name Name of the {@link Texture} to load in the assets.
     * @return {@link Texture} that was loaded by the name of the {@link ateamproject.kezuino.com.github.utility.game.balloons.BalloonMessage}.
     */
    public static Texture getBalloon(String name) {
        return get("textures/balloons/" + name + ".png", Texture.class);
    }
}
