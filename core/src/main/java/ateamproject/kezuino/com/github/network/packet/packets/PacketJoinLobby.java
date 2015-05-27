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
import java.util.UUID;

/**
 *
 * @author Fatih
 */
public class PacketJoinLobby extends Packet<PacketJoinLobbyData> {
    
    @PacketField(0)
    protected UUID lobbyid;
    
    public PacketJoinLobby() {
    }
    
    public PacketJoinLobby(UUID lobbyid, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        
        this.lobbyid = lobbyid;
    }

    public UUID getLobbyid() {
        return lobbyid;
    }
    
    public static class PacketJoinLobbyData implements Serializable{
        
        public String lobbyName;
        public Dictionary<UUID,String> members;

        public PacketJoinLobbyData() {
            this.members = new Hashtable<>();
        }
        
        
        
    }
    
}
