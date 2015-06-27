package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import ateamproject.kezuino.com.github.utility.game.Direction;

import java.util.UUID;

public class PacketPlayerSetDirection extends Packet {
    @PacketField(0)
    protected Direction direction;

    public PacketPlayerSetDirection() {
    }

    public PacketPlayerSetDirection(Direction direction, UUID sender, UUID... receivers) {
        super(sender, receivers);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
