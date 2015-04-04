package ateamproject.kezuino.com.github.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

/**
 * Created by Anton on 4/3/2015.
 */
public interface IRenderable {
    /**
     * Gets the {@link Texture} used in the {@link #draw(SpriteBatch)} method.
     *
     * @return {@link Texture} used in the {@link #draw(SpriteBatch)} method.
     */
    Texture getTexture();

    /**
     * Sets the {@link Texture} used in the {@link #draw(SpriteBatch)} method.
     *
     * @param texture {@link Texture} used in the {@link #draw(SpriteBatch)} method.
     */
    void setTexture(Texture texture);

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
