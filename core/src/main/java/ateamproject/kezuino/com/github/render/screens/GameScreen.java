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
import ateamproject.kezuino.com.github.singleplayer.Direction;
import ateamproject.kezuino.com.github.singleplayer.GameState;
import ateamproject.kezuino.com.github.singleplayer.Score;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * @author Anton
 */
public class GameScreen extends BaseScreen {

    private GameSession session;
    private Pactale player;
    private GameRenderer gameRenderer;
    private GameState state;
    private Label lblPause;
    
    public GameScreen(Game game) {
        this(game, null);
    }

    public GameScreen(Game game, Score score) {
        super(game);
        
        clearOnRenderColor = Color.WHITE.cpy();       
        Assets.create();
        
        start(score);
        
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
                        if (state.equals(GameState.Paused)){
                            state = GameState.Running;
                            resume();
                        } else if(!state.equals(GameState.Finished)) {
                            state = GameState.Paused;
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
    
    public void start(Score score) {
        session = new GameSession();
        session.setScore(score);
        session.setMap(Map.load(session, "2"));

        player = session.getPlayer(0);
        
        //Set current state to running
        state = GameState.Running;
        //Initialize pause
        lblPause = new Label("Game gepauzeerd", skin);
        lblPause.setColor(Color.RED);
        lblPause.setPosition(100,100 + 300);
        lblPause.setVisible(false);
        stage.addActor(lblPause);

        // Renderers.
        gameRenderer = addRenderer(new GameRenderer(session.getMap()));
        addRenderer(new GameUIRenderer(session.getMap()));     
    }

    @Override
    public void render(float delta) {
        // Render Game and UI.
        super.render(delta);

        if(!this.session.getMap().getNodes().stream().anyMatch(n -> n.hasItem()) && !state.equals(GameState.Finished)) {
            //clearRenderers();
            
            Actor btnContinue = new TextButton("Doorgaan", skin);
            Actor lblEndGameText = new Label("Your score was:", skin);
            Actor lblScore = new Label(Integer.toString(this.session.getScore().valueOf()), skin);
            btnContinue.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    clearRenderers();
                    
                    btnContinue.remove();
                    lblEndGameText.remove();
                    lblScore.remove();
                    start(session.getScore());
                    //game.setScreen(new GameScreen(game, score));
                }
            });
            
            lblEndGameText.setPosition(stage.getWidth() / 2 - lblEndGameText.getWidth() / 2, stage.getHeight() - 80);
            lblScore.setPosition(stage.getWidth() / 2 - lblScore.getWidth() / 2, stage.getHeight() - 100);
            btnContinue.setSize(200, 40);
            btnContinue.setPosition(stage.getWidth() / 2 - btnContinue.getWidth() / 2, stage.getHeight() / 4 - btnContinue.getHeight() / 2);

            stage.addActor(btnContinue);
            stage.addActor(lblEndGameText);
            stage.addActor(lblScore);
            
            this.state = GameState.Finished;
            gameRenderer.complete();
            //game.setScreen(new EndGameScreen(game, this.session.getScore()));
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
        // Once disposed. Assets cannot be reloaded.
        Assets.dispose();
        clearRenderers();
    }
}
