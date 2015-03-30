/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * @author David
 */
public class ClanGamesScreen extends BaseScreen {

    public ClanGamesScreen(Game game) {
        super(game);

        TextButton btnLogin = new TextButton("Press me!", skin);
        btnLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });


        btnLogin.setSize(300, 40);
        btnLogin.setPosition(stage.getWidth() / 2 - btnLogin.getWidth() / 2, stage.getHeight() / 2 - btnLogin.getHeight() / 2);

        Label nameLabel = new Label("Name:", skin);
        TextField nameText = new TextField("", skin);
        Label addressLabel = new Label("Address:", skin);
        TextField addressText = new TextField("", skin);

        Table table = new Table();
        table.add(nameLabel);
        table.add(nameText).width(10);
        table.row();
        table.add(addressLabel);
        table.add(addressText).width(10);

        table.setPosition(stage.getWidth() / 2, stage.getHeight() / 2);
        //stage.addActor(btnLogin);        
        stage.addActor(table);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render(delta);
    }
}
