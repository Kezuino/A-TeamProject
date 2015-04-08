package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

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
     * @param direction Direction this {@link Projectile} is moviong towards.
     * @param color {@link com.badlogic.gdx.graphics.Color} of this
     * {@link Projectile}.
     */
    public Projectile(Map map, int x, int y, Pactale owner, float movementSpeed, Direction direction, Color color) {
        super(map, x, y, movementSpeed, direction, color);
        this.owner = owner;
        this.drawOnDirection=true;
        this.setTexture(Assets.get("textures/projectile.png", Texture.class));
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
     * Checks whether the next {@link Node} this {@link Projectile} is heading
     * will collide with a different {@link GameObject} or impenetrable tile
     * {@link Node}.
     *
     * @return True if it will collide with a different object or impenetrable
     * {@link Node}, else false.
     */
    /*public Boolean hasCollision() {
        if (direction == null) {
            return null;
        }

        Node NextNode;
        int x = this.getX();
        int y = this.getY();

        if (direction == null) {
            return null;
        }

        NextNode = this.getMap().getNode(x + direction.getX(), y + direction.getY());
        if (NextNode.isWall()) {
            return this.collisionWithWall(NextNode);
        } else if (!this.getMap().getAllGameObjects().isEmpty()) {
            // Collision with a GameObject.;
            for (GameObject obj : this.getMap().getAllGameObjects()) {
                return collisionWithGameObject(obj);
            }
        }

        // No collision.
        return false;
    }*/

    @Override
    protected boolean collisionWithGameObject(GameObject object) {
        if (object.equals(owner)) return false;

        // TODO: Collision
        GameObject obj = object;
        if (this.getOwner().getPortal() != null && this.getActive()) {
            obj.setPosition(this.getOwner().getPortal().getNode().getX(), this.getOwner().getPortal().getNode().getY());
            //TODO force next direction
            //obj.setDirection(this.direction);
            this.setActive(false);
            return true;
        }
        return super.collisionWithGameObject(object); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean collisionWithWall(Node node) {
        Node NextNode = getMap().getAdjacentNode(node, this.direction);
        if (NextNode.isWall() && this.getActive()) {
            new Portal(owner, node, this.direction.reverse());
            this.setActive(false);
            return true;
        }

        return super.collisionWithWall(node); //To change body of generated methods, choose Tools | Templates.
    }

}
