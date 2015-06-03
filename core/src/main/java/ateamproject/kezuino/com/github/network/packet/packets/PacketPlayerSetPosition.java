package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

public class PacketPlayerSetPosition extends Packet {
    @PacketField(0)
    protected Vector2 position;

    public PacketPlayerSetPosition() {
    }

    public PacketPlayerSetPosition(Vector2 position, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }
}
