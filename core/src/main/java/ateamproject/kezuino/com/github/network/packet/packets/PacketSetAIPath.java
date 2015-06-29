package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import com.badlogic.gdx.math.Vector2;

import java.util.Collection;
import java.util.UUID;

public class PacketSetAIPath extends Packet {
    @PacketField(0)
    protected UUID objId;
    @PacketField(1)
    protected Collection<Vector2> path;
    @PacketField(2)
    protected Vector2 position;

    public PacketSetAIPath() {
    }

    public PacketSetAIPath(UUID objId, Collection<Vector2> path, Vector2 position, UUID sender, UUID... receivers) {
        super(sender, receivers);
        if (objId == null) throw new IllegalArgumentException("Parameter objId must not be null.");
        if (position == null) throw new IllegalArgumentException("Parameter position must not be null.");
        if (path == null) throw new IllegalArgumentException("Parameter path must not be null.");

        this.objId = objId;
        this.path = path;
        this.position = position;
    }

    public UUID getObjId() {
        return objId;
    }

    public Collection<Vector2> getPath() {
        return path;
    }

    public Vector2 getPosition() {
        return position;
    }
}
