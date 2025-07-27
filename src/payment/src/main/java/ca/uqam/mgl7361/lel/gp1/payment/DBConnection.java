package ca.uqam.mgl7361.lel.gp1.payment;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final String PROPERTIES_FILE = "application.properties";
    private static final String url;
    private static final String user;
    private static final String password;

    static {
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            Properties prop = new Properties();
            prop.load(input);

            Class.forName(prop.getProperty("spring.datasource.driverClassName"));
            url = prop.getProperty("spring.datasource.url");
            user = prop.getProperty("spring.datasource.username");
            password = prop.getProperty("spring.datasource.password");

        } catch (Exception ex) {
            throw new RuntimeException("Error loading JDBC properties", ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
