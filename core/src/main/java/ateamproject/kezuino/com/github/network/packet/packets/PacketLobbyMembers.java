/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Fatih
 */
public class PacketLobbyMembers extends Packet<Map<UUID, String>> {

     @PacketField(0)
    protected UUID lobbyId;

     public PacketLobbyMembers(UUID lobbyId, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.lobbyId = lobbyId;
     }
     
      public UUID getLobbyId() {
        return lobbyId;
    }
}
