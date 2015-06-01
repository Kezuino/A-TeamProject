package ateamproject.kezuino.com.github.network;

import ateamproject.kezuino.com.github.network.packet.IPacketSender;
import ateamproject.kezuino.com.github.network.packet.packets.PacketSetLoadStatus;
import ateamproject.kezuino.com.github.singleplayer.Map;

import java.util.UUID;

public abstract class ClientInfo implements IClientInfo {
    protected UUID privateId;
    protected UUID publicId;
    protected float timeSinceLastActivity;
    protected Game game;
    protected String emailAddress;
    protected String username;

    /**
     * Determines if this {@link ClientInfo} has a {@link Map} loaded. And if so, what status it is currently in.
     */
    protected PacketSetLoadStatus.LoadStatus loadStatus;

    public ClientInfo() {
        privateId = UUID.randomUUID();
        publicId = UUID.randomUUID();
        loadStatus = PacketSetLoadStatus.LoadStatus.Empty;

        resetSecondsActive();
    }

    @Override
    public UUID getPrivateId() {
        return privateId;
    }

    @Override
    public void setPrivateId(UUID id) {
        if (id == null) throw new IllegalArgumentException("Parameter id must not be null.");
        if (id.equals(this.publicId))
            throw new IllegalStateException("Parameter id must not be the same as the public id.");
        this.privateId = id;
    }

    @Override
    public UUID getPublicId() {
        return publicId;
    }

    @Override
    public void setPublicId(UUID id) {
        if (id == null) throw new IllegalArgumentException("Parameter id must not be null.");
        if (id.equals(this.privateId))
            throw new IllegalStateException("Parameter id must not be the same as the private id.");
        this.publicId = id;
    }

    @Override
    public float getSecondsInactive() {
        return (System.nanoTime() - timeSinceLastActivity) / 1000000000.0f;
    }

    @Override
    public void resetSecondsActive() {
        timeSinceLastActivity = System.nanoTime();
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public PacketSetLoadStatus.LoadStatus getLoadStatus() {
        return this.loadStatus;
    }

    @Override
    public void setLoadStatus(PacketSetLoadStatus.LoadStatus status) {
        this.loadStatus = status;
    }
}
