package dao;

import model.User;
import org.w3c.dom.ls.LSOutput;

import java.sql.*;

public class UserDAO {
    private Connection conn = DatabaseConnection.getConnection();

    public User findUserByUsername(String username) {
        User user = null;
        String sql = "SELECT user_id, username, password, role, email, created_at " +
                "FROM users WHERE username = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("role"),
                        rs.getString("email"),
                        rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm kiếm người dùng: " + e.getMessage());
        }

        return user;
    }

    /**
     * Kiểm tra xem username đã tồn tại trong hệ thống chưa
     * @param username Tên đăng nhập cần kiểm tra
     * @return true nếu username đã tồn tại, false nếu chưa
     */
    public boolean checkUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi kiểm tra tên người dùng: " + e.getMessage());
        }

        return false;
    }

    public int add(User user) {
        if(checkUsernameExists(user.getUsername())){
            System.out.println("Tài khoản đã tồn tại");
            return 0;
        }
        String sql = "INSERT INTO users (username, password, role, email, created_at) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getRoleId());
            stmt.setString(4, user.getEmail());
            stmt.setTimestamp(5, user.getCreatedAt());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
                throw new SQLException("Không lấy được user_id");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm người dùng: " + e.getMessage());
        }
        return 0;
    }
    public void update(User user) throws SQLException {
        String sql = """
            UPDATE users
               SET username=?, password=?, role=?, email=?
             WHERE user_id=?
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setInt   (3, user.getRoleId());
            ps.setString(4, user.getEmail());
            ps.setInt   (5, user.getUserId());
            ps.executeUpdate();
        }
    }
    public void delete(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

