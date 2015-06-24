/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network;

import ateamproject.kezuino.com.github.network.packet.IPacketSender;
import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketManager;
import com.badlogic.gdx.Game;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author Kez and Jules
 */
public abstract class Client implements INetworkComponent, IPacketSender {
    /**
     * Contains the public ids of all the players currently grouped with this {@link Client}.
     */
    protected HashMap<Integer, UUID> players;
    protected Game game;
    protected Thread updateThread;
    protected boolean isUpdating;
    protected UUID id;
    protected UUID publicId;
    protected PacketManager packets;
    protected String Username;
    protected String emailadres;

    public Client(com.badlogic.gdx.Game game) {
        this.game = game;
        this.players = new HashMap<>(8);
        this.isUpdating = true;
        this.packets = new PacketManager();
    }

    public UUID getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicId) {
        this.publicId = publicId;
    }

    /**
     * Sets the {@link Game} that the {@link Client} is currently using to render.
     *
     * @param game {@link Game} that the {@link Client} is currently using to render.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Gets the private id only known by the {@link Server} and this {@link Client}.
     *
     * @return Private id only known by the {@link Server} and this {@link Client}.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the private id only known by the {@link Server} and this {@link Client}.
     *
     * @return Private id only known by the {@link Server} and this {@link Client}.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Gets the bind of player's index in the game with the public {@link UUID} on the {@link INetworkComponent}.
     *
     * @return Bind of player's index in the game with the public {@link UUID} on the {@link INetworkComponent}.
     */
    public HashMap<Integer, UUID> getPlayers() {
        return players;
    }

    public UUID getPlayer(int index) {
        return getPlayers().get(index);
    }

    /**
     * Sends the {@link Packet} to the {@link Server}. If no senders were specified it sets it to the current {@link Client#id} by default.
     *
     * @param packet {@link Packet} to send to the {@link IClientInfo}.
     */
    @Override
    public void send(Packet packet) {
        if (packet.getSender() == null && packet.getReceivers().length <= 0) packet.setSender(getId());
        packets.execute(packet);
    }

    @Override
    public abstract void registerPackets();

    @Override
    public void unregisterPackets() {
        packets.unregisterAll();
    }

    /**
     * Starts the {@link IClientInfo} and begin making a connection with the {@link INetworkComponent}.
     */
    public abstract void start();

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public void setEmailadres(String emailadres) {
        this.emailadres = emailadres;
    }

    public String getUsername() {
        return Username;
    }

    public String getEmailadres() {
        return emailadres;
    }
}
