/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network;

import java.util.HashSet;
import java.util.UUID;

/**
 * Holds information about a hosted lobby/game. Used by the {@link IServer} to synchronize {@link ateamproject.kezuino.com.github.network.rmi.IProtocolClient}.
 */
public class Game<TClient extends IClient> {

    protected UUID id;
    protected String name;
    protected HashSet<UUID> clients;

    protected boolean inGame;

    public Game(String name, UUID host) {
        // Generate UUID and give lobby a name
        this.id = UUID.randomUUID();
        this.name = name;

        // Ingame is set to true if game is started, if started dont show on lobbylist.
        this.inGame = false;

        // Add host to clients list
        clients = new HashSet<>();
        clients.add(host);
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
     * Gets all {@link IClient clients} that are currently in this game / lobby.
     *
     * @return All {@link IClient clients} that are currently in this game / lobby.
     */
    public HashSet<UUID> getClients() {
        return clients;
    }

    /**
     * Gets if true, {@link IClient clients} are currently playing. False if {@link IClient clients} are in the lobby.
     *
     * @return If true, {@link IClient clients} are currently playing. False if {@link IClient clients} are in the lobby.
     */
    public boolean isInGame() {
        return inGame;
    }

    /**
     * Set to true when {@link IClient clients} are currently playing. False if {@link IClient clients} are in the lobby.
     */
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
}
