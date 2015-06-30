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
    @PacketField(3)
    protected int level;
    @PacketField(4)
    protected int score;

    public PacketLoadGame() {
    }

    public PacketLoadGame(UUID sender, UUID... receivers) {
        super(sender, receivers);
    }

    public PacketLoadGame(String mapName, boolean master, UUID sender, UUID... receivers) {
        super(sender, receivers);
        this.mapName = mapName;
        this.master = master;
    }

    public PacketLoadGame(String mapName, boolean master, int playerLimit, int level, UUID sender, UUID... receivers) {
        this(mapName, master, sender, receivers);
        this.playerLimit = playerLimit;
        this.level = level;
    }
    
     public PacketLoadGame(String mapName, boolean master, int playerLimit, int level, int score, UUID sender, UUID... receivers) {
        this(mapName, master, playerLimit, level, sender, receivers);
        this.score = score;
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
    
    public int getLevel(){
        return level;
    }
    
    public int getScore() {
        return this.score;
    }
}
