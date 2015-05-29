package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.IClientInfo;
import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

/**
 * Kicks all the receivers from the server.
 */
public class PacketKick extends Packet<Boolean> {
    /**
     * Gets or sets the reason why the {@link IClientInfo} was kicked.
     */
    @PacketField(0)
    protected String reason;
    /**
     * Gets or sets the {@link ateamproject.kezuino.com.github.network.packet.packets.PacketKick.KickReasonType} to indicate where the kick occurred.
     */
    @PacketField(1)
    protected KickReasonType reasonType;

    public PacketKick() {
    }

    public PacketKick(KickReasonType reasonType, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        if (reasonType == null) throw new IllegalArgumentException("Parameter reasonType must not be null.");
        this.reasonType = reasonType;
    }

    public PacketKick(KickReasonType reasonType, String reason, UUID... senderAndReceivers) {
        this(reasonType, senderAndReceivers);
        this.reasonType = reasonType;
        this.reason = reason;
    }

    /**
     * Gets {@link ateamproject.kezuino.com.github.network.packet.packets.PacketKick.KickReasonType} to indicate where the kick occurred.
     *
     * @return {@link ateamproject.kezuino.com.github.network.packet.packets.PacketKick.KickReasonType} to indicate where the kick occurred.
     */
    public KickReasonType getReasonType() {
        return reasonType;
    }

    /**
     * Gets the reason why the {@link IClientInfo} was kicked.
     *
     * @return Reason why the {@link IClientInfo} was kicked.
     */
    public String getReason() {
        reason = reason.trim();
        String message = "";

        // Add reasonType message.
        switch (reasonType) {
            case GAME:
                message += "Kicked from game";
                break;
            case LOBBY:
                message += "Kicked from lobby";
                break;
            case QUIT:
                message += "Kicked from server";
                break;
        }

        // Add personal message.
        if (reason != null && !reason.isEmpty()) {
            message += ": " + reason;
        }

        return message + '.';
    }

    public enum KickReasonType {
        GAME,
        LOBBY,
        QUIT
    }
}
