package opps.view;

import opps.controller.EmployeeController;
import opps.model.EmployeeModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton addBtn, removeBtn;

    public UserPanel() {
        setLayout(new BorderLayout(12,12));
        setBackground(Color.WHITE);

        model = new DefaultTableModel(new Object[]{"ID","Username","Role"},0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(3,2,8,8));
        form.setBorder(BorderFactory.createTitledBorder("Add Employee"));
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        addBtn = new JButton("Add");
        removeBtn = new JButton("Remove Selected");
        form.add(new JLabel("Username:")); form.add(usernameField);
        form.add(new JLabel("Password:")); form.add(passwordField);
        form.add(addBtn); form.add(removeBtn);
        add(form, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addEmployee());
        removeBtn.addActionListener(e -> removeEmployee());

        loadUsers();
    }

    private void loadUsers() {
        model.setRowCount(0);
        try {
            List<EmployeeModel> list = EmployeeController.getAllEmployees();
            for (EmployeeModel em : list) model.addRow(new Object[]{em.getId(), em.getUsername(), em.getRole()});
        } catch (Exception ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(this,"Error loading users"); }
    }

    private void addEmployee() {
        String u = usernameField.getText().trim();
        String p = new String(passwordField.getPassword());
        if (u.isEmpty() || p.isEmpty()) { JOptionPane.showMessageDialog(this, "Fill fields"); return; }
        EmployeeModel em = new EmployeeModel(0, u, p, "EMPLOYEE");
        boolean ok = EmployeeController.addEmployee(em);
        if (ok) { JOptionPane.showMessageDialog(this, "Added"); loadUsers(); } else JOptionPane.showMessageDialog(this, "Add failed");
    }

    private void removeEmployee() {
        int r = table.getSelectedRow();
        if (r == -1) { JOptionPane.showMessageDialog(this,"Select user"); return; }
        String username = (String) model.getValueAt(r,1);
        boolean ok = EmployeeController.removeEmployee(username);
        if (ok) { JOptionPane.showMessageDialog(this, "Removed"); loadUsers(); } else JOptionPane.showMessageDialog(this, "Remove failed");
    }
}
