package ateamproject.kezuino.com.github.render.debug;

import ateamproject.kezuino.com.github.render.IRenderable;
import ateamproject.kezuino.com.github.render.IRenderer;
import ateamproject.kezuino.com.github.singleplayer.IPositionable;

/**
 * Created by Anton on 4/4/2015.
 */
public interface IDebugRenderer<T extends IPositionable> {
    /**
     * Returns true if this {@link IDebugRenderer} listens to this {@link DebugLayers Layer}.
     *
     * @param layer {@link DebugLayers Layer} to check for.
     * @return True if {@link IDebugRenderer} listens to this {@link DebugLayers layer}.
     */
    boolean hasLayerFlag(DebugLayers layer);

    /**
     * Gets the {@link DebugLayers Layer} that this {@link IDebugRenderer} is listening to.
     *
     * @return {@link DebugLayers Layer} that this {@link IDebugRenderer} is listening to.
     */
    DebugLayers getLayer();

    /**
     * Called when this {@link ateamproject.kezuino.com.github.render.IRenderer} should be shown.
     */
    void active();

    /**
     * Called for each requested frame by the gameloop.
     *
     * @param layerObject Any type of {@link IRenderable}.
     */
    void render(T layerObject);
}
