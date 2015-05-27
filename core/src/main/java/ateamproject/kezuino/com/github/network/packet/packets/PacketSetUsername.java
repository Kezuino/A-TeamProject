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
public class PacketSetUsername extends Packet<Boolean> {

    @PacketField(0)
    protected String name;
    @PacketField(1)
    protected String emailaddress;

    public PacketSetUsername(String name, String emailaddress, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.name = name;
        this.emailaddress = emailaddress;
    }

    public String getName() {
        return name;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

}
