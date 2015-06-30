package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import ateamproject.kezuino.com.github.utility.game.Direction;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

public class PacketObjectMove extends Packet {
    @PacketField(0)
    protected Vector2 from;
    @PacketField(1)
    protected Vector2 to;
    @PacketField(2)
    protected UUID object;

    public PacketObjectMove() {
    }

    public PacketObjectMove(Vector2 from, Vector2 to, UUID object, UUID sender, UUID... receivers) {
        super(sender, receivers);
        this.from = from;
        this.to = to;
        this.object = object;
    }

    public Vector2 getFrom() {
        return this.from;
    }
    
    public Vector2 getTo() {
        return this.to;
    }
    
    public UUID getObject() {
        return this.object;
    }
}
