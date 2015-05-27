/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.packets.PacketHighScore;
import ateamproject.kezuino.com.github.network.packet.packets.PacketLogin;
import ateamproject.kezuino.com.github.network.rmi.Client;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.rmi.RemoteException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author David
 */
public class MainScreen extends BaseScreen {
    public MainScreen(Game game) {
        super(game);

        TextButton tbSearchGame = new TextButton("Spel zoeken", skin);
        tbSearchGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        TextButton tbClanGame = new TextButton("Clan spel", skin);
        tbClanGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LobbyListScreen(game, true));
            }
        });

        TextButton tbHighscores = new TextButton("Highscores", skin);
        tbHighscores.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HighscoreScreen(game));
            }
        });

        TextButton tbChangeLook = new TextButton("Uiterlijk aanpassen", skin);
        tbChangeLook.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen(new GameScreen(game));
                 boolean remoteId = false;

                    PacketHighScore packet;
                try {
                    packet = new PacketHighScore("MBoiz", 30, Client.getInstance(game).getId());
                    Client.getInstance(game).send(packet);
                   remoteId=  packet.getResult();
                } catch (RemoteException ex) {
                    Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        TextButton tbOptions = new TextButton("Opties", skin);
        tbOptions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen(new GameScreen(game));
            }
        });

        TextButton tbLogout = new TextButton("Uitloggen", skin);
        tbLogout.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LoginScreen(game));
            }
        });

        TextButton tbClanManagement = new TextButton("Clan management", skin);
        tbClanManagement.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ClanManagementScreen(game));
            }
        });

        tbChangeLook.setSize(300, 40);
        tbClanGame.setSize(300, 40);
        tbHighscores.setSize(300, 40);
        tbLogout.setSize(300, 40);
        tbOptions.setSize(300, 40);
        tbSearchGame.setSize(300, 40);
        tbClanManagement.setSize(300, 40);

        float xOfSearchGameButton = stage.getWidth() / 2 - tbSearchGame.getWidth() / 2;
        float yOfSearchGameButton = stage.getHeight() - 50;

        tbSearchGame.setPosition(xOfSearchGameButton, yOfSearchGameButton);
        tbClanGame.setPosition(xOfSearchGameButton, yOfSearchGameButton - 50);
        tbHighscores.setPosition(xOfSearchGameButton, yOfSearchGameButton - 100);
        tbChangeLook.setPosition(xOfSearchGameButton, yOfSearchGameButton - 150);
        tbOptions.setPosition(xOfSearchGameButton, yOfSearchGameButton - 200);
        tbClanManagement.setPosition(xOfSearchGameButton, yOfSearchGameButton - 250);
        tbLogout.setPosition(xOfSearchGameButton, yOfSearchGameButton - 300);

        stage.addActor(tbSearchGame);
        stage.addActor(tbClanGame);
        stage.addActor(tbHighscores);
        stage.addActor(tbChangeLook);
        stage.addActor(tbOptions);
        stage.addActor(tbClanManagement);
        stage.addActor(tbLogout);

        backgroundMusic = Assets.getMusicStream("menu.mp3");
    }
}
