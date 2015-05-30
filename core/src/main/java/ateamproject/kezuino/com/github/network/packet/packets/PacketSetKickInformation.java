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
public class PacketSetKickInformation extends Packet<Void> {

    @PacketField(0)
    protected UUID personToVoteFor;

    public PacketSetKickInformation(UUID personToVoteFor, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.personToVoteFor = personToVoteFor;
    }
    
    public UUID getPersonToVoteFor(){
        return personToVoteFor;
    }
}
