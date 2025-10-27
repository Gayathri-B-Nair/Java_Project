package opps.view;

import opps.controller.BookingController;
import opps.controller.RoomController;
import opps.controller.CustomerController;
import opps.model.BookingModel;
import opps.model.RoomModel;
import opps.model.CustomerModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class BookingsPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JButton addBtn, refreshBtn, cancelBtn, checkinBtn, checkoutBtn;

    public BookingsPanel() {
        setLayout(new BorderLayout(10,10));
        setBackground(Color.WHITE);

        JPanel top = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Bookings");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        top.add(title, BorderLayout.WEST);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addBtn = new JButton("Add Booking");
        refreshBtn = new JButton("Refresh");
        cancelBtn = new JButton("Cancel Booking");
        checkinBtn = new JButton("Check In");
        checkoutBtn = new JButton("Check Out");
        actions.add(addBtn); actions.add(refreshBtn); actions.add(checkinBtn); actions.add(checkoutBtn); actions.add(cancelBtn);
        top.add(actions, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"ID","Customer","Room","Check-in","Check-out","Status"},0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshBtn.addActionListener(e -> loadBookings());
        addBtn.addActionListener(e -> showAddDialog());
        cancelBtn.addActionListener(e -> cancelBooking());
        checkinBtn.addActionListener(e -> changeStatus("CheckedIn"));
        checkoutBtn.addActionListener(e -> changeStatus("CheckedOut"));

        loadBookings();
    }

    private void loadBookings() {
        model.setRowCount(0);
        try {
            List<BookingModel> list = BookingController.getAllBookings();
            for (BookingModel b : list) {
                model.addRow(new Object[]{b.getId(), b.getCustomerId(), b.getRoomId(), b.getCheckInDate(), b.getCheckOutDate(), b.getStatus()});
            }
        } catch (Exception ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(this, "Error loading bookings"); }
    }

    private void showAddDialog() {
        // fetch customers and rooms for selection
        try {
            java.util.List<CustomerModel> customers = CustomerController.getAllCustomers();
            java.util.List<RoomModel> rooms = RoomController.getAllRooms();

            JComboBox<String> customerBox = new JComboBox<>();
            for (CustomerModel c : customers) customerBox.addItem(c.getId() + " - " + c.getName());
            JComboBox<String> roomBox = new JComboBox<>();
            for (RoomModel r : rooms) roomBox.addItem(r.getRoomNo() + " - " + r.getType());

            JSpinner inSpinner = new JSpinner(new SpinnerDateModel());
            JSpinner outSpinner = new JSpinner(new SpinnerDateModel());
            inSpinner.setEditor(new JSpinner.DateEditor(inSpinner, "dd-MM-yyyy"));
            outSpinner.setEditor(new JSpinner.DateEditor(outSpinner, "dd-MM-yyyy"));

            JPanel p = new JPanel(new GridLayout(4,2,8,8));
            p.add(new JLabel("Customer:")); p.add(customerBox);
            p.add(new JLabel("Room:")); p.add(roomBox);
            p.add(new JLabel("Check-in:")); p.add(inSpinner);
            p.add(new JLabel("Check-out:")); p.add(outSpinner);

            int res = JOptionPane.showConfirmDialog(this, p, "Add Booking", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                String custSel = (String) customerBox.getSelectedItem();
                String roomSel = (String) roomBox.getSelectedItem();
                int custId = Integer.parseInt(custSel.split(" - ")[0]);
                int roomNo = Integer.parseInt(roomSel.split(" - ")[0]);
                LocalDate checkIn = ((java.util.Date) inSpinner.getValue()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                LocalDate checkOut = ((java.util.Date) outSpinner.getValue()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

                // map roomNo -> room_id via controller
                RoomModel rm = RoomController.findByRoomNo(String.valueOf(roomNo));
                if (rm == null) { JOptionPane.showMessageDialog(this, "Room not found"); return; }

                BookingModel b = new BookingModel(custId, rm.getId(), checkIn, checkOut, "Booked");
                boolean ok = BookingController.addBooking(b);
                if (ok) { JOptionPane.showMessageDialog(this,"Booking created"); loadBookings(); } else JOptionPane.showMessageDialog(this,"Booking failed");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void cancelBooking() {
        int r = table.getSelectedRow();
        if (r == -1) { JOptionPane.showMessageDialog(this, "Select booking"); return; }
        int id = (int) model.getValueAt(r, 0);
        int conf = JOptionPane.showConfirmDialog(this, "Cancel booking " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            boolean ok = BookingController.deleteBooking(id);
            if (ok) { JOptionPane.showMessageDialog(this, "Cancelled"); loadBookings(); }
            else JOptionPane.showMessageDialog(this, "Cancel failed");
        }
    }

    private void changeStatus(String newStatus) {
        int r = table.getSelectedRow();
        if (r == -1) { JOptionPane.showMessageDialog(this, "Select booking"); return; }
        int id = (int) model.getValueAt(r,0);
        boolean ok = BookingController.updateBookingStatus(id, newStatus);
        if (ok) { JOptionPane.showMessageDialog(this, "Updated"); loadBookings(); } else JOptionPane.showMessageDialog(this,"Update failed");
    }
}
