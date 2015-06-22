package ateamproject.kezuino.com.github.render;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Specifies that an object emits particles.
 */
public interface IPactileEmitter {
    /**
     * Initializes the {@link com.badlogic.gdx.graphics.g2d.ParticleEffect}.
     */
    void createParticleEffect();

    /**
     * Destroys the {@link com.badlogic.gdx.graphics.g2d.ParticleEffect}.
     */
    void destroyPartileEffect();

    /**
     * Updates the {@link com.badlogic.gdx.graphics.g2d.ParticleEffect}.
     */
    void updateParticleEffect();

    /**
     * Draws the {@link com.badlogic.gdx.graphics.g2d.ParticleEffect}.
     *
     * @param batch {@link SpriteBatch} for drawing the {@link ParticleEffect}.
     */
    void drawParticleEffect(SpriteBatch batch);

    /**
     * Gets the {@link ParticleEffect}. Can be null.
     *
     * @return {@link ParticleEffect}. Can be null.
     */
    ParticleEffect getParticleEffect();
}
