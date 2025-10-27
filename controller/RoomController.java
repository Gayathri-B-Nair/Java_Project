package opps.controller;

import opps.model.RoomModel;
import opps.model.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomController {

    // ✅ Add new room
    public static boolean addRoom(RoomModel room) {
        String query = "INSERT INTO rooms (room_no, room_type, price, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, room.getRoomNo());
            ps.setString(2, room.getRoomType());
            ps.setDouble(3, room.getPrice());
            ps.setString(4, room.getStatus());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Fetch all rooms
    public static List<RoomModel> getAllRooms() {
        List<RoomModel> rooms = new ArrayList<>();
        String query = "SELECT * FROM rooms";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                RoomModel room = new RoomModel(
                        rs.getInt("id"),
                        rs.getString("room_no"),
                        rs.getString("room_type"),
                        rs.getDouble("price"),
                        rs.getString("status")
                );
                rooms.add(room);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    // ✅ Find room by number
    public static RoomModel findByRoomNo(String roomNo) {
        String query = "SELECT * FROM rooms WHERE room_no = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, roomNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new RoomModel(
                        rs.getInt("id"),
                        rs.getString("room_no"),
                        rs.getString("room_type"),
                        rs.getDouble("price"),
                        rs.getString("status")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ✅ Delete room (accepts int id)
    public static boolean deleteRoom(int roomId) {
        String query = "DELETE FROM rooms WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, roomId);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Update room status
    public static boolean updateRoomStatus(int roomId, String newStatus) {
        String query = "UPDATE rooms SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, newStatus);
            ps.setInt(2, roomId);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
