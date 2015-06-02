package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.Client;
import ateamproject.kezuino.com.github.network.Server;
import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import ateamproject.kezuino.com.github.singleplayer.ItemType;
import ateamproject.kezuino.com.github.utility.game.Direction;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

/**
 * {@link Packet} that will be send on interval to check if the {@link Server} or {@link Client} is still responding.
 */
public class PacketCreateGameObject extends Packet {

    @PacketField(0)
    protected String typeName;
    @PacketField(1)
    protected Vector2 position;
    @PacketField(2)
    protected Direction direction;
    @PacketField(3)
    protected float speed;
    @PacketField(4)
    protected UUID id;
    @PacketField(5)
    protected int color;
    @PacketField(6)
    protected int index;
    @PacketField(7)
    protected ItemType type;

    public PacketCreateGameObject() {
    }

    public PacketCreateGameObject(UUID... senderAndReceivers) {
        super(senderAndReceivers);
    }

    public PacketCreateGameObject(String typeName, Vector2 position, Direction direction, float speed, UUID id, int color, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.typeName = typeName;
        this.position = position;
        this.direction = direction;
        this.speed = speed;
        this.color = color;
        this.id = id;
    }

    public ItemType getItemType() {
        return type;
    }

    public void setItemType(ItemType type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getColor() {
        return color;
    }

    public UUID getId() {
        return id;
    }

    public float getSpeed() {
        return speed;
    }

    public String getTypeName() {
        return typeName;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }
}

