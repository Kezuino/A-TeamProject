package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import ateamproject.kezuino.com.github.utility.game.Direction;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

public class PacketPickUpItem extends Packet<Boolean> {
    @PacketField(0)
    protected UUID item;

    public PacketPickUpItem() {
    }

    public PacketPickUpItem(UUID item, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.item = item;

    }

    public UUID getItem() {
        return item;
    }

}
