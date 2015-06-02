package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;

import java.util.UUID;

public class PacketLobbiesChanged extends Packet {
    public PacketLobbiesChanged() {
    }

    public PacketLobbiesChanged(UUID... senderAndReceivers) {
        super(senderAndReceivers);
    }
}