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
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import ateamproject.kezuino.com.github.singleplayer.Direction;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

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

        player = new Pactale(session.getMap(), 7,7, 3, .5f, Direction.Down, Color.RED);
        player.setTexture(Assets.get("textures/foreground/pactale.png", Texture.class));
        session.getMap().addGameObject(5, 5, this.player);
        
        renderer = new GameRenderer(session.getMap());

        // Gameplay controls handling:
        inputs.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.W:
                        //player.moveAdjacent(Direction.Up, true);
                        player.setDirection(Direction.Up);
                        break;
                    case Input.Keys.S:
                        //player.moveAdjacent(Direction.Down, true);
                        player.setDirection(Direction.Down);
                        break;
                    case Input.Keys.A:
                        //player.moveAdjacent(Direction.Left, true);
                        player.setDirection(Direction.Left);
                        break;
                    case Input.Keys.D:
                        //player.moveAdjacent(Direction.Right, true);
                        player.setDirection(Direction.Right);
                        break;
                    case Input.Keys.SPACE:
                        player.shootProjectile();
                        break;
                    case Input.Keys.ESCAPE:
                        pause();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
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
