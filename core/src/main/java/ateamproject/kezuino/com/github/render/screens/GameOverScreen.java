/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.network.packet.packets.PacketLaunchGame;
import ateamproject.kezuino.com.github.network.rmi.Client;
import ateamproject.kezuino.com.github.singleplayer.Score;
import ateamproject.kezuino.com.github.utility.assets.Assets;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;



/**
 * @author Sven
 */
public class GameOverScreen extends BaseScreen {
    public GameOverScreen(Game game, Score score) {
        super(game);

        TextButton btnBack = new TextButton("Stoppen", skin);
        TextButton btnRetry = new TextButton("Opnieuw proberen", skin);
        Label lblEndGameText = new Label("Game Over\nJe score was:", skin);
        Label lblScore = new Label(Integer.toString(score.valueOf()), skin);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });
        btnRetry.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {                
                Client.getInstance().send(new PacketLaunchGame(false));
                game.setScreen(new GameScreen(game));
            }
        });

        lblEndGameText.setPosition(stage.getWidth() / 2 - lblEndGameText.getWidth() / 2, stage.getHeight() - 50);
        lblScore.setPosition(stage.getWidth() / 2 - lblScore.getWidth() / 2, stage.getHeight() - 80);
        btnRetry.setSize(200, 40);
        btnBack.setSize(200, 40);
        btnRetry.setPosition((stage.getWidth() / 2) - (btnRetry.getWidth() / 2) + (btnBack.getWidth() / 2) + 5, stage.getHeight() / 4 - btnRetry.getHeight() / 2);
        btnBack.setPosition((stage.getWidth() / 2) - (btnBack.getWidth() / 2) - (btnRetry.getWidth() / 2) - 5, stage.getHeight() / 4 - btnBack.getHeight() / 2);


        stage.addActor(btnRetry);
        stage.addActor(btnBack);
        stage.addActor(lblEndGameText);
        stage.addActor(lblScore);

        backgroundMusic = Assets.getMusicStream("menu.mp3");
    }
}
