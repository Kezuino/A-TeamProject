package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;

import java.util.UUID;

public class PacketLaunchGame extends Packet {
    public PacketLaunchGame() {
    }

    public PacketLaunchGame(UUID... senderAndReceivers) {
        super(senderAndReceivers);
    }
}
