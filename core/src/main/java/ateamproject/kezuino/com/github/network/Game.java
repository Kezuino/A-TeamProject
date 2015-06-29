/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.singleplayer.Map;
import ateamproject.kezuino.com.github.utility.collection.ConcurrentLinkedHashSet;
import com.badlogic.gdx.graphics.Color;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

/**
 * Holds information about a hosted lobby/game. Used by the {@link INetworkComponent} to synchronize {@link ateamproject.kezuino.com.github.network.rmi.IProtocolClient}.
 */
public class Game {

    public static final Color[] SELECTABLE_COLORS = new Color[] {
            Color.RED,
            Color.BLUE,
            Color.ORANGE,
            Color.PINK,
            Color.GREEN,
            Color.PURPLE,
            Color.TEAL,
            Color.NAVY
    };

    protected UUID id;
    protected String name;
    protected ConcurrentLinkedHashSet<UUID> clients;
    /**
     * First UUID is the voter. second UUID is the person that received the vote.
     */
    protected CopyOnWriteArrayList<UUID[]> votes;
    protected UUID hostId;
    protected boolean inGame;
    protected Queue<Packet> loadQueue;
    protected String map;
    /**
     * The name of the clan who did create this game. If no clan did create it, it should be null.
     */
    protected String clanName;
    protected Server server;
    private int mapObjectCount;
    private int level;

    public Game(Server server, String name, String clanName, UUID host) {
        this.server = server;

        // Generate UUID and give lobby a name
        this.id = UUID.randomUUID();
        this.name = name;
        this.clanName = clanName;
        this.votes = new CopyOnWriteArrayList<>();//[0]voter,[1]person to kick
        this.loadQueue = new ArrayDeque<>();

        // Ingame is set to true if game is started, if started dont show on lobbylist.
        this.inGame = false;
        this.level = 0;

        // Add host to clients list
        this.hostId = host;
        this.clients = new ConcurrentLinkedHashSet<>();
        this.clients.add(host);
    }

    public Queue<Packet> getLoadQueue() {
        return loadQueue;
    }

    /**
     * Gets the {@link UUID id} of this {@link Game}.
     *
     * @return {@link UUID id} of this {@link Game}.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Gets the name of the game / lobby.
     *
     * @return Name of the game / lobby.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this {@link Game}.
     *
     * @param name New name of this {@link Game}.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets all {@link IClientInfo clients} that are currently in this {@link Game}.
     *
     * @return All {@link IClientInfo clients} that are currently in this {@link Game}.
     */
    public ConcurrentLinkedHashSet<UUID> getClients() {
        return clients;
    }

    /**
     * Gets all {@link IClientInfo clients} that are in this {@link Game} excluding the {@link UUID host}.
     *
     * @return {@link IClientInfo clients} that are in this {@link Game} excluding the {@link UUID host}.
     * @see #getClients()
     */
    public Stream<UUID> getClientsWithoutHost() {
        return clients.stream().filter(c -> !c.equals(getHostId()));
    }

    /**
     * Gets all {@link IClientInfo clients} excluding the private id {@link IClientInfo clients} given by {@code excluded}.
     *
     * @param excluded {@link IClientInfo} to exclude from the result.
     * @return {@link IClientInfo clients} excluding the {@link IClientInfo clients} given by {@code excluded}.
     */
    public Stream<UUID> getClientsExclude(UUID... excluded) {
        if (excluded == null || excluded.length == 0) return getClients().stream();
        return getClients().stream().filter(id -> !Stream.of(excluded).anyMatch(id::equals));
    }

    /**
     * This array is a copy of {@link #getClients()}. Do not add to this array!
     *
     * @return A unmodifiable list.
     */
    public UUID[] getClientsAsArray() {
        List<UUID> uuids = new ArrayList<>(getClients());
        return uuids.toArray(new UUID[uuids.size()]);
    }

    /**
     * Gets if true, {@link IClientInfo clients} are currently playing. False if {@link IClientInfo clients} are in the lobby.
     *
     * @return If true, {@link IClientInfo clients} are currently playing. False if {@link IClientInfo clients} are in the lobby.
     */
    public boolean isInGame() {
        return inGame;
    }

    /**
     * Set to true when {@link IClientInfo clients} are currently playing. False if {@link IClientInfo clients} are in the lobby.
     */
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public UUID getHostId() {
        return hostId;
    }

    public CopyOnWriteArrayList<UUID[]> getVotes() {
        return votes;
    }

    /**
     * Gets the {@link Map} that this {@link Game} will play on.
     *
     * @return {@link Map} that this {@link Game} will play on.
     */
    public String getMap() {
        return map;
    }

    /**
     * Sets the new {@link Map} name that this {@link Game} will play on.
     *
     * @param map New {@link Map} name that this {@link Game} will play on.
     */
    public void setMap(String map) {
        this.map = map;
    }

    public String getClanName() {
        return this.clanName;
    }

    public int getMapObjectCount() {
        return mapObjectCount;
    }

    public void setMapObjectCount(int mapObjectCount) {
        this.mapObjectCount = mapObjectCount;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Removes the client from the given private {@link UUID id}.
     *
     * @param id Private {@link UUID id} of the client.
     * @return If true, the client was removed.
     */
    public boolean removeClient(UUID id) {
        if (id == null) return false;
        IClientInfo client = server.getClient(id);
        if (client == null) return false;

        if (client.getPrivateId().equals(getHostId())) {
            this.server.removeGame(this.getId());
        } else {
            // Destroy game and client relation.
            client.setGame(null);
            getClients().remove(id);

            // Remove votes that are open.
            CopyOnWriteArrayList<UUID[]> allVotes = getVotes();
            for (UUID[] voteCollection : allVotes) {
                if (voteCollection[0].equals(client) || voteCollection[1].equals(client)) {
                    allVotes.remove(voteCollection);
                }
            }

            if (getClients().isEmpty()) {
                server.removeGame(getId());
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Game{" +
                "name='" + name + '\'' +
                ", inGame=" + inGame +
                ", map='" + map + '\'' +
                ", clanName='" + clanName + '\'' +
                '}';
    }
}
