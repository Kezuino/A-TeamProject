package ateamproject.kezuino.com.github.render.debug.renderers;

import ateamproject.kezuino.com.github.render.debug.DebugRenderer;
import ateamproject.kezuino.com.github.render.debug.IDebugRenderer;
import ateamproject.kezuino.com.github.render.debug.DebugLayers;
import ateamproject.kezuino.com.github.singleplayer.IPositionable;

/**
 * Created by Anton on 4/4/2015.
 */
public class DebugStatistics extends DebugRenderer {
    /**
     * Initializes a {@link IDebugRenderer} and starts it with listening to a specific {@link DebugLayers Layer}.
     */
    public DebugStatistics() {
        super(DebugLayers.UI);
    }

    @Override
    public void render(IPositionable layerObject) {
        super.render(layerObject);
    }
}
