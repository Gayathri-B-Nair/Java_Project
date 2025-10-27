package opps.view;

import javax.swing.*;
import java.awt.*;

public class AdminMainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private String adminName;

    public AdminMainFrame(String adminName) {
        this.adminName = adminName;
        setTitle("Admin Dashboard - " + adminName);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();

        // Panels
        HomePanel home = new HomePanel(adminName);
        RoomsPanel rooms = new RoomsPanel();
        BookingsPanel bookings = new BookingsPanel();
        CustomersPanel customers = new CustomersPanel();
        PaymentsPanel payments = new PaymentsPanel();
        UserPanel users = new UserPanel();
        ReportsPanel reports = new ReportsPanel();
        UserBox userBox = new UserBox(adminName);

        tabbedPane.addTab("Home", home);
        tabbedPane.addTab("Operations", bookings);
        tabbedPane.addTab("Rooms", rooms);
        tabbedPane.addTab("Customers", customers);
        tabbedPane.addTab("Payments", payments);
        tabbedPane.addTab("Reports", reports);
        tabbedPane.addTab("Users", users);

        add(tabbedPane, BorderLayout.CENTER);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(41,128,185));
        header.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        JLabel title = new JLabel("Manager Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.WEST);
        header.add(userBox, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);
    }
}
