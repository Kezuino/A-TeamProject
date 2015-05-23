/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.packets.PacketKick;
import ateamproject.kezuino.com.github.network.packet.packets.PacketLogin;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author Kez and Jules
 */
public class Client implements IClient {
    protected HashMap<Integer, UUID> players;

    protected Client() {
        players = new HashMap<>(8);
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
        return null;
    }

    @Override
    public void send(Packet packet) {
        Packet.execute(packet);
    }

    @Override
    public void registerPackets() {
        // TODO: Implement packets to handle services offered by the client.
        Packet.registerAction(PacketKick.class, packet -> System.out.println("Sending kick request to server: " + packet.getReason()));
        Packet.registerFunc(PacketLogin.class, packet -> UUID.randomUUID());
    }

    @Override
    public void unregisterPackets() {
        Packet.unregisterAll();
    }
}
