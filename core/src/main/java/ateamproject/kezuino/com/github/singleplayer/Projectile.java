package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.utility.assets.Assets;
import ateamproject.kezuino.com.github.utility.game.Direction;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Projectile extends GameObject {
    /**
     * Field created for the {@link Pactale} this {@link Projectile}
     * origined from.
     */
    protected Pactale owner;

    /**
     * Empty constructor needed for reflection instantiation.
     */
    public Projectile() {
        isActive = true;
    }

    /**
     * Initializes a new {@link Projectile}.
     *
     * @param owner         {@link Pactale} this {@link Projectile} origined from.
     * @param movementSpeed Speed in seconds it takes for this
     *                      {@link Projectile} to move to another adjacent {@link Node}.
     * @param direction     Direction this {@link Projectile} is moviong towards.
     * @param color         {@link Color} of this
     *                      {@link Projectile}.
     */
    public Projectile(Vector2 exactPosition, Pactale owner, float movementSpeed, Direction direction, Color color) {
        super(exactPosition, movementSpeed, direction, color);
        this.owner = owner;
        this.drawOnDirection = true;

        this.setTexture(new TextureRegion(Assets.getTexture("projectile.png")));
        this.setParticleEffectName("projectile");
    }

    /**
     * Gets the {@link Pactale} this {@link Projectile} origined from.
     *
     * @return {@link Pactale} that this {@link Projectile} origined from.
     */
    public Pactale getOwner() {
        return this.owner;
    }

    @Override
    public boolean collisionWithGameObject(GameObject object) {
        if (object.equals(owner) || object instanceof Projectile) return false;

        GameObject obj = object;
        if (this.getOwner().getPortal() != null && this.getActive()) {
            obj.isMoving = false;
            obj.setNodePosition(this.getOwner().getPortal().getNode());
            this.setInactive();
            return true;
        }

        return super.collisionWithGameObject(object);
    }

    @Override
    public boolean collisionWithWall(Node node) {
        Node NextNode = getMap().getAdjacentNode(node, this.direction);
        if (NextNode.isWall() && this.getActive()) {
            Portal portal = new Portal(owner, node, this.direction.reverse());
            Assets.playSound("portal_hit.mp3");
            this.setInactive();
            return true;
        }

        return super.collisionWithWall(node);
    }
}
