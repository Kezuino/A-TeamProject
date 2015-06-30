/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.packet.packets.*;
import ateamproject.kezuino.com.github.render.screens.*;
import ateamproject.kezuino.com.github.singleplayer.*;
import ateamproject.kezuino.com.github.singleplayer.Map;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import ateamproject.kezuino.com.github.utility.game.Animation;
import ateamproject.kezuino.com.github.utility.game.Direction;
import ateamproject.kezuino.com.github.utility.game.balloons.BalloonMessage;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Kez and Jules
 */
public class Client extends ateamproject.kezuino.com.github.network.Client {

    private static ateamproject.kezuino.com.github.network.rmi.Client instance;
    protected ClientBase rmi;
    protected Timer updateTimer;
    private String skin = "Skin1";

    public boolean isRunning() {
        return updateTimer != null;
    }

    protected Client() {
        super(null);
    }

    protected Client(Game game) {
        super(game);
    }

    /**
     * {@link ateamproject.kezuino.com.github.network.rmi.Client} instance.
     * Creates a new {@link ateamproject.kezuino.com.github.network.rmi.Client}
     * if it doesn't exist. Only use on client-side!
     *
     * @return {@link ateamproject.kezuino.com.github.network.rmi.Client}
     * instance. Creates a new
     * {@link ateamproject.kezuino.com.github.network.rmi.Client} if it doesn't
     * exist.
     * @throws RemoteException
     */
    public static ateamproject.kezuino.com.github.network.rmi.Client getInstance() {
        if (instance == null) {
            instance = new ateamproject.kezuino.com.github.network.rmi.Client();
        }

        return instance;
    }

    public ClientBase getRmi() {
        return rmi;
    }

    public String getSkin() {
        return this.skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    @Override
    public void start() {
        System.out.println("Client starting...");

        System.setProperty("pactales.client.serverobject", "server");
        try {
            rmi = new ClientBase(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            registerPackets();

            String rmiHost = System.getProperty("java.rmi.server.hostname");
            String rmiObject = System.getProperty("pactales.client.serverobject");

            this.rmi.setServer((IProtocolServer) Naming.lookup(String.format("//%s/%s", rmiHost, rmiObject)));

            updateTimer = new Timer();
            updateTimer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    // Update position constantly when in-game.
                    if (BaseScreen.getSession() != null && game.getScreen() instanceof GameScreen) {
                        try {
                            /*rmi.getServer()
                                    .playerSetPosition(getId(), BaseScreen.getSession()
                                            .getPlayer(getPublicId())
                                            .getExactPosition());*/
                        } catch (Exception ex) {
                            System.out.println("Error: Cannot set position, possibly not in game or offline.");
                        }
                    }
                }
            }, 0, 1);
            updateTimer.start();

            // Start updating.
            if (updateThread != null) {
                updateThread.interrupt();
            }
            updateThread = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        while (isUpdating) {
                            if (getId() != null) {
                                // Tell the server that we are still alive.
                                rmi.getServer().heartbeat(getId());
                            }
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException ignored) {
                                break;
                            }
                        }
                        Thread.sleep(100);
                    }
                } catch (RemoteException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
            updateThread.start();
        } catch (RemoteException | NotBoundException | MalformedURLException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("Client started.");
    }

    @Override
    public void close() {
        updateTimer.stop();
        updateTimer.clear();
        // Required for isRunning() to work.
        updateTimer = null;

        if (updateThread != null) {
            updateThread.interrupt();
        }

        try {
            this.rmi.getServer()
                    .kickClient(this.getId(), null, PacketKick.KickReasonType.QUIT, "Client disconnected.");
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            Timer.instance().stop();
            unregisterPackets();
        }
        super.close();
    }

    @Override
    public void registerPackets() {
        packets.registerFunc(PacketKick.class, packet -> {
            if (packet.getSender() == null) {
                new Dialog("Kicked", ((BaseScreen) game.getScreen()).getSkin()) {
                    {
                        text(packet.getReason());
                        button("Oke");

                        addListener(new ClickListener(0) {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                game.setScreen(new MainScreen(game));
                            }
                        });
                    }
                }.show(((BaseScreen) game.getScreen()).getStage());
                return true;
            } else {
                try {
                    if (packet.getReceivers().length > 0) {
                        for (UUID receiver : packet.getReceivers()) {
                            this.rmi.getServer().kickClient(packet.getSender(), receiver, packet.getReasonType(), packet.getReason());
                        }
                    } else {
                        this.rmi.getServer().kickClient(packet.getSender(), null, packet.getReasonType(), packet.getReason());
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
                return true;
            }
        });

        packets.registerFunc(PacketLoginAuthenticate.class, packet -> {
            PacketLoginAuthenticate.ReturnData data = null;
            try {
                data = getRmi().getServer().login(packet.getEmailAddress(), packet.getPassword(), getRmi());
            } catch (RemoteException e) {
                data = new PacketLoginAuthenticate.ReturnData(null, null, null, null, "Server is niet online.", false);
            }

            if (data != null) {
                if (data.getPrivateId() == null) {
                    this.setEmailaddress(null);
                    this.setUsername(null);
                    this.setId(null);
                    this.setPublicId(null);
                } else {
                    this.setEmailaddress(data.getEmailadress());
                    this.setUsername(data.getUsername());
                    this.setId(data.getPrivateId());
                    this.setPublicId(data.getPublicId());
                    System.out.printf("Logged in as: (Username: %s), (Email: %s), (Private: %s), (Public: %s)%n", data.getUsername(), data.getEmailadress(), data.getPrivateId(), data.getPublicId());
                }
            }

            return data;
        });

        packets.registerAction(PacketLogout.class, packet -> {

            try {
                getRmi().getServer().logout(getId());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        packets.registerFunc(PacketHighScore.class, (packet) -> {
            try {
                return getRmi().getServer().setScore(packet.getSender(),packet.getScore());
            } catch (RemoteException ex) {
                Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        });

        packets.registerFunc(PacketCreateLobby.class, (p) -> {

            try {
                return getRmi().getServer().createLobby(p.getSender(), p.getLobbyname(), p.getClanname());
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            return null;
        });

        packets.registerFunc(PacketGetLobbies.class, (p) -> {
            try {
                return getRmi().getServer().getLobbies(p.getSender(), p.isClanGame());
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            return null;
        });

        packets.registerAction(PacketScreenUpdate.class, packet -> {
            if (game.getScreen().getClass().equals(packet.getScreenClass())) {
                RefreshableScreen screen = (RefreshableScreen) game.getScreen();
                screen.refresh();
            }
        });

        packets.registerFunc(PacketGetClans.class, (p) -> {
            try {
                return getRmi().getServer().getClans(p.getSender());
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

            return null;
        });

        packets.registerAction(PacketReloadClans.class, (p) -> {
            // run methode op de serverbase .getclans
            try {
                getRmi().getServer().setClans(p.getSender());
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        });

        packets.registerFunc(PacketJoinLobby.class, (p) -> {
            try {
                return getRmi().getServer().joinLobby(p.getLobbyId(), p.getSender());
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            return null;
        });

        packets.registerFunc(PacketLoginCreateNewUser.class, (p) -> {
            try {
                return getRmi().getServer().loginCreateUser(p.getSender(), p.getUsername(), p.getEmail());
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

            return false;
        });

        packets.registerFunc(PacketLoginUserExists.class, (p) -> {
            try {
                return getRmi().getServer().doesUserExists(p.getEmail());
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

            return false;
        });

        packets.registerFunc(PacketGetKickInformation.class, (p) -> {
            try {
                return getRmi().getServer().getKickInformation(p.getSender());
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

            return false;
        });

        packets.registerFunc(PacketCreateClan.class, (p) -> {
            try {
                return getRmi().getServer().createClan(p.getSender(), p.getClanName());
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

            return false;
        });

        packets.registerFunc(PacketFillTable.class, (p) -> {
            try {
                return getRmi().getServer().clanFillTable(p.getEmailadres());
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

            return false;
        });

        packets.registerFunc(PacketGetEmail.class, (p) -> {
            try {
                return getRmi().getServer().getEmail(p.getSender());
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

            return false;
        });

        packets.registerFunc(PacketGetInvitation.class, (p) -> {
            try {
                return getRmi().getServer().clanGetInvitation(p.getSender(), p.getClanName());
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

            return false;
        });

        packets.registerFunc(PacketGetManagement.class, (p) -> {
            try {
                return getRmi().getServer().getManagement(p.getSender(), p.getClanName());
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

            return false;
        });

        packets.registerFunc(PacketGetPeople.class, (p) -> {
            try {
                return getRmi().getServer().getPeople(p.getClanName());
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

            return false;
        });

        packets.registerFunc(PacketGetUsername.class, (p) -> {
            try {
                return getRmi().getServer().getUsername(p.getEmailadres());
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

            return false;
        });

        packets.registerFunc(PacketHandleInvitation.class, (p) -> {
            try {
                return getRmi().getServer()
                        .handleInvitation(p.getInvite(), p.getClanName(), p.getEmailadres(), p.getNameOfInvitee());
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

            return false;
        });

        packets.registerFunc(PacketHandleManagement.class, (p) -> {
            try {
                return getRmi().getServer().handleManagement(p.getManage(), p.getClanName(), p.getEmailadres());
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

            return false;
        });

        packets.registerFunc(PacketSetUsername.class, (p) -> {
            try {
                return getRmi().getServer().setUsername(p.getName(), p.getEmailaddress(), p.getSender());
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

            return false;
        });

        packets.registerAction(PacketSetKickInformation.class, packet -> {
            try {
                getRmi().getServer().setKickInformation(packet.getSender(), packet.getPersonToVoteFor());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        packets.registerAction(PacketClientJoined.class, p -> {
            if (game.getScreen() instanceof LobbyScreen) {
                LobbyScreen t = (LobbyScreen) game.getScreen();
                t.addMember(p.getJoinenId(), p.getUsername());
            }
        });

        packets.registerAction(PacketClientLeft.class, p -> {
            if (game.getScreen() instanceof LobbyScreen) {
                LobbyScreen t = (LobbyScreen) game.getScreen();
                t.removeMember(p.getClientThatLeft());
            }

            System.out.println("Client left: " + p.getUsername());
        });

        packets.registerFunc(PacketLobbyMembers.class, packet -> {
            try {
                // return all current members in the lobby
                return getRmi().getServer().getLobbyMembers(packet.getLobbyId());

            } catch (RemoteException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        });

        packets.registerAction(PacketLaunchGame.class, p -> {
            if (p.getSender() != null) {
                // Request came from client..
                try {
                    rmi.getServer().launchGame(p.getSender(), p.getLevel(), p.getScore());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                // Request came from server..
                if (p.isPaused()) {
                    Gdx.app.postRunnable(() -> {
                        // Sync data of pactales already send to connected clients with host.
                        try {
                            List<PacketGetGameClients.Data> data = getRmi().getServer().getGameClients(Client.getInstance().getId());

                            for (PacketGetGameClients.Data d : data) {
                                Pactale player = BaseScreen.getSession().getPlayer(d.getIndex());

                                player.setId(d.getPublicId());
                                player.setColor(ateamproject.kezuino.com.github.network.Game.SELECTABLE_COLORS[player.getPlayerIndex()]);
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }

                        game.setScreen(new GameScreen(game));
                        ((GameScreen) game.getScreen()).start();
                    });
                } else {
                    // Request came from server.. resume game.
                    Gdx.app.postRunnable(() -> BaseScreen.getSession().resume());
                }
            }
        });

        packets.registerAction(PacketCreateGameObject.class, p -> {
            GameSession session = BaseScreen.getSession();
            if (session == null) {
                System.out.printf("Tried to create object: '%s' but no GameSession was set.%n", p.getTypeName());
                return;
            }

            GameObject object = null;
            try {
                String className = GameObject.class.getName();
                className = className.substring(0, className.lastIndexOf('.'));
                object = (GameObject) Class.forName(className + '.' + p.getTypeName())
                        .newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (object == null) {
                throw new IllegalStateException(String.format("Cannot create GameObject of type: '%s'", p.getTypeName()));
            }
            object.setId(p.getId());
            object.setStartingPosition(p.getPosition());
            object.setDirection(p.getDirection());
            object.setExactPosition(p.getPosition());
            object.setMap(session.getMap());
            object.setMovementSpeed(p.getSpeed());

            Color color = Color.WHITE.cpy();
            Color.rgba8888ToColor(color, p.getColor());
            object.setColor(color);

            if (object instanceof Pactale) {
                Pactale pactale = (Pactale) object;
                pactale.setPlayerIndex(p.getIndex());
                final GameObject finalObject = object;
                Gdx.app.postRunnable(() -> finalObject.setAnimation(new Animation(true, Assets.getTexture(finalObject.getClass()
                        .getSimpleName()
                        .toLowerCase() + ".png"))));
            } else if (object instanceof Enemy) {
                final Enemy enemy = (Enemy) object;

                // Only host should generate enemy paths. Clients will receive them from the host.
                enemy.setDisablePathfinding(!Client.getInstance().isHost());
                Gdx.app.postRunnable(() -> enemy.setAnimation(new Animation(Assets.getTexture(enemy.getClass()
                        .getSimpleName()
                        .toLowerCase() + ".png"))));
            }

            session.getMap().addGameObject(object);
            session.setObjectsLoaded(session.getObjectsLoaded() + 1);

            System.out.printf("Loaded (%s): %d/%d%n", object.getClass()
                    .getSimpleName(), session.getObjectsLoaded(), session.getObjectsToLoad());

            if (session.getObjectsToLoad() == session.getObjectsLoaded()) {
                send(new PacketSetLoadStatus(PacketSetLoadStatus.LoadStatus.ObjectsLoaded, null));
            }
        });

        packets.registerAction(PacketSetAIPath.class, packet -> {
            if (packet.getSender() != null) {
                try {
                    getRmi().getServer().setAIPath(packet.getSender(), packet.getObjId(), packet.getPosition(), packet.getPath());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                // Server sended this.
                Optional.of(BaseScreen.getSession().findObject(packet.getObjId())).ifPresent(obj -> {
                    if (!(obj instanceof Enemy)) {
                        return;
                    }
                    Enemy enemy = (Enemy) obj;
                    Gdx.app.postRunnable(() -> {
                        enemy.setExactPosition(packet.getPosition());
                        enemy.setPath(packet.getPath());
                    });
                });
            }
        });

        packets.registerAction(PacketCreateItem.class, packet -> {
            GameSession session = BaseScreen.getSession();
            if (session == null) {
                System.out.printf("Tried to create object: '%s' but no GameSession was set.%n", "item");
                return;
            }

            // Create item.
            Item item = new Item(packet.getType().toString(), packet.getPosition(), packet.getType());
            item.setId(packet.getObjId());
            item.setMap(session.getMap());

            Gdx.app.postRunnable(() -> item.setTexture(new TextureRegion(Assets.getTexture(item.getItemType()
                    .name()
                    .toLowerCase() + ".png"))));
            session.getMap().getNode(item.getExactPosition()).setItem(item);

            // Update load status.
            session.setObjectsLoaded(session.getObjectsLoaded() + 1);
            System.out.printf("Loaded (%s): %d/%d%n", item.getClass()
                    .getSimpleName(), session.getObjectsLoaded(), session.getObjectsToLoad());

            if (session.getObjectsToLoad() == session.getObjectsLoaded()) {
                send(new PacketSetLoadStatus(PacketSetLoadStatus.LoadStatus.ObjectsLoaded, null));
            }
        });

        packets.registerAction(PacketLobbySetDetails.class, p -> {
            try {
                rmi.getServer().setLobbyDetails(p.getSender(), p.getData());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        packets.registerFunc(PacketLobbyGetDetails.class, p -> {
            PacketLobbySetDetails.Data data = p.getResult();

            // TODO: Update lobby screen with new information.
            if (data.getName() != null) {
                System.out.println("New lobby name: " + data.getName());
            }
            if (data.getMap() != null) {
                System.out.println("New lobby map: " + data.getMap());
            }

            return data;
        });

        packets.registerAction(PacketLoadGame.class, p -> {
            // Set this session as the new main session.
            GameSession session = new GameSession(p.getLevel(), p.getScore());
            BaseScreen.setSession(session);
            MapLoader loader = Map.getLoader(session, p.getMap(), p.getLevel());

            EnumSet<MapLoader.MapObjectTypes> excluded = null;
            if (p.isMaster()) {
                // Load everything and synchronize with server.
                excluded = EnumSet.noneOf(MapLoader.MapObjectTypes.class);
                loader.addConsumer(Enemy.class, obj -> {
                    try {
                        getRmi().getServer()
                                .createObject(p.getSender(), Enemy.class.getSimpleName(), obj.getExactPosition(), obj.getDirection(), obj
                                        .getMovementSpeed(), obj.getId(), Color.argb8888(obj.getColor()), 0);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
                loader.addConsumer(Pactale.class, obj -> {
                    if (obj.getPlayerIndex() == 0) {
                        obj.setId(getPublicId());
                    }

                    try {
                        getRmi().getServer()
                                .createObject(p.getSender(), Pactale.class.getSimpleName(), obj.getExactPosition(), obj.getDirection(), obj
                                        .getMovementSpeed(), obj.getId(), Color.argb8888(obj.getColor()), obj.getPlayerIndex());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
                loader.addConsumer(Item.class, obj -> {
                    try {
                        getRmi().getServer().createItem(p.getSender(), obj.getId(), obj.getItemType(), obj
                                .getExactPosition());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                // Exclude everything that's network dependant.
                excluded = EnumSet.of(MapLoader.MapObjectTypes.ENEMY, MapLoader.MapObjectTypes.PACTALE, MapLoader.MapObjectTypes.ITEM);
            }

            // Load map (on OpenGL thread).
            final EnumSet<MapLoader.MapObjectTypes> finalExcluded = excluded;
            Gdx.app.postRunnable(() -> {
                loader.getTypesToLoad().removeAll(finalExcluded);
                loader.setPlayerLimit(p.getPlayerLimit());
                loader.load();
                session.setMap(loader.getMap());

                // Tell the game that we are done loading.
                if (p.isMaster()) {
                    send(new PacketSetLoadStatus(PacketSetLoadStatus.LoadStatus.ObjectsLoaded, null));
                } else {
                    send(new PacketSetLoadStatus(PacketSetLoadStatus.LoadStatus.MapLoaded, null));
                }
            });
        });

        packets.registerAction(PacketSetLoadStatus.class, packet -> {
            try {
                getRmi().getServer().setLoadStatus(packet.getSender(), packet.getStatus());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        packets.registerAction(PacketUpdateProgress.class, packet -> {
            switch (packet.getId()) {
                case "game_load_objects":
                    if (BaseScreen.getSession() == null) {
                        System.out.println("Warning: PacketUpdateProgress received but was not expected. GameSession is null.");
                        return;
                    }
                    BaseScreen.getSession().setObjectsToLoad(packet.getProgress());
                    break;
            }
        });

        packets.registerAction(PacketShootProjectile.class, packet -> {
            // If client sended it..send this private id to server.
            if (packet.getSender() != null && packet.getSender().equals(getId())) {
                try {
                    getRmi().getServer().shootProjectile(packet.getExactPosition(),packet.getDirection(),packet.getSender());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                if (BaseScreen.getSession().getPlayer(packet.getSender()) != null) {
                   Gdx.app.postRunnable(() -> {
                        // Server sended this.
                        BaseScreen.getSession().getPlayer(packet.getSender()).shootProjectile(packet.getExactPosition(),packet.getDirection()); 
                   });
                }
            }
        });

        packets.registerAction(PacketPlayerSetDirection.class, packet -> {
            if (packet.getSender() != null && packet.getSender().equals(getId())) {
                try {
                    getRmi().getServer().playerSetDirection(packet.getSender(), packet.getDirection());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                // Server sended this.
                Pactale player = BaseScreen.getSession().getPlayer(packet.getSender());
                if (player != null) {
                    player.setDirection(packet.getDirection());
                }
            }
        });
        
        
        packets.registerAction(PacketObjectMove.class, packet -> {
            if (packet.getSender() != null && packet.getSender().equals(getId())) {
                try {
                    getRmi().getServer().objectSetDirection(packet.getSender(), packet.getObject(), packet.getFrom(), packet.getTo());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                // Server sent this.
                GameObject object = BaseScreen.getSession().findObject(packet.getObject());
                if (object != null) {
                    object.setExactPosition(packet.getFrom());
                    object.setDirection(Direction.getDirection((int)packet.getFrom().x, (int)packet.getFrom().y, (int)packet.getTo().x, (int)packet.getTo().y));
                }
            }
        });

        packets.registerAction(PacketPlayerSetPosition.class, packet -> {
            if (packet.getSender() != null && packet.getSender().equals(getId())) {
                try {
                    getRmi().getServer().playerSetPosition(packet.getSender(), packet.getPosition());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                // Server sended this.
                BaseScreen.getSession().getPlayer(packet.getSender()).setExactPosition(packet.getPosition());
            }
        });
        
        
        packets.registerAction(PacketPlayerDied.class, packet -> {
            if (packet.getSender() != null && packet.getSender().equals(getId())) {
                try {
                    getRmi().getServer().playerDied(packet.getSender());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                BaseScreen.getSession().getPlayer(packet.getSender()).setInactive();
            }
        });

        packets.registerAction(PacketBalloonMessage.class, packet -> {
            if (packet.getSender() != null && packet.getSender().equals(getId())) {
                try {
                    getRmi().getServer()
                            .balloonMessage(packet.getSender(), packet.getTypeName(), packet.getPosition(), packet.getFollowTarget());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                // Create balloon on OpenGL thread.
                Gdx.app.postRunnable(() -> {
                    try {
                        BalloonMessage message = ((BalloonMessage) Class.forName(BalloonMessage.class.getPackage()
                                .getName() + ".messages.Balloon" + packet
                                .getTypeName()).newInstance());
                        if (packet.getFollowTarget() != null) {
                            message.setFollowObject(BaseScreen.getSession().findObject(packet.getFollowTarget()));
                        } else {
                            message.setPosition(packet.getPosition());
                        }
                        message.addBalloonMessage();
                    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
            }
        });

        packets.registerAction(PacketRemoveItem.class, packet -> {
            if (packet.getSender() != null && packet.getSender().equals(getId())) {
                try {
                    getRmi().getServer().removeItem(packet.getSender(), packet.getPlayer(), packet.getItemId(), packet.getItemType());
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            } else {
                Item foundItem;

                if ((foundItem = BaseScreen.getSession().findItem(packet.getItemId())) != null) {
                    foundItem.activate(BaseScreen.getSession().getPlayer(packet.getPlayer()));
                    foundItem.getNode().removeItem();
                }
            }
        });

        packets.registerAction(PacketObjectCollision.class, packet -> {
            if (packet.getSender() == null) {
                GameObject collider = BaseScreen.getSession().findObject(packet.getCollider());
                GameObject target = BaseScreen.getSession().findObject(packet.getTarget());
                if (collider == null || target == null) {
                    return;
                }
                Gdx.app.postRunnable(() -> collider.collisionWithGameObject(target));
            } else {
                try {
                    getRmi().getServer().objectCollision(packet.getSender(), packet.getCollider(), packet.getTarget());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        packets.registerAction(PacketScoreChanged.class, packet -> {
            if (packet.getSender() != null && packet.getSender().equals(getId())) {
                try {
                    getRmi().getServer().changeScore(packet.getSender(), packet.getManipulationType(), packet.getChange());
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            } else {
                Score currentScore = BaseScreen.getSession().getScore();

                switch (packet.getManipulationType()) {
                    case DECREASE:
                        currentScore.decrease(packet.getChange());
                        break;
                    case INCREASE:
                        currentScore.increase(packet.getChange());
                        break;
                }
            }
        });

        packets.registerFunc(PacketGetHighscores.class, packet -> {
            java.util.Map<String, Integer> returnMap = new HashMap<>();
            try {
                returnMap = getRmi().getServer().getHighscores(packet.getSender());
            } catch (RemoteException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            return returnMap;
        });
    }
}
