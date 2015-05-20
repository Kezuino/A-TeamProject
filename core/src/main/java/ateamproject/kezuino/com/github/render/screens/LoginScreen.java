/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ateamproject.kezuino.com.github.network.mail.*;
import ateamproject.kezuino.com.github.network.rmi.Client;
import ateamproject.kezuino.com.github.network.rmi.IProtocolServer;
import ateamproject.kezuino.com.github.utility.assets.Assets;

/**
 * @author David
 */
public class LoginScreen extends BaseScreen {
    public LoginScreen(Game game) {
        super(game);
        
        TextField txtUsername = new TextField("", skin);

        TextField txtPassword = new TextField("", skin);
        
        TextButton btnLogin = new TextButton("Login", skin);
        btnLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MailAccount mailAccount = new MailAccount();
                mailAccount.isValid(txtUsername.getText(), txtPassword.getText());
                try {
                    mailAccount.test();
                    System.out.println("Logged in");
                    IProtocolServer connection = Client.getInstance().getConnection();
                    connection.login(txtUsername.getMessageText());
                    game.setScreen(new MainScreen(game));
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        
        // delete this //
        TextButton btntestlogin = new TextButton("no auth login", skin);
        btntestlogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
             game.setScreen(new MainScreen(game));
            }
        });
        btntestlogin.setSize(300, 40);
        btntestlogin.setPosition(stage.getWidth() / 2 - btntestlogin.getWidth() / 2, 100);
        ////////////////// 
        
        
        txtUsername.setSize(300, 40);
        txtPassword.setSize(300, 40);
        txtPassword.setPasswordMode(true);
        btnLogin.setSize(300, 40);

        btnLogin.setPosition(stage.getWidth() / 2 - btnLogin.getWidth() / 2, 150);
        txtUsername.setPosition(stage.getWidth() / 2 - txtUsername.getWidth() / 2, 300);
        txtPassword.setPosition(stage.getWidth() / 2 - txtPassword.getWidth() / 2, 250);

        stage.addActor(btnLogin);        
        stage.addActor(btntestlogin); // << delete this
        stage.addActor(txtUsername);
        stage.addActor(txtPassword);

        backgroundMusic = Assets.getMusicStream("menu.mp3");
    }
}
