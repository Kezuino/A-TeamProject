/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Sven
 */
public class PacketGetKickInformation extends Packet<ArrayList<String>> {

    public PacketGetKickInformation() {
    }
    
    public PacketGetKickInformation(UUID sender, UUID... receivers) {
        super(sender, receivers);
    }
}
