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
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * @author David
 */
public class ClanManagementScreen extends BaseScreen {

    private Table scrollTable;
    private String emailaddress = "jip.vandevijfeijke@gmail.com";
    private ClanFunctions clanF;//should be on server using RMI!!!

    public ClanManagementScreen(Game game) {
        super(game);
    }

    private void refreshScreen() {
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
                    public void clicked(InputEvent event, float x, float y) {
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
                        }
                    }
                });

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
