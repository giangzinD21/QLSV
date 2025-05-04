package dao;

import model.ClassGroup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassGroupDAO implements InterfaceDAO<ClassGroup>{
    private Connection conn = DatabaseConnection.getConnection();
    @Override
    public void add(ClassGroup classgroup) {
        String sql = "INSERT INTO class_groups(group_code, group_name) VALUES(?,?)";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, classgroup.getGroupCode());
            p.setString(2, classgroup.getGroupName());
            p.executeUpdate();
            System.out.println("Thêm lớp hành chính thành công");
        } catch (SQLException e) {
            System.out.println("Lỗi thêm ClassGroup: " + e.getMessage());
        }
    }
    @Override
    public void update(ClassGroup classgroup) {
        String sql = "UPDATE class_groups SET group_code=?, group_name=? WHERE group_id=?";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, classgroup.getGroupCode());
            p.setString(2, classgroup.getGroupName());
            p.setInt(3, classgroup.getGroupId());
            p.executeUpdate();
            System.out.println(">> Cập nhật lớp hành chính thành công");
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật: " + e.getMessage());
        }
    }
    @Override
    public void delete(ClassGroup classgroup) {
        String sql = "DELETE FROM class_groups WHERE group_id=?";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1,classgroup.getGroupId());
            p.executeUpdate();
            System.out.println("Xóa lớp hành chính thành công");
        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    @Override
    public ClassGroup selectById(int id) {
        String sql = "SELECT * FROM class_groups WHERE group_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ClassGroup(
                        rs.getInt("group_id"),
                        rs.getString("group_code"),
                        rs.getString("group_name"),
                        rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi lấy ClassGroup: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ClassGroup selectByName(String code) {
        String sql = "SELECT * FROM class_groups WHERE group_code = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ClassGroup(
                        rs.getInt("group_id"),
                        rs.getString("group_code"),
                        rs.getString("group_name"),
                        rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi lấy ClassGroup theo mã: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<ClassGroup> selectAll() {
        ArrayList<ClassGroup> list = new ArrayList<>();
        String sql = "SELECT * FROM class_groups";
        try (PreparedStatement p = conn.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                list.add(new ClassGroup(
                        rs.getInt("group_id"),
                        rs.getString("group_code"),
                        rs.getString("group_name"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi lấy danh sách ClassGroup: " + e.getMessage());
        }
        return list;
    }

    @Override
    public ArrayList<ClassGroup> selectByCondition(String condition) {
        return null;
    }
}
