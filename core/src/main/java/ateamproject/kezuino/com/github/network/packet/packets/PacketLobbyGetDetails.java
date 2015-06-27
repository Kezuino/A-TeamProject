package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

public class PacketLobbyGetDetails extends Packet<PacketLobbySetDetails.Data> {
    public PacketLobbyGetDetails() {
    }

    public PacketLobbyGetDetails(UUID sender, UUID... receivers) {
        super(sender, receivers);
    }
}
