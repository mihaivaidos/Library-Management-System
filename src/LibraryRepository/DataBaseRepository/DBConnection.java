package LibraryRepository.DataBaseRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/LibraryManagementSystem";  // URL for PostgreSQL
    private static final String USER = "postgres";  // PostgreSQL username
    private static final String PASSWORD = "password";  // PostgreSQL password

    public static Connection getConnection() throws SQLException {
        try {
            // Load the PostgreSQL driver
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver not found", e);
        }
    }

}
