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
    protected String emailadres;

    public PacketSetUsername(String name, String emailadres) {
        this.name = name;
        this.emailadres = emailadres;
    }

    public PacketSetUsername(String name, String emailadres, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.name = name;
        this.emailadres = emailadres;
    }

    public String getName() {
        return name;
    }

    public String getEmailadres() {
        return emailadres;
    }

}
