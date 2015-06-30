package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;

import java.util.UUID;

public class PacketPlayerDied extends Packet {
    public PacketPlayerDied() {
    }

    public PacketPlayerDied(UUID sender, UUID... receivers) {
        super(sender, receivers);
    }
}