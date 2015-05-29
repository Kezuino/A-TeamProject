package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.Game;
import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.enums.InvitationType;
import ateamproject.kezuino.com.github.network.packet.enums.ManagementType;
import ateamproject.kezuino.com.github.network.packet.packets.*;
import ateamproject.kezuino.com.github.singleplayer.ClanFunctions;
import com.badlogic.gdx.math.Vector2;

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
        clanFunctions = ClanFunctions.getInstance();
    }

    @Override
    public UUID login(String email, String password, IProtocolClient client) throws RemoteException {
        PacketLoginAuthenticate packet = new PacketLoginAuthenticate(email, password, client);
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public void heartbeat(UUID client) throws RemoteException {
        server.send(new PacketHeartbeat(client));
    }

    @Override
    public UUID createLobby(UUID sender, String lobbyName) throws RemoteException {
        // add lobby to games array

        Game newGame = new Game(lobbyName, server.getClientFromPublic(sender).getPrivateId());
        server.addGame(newGame);

        System.out.println("Lobby: " + newGame.getName() + " - id " + newGame.getId() + " CREATED !");

        return newGame.getId();
    }

    @Override
    public List<PacketGetLobbies.GetLobbiesData> getLobbies() throws RemoteException {
        PacketGetLobbies packet = new PacketGetLobbies();
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public PacketJoinLobby.PacketJoinLobbyData  joinLobby(UUID lobbyId, UUID client) throws RemoteException {
        PacketJoinLobby.PacketJoinLobbyData returnval = new PacketJoinLobby.PacketJoinLobbyData();
        Game g = server.getGame(lobbyId);
        g.getClients().add(client);
        returnval.lobbyName = g.getName();
        for ( UUID g1 : g.getClients()) {
            
             returnval.members.put(g1, "member name");
        }
       return returnval;
    }

    @Override
    public boolean quitLobby(UUID sender) throws RemoteException {
        PacketQuitLobby packet = new PacketQuitLobby(sender);
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public boolean leaveLobby(UUID sender) throws RemoteException {
        PacketLeaveLobby packet = new PacketLeaveLobby(sender);
        server.send(packet);
        return packet.getResult();
    }
    
    @Override
    public boolean kickClient(UUID sender, UUID target, PacketKick.KickReasonType reasonType, String message) throws RemoteException {
        PacketKick packet = new PacketKick(reasonType, message, sender, target);
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public ArrayList<String> clanFillTable(String emailadres) throws RemoteException {
        return clanFunctions.fillTable(emailadres);
    }

    @Override
    public boolean createClan(UUID sender, String clanName) throws RemoteException {
        return clanFunctions.createClan(clanName, server.getClient(sender).getEmailAddress());
    }

    @Override
    public InvitationType clanGetInvitation(UUID sender, String clanName) throws RemoteException {
        return clanFunctions.getInvitation(clanName, server.getClient(sender).getEmailAddress());
    }

    @Override
    public ManagementType getManagement(UUID sender, String clanName) throws RemoteException {
        return clanFunctions.getManagement(clanName, server.getClient(sender).getEmailAddress());
    }

    @Override
    public String getPeople(String clanName) throws RemoteException {
        return clanFunctions.getPeople(clanName);
    }

    @Override
    public boolean handleInvitation(InvitationType invite, String clanName, String emailAddress, String nameOfInvitee) throws RemoteException {
        return clanFunctions.handleInvitation(invite, clanName, emailAddress, nameOfInvitee);
    }

    @Override
    public boolean handleManagement(ManagementType manage, String clanName, String emailaddress) throws RemoteException {
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

    @Override
    public boolean setScore(String clanName, int score) throws RemoteException {
        PacketHighScore packet = new PacketHighScore(clanName, score);
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public void logout(UUID sender) throws RemoteException {
        PacketLogout packet = new PacketLogout(sender);
        server.send(packet);
    }

    @Override
    public boolean loginCreateUser(String username, String email) throws RemoteException {
        PacketLoginCreateNewUser packet = new PacketLoginCreateNewUser(username,email);
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public void gameObjectSetDirection(UUID sender, UUID objectId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void gameObjectSetPosition(UUID sender, UUID objectId, Vector2 position) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
