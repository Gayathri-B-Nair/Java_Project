package opps.model;

import java.time.LocalDate;

public class BookingModel {
    private int id;
    private int customerId;
    private int roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;

    // 🔹 Default constructor
    public BookingModel() {}

    // 🔹 Constructor without ID (for inserting new bookings)
    public BookingModel(int customerId, int roomId, LocalDate checkInDate, LocalDate checkOutDate, String status) {
        this.customerId = customerId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
    }

    // 🔹 Constructor with ID (for retrieving from DB)
    public BookingModel(int id, int customerId, int roomId, LocalDate checkInDate, LocalDate checkOutDate, String status) {
        this.id = id;
        this.customerId = customerId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
    }

    // 🔹 Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
