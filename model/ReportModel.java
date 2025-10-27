package opps.model;

import java.sql.*;

public class ReportModel {

    public double getTotalIncome() {
        try (Connection con = DatabaseConnection.getConnection()) {
            String sql = "SELECT SUM(amount) AS total FROM payments WHERE status='Paid'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble("total");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // You can add similar methods for monthly stats, expenses, etc.
}
