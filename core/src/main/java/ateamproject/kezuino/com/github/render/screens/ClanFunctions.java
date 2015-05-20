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
     *A state in which a person opposed to a clan can be.
     */
    public enum invitationType {
        uitnodigen,accepteren,nothing;
    }

    /**
     *A state in which a person opposed to a clan can be.
     */
    public enum ManagementType {
        verwijderen,verlaten,afwijzen;
    }

    private Connection connect = null;
    private boolean hasConnection = false;

    /**
     *Constructor which will initialize clanfunctions
     */
    public ClanFunctions() {
        hasConnection = makeConnection();
    }

    /**
     *Returns if a connection has succesfully been made
     * @return
     */
    public boolean getHasConnection() {
        return hasConnection;
    }

    /**
     *Gives all clans for a specific emailaddress
     * @param emailaddress
     * @return
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
     *
     * @param clanName
     * @param emailaddress
     * @return
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
     *
     * @param clanName
     * @param emailaddress
     * @return
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
     *
     * @param clanName
     * @param emailaddress
     * @return
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
     *
     * @param clanName
     * @return
     */
    public String getPersons(String clanName) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connect.prepareStatement("SELECT COUNT(*) AS amount FROM clan_account WHERE ClanId = ?  AND Accepted = 1");
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
     *
     * @param invite
     * @param clanName
     * @param emailAddress
     * @param nameOfEmailInvitee
     * @return
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
                        preparedStatement = connect.prepareStatement("INSERT INTO clan_account(AccountId,ClanId,Accepted) VALUES(?,?,0)");
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
                        preparedStatement = connect.prepareStatement("INSERT INTO clan_account(AccountId,ClanId,Accepted) VALUES(?,?,0)");
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
     *
     * @param manage
     * @param clanName
     * @param emailaddress
     * @return
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
     *
     * @param emailaddress
     * @return
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
     *
     * @param username
     * @return
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
     *
     * @param name
     * @param emailaddress
     * @return
     */
    public boolean setUsername(String name, String emailaddress) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

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

        return false;
    }

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
