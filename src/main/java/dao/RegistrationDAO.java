package dao;

import model.ClassSchedule;
import model.Registration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAO implements InterfaceDAO<Registration> {

    @Override
    public void add(Registration registration) {
        String sql = "INSERT INTO registrations (student_id, class_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, registration.getStudentId());
            stmt.setInt(2, registration.getClassId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Registration registration) {
        String sql = "UPDATE registrations SET student_id = ?, class_id = ? WHERE registration_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, registration.getStudentId());
            stmt.setInt(2, registration.getClassId());
            stmt.setInt(3, registration.getRegistrationId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Registration registration) {
        String sql = "DELETE FROM registrations WHERE registration_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, registration.getRegistrationId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Registration selectById(Registration registration) {
        String sql = "SELECT * FROM registrations WHERE registration_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, registration.getRegistrationId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Registration(
                        rs.getInt("registration_id"),
                        rs.getInt("student_id"),
                        rs.getInt("class_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Registration selectByName(String name) {
        String sql = """
            SELECT r.* FROM registrations r
            JOIN students s ON r.student_id = s.student_id
            WHERE s.name = ?
        """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Registration(
                        rs.getInt("registration_id"),
                        rs.getInt("student_id"),
                        rs.getInt("class_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Registration> selectAll() {
        ArrayList<Registration> registrations = new ArrayList<>();
        String sql = "SELECT * FROM registrations";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                registrations.add(new Registration(
                        rs.getInt("registration_id"),
                        rs.getInt("student_id"),
                        rs.getInt("class_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrations;
    }

    @Override
    public ArrayList<Registration> selectByCondition(Registration registration) {
        ArrayList<Registration> registrations = new ArrayList<>();
        String sql = "SELECT * FROM registrations WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, registration.getStudentId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                registrations.add(new Registration(
                        rs.getInt("registration_id"),
                        rs.getInt("student_id"),
                        rs.getInt("class_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrations;
    }

    // ✅ Lấy thời khóa biểu theo student và kỳ học
    public List<ClassSchedule> getScheduleByStudent(int studentId, String semester) {
        List<ClassSchedule> schedules = new ArrayList<>();
        String sql = """
            SELECT c.class_id, co.course_name, c.schedule, c.semester, t.name AS teacher_name
            FROM registrations r
            JOIN classes c ON r.class_id = c.class_id
            JOIN courses co ON c.course_id = co.course_id
            JOIN teachers t ON c.teacher_id = t.teacher_id
            WHERE r.student_id = ? AND c.semester = ?
        """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setString(2, semester);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                schedules.add(new ClassSchedule(
                        rs.getInt("class_id"),
                        rs.getString("course_name"),
                        rs.getString("schedule"),
                        rs.getString("semester"),
                        rs.getString("teacher_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    // ✅ Kiểm tra đã đăng ký lớp chưa
    public boolean exists(int studentId, int classId) {
        String sql = "SELECT 1 FROM registrations WHERE student_id = ? AND class_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, classId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Đếm số sinh viên trong lớp
    public int countStudentsInClass(int classId) {
        String sql = "SELECT COUNT(*) FROM registrations WHERE class_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ✅ Lấy số lượng tối đa của lớp
    public int getMaxStudents(int classId) {
        String sql = "SELECT max_students FROM classes WHERE class_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("max_students");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
