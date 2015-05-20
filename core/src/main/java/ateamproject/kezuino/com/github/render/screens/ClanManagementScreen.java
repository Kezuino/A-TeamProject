/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.render.screens.ClanFunctions.invitationType;
import ateamproject.kezuino.com.github.render.screens.ClanFunctions.managementType;
import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

/**
 * @author David
 */
public class ClanManagementScreen extends BaseScreen {

    private final Table scrollTable;
    private String emailaddress = "jip.vandevijfeijke@gmail.com";
    private ClanFunctions clanF;//should be on server using RMI!!!
        if (!clanF.getHasConnection()) {
            Dialog d = new Dialog("error", skin);
            d.add("Er kan geen verbinding met de database worden gemaakt!");
            TextButton bExit = new TextButton("Oke", skin);
            bExit.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    d.hide();
                }
            });
            d.add(bExit);
            d.show(stage);
            game.setScreen(new MainScreen(game));
        } else {
            refreshScreen();//loads up whole screen
        }
    private void refreshScreen() {
        scrollTable.clear();
    private String emailaddress = "jip.vandevijfeijke@gmail.com";
    private ClanFunctions clanF;//should be on server using RMI!!!

        scrollTable = new Table();

        clanF = new ClanFunctions();
        if (!clanF.getHasConnection()) {
            Dialog d = new Dialog("error", skin);
            d.add("Er kan geen verbinding met de database worden gemaakt!");
            TextButton bExit = new TextButton("Oke", skin);
            bExit.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    d.hide();
                }
            });
            d.add(bExit);
            d.show(stage);
            game.setScreen(new MainScreen(game));
        } else {
            refreshScreen();//loads up whole screen
        }
    }

    private void refreshScreen() {
        scrollTable.clear();

        scrollTable = new Table();

        clanF = new ClanFunctions();
        if (!clanF.getHasConnection()) {
            Dialog d = new Dialog("error", skin);
            d.add("Er kan geen verbinding met de database worden gemaakt!");
            TextButton bExit = new TextButton("Oke", skin);
            bExit.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    d.hide();
                }
            });
            d.add(bExit);
            d.show(stage);
            game.setScreen(new MainScreen(game));
        } else {
            refreshScreen();//loads up whole screen
        }
    }

    private void refreshScreen() {
        scrollTable.clear();

        TextButton btnChangeName = new TextButton("Naam wijzigen", skin);
        TextField tfName = new TextField(clanF.getUsername(emailaddress), skin);
        Label lbUsername = new Label("Gebruikersnaam", skin);
        btnChangeName.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!tfName.getText().equals("")) {
                    if (clanF.setUsername(tfName.getText(), emailaddress)) {
                        Dialog d = new Dialog("succes", skin);
                        d.add("Naam succesvol aangepast");
                        TextButton bExit = new TextButton("Oke", skin);
                        bExit.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                d.hide();
                            }
                        });
                        d.add(bExit);
                        d.show(stage);
                    } else {
                        Dialog d = new Dialog("error", skin);
                        d.add("De naam kan niet worden aangepast omdat de naam al bestaat");
                        TextButton bExit = new TextButton("Oke", skin);
                        bExit.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                d.hide();
                            }
                        });
                        d.add(bExit);
                        d.show(stage);
                    }
                }
            }
        });

        TextButton btnClanToevoegen = new TextButton("Clan toevoegen", skin);
        tfClannaam = new TextField("", skin);
        Label lbClannaam = new Label("Clan naam", skin);
        btnClanToevoegen.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!tfClannaam.getText().equals("")) {
                    if (!clanF.createClan(tfClannaam.getText(), emailaddress)) {
                        Dialog d = new Dialog("error", skin);
                        d.add("Maximum van 8 clans overschreden of de clan bestaat al");
                        TextButton bExit = new TextButton("Oke", skin);
                        bExit.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                d.hide();
                            }
                        });
                        d.add(bExit);
                        d.show(stage);
                    } else {
                        Dialog d = new Dialog("succes", skin);
                        d.add("Clan succesvol toegevoegd");
                        TextButton bExit = new TextButton("Oke", skin);
                        bExit.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                d.hide();
                            }
                        });
                        d.add(bExit);
                        d.show(stage);
                        generateTableRow(tfClannaam.getText());
                        tfClannaam.setText("");
                    }
                }
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                d.hide();
                            }
                        });
                        d.add(bExit);
                        d.show(stage);
                    } else {
                        Dialog d = new Dialog("succes", skin);
                        d.add("Clan succesvol toegevoegd");
                        TextButton bExit = new TextButton("Oke", skin);
                        bExit.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                d.hide();
                            }
                        });
                        d.add(bExit);
                        d.show(stage);
                        generateTableRow(tfClannaam.getText());
                        tfClannaam.setText("");
                    }
                }
                }
            }
        });

        TextButton btnTerug = new TextButton("Terug", skin);
        btnTerug.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });

        ScrollPane spClanControl = new ScrollPane(btnTerug, skin);

        btnChangeName.setSize(200, 40);
        btnChangeName.setPosition(420, stage.getHeight() - 50);

        lbUsername.setSize(200, 40);
        lbUsername.setPosition(40, stage.getHeight() - 50);

        tfName.setSize(200, 40);
        tfName.setPosition(220, stage.getHeight() - 50);

        tfClannaam.setSize(200, 40);
        tfClannaam.setPosition(220, stage.getHeight() - 100);

        btnClanToevoegen.setSize(200, 40);
        btnClanToevoegen.setPosition(420, stage.getHeight() - 100);

        lbClannaam.setSize(200, 40);
        lbClannaam.setPosition(40, stage.getHeight() - 100);

        btnTerug.setSize(200, 40);
        btnTerug.setPosition(stage.getWidth() / 2 - 50, 50);

        spClanControl.setSize(200, 40);

        stage.addActor(spClanControl);
        stage.addActor(tfClannaam);
        stage.addActor(btnClanToevoegen);
        stage.addActor(lbClannaam);
        stage.addActor(btnTerug);
        stage.addActor(btnChangeName);
        stage.addActor(lbUsername);
        stage.addActor(tfName);

        TextField lb1 = new TextField("clan naam", skin);
        lb1.setDisabled(true);
        TextField lb2 = new TextField("uitnodigingen", skin);
        lb2.setDisabled(true);
        TextField lb3 = new TextField("personen", skin);
        lb3.setDisabled(true);
        TextField lb4 = new TextField("beheer", skin);
        lb4.setDisabled(true);

        Pixmap pm1 = new Pixmap(1, 1, Format.RGB565);
        pm1.setColor(Color.GREEN);
        pm1.fill();

        scrollTable.add(lb1);
        scrollTable.columnDefaults(0);
        scrollTable.add(lb2);
        scrollTable.columnDefaults(1);
        scrollTable.add(lb3);
        scrollTable.columnDefaults(2);
        scrollTable.add(lb4);
        scrollTable.columnDefaults(3);
        scrollTable.row();
        scrollTable.setColor(com.badlogic.gdx.graphics.Color.BLUE);
        final ScrollPane scroller = new ScrollPane(scrollTable);
        scroller.sizeBy(200, 400);
        scroller.setColor(com.badlogic.gdx.graphics.Color.BLUE);
        final Table table = new Table();
        table.setFillParent(false);
        table.add(scroller).fill().expand();
        table.setSize(stage.getWidth(), 200);
        table.setColor(com.badlogic.gdx.graphics.Color.BLUE);
        float xOfLoginButton = stage.getWidth() / 2 - table.getWidth() / 2;
        float yOfLoginButton = stage.getHeight() / 2 - table.getHeight() / 2;

        table.setPosition(xOfLoginButton, yOfLoginButton);
        this.stage.addActor(table);

        backgroundMusic = Assets.getMusicStream("menu.mp3");

        for (String clan : clanF.fillTable(emailaddress)) {
            generateTableRow(clan);
        }
    }

    private void generateTableRow(String clanName) {
        TextField lb1 = new TextField(clanName, skin);
        lb1.setDisabled(true);

        String bt2Text = clanF.getInvitation(clanName, emailaddress).toString();
        TextButton bt2 = new TextButton(bt2Text, skin);
        if (bt2Text.equals("nothing")) {
            bt2.setVisible(false);
        }
        final invitationType iType = clanF.getInvitation(clanName, emailaddress);
        bt2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (clanF.handleInvitation(iType, lb1.getText(), emailaddress, "test")) {
                    Dialog d = new Dialog("succes", skin);
                    d.add("Actie succesvol uitgevoerd");
                    TextButton bExit = new TextButton("Oke", skin);
                    bExit.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            d.hide();
                        }
                    });
                    d.add(bExit);
                    d.show(stage);
                } else {
                    Dialog d = new Dialog("error", skin);
                    d.add("De persoon bestaat niet");
                    TextButton bExit = new TextButton("Oke", skin);
                    bExit.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            d.hide();
                        }
                    });
                    d.add(bExit);
                    d.show(stage);
                }
            }

        }
        );

        TextField lb3 = new TextField(clanF.getPersons(clanName), skin);

        lb3.setDisabled(
                true);

        TextButton bt4 = new TextButton(clanF.getManagement(clanName, emailaddress).toString(), skin);
        final managementType iManage = managementType.valueOf(bt4.getText().toString());

        bt4.addListener(
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y
                    ) {
                        if (clanF.handleManagement(iManage, lb1.getText(), emailaddress)) {
                            refreshScreen();//make sure that the changes will be reflected
                            Dialog d = new Dialog("succes", skin);
                            d.add("Actie succesvol uitgevoerd");
                            TextButton bExit = new TextButton("Oke", skin);
                            bExit.addListener(new ClickListener() {
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    d.hide();
                                }
                            });
                            d.add(bExit);
                            d.show(stage);
                        } else {
                            Dialog d = new Dialog("error", skin);
                            d.add("Actie is helaas mislukt");
                            TextButton bExit = new TextButton("Oke", skin);
                            bExit.addListener(new ClickListener() {
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    d.hide();
                                }
                            });
                            d.add(bExit);
                            d.show(stage);
                        } else {
                            Dialog d = new Dialog("error", skin);
                            d.add("Actie is helaas mislukt");
                            TextButton bExit = new TextButton("Oke", skin);
                            bExit.addListener(new ClickListener() {
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    d.hide();
                                }
                            });
                            d.add(bExit);
                            d.show(stage);
                                }
                            });
                            d.add(bExit);
                            d.show(stage);
                        }
                    }
                }
        );

        scrollTable.add(lb1);

        scrollTable.columnDefaults(
                0);
        scrollTable.add(bt2);

        scrollTable.columnDefaults(
                1);
        scrollTable.add(lb3);

        scrollTable.columnDefaults(
                2);
        scrollTable.add(bt4);

        scrollTable.columnDefaults(
                3);
        scrollTable.row();
    }
}
