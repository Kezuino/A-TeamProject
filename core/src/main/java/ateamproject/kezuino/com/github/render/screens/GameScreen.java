/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.network.packet.packets.PacketGetKickInformation;
import ateamproject.kezuino.com.github.network.packet.packets.PacketKick;
import ateamproject.kezuino.com.github.network.packet.packets.PacketSetKickInformation;
import ateamproject.kezuino.com.github.network.packet.packets.PacketShootProjectile;
import ateamproject.kezuino.com.github.network.rmi.Client;
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
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.util.ArrayList;
import java.util.UUID;

import java.util.ArrayList;
import java.util.UUID;


/**
 * @author Anton
 */
public class GameScreen extends BaseScreen {

    private Pactale player;
    private GameRenderer gameRenderer;
    private InputAdapter gameInputAdapter;

    private Dialog pauseMenu;
    private Dialog playerMenu;

    public GameScreen(Game game) {
        super(game);

        clearOnRenderColor = Color.BLACK;
        Assets.create();
        backgroundMusic = Assets.getMusicStream("action.mp3");

        // Gameplay controls handling:
        gameInputAdapter = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.W:
                        if (getSession().getState() == GameState.Paused) break;
                        player.setDirection(Direction.Up);
                        break;
                    case Input.Keys.S:
                        if (getSession().getState() == GameState.Paused) break;
                        player.setDirection(Direction.Down);
                        break;
                    case Input.Keys.A:
                        if (getSession().getState() == GameState.Paused) break;
                        player.setDirection(Direction.Left);
                        break;
                    case Input.Keys.D:
                        if (getSession().getState() == GameState.Paused) break;
                        player.setDirection(Direction.Right);
                        break;
                    case Input.Keys.SPACE:
                        if (getSession().getState() != GameState.Paused) {
                            Projectile proj = player.shootProjectile();
                            Client.getInstance().send(new PacketShootProjectile(proj.getExactPosition(), proj.getDirection(), proj.getMovementSpeed(), proj.getId()));
                        }
                        break;
                    case Input.Keys.H:
                        BalloonMessage.getBalloonMessage(BalloonHelpMe.class)
                                .setFollowObject(player)
                                .addBalloonMessage();
                        break;
                    case Input.Keys.F1:
                        DebugRenderManager.toggle();
                        break;
                    case Input.Keys.F2:
                        gameRenderer.toggleFullscreen();
                        break;
                    case Input.Keys.TAB:
                        if (!getSession().isPauseMenuShowing()) {
                            if (getSession().isPlayerMenuShowing()) {
                                hidePlayersView();
                            } else {
                                showPlayersView();
                            }
                        }
                        break;
                    case Input.Keys.ESCAPE:
                        if (!getSession().isPlayerMenuShowing()) {
                            showPauseMenu();
                        }
                        break;
                    default:
                        return false;
                }
                return true;
            }
        };
        inputs.addProcessor(gameInputAdapter);

        createGui();
    }

    private void createGui() {
        // Create pause menu.
        pauseMenu = new Dialog("Menu", skin);
        pauseMenu.setKeepWithinStage(false);

        TextButton bContinue = new TextButton("Doorgaan", skin);
        bContinue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MoveToAction action = Actions.action(MoveToAction.class);
                action.setPosition(stage.getWidth() / 2 - pauseMenu.getWidth() / 2, stage.getHeight() + pauseMenu.getHeight());
                action.setDuration(0.1f);
                pauseMenu.hide(action);
            }
        });
        pauseMenu.add(bContinue);

        TextButton bExit = new TextButton("Verlaten", skin);
        bExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PacketKick packet = new PacketKick(PacketKick.KickReasonType.GAME, "Client disconnected.");
                Client.getInstance().send(packet);

                game.setScreen(new MainScreen(game));
            }
        });

        pauseMenu.add(bExit);
    }

    public void start() {
        start(null);
    }

    public void start(Score score) {
        if (getSession() == null) {
            System.out.println("Resetted session on GameScreen.start");
            setSession(new GameSession());
        }
        if (getSession().getScore() == null) getSession().setScore(score);
        if (getSession().getMap() == null) throw new IllegalStateException("Map should be loaded before the GameScreen can be started.");

        player = getSession().getPlayer(0);

        // Renderers.
        gameRenderer = addRenderer(new GameRenderer());
        addRenderer(new GameUIRenderer(getSession().getMap()));
    }

    @Override
    public void render(float delta) {
        // Render Game and UI.
        super.render(delta);

        switch (getSession().getState()) {
            case GameOver:
                game.setScreen(new GameOverScreen(game, getSession().getScore()));
                break;

            case Completed:
                Actor btnContinue = new TextButton("Doorgaan", skin);
                Actor lblEndGameText = new Label("Je score is:", skin);
                Actor lblScore = new Label(Integer.toString(getSession().getScore().valueOf()), skin);
                btnContinue.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        btnContinue.remove();
                        lblEndGameText.remove();
                        lblScore.remove();
                        start(getSession().getScore());
                    }
                });

                lblEndGameText.setPosition(stage.getWidth() / 2 - lblEndGameText.getWidth() / 2, stage.getHeight() - 80);
                lblScore.setPosition(stage.getWidth() / 2 - lblScore.getWidth() / 2, stage.getHeight() - 100);
                btnContinue.setSize(200, 40);
                btnContinue.setPosition(stage.getWidth() / 2 - btnContinue.getWidth() / 2, stage.getHeight() / 4 - btnContinue
                        .getHeight() / 2);

                stage.addActor(btnContinue);
                stage.addActor(lblEndGameText);
                stage.addActor(lblScore);
                getSession().end();
                break;
        }
    }

    public void showPauseMenu() {
        pauseMenu.show(stage);
        pauseMenu.setPosition(stage.getWidth() / 2 - pauseMenu.getWidth() / 2, stage.getHeight() + pauseMenu.getHeight());

        MoveToAction action = Actions.action(MoveToAction.class);
        action.setPosition(stage.getWidth() / 2 - pauseMenu.getWidth() / 2, stage.getHeight() / 2 - pauseMenu.getHeight() / 2);
        action.setDuration(0.3f);
        action.setInterpolation(Interpolation.bounceOut);
        pauseMenu.addAction(action);

        getSession().showPauseMenu();
    }

    public void showPlayersView() {
        //Create player menu.
        playerMenu = new Dialog("Menu", skin) {
            {
                button("Oke");
            }
        };

        Table scrollTable = new Table(skin);

        ArrayList<String> people = new ArrayList<>();
        PacketGetKickInformation packetGetKickInfo = new PacketGetKickInformation();
        Client.getInstance().send(packetGetKickInfo);
        people.addAll(packetGetKickInfo.getResult());

        for (String person : people) {
            String[] peopleResult = person.split(" ");

            TextButton bKick = new TextButton("Kick", skin);
            TextButton bContinue = new TextButton("Doorgaan", skin);
            bContinue.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    PacketSetKickInformation packetKick = new PacketSetKickInformation(UUID.fromString(peopleResult[4]));
                    Client.getInstance().send(packetKick);

                    PacketGetKickInformation packetKickInfo = new PacketGetKickInformation();
                    Client.getInstance().send(packetKickInfo);
                    people.addAll(packetKickInfo.getResult());
                    playerMenu.hide();
                    showPlayersView();
                }
            }); 

            scrollTable.add(peopleResult[0]);
            scrollTable.columnDefaults(0);
            scrollTable.add(bKick);
            scrollTable.columnDefaults(1);
            scrollTable.add(peopleResult[1] + "/" + peopleResult[2]);
            scrollTable.columnDefaults(2);
            scrollTable.row();

            if (peopleResult[3].equals("0")) {
                bKick.setDisabled(true);
            }
        }

        playerMenu.add(scrollTable);

        playerMenu.show(stage);
        getSession().showPlayerMenu();
    }

    public void hidePlayersView() {
        getSession().showPlayerMenu();
    }

    @Override
    public void resume() {
        // TODO: If multiplayer (not host): Set the state of the client to unpause. If all clients are unpaused the game can resume as a whole. The host can always force a resume.
        //getSession().resume();
    }

    @Override
    public void pause() {
        // TODO: If multiplayer: Request pausing to server when it's enabled for this game.
        //getSession().pause();
    }

    @Override
    public void dispose() {
        // Once disposed. Assets cannot be reloaded.
        Assets.dispose();
        clearRenderers();
    }
}
