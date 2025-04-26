package dao;

import model.Grade;
import java.sql.*;
import java.util.ArrayList;

public class GradeDAO {

    // Phương thức lấy điểm của sinh viên theo tất cả các học kỳ
    public ArrayList<Grade> getGradesByStudent(int studentId) {
        ArrayList<Grade> list = new ArrayList<>();
        String sql = """
            SELECT g.grade, c.course_name, c.credits, cl.semester
            FROM grades g
            JOIN classes cl ON g.class_id = cl.class_id
            JOIN courses c ON cl.course_id = c.course_id
            WHERE g.student_id = ?
        """;
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Grade(
                        rs.getString("course_name"),
                        rs.getInt("credits"),
                        rs.getFloat("grade"),
                        rs.getString("semester") // Thêm học kỳ
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Phương thức lấy điểm của sinh viên theo học kỳ cụ thể
    public ArrayList<Grade> getGradesByStudentAndSemester(int studentId, String semester) {
        ArrayList<Grade> list = new ArrayList<>();
        String sql = """
            SELECT g.grade, c.course_name, c.credits
            FROM grades g
            JOIN classes cl ON g.class_id = cl.class_id
            JOIN courses c ON cl.course_id = c.course_id
            WHERE g.student_id = ? AND cl.semester = ?
        """;
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, studentId);
            stmt.setString(2, semester);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Grade(
                        rs.getString("course_name"),
                        rs.getInt("credits"),
                        rs.getFloat("grade"),
                        semester // Học kỳ đã cho
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Phương thức lấy tất cả các môn học của một sinh viên cho một học kỳ
    public ArrayList<String> getAllCoursesForSemester(int studentId, String semester) {
        ArrayList<String> courses = new ArrayList<>();
        String sql = """
            SELECT c.course_name
            FROM grades g
            JOIN classes cl ON g.class_id = cl.class_id
            JOIN courses c ON cl.course_id = c.course_id
            WHERE g.student_id = ? AND cl.semester = ?
        """;
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, studentId);
            stmt.setString(2, semester);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(rs.getString("course_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    // Phương thức tính GPA của sinh viên cho tất cả các học kỳ
    public float calculateOverallGPA(int studentId) {
        String sql = """
            SELECT SUM(g.grade * c.credits) / SUM(c.credits) AS gpa
            FROM grades g
            JOIN classes cl ON g.class_id = cl.class_id
            JOIN courses c ON cl.course_id = c.course_id
            WHERE g.student_id = ?
        """;
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getFloat("gpa");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0f;
    }
}
