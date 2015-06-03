package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.packet.enums.InvitationType;
import ateamproject.kezuino.com.github.network.packet.enums.ManagementType;
import ateamproject.kezuino.com.github.network.packet.packets.*;
import ateamproject.kezuino.com.github.singleplayer.ItemType;
import ateamproject.kezuino.com.github.utility.game.Direction;
import com.badlogic.gdx.math.Vector2;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ServerBase extends UnicastRemoteObject implements IProtocolServer {

    protected transient Server server;

    public ServerBase(Server server) throws RemoteException {
        super(Registry.REGISTRY_PORT);
        if (server == null) {
            throw new IllegalArgumentException("Parameter server must not be null.");
        }
        this.server = server;
    }

    @Override
    public PacketLoginAuthenticate.ReturnData login(String email, String password, IProtocolClient client) throws RemoteException {
        PacketLoginAuthenticate packet = new PacketLoginAuthenticate(email, password, client);
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public void heartbeat(UUID client) throws RemoteException {
        server.send(new PacketHeartbeat(client));
    }

    @Override
    public UUID createLobby(UUID sender, String lobbyName, String clanname) throws RemoteException {
        PacketCreateLobby packet = new PacketCreateLobby(lobbyName, clanname, sender);
        server.send(packet);
        
        PacketLobbiesChanged tmp = new PacketLobbiesChanged(this.server.getClients().stream().map(info -> info.getPrivateId()).toArray(UUID[]::new));
        server.send(tmp);
        return packet.getResult();
    }

    @Override
    public List<PacketGetLobbies.GetLobbiesData> getLobbies(UUID sender, boolean isClanGame) throws RemoteException {
        PacketGetLobbies packet = new PacketGetLobbies(isClanGame, sender);
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public PacketJoinLobby.PacketJoinLobbyData joinLobby(UUID sender, UUID lobbyId) throws RemoteException {
        PacketJoinLobby packet = new PacketJoinLobby(sender, lobbyId);
        server.send(packet);
        
        PacketLobbiesChanged tmp = new PacketLobbiesChanged(this.server.getClients().stream().map(info -> info.getPrivateId()).toArray(UUID[]::new));
        server.send(tmp);
        return packet.getResult();
    }

    @Override
    public boolean leaveLobby(UUID sender) throws RemoteException {
        PacketLeaveLobby packet = new PacketLeaveLobby(sender);
        server.send(packet);
        
        PacketLobbiesChanged tmp = new PacketLobbiesChanged(this.server.getClients().stream().map(info -> info.getPrivateId()).toArray(UUID[]::new));
        server.send(tmp);
        
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
        PacketFillTable packet = new PacketFillTable(emailadres);
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public boolean createClan(UUID sender, String clanName) throws RemoteException {
        PacketCreateClan packet = new PacketCreateClan(sender, clanName);
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public InvitationType clanGetInvitation(UUID sender, String clanName) throws RemoteException {
        PacketGetInvitation packet = new PacketGetInvitation(sender, clanName);
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public ManagementType getManagement(UUID sender, String clanName) throws RemoteException {
        PacketGetManagement packet = new PacketGetManagement(clanName, sender);
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public String getPeople(String clanName) throws RemoteException {
        PacketGetPeople packet = new PacketGetPeople(clanName);
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public boolean handleInvitation(InvitationType invite, String clanName, String emailAddress, String nameOfInvitee) throws RemoteException {
        PacketHandleInvitation packet = new PacketHandleInvitation(invite, clanName, emailAddress, nameOfInvitee);
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public boolean handleManagement(ManagementType manage, String clanName, String emailaddress) throws RemoteException {
        PacketHandleManagement packet = new PacketHandleManagement(manage, clanName, emailaddress);
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public String getUsername(String emailaddress) throws RemoteException {
        PacketGetUsername packet = new PacketGetUsername(emailaddress);
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public String getEmail(UUID Sender) throws RemoteException {
        PacketGetEmail packet = new PacketGetEmail(Sender);
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public ArrayList<String> getClans(UUID Sender) throws RemoteException {
        PacketGetClans packet = new PacketGetClans(Sender);
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public void setClans(UUID Sender) throws RemoteException {
        PacketReloadClans packet = new PacketReloadClans(Sender);
        server.send(packet);
    }

    @Override
    public boolean setUsername(String name, String emailaddress, UUID sender) throws RemoteException {
        PacketSetUsername packet = new PacketSetUsername(name, emailaddress, sender);
        server.send(packet);
        return packet.getResult();
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
    public void launchGame(UUID sender) throws RemoteException {
        PacketLaunchGame packet = new PacketLaunchGame(false, sender);
        server.send(packet);
    }

    @Override
    public List<PacketGetGameClients.Data> getGameClients(UUID sender) throws RemoteException {
        PacketGetGameClients packet = new PacketGetGameClients(sender);
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public void setLobbyDetails(UUID sender, PacketLobbySetDetails.Data data) throws RemoteException {
        PacketLobbySetDetails packet = new PacketLobbySetDetails(data, sender);
        server.send(packet);
    }

    @Override
    public PacketLobbySetDetails.Data getLobbyDetails(UUID sender) throws RemoteException {
        PacketLobbyGetDetails packet = new PacketLobbyGetDetails();
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public void setLoadStatus(UUID sender, PacketSetLoadStatus.LoadStatus status) throws RemoteException {
        PacketSetLoadStatus packet = new PacketSetLoadStatus(status, sender);
        server.send(packet);
    }

    @Override
    public void setLoadStatus(UUID sender, PacketSetLoadStatus.LoadStatus status, int progress, int maxProgress) throws RemoteException {
        PacketSetLoadStatus packet = new PacketSetLoadStatus(status, progress, maxProgress, sender);
        server.send(packet);
    }

    @Override
    public boolean loginCreateUser(UUID sender, String username, String email) throws RemoteException {
        PacketLoginCreateNewUser packet = new PacketLoginCreateNewUser(username, email, sender);
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public void playerSetDirection(UUID sender, UUID objectId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void playerSetPosition(UUID sender, UUID objectId, Vector2 position) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void createObject(UUID sender, String type, Vector2 position, Direction direction, float speed, UUID newObjectId, int color, int index) throws RemoteException {
        PacketCreateGameObject packet = new PacketCreateGameObject(type, position, direction, speed, newObjectId, color, sender);
        packet.setIndex(index);
        server.send(packet);
    }

    @Override
    public void createItem(UUID sender, UUID itemId, ItemType type, Vector2 position) throws RemoteException {
        PacketCreateItem packet = new PacketCreateItem(itemId, type, position, sender);
        server.send(packet);
    }

    @Override
    public ArrayList<String> getKickInformation(UUID sender) throws RemoteException {
        PacketGetKickInformation packet = new PacketGetKickInformation(sender);
        server.send(packet);
        return packet.getResult();
    }

    @Override
    public void setKickInformation(UUID getPersonToVoteFor) throws RemoteException {
        PacketSetKickInformation packet = new PacketSetKickInformation(getPersonToVoteFor);
        server.send(packet);
    }

    @Override
    public void shootProjectile(UUID sender) throws RemoteException {
        PacketShootProjectile packet = new PacketShootProjectile(sender);
        server.send(packet);
    }

    @Override
    public Map<UUID, String> getLobbyMembers(UUID Lobbyid) throws RemoteException {
        PacketLobbyMembers packet = new PacketLobbyMembers(Lobbyid);
        server.send(packet);
        return packet.getResult();
    }
}
