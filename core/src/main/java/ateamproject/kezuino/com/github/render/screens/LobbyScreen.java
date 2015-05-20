/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import com.badlogic.gdx.Game;

/**
 *
 * @author Fatih
 */
public class LobbyScreen extends BaseScreen {

    public LobbyScreen(Game game, boolean isHost) {
        super(game);
        
        if (isHost) {
            CreateHost();
        }
        
        
    }
    
    public void CreateHost()
    {
    }
    
}
