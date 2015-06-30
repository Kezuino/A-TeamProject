/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author David
 */
public class CreditsScreen extends BaseScreen {

    private String titel;
    private ArrayList<String> content;
    private ArrayList<Label> titelLabels;
    private ArrayList<Label> contentLabels;
    private CopyOnWriteArrayList<Label> titelLabelsModified;
    private CopyOnWriteArrayList<Label> contentLabelsModified;

    private final int titelSpacingHorizontal = 40;
    private final int titelSpacingVertical = 55;
    private final int contentSpacingHorizontal = 15;
    private final int contentSpacingVertical = 30;

    private final int maxNumberOfModifieingThreadsRandom;
    private final int maxNumberOfModifieingThreadsRow;

    private final AtomicInteger numberOfModifieingThreadsRandom;
    private final AtomicInteger numberOfModifieingThreadsRow;

    private LabelStyle titelFont;
    private LabelStyle contentFont;

    private Random rand;

    public CreditsScreen(Game game) {
        super(game);
        this.maxNumberOfModifieingThreadsRandom = 40;
        this.maxNumberOfModifieingThreadsRow = 1;

        this.numberOfModifieingThreadsRow = new AtomicInteger(0);
        this.numberOfModifieingThreadsRandom = new AtomicInteger(0);

        content = new ArrayList<>();
        titelLabels = new ArrayList<>();
        contentLabels = new ArrayList<>();
        titelLabelsModified = new CopyOnWriteArrayList<>();
        contentLabelsModified = new CopyOnWriteArrayList<>();

        this.titel = "The A - Team";
        content.add("* Lead developer: Fatih Taskent*,");
        content.add("* Logic/Netcode: Anton van Dijk*,");
        content.add("* Logic/Clansystem: Jip van de Vijfeijke*,");
        content.add("* Logic/Syncing: Ken van de Linde*,");
        content.add("* Graphics/Testing: David Vlijmincx*,");
        content.add("* Documentation/Support: Sven Keunen*");

        backgroundMusic = Assets.getMusicStream("credits.mp3");
        backgroundMusic.setPosition(0);
        clearOnRenderColor = Color.BLACK;

        titelFont = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("fonts/credits.fnt"), Gdx.files.internal("fonts/credits_0.png"), false), Color.BLUE);
        contentFont = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("fonts/credits.fnt"), Gdx.files.internal("fonts/credits_0.png"), false), Color.WHITE);

        rand = new Random();

        Image i = new Image(new Texture("gui/credits.png"));
        i.setFillParent(true);
        stage.addActor(i);

        final Thread head = Thread.currentThread();

        TextButton exit = new TextButton("", skin);
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                head.interrupt();
                game.setScreen(new MainScreen(game));
            }
        });
        exit.setColor(Color.DARK_GRAY);
        exit.setPosition(stage.getWidth() - 35, stage.getHeight()- 30);
        stage.addActor(exit);

        createLabels();
        placeLabels();
        modifyLabels();
    }

    private void createLabels() {
        for (char letter : titel.toCharArray()) {
            Label temp = new Label(String.valueOf(letter), skin);
            titelLabels.add(temp);
        }

        for (String line : content) {
            for (char letter : line.toCharArray()) {
                Label temp = new Label(String.valueOf(letter), skin);
                contentLabels.add(temp);
            }
        }
    }

    private void placeLabels() {
        float lastHorizontalPositionTitel = 0;
        for (Label label : titelLabels) {
            label.setPosition(lastHorizontalPositionTitel, stage.getHeight() - this.titelSpacingVertical);
            label.setFontScale(3);
            label.setStyle(titelFont);
            stage.addActor(label);

            lastHorizontalPositionTitel += this.titelSpacingHorizontal;
        }

        int lineNumber = 1;
        float lastHorizontalPositionContent = 0;
        for (Label label : contentLabels) {
            label.setPosition(lastHorizontalPositionContent, stage.getHeight() - 60 - (this.contentSpacingVertical * lineNumber));
            label.setStyle(contentFont);
            label.setColor(Color.BLACK);

            if (!label.getText().toString().equals(",")) {
                stage.addActor(label);
                lastHorizontalPositionContent += this.contentSpacingHorizontal;
            } else {
                lineNumber++;
                lastHorizontalPositionContent = 0;
            }
        }
    }

    private void modifyLabels() {
        Thread t = new Thread(() -> {
            ArrayList<Thread> threads = new ArrayList<>();

            if (rand.nextBoolean()) {
                while (!Thread.currentThread().isInterrupted()) {
                    if (numberOfModifieingThreadsRandom.get() < maxNumberOfModifieingThreadsRandom) {
                        numberOfModifieingThreadsRandom.incrementAndGet();
                        Thread t1 = new Thread(randomModifier);
                        t1.start();
                        threads.add(t1);
                    }
                }
            } else {
                while (!Thread.currentThread().isInterrupted()) {
                    if (numberOfModifieingThreadsRow.get() < maxNumberOfModifieingThreadsRow) {
                        numberOfModifieingThreadsRow.incrementAndGet();
                        Thread t1 = new Thread(rowModifier);
                        t1.start();
                        threads.add(t1);
                    }
                }
            }

            for (Thread thread : threads) {
                thread.interrupt();
            }
        });
        t.start();
    }

    private void singleStep(Label label, float x, float y) {
        Gdx.app.postRunnable(() -> {
            label.setPosition(x, y);
            Color initRandomColor = new Color(rand.nextFloat(), rand.nextFloat(),255, 1);
            label.setColor(initRandomColor);
        });
    }

    private Runnable randomModifier = new Runnable() {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Label currentLabel = contentLabels.get(rand.nextInt(contentLabels.size()));
                    if (contentLabelsModified.contains(currentLabel)) {
                        continue;
                    } else {
                        contentLabelsModified.add(currentLabel);
                    }

                    float height = currentLabel.getY();
                    String pattern = "+1+1+1+1+1+1+1+1+1+1-1-1-1-1-1-1-1-1-1-1+1+1+1+1+1-1-1-1-1-1+1+1-1-1";

                    for (int i = 0; i < pattern.length(); i += 2) {
                        if (pattern.substring(i, i + 1).equals("+")) {
                            height += Float.parseFloat(pattern.substring(i + 1, i + 2));
                        } else {
                            height -= Float.parseFloat(pattern.substring(i + 1, i + 2));
                        }

                        singleStep(currentLabel, currentLabel.getX(), height);
                        Thread.sleep(30);
                    }

                    contentLabelsModified.remove(currentLabel);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CreditsScreen.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    Thread.sleep(rand.nextInt(255));
                } catch (InterruptedException ex) {
                    Logger.getLogger(CreditsScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

    private Runnable rowModifier = new Runnable() {
        @Override
        public void run() {
            float y = contentLabels.get(rand.nextInt(contentLabels.size())).getY();
            ArrayList<Label> labels = new ArrayList<>();
            for (Label label : contentLabels) {
                if (label.getY() == y) {
                    contentLabelsModified.add(label);
                    labels.add(label);
                }
            }

            for (Label label : labels) {
                final String pattern = "+1+1+1+1+1+1+1+1+1+1-1-1-1-1-1-1-1-1-1-1+1+1+1+1+1-1-1-1-1-1+1+1-1-1";

                Thread t = new Thread(() -> {
                    for (int i = 0; i < pattern.length(); i += 2) {
                        if (Thread.currentThread().isInterrupted()) {
                            break;
                        }

                        float height = label.getY();

                        if (pattern.substring(i, i + 1).equals("+")) {
                            height += Float.parseFloat(pattern.substring(i + 1, i + 2));
                        } else {
                            height -= Float.parseFloat(pattern.substring(i + 1, i + 2));
                        }

                        singleStep(label, label.getX(), height);
                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(CreditsScreen.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                t.start();
                try {
                    Thread.sleep(60);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CreditsScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for (Label label : contentLabels) {
                if (label.getY() == y) {
                    contentLabelsModified.remove(label);
                }
            }
            numberOfModifieingThreadsRow.decrementAndGet();
        }
    };
}
