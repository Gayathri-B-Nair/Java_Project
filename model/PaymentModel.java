package opps.model;

import java.util.Date;

/**
 * PaymentModel represents a payment record for a hotel booking.
 * Compatible with JDBC CRUD operations and MVC architecture.
 */
public class PaymentModel {

    // --- Fields ---
    private int id;
    private int bookingId;
    private int customerId;
    private double amount;
    private Date paymentDate;  // java.util.Date (works with java.sql.Date)
    private String status;

    // --- Constructors ---

    // Default constructor
    public PaymentModel() {}

    // Constructor used when reading from database (includes id)
    public PaymentModel(int id, int bookingId, int customerId, double amount, Date paymentDate, String status) {
        this.id = id;
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.status = status;
    }

    // Constructor used when adding a new payment (without id)
    public PaymentModel(int bookingId, int customerId, double amount, Date paymentDate, String status) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.status = status;
    }

    // --- Getters & Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // --- Utility (for debugging/logging) ---
    @Override
    public String toString() {
        return "PaymentModel{" +
                "id=" + id +
                ", bookingId=" + bookingId +
                ", customerId=" + customerId +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", status='" + status + '\'' +
                '}';
    }
}
