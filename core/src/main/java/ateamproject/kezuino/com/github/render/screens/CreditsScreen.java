/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import java.util.ArrayList;
import java.util.Collections;
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
    private final int titelSpacingVertical = 40;
    private final int contentSpacingHorizontal = 15;
    private final int contentSpacingVertical = 30;

    private final int maxNumberOfModifieingThreadsRandom;
    private final int maxNumberOfModifieingThreadsRow;

    private final AtomicInteger numberOfModifieingThreadsRandom;
    private final AtomicInteger numberOfModifieingThreadsRow;

    public CreditsScreen(Game game) {
        super(game);
        this.maxNumberOfModifieingThreadsRandom = 5;
        this.maxNumberOfModifieingThreadsRow = 2;

        this.numberOfModifieingThreadsRow = new AtomicInteger(0);
        this.numberOfModifieingThreadsRandom = new AtomicInteger(0);

        content = new ArrayList<>();
        titelLabels = new ArrayList<>();
        contentLabels = new ArrayList<>();
        titelLabelsModified = new CopyOnWriteArrayList();
        contentLabelsModified = new CopyOnWriteArrayList();

        this.titel = "The A - Team";
        content.add("Lead developer: Fatih Taskent,");
        content.add("Logic/Netcode: Anton van Dijk,");
        content.add("Logic/Clansystem: Jip van de Vijfeijke,");
        content.add("Logic/Synchronisation: Ken van de Linde,");
        content.add("Graphics/Testing: David Vlijmincx,");
        content.add("Documentation/Support: Sven Keunen");

        backgroundMusic = Assets.getMusicStream("credits.mp3");

        Image i = new Image(new Texture("gui/credits.png"));
        i.setFillParent(true);
        stage.addActor(i);

        final Thread head = Thread.currentThread();

        TextButton exit = new TextButton("X", skin);
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                head.interrupt();
                game.setScreen(new MainScreen(game));
            }
        });
        exit.setColor(Color.CYAN);
        exit.setPosition(20, 20);
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
            label.setFontScale(4);
            label.setColor(Color.YELLOW);
            stage.addActor(label);

            lastHorizontalPositionTitel += this.titelSpacingHorizontal;
        }

        int lineNumber = 1;
        float lastHorizontalPositionContent = 0;
        for (Label label : contentLabels) {
            label.setPosition(lastHorizontalPositionContent, stage.getHeight() - 60 - (this.contentSpacingVertical * lineNumber));
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
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                ArrayList<Thread> threads = new ArrayList<>();

                Random r = new Random();
                if (r.nextBoolean()) {
                    while (!Thread.currentThread().isInterrupted()) {
                        if (numberOfModifieingThreadsRandom.get() <= maxNumberOfModifieingThreadsRandom) {
                            numberOfModifieingThreadsRandom.incrementAndGet();
                            Thread t = new Thread(randomModifier);
                            t.start();
                            threads.add(t);
                        }
                    }
                } else {
                    while (!Thread.currentThread().isInterrupted()) {
                        if (numberOfModifieingThreadsRow.get() <= maxNumberOfModifieingThreadsRow) {
                            numberOfModifieingThreadsRow.incrementAndGet();
                            Thread t = new Thread(rowModifier);
                            t.start();
                            threads.add(t);
                        }
                    }
                }

                for (Thread thread : threads) {
                    thread.interrupt();
                }
            }
        });
        t.start();
    }

    private void singleStep(Label label, float x, float y, boolean randomColor) {
        Gdx.app.postRunnable(new Runnable() {

            @Override
            public void run() {
                label.setPosition(x, y);
                if (randomColor) {
                    Random r = new Random();
                    Color initRandomColor = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1);
                    label.setColor(initRandomColor);
                }
            }
        });
    }

    private Runnable randomModifier = new Runnable() {
        @Override
        public void run() {
            Random r = new Random();

            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Label currentLabel = contentLabels.get(r.nextInt(contentLabels.size()));
                    if (contentLabelsModified.contains(currentLabel)) {
                        continue;
                    } else {
                        contentLabelsModified.add(currentLabel);
                    }

                    float height = currentLabel.getY();
                    String pattern = "+1+1+1+1+1+1+1+1+1+1-1-1-1-1-1-1-1-1-1-1+1+1+1+1+1-1-1-1-1-1+1+1-1-1";

                    for (int i = 0; i < pattern.length(); i += 2) {
                        boolean randomColor = false;
                        if (pattern.substring(i, i + 1).equals("+")) {
                            height += Float.parseFloat(pattern.substring(i + 1, i + 2));
                        } else {
                            height -= Float.parseFloat(pattern.substring(i + 1, i + 2));
                        }

                        if (pattern.length() == i) {
                            randomColor = true;
                        }

                        singleStep(currentLabel, currentLabel.getX(), height, randomColor);
                        Thread.sleep(30);
                    }

                    contentLabelsModified.remove(currentLabel);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CreditsScreen.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    Thread.sleep(r.nextInt(255));
                } catch (InterruptedException ex) {
                    Logger.getLogger(CreditsScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

    private Runnable rowModifier = new Runnable() {
        @Override
        public void run() {
            Random r = new Random();

            float y = contentLabels.get(r.nextInt(contentLabels.size())).getY();
            ArrayList<Label> labels = new ArrayList<>();
            for (Label label : contentLabels) {
                if (label.getY() == y) {
                    contentLabelsModified.add(label);
                    labels.add(label);
                }
            }

            for (Label label : labels) {
                System.out.println(label.getText());
            }

            for (Label label : labels) {
                final String pattern = "+1+1+1+1+1+1+1+1+1+1-1-1-1-1-1-1-1-1-1-1+1+1+1+1+1-1-1-1-1-1+1+1-1-1";

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < pattern.length(); i += 2) {
                            if (Thread.currentThread().isInterrupted()) {
                                break;
                            }

                            boolean randomColor = false;
                            float height = label.getY();

                            if (pattern.substring(i, i + 1).equals("+")) {
                                height += Float.parseFloat(pattern.substring(i + 1, i + 2));
                            } else {
                                height -= Float.parseFloat(pattern.substring(i + 1, i + 2));
                            }

                            if (pattern.length() == i) {
                                randomColor = true;
                            }

                            singleStep(label, label.getX(), height, randomColor);
                            try {
                                Thread.sleep(30);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(CreditsScreen.class.getName()).log(Level.SEVERE, null, ex);
                            }
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
