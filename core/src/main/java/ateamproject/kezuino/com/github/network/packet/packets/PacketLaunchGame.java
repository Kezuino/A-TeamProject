package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

public class PacketLaunchGame extends Packet {
    protected boolean paused;
    @PacketField(0)
    protected int level;
    @PacketField(1)
    protected int score;

    public PacketLaunchGame() {
        this.paused = false;
        this.level = -1;
    }

    public PacketLaunchGame(boolean paused, UUID sender, UUID... receivers) {
        super(sender, receivers);
        this.paused = paused;
        this.level = -1;
    }

    public PacketLaunchGame(boolean paused, int level, int score, UUID sender, UUID... receivers) {
        super(sender, receivers);
        this.paused = paused;
        this.level = level < -1 ? -1 : level;
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    public int getScore() {
        return this.score;
    }

    public boolean isPaused() {
        return paused;
    }
}
