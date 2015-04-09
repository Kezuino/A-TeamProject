package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.render.debug.DebugRenderer;
import ateamproject.kezuino.com.github.render.debug.IDebugRenderer;
import ateamproject.kezuino.com.github.render.debug.DebugLayers;
import ateamproject.kezuino.com.github.singleplayer.IPositionable;
import ateamproject.kezuino.com.github.singleplayer.IPositionable;
import ateamproject.kezuino.com.github.singleplayer.Pactale;
import ateamproject.kezuino.com.github.singleplayer.Score;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Jip
 */
public class Hud extends DebugRenderer {
    private final BitmapFont font;
    private final Pactale pactale;
    private final Score score;

    /**
     * Initializes a {@link IDebugRenderer} and starts it with listening to a specific {@link DebugLayers Layer}.
     */
    public Hud(Pactale pactale, Score score) {
        super(DebugLayers.UI);
        font = Assets.getFont("fonts/opensans.ttf");
        
        this.pactale = pactale;
        this.score = score;
    }

    @Override
    public void render(IPositionable layerObject) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "levens: "+pactale.getLives() +", score: "+ score.getScore(), 100, 0);
        batch.end();
    }
}
