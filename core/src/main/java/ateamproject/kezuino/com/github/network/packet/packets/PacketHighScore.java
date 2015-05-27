package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

public class PacketHighScore extends Packet<Boolean> {
    @PacketField(0)
    protected String ClanName;
    @PacketField(1)
    protected int Score;

    public PacketHighScore() {
    }

    public PacketHighScore(String ClanName, int Score, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.ClanName = ClanName;
        this.Score = Score;
    }

    public String getClanName() {
        return ClanName;
    }

    public int getScore() {
        return Score;
    }
}
