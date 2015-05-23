package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.IClient;
import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

/**
 * Kicks all the receivers from the server.
 */
public class PacketKick extends Packet {
    /**
     * Gets or sets the reason why the {@link IClient} was kicked.
     */
    @PacketField(0)
    protected String reason;

    public PacketKick(UUID... senderAndReceivers) {
        super(senderAndReceivers);
    }

    public PacketKick(String reason, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.reason = reason;
    }

    /**
     * Gets the reason why the {@link IClient} was kicked.
     *
     * @return Reason why the {@link IClient} was kicked.
     */
    public String getReason() {
        return reason;
    }
}
