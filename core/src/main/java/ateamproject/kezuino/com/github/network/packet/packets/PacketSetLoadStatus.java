package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

public class PacketSetLoadStatus extends Packet {
    @PacketField(0)
    protected LoadStatus status;

    public PacketSetLoadStatus() {
    }

    public PacketSetLoadStatus(LoadStatus status, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.status = status;
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
