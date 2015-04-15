package ateamproject.kezuino.com.github.render.orthographic;

import ateamproject.kezuino.com.github.render.IRenderer;
import ateamproject.kezuino.com.github.render.debug.DebugLayers;
import ateamproject.kezuino.com.github.render.debug.DebugRenderManager;
import ateamproject.kezuino.com.github.render.debug.renderers.DebugMovement;
import ateamproject.kezuino.com.github.render.orthographic.camera.Camera;
import ateamproject.kezuino.com.github.singleplayer.*;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.stream.Collectors;

public class GameRenderer implements IRenderer {

    private final Map map;
    private final MapRenderer tileMapRenderer;
    private final Camera camera;
    private final GameSession session;
    private SpriteBatch batch;

    public GameRenderer(GameSession session) {
        this.session = session;
        this.map = this.session.getMap();

        // Camera.
        camera = new Camera(map.getWidth() * 32, map.getHeight() * 32, map, 32);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        // Init tilemap.
        tileMapRenderer = new OrthogonalTiledMapRenderer(map.getBaseMap());
        tileMapRenderer.setView(camera);

        // Init visual debugger (For debugging only! Don't use this for gameplay render).
        DebugRenderManager.setCamera(camera);
        //DebugRenderManager.addRenderer(new DebugStatistics());
        //DebugRenderManager.addRenderer(new DebugPathfinding());
        DebugRenderManager.addRenderer(new DebugMovement());
        //DebugRenderManager.active();
        //DebugRenderManager.show();
    }

    @Override
    public void active() {
    }

    @Override
    public void render() {
        // Render background only.
        tileMapRenderer.render();

        // Render nodes.
        for (Node node : map.getNodes()) {
            // Render items.
            Item item = node.getItem();
            if (item != null) {
                if (this.session.getState().equals(GameState.Running)) {
                    item.update();
                }
                batch.begin();
                item.draw(batch);
                batch.end();
            }

            // Render portals.
            for (Portal portal : node.getPortals()) {
                if (this.session.getState().equals(GameState.Running)) {
                    portal.update();
                }
                batch.begin();
                portal.draw(batch);
                batch.end();
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
            if (this.session.getState().equals(GameState.Running)) {
                obj.update();
            }
            DebugRenderManager.render(DebugLayers.GameObject, obj);
            batch.begin();
            obj.draw(batch);
            batch.end();
        }

        if (!this.session.getState().equals(GameState.Running)) {
            Pixmap pxTest = new Pixmap(map.getWidth() * 32, map.getHeight() * 32, Pixmap.Format.Alpha);
            pxTest.setColor(0, 0, 0, 0.5f);
            pxTest.fillRectangle(0, 0, map.getWidth() * 32, map.getHeight() * 32);

            Texture tex = new Texture(pxTest);

            batch.begin();
            batch.draw(tex, 0, 0, tex.getWidth(), tex.getHeight());
            batch.end();
        }

        this.map.getSession()
                .getScore()
                .generateNewScore(this.map.getAllGameObjects()); // Will calculate/decrease score once in a period.

        // Check if here are any pactales on the map.
        if (!this.map.getAllGameObjects().stream().anyMatch(go -> go instanceof Pactale)) {
            this.session.gameOver();
        }

        // Check if there are any items on the map.
        if (!this.map.getNodes().stream().anyMatch(n -> n.hasItem())) {
            this.session.complete();
        }
        DebugRenderManager.render(DebugLayers.UI);
    }

    /**
     * Change the status of pause. It will be set true meaning the game will stop updating
     */
    /*public void pause() {
      this.state = GameState.Paused;
    }*/

    /**
     * Change the status of pause to false. This will make the game start updating again
     */
    /*public void unpause(){
        this.state = GameState.Running;
    }

    public GameState getState() {
        return this.state;
    }*/

}
