package ateamproject.kezuino.com.github.network.packet;

import ateamproject.kezuino.com.github.network.IClientInfo;

public interface IPacketSender {
    /**
     * Sends a {@link Packet} to the {@link IClientInfo}. Used method for sending (TCP, UDP, etc..) is unknown.
     * Execution is done on the client-side.
     *
     * @param packet {@link Packet} to send to the {@link IClientInfo}.
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
}
