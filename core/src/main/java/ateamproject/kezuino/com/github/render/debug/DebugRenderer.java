package ateamproject.kezuino.com.github.render.debug;

import ateamproject.kezuino.com.github.utility.game.IPositionable;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Anton on 4/4/2015.
 */
public abstract class DebugRenderer<T extends IPositionable> implements IDebugRenderer<T> {
    protected DebugLayers layer;
    protected SpriteBatch batch;
    protected Camera camera;

    /**
     * Initializes a {@link IDebugRenderer} and starts it with listening to a specific {@link DebugLayers Layer}.
     *
     * @param layer {@link DebugLayers Layer} to listen for.
     */
    protected DebugRenderer(DebugLayers layer) {
        if (layer == null) throw new IllegalArgumentException("Parameter layer must not be null.");
        this.layer = layer;
        this.batch = DebugRenderManager.getSpriteBatch(layer);
    }

    @Override
    public DebugLayers getLayer() {
        return layer;
    }

    @Override
    public boolean hasLayerFlag(DebugLayers layer) {
        return this.layer.equals(layer);
    }

    @Override
    public void active() {

    }

    @Override
    public void render(T layerObject) {
        // Set the projection view to the bounds of the layer object.

    }
}
