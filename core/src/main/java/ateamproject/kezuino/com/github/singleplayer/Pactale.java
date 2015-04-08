package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Collection;

public class Pactale extends GameObject {

    private static int playerIndexCounter = 0;
    private int playerIndex;
    private int lives;
    private Portal portal;
    /**
     * Initialize a {@link Pactale}.
     *
     * @param x                position of this
     *                         {@link ateamproject.kezuino.com.github.singleplayer.Pactale} on the @see
     *                         Map.
     * @param y                position of this
     *                         {@link ateamproject.kezuino.com.github.singleplayer.Pactale} on the @see
     *                         Map.
     * @param lives            Times that the
     *                         {@link ateamproject.kezuino.com.github.singleplayer.Pactale} can be hit.
     *                         Defaults to 1 for a multiplayer session.
     * @param movementSpeed    Amount of seconds that it will take to move to
     *                         another node.
     * @param walkingDirection Looking direction to start with.
     * @param color            Distinct color of this
     *                         {@link ateamproject.kezuino.com.github.singleplayer.Pactale} in the game.
     */
    public Pactale(Map map, int x, int y, int lives, float movementSpeed, Direction walkingDirection, Color color) {
        super(map, x, y, movementSpeed, walkingDirection, color);
        this.lives = lives;
        this.playerIndex = playerIndexCounter++;
        this.drawOnDirection=false;
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
        //Checks if the lives that are getting set is lower than the current lives, if so pactale got defeated.
        if (lives > this.lives) {
            Sound sound = Assets.get("sounds/defeat.wav", Sound.class);
            if (sound != null) {
                sound.play();
            }
        }
        this.lives = lives;
    }

    /**
     * Will shoot a portal in the direction that the {@link Pactale} currently
     * is heading.
     */
    public void shootProjectile() {
        // create projectile
        Projectile prjtl = new Projectile(this.getMap(), this.getX(), getY(), this, this.getMovementSpeed() / 3, this.getDirection(), this.getColor());
        prjtl.getMap().addGameObject(this.getX(), this.getY(), prjtl);

        // check if next node has collision
        prjtl.moveAdjacent(direction);
    }


    int getCollisionObject(int x, int y) {
        Node NextNode;
        int ReturnVal = 0;
        NextNode = this.getMap().getNode(x + direction.getX(), y + direction.getY());
        if (NextNode.isWall()) {
            // Next Node is a wall, colision detected, return true
            ReturnVal = 1;
        } else if (!this.getMap().getAllGameObjects().isEmpty()) {
            ReturnVal = 2;
        }
        return ReturnVal;
    }
    
    @Override
    protected boolean collisionWithGameObject(GameObject object) {
        if(object instanceof Enemy) {
            Enemy e = (Enemy) object;
            
            if(e.isEdible()) {
                this.getMap().getSession().getScore().incrementScore(500);
                e.setActive(false);
            } else {
                this.lives -= 1;
                this.setActive(false);
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
        // remove current portal
        removePortal();

        // add portal to new node
        this.setPortal(portal);
        this.getMap().getNode(portal.getNode().getX(), portal.getNode().getY()).setPortal(portal.getDirection(), portal);
    }

    /**
     * Will remove all listed portals from this {@link Pactale}.
     */
    public void removePortal() {
         if (this.portal != null) {
            this.getMap().getNode(portal.getNode().getX(), portal.getNode().getY()).removePortal(portal.getDirection());
            this.portal = null;
        }
    }
}
