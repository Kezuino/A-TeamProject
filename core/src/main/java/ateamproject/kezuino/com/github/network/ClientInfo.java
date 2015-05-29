package ateamproject.kezuino.com.github.network;

import java.util.UUID;

public class ClientInfo implements IClientInfo {
    protected UUID privateId;
    protected UUID publicId;
    protected float timeSinceLastActivity;

    public ClientInfo() {
        privateId = UUID.randomUUID();
        publicId = UUID.randomUUID();
        resetSecondsActive();
    }

    @Override
    public UUID getPrivateId() {
        return privateId;
    }

    @Override
    public UUID getPublicId() {
        return publicId;
    }

    @Override
    public void setPublicId(UUID id) {
        if (id == null) throw new IllegalArgumentException("Parameter id must not be null.");
        if (id.equals(this.privateId)) throw new IllegalStateException("Parameter id must not be the same as the private id.");
        this.publicId = id;
    }

    @Override
    public void setPrivateId(UUID id) {
        if (id == null) throw new IllegalArgumentException("Parameter id must not be null.");
        if (id.equals(this.publicId)) throw new IllegalStateException("Parameter id must not be the same as the public id.");
        this.privateId = id;
    }

    @Override
    public float getSecondsInactive() {
        return (System.nanoTime() - timeSinceLastActivity) / 1000000000.0f;
    }

    @Override
    public void resetSecondsActive() {
        timeSinceLastActivity = System.nanoTime();
    }
}
