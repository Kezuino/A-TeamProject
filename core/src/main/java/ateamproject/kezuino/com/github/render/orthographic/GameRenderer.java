package ateamproject.kezuino.com.github.render.orthographic;

import ateamproject.kezuino.com.github.render.IRenderer;
import ateamproject.kezuino.com.github.render.debug.DebugLayers;
import ateamproject.kezuino.com.github.render.debug.DebugRenderManager;
import ateamproject.kezuino.com.github.render.debug.renderers.DebugPathfinding;
import ateamproject.kezuino.com.github.render.debug.renderers.DebugStatistics;
import ateamproject.kezuino.com.github.render.orthographic.camera.Camera;
import ateamproject.kezuino.com.github.singleplayer.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import java.util.stream.Collectors;

public class GameRenderer implements IRenderer {

    private SpriteBatch batch;
    private final Map map;
    private final MapRenderer tileMapRenderer;
    private final Camera camera;
    private GameState state = GameState.Running;

    public GameRenderer(Map map) {
        // Camera.
        camera = new Camera(map.getWidth() * 32 + 100, map.getHeight() * 32 + 100, map, 32);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        this.map = map;

        // Init tilemap.
        tileMapRenderer = new OrthogonalTiledMapRenderer(map.getBaseMap());
        tileMapRenderer.setView(camera);

        // Init visual debugger (For debugging only! Don't use this for gameplay render).
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
        // Render background only.
        tileMapRenderer.render();

        batch.begin();

        // Render nodes.
        for (Node node : map.getNodes()) {
            // Render items.
            Item item = node.getItem();
            if (item != null) {
                if (state.equals(GameState.Running)) {
                    item.update();
                }
                item.draw(batch);
            }

            // Render portals.
            for (Portal portal : node.getPortals()) {
                if (state.equals(GameState.Running)) {
                    portal.update();
                }
                portal.draw(batch);
            }
        }

        // Cleanup gameobjects ready for deletion.
        for (GameObject obj : this.map.getAllGameObjects()
                .stream()
                .filter(o -> !o.getActive())
                .collect(Collectors.toList())) {
            this.map.removeGameObject(obj);
        }

        // Render dynamic objects.
        for (GameObject obj : this.map.getAllGameObjects()) {
            if (state.equals(GameState.Running)) {
                obj.update();
            }
            obj.draw(batch);
        }
        
        if(!state.equals(GameState.Running)) {
            Pixmap pxTest = new Pixmap(map.getWidth() * 32, map.getHeight() * 32, Pixmap.Format.Alpha);
            pxTest.setColor(0, 0, 0, 0.5f);
            pxTest.fillRectangle(0, 0, map.getWidth() * 32, map.getHeight()* 32);
            
            Texture tex = new Texture(pxTest);
            
            batch.draw(tex, 0, 0, tex.getWidth(), tex.getHeight());
        }

        this.map.getSession().getScore().generateNewScore(this.map.getAllGameObjects()); // Will calculate/decrease score once in a period.

        batch.end();
        DebugRenderManager.render(DebugLayers.UI);
    }

    /**
     * Change the status of pause. It will be set true meaning the game will stop updating
     */
    public void pause() {
      this.state = GameState.Paused;
    }
    
    /**
     * Change the status of pause to false. This will make the game start updating again
     */
    public void unpause(){
        this.state = GameState.Running;
    }
    
    public void complete() {
        this.state = GameState.Finished;
    }

}
