package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.Game;
import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.packets.PacketHeartbeat;
import ateamproject.kezuino.com.github.network.packet.packets.PacketKick;
import ateamproject.kezuino.com.github.network.packet.packets.PacketLoginAuthenticate;
import ateamproject.kezuino.com.github.network.packet.packets.PacketLoginUserExists;
import ateamproject.kezuino.com.github.singleplayer.ClanFunctions;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServerBase extends UnicastRemoteObject implements IProtocolServer {

    protected transient Server server;
    private ClanFunctions clanFunctions;

    public ServerBase(Server server) throws RemoteException {
        super(Registry.REGISTRY_PORT);
        if (server == null) {
            throw new IllegalArgumentException("Parameter server must not be null.");
        }
        this.server = server;
        clanFunctions = new ClanFunctions();
    }

    @Override
    public void creatureMove(int creatureID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UUID login(String email, String password) throws RemoteException {
        PacketLoginAuthenticate packet = new PacketLoginAuthenticate(email, password);
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public void heartbeat(UUID client) throws RemoteException {
        PacketHeartbeat packet = new PacketHeartbeat(client);
        server.send(packet);
    }

    @Override
    public Game createLobby(String lobbyName, UUID host) throws RemoteException {
        // add lobby to games array

        Game newGame = new Game(lobbyName, server.getClientFromPublic(host).getId());
        server.getGames().add(newGame);

        System.out.println("Lobby: " + newGame.getName() + " - id " + newGame.getId() + " CREATED !");

        return newGame;
    }

    @Override
    public List<Game> getLobbies() throws RemoteException {
        return new ArrayList<>(this.server.getGames());
    }

    @Override
    public Game getLobbyById(UUID lobbyId) throws RemoteException {
//        for (Game game : gameList) {
//            if (game.getId().equals(lobbyId)) {
//                return game;
//            }
//        }
        return null;
    }

    @Override
    public Game joinLobby(UUID lobbyId, UUID client) throws RemoteException {
        //        for (Game game : gameList) {
//            if (game.getId().equals(lobbyId)) {
//                if (game.invite(client)) {
//                    return game;
//                }
//            }
//        }
        return null;
    }

    @Override
    public boolean quitLobby(UUID lobbyId) throws RemoteException {
//        for (Game game : gameList) {
//            boolean match = game.getId().equals(lobbyId);
//            System.out.println(match);
//            if (match) {
//                return gameList.remove(game);
//            }
//        }

        return false;
    }

    @Override
    public boolean kickClient(UUID client, PacketKick.KickReasonType reasonType, String message) throws RemoteException {
        return Packet.execute(new PacketKick(reasonType, message, client)).getResult();
    }

    @Override
    public ArrayList<String> clanFillTable(String emailadres) throws RemoteException {
        return clanFunctions.fillTable(emailadres);
    }

    @Override
    public boolean clanCreateClan(String clanName, String emailaddress) throws RemoteException {
        return clanFunctions.createClan(clanName, emailaddress);
    }

    @Override
    public ClanFunctions.InvitationType clanGetInvitation(String clanName, String emailaddress) throws RemoteException {
        return clanFunctions.getInvitation(clanName, emailaddress);
    }

    @Override
    public ClanFunctions.ManagementType getManagement(String clanName, String emailaddress) throws RemoteException {
        return clanFunctions.getManagement(clanName, emailaddress);
    }

    @Override
    public String getPeople(String clanName) throws RemoteException {
        return clanFunctions.getPeople(clanName);
    }

    @Override
    public boolean handleInvitation(ClanFunctions.InvitationType invite, String clanName, String emailAddress, String nameOfInvitee) throws RemoteException {
        return clanFunctions.handleInvitation(invite, clanName, emailAddress, nameOfInvitee);
    }

    @Override
    public boolean handleManagement(ClanFunctions.ManagementType manage, String clanName, String emailaddress) throws RemoteException {
        return clanFunctions.handleManagement(manage, clanName, emailaddress);
    }

    @Override
    public String getUsername(String emailaddress) throws RemoteException {
        return clanFunctions.getUsername(emailaddress);
    }

    @Override
    public String getEmail(String username) throws RemoteException {
        return clanFunctions.getEmail(username);
    }

    @Override
    public boolean setUsername(String name, String emailaddress) throws RemoteException {
        return clanFunctions.setUsername(name, emailaddress);
    }

    @Override
    public boolean doesUserExists(String email) throws RemoteException {
        PacketLoginUserExists packet = new PacketLoginUserExists(email);
        server.send(packet);
        return packet.getResult();
    }

}
