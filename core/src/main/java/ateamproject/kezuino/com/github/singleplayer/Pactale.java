package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.utility.assets.Assets;
import ateamproject.kezuino.com.github.utility.game.Animation;
import ateamproject.kezuino.com.github.utility.game.Direction;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Pactale extends GameObject {
    private int playerIndex;
    private int lives;
    private Portal portal;

    /**
     * Initialize a {@link Pactale}.
     *
     * @param lives            Times that the
     *                         {@link ateamproject.kezuino.com.github.singleplayer.Pactale} can be hit.
     *                         Defaults to 1 for a multiplayer session.
     * @param movementSpeed    Amount of seconds that it will take to move to
     *                         another node.
     * @param walkingDirection Looking direction to start with.
     * @param color            Distinct color of this
     *                         {@link ateamproject.kezuino.com.github.singleplayer.Pactale} in the game.
     */
    public Pactale(Vector2 exactPosition, int lives, float movementSpeed, Direction walkingDirection, Color color) {
        super(exactPosition, movementSpeed, walkingDirection, color);
        this.lives = lives;
        this.playerIndex = -1;
        this.drawOnDirection = false;
        
        Animation pacAnimation = new Animation(true);
        Texture atlas = Assets.get("textures/pactale.png", Texture.class);
        
        TextureRegion[][] reg = TextureRegion.split(atlas, 32, 32);
        
        pacAnimation.addFrame(Direction.Down, new Array(reg[0]));
        pacAnimation.addFrame(Direction.Right, new Array(reg[1]));
        pacAnimation.addFrame(Direction.Up, new Array(reg[2]));
        pacAnimation.addFrame(Direction.Left, new Array(reg[3]));

        this.setAnimation(pacAnimation);
    }

    public Pactale(int playerIndex, Vector2 exactPosition, int lives, float movementSpeed, Direction walkingDirection, Color color) {
        this(exactPosition, lives, movementSpeed, walkingDirection, color);
        this.playerIndex = playerIndex;
    }

    public int getPlayerIndex() {
        return this.playerIndex;
    }

    public Portal getPortal() {
        return this.portal;
    }

    void setPortal(Portal portal) {
        this.portal = portal;
    }

    public int getLives() {
        return this.lives;
    }

    public void setLives(int lives) {
        if (lives < 0) return;
        this.lives = lives;
    }

    /**
     * Hurts the {@link Pactale} so that it loses one life and resets it's position to spawn.
     */
    public void hurt() {
        if (this.lives < 0) return;

        this.isMoving = false;
        this.lives--;
        if (this.lives == 0) {
            Assets.playSound("defeat.wav");
            this.setInactive();
        }
    }

    /**
     * If this {@link Pactale} is active, will shoot a portal in the direction
     * that this {@link Pactale} currently is heading.
     */
    public void shootProjectile() {
        if (this.getActive()) {
            // create projectile
            Projectile proj = new Projectile(this.getExactPosition(), this, this.getMovementSpeed() * 3, this.getDirection(), this
                    .getColor());
            getMap().addGameObject(proj);
            Assets.playSound("portal_shot.mp3");

            // check if next node has collision
            proj.moveAdjacent(direction);
        }
    }

    @Override
    protected boolean collisionWithGameObject(GameObject object) {
        if (object instanceof Enemy) {
            Enemy e = (Enemy) object;

            if (!e.isEdible()) {
                this.hurt();
                this.setNodePosition(this.getStartingPosition().x / 32, this.getStartingPosition().y / 32);
                this.getMap().getSession().getScore().decrease(100);
                Assets.playSound("enemy_eat.mp3");
            }
            return true;
        }
        return false;
    }

    @Override
    protected boolean collisionWithItem(Item item) {
        item.activate(this);
        item.getNode().removeItem();
        return true;
    }

    public void addPortal(Portal portal) {
        // Remove current portal.
        removePortal();

        // Add portal to new node.
        this.setPortal(portal);
        this.getMap()
            .getNode(portal.getNode().getX(), portal.getNode().getY())
            .setPortal(portal.getDirection(), portal);
    }

    /**
     * If a portal has been set to this {@link Pactale},
     * will remove the current set {@link Pactale}.
     */
    public void removePortal() {
        if (this.portal == null) return;
        this.getMap().getNode(portal.getNode().getX(), portal.getNode().getY()).removePortal(portal.getDirection());
        this.portal = null;
    }
}
