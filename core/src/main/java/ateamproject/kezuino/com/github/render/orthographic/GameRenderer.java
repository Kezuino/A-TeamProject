package ateamproject.kezuino.com.github.render.orthographic;

import ateamproject.kezuino.com.github.render.IRenderer;
import ateamproject.kezuino.com.github.render.debug.DebugLayers;
import ateamproject.kezuino.com.github.render.debug.DebugRenderManager;
import ateamproject.kezuino.com.github.render.debug.renderers.DebugPathfinding;
import ateamproject.kezuino.com.github.render.debug.renderers.DebugStatistics;
import ateamproject.kezuino.com.github.render.orthographic.camera.Camera;
import ateamproject.kezuino.com.github.render.screens.Hud;
import ateamproject.kezuino.com.github.singleplayer.*;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.stream.Collectors;

public class GameRenderer implements IRenderer {
    private final SpriteBatch batch;
    private final Map map;
    private final MapRenderer tileMapRenderer;
    private final Camera camera;

    public GameRenderer(Map map, Score score) {
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
        for (GameObject gObject : map.getAllGameObjects()) {
            if (gObject.getClass().equals(Pactale.class)) {
                DebugRenderManager.addRenderer(new Hud((Pactale)gObject,score));  
            }
        }
        DebugRenderManager.show();
    }

    @Override
    public void active() {
    }

    @Override
    public void render() {
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
        }

        // Cleanup gameobjects ready for deletion.
        for (GameObject obj : this.map.getAllGameObjects()
                                      .stream()
                                      .filter(o -> !o.getActive())
                                      .collect(Collectors.toList())) {
            this.map.removeGameObject(obj);
            obj.destroy();
        }

        // Render dynamic objects.
        for (GameObject obj : this.map.getAllGameObjects()) {
            obj.update();
            obj.draw(batch);
        }
        
        if(!this.map.getNodes().stream().anyMatch(n -> n.hasItem())) {
            for (GameObject obj : this.map.getAllGameObjects()) {
                obj.destroy();
            }             
        }
        
        batch.end();

        DebugRenderManager.render(DebugLayers.UI);
    }
}
