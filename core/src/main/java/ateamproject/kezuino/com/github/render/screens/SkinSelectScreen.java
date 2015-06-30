/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.network.packet.packets.PacketGetClans;
import ateamproject.kezuino.com.github.network.packet.packets.PacketHighScore;
import ateamproject.kezuino.com.github.network.rmi.Client;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.util.ArrayList;

/**
 * @author David
 */
public class SkinSelectScreen extends BaseScreen {

    public SkinSelectScreen(Game game) {
        super(game);
        String[] Skins = Assets.getSkins();
        float x = stage.getWidth() / 2 - 300 / 2;
        float y = stage.getHeight() - 200;

        for (String SkinString : Skins) {

            TextButton tbChangeLook = new TextButton("Uiterlijk aanpassen", skin);
            tbChangeLook.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Assets.unload();
                    Assets.create(SkinString);
                    Assets.getSkins();
                }
            });
            tbChangeLook.setSize(300, 40);

            tbChangeLook.setPosition(x, y);
            stage.addActor(tbChangeLook);
            y += 100;
        }

        TextButton tbOptions = new TextButton("Opties", skin);
        tbOptions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });

        tbOptions.setSize(300, 40);

        stage.addActor(tbOptions);

        backgroundMusic = Assets.getMusicStream("menu.mp3");
    }
}
