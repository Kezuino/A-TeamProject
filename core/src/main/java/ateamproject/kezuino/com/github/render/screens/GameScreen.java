/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.network.packet.packets.PacketGetKickInformation;
import ateamproject.kezuino.com.github.network.packet.packets.PacketKick;
import ateamproject.kezuino.com.github.network.packet.packets.PacketLoginAuthenticate;
import ateamproject.kezuino.com.github.network.packet.packets.PacketLoginUserExists;
import ateamproject.kezuino.com.github.network.packet.packets.PacketSetKickInformation;
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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Anton
 */
public class GameScreen extends BaseScreen {

    private GameSession session;
    private Pactale player;
    private GameRenderer gameRenderer;
    private Button b;
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
        Dialog d = new Dialog("Menu", skin);

        TextButton bContinue = new TextButton("Doorgaan", skin);
        bContinue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                d.hide();
            }
        });
        d.add(bContinue);

        TextButton bExit = new TextButton("Verlaten", skin);
        bExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PacketKick packet = new PacketKick(PacketKick.KickReasonType.GAME, "Leaved through menu");
                Client.getInstance().send(packet);

                game.setScreen(new MainScreen(game));
            }
        });

        d.add(bExit);
        d.show(stage);
        this.session.showPauseMenu();
    }

    public void hidePauseView() {
        this.session.showPauseMenu();
    }

    public void showPlayersView() {
        Dialog d = new Dialog("Menu", skin);

        TextButton bContinue = new TextButton("Oke", skin);
        bContinue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                d.hide();
            }
        });
        d.add(bContinue);

        Table scrollTable = new Table(skin);

        ArrayList<String> peoples = new ArrayList<>();
        PacketGetKickInformation packetKickInfo = new PacketGetKickInformation();
        Client.getInstance().send(packetKickInfo);
        peoples.addAll(packetKickInfo.getResult());

        for (String people : peoples) {
            String[] peopleResult = people.split(" ");

            TextButton bKick = new TextButton("Kick", skin);
            bContinue.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    PacketSetKickInformation packetKick = new PacketSetKickInformation(UUID.fromString(peopleResult[4]));
                    Client.getInstance().send(packetKick);

                    PacketGetKickInformation packetKickInfo = new PacketGetKickInformation();
                    Client.getInstance().send(packetKickInfo);
                    peoples.addAll(packetKickInfo.getResult());
                    d.hide();
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

        d.add(scrollTable);
        d.show(stage);
        this.session.showPlayerMenu();
    }

    public void hidePlayersView() {
        this.session.showPlayerMenu();
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
