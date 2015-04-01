package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;

public class Projectile extends GameObject {
    /*
        Field created for the {@link Pactale} this {@link Projectile}
        origined from.
    */
    private final Pactale owner;

    /**
     * Initializes a new {@link Projectile}.
     *
     * @param owner {@link Pactale} this {@link Projectile} origined from.
     * @param movementSpeed Speed in seconds it takes for this
     * {@link Projectile} to move to another adjacent {@link Node}.
     * @param direction Direction this {@link Projectile} is moviong
     * towards.
     * @param color {@link com.badlogic.gdx.graphics.Color} of this 
     * {@link Projectile}.
     */
    public Projectile(Map map, int x, int y, Pactale owner, float movementSpeed, Direction direction, Color color) {
        super(map, x, y, movementSpeed, direction, color);
        this.owner = owner;
    }

    /**
     * Gets the {@link Pactale} this {@link Projectile} origined from.
     * 
     * @return {@link Pactale} that this {@link Projectile} origined from.
     */
    public Pactale getOwner() {
        return this.owner;
    }

    /**
     * Checks wether the next {@link Node} this {@link Projectile} is heading
     * will collide with a different {@link GameObject} or impenetratable tile {@see Node}.
     * @param direction The direction this {@link Projectile} is heading?
     * @return True if it will collide with a different object or impentratable tile, else false.
     */
    public Boolean hasCollision() {
        /**
         * Will check if a colission has happened and returns a boolean
         * accordingly. if direction is null , this method will return null as
         * return value;
         * 
         * @param direction
         */
        Node NextNode = null;
        int x = this.getX();
        int y = this.getY();
        
        if (direction == null) {
            return null;
        }
        
        switch (direction) {
            case Up:
                NextNode = this.getMap().getNode(x, y--);
                if (NextNode.isWall()) {
                    // Next Node is a wall, colision detected, return false
                    return true;
                } else if (!NextNode.getGameObjects().isEmpty()) {
                    return true;
                } else { // No Wall or Gameobject found on next Node, projecttile can move to this node ,return false
                    return false;
                }
            case Right:
                NextNode = this.getMap().getNode(x++, y);
                if (NextNode.isWall()) {
                    // Next Node is a wall, colision detected, return false
                    return true;
                } else if (!NextNode.getGameObjects().isEmpty()) {
                    return true;
                } else { // No Wall or Gameobject found on next Node, projecttile can move to this node ,return false
                    return false;
                }
            case Down:
                NextNode = this.getMap().getNode(x, y++);
                if (NextNode.isWall()) {
                    // Next Node is a wall, colision detected, return false
                    return true;
                } else if (!NextNode.getGameObjects().isEmpty()) {
                    return true;
                } else { // No Wall or Gameobject found on next Node, projecttile can move to this node ,return false
                    return false;
                }
            case Left:
                NextNode = this.getMap().getNode(x--, y);
                if (NextNode.isWall()) {
                    // Next Node is a wall, colision detected, return false
                    return true;
                } else if (!NextNode.getGameObjects().isEmpty()) {
                    return true;
                } else { // No Wall or Gameobject found on next Node, projecttile can move to this node ,return false
                    return false;
                }
            default:
                return null;
        }
    }
}

