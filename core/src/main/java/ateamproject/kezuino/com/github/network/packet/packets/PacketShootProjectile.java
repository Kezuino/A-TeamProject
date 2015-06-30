package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import ateamproject.kezuino.com.github.utility.game.Direction;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

public class PacketShootProjectile extends Packet<Boolean> {

    @PacketField(0)
    protected Vector2 exactPosition;
    @PacketField(1)
    protected Direction direction;

    public PacketShootProjectile(Vector2 position, Direction dir) {
        this.exactPosition = position;
        this.direction = dir;
    }

    public PacketShootProjectile(Vector2 position, Direction dir, UUID sender, UUID... receivers) {
        super(sender, receivers);
        this.exactPosition = position;
        this.direction = dir;
    }

    public Vector2 getExactPosition() {
        return exactPosition;
    }

    public Direction getDirection() {
        return direction;
    }
}
