package opps.view;

import javax.swing.*;
import java.awt.*;

public class UserBox extends JPanel {
    public UserBox(String username) {
        setLayout(new FlowLayout(FlowLayout.RIGHT,10,5));
        setBackground(new Color(245,245,245));
        JLabel name = new JLabel(username);
        name.setFont(new Font("Arial", Font.BOLD, 13));
        name.setForeground(new Color(41,128,185));
        JButton logout = new JButton("Logout");
        logout.setBackground(new Color(231,76,60)); logout.setForeground(Color.WHITE);
        logout.addActionListener(e -> {
            int conf = JOptionPane.showConfirmDialog(this, "Logout?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                SwingUtilities.getWindowAncestor(this).dispose();
                new LoginView().setVisible(true);
            }
        });
        add(name); add(logout);
    }
}
