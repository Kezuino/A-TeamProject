package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

public class PacketUpdateProgress extends Packet {
    @PacketField(0)
    protected String id;
    @PacketField(1)
    protected int progress;

    public PacketUpdateProgress() {
    }

    public PacketUpdateProgress(String id, int progress, UUID sender, UUID... receivers) {
        super(sender, receivers);
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
