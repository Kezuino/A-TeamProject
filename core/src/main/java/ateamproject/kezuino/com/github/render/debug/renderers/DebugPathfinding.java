package ateamproject.kezuino.com.github.render.debug.renderers;

import ateamproject.kezuino.com.github.render.IRenderable;
import ateamproject.kezuino.com.github.render.debug.DebugLayers;
import ateamproject.kezuino.com.github.render.debug.DebugRenderer;
import ateamproject.kezuino.com.github.render.debug.IDebugRenderer;
import ateamproject.kezuino.com.github.singleplayer.Node;
import ateamproject.kezuino.com.github.utility.Assets;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Anton on 4/4/2015.
 */
public class DebugPathfinding extends DebugRenderer<Node> {
    /**
     * Initializes a {@link IDebugRenderer} and starts it with listening to a specific {@link DebugLayers Layer}.
     */
    public DebugPathfinding() {
        super(DebugLayers.Background);
    }

    @Override
    public void render(Node layerObject) {
        super.render(layerObject);

    }
}
