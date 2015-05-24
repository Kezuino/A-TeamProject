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
    protected long secondsFromLastUpdate;
    protected Thread updateThread;
    protected boolean isUpdating;
    private UUID id;

    public Client(com.badlogic.gdx.Game game) {
        this.game = game;
        this.players = new HashMap<>(8);
        this.secondsFromLastUpdate = System.nanoTime();
        this.isUpdating = true;
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

    @Override
    public UUID getId() {
        return id;
    }

    /**
     * Sets the {@link UUID} used by this {@link Client} to identify itself with the {@link Server}.
     *
     * @param id
     */
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public void send(Packet packet) {
        this.secondsFromLastUpdate = System.nanoTime();
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
    public double getSecondsFromLastPacket() {
        return (System.nanoTime() - secondsFromLastUpdate) / 1000000000.0;
    }
}
