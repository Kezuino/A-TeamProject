package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.Game;
import ateamproject.kezuino.com.github.network.IClientInfo;
import ateamproject.kezuino.com.github.network.mail.MailAccount;
import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.packets.*;
import ateamproject.kezuino.com.github.render.screens.ClanManagementScreen;
import ateamproject.kezuino.com.github.render.screens.LobbyListScreen;
import ateamproject.kezuino.com.github.render.screens.LobbyScreen;
import ateamproject.kezuino.com.github.singleplayer.ClanFunctions;
import ateamproject.kezuino.com.github.singleplayer.Pactale;
import ateamproject.kezuino.com.github.utility.io.Database;
import com.badlogic.gdx.graphics.Color;

import java.rmi.AlreadyBoundException;
import java.rmi.ConnectException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Server extends ateamproject.kezuino.com.github.network.Server<ClientInfo> {

    private static Server instance;
    protected ServerBase rmi;
    private final ClanFunctions clanFunctions;

    public Server() throws RemoteException {
        super();
        clanFunctions = ClanFunctions.getInstance();
        rmi = new ServerBase(this);
    }

    public static Server getInstance() throws RemoteException {
        if (instance == null) {
            instance = new Server();
        }

        return instance;
    }

    public IProtocolClient[] getRmiClientsFromGameId(UUID gameId) {
        Game game = getGame(gameId);
        if (game == null) {
            return null;
        }

        return game.getClients().stream().map(id -> getClient(id).getRmi()).toArray(IProtocolClient[]::new);
    }

    @Override
    public void update() {
        // Handle heartbeat messages.
        for (IClientInfo c : getClients()) {
            if (c.getSecondsInactive() >= clientTimeout) {
                PacketKick packet = new PacketKick(PacketKick.KickReasonType.QUIT, "Client lost connection.", c.getPrivateId());
                send(packet);
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

            Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            registry.bind("server", rmi);

        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(ServerBase.class.getName()).log(Level.SEVERE, ex.getMessage());
        }

        System.out.printf("Server started on port: %d%n", Registry.REGISTRY_PORT);
    }

    @Override
    public void stop() {
        super.stop();

        send(new PacketKick(PacketKick.KickReasonType.QUIT, "Server stopped.", null, getClients().stream().map(c -> c.getPublicId()).toArray(UUID[]::new)));

        unregisterPackets();

        // Unexport server from RMI.
        try {
            UnicastRemoteObject.unexportObject(rmi, true);
        } catch (NoSuchObjectException ignored) {
        }

        System.out.println("Server stopped.");
    }

    @Override
    public void registerPackets() {
        packets.registerFunc(PacketKick.class, (packet) -> {
            if (packet.getSender() != null) {
                // Sender wants to kick receiver.
                if (packet.getReceivers().length > 0) {
                    // TODO: Check if sender is allowed to kick the receivers (is host of game).
                    PacketKick kick = new PacketKick(packet.getReasonType(), packet.getReason(), null, packet.getReceivers());
                    send(kick);
                } else {
                    // Sender wants to kick itself.
                    ClientInfo client = getClient(packet.getSender());
                    PacketKick kick = new PacketKick(packet.getReasonType(), packet.getReason(), null, client.getPublicId());
                    send(kick);
                }
            } else {
                Stream.of(packet.getReceivers()).forEach(id -> {
                    ClientInfo client = getClientFromPublic(id);

                    switch (packet.getReasonType()) {
                        case QUIT:
                            // Disconnect client.
                            this.removeClient(client.getPrivateId());

                            // Fall-through (also need to remove game).
                        case GAME:
                            // Remove client instance from relate game.
                            Game game = client.getGame();
                            if (game != null) {
                                if (client.getPrivateId().equals(game.getHostId())) {
                                    List<ClientInfo> gameClients = Stream.of(game.getClientsAsArray()).map(this::getClient).collect(Collectors.toList());

                                    // Kick everyone.
                                    removeGame(game.getId());

                                    // Notify.
                                    for (ClientInfo gameClient : gameClients) {
                                        try {
                                            if (gameClient == null) continue;
                                            gameClient.getRmi().drop(packet.getReasonType(), packet.getReason());
                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else {
                                    // Kick self only.
                                    game.removeClient(client.getPrivateId());

                                    // Notify.
                                    try {
                                        client.getRmi().drop(packet.getReasonType(), packet.getReason());
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }

                                    PacketScreenUpdate tmp = new PacketScreenUpdate(LobbyScreen.class, null, game.getClients().stream().map(cid -> getClient(cid).getPublicId()).toArray(UUID[]::new));
                                    send(tmp);
                                }
                            }
                            break;
                    }
                });

                // Update screens.
                PacketScreenUpdate tmp = new PacketScreenUpdate(LobbyListScreen.class, null, this.getClients().stream().map(IClientInfo::getPublicId).toArray(UUID[]::new));
                send(tmp);
            }
            return true;
        });

        packets.registerAction(PacketClientJoined.class, packet -> {
            for (UUID id : packet.getReceivers()) {
                ClientInfo client = getClient(id);
                try {
                    client.getRmi().clientJoined(packet.getJoinenId(), packet.getUsername());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        packets.registerFunc(PacketCreateClan.class, (packet) -> clanFunctions.createClan(packet.getClanName(), getClient(packet.getSender()).getEmailAddress()));

        packets.registerFunc(PacketGetLobbies.class, (packet -> {
            List<PacketGetLobbies.GetLobbiesData> result = new ArrayList<>();
            if (packet.isClanGame()) {
                List<String> clans = getClient(packet.getSender()).getClans();
                if (!clans.isEmpty()) {
                    for (String clan : clans) {
                        for (Game game : getGamesAsList()) {
                            if (game.getClanName().equals(clan) && !game.isInGame()) {
                                result.add(new PacketGetLobbies.GetLobbiesData(game.getName(),
                                                                               game.getId(),
                                                                               game.getClients().size(),
                                                                               getClient(game.getHostId()).getUsername()));
                            }
                        }
                    }
                }
            } else {
                for (Game game : getGamesAsList()) {
                    if (game.getClanName() == null && !game.isInGame()) {
                        result.add(new PacketGetLobbies.GetLobbiesData(game.getName(),
                                                                       game.getId(),
                                                                       game.getClients().size(),
                                                                       getClient(game.getHostId()).getUsername()));
                    }
                }
            }
            return result;
        }));

        packets.registerAction(PacketScreenUpdate.class, packet -> {
            for (UUID id : packet.getReceivers()) {
                ClientInfo client = getClientFromPublic(id);
                try {
                    client.getRmi().screenRefresh(packet.getScreenClass());
                } catch (ConnectException e) {
                    removeClient(client.getPrivateId());
                } catch (RemoteException e) {
                    System.out.println("Failed to send screen update to client: " + id.toString());
                }
            }
        });

        packets.registerFunc(PacketGetClans.class, (p) -> getClient(p.getSender()).getClans());

        packets.registerAction(PacketReloadClans.class, (p) -> getClient(p.getSender()).setClans(clanFunctions.getClansByUserName(getClient(p.getSender()).getUsername())));
        
        packets.registerFunc(PacketFillTable.class, (packet) -> clanFunctions.getClansByEmailAddress(packet.getEmailadres()));

        packets.registerFunc(PacketGetEmail.class, (packet) -> clanFunctions.getEmail(getClient(packet.getSender()).getUsername()));

        packets.registerFunc(PacketGetInvitation.class, (packet) -> clanFunctions.getInvitation(packet.getClanName(), getClient(packet.getSender()).getEmailAddress()));

        packets.registerFunc(PacketGetManagement.class, (packet) -> clanFunctions.getManagement(packet.getClanName(), getClient(packet.getSender()).getEmailAddress()));

        packets.registerFunc(PacketGetPeople.class, (packet) -> clanFunctions.getPeople(packet.getClanName()));

        packets.registerFunc(PacketGetUsername.class, (packet) -> clanFunctions.getUsername(packet.getEmailadres()));

        packets.registerFunc(PacketHandleInvitation.class, (packet) -> clanFunctions.handleInvitation(packet.getInvite(), packet.getClanName(), packet.getEmailadres(), packet.getNameOfInvitee()));

        packets.registerFunc(PacketHandleManagement.class, (packet) -> clanFunctions.handleManagement(packet.getManage(), packet.getClanName(), packet.getEmailadres()));

        packets.registerFunc(PacketSetUsername.class, (packet) -> {
            boolean setUsername = clanFunctions.setUsername(packet.getName(), packet.getEmailaddress());
            if (setUsername) {
                getClient(packet.getSender()).setUsername(packet.getName());
            }
            return setUsername;
        });

        packets.registerFunc(PacketLoginAuthenticate.class, (packet) -> {
            String resultMessage = "Ingelogd.";

            if (true) {
            //if (MailAccount.isValid(packet.getEmailAddress(), packet.getPassword())) {
                // Register client on server.
                ClientInfo client = new ClientInfo((IProtocolClient) packet.getConnectObject());
                client.setEmailAddress(packet.getEmailAddress());
                if (!getClients().stream().anyMatch(c -> c.getEmailAddress().equals(packet.getEmailAddress()))) {
                    clients.put(client.getPrivateId(), client);

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
                    // fill clans
                    client.setClans(clanFunctions.getClansByUserName(client.getUsername()));
                } else {
                    resultMessage = "Dit account is al ingelogd.";
                }

                // Tell client what its id is.
                return new PacketLoginAuthenticate.ReturnData(client.getUsername(), client.getEmailAddress(), client.getPrivateId(), client
                        .getPublicId(), resultMessage, resultMessage.equals("Ingelogd."));
            }

            System.out.println(" .. login credentials not valid.");
            return new PacketLoginAuthenticate.ReturnData(null, null, null, null, null, resultMessage.equals("Invalide credentials."));
        });

        packets.registerAction(PacketLogout.class, packet -> {
            PacketKick pKick = new PacketKick(PacketKick.KickReasonType.QUIT, null, null, getClient(packet.getSender()).getPublicId());
            this.send(pKick);
            System.out.printf("Client %s logged out.%n", packet.getSender());
        });

        //return true if score is in top 10, false if not
        packets.registerFunc(PacketHighScore.class, (packet) -> {

            String clanName = getGameFromClientId(packet.getSender()).getClanName();

            if (clanName != null) {
                try {
                    ResultSet resultSet = Database.getInstance().query("SELECT `Score` FROM `clan` WHERE Name = ?", clanName);
                    resultSet.next();
                    int id = resultSet.getInt("Score");

                    if (id < packet.getScore()) {
                        Database.getInstance().update("UPDATE `clan` SET `Score` = ? WHERE `Name` = ?", packet.getScore(), clanName);
                    }

                    resultSet = Database.getInstance().query("SELECT Name FROM clan WHERE ? IN (SELECT Name FROM (SELECT Name FROM clan ORDER BY score DESC LIMIT 10) AS t)", clanName);//check if score is in top 10. weird query due the fact that limit could not be used in a inner query
                    return resultSet.first();//true if in top 10, false if not
                } catch (SQLException ex) {
                    Logger.getLogger(ClanManagementScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return false;
        });

        packets.registerFunc(PacketLoginCreateNewUser.class, (packet) -> {
            System.out.println(
                    "Trying to create following user in database: " + packet.getUsername());
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

            return count
                    == 1;
        });

        packets.registerAction(PacketHeartbeat.class, packet -> {
            if (packet.getSender()
                    == null) {
                return;
            }
            IClientInfo client = getClient(packet.getSender());
            if (client
                    != null) {
                client.resetSecondsActive();
            }
        });

        packets.registerFunc(PacketCreateLobby.class, packet -> {
            Game newGame = new Game(this, packet.getLobbyname(), packet.getClanname(), packet.getSender());

            addGame(newGame);

            getClient(packet.getSender()).setGame(newGame);
            return newGame.getId();
        });

        packets.registerFunc(PacketJoinLobby.class, packet -> {
            Game lobby = getGame(packet.getLobbyId());
            if (lobby
                    == null) {
                return null;
            }

            // Add new client to game.
            IClientInfo client = getClient(packet.getSender());
            if (client
                    == null) {
                System.out.printf("Cannot let NULL client: Join lobby %s%n", lobby);
                return null;
            }

            lobby.getClients()
                    .add(packet.getSender());
            client.setGame(lobby);

            // Get all clients currently in the game.
            PacketJoinLobby.PacketJoinLobbyData data = new PacketJoinLobby.PacketJoinLobbyData();

            data.setLobbyName(lobby.getName());
            data.setMap(lobby.getMap());

            for (UUID clientId
                    : lobby.getClients()) {
                IClientInfo c = getClient(clientId);
                data.getMembers().put(c.getPublicId(), c.getUsername());
            }

            // Notify other users that someone joined the lobby (excluding itself).
            PacketClientJoined p = new PacketClientJoined(client.getPublicId(), client.getUsername(), null);

            p.setReceivers(lobby.getClients()
                                   .stream()
                                   .filter(id -> !id.equals(client.getPrivateId())).toArray(UUID[]::new));
            send(p);

            return data;
        });

        packets.registerFunc(PacketLobbyMembers.class, packet -> {
                                 // return all current members in the lobby
                                 Game game = games.get(packet.getLobbyId());
                                 Map<UUID, String> curMembers = new HashMap<>();

                                 if (game
                                         == null) {
                                     return curMembers;
                                 }

                                 for (UUID lobby
                                         : games.get(packet.getLobbyId()).getClients()) {
                                     IClientInfo client = getClient(lobby);
                                     curMembers.put(client.getPublicId(), client.getUsername());
                                 }
                                 return curMembers;
                             }
        );

        packets.registerFunc(PacketGetKickInformation.class, packet -> {
            ArrayList<String> peoples = new ArrayList<>();

            System.out.println(
                    "sender:" + packet.getSender().toString());
            for (UUID personId
                    : getGameFromClientId(packet.getSender()).getClients()) {//for all clients in this game
                if (!personId.equals(packet.getSender()) && !getGameFromClientId(packet.getSender()).getClients().get(0).equals(personId)) {//if not sender and if not host == true
                    boolean hasVotedForCurrentPerson = false;
                    int amountOfVotesForCurrentPerson = 0;
                    int amountOfVotesNeededForKick = (int) Math.ceil((float) getGameFromClientId(packet.getSender()).getClients()
                            .size() / 2);

                    for (UUID[] voteCombination : getGameFromClientId(packet.getSender()).getVotes()) {//for all votecombination in this game
                        if (voteCombination[0].equals(packet.getSender()) && voteCombination[1].equals(personId)) {
                            hasVotedForCurrentPerson = true;
                            break;
                        }
                    }

                    for (UUID[] voteCombination : getGameFromClientId(packet.getSender()).getVotes()) {//for all votecombination in this game
                        if (voteCombination[1].equals(personId)) {
                            amountOfVotesForCurrentPerson++;
                        }
                    }

                    if (amountOfVotesForCurrentPerson >= amountOfVotesNeededForKick) {
                        PacketKick packetKick = new PacketKick(PacketKick.KickReasonType.GAME, "Uitgestemd", null, getClient(personId).getPublicId());
                        this.send(packetKick);
                    } else {
                        peoples.add(getClient(personId).getUsername() + " " + amountOfVotesForCurrentPerson + " " + amountOfVotesNeededForKick + " " + String
                                .valueOf(hasVotedForCurrentPerson + " " + personId));
                    }
                }
            }

            return peoples;
        });

        packets.registerAction(PacketSetKickInformation.class, packet -> {
            boolean hasVotedForSpecificPerson = false;

            for (UUID[] voteCombination
                    : getGameFromClientId(packet.getSender()).getVotes()) {//for all votecombination in this game
                if (voteCombination[0] == packet.getSender() && voteCombination[1] == packet.getPersonToVoteFor()) {
                    hasVotedForSpecificPerson = true;
                    break;
                }
            }

            if (!hasVotedForSpecificPerson) {
                getGameFromClientId(packet.getSender()).getVotes()
                        .add(new UUID[] {packet.getSender(), packet.getPersonToVoteFor()});
            }
        });

        packets.registerAction(PacketLobbySetDetails.class, packet -> {
            Game game = getGameFromClientId(packet.getSender());
            if (game
                    == null) {
                return;
            }

            String newName = packet.getData().getName();
            String newMap = packet.getData().getMap();
            if (newMap
                    != null && !newMap.isEmpty()) {
                game.setMap(newMap);
            }
            if (newName
                    != null && !newName.isEmpty()) {
                game.setName(newName);
            }

            // Notify all connected clients (but host) that the game details have changed.
            for (UUID uuid
                    : game.getClientsAsArray()) {
                if (game.getHostId().equals(uuid)) {
                    continue;
                }

                ClientInfo client = getClient(uuid);
                try {
                    client.getRmi().setLobbyDetails(packet.getData());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        packets.registerFunc(PacketLobbyGetDetails.class, packet -> {
            Game game = getGameFromClientId(packet.getSender());
            if (game
                    == null) {
                return null;
            }

            PacketLobbySetDetails.Data data = new PacketLobbySetDetails.Data();

            data.setName(game.getName());
            data.setMap(game.getMap());

            return data;
        });

        packets.registerAction(PacketLaunchGame.class, packet -> {
            Game game = getGameFromClientId(packet.getSender());
            if (game
                    == null) {
                System.out.println("Cannot launch game. The game was not found.");
                return;
            }

            // Increase level.
            game.setLevel(packet.getLevel() == -1 ? 1 : packet.getLevel());

            // Set the loading states of everyone to empty and notify everyone to start loading the map.
            for (UUID uuid
                    : game.getClients()) {
                ClientInfo client = getClient(uuid);
                client.setLoadStatus(PacketSetLoadStatus.LoadStatus.Empty);

                try {
                    client.getRmi().loadGame(game.getMap(), game.getHostId().equals(uuid), game.getClients().size(), game.getLevel(), packet.getScore());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        packets.registerAction(PacketCreateGameObject.class, packet -> {
            Game game = getGameFromClientId(packet.getSender());

            // This packet is only called for the connected clients excluding the host. Public ids will be shared here.
            if (packet.getTypeName()
                    .equalsIgnoreCase(Pactale.class
                                              .getSimpleName())) {
                packet.setId(getClient(game.getClientsAsArray()[packet.getIndex()]).getPublicId());
            }

            game.getLoadQueue()
                    .add(packet);
        });

        packets.registerAction(PacketCreateItem.class, packet -> {
            Game game = getGameFromClientId(packet.getSender());

            game.getLoadQueue()
                    .add(packet);
        });

        packets.registerAction(PacketRemoveItem.class, packet -> {
            Game game = getGameFromClientId(packet.getSender());
            if (game == null) {
                System.out.println("Could not remove item with unique id: " + packet.getItemId());
                return;
            }

            for (UUID receiver
                    : game.getClients()) {
                try {
                    ClientInfo client = getClient(receiver);
                    if (client != null) {
                        if (!receiver.equals(packet.getSender())) {
                            client.getRmi().removeItem(null, packet.getPlayer(), packet.getItemId(), packet.getItemType());
                        }
                        client.getRmi().changeScore(null, PacketScoreChanged.ManipulationType.INCREASE, packet.getItemType().getScore());
                    }
                } catch (RemoteException ignored) {
                    // One of the clients lost connection.
                }
            }
        });

        packets.registerAction(PacketScoreChanged.class, packet -> {
            Game game = getGameFromClientId(packet.getSender());
            if (game
                    == null) {
                System.out.println("Could not change score since game is not active");
                return;
            }

            //Calculate the score based on the level of the game. For every level above 1, add 5% more score.
            int score = packet.getChange();
            if (game.getLevel() > 1) {
                double factor = Math.pow(1.05, game.getLevel() - 1);
                score *= factor;
            }

            IProtocolClient[] receivers = game.getClients()
                    .stream()
                    .map(id -> getClient(id).getRmi())
                    .toArray(IProtocolClient[]::new);

            for (IProtocolClient receiver : receivers) {
                try {
                    receiver.changeScore(null, packet.getManipulationType(), score);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        packets.registerAction(PacketSetLoadStatus.class, packet -> {
            ClientInfo client = getClient(packet.getSender());

            client.setLoadStatus(packet.getStatus());

            // Find game with the player that sended the new load status.
            Game game = getGameFromClientId(packet.getSender());
            if (game
                    == null) {
                System.out.println(String.format("Error: Client '%s' tried to load a game while not in a lobby.", packet
                        .getSender()));
                return;
            }

            if (game.getClients()
                    .stream()
                    .map(this::getClient)
                    .allMatch(info -> info.getLoadStatus() == PacketSetLoadStatus.LoadStatus.ObjectsLoaded)) {

                // Launch the game (everyone is done loading).
                game.getClients().stream().map(id -> getClient(id).getRmi()).forEach(rmi -> {
                    try {
                        game.setInGame(true);
                        rmi.launchGame(true);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });

            } else if (game.getClients()
                    .stream()
                    .map(this::getClient)
                    .filter(info -> !info.getPrivateId().equals(game.getHostId()))
                    .allMatch(info -> info.getLoadStatus() == PacketSetLoadStatus.LoadStatus.MapLoaded) && getClient(game.getHostId()).getLoadStatus() == PacketSetLoadStatus.LoadStatus.ObjectsLoaded) {
                // Send all packets that were queued from the host to all the other clients (so, excluding the host).
                int objectsToSend = game.getLoadQueue().size();

                // Get all connected clients (excluding the host).
                IProtocolClient[] receivers = game.getClients()
                        .stream()
                        .filter(c -> !c.equals(game.getHostId()))
                        .map(id -> getClient(id).getRmi())
                        .toArray(IProtocolClient[]::new);

                // Tell clients that they should announce when they reached the right amount of objects created.
                for (IProtocolClient receiver : receivers) {
                    try {
                        receiver.requestCompleted("game_load_objects", objectsToSend);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

                // Unload all queued createObject packets to all connected clients of the lobby.
                while (!game.getLoadQueue().isEmpty()) {
                    // Remove one packet from queue.
                    Packet p = game.getLoadQueue().remove();

                    if (p instanceof PacketCreateGameObject) {
                        PacketCreateGameObject pObject = (PacketCreateGameObject) p;
                        // Send it to all receivers.
                        for (IProtocolClient receiver : receivers) {
                            try {
                                if (pObject.getTypeName().equalsIgnoreCase("pactale")) {
                                    receiver.createObject(null, pObject.getTypeName(), pObject.getPosition(), pObject
                                            .getDirection(), pObject
                                                                  .getSpeed(), pObject.getId(), Color.rgba8888(Game.SELECTABLE_COLORS[pObject.getIndex()]), pObject.getIndex());
                                } else {
                                    receiver.createObject(null, pObject.getTypeName(), pObject.getPosition(), pObject
                                            .getDirection(), pObject
                                                                  .getSpeed(), pObject.getId(), pObject.getColor(), pObject.getIndex());
                                }
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (p instanceof PacketCreateItem) {
                        PacketCreateItem pItem = (PacketCreateItem) p;
                        // Send it to all receivers.
                        for (IProtocolClient receiver : receivers) {
                            try {
                                receiver.createItem(null, pItem.getObjId(), pItem.getType(), pItem.getPosition());
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

        packets.registerAction(PacketShootProjectile.class, packet -> {
            Game game = getGameFromClientId(packet.getSender());
            IProtocolClient[] receivers = game.getClients()
                    .stream()
                    .filter(c -> !c.equals(packet.getSender()))
                    .map(id -> getClient(id).getRmi())
                    .toArray(IProtocolClient[]::new);

            for (IProtocolClient receiver : receivers) {
                try {
                    receiver.shootProjectile(packet.getExactPosition(), packet.getDirection(), getClient(packet.getSender()).getPublicId());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        packets.registerAction(PacketObjectMove.class, packet -> {
            Game game = getGameFromClientId(packet.getSender());
            if (game
                    == null) {
                System.out.println("Could not set direction for client: " + packet.getSender());
                return;
            }

            IProtocolClient[] receivers = game.getClients()
                    .stream()
                    .filter(c -> !c.equals(packet.getSender()))
                    .map(id -> getClient(id).getRmi())
                    .toArray(IProtocolClient[]::new);

            for (IProtocolClient receiver : receivers) {
                try {
                    receiver.objectSetDirection(packet.getSender(), packet.getObject(), packet.getFrom(), packet.getTo());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        packets.registerAction(PacketPlayerDied.class, packet -> {
            Game game = getGameFromClientId(packet.getSender());
            if (game
                    == null) {
                System.out.println("Could not set position for client: " + packet.getSender());
                return;
            }

            IProtocolClient[] receivers = game.getClients()
                    .stream()
                    .filter(c -> !c.equals(packet.getSender()))
                    .map(id -> getClient(id).getRmi())
                    .toArray(IProtocolClient[]::new);

            for (IProtocolClient receiver : receivers) {
                try {
                    receiver.playerDied(getClient(packet.getSender()).getPublicId());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        packets.registerFunc(PacketGetGameClients.class, packet -> {
            Game game = getGameFromClientId(packet.getSender());
            if (game
                    == null) {
                System.out.println("Game was not found for the sender.");
                return null;
            }
            List<PacketGetGameClients.Data> result = new ArrayList<>();
            int num = 0;
            for (UUID uuid
                    : game.getClients()) {
                result.add(new PacketGetGameClients.Data(num++, getClient(uuid).getPublicId(), game.getHostId().equals(uuid)));
            }
            return result;
        });

        packets.registerAction(PacketBalloonMessage.class, packet -> {
            if (packet.getSender()
                    == null) {
                throw new IllegalStateException("Sender must not be null.");
            }

            Game game = getGameFromClientId(packet.getSender());
            if (game
                    == null) {
                System.out.println("Game was not found for sender.");
                return;
            }

            IProtocolClient[] receivers = game.getClients()
                    .stream()
                    .filter(c -> !c.equals(packet.getSender()))
                    .map(id -> getClient(id).getRmi())
                    .toArray(IProtocolClient[]::new);

            for (IProtocolClient receiver : receivers) {
                try {
                    receiver.balloonMessage(null, packet.getTypeName(), packet.getPosition(), packet.getFollowTarget());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        packets.registerFunc(PacketGetHighscores.class, packet -> {
            LinkedHashMap<String, Integer> highscores = new LinkedHashMap<>();

            ResultSet result = Database.getInstance().query("SELECT Name,Score FROM clan ORDER BY Score DESC LIMIT 10");

            try {
                while (result.next()) {
                    highscores.put(result.getString(1), result.getInt(2));
                }
            } catch (SQLException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            return highscores;
        });
    }

    @Override

    public void unregisterPackets() {
        if (packets == null) {
            return;
        }
        packets.unregisterAll();
    }
}
