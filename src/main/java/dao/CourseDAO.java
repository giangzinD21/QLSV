package dao;

import model.*;
import model.Class;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseDAO implements InterfaceDAO<Course> {
    Connection conn = DatabaseConnection.getConnection();
    @Override
    public void add(Course course) {
        String sql = "INSERT INTO courses (course_code, course_name, credits) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getCourseCode());
            stmt.setString(2, course.getCourseName());
            stmt.setInt(3, course.getCredits());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Thêm môn học thành công");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm môn học: " + e.getMessage());
        }
    }


    @Override
    public void update(Course course) {
        String sql = "UPDATE courses SET course_code = ?, course_name = ?, credits = ? WHERE course_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, course.getCourseCode());
            stmt.setString(2, course.getCourseName());
            stmt.setInt(3, course.getCredits());
            stmt.setInt(4, course.getCourseId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cập nhật môn học thành công");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật môn học: " + e.getMessage());
        }
    }

    @Override
    public void delete(Course course) {
        String sql = "DELETE FROM courses WHERE course_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, course.getCourseId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Xóa môn học thành công");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa môn học: " + e.getMessage());
        }
    }
    @Override
    public Course selectById(int id) {
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Course(
                            rs.getInt("course_id"),
                            rs.getString("course_code"),
                            rs.getString("course_name"),
                            rs.getInt("credits")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi chọn theo id : " + e.getMessage());
        }
        return null;
    }
    @Override
    // Tìm theo code trước, nếu không có thì tìm theo name
    public Course selectByName(String name) {
        String sql = "SELECT * FROM courses WHERE course_code=? OR course_name=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Course(
                            rs.getInt("course_id"),
                            rs.getString("course_code"),
                            rs.getString("course_name"),
                            rs.getInt("credits")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi chọn theo code hoặc name : " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Course> selectAll() {
        String sql = "SELECT * FROM courses";
        ArrayList<Course> courses = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                courses.add(new Course(
                        rs.getInt("course_id"),
                        rs.getString("course_code"),
                        rs.getString("course_name"),
                        rs.getInt("credits")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi chọn tất cả : " + e.getMessage());
        }
        return courses;
    }

    @Override
    public ArrayList<Course> selectByCondition(String condition) {
        return null;
    }
    public ArrayList<Course> selectByCondition(Course course) {
        String sql = "SELECT * FROM courses WHERE course_code LIKE ? OR course_name LIKE ?";
        ArrayList<Course> courses = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + course.getCourseCode() + "%");
            stmt.setString(2, "%" + course.getCourseName() + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    courses.add(new Course(
                            rs.getInt("course_id"),
                            rs.getString("course_code"),
                            rs.getString("course_name"),
                            rs.getInt("credits")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
}
