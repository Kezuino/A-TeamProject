/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.packet.packets.*;
import ateamproject.kezuino.com.github.render.screens.BaseScreen;
import ateamproject.kezuino.com.github.render.screens.GameScreen;
import ateamproject.kezuino.com.github.render.screens.LobbyScreen;
import ateamproject.kezuino.com.github.render.screens.MainScreen;
import ateamproject.kezuino.com.github.singleplayer.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.EnumSet;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Kez and Jules
 */
public class Client extends ateamproject.kezuino.com.github.network.Client {

    private static ateamproject.kezuino.com.github.network.rmi.Client instance;
    protected ClientBase rmi;

    protected Client() {
        super(null);

        System.setProperty("pactales.client.servername", "localhost");
        System.setProperty("pactales.client.serverobject", "server");
        try {
            rmi = new ClientBase(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    protected Client(Game game) {
        super(game);

        System.setProperty("pactales.client.servername", "localhost");
        System.setProperty("pactales.client.serverobject", "server");
        try {
            rmi = new ClientBase(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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

    @Override
    public void start() {
        System.out.println("Client starting...");

        try {
            registerPackets();

            String rmiHost = System.getProperty("pactales.client.servername");
            String rmiObject = System.getProperty("pactales.client.serverobject");

            this.rmi.setServer((IProtocolServer) Naming.lookup(String.format("//%s/%s", rmiHost, rmiObject)));

            // Start updating.
            if (updateThread != null) {
                updateThread.interrupt();
            }
            updateThread = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        while (isUpdating) {
                            if (getId() != null) {
                                rmi.getServer().heartbeat(getId());
                            }
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
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
    public void stop() {
        try {
            this.rmi.getServer()
                    .kickClient(this.getId(), this.getId(), PacketKick.KickReasonType.QUIT, "Client disconnected.");
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Client stopped.");
            Timer.instance().stop();
            unregisterPackets();
        }
    }

    @Override
    public void registerPackets() {
        packets.registerFunc(PacketKick.class, packet -> {
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
        });

        packets.registerFunc(PacketLoginAuthenticate.class, packet -> {
            PacketLoginAuthenticate.ReturnData data = null;
            try {
                data = getRmi().getServer().login(packet.getEmailAddress(), packet.getPassword(), getRmi());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            if (data != null) {
                this.setEmailadres(data.getEmailadress());
                this.setUsername(data.getUsername());
                this.setId(data.getClientUuid());
                System.out.println("Logged in as: " + data.getClientUuid());
            } else {
                this.setEmailadres(null);
                this.setUsername(null);
                this.setId(null);
                return false;
            }
            return true;
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
                return getRmi().getServer().setScore(packet.getClanName(), packet.getScore());
            } catch (RemoteException ex) {
                Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        });

        packets.registerFunc(PacketCreateLobby.class, (p) -> {

            try {
                UUID newGame = getRmi().getServer().createLobby(p.getSender(), p.getLobbyname());
                return newGame;
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            return null;
        });

        packets.registerFunc(PacketGetLobbies.class, (p) -> {
            try {
                return getRmi().getServer().getLobbies(p.getSender(), p.getIsClanGame());
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            return null;
        });
        
        packets.registerFunc(PacketGetClans.class, (p) -> {
            // run methode op de serverbase .getclans
            try{
                return getRmi().getServer().getClans(p.getSender());
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            
            return null;
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

        packets.registerFunc(PacketLeaveLobby.class, (p) -> {
            try {
                return getRmi().getServer().leaveLobby(p.getSender());
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            return false;
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
                return getRmi().getServer().handleInvitation(p.getInvite(), p.getClanName(), p.getEmailadres(), p.getNameOfInvitee());
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
                return getRmi().getServer().setUsername(p.getName(), p.getEmailaddress(),p.getSender());
            } catch (RemoteException ex) {
                Logger.getLogger(ateamproject.kezuino.com.github.network.rmi.Client.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

            return false;
        });

        packets.registerAction(PacketSetKickInformation.class, packet -> {
            try {
                getRmi().getServer().setKickInformation(packet.getPersonToVoteFor());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        packets.registerAction(PacketClientJoined.class, p -> System.out.println("Client joined: " + p.getUsername()));

        packets.registerAction(PacketClientLeft.class, p -> System.out.println("Client left: " + p.getUsername()));

        packets.registerAction(PacketLaunchGame.class, p -> {
            try {
                rmi.getServer().launchGame(p.getSender());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        packets.registerAction(PacketCreateGameObject.class, p -> {
            GameSession session = BaseScreen.getSession();
            if (session == null) return;

            if (p.getTypeName().equalsIgnoreCase("item")) {

            } else {
                GameObject object = null;
                try {
                    object = (GameObject) Class.forName(GameObject.class.getName() + '.' + p.getTypeName()).newInstance();
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                if (object == null) return;
                object.setId(p.getId());
                object.setDirection(p.getDirection());
                object.setExactPosition(p.getPosition());
                object.setMap(session.getMap());
                object.setMovementSpeed(p.getSpeed());
                object.setColor(Color.WHITE);

                session.getMap().addGameObject(object);
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
            if (data.getName() != null) System.out.println("New lobby name: " + data.getName());
            if (data.getMap() != null) System.out.println("New lobby map: " + data.getMap());

            return data;
        });

        packets.registerAction(PacketLoadGame.class, p -> {
            GameSession session = new GameSession();
            MapLoader loader = Map.getLoader(session, p.getMap());
            EnumSet<MapLoader.MapObjectTypes> excluded = null;
            if (p.isMaster()) {
                // Load everything and synchronize with server.
                excluded = EnumSet.noneOf(MapLoader.MapObjectTypes.class);
                loader.addConsumer(Enemy.class, enemy -> {

                });
                loader.addConsumer(Pactale.class, System.out::println);
                loader.addConsumer(Item.class, System.out::println);
            } else {
                // Load everything that's not network dependant.
                excluded = EnumSet.of(MapLoader.MapObjectTypes.ENEMY, MapLoader.MapObjectTypes.PACTALE);
            }

            // Load map (on OpenGL thread).
            final EnumSet<MapLoader.MapObjectTypes> finalExcluded = excluded;
            Gdx.app.postRunnable(() -> {
                loader.getTypesToLoad().removeAll(finalExcluded);
                loader.load();
                session.setMap(loader.getMap());
            });
        });
    }
}
