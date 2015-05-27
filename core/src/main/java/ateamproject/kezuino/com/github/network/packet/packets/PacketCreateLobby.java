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
public class PacketCreateLobby extends Packet<Boolean> {

    @PacketField(0)
    protected String lobbyname;

    public PacketCreateLobby() {
    }

    public PacketCreateLobby(UUID... senderAndReceivers) {
        super(senderAndReceivers);
    }
    
    public PacketCreateLobby(String lobbyname,UUID... senderAndReceivers) {
        this(senderAndReceivers);
        
        this.lobbyname = lobbyname;
    }

    public String getLobbyname() {
        return lobbyname;
    }

}
