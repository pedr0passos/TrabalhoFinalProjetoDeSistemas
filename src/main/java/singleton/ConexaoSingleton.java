package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class to manage database connection.
 */
public class ConexaoSingleton {

    private static ConexaoSingleton instance;
    private Connection connection;
    private static final String URL = "jdbc:sqlite:db/database.db";

    // Private constructor to prevent instantiation
    private ConexaoSingleton() {
        try {
            // Initialize the connection
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database: " + e.getMessage());
        }
    }

    // Public method to provide access to the instance
    public static synchronized ConexaoSingleton getInstance() {
        if (instance == null) {
            instance = new ConexaoSingleton();
        }
        return instance;
    }

    // Method to get the connection
    public Connection getConnection() {
        return connection;
    }

    // Method to close the connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error closing the database connection: " + e.getMessage());
        }
    }
}
