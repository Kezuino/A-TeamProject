package ateamproject.kezuino.com.github.network;

import ateamproject.kezuino.com.github.network.packet.IPacketSender;
import java.util.ArrayList;

import java.util.UUID;

public abstract class ClientInfo implements IClientInfo {
    protected UUID privateId;
    protected UUID publicId;
    protected float timeSinceLastActivity;
    protected Game game;
    protected String emailAddress;
    protected String username;
    protected ArrayList<String> clans;

    public ClientInfo() {
        privateId = UUID.randomUUID();
        publicId = UUID.randomUUID();
        clans = new ArrayList<>();
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
    public ArrayList<String> getClans(){
        return this.clans;
    }
    
     @Override
    public ArrayList<String> setClans(ArrayList<String> clans){
        this.clans = clans;
        return this.clans;
    }
}
