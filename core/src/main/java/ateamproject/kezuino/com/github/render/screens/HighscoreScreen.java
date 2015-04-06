/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


/**
 * @author Sven
 */
public class HighscoreScreen extends BaseScreen {
    public HighscoreScreen(Game game) {
        super(game);
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/Background.mp3"));
        if (sound!=null){
        sound.loop();
        }

        TextButton btnBack = new TextButton("Terug", skin);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (sound!=null){
                sound.dispose();
                }
                game.setScreen(new MainScreen(game));
            }
        });
        float x = 240;
        float y = stage.getHeight() / 4;
        btnBack.setSize(200, 40);
        btnBack.setPosition(stage.getWidth() / 2 - btnBack.getWidth() / 2, stage.getHeight() / 4 - btnBack.getHeight() / 2);


        Label lblTitle = new Label("Highscore", skin);
        lblTitle.setColor(Color.YELLOW);
        lblTitle.setPosition(stage.getWidth() / 2 - lblTitle.getWidth() / 2, stage.getHeight() - 50);

        Map<String, Integer> scores = new HashMap<>();
        ValueComparator bvc = new ValueComparator(scores);
        TreeMap<String, Integer> sortedScores = new TreeMap<>(bvc);

        // TODO: Fill with real data.
        scores.put("Team8", 345345);
        scores.put("Winter Wolfs", 454646);
        scores.put("Meet Your Makers", 4533);
        scores.put("CLG", 45364745);
        scores.put("Unicorns of Love", 2114342343);
        scores.put("F'natic", 8685676);
        scores.put("Unforce", 45657474);
        scores.put("Team Dignitas", 763748335);
        scores.put("Cloud 9", 243422);
        scores.put("TSM", 66362);

        sortedScores.putAll(scores);

        Table table = new Table();
        table.setSkin(skin);

        int rankNr = 0;

        for (Map.Entry<String, Integer> entry : sortedScores.entrySet()) {
            rankNr++;

            table.add(new Label(Integer.toString(rankNr), skin)).padRight(50);
            table.add(new Label(entry.getKey(), skin)).padRight(50);
            table.add(new Label(Integer.toString(entry.getValue()), skin));
            table.row();
        }

        table.setPosition(stage.getWidth() / 2 - table.getWidth() / 2, stage.getHeight() - 190);

        stage.addActor(btnBack);
        stage.addActor(lblTitle);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render(delta);
    }
}

/**
 * Tryout for sorting (Tree)Map
 *
 * @author Ken van de Linde
 */
class ValueComparator implements Comparator<String> {
    Map<String, Integer> base;

    public ValueComparator(Map<String, Integer> base) {
        this.base = base;
    }

    @Override
    public int compare(String a, String b) {
        return base.get(b).compareTo(base.get(a));
    }

}
