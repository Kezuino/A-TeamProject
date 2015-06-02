package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;

import java.util.UUID;

public class PacketLaunchGame extends Packet {
    protected boolean paused;

    public PacketLaunchGame() {
    }

    public PacketLaunchGame(boolean paused, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.paused = paused;
    }

    public boolean isPaused() {
        return paused;
    }
}
