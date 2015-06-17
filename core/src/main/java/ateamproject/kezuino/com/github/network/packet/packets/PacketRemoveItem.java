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
 * @author Kez and Jules
 */
public class PacketRemoveItem extends Packet {
    @PacketField(0)
    protected UUID id;

    public PacketRemoveItem() {
    }

    public PacketRemoveItem(UUID id,  UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
