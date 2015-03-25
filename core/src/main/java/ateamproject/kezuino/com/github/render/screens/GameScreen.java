/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.pathfinding.GameObjectPathfinding;
import ateamproject.kezuino.com.github.render.IRenderer;
import ateamproject.kezuino.com.github.render.orthographic.GameRenderer;
import ateamproject.kezuino.com.github.singleplayer.GameSession;
import ateamproject.kezuino.com.github.singleplayer.Map;
import ateamproject.kezuino.com.github.utility.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * @author Anton
 */
public class GameScreen implements Screen {
    private Game game;
    private Stage stage;

    private GameSession session;
    private IRenderer renderer;

    public GameScreen(Game game){
        this.game = game;
        
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
        //Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    }
    
    @Override
    public void show() {
        // Initialize screen here.
        Assets.create();

        GameObjectPathfinding g = new GameObjectPathfinding();
        GameSession gs = new GameSession();
        gs.setMap(5);
        gs.getMap().getNode(0, 1).setWall();
        gs.getMap().getNode(1, 1).setWall();
        gs.getMap().getNode(0, 2).setWall();
        gs.getMap().getNode(0, 3).setWall();
        gs.getMap().getNode(2, 1).setWall();
        gs.getMap().getNode(3, 1).setWall();
        gs.getMap().getNode(3, 2).setWall();
        gs.getMap().getNode(1, 4).setWall();
        g.generatePath(gs.getMap().getNode(1, 3), gs.getMap().getNode(0, 0));

        renderer = new GameRenderer(gs.getMap());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        // Reset controls of this screen to align with new resolution.
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
    public void hide() {

    }

    @Override
    public void dispose() {
        Assets.dispose();
    }
}
