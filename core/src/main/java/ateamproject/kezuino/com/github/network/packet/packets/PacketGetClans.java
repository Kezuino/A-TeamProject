/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author Fatih
 */
public class PacketGetClans extends Packet<ArrayList<String>> {

    public PacketGetClans() {
    }

    public PacketGetClans(UUID sender, UUID... receivers) {
        super(sender, receivers);
    }

}
