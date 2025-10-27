package opps.model;

public class RoomModel {
    private int id;
    private String roomNo;
    private String roomType;
    private double price;
    private String status;

    // --- Constructors ---
    public RoomModel() {
        // Default constructor (optional, useful for frameworks or empty creation)
    }

    // ✅ Full constructor (used when fetching from DB)
    public RoomModel(int id, String roomNo, String roomType, double price, String status) {
        this.id = id;
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.price = price;
        this.status = status;
    }

    // ✅ Constructor for inserting new rooms (used before saving to DB)
    public RoomModel(String roomNo, String roomType, double price, String status) {
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.price = price;
        this.status = status;
    }

    // --- Getters and Setters ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ✅ Add this method for compatibility with "getType()" calls in your existing code
    public String getType() {
        return roomType;
    }

    // ✅ Add this method for compatibility with "setType()" if needed
    public void setType(String type) {
        this.roomType = type;
    }

    @Override
    public String toString() {
        return "RoomModel{" +
                "id=" + id +
                ", roomNo='" + roomNo + '\'' +
                ", roomType='" + roomType + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}
