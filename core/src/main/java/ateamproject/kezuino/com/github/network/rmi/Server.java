package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.Game;
import ateamproject.kezuino.com.github.network.IClientInfo;
import ateamproject.kezuino.com.github.network.mail.MailAccount;
import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketManager;
import ateamproject.kezuino.com.github.network.packet.packets.*;
import ateamproject.kezuino.com.github.render.screens.ClanManagementScreen;
import ateamproject.kezuino.com.github.singleplayer.ClanFunctions;
import ateamproject.kezuino.com.github.utility.io.Database;

import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends ateamproject.kezuino.com.github.network.Server<ClientInfo> {

    private static Server instance;
    protected ServerBase rmi;
    protected PacketManager packets;
    private ClanFunctions clanFunctions = ClanFunctions.getInstance();

    public Server() throws RemoteException {
        super();
        rmi = new ServerBase(this);
        packets = new PacketManager();
    }

    public static Server getInstance() throws RemoteException {
        if (instance == null) {
            instance = new Server();
        }

        return instance;
    }

    @Override
    public void update() {
        for (IClientInfo c : getClients()) {
            if (c.getSecondsInactive() >= clientTimeout) {
                removeClient(c.getPrivateId());
                System.out.printf("Client %s timed out.%n", c.getPrivateId().toString());
            }
        }
    }

    public ServerBase getRmi() {
        return rmi;
    }

    @Override
    public void start() {
        System.out.println("Starting server..");

        try {
            registerPackets();

            System.out.println(Registry.REGISTRY_PORT);
            Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            registry.bind("server", rmi);

        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(ServerBase.class.getName()).log(Level.SEVERE, ex.getMessage());
        }

        System.out.println("Server started");
    }

    @Override
    public void stop() {
        super.stop();

        try {
            send(new PacketKick(PacketKick.KickReasonType.QUIT, "Server stopped"));

            unregisterPackets();

            UnicastRemoteObject.unexportObject(rmi, true);

            System.out.println("Server stopped");
        } catch (NoSuchObjectException ex) {
            System.out.println(ex.getMessage());

            Logger.getLogger(ServerBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Executes the given {@link Packet} based on the registered function or
     * action. For RMI, the packet executes a RMI function directly.
     *
     * @param packet {@link Packet} to send to the {@link Client}.
     */
    @Override
    public void send(Packet packet) {
        packets.execute(packet);
    }

    @Override
    public void registerPackets() {
        packets.registerFunc(PacketKick.class, (packet) -> {
            switch (packet.getReasonType()) {
                case LOBBY:
                    // Drop all receivers.
                    for (UUID receiver : packet.getReceivers()) {
                        try {
                            getClient(receiver).getRmi().drop(packet.getReason());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                case QUIT:

                    return true;
            }

            return false;
        });

        packets.registerFunc(PacketCreateClan.class, (packet) -> {
            System.out.print("Create clan packet received");
            return clanFunctions.createClan(packet.getClanName(), getClient(packet.getSender()).getEmailAddress());
        });

        packets.registerFunc(PacketGetLobbies.class, (packet -> {
            List<PacketGetLobbies.GetLobbiesData> result = new ArrayList<>();
            for (Game game : getGames()) {
                result.add(new PacketGetLobbies.GetLobbiesData(game.getName(),
                        game.getId(),
                        game.getClients().size(),
                        "TODO NAME"));
            }
            return result;
        }));

        packets.registerFunc(PacketFillTable.class, (packet) -> clanFunctions.fillTable(packet.getEmailadres()));

        packets.registerFunc(PacketGetEmail.class, (packet) -> clanFunctions.getEmail(getClient(packet.getSender()).getUsername()));

        packets.registerFunc(PacketGetInvitation.class, (packet) -> clanFunctions.getInvitation(getClient(packet.getSender()).getEmailAddress(), packet
                .getClanName()));

        packets.registerFunc(PacketGetManagement.class, (packet) -> clanFunctions.getManagement(packet.getClanName(), 
                getClient(packet.getSender()).getEmailAddress()));

        packets.registerFunc(PacketGetPeople.class, (packet) -> clanFunctions.getPeople(packet.getClanName()));

        packets.registerFunc(PacketGetUsername.class, (packet) -> clanFunctions.getUsername(packet.getEmailadres()));

        packets.registerFunc(PacketHandleInvitation.class, (packet) -> clanFunctions.handleInvitation(packet.getInvite(), packet
                .getClanName(), packet.getEmailadres(), packet
                .getNameOfInvitee()));

        packets.registerFunc(PacketHandleManagement.class, (packet) -> clanFunctions.handleManagement(packet.getManage(), packet
                .getClanName(), packet.getEmailadres()));

        packets.registerFunc(PacketSetUsername.class, (packet) -> clanFunctions.setUsername(packet.getName(), packet.getEmailaddress()));

        packets.registerFunc(PacketLoginAuthenticate.class, (packet) -> {
            System.out.print("Login request received for account: " + packet.getEmailAddress());

            if (MailAccount.isValid(packet.getEmailAddress(), packet.getPassword())) {
                // Register client on server.
                ClientInfo client = new ClientInfo((IProtocolClient) packet.getConnectObject());
                client.setEmailAddress(packet.getEmailAddress());
                clients.put(client.getPrivateId(), client);
                System.out.println(" .. login accepted. " + clients.size() + " clients total.");

                // Get username from database.
                try (ResultSet set = Database.getInstance()
                        .query("SELECT Name FROM account WHERE Email = ?", packet.getEmailAddress())) {
                    if (set.isBeforeFirst()) {
                        set.next();
                        client.setUsername(set.getString(1));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // Tell client what its id is.
                PacketLoginAuthenticate.ReturnData packetLoginAuthenticateData = 
                        new PacketLoginAuthenticate.ReturnData(client.getUsername(), client.getEmailAddress(), client.getPrivateId());
                return packetLoginAuthenticateData;
            }

            System.out.println(" .. login credentials not valid.");
            return null;
        });

        packets.registerAction(PacketLogout.class, packet -> {
            removeClient(packet.getSender());
            System.out.printf("Client %s logged out.%n", packet.getSender());
        });

        packets.registerFunc(PacketHighScore.class, (packet) -> {
            try {
                ResultSet resultSet = Database.getInstance()
                        .query("SELECT `Score` FROM `clan` WHERE Name = ?", packet
                                .getClanName());
                resultSet.next();
                int id = resultSet.getInt("Score");

                if (id > packet.getScore()) {
                    return Database.getInstance()
                            .update("UPDATE `clan` SET `Score` = ? WHERE `Name` = ?", packet
                                    .getScore(), packet.getClanName()) > -1;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClanManagementScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        });

        packets.registerFunc(PacketLoginCreateNewUser.class, (packet) -> {
            System.out.println("Trying to create following user in database: " + packet.getUsername());
            ResultSet resultSet = null;
            int count = 0;

            try {
                resultSet = Database.getInstance()
                        .query("SELECT COUNT(*) as amount FROM account WHERE Name = ?", packet.getUsername());
                resultSet.next();
                count = resultSet.getInt("amount");

                if (count == 1) {
                    System.out.println("Following user does already exists: " + packet.getUsername());
                } else {
                    int result = Database.getInstance()
                            .update("INSERT INTO account(Name,Email) VALUES(?,?)", packet.getUsername(), packet
                                    .getEmail());
                    if (result > 0) {
                        System.out.println("Adding following user to database: " + packet.getUsername());
                        getClient(packet.getSender()).setUsername(packet.getUsername());
                        getClient(packet.getSender()).setEmailAddress(packet.getEmail());
                        return true;
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

            return false;
        });

        packets.registerFunc(PacketLoginUserExists.class, (packet) -> {
            System.out.println("Checking if the following user exists: " + packet.getEmail());
            ResultSet resultSet = null;
            int count = 0;

            try {
                resultSet = Database.getInstance()
                        .query("SELECT COUNT(*) as amount FROM account WHERE Email = ?", packet.getEmail());
                resultSet.next();
                count = resultSet.getInt("amount");
            } catch (SQLException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

            return count == 1;
        });


        packets.registerAction(PacketHeartbeat.class, packet -> {
            if (packet.getSender() == null) return;
            IClientInfo client = getClient(packet.getSender());
            if (client != null) client.resetSecondsActive();
        });

        packets.registerFunc(PacketCreateLobby.class, packet -> {
            Game newGame = new Game(packet.getLobbyname(), packet.getSender());
            addGame(newGame);
            getClient(packet.getSender()).setGame(newGame);

            System.out.println("Lobby: " + newGame.getName() + " - id " + newGame.getId() + " CREATED !");
            return newGame.getId();
        });

        packets.registerFunc(PacketLeaveLobby.class, packet -> {
            Game lobby = getGameFromClientId(packet.getSender());
            if (lobby == null) return false;

            if (packet.getSender().equals(lobby.getHostId())) {
                // Remove everyone from the lobby.
                removeGame(lobby.getId());
            } else {
                // Remove the requesting client only.
                lobby.getClients().remove(packet.getSender());
                getClient(packet.getSender()).setGame(null);
            }
            return true;
        });

        packets.registerFunc(PacketJoinLobby.class, packet -> {
            Game lobby = getGame(packet.getLobbyId());
            if (lobby == null) return false;

            // Get all clients currently in the game.
            PacketJoinLobby.PacketJoinLobbyData data = new PacketJoinLobby.PacketJoinLobbyData();
            data.lobbyName = lobby.getName();

            for (UUID clientId : lobby.getClients()) {
                IClientInfo c = getClient(clientId);
                data.members.put(c.getPublicId(), c.getUsername());
            }

            // Add new client to game.
            lobby.getClients().add(packet.getSender());
            IClientInfo client = getClient(packet.getSender());
            client.setGame(lobby);

            // Notify other users that someone joined the lobby (excluding itself).
            PacketClientJoined p = new PacketClientJoined(client.getPublicId(), client.getUsername());
            packet.setReceivers(lobby.getClients()
                    .stream()
                    .filter(id -> !id.equals(client.getPrivateId())).toArray(UUID[]::new));
            send(p);

            return data;
        });

        packets.registerAction(PacketClientJoined.class, packet -> {
            for (UUID id : packet.getReceivers()) {
                ClientInfo client = getClient(id);
                try {
                    client.getRmi().clientJoined(packet.getId(), packet.getUsername());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void unregisterPackets() {
        packets.unregisterAll();
    }
}
