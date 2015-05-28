/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.rmi;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.packets.*;
import ateamproject.kezuino.com.github.render.screens.MainScreen;
import ateamproject.kezuino.com.github.utility.graphics.DialogHelper;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.Timer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Kez and Jules
 */
public class Client extends ateamproject.kezuino.com.github.network.Client {

    private static Client instance;
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
     * Only use on client-side!
     *
     * @return {@link Client} instance. Creates a new {@link Client} if it
     * doesn't exist.
     * @throws RemoteException
     */
    public static Client getInstance(Game game) throws RemoteException {
        if (instance == null) {
            instance = new Client(game);
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
            this.rmi.getServer().kickClient(this.getId(), PacketKick.KickReasonType.QUIT, null);
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
        Packet.registerFunc(PacketLoginAuthenticate.class, packet -> {
            UUID id = null;
            try {
                id = getRmi().getServer().login(packet.getUsername(), packet.getPassword());
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

        Packet.registerFunc(PacketHighScore.class, (packet) -> {
            try {
                return Client.getInstance(game).getRmi().getServer().setScore(packet.getClanName(), packet.getScore());
            } catch (RemoteException ex) {
                Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        });

        Packet.registerAction(PacketCreateLobby.class, (p) -> {

            try {
                getRmi().getServer().createLobby(p.getLobbyname(), p.getSender());

                // ateamproject.kezuino.com.github.network.Game game = new ateamproject.kezuino.com.github.network.Game(p.getLobbyname(), p.getSender());
                //games.put(game.getId(), game);
            } catch (RemoteException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
         Packet.registerFunc(PacketGetLobbies.class, (p) -> {
            
            try {
                List<PacketGetLobbies.GetLobbiesData> list =  getRmi().getServer().getLobbies();
               // p.setResult(list);
                return list;
                
                // ateamproject.kezuino.com.github.network.Game game = new ateamproject.kezuino.com.github.network.Game(p.getLobbyname(), p.getSender());
                //games.put(game.getId(), game);
            } catch (RemoteException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        });

        Packet.registerFunc(PacketJoinLobby.class, (p) -> {
            try {
                PacketJoinLobby.PacketJoinLobbyData result = getRmi().getServer().joinLobby(p.getLobbyid(), p.getSender());
                return result;
                 
                // getRmi().createLobby(p.getLobbyname(), p.getSender());
                //Game game = new Game(p.getLobbyname(), p.getSender());
                //games.put(game.getId(), game);
            } catch (RemoteException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        });
        
         Packet.registerFunc(PacketLeaveLobby.class, (p) -> {
            try {
                Boolean result = getRmi().getServer().leaveLobby(p.getLobbyid(), p.getSender());
                return result;
                 
                // getRmi().createLobby(p.getLobbyname(), p.getSender());
                //Game game = new Game(p.getLobbyname(), p.getSender());
                //games.put(game.getId(), game);
            } catch (RemoteException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        });

        Packet.registerFunc(PacketLoginCreateNewUser.class, (p) -> {
            try {
                return getRmi().getServer().loginCreateUser(p.getUsername(), p.getEmail());
            } catch (RemoteException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }

            return false;
        });

        Packet.registerFunc(PacketLoginUserExists.class, (p) -> {
            try {
                return getRmi().getServer().doesUserExists(p.getEmail());
            } catch (RemoteException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }

            return false;
        });
    }
}
