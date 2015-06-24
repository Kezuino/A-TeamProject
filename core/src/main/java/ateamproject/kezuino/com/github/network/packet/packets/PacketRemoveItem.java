/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import ateamproject.kezuino.com.github.singleplayer.ItemType;
import java.util.UUID;

/**
 *
 * @author Kez and Jules
 */
public class PacketRemoveItem extends Packet {
    @PacketField(0)
    protected UUID itemId;
    @PacketField(1)
    protected ItemType itemType;
    
    public PacketRemoveItem() {
    }
    
    public PacketRemoveItem(UUID... senderAndReceivers) {
        super(senderAndReceivers);
    }

    public PacketRemoveItem(UUID id, ItemType itemType, UUID... senderAndReceivers) {
        this(senderAndReceivers);
        this.itemId = id;
        this.itemType = itemType;
    }

    public UUID getItemId() {
        return this.itemId;
    }
    
    public ItemType getItemType() {
        return this.itemType;
    }
}
