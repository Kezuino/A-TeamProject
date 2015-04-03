package ateamproject.kezuino.com.github.render.orthographic;

import ateamproject.kezuino.com.github.render.IRenderer;
import ateamproject.kezuino.com.github.render.orthographic.camera.Camera;
import ateamproject.kezuino.com.github.singleplayer.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Renders a debug overlay on a orthographical game.
 */
public class DebugRenderer implements IRenderer {
    private SpriteBatch batch;
    private Map map;
    private Camera camera;

    public DebugRenderer(Map map, Camera camera, SpriteBatch batch) {
        batch = new SpriteBatch();
        this.map = map;

        // Camera.
        this.camera = camera;
        this.batch = batch;
    }

    public DebugRenderer(Map map) {
        this(map, new Camera(map.getWidth() * 32 + 100, map.getHeight() * 32 + 100, map, 32), new SpriteBatch());
    }

    @Override
    public void active() {
        // Init debug rendering.
    }

    @Override
    public void render() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Render FPS.


        // Render Statistics.


        // Render Pathfinding.


        batch.end();
    }
}
