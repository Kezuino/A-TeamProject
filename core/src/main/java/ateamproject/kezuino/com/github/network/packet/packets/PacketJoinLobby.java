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

        protected String map;
        protected String lobbyName;
        protected Map<UUID, String> members;

        public PacketJoinLobbyData() {
            this.members = new Hashtable<>();
        }

        public String getMap() {
            return map;
        }

        public void setMap(String map) {
            this.map = map;
        }

        public String getLobbyName() {
            return lobbyName;
        }

        public void setLobbyName(String lobbyName) {
            this.lobbyName = lobbyName;
        }

        public Map<UUID, String> getMembers() {
            return members;
        }

        public void setMembers(Map<UUID, String> members) {
            this.members = members;
        }
    }
}
