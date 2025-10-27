package opps.controller;

import opps.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentController {

    // 🔹 Add a new payment manually
    public static boolean addPayment(PaymentModel payment) {
        String sql = "INSERT INTO payments (booking_id, customer_id, amount, payment_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, payment.getBookingId());
            ps.setInt(2, payment.getCustomerId());
            ps.setDouble(3, payment.getAmount());
            ps.setDate(4, new java.sql.Date(payment.getPaymentDate().getTime()));
            ps.setString(5, payment.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 🔹 Fetch all payments
    public static List<PaymentModel> getAllPayments() {
        List<PaymentModel> list = new ArrayList<>();
        String sql = "SELECT * FROM payments";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                PaymentModel payment = new PaymentModel(
                        rs.getInt("id"),
                        rs.getInt("booking_id"),
                        rs.getInt("customer_id"),
                        rs.getDouble("amount"),
                        rs.getDate("payment_date"),
                        rs.getString("status")
                );
                list.add(payment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔹 Update payment status (e.g., Paid, Pending)
    public static boolean updatePaymentStatus(int id, String status) {
        String sql = "UPDATE payments SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 🔹 Generate Bill for a given Booking ID
    public static double generateBill(int bookingId) {
        double totalAmount = 0.0;
        String sql = "SELECT b.id AS booking_id, b.customer_id, r.price, " +
                     "DATEDIFF(b.checkout_date, b.checkin_date) AS days " +
                     "FROM bookings b JOIN rooms r ON b.room_id = r.id WHERE b.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int customerId = rs.getInt("customer_id");
                double price = rs.getDouble("price");
                int days = rs.getInt("days");

                if (days <= 0) days = 1; // if same day

                totalAmount = price * days;

                // Insert into payments
                String insertSql = "INSERT INTO payments (booking_id, customer_id, amount, payment_date, status) VALUES (?, ?, ?, NOW(), 'Pending')";
                try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                    insertPs.setInt(1, bookingId);
                    insertPs.setInt(2, customerId);
                    insertPs.setDouble(3, totalAmount);
                    insertPs.executeUpdate();
                }

                System.out.println("✅ Bill generated successfully for Booking ID: " + bookingId);
            } else {
                System.out.println("❌ Booking not found for ID: " + bookingId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalAmount;
    }
}
