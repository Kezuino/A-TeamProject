package ateamproject.kezuino.com.github.render.orthographic;

import ateamproject.kezuino.com.github.render.IRenderer;
import ateamproject.kezuino.com.github.render.orthographic.camera.*;
import ateamproject.kezuino.com.github.singleplayer.GameObject;
import ateamproject.kezuino.com.github.singleplayer.Map;
import ateamproject.kezuino.com.github.singleplayer.Node;
import ateamproject.kezuino.com.github.utility.Assets;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
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

    @Override
    public void active() {

    }

    @Override
    public void render() {
        // Render background.
        tileMapRenderer.render();

        // Render objects.
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (GameObject obj : map.getAllGameObjects()) {
            obj.update();
            obj.draw(batch);
        }
        batch.end();
    }
}
