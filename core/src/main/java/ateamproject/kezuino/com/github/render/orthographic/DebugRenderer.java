package ateamproject.kezuino.com.github.render.orthographic;

import ateamproject.kezuino.com.github.render.IRenderer;
import ateamproject.kezuino.com.github.render.orthographic.camera.Camera;
import ateamproject.kezuino.com.github.singleplayer.*;
import ateamproject.kezuino.com.github.utility.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Renders a debug overlay on a orthographical game.
 */
public class DebugRenderer implements IRenderer {
    private SpriteBatch batch;
    private Map map;
    private Camera camera;

    private BitmapFont fpsFont;

    public DebugRenderer(Map map) {
        this.map = map;

        // Camera.
        this.camera = new Camera(map.getWidth() * 32 + 100, map.getHeight() * 32 + 100, map, 32);
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // TODO: Flip drawing (because of inverted Y when setToOrtho(true, ...) but gives nice coordiantes in the draw method (won't need to invert them then :)).
        this.batch = new SpriteBatch();
        this.batch.setProjectionMatrix(camera.combined);

        // Fonts.
        fpsFont = Assets.fonts.get("fps");
    }

    @Override
    public void active() {
        // Init debug rendering.
    }

    @Override
    public void render() {
        batch.begin();

        // Render FPS.
        fpsFont.draw(batch, String.valueOf(Gdx.graphics.getFramesPerSecond()), 20, 20);

        // Render Statistics.


        // Render Pathfinding.


        batch.end();
    }
}
