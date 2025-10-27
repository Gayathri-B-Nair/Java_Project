package opps.controller;

import opps.model.CustomerModel;
import opps.model.DatabaseConnection;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {

    // --- Add Customer ---
    public static boolean addCustomer(CustomerModel customer) {

        // 🔹 Validate phone number (must be exactly 10 digits)
        if (!customer.getPhone().matches("\\d{10}")) {
            JOptionPane.showMessageDialog(null,
                    "Invalid Phone Number!\nPhone number must contain exactly 10 digits.",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // 🔹 Validate email (must end with @gmail.com)
        if (!customer.getEmail().matches("^[A-Za-z0-9+_.-]+@gmail\\.com$")) {
            JOptionPane.showMessageDialog(null,
                    "Invalid Email Address!\nEmail must be in the format: example@gmail.com",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // ✅ If validation passes, insert into database
        String query = "INSERT INTO customers (name, email, phone) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Customer added successfully!");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error adding customer: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    // --- Get All Customers ---
    public static List<CustomerModel> getAllCustomers() {
        List<CustomerModel> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                CustomerModel c = new CustomerModel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
                customers.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    // --- Remove Customer ---
    public static boolean removeCustomer(int id) {
        String query = "DELETE FROM customers WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // --- Find Customer by ID ---
    public static CustomerModel findCustomerById(int id) {
        String query = "SELECT * FROM customers WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new CustomerModel(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
