package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

public class PacketClientJoined extends Packet {
    @PacketField(0)
    protected UUID joinenId;
    @PacketField(1)
    protected String username;

    public PacketClientJoined() {
    }

    public PacketClientJoined(UUID... senderAndReceivers) {
        super(senderAndReceivers);
    }

    public PacketClientJoined(UUID joinenId, String username, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.joinenId = joinenId;
        this.username = username;
    }

    public UUID getJoinenId() {
        return joinenId;
    }

    public String getUsername() {
        return username;
    }
}
