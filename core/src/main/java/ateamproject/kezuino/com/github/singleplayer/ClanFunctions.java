/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.network.packet.enums.InvitationType;
import ateamproject.kezuino.com.github.network.packet.enums.ManagementType;
import ateamproject.kezuino.com.github.render.screens.ClanManagementScreen;
import ateamproject.kezuino.com.github.utility.io.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jip
 */
public class ClanFunctions {

    private static ClanFunctions instance = new ClanFunctions();

    private ClanFunctions() {
        if (!Database.getInstance().open()) {
            System.out.println("Database could not be accessed.");
        }
    }

    public static ClanFunctions getInstance() {
        return instance;
    }

    /**
     * Gives all clans for a specific {@code emailaddress}.
     *
     * @param emailaddress the emailaddress for which the clans needs to
     * searched for.
     * @return the String array which contains all the clan names.
     */
    public ArrayList<String> fillTable(String emailaddress) {
        ArrayList<String> clans = new ArrayList<>();

        ResultSet resultSet;
        ResultSet resultSetWithClans;

        try {
            resultSet = Database.getInstance().query("SELECT Id FROM account WHERE Email = ?", emailaddress);
            resultSet.next();
            int id = resultSet.getInt("Id");

            resultSet = Database.getInstance().query("SELECT ClanId FROM clan_account WHERE AccountId = ?", id);
            while (resultSet.next()) {
                id = resultSet.getInt("ClanId");

                resultSetWithClans = Database.getInstance().query("SELECT Name FROM clan WHERE Id = ?", id);
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
     * @param clanName Name of the clan.
     * @param emailaddress Creater of the clan.
     * @return True if creation did succeed else false.
     */
    public boolean createClan(String clanName, String emailaddress) {
        if (!clanExists(clanName)) {
            if (hasRoomForClan(emailaddress)) {
                int id = getAccountIdFromEmail(emailaddress);

                Database.getInstance().update("INSERT INTO clan(Name,Score,ManagerId) VALUES(?,0,?)", clanName, id);
                Database.getInstance()
                        .update("INSERT INTO clan_account(AccountId,ClanId,Accepted) VALUES(?,?,1)", id, getClanIdFromName(clanName));

                return true;
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
        ResultSet resultSet;

        try {
            resultSet = Database.getInstance()
                    .query("SELECT COUNT(*) as amount FROM clan,account WHERE clan.ManagerId = account.Id AND account.Email = ?", emailaddress);
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
     * Gets the {@link InvitationType} for a given clan and a given
     * {@code emailaddress}.
     *
     * @param clanName Name of the clan.
     * @param emailaddress {@code emailaddress} of who is in the clan.
     * @return {@link InvitationType}, or null if failed.
     */
    public InvitationType getInvitation(String clanName, String emailaddress) {
        ResultSet resultSet;

        try {
            int managerId = getManagerIdFromClanName(clanName);
            int accountIdFromEmail = getAccountIdFromEmail(emailaddress);
            if ((managerId == accountIdFromEmail) && (managerId != -1)) {
                return InvitationType.INVITE;//if user is owner of clan, he can invite
            }

            resultSet = Database.getInstance()
                    .query("SELECT Accepted FROM clan_account WHERE AccountId = ? AND ClanId = ?", emailaddress, clanName);
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
        System.out.println("Null bij invitation word returned");
        return InvitationType.ACCEPT;
        //return null;
    }

    /**
     * Gets the managementType for a given clan and a given {
     *
     * @copde emailaddress}.
     *
     * @param clanName Name of the clan
     * @param emailaddress Emailaddress of who is in the clan
     * @return {@link ManagementType}, or null if failed.
     */
    public ManagementType getManagement(String clanName, String emailaddress) {
        ResultSet resultSet;

        try {
            if (getManagerIdFromClanName(clanName) == getAccountIdFromEmail(emailaddress)) {
                return ManagementType.REMOVE;//if user is owner of clan he can remove it
            }

            resultSet = Database.getInstance()
                    .query("SELECT Accepted FROM clan_account WHERE AccountId = ? AND ClanId = ?", clanName, emailaddress);
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
        System.out.println("Er word nu ll returned bij clanfunctions");
        return ManagementType.LEAVE;
        //return null;
    }

    /**
     * Gets amount of people in a clan.
     *
     * @param clanName Name of the clan to get the amount from
     * @return String in the format 'personen ?/8' where ? is the number of
     * people in the clan.
     */
    public String getPeople(String clanName) {
        ResultSet resultSet;

        try {
            resultSet = Database.getInstance()
                    .query("SELECT COUNT(*) AS amount FROM clan_account WHERE ClanId = ?", clanName);
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
     * @param invite Invite to process.
     * @param clanName Name of the clan where the invite belongs to.
     * @param emailAddress Email of the person who did send the invite to this
     * function.
     * @param nameOfEmailInvitee Optional name/emailaddress parameter for the
     * 'INVITE' invite. Needs to be null when a invite is not of the type
     * 'INVITE'.
     * @return True if invitation is successfully handled else false.
     */
    public boolean handleInvitation(InvitationType invite, String clanName, String emailAddress, String nameOfEmailInvitee) {
        if (invite.equals(InvitationType.ACCEPT)) {

            Database.getInstance()
                    .update("UPDATE clan_account SET Accepted = 1 WHERE AccountId = ? AND ClanId = ?", emailAddress, clanName);
            return true;
        } else if (invite.equals(InvitationType.INVITE)) {
            PreparedStatement preparedStatement;

            String email = getEmail(nameOfEmailInvitee);
            String username = getUsername(nameOfEmailInvitee);
            if (email == null) {
                if (username == null) {
                    return false;
                }

                if (!userIsInOrInvitedInClan(nameOfEmailInvitee, clanName)) {
                    return Database.getInstance()
                            .update("UPDATE INTO clan_account(AccountId,ClanId,Accepted) VALUES(?,?,0)", getAccountIdFromEmail(getEmail(username)), getClanIdFromName(clanName)) > 0;
                }
            } else {
                if (!userIsInOrInvitedInClan(nameOfEmailInvitee, clanName)) {
                    return Database.getInstance()
                            .update("UPDATE INTO clan_account(AccountId,ClanId,Accepted) VALUES(?,?,0)", getAccountIdFromEmail(emailAddress), getClanIdFromName(clanName)) > 0;
                }
            }
        }

        return false;
    }

    /**
     * Executes an action based on the given {@link ManagementType} to run on
     * the {@code clan} and {@code emailaddress}.
     *
     * @param managementType {@link ManagementType} to process.
     * @param clanName Name of the clan where the {@link ManagementType} belongs
     * to
     * @param emailaddress Emailaddress of the user.
     * @return True if {@link ManagementType} is successfully handled else
     * false.
     */
    public boolean handleManagement(ManagementType managementType, String clanName, String emailaddress) {
        int clanIdFromName = getClanIdFromName(clanName);
        if (managementType.equals(ManagementType.REJECT) || managementType.equals(ManagementType.LEAVE)) {
            return Database.getInstance()
                    .update("DELETE FROM clan_account WHERE AccountId = ? AND ClanId = ?", getAccountIdFromEmail(emailaddress), clanIdFromName) > 0;
        } else if (managementType.equals(ManagementType.REMOVE)) {
            Database.getInstance().update("DELETE FROM clan_account WHERE ClanId = ?", clanIdFromName);
            Database.getInstance().update("DELETE FROM clan WHERE Id = ?", clanIdFromName);
            return true;
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
        ResultSet resultSet;

        try {
            resultSet = Database.getInstance().query("SELECT Name FROM account WHERE Email = ?", emailaddress);
            resultSet.next();

            return resultSet.getString("Name");

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
        ResultSet resultSet;

        try {
            resultSet = Database.getInstance().query("SELECT Email FROM account WHERE Name = ?", username);
            resultSet.next();
            return resultSet.getString("Email");

        } catch (SQLException ex) {
            return null;
        }
    }

    /**
     * Set the username of an user.
     *
     * @param name New name of the user.
     * @param emailaddress Emailaddress of the user to find in the database.
     * @return True if it succeeded, false if the {@code name} was already
     * taken.
     */
    public boolean setUsername(String name, String emailaddress) {
        if (getEmail(name) != null) {
            return false;//if it cant get the email, the name is not taken
        }
        return Database.getInstance().update("UPDATE account SET Name = ? WHERE Email = ?", name, emailaddress) > 0;
    }

    /**
     * Check if a user is in or invited into a clan.
     *
     * @param nameOfEmailInvitee Name of the person to look for
     * @param clanName Clan in which to search for
     * @return True if the user is currently in or is invited into the clan,
     * else false.
     */
    private boolean userIsInOrInvitedInClan(String nameOfEmailInvitee, String clanName) {
        String email = getEmail(nameOfEmailInvitee);
        String username = getUsername(nameOfEmailInvitee);
        ResultSet resultSet;
        if (email == null) {
            if (username == null) {
                return false;
            }

            try {
                resultSet = Database.getInstance()
                        .query("SELECT COUNT(*) as amount FROM clan_account WHERE AccountId = ? AND ClanId = ?", getAccountIdFromEmail(getEmail(username)), getClanIdFromName(clanName));
                resultSet.next();
                return resultSet.getInt("amount") == 1;

            } catch (SQLException ex) {
                return false;
            }

        } else {

            try {
                resultSet = Database.getInstance()
                        .query("SELECT COUNT(*) as amount FROM clan_account WHERE AccountId = ? AND ClanId = ?", getAccountIdFromEmail(email), getClanIdFromName(clanName));
                resultSet.next();
                return resultSet.getInt("amount") == 1;

            } catch (SQLException ex) {
                return false;
            }
        }
    }

    /**
     * Looks if a clan exists.
     *
     * @param clanName the name of the clan.
     * @return true if it exists, else false.
     */
    private boolean clanExists(String clanName) {
        ResultSet resultSet;

        try {
            resultSet = Database.getInstance().query("SELECT COUNT(*) AS amount FROM clan WHERE Name = ?", clanName);
            resultSet.next();
            return resultSet.getInt("amount") != 0;
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
            ResultSet resultSet;
            resultSet = Database.getInstance().query("SELECT Id FROM account WHERE Email = ?", emailaddress);
            resultSet.next();
            return resultSet.getInt("Id");
        } catch (SQLException ex) {
            return -1;
        }
    }

    /**
     * Gets the manager id from a clan.
     *
     * @param clanName Clan name to search for.
     * @return Id of the manager, -1 if the clan does not exists.
     */
    private int getManagerIdFromClanName(String clanName) {
        try {
            ResultSet resultSet;
            resultSet = Database.getInstance().query("SELECT ManagerId FROM clan WHERE Name = ?", clanName);
            resultSet.next();
            return resultSet.getInt("ManagerId");
        } catch (SQLException ex) {
            return -1;
        }
    }

    /**
     * Gets the clan id from a clan.
     *
     * @param clanName Clan name to search for.
     * @return Id of the clan, -1 if the clan does not exists.
     */
    private int getClanIdFromName(String clanName) {
        try {
            ResultSet resultSet;
            resultSet = Database.getInstance().query("SELECT Id FROM clan WHERE Name = ?", clanName);
            resultSet.next();
            return resultSet.getInt("Id");
        } catch (SQLException ex) {
            Logger.getLogger(ClanFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}
