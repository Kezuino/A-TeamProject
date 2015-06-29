package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

public class PacketObjectCollision extends Packet {
    @PacketField(0)
    protected UUID collider;
    @PacketField(1)
    protected UUID target;

    public PacketObjectCollision() {
    }
    public PacketObjectCollision(UUID collider, UUID target, UUID sender, UUID... receivers) {
        super(sender, receivers);
        if (collider == null) throw new IllegalArgumentException("Parameter collider must not be null.");
        if (target == null) throw new IllegalArgumentException("Parameter target must not be null.");

        this.collider = collider;
        this.target = target;
    }

    public UUID getCollider() {
        return collider;
    }

    public UUID getTarget() {
        return target;
    }
}
