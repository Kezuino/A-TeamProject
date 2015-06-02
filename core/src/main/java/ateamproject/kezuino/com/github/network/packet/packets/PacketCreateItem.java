package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import ateamproject.kezuino.com.github.singleplayer.ItemType;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

public class PacketCreateItem extends Packet {
    @PacketField(0)
    protected UUID objId;
    @PacketField(1)
    protected ItemType type;
    @PacketField(2)
    protected Vector2 position;

    public PacketCreateItem() {
    }

    public PacketCreateItem(UUID objId, ItemType type, Vector2 position, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.objId = objId;
        this.type = type;
        this.position = position;
    }

    public UUID getObjId() {
        return objId;
    }

    public ItemType getType() {
        return type;
    }

    public Vector2 getPosition() {
        return position;
    }
}
