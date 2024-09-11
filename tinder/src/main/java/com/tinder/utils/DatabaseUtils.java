package com.tinder.utils;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtils {
    private static final String DB_PROPERTIES_FILE = "src/main/resources/configs/dbconfig.properties";
    private static String jdbcURL;
    private static String jdbcUsername;
    private static String jdbcPassword;
    private static Connection jdbcConnection = null;

    static {
        try (FileInputStream fileInputStream = new FileInputStream(new File(DB_PROPERTIES_FILE))) {
            Properties properties = new Properties();
            properties.load(fileInputStream);

            jdbcURL = properties.getProperty("jdbc.url");
            jdbcUsername = properties.getProperty("jdbc.username");
            jdbcPassword = properties.getProperty("jdbc.password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection connect() {
        try {
            if (jdbcConnection != null && !jdbcConnection.isClosed()) {
                return jdbcConnection;
            }

            Class.forName("com.mysql.cj.jdbc.Driver");

            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return jdbcConnection;
    }

    public static void disconnect() {
        try {
            if (jdbcConnection != null && !jdbcConnection.isClosed()) {
                jdbcConnection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}