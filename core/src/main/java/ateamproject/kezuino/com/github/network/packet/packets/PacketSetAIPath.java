package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import ateamproject.kezuino.com.github.singleplayer.ItemType;
import ateamproject.kezuino.com.github.singleplayer.Node;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

public class PacketSetAIPath extends Packet {
    @PacketField(0)
    protected UUID objId;
    @PacketField(1)
    protected GraphPath<Node> path;
    @PacketField(2)
    protected Vector2 position;

    public PacketSetAIPath() {
    }

    public PacketSetAIPath(UUID objId, GraphPath<Node> path, Vector2 position, UUID sender, UUID... receivers) {
        super(sender, receivers);
        this.objId = objId;
        this.path = path;
        this.position = position;
    }

    public UUID getObjId() {
        return objId;
    }

    public GraphPath<Node> getPath() {
        return path;
    }

    public Vector2 getPosition() {
        return position;
    }
}
