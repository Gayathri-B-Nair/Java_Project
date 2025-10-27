package opps.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/hotel_db"; // your DB name
    private static final String USER = "root";  // your MySQL username
    private static final String PASSWORD = "";   // your MySQL password

    private static Connection connection;

    // Static method to get the connection
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Database connected successfully!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("❌ Error connecting to database: " + e.getMessage());
        }
        return connection;
    }
}
