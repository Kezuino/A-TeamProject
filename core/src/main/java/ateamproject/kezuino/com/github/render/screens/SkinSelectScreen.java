/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.nio.file.Path;

/**
 * @author David
 */
public class SkinSelectScreen extends BaseScreen {

    public SkinSelectScreen(Game game) {
        super(game);
        float x = stage.getWidth() / 2 - 300 / 2;
        float y = stage.getHeight() - 100;

        for (Path path : Assets.getSkins()) {
            TextButton tbChangeLook = new TextButton(path.getFileName().toString(), skin);
            tbChangeLook.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.getPreferences("user").putString("skin", path.getFileName().toString());
                    Assets.setSkin(path.getFileName().toString(), true, () -> game.setScreen(new MainScreen(game)));
                }
            });
            tbChangeLook.setSize(300, 40);

            tbChangeLook.setPosition(x, y);
            stage.addActor(tbChangeLook);
            y = y - 50;
        }

        TextButton tbOptions = new TextButton("Terug", skin);
        tbOptions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });

        tbOptions.setSize(300, 40);
        tbOptions.setPosition(x, y - 50);
        stage.addActor(tbOptions);

        backgroundMusic = Assets.getMusicStream("menu.mp3");
    }
}
