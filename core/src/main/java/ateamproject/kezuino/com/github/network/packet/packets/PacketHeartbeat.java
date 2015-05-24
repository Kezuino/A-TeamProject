package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.Client;
import ateamproject.kezuino.com.github.network.Server;
import ateamproject.kezuino.com.github.network.packet.Packet;

import java.util.UUID;

/**
 * {@link Packet} that will be send on interval to check if the {@link Server} or {@link Client} is still responding.
 */
public class PacketHeartbeat extends Packet {
    public PacketHeartbeat() {
    }

    public PacketHeartbeat(UUID... senderAndReceivers) {
        super(senderAndReceivers);
    }
}

