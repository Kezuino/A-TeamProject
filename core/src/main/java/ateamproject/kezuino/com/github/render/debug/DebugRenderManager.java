package ateamproject.kezuino.com.github.render.debug;

import ateamproject.kezuino.com.github.singleplayer.IPositionable;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Renders a debug overlay on a orthographical game.
 */
public class DebugRenderManager {
    private static HashMap<DebugLayers, SpriteBatch> layerSpriteBatches;
    /**
     * Contains all debug renderers.
     */
    private static List<IDebugRenderer> renderers;

    /**
     * {@link Camera} used for setting the {@link SpriteBatch#projectionMatrix} when calling the {@link DebugRenderer#render(IPositionable)}.
     */
    private static Camera camera;

    static {
        renderers = new ArrayList<>();
        layerSpriteBatches = new HashMap<>();
    }

    public static void setCamera(Camera camera) {
        DebugRenderManager.camera = camera;
    }

    /**
     * Activates all {@link IDebugRenderer DebugRenderers} that are listening for this {@link DebugLayers layer}.
     *
     * @param layer {@link DebugLayers Layer} to search {@link IDebugRenderer DebugRenderers} by.
     */
    public static void active(DebugLayers layer) {
        if (layer == null) return;
        for (IDebugRenderer renderer : renderers) {
            if (!renderer.hasLayerFlag(layer)) continue;
            renderer.active();
        }
    }

    /**
     * Renders alls {@link IDebugRenderer DebugRenderers} that are listening for this layer.
     *
     * @param layer  {@link DebugLayers Layer} to search {@link IDebugRenderer DebugRenderers} by.
     * @param object {@link IPositionable} to be drawn on.
     */
    public static void render(DebugLayers layer, IPositionable object) {
        if (layer == null) return;
        if (!layer.isVisible()) return;
        for (IDebugRenderer renderer : renderers) {
            if (!renderer.hasLayerFlag(layer)) continue;
            renderer.render(object);
        }
    }

    /**
     * Renders alls {@link IDebugRenderer DebugRenderers} that are listening for this layer.
     *
     * @param layer {@link DebugLayers Layer} to search {@link IDebugRenderer DebugRenderers} by.
     */
    public static void render(DebugLayers layer) {
        render(layer, null);
    }

    /**
     * Adds a {@link IDebugRenderer} to the list of renderers.
     *
     * @param renderer {@link IDebugRenderer} to add to the list of renderers.
     * @param <T>      Any type of {@link IDebugRenderer}.
     * @return {@link IDebugRenderer} that was given for method chaining.
     */
    public static <T extends IDebugRenderer> T addRenderer(T renderer) {
        renderers.add(renderer);
        if (renderer instanceof DebugRenderer) {
            DebugRenderer debugRenderer = (DebugRenderer) renderer;
            debugRenderer.batch = getSpriteBatch(renderer.getLayer());
            debugRenderer.camera = camera;
        }
        return renderer;
    }

    public static void setVisible(boolean value) {
        for (DebugLayers layer : DebugLayers.values()) {
            layer.setVisible(value);
        }
    }


    public static void show() {
        setVisible(true);
    }

    public static void hide() {
        setVisible(false);
    }

    public static boolean isHidden() {
        for (DebugLayers layer : DebugLayers.values()) {
            if (layer.isVisible()) return false;
        }
        return true;
    }

    public static SpriteBatch getSpriteBatch(DebugLayers layer) {
        if (layer == null) throw new IllegalArgumentException("Parameter layer must not be null.");
        if (!layerSpriteBatches.containsKey(layer)) {
            layerSpriteBatches.put(layer, new SpriteBatch());
        }
        return layerSpriteBatches.get(layer);
    }
}
