/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import ateamproject.kezuino.com.github.utility.assets.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author David
 */
public class ClanManagementScreen extends BaseScreen {

    private enum invitationType {

        uitnodigen, accepteren, nothing;
    }

    private enum managementType {

        verwijderen, verlaten, afwijzen;
    }

    private TextField tfClannaam;
    private final Table scrollTable;
    private Connection connect = null;

    public ClanManagementScreen(Game game) {
        super(game);

        TextButton btnChangeName = new TextButton("Naam wijzigen", skin);
        TextField tfName = new TextField("", skin);
        Label lbUsername = new Label("Gebruikersnaam", skin);

        TextButton btnClanToevoegen = new TextButton("Clan toevoegen", skin);
        tfClannaam = new TextField("", skin);
        Label lbClannaam = new Label("Clan naam", skin);
        btnClanToevoegen.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                createClan(tfClannaam.getText());
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

        scrollTable = new Table();
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

        if (makeConnection()) {
            fillTable("jip.vandevijfeijke@gmail.com");//will fill table with all asociated clans
        }
        else{//general connection error has happened
            game.setScreen(new MainScreen(game));
        }
    }

    private void fillTable(String emailaddress) {
        ArrayList<String> clans = new ArrayList();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connect.prepareStatement("SELECT Id FROM clan WHERE Email = ?");
            preparedStatement.setString(1, emailaddress);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ClanManagementScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            int id = resultSet.getInt("Id");

            preparedStatement = connect.prepareStatement("SELECT ClanId FROM clan_account WHERE AccountId = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ClanManagementScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("ClanId");

                preparedStatement = connect.prepareStatement("SELECT Name FROM clan WHERE Id = ?");
                preparedStatement.setInt(1, id);
                resultSet = preparedStatement.executeQuery();

                generateTableRow(resultSet.getString("Name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClanManagementScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (String clan : clans) {
            generateTableRow(clan);//use clan name to generate table rows
        }
    }

    private void createClan(String clanName) {
        if (!clanExists(clanName)) {
            tfClannaam.setText("");
            generateTableRow(clanName);
        }
    }

    private void generateTableRow(String clanName) {
        TextField lb1 = new TextField(clanName, skin);
        lb1.setDisabled(true);

        String bt2Text = getInvitation(clanName).toString();
        TextButton bt2 = new TextButton(bt2Text, skin);
        if (bt2Text.equals("nothing")) {
            bt2 = new TextButton("", skin);
        }
        final invitationType iType = invitationType.valueOf(bt2.getText().toString());
        bt2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleInvitation(iType, lb1.getText());
            }
        });

        TextField lb3 = new TextField(getPersons(clanName), skin);
        lb3.setDisabled(true);

        TextButton bt4 = new TextButton(getManagement(clanName).toString(), skin);
        final managementType iManage = managementType.valueOf(bt4.getText().toString());
        bt4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleManagement(iManage, lb1.getText());
            }
        });

        scrollTable.add(lb1);
        scrollTable.columnDefaults(0);
        scrollTable.add(bt2);
        scrollTable.columnDefaults(1);
        scrollTable.add(lb3);
        scrollTable.columnDefaults(2);
        scrollTable.add(bt4);
        scrollTable.columnDefaults(3);
        scrollTable.row();
    }

    private boolean clanExists(String clanName) {
        return false;
    }

    private invitationType getInvitation(String clanName) {
        return invitationType.accepteren;
    }

    private managementType getManagement(String clanName) {
        return managementType.afwijzen;
    }

    private String getPersons(String clanName) {
        return "personen 3/8";
    }

    private void handleInvitation(invitationType invite, String clanName) {
        if (invite.equals(invitationType.accepteren)) {

        } else if (invite.equals(invitationType.uitnodigen)) {

        }
    }

    private void handleManagement(managementType manage, String clanName) {
        if (manage.equals(managementType.afwijzen)) {

        } else if (manage.equals(managementType.verlaten)) {

        } else if (manage.equals(managementType.verwijderen)) {

        }
    }

    private boolean makeConnection() {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");

            // Setup the connection with the DB
            connect = DriverManager.getConnection("jdbc:mysql://phpmyadmin.darkhellentertainment.com/pactales","pactales","p@2015");
            
            return true;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClanManagementScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClanManagementScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
}
