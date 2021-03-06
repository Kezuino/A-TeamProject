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
public class PacketCreateClan extends Packet<Boolean> {


    @PacketField(0)
    protected String clanName;

    public PacketCreateClan(UUID sender, String clanName) {
        super(sender);        
        this.clanName = clanName;
    }


    public String getClanName() {
        return clanName;
    }

}
