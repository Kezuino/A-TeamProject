package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.utility.Assets;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import java.util.*;

public class Pactale extends GameObject {

    private int playerIndex;
    private int lives;
    private Portal portal;
    private Collection<Projectile> projectiles;

    /**
     * Initialize a {@link Pactale}.
     *
     * @param x position of this
     * {@link ateamproject.kezuino.com.github.singleplayer.Pactale} on the @see
     * Map.
     * @param y position of this
     * {@link ateamproject.kezuino.com.github.singleplayer.Pactale} on the @see
     * Map.
     * @param lives Times that the
     * {@link ateamproject.kezuino.com.github.singleplayer.Pactale} can be hit.
     * Defaults to 1 for a multiplayer session.
     * @param movementSpeed Amount of seconds that it will take to move to
     * another node.
     * @param walkingDirection Looking direction to start with.
     * @param color Distinct color of this
     * {@link ateamproject.kezuino.com.github.singleplayer.Pactale} in the game.
     */
    public Pactale(Map map, int x, int y, int lives, float movementSpeed, Direction walkingDirection, Color color) {
        super(map, x, y, movementSpeed, walkingDirection, color);
        this.lives = lives;
        this.projectiles = new ArrayList<>();
    }

    public int getPlayerIndex() {
        return this.playerIndex;
    }

    public Portal getPortal() {
        return this.portal;
    }

    public int getLives() {
        return this.lives;
    }

    public void setLives(int lives) {
        //Checks if the lives that are getting set is lower than the current lives, if so pactale got defeated.
        if (lives > this.lives) {
            Sound sound = Assets.get("sounds/Defeat.wav", Sound.class);
            if (sound != null) {
                sound.play();
                sound.dispose();
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
        } else if (!NextNode.getGameObjects().isEmpty()) {
            ReturnVal = 2;
        }
        return ReturnVal;
    }

    public boolean addPortal(Portal portal)
    {
        if (portal != null) {
            this.portal = portal;
            return true;
        }
        else 
        {
            return false;
        }
    }
    
    /**
     * Will remove all listed portals from this {@link Pactale}.
     */
    public void removePortal() {
        portal.getNode().removePortal(portal.getDirection());
        portal = null;
    }
}
