package ateamproject.kezuino.com.github.render.debug.renderers;

import ateamproject.kezuino.com.github.render.debug.DebugLayers;
import ateamproject.kezuino.com.github.render.debug.DebugRenderer;
import ateamproject.kezuino.com.github.render.debug.IDebugRenderer;
import ateamproject.kezuino.com.github.singleplayer.Node;

/**
 * Created by Anton on 4/4/2015.
 */
public class DebugMovement extends DebugRenderer<Node> {
    public DebugMovement() {
        super(DebugLayers.Background);
    }

    @Override
    public void render(Node layerObject) {
        super.render(layerObject);

    }
}
