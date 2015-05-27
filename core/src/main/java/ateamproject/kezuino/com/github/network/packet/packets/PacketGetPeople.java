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
public class PacketGetPeople extends Packet<String> {

    @PacketField(0)
    protected String clanName;

    public PacketGetPeople(String clanName) {
        this.clanName = clanName;
    }

    public PacketGetPeople(String clanName, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.clanName = clanName;
    }

    public String getClanName() {
        return clanName;
    }

}
