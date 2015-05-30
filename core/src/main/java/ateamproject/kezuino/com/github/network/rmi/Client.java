/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.packet.packets.*;
import ateamproject.kezuino.com.github.render.screens.BaseScreen;
import ateamproject.kezuino.com.github.render.screens.LobbyListScreen;
import ateamproject.kezuino.com.github.render.screens.MainScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Kez and Jules
 */
public class Client extends ateamproject.kezuino.com.github.network.Client {

    private static ateamproject.kezuino.com.github.network.rmi.Client instance;
    protected ClientBase rmi;

    protected Client() throws RemoteException {
        super(null);

        System.setProperty("pactales.client.servername", "localhost");
        System.setProperty("pactales.client.serverobject", "server");
        rmi = new ClientBase(this);
    }

    protected Client(Game game) throws RemoteException {
        super(game);

        System.setProperty("pactales.client.servername", "localhost");
        System.setProperty("pactales.client.serverobject", "server");
        rmi = new ClientBase(this);
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
    public static ateamproject.kezuino.com.github.network.rmi.Client getInstance() throws RemoteException {
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
            UUID id = null;
            try {
                id = getRmi().getServer().login(packet.getEmailAddress(), packet.getPassword(), getRmi());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            if (id != null) {
                this.setId(id);
                System.out.println("Logged in as: " + id);
            } else {
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
                return getRmi().getServer().getLobbies();
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

        packets.registerAction(PacketClientJoined.class, p -> System.out.println("Client joined: " + p.getUsername()));
    }
}
