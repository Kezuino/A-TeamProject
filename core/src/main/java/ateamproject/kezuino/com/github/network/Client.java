/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network;

import ateamproject.kezuino.com.github.network.packet.IPacketSender;
import ateamproject.kezuino.com.github.network.packet.Packet;
import com.badlogic.gdx.Game;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author Kez and Jules
 */
public abstract class Client implements IClient, IPacketSender {
    protected HashMap<Integer, UUID> players;
    protected Game game;
    protected long secondInactive;
    protected Thread updateThread;
    protected boolean isUpdating;
    protected UUID privateId;
    protected UUID publicId;

    public Client(com.badlogic.gdx.Game game) {
        this.game = game;
        this.players = new HashMap<>(8);
        this.secondInactive = System.nanoTime();
        this.isUpdating = true;

        this.privateId = UUID.randomUUID();
        this.publicId = UUID.randomUUID();
    }

    /**
     * Gets the public {@link UUID} used to send to other {@link Client clients} than this {@link Client} so that they can reference this {@link Client}, but not identify themself as him.
     *
     * @return Public {@link UUID} used to send to other {@link Client clients} than this {@link Client} so that they can reference this {@link Client}, but not identify themself as him.
     */
    public UUID getPublicId() {
        return publicId;
    }

    /**
     * Sets the public {@link UUID} used to send to other {@link Client clients} than this {@link Client} so that they can reference this {@link Client}, but not identify themself as him.
     *
     * @return Public {@link UUID} used to send to other {@link Client clients} than this {@link Client} so that they can reference this {@link Client}, but not identify themself as him.
     */
    public void setPublicId(UUID publicId) {
        this.publicId = publicId;
    }

    /**
     * Gets the bind of player's index in the game with the public {@link UUID} on the {@link IServer}.
     *
     * @return Bind of player's index in the game with the public {@link UUID} on the {@link IServer}.
     */
    public HashMap<Integer, UUID> getPlayers() {
        return players;
    }

    public UUID getPlayer(int index) {
        return getPlayers().get(index);
    }

    /**
     * Gets the private id of this {@link Client}. Do not send this id to other {@link Client clients}.
     *
     * @return Private id of this {@link Client}. Do not send this id to other {@link Client clients
     */
    @Override
    public UUID getPrivateId() {
        return privateId;
    }

    /**
     * Sets the private {@link UUID} used by this {@link Client} to identify itself with the {@link Server}.
     *
     * @param id
     */
    public void setPrivateId(UUID id) {
        this.privateId = id;
    }

    @Override
    public void send(Packet packet) {
        this.secondInactive = System.nanoTime();
        Packet.execute(packet);
    }

    @Override
    public abstract void registerPackets();

    @Override
    public void unregisterPackets() {
        Packet.unregisterAll();
    }

    /**
     * Starts the {@link IClient} and begin making a connection with the {@link IServer}.
     */
    public abstract void start();

    /**
     * Stops the {@link IClient} from listening and sending to {@link IServer}.
     */
    public abstract void stop();

    @Override
    public double getSecondsInactive() {
        return (System.nanoTime() - secondInactive) / 1000000000.0;
    }
}
