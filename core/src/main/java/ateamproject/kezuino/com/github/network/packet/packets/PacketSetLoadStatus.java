package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

public class PacketSetLoadStatus extends Packet {
    @PacketField(0)
    protected LoadStatus status;
    @PacketField(1)
    protected int progress;
    @PacketField(2)
    protected int maxProgress;

    public PacketSetLoadStatus() {
    }

    public PacketSetLoadStatus(LoadStatus status, UUID sender, UUID... receivers) {
        super(sender, receivers);
        this.status = status;
    }

    public PacketSetLoadStatus(LoadStatus status, int progress, int maxProgress, UUID sender, UUID... receivers) {
        this(status, sender, receivers);
        this.progress = progress;
        this.maxProgress = maxProgress;
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public LoadStatus getStatus() {
        return status;
    }

    public void setStatus(LoadStatus status) {
        this.status = status;
    }

    public enum LoadStatus {
        /**
         * Nothing is currently loaded.
         */
        Empty,

        /**
         * Indicates that the map was loaded without objects.
         */
        MapLoaded,

        /**
         * Indicates that the map with its objects are fully loaded and ready to be started.
         */
        ObjectsLoaded
    }
}
