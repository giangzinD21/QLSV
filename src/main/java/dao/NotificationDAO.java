package dao;

import model.Notification;
import java.sql.*;
import java.util.ArrayList;

public class NotificationDAO implements InterfaceDAO<Notification> {

    @Override
    public void add(Notification noti) {
        String sql = "INSERT INTO notifications (sender_id, sender_role, message) VALUES (?, ?, ?)";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, noti.getSenderId());
            stmt.setString(2, noti.getSenderRole());
            stmt.setString(3, noti.getMessage());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Notification noti) {
        String sql = "UPDATE notifications SET message = ? WHERE notification_id = ?";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, noti.getMessage());
            stmt.setInt(2, noti.getId());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("❌ Không tìm thấy thông báo để cập nhật!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Notification noti) {
        String sql = "DELETE FROM notifications WHERE notification_id = ?";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, noti.getId());
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted == 0) {
                System.out.println("❌ Không tìm thấy thông báo để xóa!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Notification selectById(Notification noti) {
        String sql = "SELECT * FROM notifications WHERE notification_id = ?";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, noti.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Notification(
                        rs.getInt("notification_id"),
                        rs.getInt("sender_id"),
                        rs.getString("sender_role"),
                        rs.getString("message"),
                        rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Notification selectByName(String name) {
        return null; // Không áp dụng cho Notification
    }

    @Override
    public ArrayList<Notification> selectAll() {
        ArrayList<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notifications ORDER BY created_at DESC";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Notification noti = new Notification(
                        rs.getInt("notification_id"),
                        rs.getInt("sender_id"),
                        rs.getString("sender_role"),
                        rs.getString("message"),
                        rs.getTimestamp("created_at")
                );
                list.add(noti);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<Notification> selectByCondition(Notification condition) {
        ArrayList<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE sender_id = ? AND sender_role = ? ORDER BY created_at DESC";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, condition.getSenderId());
            stmt.setString(2, condition.getSenderRole());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Notification noti = new Notification(
                        rs.getInt("notification_id"),
                        rs.getInt("sender_id"),
                        rs.getString("sender_role"),
                        rs.getString("message"),
                        rs.getTimestamp("created_at")
                );
                list.add(noti);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
