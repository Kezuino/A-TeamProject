package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;

public class Enemy extends GameObject {

    private boolean isDead;
    private boolean isEdible;
    /**
     * Can be null!
     */
    private Pactale pactaleToFollow;
    private Node Node;

    public boolean isDead() {
        return this.isDead;
    }

    public void setIsDead(boolean dead) {
        this.isDead = dead;
    }

    public boolean isEdible() {
        return this.isEdible;
    }

    public void setIsEdible(boolean eatable) {
        this.isEdible = eatable;
    }

    /**
     * Initializes an enemy.
     *
     * @param pactaleToFollow
     * @param map
     * @param spawningpoint
     * @param movementSpeed
     * @param walkingDirection
     * @param color
     */
    public Enemy(Pactale pactaleToFollow, Map map, Node spawningpoint, float movementSpeed, Direction walkingDirection, Color color) {
        super(map,spawningpoint.getX(), spawningpoint.getY(), movementSpeed, walkingDirection, color);
        // TODO - implement Enemy.Enemy
        throw new UnsupportedOperationException();
    }

    /**
     * Will move a Enemy to its spawn and reset some of its properties.
     */
    public void respawn() {
        // TODO - implement Enemy.respawn
        throw new UnsupportedOperationException();
    }

    /**
     * Will move the enemy to a specific location.
     *
     * @param position
     */
    public void teleport(Node position) {
        // TODO - implement Enemy.teleport
        throw new UnsupportedOperationException();
    }

    public Pactale getPactaleToFollow() {
        return this.pactaleToFollow;
    }

}