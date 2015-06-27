package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

public class PacketLogout extends Packet {
    public PacketLogout() {
    }

    public PacketLogout(UUID sender, UUID... receivers) {
        super(sender, receivers);
    }
}
