/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import java.util.UUID;

/**
 *
 * @author Fatih
 */
public class PacketLeaveLobby extends Packet<Boolean> { 

     @PacketField(0)
    protected UUID lobbyid;
    
    public PacketLeaveLobby() {
    }
    
    
    
    public PacketLeaveLobby(UUID lobbyid,UUID... senderAndReceivers) {
        super(senderAndReceivers);
        
        this.lobbyid = lobbyid;
    }

    public UUID getLobbyid() {
        return this.lobbyid;
    }
    
}
