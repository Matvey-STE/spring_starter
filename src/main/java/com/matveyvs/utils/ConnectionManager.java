package com.matveyvs.utils;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionManager {
    private static String URL_KEY;
    private static String URL_NAME;
    private static String URL_PASSWORD;
    private static String URL_DRIVER;

    public ConnectionManager(String urlKey, String urlName, String urlPassword,
                             String urlDriver) {
        ConnectionManager.URL_KEY = urlKey;
        ConnectionManager.URL_NAME = urlName;
        ConnectionManager.URL_PASSWORD = urlPassword;
        ConnectionManager.URL_DRIVER = urlDriver;

        loadDriver();
    }

    private static void loadDriver() {
        try {
            Class.forName(URL_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(URL_KEY, URL_NAME, URL_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionManager() {

    }
}
