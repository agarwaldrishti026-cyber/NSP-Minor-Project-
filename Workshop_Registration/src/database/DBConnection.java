package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/workshop_registration?useSSL=false&serverTimezone=UTC";

    private static final String USER = "root";

    // CHANGE THIS TO YOUR MYSQL PASSWORD
    private static final String PASSWORD = "palak";

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}