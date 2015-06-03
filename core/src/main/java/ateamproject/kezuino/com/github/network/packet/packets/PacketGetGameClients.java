package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class PacketGetGameClients extends Packet<List<PacketGetGameClients.Data>> {
    public PacketGetGameClients() {
    }

    public PacketGetGameClients(UUID... senderAndReceivers) {
        super(senderAndReceivers);
    }

    public static class Data implements Serializable {
        protected int index;
        protected UUID publicId;
        protected boolean isHost;

        public Data(int index, UUID publicId, boolean isHost) {
            this.index = index;
            this.publicId = publicId;
            this.isHost = isHost;
        }

        public int getIndex() {
            return index;
        }

        public UUID getPublicId() {
            return publicId;
        }

        public boolean isHost() {
            return isHost;
        }
    }
}
