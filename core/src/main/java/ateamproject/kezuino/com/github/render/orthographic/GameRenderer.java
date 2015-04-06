package ateamproject.kezuino.com.github.render.orthographic;

import ateamproject.kezuino.com.github.render.IRenderer;
import ateamproject.kezuino.com.github.render.debug.DebugLayers;
import ateamproject.kezuino.com.github.render.debug.DebugRenderManager;
import ateamproject.kezuino.com.github.render.debug.renderers.DebugPathfinding;
import ateamproject.kezuino.com.github.render.debug.renderers.DebugStatistics;
import ateamproject.kezuino.com.github.render.orthographic.camera.Camera;
import ateamproject.kezuino.com.github.singleplayer.*;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameRenderer implements IRenderer {
    private SpriteBatch batch;
    private Map map;
    private MapRenderer tileMapRenderer;
    private Camera camera;

    public GameRenderer(Map map) {
        // Camera.
        camera = new Camera(map.getWidth() * 32 + 100, map.getHeight() * 32 + 100, map, 32);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        this.map = map;

        // Init tilemap.
        tileMapRenderer = new OrthogonalTiledMapRenderer(map.getBaseMap());
        tileMapRenderer.setView(camera);

        // Init visual debugger.
        DebugRenderManager.setCamera(camera);
        DebugRenderManager.addRenderer(new DebugStatistics());
        DebugRenderManager.addRenderer(new DebugPathfinding());
        DebugRenderManager.show();
    }

    @Override
    public void active() {
    }

    @Override
    public void render() {
        if (!DebugRenderManager.isHidden()) {
            renderWithDebugger();
            return;
        }

        // Render background only.
        tileMapRenderer.render();

        // Render nodes.
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

    public void renderWithDebugger() {
        DebugRenderManager.render(DebugLayers.First);

        // Render background only.
        tileMapRenderer.render(new int[]{0});

        // Render nodes.
        for (Node node : map.getNodes()) {
            // Background.
            DebugRenderManager.render(DebugLayers.Background);

            // Render items.
            batch.begin();
            Item item = node.getItem();
            if (item != null) {
                item.update();
                item.draw(batch);
            }
            batch.end();
            DebugRenderManager.render(DebugLayers.Item, item);

            // Render portals.
            for (Portal portal : node.getPortals()) {
                batch.begin();
                portal.update();
                portal.draw(batch);
                batch.end();
                DebugRenderManager.render(DebugLayers.Portal, portal);
            }

            // Render dynamic objects.
            for (GameObject obj : node.getGameObjects()) {
                batch.begin();
                obj.update();
                obj.draw(batch);
                batch.end();
                DebugRenderManager.render(DebugLayers.GameObject, obj);
            }
        }
    }
}
