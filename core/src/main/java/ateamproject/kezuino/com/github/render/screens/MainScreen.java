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
                game.setScreen(new ClanGamesScreen(game));
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
                Gdx.app.exit();
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
