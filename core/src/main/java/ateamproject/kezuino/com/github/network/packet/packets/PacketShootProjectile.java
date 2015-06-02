package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import ateamproject.kezuino.com.github.utility.game.Direction;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

public class PacketShootProjectile extends Packet<Boolean> {
    public PacketShootProjectile() {
    }

    public PacketShootProjectile(UUID... senderAndReceivers) {
        super(senderAndReceivers);
    }
}
