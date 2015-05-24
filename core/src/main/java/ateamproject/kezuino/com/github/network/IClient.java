package ateamproject.kezuino.com.github.network;

import ateamproject.kezuino.com.github.network.packet.Packet;

import java.util.UUID;

/**
 * Interface for providing features for server-side as well as client-side. Don't put communication methods here that can only go one way.
 */
public interface IClient {

    /**
     * Gets the public and well-known id of this {@link IClient}.
     * Clients will know the public ids of all connected clients. A client will use this id to reference someone or himself.
     *
     * @return Public and well-known id of this {@link IClient}.
     */
    UUID getId();
}
