/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.render.orthographic.GameRenderer;
import ateamproject.kezuino.com.github.render.orthographic.GameUIRenderer;
import ateamproject.kezuino.com.github.singleplayer.GameSession;
import ateamproject.kezuino.com.github.singleplayer.Map;
import ateamproject.kezuino.com.github.singleplayer.Pactale;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import ateamproject.kezuino.com.github.singleplayer.Direction;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * @author Anton
 */
public class GameScreen extends BaseScreen {

    private GameSession session;
    private final Pactale player;
    private GameRenderer gameRenderer;
    private GameUIRenderer UIRenderer;
    private boolean paused;
    private Label lblPause;

    public GameScreen(Game game) {
        super(game);
        clearOnRenderColor = Color.WHITE.cpy();       
        Assets.create();

        session = new GameSession();
        session.setMap(Map.load(session, "1"));

        player = session.getPlayer(0);
        
        //Initialize the pausing
        paused = false;
        lblPause = new Label("Game gepauzeerd", skin);
        lblPause.setColor(Color.RED);
        lblPause.setPosition(100,100 + 300);
        lblPause.setVisible(false);
        stage.addActor(lblPause);
        
        
        // Renderers.
        gameRenderer = addRenderer(new GameRenderer(session.getMap()));
        UIRenderer = addRenderer(new GameUIRenderer(session.getMap()));

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
                        if (paused){
                            paused = false;
                            resume();
                        }else {
                            paused = true;
                            pause();
                        }
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
 
        // Render Game and UI.
        super.render(delta);

        if(!this.session.getMap().getNodes().stream().anyMatch(n -> n.hasItem())) {
            removeRenderer(gameRenderer);
            removeRenderer(UIRenderer);
            //this.dispose();
            
            game.setScreen(new EndGameScreen(game, this.session.getScore(), this));
            //end game
        }
    }
    
    @Override
    public void pause() {
        // TODO: If singleplayer: pause game.
        gameRenderer.pause();
        lblPause.setVisible(true);
        // TODO: If multiplayer: draw menu on top of game and capture input, but do NOT pause the game!
    }

    @Override
    public void resume() {
        // TODO: If singleplayer: unpause the game.
        gameRenderer.unpause();
        lblPause.setVisible(false);
        // TODO: If multiplayer: stop rendering the menu on top of the game and resume input processing of the game.
    }

    @Override
    public void dispose() {
        Assets.dispose();
    }
}
