package ateamproject.kezuino.com.github.utility.game.balloons;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

import java.util.HashMap;
import java.util.HashSet;

public abstract class BalloonMessage<T extends BalloonMessage> {
    /**
     * Timer for {@link BalloonMessage} fading and other effects.
     */
    protected Timer timer;

    protected static HashSet<BalloonMessage> balloonMessages;
    protected static HashMap<Class, BalloonMessage> cachedBalloonMessages;

    static {
        balloonMessages = new HashSet<>();
        cachedBalloonMessages = new HashMap<>();
    }

    /**
     * Destination size (width, height) to draw the {@link BalloonMessage} on.
     */
    protected Vector2 size;
    /**
     * Total time in milliseconds that this {@link BalloonMessage} will be visible.
     */
    protected float totalTimeShown;
    /**
     * Exact position to draw the {@link BalloonMessage messages} on.
     */
    protected Vector2 position;
    /**
     * Texture that will be rendered.
     */
    private Texture texture;

    public BalloonMessage() {
        timer = new Timer();

        this.totalTimeShown = 1f;
        this.position = Vector2.Zero;
        createTexture();
        this.size = new Vector2(texture.getWidth(), texture.getHeight());
    }

    public BalloonMessage(float totalTimeShown, Vector2 position, Vector2 size) {
        timer = new Timer();

        this.totalTimeShown = totalTimeShown;
        this.position = position;
        this.size = size;
    }

    /**
     * Renders all the {@link BalloonMessage messages}.
     *
     * @param batch {@link SpriteBatch} to use for drawing the {@link BalloonMessage messages}.
     */
    public static void renderAll(SpriteBatch batch) {
        for (BalloonMessage balloon : balloonMessages) {
            balloon.render(batch);
        }
    }

    /**
     * Adds the {@link BalloonMessage} to the {@link #balloonMessages}. The {@link BalloonMessage} will be automatically removed when the {@link #totalTimeShown} reaches zero.
     *
     * @param balloon {@link BalloonMessage} to add to the {@link #balloonMessages}.
     * @return The {@code balloon} parameter for chaining.
     */
    public static <T extends BalloonMessage> T addBalloonMessage(T balloon) {
        if (balloon == null) return null;
        balloonMessages.add(balloon);
        balloon.timer.clear();
        balloon.timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                balloonMessages.remove(balloon);
            }
        }, 1);
        return balloon;
    }

    /**
     * Gets (if it exists) or creates a {@link BalloonMessage} to be reused later and returns it for setting it up. This instance must be added with {@link #addBalloonMessage(BalloonMessage)} to be visible.
     *
     * @return {@link BalloonMessage} for chaining.
     */
    public static <T extends BalloonMessage> T getBalloonMessage(Class<T> clazz) {
        T instance = null;
        if (cachedBalloonMessages.get(clazz) == null) {
            try {
                instance = clazz.newInstance();
                cachedBalloonMessages.put(clazz, instance);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return (T) cachedBalloonMessages.get(clazz);
    }

    /**
     * Gets the total time in milliseconds that this {@link BalloonMessage} will be visible.
     *
     * @return total time in milliseconds that this {@link BalloonMessage} will be visible.
     */
    public float getTotalTimeShown() {
        return totalTimeShown;
    }

    /**
     * Gets the exact position to draw the {@link BalloonMessage messages} on.
     *
     * @return Exact position to draw the {@link BalloonMessage messages} on.
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Sets the exact position to draw the {@link BalloonMessage} on.
     *
     * @param position Exact position to draw the {@link BalloonMessage} on.
     * @return The {@link BalloonMessage} for chaining.
     */
    public BalloonMessage<T> setPosition(Vector2 position) {
        this.position = position;
        return this;
    }

    /**
     * Sets the position centered to draw the {@link BalloonMessage} on based on the {@link #size}.
     *
     * @param position Position to draw {@link BalloonMessage} on.
     * @return {@link BalloonMessage} for chaining.
     */
    public BalloonMessage<T> setPositionCenter(Vector2 position) {
        return setPositionCenter(position, false);
    }

    /**
     * Sets the position centered to draw the {@link BalloonMessage} on based on the {@link #size}.
     *
     * @param position    Position to draw {@link BalloonMessage} on.
     * @param negativeAdd If true, the centering offset will be substracted from the given position.
     * @return {@link BalloonMessage} for chaining.
     */
    public BalloonMessage<T> setPositionCenter(Vector2 position, boolean negativeAdd) {
        createTexture();
        // TODO: Fix centering..
        this.position = position.cpy().add(32 / 2f * (negativeAdd ? -1 : 1), (size.y / 2f * (negativeAdd ? -1 : 1)));
        return this;
    }

    /**
     * Creates the {@link Texture} based on the {@link #createGraphic()} implementation if it doesn't exist.
     */
    protected void createTexture() {
        if (texture != null) return;
        texture = new Texture(createGraphic());
    }

    /**
     * Creates the graphic for the first time when it's required for {@link #render(SpriteBatch) rendering}.
     */
    public abstract Pixmap createGraphic();

    /**
     * Renders the {@link BalloonMessage} on the {@link #position}.
     *
     * @param batch {@link SpriteBatch} to be used for drawing the {@link BalloonMessage} {@link com.badlogic.gdx.graphics.Texture}.
     */
    public void render(SpriteBatch batch) {
        if (batch == null) throw new IllegalArgumentException("Parameter batch must not be null.");
        if (texture == null) texture = new Texture(createGraphic());

        batch.draw(texture, position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    /**
     * Adds the current {@link BalloonMessage} to the {@link #balloonMessages} so that it will be visible.
     */
    public BalloonMessage<T> addBalloonMessage() {
        return addBalloonMessage(this);
    }
}
