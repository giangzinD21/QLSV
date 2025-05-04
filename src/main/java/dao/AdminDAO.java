package dao;

import model.*;
import model.Class;

import java.sql.*;
import java.util.ArrayList;

public class AdminDAO implements InterfaceDAO<Admin> {
    Connection conn = DatabaseConnection.getConnection();
    UserDAO userDAO = new UserDAO();
    @Override
    public void add(Admin admin) {
        try{
            User u = new User(
                    admin.getUsername(),
                    admin.getPassword(),
                    admin.isSuperAdmin() ? 1 : 2,                        // role = '2' cho admin
                    admin.getEmail(),
                    new Timestamp(System.currentTimeMillis())
            );
            int user_id = userDAO.add(u);
            if (user_id > 0) {
                String sql = "INSERT INTO admins (user_id, name, super) VALUES (?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, user_id);
                    stmt.setString(2, admin.getName());
                    stmt.setBoolean(3,admin.isSuperAdmin());

                    stmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println(">> Thêm Admin thành công");
            }
            else{
                System.out.println("Thêm Admin thất bại");
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Admin admin) {
        try{
            User u = new User(
                    admin.getUserId(),
                    admin.getUsername(),
                    admin.getPassword(),
                    admin.isSuperAdmin() ? 1 : 2,
                    admin.getEmail(),
                    null      // tạoAt giữ nguyên
            );
            userDAO.update(u);
            String sql = "UPDATE admins SET name = ?, super = ? WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, admin.getName());
                stmt.setBoolean(2, admin.isSuperAdmin());
                stmt.setInt(3, admin.getUserId());
                stmt.executeUpdate();
            }

            System.out.println("Cập nhật Admin thành công");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Admin admin) {
        try{
            userDAO.delete(admin.getUserId());
            System.out.println(">> Xóa Admin thành công!");
        }
        catch(Exception e){
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    @Override
    public Admin selectById(int id) {
        String sql = "SELECT * FROM admins WHERE admin_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String sqlUser = "SELECT * FROM users WHERE user_id = ?";
                try (PreparedStatement stmtUser = conn.prepareStatement(sqlUser)) {
                    stmtUser.setInt(1, userId);
                    ResultSet rsUser = stmtUser.executeQuery();
                    if (rsUser.next()) {
                        return new Admin(
                                rs.getInt("user_id"),
                                rs.getInt("admin_id"),
                                rsUser.getString("username"),
                                rsUser.getString("password"),
                                rs.getString("name"),
                                rs.getBoolean("super"),
                                rsUser.getString("email")
                        );
                    }

                }
                catch (SQLException e) {
                    System.out.println(e.getMessage());
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Admin selectByName(String name) {
        String sql = "SELECT * FROM admins WHERE name = ?";
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
                        return new Admin(
                                rs.getInt("admin_id"),
                                rsUser.getString("username"),
                                rsUser.getString("password"),
                                rs.getString("name"),
                                rsUser.getString("email"),
                                rs.getBoolean("super")
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
    public ArrayList<Admin> selectAll() {
        ArrayList<Admin> admins = new ArrayList<>();
        String sql = "SELECT * FROM admins";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String sqlUser = "SELECT * FROM users WHERE user_id = ?";
                try (PreparedStatement stmtUser = conn.prepareStatement(sqlUser)) {
                    stmtUser.setInt(1, userId);
                    ResultSet rsUser = stmtUser.executeQuery();
                    if (rsUser.next()) {
                        admins.add(new Admin(
                                rs.getInt("admin_id"),
                                rsUser.getString("username"),
                                rsUser.getString("password"),
                                rs.getString("name"),
                                rsUser.getString("email"),
                                rs.getBoolean("super")
                        ));
                    }
                }
                catch (SQLException e) {
                    System.out.println(e.getMessage());
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    @Override
    public ArrayList<Admin> selectByCondition(String condition) {
        return null;
    }
    public int selectByUserId(int userId) {
        String sql = "SELECT * FROM admins WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("admin_id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }
}