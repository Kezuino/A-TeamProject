package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.utility.Assets;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
        this.lives = lives;
    }

    /**
     * Will shoot a portal in the direction that the {@link Pactale} currently
     * is heading.
     */
    public void shootPortal() {
        // create projectile
        Projectile prjtl = new Projectile(this.getMap(), this.getX(), getY(), this, this.getMovementSpeed(), this.getDirection(), Color.CLEAR);
        //int i = 0;

        // check if next node has collision
        while (!prjtl.hasCollision()) {
            prjtl.moveAdjacent(direction); // Test projecttile direction to right
           // System.out.println("Steps to wall: " + i++);
        }

        // if collision has detected, check if it is a wall or an object
        // 1 = wall 
        // 2 = Object (pactale/Enemy)
        int WallOrObject = GetCollisionObject(prjtl.getX(), prjtl.getY());
        System.out.println("Shot on : "+WallOrObject);
        if (WallOrObject == 1) { // wall
            Portal portal = new Portal(this, prjtl.getNode(), direction);
            this.getMap().getNode(prjtl.getX(), prjtl.getY()).setPortal(direction, portal);
            System.out.println("Portal set on : x" + portal.getNode().getX() + "-y" + portal.getNode().getY());
        } else if (WallOrObject == 2) {// object
            
            // TO Move object to existing portal of this.pactale
            // if no portal, exit/break code
            System.out.println("Object is Shot on : x" + prjtl.getX() + "-y" + prjtl.getY());

        } else { }

    }

    int GetCollisionObject(int x, int y) {
        Node NextNode = null;
        int ReturnVal = 0;
        switch (direction) {
            case Up:
                NextNode = this.getMap().getNode(x, y--);
                if (NextNode.isWall()) {
                    // Next Node is a wall, colision detected, return true
                    ReturnVal = 1;
                } else if (!NextNode.getGameObjects().isEmpty()) {
                    ReturnVal = 2;
                }
            case Right:
                NextNode = this.getMap().getNode(x++, y);
                if (NextNode.isWall()) {
                    // Next Node is a wall, colision detected, return true
                    ReturnVal = 1;
                } else if (!NextNode.getGameObjects().isEmpty()) {
                    ReturnVal = 2;
                }
            case Down:
                NextNode = this.getMap().getNode(x, y++);
                if (NextNode.isWall()) {
                    // Next Node is a wall, colision detected, return true
                    ReturnVal = 1;
                } else if (!NextNode.getGameObjects().isEmpty()) {
                    ReturnVal = 2;
                }
            case Left:
                NextNode = this.getMap().getNode(x--, y);
                if (NextNode.isWall()) {
                    // Next Node is a wall, colision detected, return true
                    ReturnVal = 1;
                } else if (!NextNode.getGameObjects().isEmpty()) {
                    ReturnVal = 2;
                }
        }
        return ReturnVal;
    }

    /**
     * Will remove all listed portals from this {@link Pactale}.
     */
    public void removePortal() {
        portal.getNode().removePortal(portal.getDirection());
        portal = null;
    }
}
