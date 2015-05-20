/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * @author David
 */
public class LoginScreen extends BaseScreen {
    public LoginScreen(Game game) {
        super(game);
        
        

        TextButton btnLogin = new TextButton("Login", skin);
        btnLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });

        btnLogin.setSize(300, 40);

        float xOfLoginButton = stage.getWidth() / 2 - btnLogin.getWidth() / 2;
        float yOfLoginButton = stage.getHeight() / 2 - btnLogin.getHeight() / 2;

        btnLogin.setPosition(xOfLoginButton, yOfLoginButton);

        stage.addActor(btnLogin);

        backgroundMusic = Assets.getMusicStream("menu.mp3");
    }
}
