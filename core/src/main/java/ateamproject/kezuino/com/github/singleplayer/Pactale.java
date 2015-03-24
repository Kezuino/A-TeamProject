package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;

import java.util.*;

public class Pactale extends GameObject {

    private int playerIndex;
    private int lives;
    private Portal Portal;
    private Node Node;
    private Collection<Projectile> Projectile;

    /**
     * Initialize a {@link Pactale}.
     *  @param x                position of this {@link ateamproject.kezuino.com.github.singleplayer.Pactale} on the @see Map.
     * @param y                position of this {@link ateamproject.kezuino.com.github.singleplayer.Pactale} on the @see Map.
     * @param lives            Times that the {@link ateamproject.kezuino.com.github.singleplayer.Pactale} can be hit. Defaults to 1 for a multiplayer session.
     * @param movementSpeed    Amount of seconds that it will take to move to another node.
     * @param walkingDirection Looking direction to start with.
     * @param color            Distinct color of this {@link ateamproject.kezuino.com.github.singleplayer.Pactale} in the game.
     */
    public Pactale(Map map, int x, int y, int lives, float movementSpeed, Direction walkingDirection, Color color) {
        super(map, x, y, movementSpeed, walkingDirection, color);
        this.lives = lives;
    }

    public Portal getPortal() {
        return this.Portal;
    }

    public int getLives() {
        return this.lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Will shoot a portal in the direction that the {@link Pactale} currently is heading.
     */
    public void shootPortal() {
        // TODO - implement Pactale.shootPortal
        throw new UnsupportedOperationException();
    }

    /**
     * Will remove all listed portals from this {@link Pactale}.
     */
    public void removePortal() {
        // TODO - implement Pactale.removePortal
        throw new UnsupportedOperationException();
    }
}