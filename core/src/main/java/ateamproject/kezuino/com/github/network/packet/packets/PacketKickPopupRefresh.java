package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;

import java.util.UUID;

public class PacketKickPopupRefresh extends Packet {

    public PacketKickPopupRefresh(UUID... senderAndReceivers) {
        super(senderAndReceivers);
    }
}
