package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;

import java.util.UUID;

public class PacketShootProjectile extends Packet<Boolean> {
    public PacketShootProjectile() {
    }

    public PacketShootProjectile(UUID... senderAndReceivers) {
        super(senderAndReceivers);
    }
}
