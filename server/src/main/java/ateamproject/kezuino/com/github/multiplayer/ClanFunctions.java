/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.multiplayer;

import ateamproject.kezuino.com.github.render.screens.ClanManagementScreen;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jip
 */
public class ClanFunctions {

    private enum invitationType {

        uitnodigen, accepteren, nothing;
    }

    private enum managementType {

        verwijderen, verlaten, afwijzen;
    }

    private Connection connect = null;

    public ClanFunctions() {
        makeConnection();
    }

    private ArrayList<String> fillTable(String emailaddress) {
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

                clans.add(resultSet.getString("Name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClanManagementScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

        return clans;
    }

    public boolean createClan(String clanName, String emailAddress) {
        if (!clanExists(clanName)) {
            if (hasRoomForClan(emailAddress)) {
                generateTableRow(clanName);
            }
        }
    }

    private boolean hasRoomForClan(String emailaddress) {
        return true;//there should be max 8 clans per email
    }

    private boolean makeConnection() {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");

            // Setup the connection with the DB
            connect = DriverManager.getConnection("jdbc:mysql://localhost/pactales", "pactales", "p@2015");

            return true;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClanManagementScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClanManagementScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
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

    private void handleInvitation(invitationType invite, String clanName, String emailAddress) {
        if (invite.equals(invitationType.accepteren)) {

        } else if (invite.equals(invitationType.uitnodigen)) {

        }
    }

    private void handleManagement(managementType manage, String clanName, String emailAddress) {
        if (manage.equals(managementType.afwijzen)) {

        } else if (manage.equals(managementType.verlaten)) {

        } else if (manage.equals(managementType.verwijderen)) {

        }
    }
}
