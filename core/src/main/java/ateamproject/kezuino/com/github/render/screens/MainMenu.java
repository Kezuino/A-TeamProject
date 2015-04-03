/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.render.IRenderer;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * @author David
 */
public class MainMenu extends BaseScreen {

    /**
     *
     */
    public MainMenu(Game game) {
        super(game);
        
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        TextButton tbSearchGame = new TextButton("Spel zoeken", skin);
        tbSearchGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen(new GameScreen(game));
            }
        });

        TextButton tbClanGame = new TextButton("Clan spel", skin);
        tbClanGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen(new GameScreen(game));
            }
        });

        TextButton tbHighscores = new TextButton("Highscores", skin);
        tbHighscores.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen(new GameScreen(game));
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
                //game.setScreen(new GameScreen(game));
            }
        });

        tbChangeLook.setSize(300, 40);
        tbClanGame.setSize(300, 40);
        tbHighscores.setSize(300, 40);
        tbLogout.setSize(300, 40);
        tbOptions.setSize(300, 40);
        tbSearchGame.setSize(300, 40);

        float xOfSearchGameButton = stage.getWidth() / 2 - tbSearchGame.getWidth() / 2;
        float yOfSearchGameButton = stage.getHeight() - 50;

        tbSearchGame.setPosition(xOfSearchGameButton, yOfSearchGameButton);
        tbClanGame.setPosition(xOfSearchGameButton, yOfSearchGameButton - 50);
        tbHighscores.setPosition(xOfSearchGameButton, yOfSearchGameButton - 100);
        tbChangeLook.setPosition(xOfSearchGameButton, yOfSearchGameButton - 150);
        tbOptions.setPosition(xOfSearchGameButton, yOfSearchGameButton - 200);
        tbLogout.setPosition(xOfSearchGameButton, yOfSearchGameButton - 250);

        stage.addActor(tbSearchGame);
        stage.addActor(tbClanGame);
        stage.addActor(tbHighscores);
        stage.addActor(tbChangeLook);
        stage.addActor(tbOptions);
        stage.addActor(tbLogout);
    }
}
