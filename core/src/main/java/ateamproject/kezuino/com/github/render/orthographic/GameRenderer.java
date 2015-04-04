package ateamproject.kezuino.com.github.render.orthographic;

import ateamproject.kezuino.com.github.render.IRenderer;
import ateamproject.kezuino.com.github.render.orthographic.camera.Camera;
import ateamproject.kezuino.com.github.singleplayer.*;
import ateamproject.kezuino.com.github.utility.Assets;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameRenderer implements IRenderer {
    private SpriteBatch batch;
    private Map map;
    private MapRenderer tileMapRenderer;
    private Camera camera;

    public GameRenderer(Map map) {
        batch = new SpriteBatch();
        this.map = map;

        // Camera.
        camera = new Camera(map.getWidth() * 32 + 100, map.getHeight() * 32 + 100, map, 32);

        // Init tilemap.
        tileMapRenderer = new OrthogonalTiledMapRenderer(map.getBaseMap());
        tileMapRenderer.setView(camera);
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public Camera getCamera() {
        return camera;
    }

    @Override
    public void active() {
    }

    @Override
    public void render() {
        // Render background only.
        tileMapRenderer.render(new int[]{0});

        // Render nodes.
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Node node : map.getNodes()) {
            // Render items.
            Item item = node.getItem();
            if (item != null) {
                item.update();
                item.draw(batch);
            }

            // Render portals.
            for (Portal portal : node.getPortals()) {
                portal.update();
                portal.draw(batch);
            }

            // Render dynamic objects.
            for (GameObject obj : node.getGameObjects()) {
                obj.update();
                obj.draw(batch);
            }
        }
        batch.end();
    }
}
