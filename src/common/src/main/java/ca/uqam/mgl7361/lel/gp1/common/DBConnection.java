package ca.uqam.mgl7361.lel.gp1.common;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final String PROPERTIES_FILE = "jdbc.properties";
    private static final String url;
    private static final String user;
    private static final String password;

    static {
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            Properties prop = new Properties();
            prop.load(input);

            Class.forName(prop.getProperty("jdbc.driverClassName"));
            url = prop.getProperty("jdbc.url");
            user = prop.getProperty("jdbc.username");
            password = prop.getProperty("jdbc.password");

        } catch (Exception ex) {
            throw new RuntimeException("Error loading JDBC properties", ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
