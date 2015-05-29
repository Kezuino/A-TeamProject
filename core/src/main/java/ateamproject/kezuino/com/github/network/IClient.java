package ateamproject.kezuino.com.github.network;

import java.util.UUID;

/**
 * Interface for providing features needed for the {@link IServer} to function with its {@link IClient clients}.
 */
public interface IClient {


    /**
     * Gets the private and server-client only known id of this {@link IClient}.
     * Clients will <b>NOT</b> know the private ids of all connected clients.
     *
     * @return Private and server to referenced client only known id of this {@link IClient}.
     */
    UUID getPrivateId();

    /**
     * Gets the public and well-known id of this {@link IClient}.
     * Clients will know the public ids of all connected clients. A client will use this id to reference someone but <b>NOT</b> himself. {@see #getPrivateId}.
     *
     * @return Public and well-known id of this {@link IClient}.
     */
    UUID getPublicId();
}
