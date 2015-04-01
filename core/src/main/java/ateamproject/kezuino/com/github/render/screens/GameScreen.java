/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.render.IRenderer;
import ateamproject.kezuino.com.github.render.orthographic.GameRenderer;
import ateamproject.kezuino.com.github.singleplayer.GameSession;
import ateamproject.kezuino.com.github.singleplayer.Map;
import ateamproject.kezuino.com.github.singleplayer.Pactale;
import ateamproject.kezuino.com.github.utility.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import ateamproject.kezuino.com.github.singleplayer.Direction;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;

/**
 * @author Anton
 */
public class GameScreen extends BaseScreen {

    private GameSession session;
    private IRenderer renderer;
    
    //TEST CONTROLS
    private final Pactale player;

    public GameScreen(Game game) {
        super(game);

        session = new GameSession();
        session.setMap(Map.load(session, "maps/0.tmx"));
        
        //TEST CONTROLS
        player = new Pactale(session.getMap(), 5, 5, 3, 0.1f, Direction.Down, Color.BLUE);
        session.getMap().addGameObject(5, 5, this.player);
        
        renderer = new GameRenderer(session.getMap());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //CONTROLS
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.player.moveAdjacent(Direction.Up);
        }
        
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.player.moveAdjacent(Direction.Left);
        }
        
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.player.moveAdjacent(Direction.Down);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.player.moveAdjacent(Direction.Right);
        }
        
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            //this.player.shootPortal();
        }
        
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            this.pause();
        }
        
        // Render UI controls.
        super.render(delta);

        // Render game.
        renderer.render();
    }


    @Override
    public void pause() {
        // TODO: If singleplayer: pause game.

        // TODO: If multiplayer: render menu on top of game and capture input, but do NOT pause the game!
    }

    @Override
    public void resume() {
        // TODO: If singleplayer: unpause the game.

        // TODO: If multiplayer: stop rendering the menu on top of the game and resume input processing of the game.
    }

    @Override
    public void dispose() {
        Assets.dispose();
    }
}
