package opps.view;

import opps.controller.RoomController;
import opps.model.RoomModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RoomsPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtRoomNo, txtType, txtPrice;
    private JButton btnAdd, btnDelete, btnRefresh;

    public RoomsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // ----- TABLE -----
        model = new DefaultTableModel(new Object[]{"ID", "Room No", "Type", "Price", "Status"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // ----- FORM -----
        JPanel formPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add / Manage Rooms"));
        formPanel.setBackground(Color.WHITE);

        formPanel.add(new JLabel("Room No:"));
        txtRoomNo = new JTextField();
        formPanel.add(txtRoomNo);

        formPanel.add(new JLabel("Type:"));
        txtType = new JTextField();
        formPanel.add(txtType);

        formPanel.add(new JLabel("Price:"));
        txtPrice = new JTextField();
        formPanel.add(txtPrice);

        btnAdd = new JButton("Add Room");
        btnDelete = new JButton("Delete Room");
        btnRefresh = new JButton("Refresh List");

        formPanel.add(btnAdd);
        formPanel.add(btnDelete);
        formPanel.add(btnRefresh);

        add(formPanel, BorderLayout.SOUTH);

        // ----- BUTTON ACTIONS -----
        btnAdd.addActionListener(e -> addRoom());
        btnDelete.addActionListener(e -> deleteRoom());
        btnRefresh.addActionListener(e -> loadRooms());

        // Load rooms at startup
        loadRooms();
    }

    // ----- ADD ROOM -----
    private void addRoom() {
        String roomNo = txtRoomNo.getText().trim();
        String type = txtType.getText().trim();
        String priceText = txtPrice.getText().trim();

        if (roomNo.isEmpty() || type.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            RoomModel rm = new RoomModel(roomNo, type, price, "Available");
            boolean success = RoomController.addRoom(rm);

            if (success) {
                JOptionPane.showMessageDialog(this, "Room added successfully!");
                clearFields();
                loadRooms();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add room!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Price must be a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ----- DELETE ROOM -----
    private void deleteRoom() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a room to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) model.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this room?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = RoomController.deleteRoom(id);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Room deleted successfully!");
                loadRooms();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete room!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ----- LOAD ROOMS -----
    private void loadRooms() {
        model.setRowCount(0);
        List<RoomModel> rooms = RoomController.getAllRooms();
        for (RoomModel r : rooms) {
            model.addRow(new Object[]{r.getId(), r.getRoomNo(), r.getRoomType(), r.getPrice(), r.getStatus()});
        }
    }

    // ----- CLEAR FORM -----
    private void clearFields() {
        txtRoomNo.setText("");
        txtType.setText("");
        txtPrice.setText("");
    }
}
