package opps.view;

import javax.swing.*;
import java.awt.*;

public class EmployeeMainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private String empName;

    public EmployeeMainFrame(String empName) {
        this.empName = empName;
        setTitle("Employee Dashboard - " + empName);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();

        HomePanel home = new HomePanel(empName);
        RoomsPanel rooms = new RoomsPanel();
        BookingsPanel bookings = new BookingsPanel();
        PaymentsPanel payments = new PaymentsPanel();
        UserBox userBox = new UserBox(empName);

        tabbedPane.addTab("Home", home);
        tabbedPane.addTab("Operations", bookings);
        tabbedPane.addTab("Rooms", rooms);
        tabbedPane.addTab("Payments", payments);

        add(tabbedPane, BorderLayout.CENTER);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(41,128,185));
        header.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        JLabel title = new JLabel("Employee Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.WEST);
        header.add(userBox, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);
    }
}
