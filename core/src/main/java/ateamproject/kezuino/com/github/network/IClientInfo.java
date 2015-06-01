package ateamproject.kezuino.com.github.network;

import ateamproject.kezuino.com.github.network.packet.packets.PacketSetLoadStatus;
import java.util.ArrayList;

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

    /**
     * Gets the {@link Game} this {@link IClientInfo} is currently in. Or returns null if this {@link IClientInfo} isn't in a {@link Game}.
     *
     * @return {@link Game} this {@link IClientInfo} is currently in. Or returns null if this {@link IClientInfo} isn't in a {@link Game}.
     */
    Game getGame();

    /**
     * Sets the {@link Game} this {@link IClientInfo} is currently in. Can be set to null.
     *
     * @param game {@link Game} that the {@link IClientInfo} is currently in.
     */
    void setGame(Game game);

    /**
     * Gets the emailaddress from the {@link IClientInfo}.
     *
     * @return Emailaddress from the {@link IClientInfo}.
     */
    String getEmailAddress();


    /**
     * Sets the emailaddress from the {@link IClientInfo}.
     *
     * @param emailAddress Emailaddress of the {@link IClientInfo}.
     */
    void setEmailAddress(String emailAddress);

    /**
     * Gets the username from the {@link IClientInfo}.
     *
     * @return Username from the {@link IClientInfo}.
     */
    String getUsername();

    /**
     * Sets the username from the {@link IClientInfo}.
     *
     * @param username Username from the {@link IClientInfo}.
     */
    void setUsername(String username);
    
     ArrayList<String> getClans();
     
     ArrayList<String> setClans(ArrayList<String> clans);

    /**
     * Gets the {@link ateamproject.kezuino.com.github.network.packet.packets.PacketSetLoadStatus.LoadStatus} that this {@link IClientInfo} is currently in.
     *
     * @return New {@link ateamproject.kezuino.com.github.network.packet.packets.PacketSetLoadStatus.LoadStatus} to set for this {@link IClientInfo}.
     */
    PacketSetLoadStatus.LoadStatus getLoadStatus();

    /**
     * Sets the {@link ateamproject.kezuino.com.github.network.packet.packets.PacketSetLoadStatus.LoadStatus} that this {@link IClientInfo} is currently in.
     *
     * @param status New {@link ateamproject.kezuino.com.github.network.packet.packets.PacketSetLoadStatus.LoadStatus} to set for this {@link IClientInfo}.
     */
    void setLoadStatus(PacketSetLoadStatus.LoadStatus status);
}
