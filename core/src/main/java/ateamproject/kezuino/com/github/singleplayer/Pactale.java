package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Pactale extends GameObject {

    private static int playerIndexCounter = 0;
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
        this.playerIndex = playerIndexCounter++;
        this.drawOnDirection = false;
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
        this.lives = lives;
        if (this.lives == 0) {
            Assets.playSound("defeat.wav");
            this.setInactive();
        }
    }

    /**
     * Will shoot a portal in the direction that the {@link Pactale} currently
     * is heading.
     */
    public void shootProjectile() {
        // create projectile
        Projectile proj = new Projectile(this.getExactPosition(), this, this.getMovementSpeed() / 3, this.getDirection(), this
                .getColor());
        getMap().addGameObject(proj);

        // check if next node has collision
        proj.moveAdjacent(direction);
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
        if (object instanceof Enemy) {
            Enemy e = (Enemy) object;

            if (!e.isEdible()) {
                this.setLives(this.getLives() - 1);
                this.setNodePosition(this.getStartingPosition().x / 32, this.getStartingPosition().y / 32);
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
        this.getMap()
            .getNode(portal.getNode().getX(), portal.getNode().getY())
            .setPortal(portal.getDirection(), portal);
    }

    /**
     * Will remove all listed portals from this {@link Pactale}.
     */
    public void removePortal() {
        if (this.portal == null) return;
        this.getMap().getNode(portal.getNode().getX(), portal.getNode().getY()).removePortal(portal.getDirection());
        this.portal = null;
    }
}
