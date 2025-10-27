package opps.controller;

import opps.model.DatabaseConnection;
import java.sql.*;

public class LoginController {

    // ✅ Validate login for both Admin and Employee
    public static boolean validateLogin(String username, String password, String role) {
        String query = "";

        // Choose table based on selected role
        if (role.equalsIgnoreCase("Admin")) {
            query = "SELECT * FROM employees WHERE username = ? AND password = ?";
        } else if (role.equalsIgnoreCase("Employee")) {
            query = "SELECT * FROM employees WHERE username = ? AND password = ?";
        } else {
            return false; // invalid role
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next(); // ✅ if true → login success

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Optional: Get Employee Name (to display on dashboard)
    public static String getEmployeeName(String username) {
        String query = "SELECT * FROM employees WHERE username = ? AND password=? AND role=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
