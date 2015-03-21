package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;

public class Enemy extends GameObject {

    private boolean Dead;
    private boolean Edible;
    /**
     * Can be null!
     */
    private Pactale pactaleToFollow;
    private Node Node;

    /**
     * Will move a Enemy to its spawn and reset some of its properties.
     */
    public void respawn() {
        // TODO - implement Enemy.respawn
        throw new UnsupportedOperationException();
    }

    /**
     * @return If the enemy is dead this will return true, if the enemy is still alive will return false
     */
    public boolean isDead() {
        return this.Dead;
    }

    /**
     * Sets the dead propertie of the enemy. If the enemy is dead it should be set to true, if the enemy is not dead should be false.
     *
     * @param dead Must be true or false, cannot be null
     */
    public void setDead(boolean dead) {
        this.Dead = dead;
    }

    /**
     * @return True if the enemy is edible at the moment, false if the enemy is not edible.
     */
    public boolean isEdible() {
        return this.Edible;
    }

    /**
     * Sets wether the enemy is edible or not, the enemy is edible if true. The enemy is not edible if false
     *
     * @param eatable Boolean, cannot be null
     */
    public void setEdible(boolean eatable) {
        this.Edible = eatable;
    }

    /**
     * Initializes an enemy. Default isDead = False, isEatable = False.
     *
     * @param pactaleToFollow  Pactale object to follow, CAN BE NULL
     * @param map
     * @param spawningpoint
     * @param movementSpeed
     * @param walkingDirection
     * @param color
     */
    public Enemy(Pactale pactaleToFollow, Map map, Node spawningpoint, float movementSpeed, Direction walkingDirection, Color color) {
        super(map, spawningpoint.getX(), spawningpoint.getY(), movementSpeed, walkingDirection, color);
        // TODO - implement Enemy.Enemy
        throw new UnsupportedOperationException();
    }

    /**
     * Will move the enemy to a specific location.
     *
     * @param position Node, cannot be null
     */
    public void teleport(Node position) {
        // TODO - implement Enemy.teleport
        throw new UnsupportedOperationException();
    }

    /**
     * @return The pectale that this enemy follows. Can return null.
     */
    public Pactale getPactaleToFollow() {
        return this.pactaleToFollow;
    }
}