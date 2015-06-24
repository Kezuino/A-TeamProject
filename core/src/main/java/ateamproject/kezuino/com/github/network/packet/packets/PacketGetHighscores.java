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
 * @author Sven
 */
public class PacketGetHighscores extends Packet<Map<String, Integer>> {


    public PacketGetHighscores(UUID sender) {
        super(sender);       
    }
}
