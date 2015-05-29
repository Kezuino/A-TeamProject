package ateamproject.kezuino.com.github.network;

import java.util.UUID;

/**
 * Interface for providing features needed for the {@link INetworkComponent} to function with its {@link IClientInfo clients}.
 */
public interface IClientInfo {

    /**
     * Gets the private and server-client only known id of this {@link IClientInfo}.
     * Clients will <b>NOT</b> know the private ids of all connected clients.
     *
     * @return Private and server to referenced client only known id of this {@link IClientInfo}.
     */
    UUID getPrivateId();

    /**
     * Sets the private {@link UUID} used by this {@link Client} to identify itself with the {@link Server}.
     *
     * @param id Private {@link UUID} used by this {@link Client} to identify itself with the {@link Server}.
     */
    void setPrivateId(UUID id);

    /**
     * Gets the public and well-known id of this {@link IClientInfo}.
     * Clients will know the public ids of all connected clients. A client will use this id to reference someone but <b>NOT</b> himself. {@see #getPrivateId}.
     *
     * @return Public and well-known id of this {@link IClientInfo}.
     */
    UUID getPublicId();

    /**
     * the public {@link UUID} used to send to other {@link Client clients} than this {@link Client} so that they can reference this {@link Client}, but not identify themself as him.
     *
     * @return Public {@link UUID} used to send to other {@link Client clients} than this {@link Client} so that they can reference this {@link Client}, but not identify themself as him.
     */
    void setPublicId(UUID publicId);

    /**
     * Gets the total time in seconds that have passed since the last activity from the {@link IClientInfo}.
     *
     * @return Total time in seconds that have passed since the last activity from the {@link IClientInfo}.
     */
    float getSecondsInactive();

    /**
     * Resets the seconds since last activity. Call this when activity from the {@link Client} was received.
     */
    void resetSecondsActive();
}
