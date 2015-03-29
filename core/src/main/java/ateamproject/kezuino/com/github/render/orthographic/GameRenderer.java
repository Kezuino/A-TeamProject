package ateamproject.kezuino.com.github.render.orthographic;

import ateamproject.kezuino.com.github.render.IRenderer;
import ateamproject.kezuino.com.github.render.orthographic.camera.*;
import ateamproject.kezuino.com.github.singleplayer.Map;
import ateamproject.kezuino.com.github.singleplayer.Node;
import ateamproject.kezuino.com.github.utility.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Matrix4;

public class GameRenderer implements IRenderer {
    private SpriteBatch batch;
    private Map map;
    private MapRenderer tileMapRenderer;
    private Camera camera;
    private TiledMap tiledMap;

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
        tileMapRenderer.render();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        batch.end();
    }
}
