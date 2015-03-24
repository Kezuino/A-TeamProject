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
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class Renderer2D implements IRenderer {
    private final SpriteBatch batch;
    private final Map map;
    private final MapRenderer tileMapRenderer;
    private final Camera camera;
    private final TiledMap tiledMap;

    public Renderer2D(Map map) {
        batch = new SpriteBatch();
        this.map = map;
        tiledMap = new TiledMap();
        tileMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        // Camera.
        camera = new Camera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), map, 32);

        // Init tilemap.
        tiledMap.getLayers().add(getBackgroundLayer());
        // TODO: tiledMap.getLayers().add(getObjectLayer());
    }

    /**
     * Builds the static layer of the map.
     *
     * @return {@link com.badlogic.gdx.maps.tiled.TiledMapTileLayer} that holds all texture data based on the dimensions of the {@link ateamproject.kezuino.com.github.singleplayer.Map#nodes}.
     */
    protected TiledMapTileLayer getBackgroundLayer() {
        TiledMapTileLayer layer = new TiledMapTileLayer(map.getWidth(), map.getHeight(), 32, 32);

        // Set textures on all nodes in 2D array of map.
        for (Node node : map.getNodes()) {
            Texture texture = null; // Default texture.

            if (node.getWall() != null) {
                texture = Assets.manager.get("nodes/wall.png");
            } else if (node.getWall() == null) {
                texture = Assets.manager.get("nodes/floor.png");
            }

            // Create a tile for this node.
            TiledMapTile tile = new StaticTiledMapTile(new TextureRegion(texture));
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            cell.setTile(tile);

            // Set tile in relative position with the map.
            layer.setCell(node.getX(), node.getY(), cell);
        }

        return layer;
    }

    protected TiledMapTileLayer getObjectLayer() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void active() {

    }

    @Override
    public void render() {
        tileMapRenderer.setView(camera);
        tileMapRenderer.render();

        batch.begin();
        Assets.fonts.get("fps").draw(batch, "FPS " + String.valueOf(Gdx.graphics.getFramesPerSecond()), 0, camera.viewportHeight);
        batch.end();
    }
}
