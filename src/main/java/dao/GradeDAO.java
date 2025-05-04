// dao/GradeDAO.java
package dao;

import model.Grade;
import model.StudentGrade;

import java.sql.*;
import java.util.*;

public class GradeDAO {
    private final Connection conn = DatabaseConnection.getConnection();

    //Kiểm tra xem giá trị có trong khoảng 0-10 không.
    public boolean checkGradeValue(Grade g){
        if (g.getGradeValue() < 0.0 || g.getGradeValue() > 10.0) {
            System.out.println("❌ Điểm phải trong khoảng 0 - 10.");
            return false;
        }
        return true;
    }
    // Kiểm tra lớp đã xếp môn đó chưa?
    public boolean checkCourseInClass(Grade g){
        int scheduleId = 0;
        String sqlGroupCourse =
                "SELECT COUNT(*) FROM group_schedule "
                 + "WHERE group_id = ? AND course_id = ?";
        try (PreparedStatement ps1 = conn.prepareStatement(sqlGroupCourse)) {
            ps1.setInt(1, g.getGroupId());
            ps1.setInt(2, g.getCourseId());
            try (ResultSet rs1 = ps1.executeQuery()) {
                if (rs1.next() && rs1.getInt(1) == 0) {
                    System.out.println("❌ Lớp " + g.getGroupId()
                            + " chưa được xếp môn " + g.getCourseId() + ".");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi kiểm tra lịch học lớp-môn: " + e.getMessage());
            return false;
        }
        return true;
    }

    // Kiểm tra sinh viên đã có điểm chưa
    public boolean checkStudentInGrade(Grade g){
        int scheduleId = 0;
        String checkSql =
                "SELECT COUNT(*) FROM grades "
                        + "WHERE group_id=? AND course_id=? AND student_id=?";
        try (PreparedStatement ps3 = conn.prepareStatement(checkSql)) {
            ps3.setInt(1, g.getGroupId());
            ps3.setInt(2, g.getCourseId());
            ps3.setInt(3, g.getStudentId());
            try (ResultSet rs3 = ps3.executeQuery()) {
                if (rs3.next() && rs3.getInt(1) > 0) {
                    System.out.println("❌ Đã có điểm cho sinh viên này trong lớp và môn trên.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi kiểm tra trùng điểm: " + e.getMessage());
            return false;
        }
        return true;
    }
    //Kiểm tra sinh viên có trong lớp không
    public boolean checkStudentInClass(Grade g){
        String checkSql = "SELECT COUNT(*) FROM students WHERE student_id=? AND group_id=?";
        try(PreparedStatement stmt = conn.prepareStatement(checkSql)){
            stmt.setInt(1, g.getStudentId());
            stmt.setInt(2, g.getGroupId());
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next() && rs.getInt(1) > 0) return true;
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi kiểm tra sinh viên trong lớp: " + e.getMessage());
        }
        System.out.println("❌ Sinh viên " + g.getStudentId() + " chưa có mặt trong lớp " + g.getGroupId() + ".");
        return false;
    }
    public void add(Grade g) {
        // Kiểm tra điểm hợp lệ
        if(!checkGradeValue(g)) return;
        if(!checkCourseInClass(g)) return;
        if(!checkStudentInClass(g)) return;
        if(!checkStudentInGrade(g)) return;

        String sql =
                "INSERT INTO grades (group_id, course_id, student_id, grade_value) VALUES (?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, g.getGroupId());
            ps.setInt(2, g.getCourseId());
            ps.setInt(3, g.getStudentId());
            ps.setDouble(4, g.getGradeValue());
            ps.executeUpdate();
            System.out.println("✅ Thêm điểm thành công.");
        } catch (SQLException e) {
            System.out.println("❌ Lỗi thêm điểm: " + e.getMessage());
        }
    }

    public void update(Grade g) {
        if(!checkGradeValue(g)) return;
        if(!checkCourseInClass(g)) return;
        if(!checkStudentInClass(g)) return;
        String sql =
                "UPDATE grades SET grade_value=? WHERE grade_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, g.getGradeValue());
            ps.setInt(2, g.getGradeId());
            int u = ps.executeUpdate();
            if (u > 0) System.out.println("✅ Cập nhật điểm thành công.");
            else       System.out.println("❌ Không tìm thấy grade_id.");
        } catch (SQLException e) {
            System.out.println("❌ Lỗi cập nhật điểm: " + e.getMessage());
        }
    }

    public void delete(int gradeId) {
        String sql = "DELETE FROM grades WHERE grade_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, gradeId);
            int d = ps.executeUpdate();
            if (d > 0) System.out.println("✅ Xóa điểm thành công.");
            else       System.out.println("❌ Không tìm thấy grade_id.");
        } catch (SQLException e) {
            System.out.println("❌ Lỗi xóa điểm: " + e.getMessage());
        }
    }
    public List<Grade> selectAll() {
        List<Grade> list = new ArrayList<>();
        String sql = "SELECT * FROM grades";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Grade(
                        rs.getInt("grade_id"),
                        rs.getInt("group_id"),
                        rs.getInt("course_id"),
                        rs.getInt("student_id"),
                        rs.getDouble("grade_value"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi lấy danh sách điểm: " + e.getMessage());
        }
        return list;
    }


    public Grade selectById(int id) {
        String sql = "SELECT * FROM grades WHERE grade_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Grade(
                        rs.getInt("grade_id"),
                        rs.getInt("group_id"),
                        rs.getInt("course_id"),
                        rs.getInt("student_id"),
                        rs.getDouble("grade_value"),
                        rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi tìm điểm: " + e.getMessage());
        }
        return null;
    }
    public List<StudentGrade> selectByStudent(int studentId) {
        List<StudentGrade> list = new ArrayList<>();
        String sql = """
            SELECT g.grade_id,
                   c.course_name,
                   c.credits,
                   g.grade_value
              FROM grades g
              JOIN courses c ON g.course_id = c.course_id
             WHERE g.student_id = ?
             ORDER BY c.course_code
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new StudentGrade(
                            rs.getInt("grade_id"),
                            rs.getString("course_name"),
                            rs.getInt("credits"),
                            rs.getDouble("grade_value")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi lấy điểm sinh viên: " + e.getMessage());
        }
        return list;
    }
}
