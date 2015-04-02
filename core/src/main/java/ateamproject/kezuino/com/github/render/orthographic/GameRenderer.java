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
        SpriteBatch batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (GameObject obj : map.getAllGameObjects()) {
            Node node = obj.getNode();
            int x = 0;
            int y = 0;
            
            if(node != null) {
                x = node.getX();
                y = node.getY();
            } else {
                System.out.printf("Node is null: %s%n", obj);
            }

            batch.setColor(obj.getColor());
            
            Texture texture = Assets.manager.get("characters/pactale.png", Texture.class);
            TextureRegion region = new TextureRegion(texture, 0, 0, 28, 32);

            // Draw centered in node.
            // TODO: Add x and y draw offset for movement interpolation.
            float xOffset = (32 - region.getRegionWidth()) / 2;
            float yOffset = (32 - region.getRegionHeight()) / 2;
            batch.draw(region, x * 32 + xOffset, y * 32 + yOffset);
        }
        batch.end();
    }
}
