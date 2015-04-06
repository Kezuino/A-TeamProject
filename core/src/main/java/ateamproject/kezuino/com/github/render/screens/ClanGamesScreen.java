/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import javafx.scene.paint.Color;

/**
 * @author David
 */
public class ClanGamesScreen extends BaseScreen{

    public ClanGamesScreen(Game game)
    {
        super(game);
    
        Table table = new Table();
       
        for (int i = 0; i < 5; i++) {
            Label clan = new Label("OurClan", skin);        
            Label clanjoin = new Label("8/10", skin);
            
            table.add(clan);
            table.add(clanjoin);
            table.row();
        }
        
        TextButton btnBack = new TextButton("< Back", skin);
         btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });
        btnBack.setSize(300, 400);
        
        table.add(btnBack).colspan(2);
        
        
        table.setPosition(stage.getWidth()/2, stage.getHeight()/2);
        
        //stage.addActor(btnLogin);        
        stage.addActor(table);

        backgroundMusic = Assets.getMusicStream("menu.mp3");
    }

}
