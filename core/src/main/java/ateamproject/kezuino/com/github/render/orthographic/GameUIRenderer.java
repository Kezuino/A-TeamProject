package ateamproject.kezuino.com.github.render.orthographic;

import ateamproject.kezuino.com.github.render.IRenderer;
import ateamproject.kezuino.com.github.render.debug.DebugLayers;
import ateamproject.kezuino.com.github.render.debug.DebugRenderManager;
import ateamproject.kezuino.com.github.render.debug.renderers.DebugPathfinding;
import ateamproject.kezuino.com.github.render.debug.renderers.DebugStatistics;
import ateamproject.kezuino.com.github.render.orthographic.camera.Camera;
import ateamproject.kezuino.com.github.singleplayer.*;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.stream.Collectors;

public class GameUIRenderer implements IRenderer {
    private final SpriteBatch batch;
    private final Camera camera;
    private BitmapFont font;

    private Map map;
    private GameSession session;
    private Pactale pactale;

    public GameUIRenderer(Map map) {
        // Camera.
        camera = new Camera(map.getWidth() * 32 + 100, map.getHeight() * 32 + 100, map, 32);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        font = Assets.getFont("opensans.ttf");

        this.map = map;
        this.session = map.getSession();
        this.pactale = session.getPlayer(0);
    }

    @Override
    public void active() {
    }

    @Override
    public void render() {
        batch.begin();
        font.draw(batch, "levens: " + pactale.getLives() + ", score: " + session.getScore().valueOf(), 100, 0);
        batch.end();
    }
}
