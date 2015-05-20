package ateamproject.kezuino.com.github.utility.game;

import ateamproject.kezuino.com.github.singleplayer.Map;
import ateamproject.kezuino.com.github.singleplayer.Node;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Anton on 4/4/2015.
 */
public interface IPositionable {
    /**
     * {@link Map} that contains this {@link IPositionable}.
     */
    Map getMap();

    Vector2 getExactPosition();
}
