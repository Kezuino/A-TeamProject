/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


/**
 * @author Sven
 */
public class StoreScreen extends BaseScreen {

    public StoreScreen(Game game) {
        super(game);

        TextButton btnBack = new TextButton("Terug", skin);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // game.setScreen(new HomeScreen(game));
            }
        });
        float x = 240;
        float y = stage.getHeight() / 4;
        btnBack.setSize(200, 40);
        btnBack.setPosition(x, y);


        Label lblStore = new Label("Winkel", skin);
        lblStore.setColor(Color.YELLOW);
        lblStore.setPosition(x, y + 300);

        List listStoreItems = new List(skin);
        String[] skinsArray = {"Skin pack", "Minecraft pack"};
        listStoreItems.setItems((Object)skinsArray);
        listStoreItems.setPosition(x, y + 200);

        TextButton btnBuy = new TextButton("Koop item", skin);
        btnBuy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // game.setScreen(new HomeScreen(game));
            }
        });
        btnBuy.setSize(200, 40);
        btnBuy.setPosition(x - 50, y + 50);

        TextButton btnSelect = new TextButton("Selecteer item", skin);
        btnSelect.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // game.setScreen(new HomeScreen(game));
            }
        });
        btnSelect.setSize(200, 40);
        btnSelect.setPosition(x + 50, y + 50);

        stage.addActor(btnBack);
        stage.addActor(lblStore);
        stage.addActor(listStoreItems);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render(delta);
    }
}
