package dao;

import model.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {
    private final Connection conn = DatabaseConnection.getConnection();

    public void add(Notification n) {
        String sql = """
            INSERT INTO notifications
              (sender_id, target_type, target_id, message)
            VALUES (?,?,?,?)
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt   (1, n.getSenderId());
            ps.setString(2, n.getTargetType());
            if (n.getTargetId() != null) ps.setInt(3, n.getTargetId());
            else                          ps.setNull(3, Types.INTEGER);
            ps.setString(4, n.getMessage());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        n.setNotificationId(rs.getInt(1));
                    }
                }
                System.out.println("✅ Gửi thông báo thành công (ID=" + n.getNotificationId() + ").");
            } else {
                System.out.println("❌ Gửi thông báo thất bại.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi thêm thông báo: " + e.getMessage());
        }
    }

    public void update(Notification n) {
        String sql = """
            UPDATE notifications
               SET message=?, target_type=?, target_id=?
             WHERE notification_id=?
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, n.getMessage());
            ps.setString(2, n.getTargetType());
            if (n.getTargetId() != null) ps.setInt(3, n.getTargetId());
            else                          ps.setNull(3, Types.INTEGER);
            ps.setInt   (4, n.getNotificationId());

            int u = ps.executeUpdate();
            if (u > 0) System.out.println("✅ Cập nhật thông báo thành công.");
            else       System.out.println("❌ Không tìm thấy notification_id.");
        } catch (SQLException e) {
            System.out.println("❌ Lỗi cập nhật thông báo: " + e.getMessage());
        }
    }

    public void delete(int notificationId) {
        String sql = "DELETE FROM notifications WHERE notification_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, notificationId);
            int d = ps.executeUpdate();
            if (d > 0) System.out.println("✅ Xóa thông báo thành công.");
            else       System.out.println("❌ Không tìm thấy notification_id.");
        } catch (SQLException e) {
            System.out.println("❌ Lỗi xóa thông báo: " + e.getMessage());
        }
    }

    public Notification selectById(int id) {
        String sql = "SELECT * FROM notifications WHERE notification_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Notification(
                            rs.getInt("notification_id"),
                            rs.getInt("sender_id"),
                            rs.getString("target_type"),
                            rs.getObject("target_id") != null ? rs.getInt("target_id") : null,
                            rs.getString("message"),
                            rs.getTimestamp("created_at")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi lấy thông báo theo ID: " + e.getMessage());
        }
        return null;
    }

    public List<Notification> selectAllBySender(int adminId) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE sender_id=? ORDER BY created_at DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, adminId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Notification(
                            rs.getInt("notification_id"),
                            rs.getInt("sender_id"),
                            rs.getString("target_type"),
                            rs.getObject("target_id") != null ? rs.getInt("target_id") : null,
                            rs.getString("message"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi lấy danh sách thông báo: " + e.getMessage());
        }
        return list;
    }

    /**
     * Lấy thông báo cho teacher hoặc student:
     * - target_type = 'all'
     * - OR target_type = 'teacher' AND target_id = teacherId
     * - OR target_type = 'student' AND target_id = studentId
     */
    public List<Notification> selectInbox(String role, int id) {
        String sql = """
            SELECT * FROM notifications
             WHERE target_type = 'all'
                OR (target_type = ? AND target_id = ?)
             ORDER BY created_at DESC
        """;
        List<Notification> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, role);   // "teacher" hoặc "student"
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Notification n = new Notification();
                n.setNotificationId(rs.getInt("notification_id"));
                n.setSenderId(rs.getInt("sender_id"));
                n.setTargetType(rs.getString("target_type"));
                int tid = rs.getInt("target_id");
                if (!rs.wasNull()) n.setTargetId(tid);
                n.setMessage(rs.getString("message"));
                n.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(n);
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi lấy thông báo: " + e.getMessage());
        }
        return list;
    }
}
