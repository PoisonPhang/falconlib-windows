package com.staleyhighschool.fbla.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Establishes connection to Book database
 *
 * @author PoisonPhang
 */
public class Connector {

    public static Connection connection;

    private final String DATABASE_URL = "";
    private final String PORT = ":";

    public Connector() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL + PORT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
