/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

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

    /**
     * A state in which a person opposed to a clan can be.
     */
    public enum invitationType {

        /**
         *The user can invite people to its clan
         */
        uitnodigen,

        /**
         *The user can accept the invite to this clan
         */
        accepteren,

        /**
         *Nothing will be displayed
         */
        nothing;
    }

    /**
     * A state in which a person opposed to a clan can be.
     */
    public enum ManagementType {

        /**
         *User can remove this clan
         */
        verwijderen,

        /**
         *User can leave this clan
         */
        verlaten,

        /**
         *User can reject an offer to leave this clan
         */
        afwijzen;
    }

    private Connection connect = null;
    private boolean hasConnection = false;

    /**
     * Constructor which will initialize clanfunctions
     */
    public ClanFunctions() {
        hasConnection = makeConnection();
    }

    /**
     * Returns if a connection has been made
     *
     * @return True if succeeded false if failed
     */
    public boolean getHasConnection() {
        return hasConnection;
    }

    /**
     * Gives all clans for a specific emailaddress
     *
     * @param emailaddress the emailaddress for which the clans needs to
     * searched for
     * @return the String array which contains all the clan names
     */
    public ArrayList<String> fillTable(String emailaddress) {
        ArrayList<String> clans = new ArrayList();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSet resultSetWithClans = null;

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
     * Create a clan with the given parameters
     *
     * @param clanName name of the clan
     * @param emailaddress the creater of the clan
     * @return true if creation did succeed else false
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
     * Looks if a user has room to make another clan
     * @param emailaddress the emailaddress of the user to look for space
     * @return true if there is space, otherwise false
     */
    private boolean hasRoomForClan(String emailaddress) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

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
     * Makes connection to the database
     * @return true if the connection making did succeed, false otherwise
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
     * Looks if a clan exists
     * @param clanName the name of the clan
     * @return true if it exists, else false
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
     * Gets the account id from a player
     * @param emailaddress the emailaddress to get the id from
     * @return the id, -1 if the player does not exists
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
    * Gets the manager id from a clan
    * @param clanName the clan name to search for
    * @return the id of the manager, -1 if the clan does not exists
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
    * Gets the clan id from a clan
    * @param clanName the clan name to search for
    * @return the id of the clan, -1 if the clan does not exists
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
     * Gets the invitationtype for a given clan and a given emailaddress
     *
     * @param clanName the name of the clan
     * @param emailaddress the emailaddress of who is in the clan
     * @return returns the type, if something goes wrong returns null
     */
    public invitationType getInvitation(String clanName, String emailaddress) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            if (getManagerIdFromClanName(clanName) == getAccountIdFromEmail(emailaddress)) {
                return invitationType.uitnodigen;//if user is owner of clan, he can invite
            }

            preparedStatement = connect.prepareStatement("SELECT Accepted FROM clan_account WHERE AccountId = ? AND ClanId = ?");
            preparedStatement.setInt(1, getAccountIdFromEmail(emailaddress));
            preparedStatement.setInt(2, getClanIdFromName(clanName));
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int accepted = resultSet.getInt("Accepted");

            if (accepted == 0) {
                return invitationType.accepteren;//user can accept joining clan and is not owner
            } else {
                return invitationType.nothing;//user is not owner and user is joined
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClanFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Gets the managementType for a given clan and a given emailaddress
     *
     * @param clanName the name of the clan
     * @param emailaddress the emailaddress of who is in the clan
     * @return returns the type, if something goes wrong returns null
     */
    public ManagementType getManagement(String clanName, String emailaddress) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            if (getManagerIdFromClanName(clanName) == getAccountIdFromEmail(emailaddress)) {
                return ManagementType.verwijderen;//if user is owner of clan he can remove it
            }

            preparedStatement = connect.prepareStatement("SELECT Accepted FROM clan_account WHERE AccountId = ? AND ClanId = ?");
            preparedStatement.setInt(1, getAccountIdFromEmail(emailaddress));
            preparedStatement.setInt(2, getClanIdFromName(clanName));
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int accepted = resultSet.getInt("Accepted");

            if (accepted == 0) {
                return ManagementType.afwijzen;//user has not accepted thus is can decline it
            } else {
                return ManagementType.verlaten;//user is owner thus it can leave
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClanFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Gets amount of persons in a clan
     *
     * @param clanName name of the clan to get the amount from
     * @return a string in the format 'personen ?/8' where ? is the number of
     * persons in the clan
     */
    public String getPersons(String clanName) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

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
     * processes an invitation
     *
     * @param invite the actual invite to process
     * @param clanName the name of the clan where the invite belongs to
     * @param emailAddress email of the person who did send the invite to this
     * function
     * @param nameOfEmailInvitee optional name/emailaddress parameter for the
     * 'uitnodigen' invite. Needs to be null when a invite is not of the type
     * 'uitnodigen'
     * @return true if invitation is successfully handled else false
     */
    public boolean handleInvitation(invitationType invite, String clanName, String emailAddress, String nameOfEmailInvitee) {
        if (invite.equals(invitationType.accepteren)) {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {
                preparedStatement = connect.prepareStatement("UPDATE clan_account SET Accepted = 1 WHERE AccountId = ? AND ClanId = ?");
                preparedStatement.setInt(1, getAccountIdFromEmail(emailAddress));
                preparedStatement.setInt(2, getClanIdFromName(clanName));
                preparedStatement.executeUpdate();

                return true;
            } catch (SQLException ex) {
                return false;
            }
        } else if (invite.equals(invitationType.uitnodigen)) {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

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
     * processes an management type of a clan
     *
     * @param manage the actual managementType to process
     * @param clanName the name of the clan where the managementType belongs to
     * @param emailaddress
     * @return true if managementType is successfully handled else false
     */
    public boolean handleManagement(ManagementType manage, String clanName, String emailaddress) {
        if (manage.equals(ManagementType.afwijzen) || manage.equals(ManagementType.verlaten)) {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

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
        } else if (manage.equals(ManagementType.verwijderen)) {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

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
     * Gets the name from a emailaddress
     *
     * @param emailaddress the emailaddress to search for
     * @return the name of the user. Null if the user can not be found
     */
    public String getUsername(String emailaddress) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

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
     * Find emailaddress of user
     *
     * @param username username to get emailaddress from
     * @return emailaddress of the user or null if the user can not be found
     */
    public String getEmail(String username) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

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
     * Set the username of an user
     *
     * @param name name of the user
     * @param emailaddress the emailaddress of the user where the name needs to
     * be changed from
     * @return true if it succeeded, false if the name was already taken
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
     * Check if a user is in or invited into a clan
     * @param nameOfEmailInvitee name of the person to look for
     * @param clanName the clan in which to search for
     * @return true if the user is in or invited into the clan, false if not so
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
}
