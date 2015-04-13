/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

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
public class EndGameScreen extends BaseScreen {
    public EndGameScreen(Game game, Score score, BaseScreen base) {
        super(game);
        
        base.dispose();

        TextButton btnBack = new TextButton("Terug", skin);
        Label lblEndGameText = new Label("Your score was:", skin);
        Label lblScore = new Label(Integer.toString(score.valueOf()), skin);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });

        lblEndGameText.setPosition(stage.getWidth() / 2 - lblEndGameText.getWidth() / 2, stage.getHeight() - 50);
        lblScore.setPosition(stage.getWidth() / 2 - lblScore.getWidth() / 2, stage.getHeight() - 80);
        btnBack.setSize(200, 40);
        btnBack.setPosition(stage.getWidth() / 2 - btnBack.getWidth() / 2, stage.getHeight() / 4 - btnBack.getHeight() / 2);

        stage.addActor(btnBack);
        stage.addActor(lblEndGameText);
        stage.addActor(lblScore);

        backgroundMusic = Assets.getMusicStream("menu.mp3");
    }
}
