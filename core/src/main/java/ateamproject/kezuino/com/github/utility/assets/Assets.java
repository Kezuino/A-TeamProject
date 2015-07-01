package ateamproject.kezuino.com.github.utility.assets;

import ateamproject.kezuino.com.github.utility.io.FilenameUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Assets {

    public static final String AUDIO_SOUND_DIR = "audio/sound/";
    public static final String AUDIO_MUSIC_DIR = "audio/music/";
    public static final String FONTS_DIR = "fonts/";
    public static final String GUI_DIR = "gui/";
    public static final String TEXTURE_DIR = "textures/";
    public static final String SHADER_DIR = "shaders/";
    public static AssetManager manager;
    private static String skin;
    private static HashMap<String, Music> musicInstances;
    private static boolean debug;

    static {
        FileHandleResolver resolver = fileName -> {
            if (Assets.skin == null || Assets.skin.isEmpty()) {
                // Load from internal.
                return Gdx.files.internal(fileName);
            } else {
                // Load files from external map.
                FileHandle handle = Gdx.files.local("Skins/" + Assets.skin + '/' + fileName);
                if (!handle.exists()) {
                    handle = Gdx.files.internal(fileName);
                }
                return handle;
            }
        };

        manager = new AssetManager(resolver);
        musicInstances = new HashMap<>();

        // Add loaders to the ContentManager.
        manager.setLoader(BitmapFont.class, ".ttf", new FreeTypeFontLoader(resolver));
    }

    public static String getFileName(String... args) {
        String result;
        result = String.join("/", args).replace("\\", "/").replace("//", "/");
        return result;
    }

    /**
     * Asynchroniously sets a new skin and reloads all the related content.
     *
     * @param blocking If true, this method will wait until all the assets are reloaded.
     * @param callback Callback to call when the reloading is done.
     */
    public static void setSkin(boolean blocking, Runnable callback) {
        setSkin(null, blocking, callback);
    }

    /**
     * Asynchroniously sets a new skin and reloads all the related content.
     *
     * @param skin     Name of the skin directory to look for and load content from.<br>
     *                 Missing content in the skin directory will backed-up by internal assets.
     * @param blocking If true, this method will wait until all the assets are reloaded.
     * @param callback Callback to call when the reloading is done.
     */
    public static void setSkin(String skin, boolean blocking, Runnable callback) {
        if (debug) {
            try {
                File file = new File("out.txt");
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                System.setIn(new FileInputStream(file));
                System.setOut(new PrintStream(file));
                System.setErr(new PrintStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Clear assets if one or more are loaded.
        if (manager != null && manager.getLoadedAssets() > 0) {
            clean();
        }

        Assets.skin = skin;

        // Load all the skin assets.
        load(blocking);

        if (callback != null) callback.run();
    }

    /**
     * Loads all the basic textures required for the
     * {@link ateamproject.kezuino.com.github.PactaleGame}.
     *
     * @param block If true, this method will block until all assets are loaded.
     */
    private static void load(boolean block) {
        // Fonts.
        manager.load(getFileName(FONTS_DIR, "opensans.ttf"), BitmapFont.class);

        // Textures (only load those that aren't loaded by the TmxMapLoader).
        manager.load(getFileName(TEXTURE_DIR, "projectile.png"), Texture.class);
        manager.load(getFileName(TEXTURE_DIR, "portal.png"), Texture.class);
        manager.load(getFileName(TEXTURE_DIR, "pactale.png"), Texture.class);
        manager.load(getFileName(TEXTURE_DIR, "enemy.png"), Texture.class);

        // Particle effects.
        manager.load("textures/particles/projectile", ParticleEffect.class);

        // Sounds (Only short clips that are mainly used for interaction/action effects).
        manager.load(getFileName(AUDIO_SOUND_DIR, "defeat.wav"), Sound.class);
        manager.load(getFileName(AUDIO_SOUND_DIR, "portal_shot.mp3"), Sound.class);
        manager.load(getFileName(AUDIO_SOUND_DIR, "portal_hit.mp3"), Sound.class);
        manager.load(getFileName(AUDIO_SOUND_DIR, "enemy_eat.mp3"), Sound.class);

        // Music (Do not load music. Music is streamed when needed.) See getMusicStream.

        if (block) {
            // Wait for assets to load.
            manager.finishLoading();
        }
    }

    /**
     * Gets the resource from the {@link AssetManager} or null if not found.
     *
     * @param asset Name of the resource to find.
     * @return Resource from the {@link AssetManager} or null if not found
     */
    public static <T> T get(String asset, Class<T> type) {
        if (asset == null || asset.isEmpty()) {
            throw new IllegalArgumentException("Parameter asset must not be null or empty.");
        }
        if (manager == null) {
            return null;
        }
        if (!manager.isLoaded(getFileName(asset), type)) {
            manager.load(getFileName(asset), type);
            manager.finishLoading();
        }
        return manager.get(getFileName(asset), type);
    }

    public static Texture getTexture(String asset) {
        return get(TEXTURE_DIR + asset, Texture.class);
    }

    /**
     * Returns the {@link BitmapFont} that was loaded in the
     * {@link AssetManager} with the given name.
     *
     * @param asset Name of the {@link BitmapFont} to get.
     * @return {@link BitmapFont} if it was found. Or null.
     */
    public static BitmapFont getFont(String asset) {
        return get(FONTS_DIR + asset, BitmapFont.class);
    }

    public static Skin getSkin(String asset) {
        return get(GUI_DIR + asset, Skin.class);
    }

    /**
     * Loads the {@link Texture} for the
     * {@link ateamproject.kezuino.com.github.utility.game.balloons.BalloonMessage}.
     *
     * @param name Name of the {@link Texture} to load in the assets.
     * @return {@link Texture} that was loaded by the name of the
     * {@link ateamproject.kezuino.com.github.utility.game.balloons.BalloonMessage}.
     */
    public static Texture getBalloon(String name) {
        return get("textures/balloons/" + name + ".png", Texture.class);
    }

    /**
     * Retrieves the {@link ParticleEffect} by the given {@code name}.
     * Automatically loads the {@link ParticleEffect} if it hasn't been loaded
     * yet.
     *
     * @param name Name of the {@link ParticleEffect} file to search for.
     * @return {@link ParticleEffect} that was loaded from the file.
     */
    public static ParticleEffect getParticleEffect(String name) {
        return get(TEXTURE_DIR + "particles/" + name, ParticleEffect.class);
    }

    /**
     * Loads a {@link Sound} from the assets folder and plays it.
     *
     * @param asset Name of {@link Sound} to search for in the assets folder.
     * @return {@link Sound} from the assets folder and plays it.
     */
    public static Sound playSound(String asset) {
        Sound sound = Assets.get(getFileName(AUDIO_SOUND_DIR, asset), Sound.class);
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
        Sound sound = Assets.get(getFileName(AUDIO_SOUND_DIR, asset), Sound.class);
        if (sound != null) {
            sound.loop();
        }
        return sound;
    }

    /**
     * Creates a {@link Music Musicstream} to stream {@link Music} while it's
     * playing.
     *
     * @param fileName Name of {@link Music} to search for in the assets folder.
     * @return {@link Music Musicstream} to stream {@link Music} while it's
     * playing
     */
    public static Music getMusicStream(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }
        String asset = getFileName(AUDIO_MUSIC_DIR, fileName);

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
     * Gets the vertex and fragement shaders and creates a new
     * {@link ShaderProgram}.
     *
     * @param shaderName Name of the shader to search for in the assets folder.
     * @return {@link ShaderProgram} that has been created by the two shader
     * files.
     */
    public static ShaderProgram getShaderProgram(String shaderName) {
        ShaderProgram.pedantic = false;
        String assetName = FilenameUtils.getFileNameWithoutExtension(shaderName);
        FileHandle vertexFile = new FileHandle((getFileName(SHADER_DIR + "vertex", assetName + ".vsh")));
        FileHandle fragmentFile = new FileHandle((getFileName(SHADER_DIR + "fragment", assetName + ".fsh")));
        if (!vertexFile.exists() || !fragmentFile.exists()) {
            throw new GdxRuntimeException("Couldn't find shader files.");
        }

        ShaderProgram shader = new ShaderProgram(vertexFile.readString(), fragmentFile.readString());
        if (!shader.isCompiled()) {
            Gdx.app.error("SHADER", shader.getLog());
        }
        return shader;
    }

    /**
     * Gets the {@link Path paths} of all the skin directories.
     *
     * @return {@link Path paths} of all the skin directories. Or empty (not null!) if no directories were found.
     */
    public static List<Path> getSkins() {
        ArrayList<Path> result = new ArrayList<>();

        File skins = Gdx.files.local("skins").file();
        if (!skins.exists()) return result;

        File[] files = skins.listFiles();
        return files == null ? result : Stream.of(files).map(File::toPath).collect(Collectors.toList());
    }

    public static void setDebug(boolean debug) {
        Assets.debug = debug;
    }

    public static void clean() {
        musicInstances.clear();
        manager.clear();
    }

    public static void close() {
        musicInstances.clear();
        manager.dispose();
    }
}
