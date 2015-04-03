package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

/**
 * Created by Anton on 4/3/2015.
 */
public interface IRenderable {
    /**
     * Called before the {@link #draw(SpriteBatch)} to update the state of this {@link IRenderable}.
     */
    void update();

    /**
     * Renders the {@link IRenderable} using the given {@code batch} or a custom batch.
     *
     * @param batch {@link SpriteBatch} with the correct {@link Matrix4 Projectionmatrix} for drawing.
     */
    void draw(SpriteBatch batch);
}
