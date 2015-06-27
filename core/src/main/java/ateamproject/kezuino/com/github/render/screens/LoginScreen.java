/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.network.packet.packets.PacketLoginAuthenticate;
import ateamproject.kezuino.com.github.network.packet.packets.PacketLoginCreateNewUser;
import ateamproject.kezuino.com.github.network.packet.packets.PacketLoginUserExists;
import ateamproject.kezuino.com.github.network.rmi.Client;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * @author David
 */
public class LoginScreen extends BaseScreen {

    protected TextButton bExit;

    public LoginScreen(Game game) {
        super(game);

        TextField txtUsername = new TextField("pactales1@gmail.com", skin);

        TextField txtPassword = new TextField("pactales!", skin);
        txtPassword.setPasswordCharacter('*');
        txtPassword.setPasswordMode(true);

        TextButton btnLogin = new TextButton("Inloggen", skin);

        btnLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                btnLogin.setTouchable(Touchable.disabled);
                btnLogin.setText("Laden");
                Thread loginThread = new Thread(() -> {
                    PacketLoginAuthenticate packet = new PacketLoginAuthenticate(txtUsername.getText(), txtPassword.getText(), null);
                    Client.getInstance().send(packet);

                    Gdx.app.postRunnable(() -> {
                        // Login was successful..
                        if (Client.getInstance().getId() != null) {
                            PacketLoginUserExists packetUserExists = new PacketLoginUserExists(txtUsername.getText(), null);
                            Client.getInstance().send(packetUserExists);

                            // Handle user doesn't exist.
                            if (!packetUserExists.getResult()) {
                                Dialog d = new Dialog("Geen gebruiker gevonden", skin);
                                d.add("Gebruikersnaam:");
                                TextField f = new TextField("", skin);
                                d.add(f);
                                bExit = new TextButton("Oke", skin);
                                bExit.addListener(new ClickListener() {
                                    @Override
                                    public void clicked(InputEvent event, float x, float y) {
                                        if (!f.getText().isEmpty()) {
                                            PacketLoginCreateNewUser packet1;
                                            packet1 = new PacketLoginCreateNewUser(f.getText(), txtUsername.getText());
                                            Client.getInstance().send(packet1);
                                            if (!packet1.getResult()) {
                                                new Dialog("Error", skin) {
                                                    {
                                                        text("De naam bestaat al.");
                                                        button("Oke");
                                                    }
                                                }.show(stage);
                                            } else {
                                                d.hide();
                                                Client.getInstance().setUsername(f.getText());
                                                game.setScreen(new MainScreen(game));
                                            }
                                        }
                                    }
                                });
                                d.add(bExit);
                                d.show(stage);
                            } else {
                                game.setScreen(new MainScreen(game));
                            }
                        } else {
                            new Dialog("Error", skin) {
                                {
                                    text(packet.getResult().getMessage());
                                    button("Oke");
                                }
                            }.show(stage);
                        }
                        btnLogin.setText("Inloggen");
                        btnLogin.setTouchable(Touchable.enabled);//enable button touch
                    });
                });
                loginThread.start();
            }
        });

        txtUsername.setSize(300, 40);
        txtPassword.setSize(300, 40);
        txtPassword.setPasswordMode(true);
        btnLogin.setSize(300, 40);

        btnLogin.setPosition(stage.getWidth() / 2 - btnLogin.getWidth() / 2, 150);
        txtUsername.setPosition(stage.getWidth() / 2 - txtUsername.getWidth() / 2, 300);
        txtPassword.setPosition(stage.getWidth() / 2 - txtPassword.getWidth() / 2, 250);

        stage.addActor(btnLogin);
        stage.addActor(txtUsername);
        stage.addActor(txtPassword);

        backgroundMusic = Assets.getMusicStream("menu.mp3");
    }
}
