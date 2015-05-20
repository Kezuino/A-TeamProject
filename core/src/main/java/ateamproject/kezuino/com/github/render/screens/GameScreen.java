/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.render.debug.DebugRenderManager;
import ateamproject.kezuino.com.github.render.orthographic.GameRenderer;
import ateamproject.kezuino.com.github.render.orthographic.GameUIRenderer;
import ateamproject.kezuino.com.github.singleplayer.*;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import ateamproject.kezuino.com.github.utility.game.Direction;
import ateamproject.kezuino.com.github.utility.game.balloons.BalloonMessage;
import ateamproject.kezuino.com.github.utility.game.balloons.messages.BalloonHelpMe;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
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
    private Label lblPause;
    private Label lblPlayers;
    private InputAdapter gameInputAdapter;

    public GameScreen(Game game) {
        this(game, null);
    }

    public GameScreen(Game game, Score score) {
        super(game);

        clearOnRenderColor = Color.BLACK;
        Assets.create();
        backgroundMusic = Assets.getMusicStream("action.mp3");

        start(score);

        // Gameplay controls handling:
        gameInputAdapter = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.W:
                        player.setDirection(Direction.Up);
                        break;
                    case Input.Keys.S:
                        player.setDirection(Direction.Down);
                        break;
                    case Input.Keys.A:
                        player.setDirection(Direction.Left);
                        break;
                    case Input.Keys.D:
                        player.setDirection(Direction.Right);
                        break;
                    case Input.Keys.SPACE:
                        if (session.getState() != GameState.Paused) {
                            player.shootProjectile();
                        }
                        break;
                    case Input.Keys.H:
                        BalloonMessage.getBalloonMessage(BalloonHelpMe.class).setFollowObject(player).addBalloonMessage();
                        break;
                    case Input.Keys.F1:
                        DebugRenderManager.toggle();
                        break;
                    case Input.Keys.F2:
                        gameRenderer.toggleFullscreen();
                        break;
                    case Input.Keys.TAB:
                        if (!session.isPauseMenuShowing()) {
                            if (session.isPlayerMenuShowing()) {
                                hidePlayersView();
                            } else {
                                showPlayersView();
                            }
                        }
                        break;
                    case Input.Keys.ESCAPE:
                        if (!session.isPlayerMenuShowing()) {
                            if (session.isPauseMenuShowing()) {
                                hidePauseView();
                            } else {
                                showPauseView();
                            }
                        }
                        break;
                    default:
                        return false;
                }
                return true;
            }
        };
        inputs.addProcessor(gameInputAdapter);
    }

    public void start(Score score) {
        session = new GameSession();
        session.setScore(score);
        session.setMap(Map.load(session, "2"));

        player = session.getPlayer(0);

        // Initialize pause.
        lblPause = new Label("Game gepauzeerd", skin);
        lblPause.setColor(Color.RED);
        lblPause.setPosition(100, 100 + 300);
        lblPause.setVisible(false);
        stage.addActor(lblPause);

        // Initialize lblPlayers.
        lblPlayers = new Label("spelersoverzicht", skin);
        lblPlayers.setColor(Color.RED);
        lblPlayers.setPosition(100, 100 + 300);
        lblPlayers.setVisible(false);
        stage.addActor(lblPlayers);

        // Renderers.
        gameRenderer = addRenderer(new GameRenderer(session));
        addRenderer(new GameUIRenderer(session.getMap()));
    }

    @Override
    public void render(float delta) {
        // Render Game and UI.
        super.render(delta);

        switch (this.session.getState()) {
            case GameOver:
                game.setScreen(new GameOverScreen(game, this.session.getScore()));
                break;

            case Completed:
                Actor btnContinue = new TextButton("Doorgaan", skin);
                Actor lblEndGameText = new Label("Your score was:", skin);
                Actor lblScore = new Label(Integer.toString(this.session.getScore().valueOf()), skin);
                btnContinue.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        btnContinue.remove();
                        lblEndGameText.remove();
                        lblScore.remove();
                        start(session.getScore());
                    }
                });

                lblEndGameText.setPosition(stage.getWidth() / 2 - lblEndGameText.getWidth() / 2, stage.getHeight() - 80);
                lblScore.setPosition(stage.getWidth() / 2 - lblScore.getWidth() / 2, stage.getHeight() - 100);
                btnContinue.setSize(200, 40);
                btnContinue.setPosition(stage.getWidth() / 2 - btnContinue.getWidth() / 2, stage.getHeight() / 4 - btnContinue.getHeight() / 2);

                stage.addActor(btnContinue);
                stage.addActor(lblEndGameText);
                stage.addActor(lblScore);
                this.session.end();
                break;
        }
    }

    public void showPauseView() {
        lblPause.setVisible(true);
        this.session.showPauseMenu();
        // TODO: If multiplayer: draw menu on top of game and capture input, but do NOT pause the game!
    }

    public void hidePauseView() {
        lblPause.setVisible(false);
        this.session.showPauseMenu();
        //this.session.pause();
        // TODO: If multiplayer: stop rendering the menu on top of the game and resume input processing of the game.
    }

    public void showPlayersView() {
        lblPlayers.setVisible(true);
        this.session.showPlayerMenu();
        // TODO: If multiplayer: stop rendering the menu on top of the game and resume input processing of the game.
    }

    public void hidePlayersView() {
        lblPlayers.setVisible(false);
        this.session.showPlayerMenu();
        // TODO: If multiplayer: stop rendering the menu on top of the game and resume input processing of the game.
    }

    @Override
    public void resume() {
        // TODO: If multiplayer (not host): Set the state of the client to unpause. If all clients are unpaused the game can resume as a whole. The host can always force a resume.
        this.session.resume();
    }

    @Override
    public void pause() {
        // TODO: If multiplayer: Request pausing to server when it's enabled for this game.
        this.session.pause();
    }

    @Override
    public void dispose() {
        // Once disposed. Assets cannot be reloaded.
        Assets.dispose();
        clearRenderers();
    }
}
