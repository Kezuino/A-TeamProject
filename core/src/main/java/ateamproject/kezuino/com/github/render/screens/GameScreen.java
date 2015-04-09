/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.render.orthographic.GameRenderer;
import ateamproject.kezuino.com.github.singleplayer.GameSession;
import ateamproject.kezuino.com.github.singleplayer.Map;
import ateamproject.kezuino.com.github.singleplayer.Pactale;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import ateamproject.kezuino.com.github.singleplayer.Direction;
import ateamproject.kezuino.com.github.singleplayer.GameObject;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Anton
 */
public class GameScreen extends BaseScreen {

    private GameSession session;
    private final Pactale player;
    
    //score manipulation
    private long startTime = System.currentTimeMillis();
    private long nextScoreUpdate = 1000;//amount of miliseconds that a score will be decremented
    private int maxScoreManipulation;//the maximal amount of score that will be decremented
    private int currentScoreManipulation = 0;//the current amount of decremented score

    public GameScreen(Game game) {
        super(game);
        clearOnRenderColor = Color.WHITE;

        Assets.create();

        session = new GameSession();
        session.setMap(Map.load(session, "1"));

        player = session.getPlayer(0);
        //session.getMap().addGameObject(9, 5, this.player);

        maxScoreManipulation = session.getScore().getScore();
        
        // Renderers.
        addRenderer(new GameRenderer(session.getMap(),session.getScore()));

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
        
        if (System.currentTimeMillis() - startTime > nextScoreUpdate && currentScoreManipulation < maxScoreManipulation){//make sure that the score wont be decremented beyond its initial starting value
            int scoreToDecrement = 0;
            for (GameObject gObject : this.session.getMap().getAllGameObjects()) {
                if (gObject instanceof Pactale) {
                    scoreToDecrement += 60;
                }
            }
            
            this.session.getScore().decrementScore(scoreToDecrement);
            this.currentScoreManipulation += scoreToDecrement;
            nextScoreUpdate = nextScoreUpdate + 1000;            
        }
        
        // Render Game and UI.
        super.render(delta);
        
    }
    @Override
    public void pause() {
        // TODO: If singleplayer: pause game.

        // TODO: If multiplayer: draw menu on top of game and capture input, but do NOT pause the game!
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
