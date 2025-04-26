package dao;

import model.Teacher;
import ui.TeacherUI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TeacherDAO implements InterfaceDAO<Teacher> {
        public void getCheckTeacher(String teacherEmail, String teacherPassword) {
            String sql = "SELECT * FROM teachers WHERE email = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, teacherEmail);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    // Không cần kiểm tra lại email vì đã query theo email
                    if (rs.getString("password").equals(teacherPassword)) {
                        TeacherUI.showMenu(); // Gọi giao diện sau khi đăng nhập
                    } else {
                        System.out.println("Wrong email or password");
                    }
                } else {
                    System.out.println("Teacher not found");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void add(Teacher teacher) {
        String sql = "INSERT INTO teachers (teacher_id, name, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teacher.getTeacherId());
            stmt.setString(2, teacher.getName());
            stmt.setString(3, teacher.getEmail());
            stmt.setString(5, teacher.getPassword());
            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Teacher added successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Teacher teacher) {
        String sql = "UPDATE teachers SET name = ?, email = ?,password = ? WHERE teacher_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teacher.getTeacherId());
            stmt.setString(2, teacher.getName());
            stmt.setString(3, teacher.getEmail());
            stmt.setString(4,teacher.getPassword());
            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Teacher updated successfully");
            }
            else{
                System.out.println("Teacher not found");}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Teacher teacher) {
        String sql = "DELETE FROM teachers WHERE teacher_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teacher.getTeacherId());
            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Teacher deleted successfully");
            } else {
                System.out.println("Teacher not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Teacher selectById(String id) {
        String sql = "SELECT * FROM teachers WHERE teacher_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
           try  (ResultSet rs = stmt.executeQuery());
            if (rs.next()) {
                result = new Teacher(
                        rs.getInt("teacher_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Teacher selectByName(String name) {
        String sql = "SELECT * FROM teachers WHERE name = ?";
        Teacher result = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    result = new Teacher(
                            rs.getInt("teacher_id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<Teacher> selectAll() {
        ArrayList<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teachers";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                teachers.add(new Teacher(
                        rs.getInt("teacher_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }
    @Override
public ArrayList<Teacher> selectByCondition(Teacher teacher) {
    String sql = "SELECT * FROM teachers WHERE email LIKE ?";
    ArrayList<Teacher> teachers = new ArrayList<>();

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, "%" + teacher.getEmail() + "%");

        ResultSet rs = stmt.executeQuery();

        // Duyệt qua các kết quả trả về và thêm vào danh sách
        while (rs.next()) {
            teachers.add(new Teacher(
                    rs.getInt("teacher_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password")
            ));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return teachers;
}

