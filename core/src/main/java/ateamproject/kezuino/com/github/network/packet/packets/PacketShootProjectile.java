package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import ateamproject.kezuino.com.github.utility.game.Direction;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

public class PacketShootProjectile extends Packet<Boolean> {
    @PacketField(0)
    protected Vector2 position;
    @PacketField(1)
    protected  Direction direction;
    @PacketField(2)
    protected float speed;

    public PacketShootProjectile() {
    }

    public PacketShootProjectile(Vector2 position, Direction direction, float speed, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.position = position;
        this.direction = direction;
        this.speed = speed;
        this.sender = sender;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }
    
    public float getSpeed() {
        return speed;
    }
}
