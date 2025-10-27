package opps.view;

import opps.controller.PaymentController;
import opps.model.PaymentModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PaymentsPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JButton btnRefresh, btnMarkPaid, btnGenerateBill;

    public PaymentsPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);

        // --- Table Setup ---
        model = new DefaultTableModel(new Object[]{"ID", "Booking ID", "Customer ID", "Amount", "Date", "Status"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Button Panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        btnRefresh = new JButton("Refresh");
        btnMarkPaid = new JButton("Mark as Paid");
        btnGenerateBill = new JButton("Generate Bill");

        buttonPanel.add(btnGenerateBill);
        buttonPanel.add(btnMarkPaid);
        buttonPanel.add(btnRefresh);

        add(buttonPanel, BorderLayout.SOUTH);

        // --- Button Actions ---
        btnRefresh.addActionListener(e -> loadPayments());

        btnMarkPaid.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a payment to update.");
                return;
            }
            int id = (int) model.getValueAt(selectedRow, 0);
            boolean ok = PaymentController.updatePaymentStatus(id, "Paid");
            if (ok) {
                JOptionPane.showMessageDialog(this, "Payment marked as PAID successfully!");
                loadPayments();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update payment status.");
            }
        });

        btnGenerateBill.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter Booking ID to generate bill:");
            if (input != null && !input.trim().isEmpty()) {
                try {
                    int bookingId = Integer.parseInt(input.trim());
                    double total = PaymentController.generateBill(bookingId);
                    if (total > 0)
                        JOptionPane.showMessageDialog(this, "Bill generated successfully!\nTotal: ₹" + total);
                    else
                        JOptionPane.showMessageDialog(this, "No booking found for given ID.");
                    loadPayments();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Booking ID!");
                }
            }
        });

        // Load data initially
        loadPayments();
    }

    private void loadPayments() {
        model.setRowCount(0);
        List<PaymentModel> list = PaymentController.getAllPayments();
        for (PaymentModel p : list) {
            model.addRow(new Object[]{
                    p.getId(),
                    p.getBookingId(),
                    p.getCustomerId(),
                    p.getAmount(),
                    p.getPaymentDate(),
                    p.getStatus()
            });
        }
    }
}
