/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import ateamproject.kezuino.com.github.network.packet.packets.PacketJoinLobby.PacketJoinLobbyData;

import java.io.Serializable;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

/**
 * @author Fatih
 */
public class PacketJoinLobby extends Packet<PacketJoinLobbyData> {

    @PacketField(0)
    protected UUID lobbyId;

    public PacketJoinLobby() {
    }

    public PacketJoinLobby(UUID lobbyId, UUID... senderAndReceivers) {
        super(senderAndReceivers);

        this.lobbyId = lobbyId;
    }

    public UUID getLobbyId() {
        return lobbyId;
    }

    public static class PacketJoinLobbyData implements Serializable {

        public String lobbyName;
        public Map<UUID, String> members;

        public PacketJoinLobbyData() {
            this.members = new Hashtable<>();
        }
    }
}
