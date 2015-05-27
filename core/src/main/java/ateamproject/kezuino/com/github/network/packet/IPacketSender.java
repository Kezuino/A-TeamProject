package ateamproject.kezuino.com.github.network.packet;

import ateamproject.kezuino.com.github.network.IClient;

public interface IPacketSender {
    /**
     * Sends a {@link Packet} to the {@link IClient}. Used method for sending (TCP, UDP, etc..) is unknown.
     * Execution is done on the client-side.
     *
     * @param packet {@link Packet} to send to the {@link IClient}.
     */
    void send(Packet packet);

    /**
     * Registers all {@link Packet packets} that the domain can handle in this method.
     */
    void registerPackets();

    /**
     * Unegisters all {@link Packet packets} that this domain had registered.
     */
    void unregisterPackets();

    /**
     * Gets the total seconds that went past since the last {@link Packet}.
     *
     * @return Total seconds that went past since the last {@link Packet}.
     */
    double getSecondsFromLastPacket();
}