package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

public class PacketClientJoined extends Packet {
    @PacketField(0)
    protected UUID id;
    @PacketField(1)
    protected String username;

    public PacketClientJoined() {
    }

    public PacketClientJoined(UUID... senderAndReceivers) {
        super(senderAndReceivers);
    }

    public PacketClientJoined(UUID id, String username, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.id = id;
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
