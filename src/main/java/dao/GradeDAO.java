package dao;

import model.Grade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GradeDAO implements InterfaceDAO<Grade> {

    // Thêm điểm
    @Override
    public void add(Grade grade) {
        String sql = "INSERT INTO grades (grade_id,student_id, class_id, grade) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, grade.getGradeId());
            stmt.setInt(2, grade.getStudentId());
            stmt.setInt(3, grade.getClassId());
            stmt.setFloat(4, grade.getGrade());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Grade added successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật điểm
    @Override
    public void update(Grade grade) {
        String sql = "UPDATE grades SET grade = ? WHERE grade_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setFloat(1, grade.getGrade());
            stmt.setInt(2, grade.getGradeId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Grade updated successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa điểm
    @Override
    public void delete(Grade grade) {
        String sql = "DELETE FROM grades WHERE grade_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, grade.getGradeId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Grade deleted successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy điểm theo ID
    @Override
    public Grade selectById(Grade grade) {
        String sql = "SELECT * FROM grades WHERE grade_id = ?";
        Grade result = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, grade.getGradeId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    result = new Grade(
                            rs.getInt("grade_id"),
                            rs.getInt("student_id"),
                            rs.getInt("class_id"),
                            rs.getFloat("grade")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Lấy điểm theo tên sinh viên
    @Override
    public Grade selectByName(String name) {
        String sql = "SELECT * FROM grades g JOIN students s ON g.student_id = s.student_id WHERE s.name = ?";
        Grade result = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    result = new Grade(
                            rs.getInt("grade_id"),
                            rs.getInt("student_id"),
                            rs.getInt("class_id"),
                            rs.getFloat("grade")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Lấy tất cả điểm
    @Override
    public ArrayList<Grade> selectAll() {
        String sql = "SELECT * FROM grades";
        ArrayList<Grade> grades = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                grades.add(new Grade(
                        rs.getInt("grade_id"),
                        rs.getInt("student_id"),
                        rs.getInt("class_id"),
                        rs.getFloat("grade")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    // Lấy điểm theo điều kiện (student_id, class_id)
    @Override
    public ArrayList<Grade> selectByCondition(Grade grade) {
        String sql = "SELECT * FROM grades WHERE student_id = ? AND class_id = ?";
        ArrayList<Grade> grades = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, grade.getStudentId());
            stmt.setInt(2, grade.getClassId());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    grades.add(new Grade(
                            rs.getInt("grade_id"),
                            rs.getInt("student_id"),
                            rs.getInt("class_id"),
                            rs.getFloat("grade")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }
}
