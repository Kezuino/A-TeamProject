package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import ateamproject.kezuino.com.github.singleplayer.GameObject;
import ateamproject.kezuino.com.github.utility.game.balloons.BalloonMessage;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

/**
 * Kicks all the receivers from the server.
 */
public class PacketBalloonMessage extends Packet {
    /**
     * Name of the {@link BalloonMessage} to show.
     */
    @PacketField(0)
    protected String typeName;
    /**
     * Position to show this {@link BalloonMessage} at. Can be null if {@link #followTarget} is null.
     */
    @PacketField(1)
    protected Vector2 position;
    /**
     * Specifies which {@link GameObject} to follow.
     */
    @PacketField(2)
    protected UUID followTarget;

    public PacketBalloonMessage() {
    }

    public PacketBalloonMessage(String typeName, Vector2 position, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.typeName = typeName;
        this.position = position;
    }

    public PacketBalloonMessage(String typeName, UUID followTarget, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.typeName = typeName;
        this.followTarget = followTarget;
    }

    public String getTypeName() {
        return typeName;
    }

    public Vector2 getPosition() {
        return position;
    }

    public UUID getFollowTarget() {
        return followTarget;
    }
}
