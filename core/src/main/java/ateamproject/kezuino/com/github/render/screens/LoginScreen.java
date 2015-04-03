/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.utility.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
        Sound sound = Assets.get("sounds/Background.mp3",Sound.class);
        if (sound != null)
            sound.play();

        TextButton btnLogin = new TextButton("Login", skin);
        btnLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (sound != null) sound.dispose();
                game.setScreen(new MainScreen(game));

            }
        });

        btnLogin.setSize(300, 40);

        float xOfLoginButton = stage.getWidth() / 2 - btnLogin.getWidth() / 2;
        float yOfLoginButton = stage.getHeight() / 2 - btnLogin.getHeight() / 2;

        btnLogin.setPosition(xOfLoginButton, yOfLoginButton);

        stage.addActor(btnLogin);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render(delta);
    }
}
