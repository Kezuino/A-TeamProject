/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashSet;
import java.util.UUID;

/**
 * Holds information about a hosted lobby/game. Used by the {@link IServer} to synchronize {@link ateamproject.kezuino.com.github.network.rmi.IProtocolClient}.
 */
public class Game {

    protected UUID id;
    protected String name;
    protected HashSet<IClient> clients;

    protected boolean inGame;

    public Game(String name, IClient host) {
        // Generate UUID and give lobby a name
        this.id = UUID.randomUUID();
        this.name = name;

        // Ingame is set to true if game is started, if started dont show on lobbylist.
        this.inGame = false;

        // Add host to clients list
        clients = new HashSet<>();
        clients.add(host);
    }


    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HashSet<IClient> getClients() {
        return clients;
    }

//    /**
//     * Tries to drop a {@link IProtocolClient} based on the name.
//     *
//     * @param client {@link IProtocolClient}'s name to find and drop out.
//     * @return True if {@link IProtocolClient} was found and dropped. False otherwise.
//     */
//    public boolean kick(IClient client) {
//        if (client == null) return false;
//        IClient match = clients.stream()
//                               .filter(c -> c.getName().equalsIgnoreCase(client.getName()))
//                               .findAny()
//                               .orElse(null);
//        if (match == null) {
//            return false;
//        }
//
//        match.send(new PacketKick(null, new IClient[] { client }));
//        return true;
//    }

    public boolean invite(IClient client) {
        if (client == null) return false;

        throw new NotImplementedException();

        //return true;
    }

    public boolean isInGame() {
        return inGame;
    }
}
