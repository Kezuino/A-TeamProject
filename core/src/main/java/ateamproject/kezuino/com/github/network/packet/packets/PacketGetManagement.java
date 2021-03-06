/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import ateamproject.kezuino.com.github.network.packet.enums.ManagementType;

import java.util.UUID;

/**
 *
 * @author Sven
 */
public class PacketGetManagement extends Packet<ManagementType> {


    @PacketField(0)
    protected String clanName;

    public PacketGetManagement(String clanName,UUID sender) {
        super(sender);        
        this.clanName = clanName;
    }

    public String getClanName() {
        return clanName;
    }

}
