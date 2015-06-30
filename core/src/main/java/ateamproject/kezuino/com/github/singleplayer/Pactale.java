package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.network.packet.packets.PacketObjectCollision;
import ateamproject.kezuino.com.github.network.packet.packets.PacketRemoveItem;
import ateamproject.kezuino.com.github.network.packet.packets.PacketScoreChanged;
import ateamproject.kezuino.com.github.network.rmi.Client;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import ateamproject.kezuino.com.github.utility.game.Direction;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Pactale extends GameObject {

    private int playerIndex;
    private int lives;
    private Portal portal;

    /**
     * Empty constructor needed for reflection instantiation.
     */
    public Pactale() {
        isActive = true;
    }

    /**
     * Initialize a {@link Pactale}.
     *
     * @param lives Times that the
     * {@link ateamproject.kezuino.com.github.singleplayer.Pactale} can be hit.
     * Defaults to 1 for a multiplayer session.
     * @param movementSpeed Amount of seconds that it will take to move to
     * another node.
     * @param walkingDirection Looking direction to start with.
     * @param color Distinct color of this
     * {@link ateamproject.kezuino.com.github.singleplayer.Pactale} in the game.
     */
    public Pactale(Vector2 exactPosition, int lives, float movementSpeed, Direction walkingDirection, Color color) {
        super(exactPosition, movementSpeed, walkingDirection, color);
        this.lives = lives;
        this.playerIndex = -1;
        this.drawOnDirection = false;
    }

    public Pactale(int playerIndex, Vector2 exactPosition, int lives, float movementSpeed, Direction walkingDirection, Color color) {
        this(exactPosition, lives, movementSpeed, walkingDirection, color);
        this.playerIndex = playerIndex;
    }

    public int getPlayerIndex() {
        return this.playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
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
        if (lives < 0) {
            return;
        }
        this.lives = lives;
    }

    /**
     * Hurts the {@link Pactale} so that it loses one life and resets it's
     * position to spawn.
     */
    public void hurt() {
        if (this.lives < 0) {
            return;
        }

        this.isMoving = false;
        this.lives--;
        if (this.lives <= 0) {
            Assets.playSound("defeat.wav");
            this.setInactive();
        }
    }

    /**
     * If this {@link Pactale} is active, will shoot a portal in the direction
     * that this {@link Pactale} currently is heading.
     */
    public Projectile shootProjectile(Vector2 position, Direction direction) {
        if (this.getActive()) {
            // create projectile
            Projectile proj = new Projectile(position, this, this.getMovementSpeed() * 3, direction, this
                    .getColor());
            proj.setId();
            getMap().addGameObject(proj);
            Assets.playSound("portal_shot.mp3");

            // check if next node has collision
            proj.moveAdjacent(direction);
            return proj;
        }
        return null;
    }

    @Override
    public boolean collisionWithGameObject(GameObject object) {
        if (this.getId().equals(Client.getInstance().getPublicId()) && super.getActive()) {
            PacketObjectCollision packet = new PacketObjectCollision(getId(), object.getId(), null);
            Client.getInstance().send(packet);

            if (object instanceof Enemy) {
                Enemy e = (Enemy) object;

                if (!e.isEdible()) {
                    this.hurt();
                    this.setNodePosition(this.getStartingPosition().x / 32, this.getStartingPosition().y / 32);

                    PacketScoreChanged pScore = new PacketScoreChanged(100, PacketScoreChanged.ManipulationType.DECREASE, Client.getInstance().getId());
                    Client.getInstance().send(pScore);

                    Assets.playSound("enemy_eat.mp3");
                }
                return true;
            }
            //If the pactale is not the player, it is another player and shouldnt update score and send another packet.
        } else if (!this.getId().equals(Client.getInstance().getPublicId()) && super.getActive()) {
             if (object instanceof Enemy) {
                Enemy e = (Enemy) object;

                if (!e.isEdible()) {
                    this.hurt();
                    this.setNodePosition(this.getStartingPosition().x / 32, this.getStartingPosition().y / 32);

                    Assets.playSound("enemy_eat.mp3");
                }
                return true;
            }
        } else {
            return false;
        }
        return false;
    }

    @Override
    public boolean collisionWithItem(Item item) {
        if (this.getId().equals(Client.getInstance().getPublicId())) {
            Client.getInstance().send(new PacketRemoveItem(item.getId(), item.getItemType(), Client.getInstance().getId()));
            item.activate(this);
            item.getNode().removeItem();
        } else if (!Client.getInstance().isRunning()) {
            // Singleplayer.
            item.activate(this);
            item.getNode().removeItem();
        }
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
     * If a portal has been set to this {@link Pactale}, will remove the current
     * set {@link Pactale}.
     */
    public void removePortal() {
        if (this.portal == null) {
            return;
        }
        this.getMap().getNode(portal.getNode().getX(), portal.getNode().getY()).removePortal(portal.getDirection());
        this.portal = null;
    }
}
