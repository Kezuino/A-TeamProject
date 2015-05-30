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
 * @author Sven
 */
public class PacketGetEmail extends Packet<String> {

    @PacketField(0)
    protected String username;

    public PacketGetEmail(String username, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.username = username;
    }

    public PacketGetEmail(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
