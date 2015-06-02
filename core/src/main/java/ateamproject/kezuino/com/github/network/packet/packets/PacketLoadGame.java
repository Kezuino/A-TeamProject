package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

/**
 * Indicates when to start loading a game.
 */
public class PacketLoadGame extends Packet {
    /**
     * Name of the level to load.
     */
    @PacketField(0)
    protected String mapName;

    /**
     * If true, the receiver should send created objects from the TMX file back to the server for broadcasting.
     */
    @PacketField(1)
    protected boolean master;
    @PacketField(2)
    protected int playerLimit;

    public PacketLoadGame() {
    }

    public PacketLoadGame(UUID... senderAndReceivers) {
        super(senderAndReceivers);
    }

    public PacketLoadGame(String mapName, boolean master, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.mapName = mapName;
        this.master = master;
    }

    public PacketLoadGame(String mapName, boolean master, int playerLimit, UUID... senderAndReceivers) {
        this(mapName, master);
        this.playerLimit = playerLimit;
    }

    public int getPlayerLimit() {
        return playerLimit;
    }

    public void setPlayerLimit(int playerLimit) {
        this.playerLimit = playerLimit;
    }

    /**
     * See {@link #mapName}.
     */
    public String getMap() {
        return mapName;
    }

    public boolean isMaster() {
        return master;
    }
}
