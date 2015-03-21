package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;

import java.util.*;

public class Pactale extends GameObject {

    private int playerIndex;
    private int lives;
    private Portal Portal;
    private Node Node;
    private Collection<Projectile> Projectile;

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
     * Initialize a pactale
     *
     * @param x                position of this @see Pactale on the @see Map.
     * @param y                position of this @see Pactale on the @see Map.
     * @param lives            Times that the @see Pactale can be hit. Defaults to 1 for multiplayer.
     * @param color            Distinct color of this pactale in the game.
     * @param movementSpeed    Amount of seconds that it will take to move to another node.
     * @param walkingDirection Looking direction to start with.
     */
    public Pactale(Map map, int x, int y, int lives, Color color, float movementSpeed, Direction walkingDirection) {
        super(map, x, y, movementSpeed, walkingDirection, color);
        this.lives = lives;
    }

    /**
     * Will shoot a portal in the direction that the pactale currently is heading.
     */
    public void shootPortal() {
        // TODO - implement Pactale.shootPortal
        throw new UnsupportedOperationException();
    }

    /**
     * Will remove all listed portals from this Pactale
     */
    public void removePortal() {
        // TODO - implement Pactale.removePortal
        throw new UnsupportedOperationException();
    }
}