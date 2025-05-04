package dao;

import model.Admin;
import model.Teacher;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO implements InterfaceDAO<Teacher>{
    private UserDAO userDAO = new UserDAO();
    Connection conn = DatabaseConnection.getConnection();

    /** Add mới: tạo user rồi tạo teacher profile */
    @Override
    public void add(Teacher teacher) {
        try{
            User u = new User(
                    teacher.getUsername(),
                    teacher.getPassword(),
                    3,
                    teacher.getEmail(),
                    new Timestamp(System.currentTimeMillis())
            );
            int user_id = userDAO.add(u);
            if (user_id > 0) {
                String sql = "INSERT INTO teachers(user_id, name, email) VALUES (?, ?, ?) ";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, user_id);
                    stmt.setString(2, teacher.getName());
                    stmt.setString(3, teacher.getEmail());

                    stmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println(">> Thêm Teacher thành công");
            }
            else{
                System.out.println(">> Thêm Teacher thất bại");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /** Update cả user và teacher */
    @Override
    public void update(Teacher teacher) {
        try {
            // 1) Cập nhật bảng users
            User u = new User(
                    teacher.getUserId(),
                    teacher.getUsername(),
                    teacher.getPassword(),
                    3,
                    teacher.getEmail(),
                    null  // giữ nguyên created_at
            );
            userDAO.update(u);

            // 2) Cập nhật bảng teachers
            String sql = "UPDATE teachers SET name = ?, email = ? WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, teacher.getName());
                stmt.setString(2, teacher.getEmail());
                stmt.setInt   (3, teacher.getUserId());
                stmt.executeUpdate();
            }
            System.out.println("Cập nhật giảng viên thành công");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Teacher teacher) {
        userDAO.delete(teacher.getUserId());
        System.out.println("Xóa giảng viên thành công");
    }

    /** Select theo teacher_id */
    @Override
    public Teacher selectById(int teacherId) {
        String sql = "SELECT * FROM teachers WHERE teacher_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teacherId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                // Lấy tiếp thông tin user
                String sqlUser = "SELECT * FROM users WHERE user_id = ?";
                try (PreparedStatement stmtUser = conn.prepareStatement(sqlUser)) {
                    stmtUser.setInt(1, userId);
                    ResultSet rsUser = stmtUser.executeQuery();
                    if (rsUser.next()) {
                        return new Teacher(
                                rs.getInt("teacher_id"),
                                userId,
                                rsUser.getString("username"),
                                rsUser.getString("password"),
                                rs.getString("name"),
                                rsUser.getString("email"),
                                rsUser.getTimestamp("created_at")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return null;
    }

    @Override
    public Teacher selectByName(String name) {
        String sql = "SELECT * FROM teachers WHERE name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String sqlUser = "SELECT * FROM users WHERE user_id = ?";
                try (PreparedStatement stmtUser = conn.prepareStatement(sqlUser)) {
                    stmtUser.setInt(1, userId);
                    ResultSet rsUser = stmtUser.executeQuery();
                    if (rsUser.next()) {
                        return new Teacher(
                                rs.getInt("teacher_id"),
                                userId,
                                rsUser.getString("username"),
                                rsUser.getString("password"),
                                rs.getString("name"),
                                rsUser.getString("email"),
                                rsUser.getTimestamp("created_at")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    /** Lấy tất cả teacher */
    @Override
    public ArrayList<Teacher> selectAll() {
        ArrayList<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT t.teacher_id, t.user_id, u.username, u.password, t.name, u.email, u.created_at " +
                "FROM teachers t " +
                "JOIN users u ON t.user_id = u.user_id ";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Teacher t = new Teacher(
                        rs.getInt("teacher_id"),
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getTimestamp("created_at")
                    );
                teachers.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    @Override
    public ArrayList<Teacher> selectByCondition(String condition) {
        ArrayList<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT t.teacher_id, t.user_id, u.username, u.password, t.name, u.email, u.created_at " +
                "FROM teachers t " +
                "JOIN users u ON t.user_id = u.user_id " +
                "WHERE u.email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, condition);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Teacher t = new Teacher(
                        rs.getInt("teacher_id"),
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getTimestamp("created_at")
                );
                teachers.add(t);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return teachers;
    }

    public int selectByUserId(int userId) {
        String sql = "SELECT * FROM teachers WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("teacher_id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }
}
