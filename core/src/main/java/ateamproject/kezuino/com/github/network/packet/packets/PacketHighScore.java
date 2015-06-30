package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

public class PacketHighScore extends Packet<Boolean> {
    @PacketField(0)
    protected int score;

    public PacketHighScore() {
    }

    public PacketHighScore(int score, UUID sender) {
        super(sender);
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
