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
import ateamproject.kezuino.com.github.utility.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * @author Anton
 */
public class GameScreen extends BaseScreen {

    private GameSession session;
    private IRenderer renderer;

    public GameScreen(Game game) {
        super(game);
        Assets.create();

        session = new GameSession();
        session.setMap(Map.load(session, "maps/level/0.tmx"));
        renderer = new GameRenderer(session.getMap());
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
