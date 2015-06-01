package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

public class PacketRequestCompleted extends Packet {
    @PacketField(0)
    protected String id;
    @PacketField(1)
    protected int progress;

    public PacketRequestCompleted() {
    }

    public PacketRequestCompleted(String id, int progress, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.id = id;
        this.progress = progress;
    }

    public String getId() {
        return id;
    }

    public int getProgress() {
        return progress;
    }
}
