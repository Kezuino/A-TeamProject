package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

/**
 * Notifies other clients that someone has left.
 */
public class PacketClientLeft extends Packet {
    @PacketField(0)
    protected UUID clientThatLeft;
    @PacketField(1)
    protected String username;

    public PacketClientLeft() {
    }

    public PacketClientLeft(UUID... senderAndReceivers) {
        super(senderAndReceivers);
    }

    public PacketClientLeft(UUID clientThatLeft, String username, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.clientThatLeft = clientThatLeft;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getClientThatLeft() {
        return clientThatLeft;
    }

    public void setClientThatLeft(UUID clientThatLeft) {
        this.clientThatLeft = clientThatLeft;
    }
}
