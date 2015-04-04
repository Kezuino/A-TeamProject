package ateamproject.kezuino.com.github.singleplayer;

/**
 * Created by Anton on 4/4/2015.
 */
public interface IPositionable {
    /**
     * {@link Map} that contains this {@link IPositionable}.
     */
    Map getMap();

    /**
     * Returns the {@link Node} based on the X and Y axis on the {@link Map}.
     *
     * @return {@link Node} based on the X and Y axis on the {@link Map}.
     */
    Node getNode();
}
