/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network;

import ateamproject.kezuino.com.github.singleplayer.Map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

/**
 * Holds information about a hosted lobby/game. Used by the {@link INetworkComponent} to synchronize {@link ateamproject.kezuino.com.github.network.rmi.IProtocolClient}.
 */
public class Game {

    protected UUID id;
    protected String name;
    protected HashSet<UUID> clients;
    protected ArrayList<UUID[]> votes;//first UUID is the voter. second UUID is the person who did recieve the vote.
    protected UUID hostId;
    protected boolean inGame;
    protected String map;
    protected String clanName;//the name of the clan who did create this game. If no clan did create it, it should be null

    public Game(String name, UUID host) {
        // Generate UUID and give lobby a name
        this.id = UUID.randomUUID();
        this.name = name;
        this.votes = new ArrayList<>();

        // Ingame is set to true if game is started, if started dont show on lobbylist.
        this.inGame = false;

        // Add host to clients list
        this.hostId = host;
        this.clients = new HashSet<>();
        this.clients.add(host);
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
     * Gets all {@link IClientInfo clients} that are currently in this game / lobby.
     *
     * @return All {@link IClientInfo clients} that are currently in this game / lobby.
     */
    public HashSet<UUID> getClients() {
        return clients;
    }

    public UUID[] getClientsAsArray() {
        return clients.toArray(new UUID[clients.size()]);
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

    public ArrayList<UUID[]> getVotes() {
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
    
    public String getClanName(){
        return this.clanName;
    }
}
