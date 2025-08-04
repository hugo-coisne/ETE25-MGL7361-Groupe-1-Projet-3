package ca.uqam.mgl7361.lel.gp1.delivery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String url;
    private static final String user;
    private static final String password;

    static {
        try {
            Class.forName(System.getenv("driver_class_name"));
            url = System.getenv("DB_URL");
            user = System.getenv("DB_UNAME");
            password = System.getenv("DB_PWD");

        } catch (Exception ex) {
            throw new RuntimeException("Error loading JDBC properties", ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}