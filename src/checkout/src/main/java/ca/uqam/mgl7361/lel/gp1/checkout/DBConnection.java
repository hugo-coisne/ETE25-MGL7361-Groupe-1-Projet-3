package ca.uqam.mgl7361.lel.gp1.checkout;

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
            System.out.println(url);
            user = System.getenv("DB_UNAME");
            System.out.println(user);
            password = System.getenv("DB_PWD");
            System.out.println(password);

        } catch (Exception ex) {
            throw new RuntimeException("Error loading JDBC properties", ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
