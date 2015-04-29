package ateamproject.kezuino.com.github.render.orthographic;

import ateamproject.kezuino.com.github.render.IRenderer;
import ateamproject.kezuino.com.github.render.orthographic.camera.Camera;
import ateamproject.kezuino.com.github.singleplayer.*;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameUIRenderer implements IRenderer {
    private final SpriteBatch batch;
    private final Camera camera;
    private final BitmapFont font;

    private final Map map;
    private final GameSession session;
    private final Pactale pactale;

    public GameUIRenderer(Map map) {
        // Camera.
        camera = new Camera(map.getWidth() * 32, map.getHeight() * 32 + 50, map, 32);

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
        font.draw(batch, "levens: " + pactale.getLives() + ", score: " + session.getScore().valueOf(), 0, 0);
        batch.end();
    }
}
