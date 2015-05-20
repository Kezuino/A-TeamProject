/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jip
 */
public class ClanFunctions {

    private Connection connect = null;
    private boolean hasConnection = false;

    /**
     * Constructor which will initialize clanfunctions
     */
    public ClanFunctions() {
        hasConnection = makeConnection();
    }

    /**
     * Returns true if a connection could be made.
     *
     * @return True if succeeded, false otherwise.
     */
    public boolean getHasConnection() {
        return hasConnection;
    }

    /**
     * Gives all clans for a specific {@code emailaddress}.
     *
     * @param emailaddress the emailaddress for which the clans needs to
     *                     searched for.
     * @return the String array which contains all the clan names.
     */
    public List<String> fillTable(String emailaddress) {
        List<String> clans = new ArrayList<>();

        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        ResultSet resultSetWithClans;

        try {
            preparedStatement = connect.prepareStatement("SELECT Id FROM account WHERE Email = ?");
            preparedStatement.setString(1, emailaddress);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ClanManagementScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            resultSet.next();
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
                resultSetWithClans = preparedStatement.executeQuery();

                resultSetWithClans.next();
                clans.add(resultSetWithClans.getString("Name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClanManagementScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

        return clans;
    }

    /**
     * Create a clan with the given {@code clanName} and {@code emailaddress}.
     *
     * @param clanName     Name of the clan.
     * @param emailaddress Creater of the clan.
     * @return True if creation did succeed else false.
     */
    public boolean createClan(String clanName, String emailaddress) {
        if (!clanExists(clanName)) {
            if (hasRoomForClan(emailaddress)) {
                PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;

                try {
                    preparedStatement = connect.prepareStatement("SELECT Id FROM account WHERE Email = ?");
                    preparedStatement.setString(1, emailaddress);
                    resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    int id = resultSet.getInt("Id");

                    preparedStatement = connect.prepareStatement("INSERT INTO clan(Name,Score,ManagerId) VALUES(?,0,?)");
                    preparedStatement.setString(1, clanName);
                    preparedStatement.setInt(2, id);
                    preparedStatement.executeUpdate();

                    preparedStatement = connect.prepareStatement("INSERT INTO clan_account(AccountId,ClanId,Accepted) VALUES(?,?,1)");
                    preparedStatement.setInt(1, getAccountIdFromEmail(emailaddress));
                    preparedStatement.setInt(2, getClanIdFromName(clanName));
                    preparedStatement.executeUpdate();

                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(ClanFunctions.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    /**
     * Looks if a user has room to make another clan.
     *
     * @param emailaddress Emailaddress of the user to look for space.
     * @return True if there is space, otherwise false.
     */
    private boolean hasRoomForClan(String emailaddress) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            preparedStatement = connect.prepareStatement("SELECT COUNT(*) as amount FROM clan,account WHERE clan.ManagerId = account.Id AND account.Email = ?");
            preparedStatement.setString(1, emailaddress);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int clans = resultSet.getInt("amount");

            if (clans < 8) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClanFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    /**
     * Makes connection to the database.
     *
     * @return true if the connection making did succeed, false otherwise.
     */
    private boolean makeConnection() {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");

            // Setup the connection with the DB
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/pactales", "root", "");

            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ClanManagementScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    /**
     * Looks if a clan exists.
     *
     * @param clanName the name of the clan.
     * @return true if it exists, else false.
     */
    private boolean clanExists(String clanName) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connect.prepareStatement("SELECT COUNT(*) AS amount FROM clan WHERE Name = ?");
            preparedStatement.setString(1, clanName);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int clans = resultSet.getInt("amount");

            if (clans == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClanFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    /**
     * Gets the account id from a player.
     *
     * @param emailaddress the emailaddress to get the id from.
     * @return the id, -1 if the player does not exists.
     */
    private int getAccountIdFromEmail(String emailaddress) {
        try {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            preparedStatement = connect.prepareStatement("SELECT Id FROM account WHERE Email = ?");
            preparedStatement.setString(1, emailaddress);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("Id");
        } catch (SQLException ex) {
            Logger.getLogger(ClanFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    /**
     * Gets the manager id from a clan.
     *
     * @param clanName Clan name to search for.
     * @return Id of the manager, -1 if the clan does not exists.
     */
    private int getManagerIdFromClanName(String clanName) {
        try {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            preparedStatement = connect.prepareStatement("SELECT ManagerId FROM clan WHERE Name = ?");
            preparedStatement.setString(1, clanName);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("ManagerId");
        } catch (SQLException ex) {
            Logger.getLogger(ClanFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    /**
     * Gets the clan id from a clan.
     *
     * @param clanName Clan name to search for.
     * @return Id of the clan, -1 if the clan does not exists.
     */
    private int getClanIdFromName(String clanName) {
        try {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            preparedStatement = connect.prepareStatement("SELECT Id FROM clan WHERE Name = ?");
            preparedStatement.setString(1, clanName);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("Id");
        } catch (SQLException ex) {
            Logger.getLogger(ClanFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    /**
     * Gets the {@link ateamproject.kezuino.com.github.render.screens.ClanFunctions.InvitationType} for a given clan and a given {@code emailaddress}.
     *
     * @param clanName     Name of the clan.
     * @param emailaddress {@code emailaddress} of who is in the clan.
     * @return {@link ateamproject.kezuino.com.github.render.screens.ClanFunctions.InvitationType}, or null if failed.
     */
    public InvitationType getInvitation(String clanName, String emailaddress) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            if (getManagerIdFromClanName(clanName) == getAccountIdFromEmail(emailaddress)) {
                return InvitationType.INVITE;//if user is owner of clan, he can invite
            }

            preparedStatement = connect.prepareStatement("SELECT Accepted FROM clan_account WHERE AccountId = ? AND ClanId = ?");
            preparedStatement.setInt(1, getAccountIdFromEmail(emailaddress));
            preparedStatement.setInt(2, getClanIdFromName(clanName));
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int accepted = resultSet.getInt("Accepted");

            if (accepted == 0) {
                return InvitationType.ACCEPT;//user can accept joining clan and is not owner
            } else {
                return InvitationType.NONE;//user is not owner and user is joined
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClanFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Gets the managementType for a given clan and a given {@copde emailaddress}.
     *
     * @param clanName     Name of the clan
     * @param emailaddress Emailaddress of who is in the clan
     * @return {@link ateamproject.kezuino.com.github.render.screens.ClanFunctions.ManagementType}, or null if failed.
     */
    public ManagementType getManagement(String clanName, String emailaddress) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            if (getManagerIdFromClanName(clanName) == getAccountIdFromEmail(emailaddress)) {
                return ManagementType.REMOVE;//if user is owner of clan he can remove it
            }

            preparedStatement = connect.prepareStatement("SELECT Accepted FROM clan_account WHERE AccountId = ? AND ClanId = ?");
            preparedStatement.setInt(1, getAccountIdFromEmail(emailaddress));
            preparedStatement.setInt(2, getClanIdFromName(clanName));
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int accepted = resultSet.getInt("Accepted");

            if (accepted == 0) {
                return ManagementType.REJECT;//user has not accepted thus is can decline it
            } else {
                return ManagementType.LEAVE;//user is owner thus it can leave
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClanFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Gets amount of people in a clan.
     *
     * @param clanName Name of the clan to get the amount from
     * @return String in the format 'personen ?/8' where ? is the number of
     * people in the clan.
     */
    public String getPeople(String clanName) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            preparedStatement = connect.prepareStatement("SELECT COUNT(*) AS amount FROM clan_account WHERE ClanId = ?");
            preparedStatement.setInt(1, getClanIdFromName(clanName));
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int amountOfPlayers = resultSet.getInt("amount");

            return "personen " + amountOfPlayers + "/8";
        } catch (SQLException ex) {
            Logger.getLogger(ClanFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "personen ?/8";
    }

    /**
     * Processes an invitation.
     *
     * @param invite             Invite to process.
     * @param clanName           Name of the clan where the invite belongs to.
     * @param emailAddress       Email of the person who did send the invite to this
     *                           function.
     * @param nameOfEmailInvitee Optional name/emailaddress parameter for the
     *                           'INVITE' invite. Needs to be null when a invite is not of the type
     *                           'INVITE'.
     * @return True if invitation is successfully handled else false.
     */
    public boolean handleInvitation(InvitationType invite, String clanName, String emailAddress, String nameOfEmailInvitee) {
        if (invite.equals(InvitationType.ACCEPT)) {
            PreparedStatement preparedStatement;

            try {
                preparedStatement = connect.prepareStatement("UPDATE clan_account SET Accepted = 1 WHERE AccountId = ? AND ClanId = ?");
                preparedStatement.setInt(1, getAccountIdFromEmail(emailAddress));
                preparedStatement.setInt(2, getClanIdFromName(clanName));
                preparedStatement.executeUpdate();

                return true;
            } catch (SQLException ex) {
                return false;
            }
        } else if (invite.equals(InvitationType.INVITE)) {
            PreparedStatement preparedStatement;

            String email = getEmail(nameOfEmailInvitee);
            String username = getUsername(nameOfEmailInvitee);
            if (email == null) {
                if (username == null) {
                    return false;
                }

                if (!userIsInOrInvitedInClan(nameOfEmailInvitee, clanName)) {
                    try {
                        preparedStatement = connect.prepareStatement("UPDATE INTO clan_account(AccountId,ClanId,Accepted) VALUES(?,?,0)");
                        preparedStatement.setInt(1, getAccountIdFromEmail(getEmail(username)));
                        preparedStatement.setInt(2, getClanIdFromName(clanName));
                        preparedStatement.executeUpdate();

                        return true;
                    } catch (SQLException ex) {
                        return false;
                    }
                }
            } else {
                if (!userIsInOrInvitedInClan(nameOfEmailInvitee, clanName)) {
                    try {
                        preparedStatement = connect.prepareStatement("UPDATE INTO clan_account(AccountId,ClanId,Accepted) VALUES(?,?,0)");
                        preparedStatement.setInt(1, getAccountIdFromEmail(emailAddress));
                        preparedStatement.setInt(2, getClanIdFromName(clanName));
                        preparedStatement.executeUpdate();

                        return true;
                    } catch (SQLException ex) {
                        return false;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Executes an action based on the given {@link ateamproject.kezuino.com.github.render.screens.ClanFunctions.ManagementType managementType} to run on the {@code clan} and {@code emailaddress}.
     *
     * @param managementType {@link ateamproject.kezuino.com.github.render.screens.ClanFunctions.ManagementType} to process.
     * @param clanName       Name of the clan where the {@link ateamproject.kezuino.com.github.render.screens.ClanFunctions.ManagementType} belongs to
     * @param emailaddress   Emailaddress of the user.
     * @return True if {@link ateamproject.kezuino.com.github.render.screens.ClanFunctions.ManagementType} is successfully handled else false.
     */
    public boolean handleManagement(ManagementType managementType, String clanName, String emailaddress) {
        if (managementType.equals(ManagementType.REJECT) || managementType.equals(ManagementType.LEAVE)) {
            PreparedStatement preparedStatement;

            try {
                preparedStatement = connect.prepareStatement("DELETE FROM clan_account WHERE AccountId = ? AND ClanId = ?");
                preparedStatement.setInt(1, getAccountIdFromEmail(emailaddress));
                preparedStatement.setInt(2, getClanIdFromName(clanName));
                preparedStatement.executeUpdate();

                return true;

            } catch (SQLException ex) {
                Logger.getLogger(ClanFunctions.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else if (managementType.equals(ManagementType.REMOVE)) {
            PreparedStatement preparedStatement;

            try {

                preparedStatement = connect.prepareStatement("DELETE FROM clan_account WHERE ClanId = ?");
                preparedStatement.setInt(1, getClanIdFromName(clanName));
                preparedStatement.executeUpdate();

                preparedStatement = connect.prepareStatement("DELETE FROM clan WHERE Id = ?");
                preparedStatement.setInt(1, getClanIdFromName(clanName));
                preparedStatement.executeUpdate();

                return true;

            } catch (SQLException ex) {
                Logger.getLogger(ClanFunctions.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        return false;
    }

    /**
     * Gets the name from a emailaddress.
     *
     * @param emailaddress Emailaddress to search for.
     * @return Name of the user. Null if the user can not be found.
     */
    public String getUsername(String emailaddress) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            preparedStatement = connect.prepareStatement("SELECT Name FROM account WHERE Email = ?");
            preparedStatement.setString(1, emailaddress);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String username = resultSet.getString("Name");

            return username;

        } catch (SQLException ex) {
            return null;
        }

    }

    /**
     * Finds the emailaddress of the user.
     *
     * @param username Username to get emailaddress from.
     * @return Emailaddress of the user or null if the user can not be found.
     */
    public String getEmail(String username) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            preparedStatement = connect.prepareStatement("SELECT Email FROM account WHERE Name = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String email = resultSet.getString("Email");

            return email;

        } catch (SQLException ex) {
            return null;
        }
    }

    /**
     * Set the username of an user.
     *
     * @param name         New name of the user.
     * @param emailaddress Emailaddress of the user to find in the database.
     * @return True if it succeeded, false if the {@code name} was already taken.
     */
    public boolean setUsername(String name, String emailaddress) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        if (getEmail(name) == null) {//if it cant get the email, the name is not taken
            try {

                preparedStatement = connect.prepareStatement("UPDATE account SET Name = ? WHERE Email = ?");
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, emailaddress);
                preparedStatement.executeUpdate();

                return true;

            } catch (SQLException ex) {
                Logger.getLogger(ClanFunctions.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        return false;
    }

    /**
     * Check if a user is in or invited into a clan.
     *
     * @param nameOfEmailInvitee Name of the person to look for
     * @param clanName           Clan in which to search for
     * @return True if the user is currently in or is invited into the clan, else false.
     */
    private boolean userIsInOrInvitedInClan(String nameOfEmailInvitee, String clanName) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String email = getEmail(nameOfEmailInvitee);
        String username = getUsername(nameOfEmailInvitee);
        if (email == null) {
            if (username == null) {
                return false;
            }

            try {
                preparedStatement = connect.prepareStatement("SELECT COUNT(*) as amount FROM clan_account WHERE AccountId = ? AND ClanId = ?");
                preparedStatement.setInt(1, getAccountIdFromEmail(getEmail(username)));
                preparedStatement.setInt(2, getClanIdFromName(clanName));
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                if (resultSet.getInt("amount") == 1) {
                    return true;
                }

                return false;

            } catch (SQLException ex) {
                return false;
            }

        } else {

            try {
                preparedStatement = connect.prepareStatement("SELECT COUNT(*) as amount FROM clan_account WHERE AccountId = ? AND ClanId = ?");
                preparedStatement.setInt(1, getAccountIdFromEmail(email));
                preparedStatement.setInt(2, getClanIdFromName(clanName));
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                if (resultSet.getInt("amount") == 1) {
                    return true;
                }

                return false;

            } catch (SQLException ex) {
                return false;
            }
        }
    }

    /**
     * A state in which a person opposed to a clan can be.
     */
    public enum InvitationType {

        /**
         * Nothing will be displayed.
         */
        NONE,

        /**
         * The user can invite people to its clan.
         */
        INVITE,

        /**
         * The user can accept the invite to this clan.
         */
        ACCEPT
    }

    /**
     * A state in which a person opposed to a clan can be.
     */
    public enum ManagementType {

        /**
         * User can remove this clan.
         */
        REMOVE,

        /**
         * User can leave this clan.
         */
        LEAVE,

        /**
         * User can reject an offer to leave this clan.
         */
        REJECT
    }
}
