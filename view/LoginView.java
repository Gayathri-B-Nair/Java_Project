package opps.view;

import opps.controller.LoginController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private JButton loginBtn, resetBtn;

    public LoginView() {
        setTitle("Hotel Management - Login");
        setSize(420, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createEmptyBorder(18,18,18,18));

        JLabel title = new JLabel("Hotel Management System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(0,0,12,0));
        main.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(4,2,10,10));
        form.add(new JLabel("Username:"));
        usernameField = new JTextField();
        form.add(usernameField);

        form.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        form.add(passwordField);

        form.add(new JLabel("Login as:"));
        roleBox = new JComboBox<>(new String[]{"Admin","Employee"});
        form.add(roleBox);

        loginBtn = new JButton("Login");
        resetBtn = new JButton("Reset");
        form.add(loginBtn);
        form.add(resetBtn);

        main.add(form, BorderLayout.CENTER);
        add(main);

        loginBtn.addActionListener(this);
        resetBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            String u = usernameField.getText().trim();
            String p = new String(passwordField.getPassword());
            String role = (String) roleBox.getSelectedItem();
            try {
                boolean ok = LoginController.validateLogin(u, p, role);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Welcome, " + u);
                    dispose();
                    if ("Admin".equalsIgnoreCase(role)) {
                        new AdminMainFrame(u).setVisible(true);
                    } else {
                        new EmployeeMainFrame(u).setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Login error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            usernameField.setText("");
            passwordField.setText("");
            roleBox.setSelectedIndex(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
