package dao;

import model.Class;
import java.sql.*;
import java.util.ArrayList;

public class ClassDAO implements InterfaceDAO<Class> {

    @Override
    public void add(Class c) {
        String sql = "INSERT INTO classes (class_id, course_id, teacher_id, max_students) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, c.getClassId());
            stmt.setString(2, c.getCourseId());
            stmt.setString(3, c.getTeacherId());
            stmt.setInt(4, c.getMaxStudents());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Class c) {
        String sql = "UPDATE classes SET course_id = ?, teacher_id = ?, max_students = ? WHERE class_id = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, c.getCourseId());
            stmt.setString(2, c.getTeacherId());
            stmt.setInt(3, c.getMaxStudents());
            stmt.setString(4, c.getClassId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Class c) {
        String sql = "DELETE FROM classes WHERE class_id = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, c.getClassId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Class selectById(Class c) {
        String sql = "SELECT * FROM classes WHERE class_id = ?";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, c.getClassId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Class(
                        rs.getString("class_id"),
                        rs.getString("course_id"),
                        rs.getString("teacher_id"),
                        rs.getInt("max_students")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Class selectByName(String name) {
        String sql = "SELECT * FROM classes WHERE class_id = ?"; // dùng class_id làm tên
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Class(
                        rs.getString("class_id"),
                        rs.getString("course_id"),
                        rs.getString("teacher_id"),
                        rs.getInt("max_students")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Class> selectAll() {
        ArrayList<Class> list = new ArrayList<>();
        String sql = "SELECT * FROM classes";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Class c = new Class(
                        rs.getString("class_id"),
                        rs.getString("course_id"),
                        rs.getString("teacher_id"),
                        rs.getInt("max_students")
                );
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public ArrayList<Class> selectByCondition(Class condition) {
        ArrayList<Class> list = new ArrayList<>();
        String sql = "SELECT * FROM classes WHERE teacher_id = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, condition.getTeacherId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Class c = new Class(
                        rs.getString("class_id"),
                        rs.getString("course_id"),
                        rs.getString("teacher_id"),
                        rs.getInt("max_students")
                );
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
