package ateamproject.kezuino.com.github.render.orthographic;

import ateamproject.kezuino.com.github.render.IRenderer;
import ateamproject.kezuino.com.github.render.orthographic.camera.Camera;
import ateamproject.kezuino.com.github.singleplayer.*;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameUIRenderer implements IRenderer {
    private final SpriteBatch batch;
    private final Camera camera;
    private final BitmapFont font;
    private final TextureRegion gold;

    private final Map map;
    private final GameSession session;
    private final Pactale pactale;

    public GameUIRenderer(Map map) {
        // Camera.
        camera = new Camera(map.getWidth() * 32, map.getHeight() * 32 + 50, map, 32);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        font = new BitmapFont(Gdx.files.internal("fonts/credits.fnt"), Gdx.files.internal("fonts/credits_0.png"), false);
        font.setColor(Color.WHITE);
        
        gold = new TextureRegion(Assets.getTexture("smallobject.png"));

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
        batch.draw(gold, 0, -27);
        font.draw(batch, "score: " + session.getScore().valueOf(), 30, -5);
        font.draw(batch, "test", camera.viewportWidth - 100, -5);
        batch.end();
    }
}
