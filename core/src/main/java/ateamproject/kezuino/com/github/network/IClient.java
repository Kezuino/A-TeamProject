package ateamproject.kezuino.com.github.network;

import ateamproject.kezuino.com.github.network.packet.IPacketSender;

import java.util.UUID;

/**
 * Interface for communicating with a client from a server. Method of communication should not be defined in this interface.
 */
public interface IClient extends IPacketSender {

    /**
     * Gets the public and well-known id of this {@link IClient}.
     * Clients will know the public ids of all connected clients. A client will use this id to reference someone or himself.
     *
     * @return Public and well-known id of this {@link IClient}.
     */
    UUID getId();
}
