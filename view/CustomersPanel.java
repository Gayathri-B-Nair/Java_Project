package opps.view;

import opps.controller.CustomerController;
import opps.model.CustomerModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomersPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JButton addBtn, refreshBtn, deleteBtn;

    public CustomersPanel() {
        setLayout(new BorderLayout(10,10));
        setBackground(Color.WHITE);

        JPanel top = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Customers");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        top.add(title, BorderLayout.WEST);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addBtn = new JButton("Add Customer");
        refreshBtn = new JButton("Refresh");
        deleteBtn = new JButton("Delete");
        actions.add(addBtn); actions.add(refreshBtn); actions.add(deleteBtn);
        top.add(actions, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"ID","Name","Phone","Email"},0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshBtn.addActionListener(e -> loadCustomers());
        addBtn.addActionListener(e -> showAddDialog());
        deleteBtn.addActionListener(e -> deleteSelected());

        loadCustomers();
    }

    private void loadCustomers() {
        model.setRowCount(0);
        try {
            List<CustomerModel> list = CustomerController.getAllCustomers();
            for (CustomerModel c : list) {
                model.addRow(new Object[]{c.getId(), c.getName(), c.getPhone(), c.getEmail()});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading customers");
        }
    }

    private void showAddDialog() {
        JTextField name = new JTextField();
        JTextField phone = new JTextField();
        JTextField email = new JTextField();
        JPanel p = new JPanel(new GridLayout(3,2,8,8));
        p.add(new JLabel("Name:")); p.add(name);
        p.add(new JLabel("Phone:")); p.add(phone);
        p.add(new JLabel("Email:")); p.add(email);
        int res = JOptionPane.showConfirmDialog(this, p, "Add Customer", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            try {
                CustomerModel cm = new CustomerModel(name.getText().trim(), email.getText().trim(), phone.getText().trim(), null);
                boolean ok = CustomerController.addCustomer(cm);
                if (ok) { JOptionPane.showMessageDialog(this,"Added"); loadCustomers(); }
                else JOptionPane.showMessageDialog(this,"Add failed");
            } catch (Exception ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(this,"Error: "+ex.getMessage()); }
        }
    }

    private void deleteSelected() {
        int r = table.getSelectedRow();
        if (r == -1) { JOptionPane.showMessageDialog(this, "Select a customer"); return; }
        int id = (int) model.getValueAt(r,0);
        int conf = JOptionPane.showConfirmDialog(this, "Delete customer?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            boolean ok = CustomerController.removeCustomer(id);
            if (ok) { JOptionPane.showMessageDialog(this,"Deleted"); loadCustomers(); }
            else JOptionPane.showMessageDialog(this,"Delete failed");
        }
    }
}
