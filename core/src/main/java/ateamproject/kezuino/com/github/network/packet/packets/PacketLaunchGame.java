package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

public class PacketLaunchGame extends Packet {
    protected boolean paused;
    @PacketField(0)
    protected int level;

    public PacketLaunchGame() {
    }

    public PacketLaunchGame(boolean paused, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.paused = paused;
        this.level = -1;
    }

    public PacketLaunchGame(boolean paused, int level, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.paused = paused;
        this.level = level < -1 ? -1 : level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isPaused() {
        return paused;
    }
}
