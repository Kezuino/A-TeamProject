package ateamproject.kezuino.com.github.render.debug.renderers;

import ateamproject.kezuino.com.github.render.debug.DebugRenderer;
import ateamproject.kezuino.com.github.render.debug.IDebugRenderer;
import ateamproject.kezuino.com.github.render.debug.DebugLayers;
import ateamproject.kezuino.com.github.singleplayer.IPositionable;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Anton on 4/4/2015.
 */
public class DebugStatistics extends DebugRenderer {
    private BitmapFont font;

    /**
     * Initializes a {@link IDebugRenderer} and starts it with listening to a specific {@link DebugLayers Layer}.
     */
    public DebugStatistics() {
        super(DebugLayers.UI);

        font = Assets.getFont("fonts/opensans.ttf");
    }

    @Override
    public void render(IPositionable layerObject) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, 0);
        batch.end();
    }
}
