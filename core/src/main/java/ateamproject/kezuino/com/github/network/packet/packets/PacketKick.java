package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.IClientInfo;
import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.io.Serializable;
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

    /**
     * Creates a new {@link Packet} for kicking of clients.
     *
     * @param reasonType ReasonType to give why this {@link Packet} was send.
     * @param reason Reason message to send to clients that should be kicked (can be null for default).
     * @param sender {@link UUID} that sent this {@link Packet}.
     * @param receivers {@link UUID clients} that should be kicked.
     */
    public PacketKick(KickReasonType reasonType, String reason, UUID sender, UUID... receivers) {
        super(sender, receivers);
        if (reasonType == null) throw new IllegalArgumentException("Parameter reasonType must not be null.");
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
        return reason;
    }

    /**
     * Gets the detailed reason why the {@link IClientInfo} was kicked.
     *
     * @return Detailed reason why the {@link IClientInfo} was kicked.
     */
    public String getReasonDetailed() {
        reason = reason.trim();
        String message = "";

        // Add reasonType message.
        switch (reasonType) {
            case GAME:
                message += "Kicked from game";
                break;
            case QUIT:
                message += "Kicked from server";
                break;
        }

        // Add personal message.
        if (!reason.isEmpty()) {
            message += ": " + reason;
        }

        return message + '.';
    }

    @Override
    public String toString() {
        return "PacketKick{" +
                "reason='" + reason + '\'' +
                ", reasonType=" + reasonType +
                "} " + super.toString();
    }

    public enum KickReasonType implements Serializable {
        /**
         * When playing or in a lobby.
         */
        GAME,
        /**
         * When the client closes the program or logs out.
         */
        QUIT
    }
}
