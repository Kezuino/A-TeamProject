package ateamproject.kezuino.com.github.utility.io;

import ateamproject.kezuino.com.github.render.screens.ClanManagementScreen;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Database {

    private static final Database instance = Database.from(new File("db.pconf"));
    /**
     * {@link Connection} used for executing queries on the database.
     */
    protected Connection connection;
    /**
     * Composed connectionString.
     */
    protected String connectionString;
    /**
     * Username to use for the connectionString.
     */
    protected String username;
    /**
     * Password to use for the connectionString.
     */
    protected String password;

    public Database(String connectionString, String username, String password) {
        this.connectionString = connectionString;
        this.username = username;
        this.password = password;
    }

    public static Database getInstance() {
        return instance;
    }

    public static Database from(File file) {
        try {
            List<String> lines = Files.lines(file.toPath()).collect(Collectors.toList());
            if (lines.size() >= 3) {
                return new Database(String.format("jdbc:mysql://%s/pactales", lines.get(0)), lines.get(1), lines.get(2));
            }
        } catch (IOException ignored) {
        }
        return new Database("jdbc:mysql://localhost:3306/pactales", "root", "");
    }

    /**
     * Opens the connection if it's not open already.
     *
     * @return True if connection was opened (or is already open). False
     * otherwise.
     */
    public boolean open() {
        if (isOpen()) {
            return true;
        }

        // Create / reopen connection.
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");

            // Setup the connection with the DB
            connection = DriverManager.getConnection(connectionString, username, password);
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            return false;
        }
    }

    /**
     * Gets if the {@link #connection} is set and open or not.
     *
     * @return True if {@link #connection} is open. False if it's closed.
     */
    public boolean isOpen() {
        try {
            if (connection != null && !connection.isClosed()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Executes the {@code query} on the database and gets the
     * {@link ResultSet result}.
     *
     * @param query Query command to run on the database.
     * @param parms Parameters to prepare with the command to counter SQL
     * Injection and increase performance.
     * @return Executed {@link ResultSet} to get results from.
     */
    public ResultSet query(String query, Object... parms) {
        open();

        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(query);
            for (int i = 0; i < parms.length; i++) {
                Object parm = parms[i];
                statement.setObject(i + 1, parm);
            }
            return statement.executeQuery();
        } catch (SQLException e) {
        }

        return null;
    }

    /**
     * Executes the {@code query} on the database and gets the amount of rows
     * modified. If failed, returns -1.
     *
     * @param query Query command to run on the database.
     * @param parms Parameters to prepare with the command to counter SQL
     * Injection and increase performance.
     * @return Amount of rows modified or -1 if query failed.
     */
    public int update(String query, Object... parms) {
        open();

        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(query);
            for (int i = 0; i < parms.length; i++) {
                Object parm = parms[i];
                statement.setObject(i + 1, parm);
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
        }

        return -1;
    }
}
