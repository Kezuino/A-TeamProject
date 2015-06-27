/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.network.packet.packets.PacketGetClans;
import ateamproject.kezuino.com.github.network.packet.packets.PacketHighScore;
import ateamproject.kezuino.com.github.network.packet.packets.PacketKick;
import ateamproject.kezuino.com.github.network.rmi.Client;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.util.ArrayList;

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
                game.setScreen(new LobbyListScreen(game, false));
            }
        });

        TextButton tbClanGame = new TextButton("Clan spel", skin);
        tbClanGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Client client = Client.getInstance();
                PacketGetClans packet = new PacketGetClans();

                client.send(packet);
                ArrayList<String> listclans = packet.getResult();

                if (listclans.isEmpty()) {
                    Dialog d = new Dialog("error", skin);
                    d.add("Gebruiker zit niet in een clan.");
                    TextButton bExit = new TextButton("Oke", skin);
                    bExit.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            d.hide();
                        }
                    });
                    d.add(bExit);
                    d.show(stage);
                } else {
                    game.setScreen(new LobbyListScreen(game, true));
                }
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
                Client.getInstance().setSkin("Skin2");
               game.setScreen(new SkinSelectScreen(game));
                  
            }
        });


        TextButton tbLogout = new TextButton("Uitloggen", skin);
        tbLogout.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PacketKick packet = new PacketKick(PacketKick.KickReasonType.QUIT, "Logging out.", null);
                Client.getInstance().send(packet);
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
        tbSearchGame.setSize(300, 40);
        tbClanManagement.setSize(300, 40);

        float xOfSearchGameButton = stage.getWidth() / 2 - tbSearchGame.getWidth() / 2;
        float yOfSearchGameButton = stage.getHeight() - 50;

        tbSearchGame.setPosition(xOfSearchGameButton, yOfSearchGameButton);
        tbClanGame.setPosition(xOfSearchGameButton, yOfSearchGameButton - 50);
        tbHighscores.setPosition(xOfSearchGameButton, yOfSearchGameButton - 100);
        tbChangeLook.setPosition(xOfSearchGameButton, yOfSearchGameButton - 150);
        tbClanManagement.setPosition(xOfSearchGameButton, yOfSearchGameButton - 200);
        tbLogout.setPosition(xOfSearchGameButton, yOfSearchGameButton - 250);

        stage.addActor(tbSearchGame);
        stage.addActor(tbClanGame);
        stage.addActor(tbHighscores);
        stage.addActor(tbChangeLook);
        stage.addActor(tbClanManagement);
        stage.addActor(tbLogout);

        backgroundMusic = Assets.getMusicStream("menu.mp3");
    }
}
