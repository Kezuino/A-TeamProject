package ateamproject.kezuino.com.github.render.orthographic.debug;

import ateamproject.kezuino.com.github.render.IRenderer;
import ateamproject.kezuino.com.github.render.orthographic.camera.Camera;
import ateamproject.kezuino.com.github.singleplayer.Map;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

/**
 * Renders a debug overlay on a orthographical game.
 */
public class DebugRenderManager {
    /**
     * {@link Map} used by the current {@link ateamproject.kezuino.com.github.singleplayer.GameSession}.
     */
    private Map map;
    private Camera camera;
    /**
     * Contains all debug renderers.
     */
    private List<IRenderer> renderers;

    public DebugRenderManager(Map map) {
        renderers = new ArrayList<>();
        this.map = map;

        // Camera.
        this.camera = new Camera(map.getWidth() * 32 + 100, map.getHeight() * 32 + 100, map, 32);
        this.camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // TODO: Flip drawing (because of inverted Y when setToOrtho(true, ...) but gives nice coordiantes in the draw method (won't need to invert them then :)).
    }

    /**
     * Gets the {@link Map} used by the current {@link ateamproject.kezuino.com.github.singleplayer.GameSession}.
     *
     * @return {@link Map} used by the current {@link ateamproject.kezuino.com.github.singleplayer.GameSession}.
     */
    public Map getMap() {
        return map;
    }

}
