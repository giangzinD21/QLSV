// dao/StudentDAO.java
package dao;

import model.Student;
import model.User;

import java.sql.*;
import java.util.ArrayList;

public class StudentDAO implements InterfaceDAO<Student> {
    private Connection conn = DatabaseConnection.getConnection();
    private UserDAO userDAO = new UserDAO();

    @Override
    public void add(Student s) {
        try {
            // 1) Tạo user với role=4
            User u = new User(
                    s.getUsername(),
                    s.getPassword(),
                    4,
                    s.getEmail(),
                    new Timestamp(System.currentTimeMillis())
            );
            int userId = userDAO.add(u);

            if (userId>0) {

                String sql = "INSERT INTO students(user_id, group_id,  name, student_code, email) VALUES(?,?,?,?,?)";
                try (PreparedStatement p = conn.prepareStatement(sql)) {
                    p.setInt   (1, userId);
                    p.setInt   (2, s.getGroupId());
                    p.setString(3, s.getName());
                    p.setString(4, s.getStudentCode());
                    p.setString(5, s.getEmail());
                    p.executeUpdate();
                }
                System.out.println("Thêm sinh viên thành công");
            } else {
                System.out.println("Thêm sinh viên thất bại");
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Student s) {
        try {
            // 1) Cập nhật users
            User u = new User(
                    s.getUserId(),
                    s.getUsername(),
                    s.getPassword(),
                    4,
                    s.getEmail(),
                    null
            );
            userDAO.update(u);

            // 2) Cập nhật students
            String sql = "UPDATE students SET name=?, group_id=?, student_code=?, email=? WHERE user_id=?";
            try (PreparedStatement p = conn.prepareStatement(sql)) {
                p.setString(1, s.getName());
                p.setInt   (2, s.getGroupId());
                p.setString(3, s.getStudentCode());
                p.setString(4, s.getEmail());
                p.setInt   (5, s.getUserId());
                p.executeUpdate();
            }
            System.out.println("Cập nhật sinh viên thành công");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Student s) {
        // Xóa user => cascade xóa students
        try{
            userDAO.delete(s.getUserId());
            System.out.println("Xóa sinh viên thành công");
        }
        catch(Exception e){
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    @Override
    public Student selectById(int id) {
        try {
            // lấy theo student_id
            String sel = "SELECT * FROM students WHERE student_id=?";
            try (PreparedStatement p = conn.prepareStatement(sel)) {
                p.setInt(1,id);
                ResultSet rs = p.executeQuery();
                if (rs.next()) {
                    int uid = rs.getInt("user_id");
                    // lấy user
                    String su = "SELECT * FROM users WHERE user_id=?";
                    try (PreparedStatement pu = conn.prepareStatement(su)) {
                        pu.setInt(1, uid);
                        ResultSet ru = pu.executeQuery();
                        if (ru.next()) {
                            return new Student(
                                    rs.getInt("student_id"),
                                    uid,
                                    rs.getInt("group_id"),
                                    ru.getString("username"),
                                    ru.getString("password"),
                                    rs.getString("name"),
                                    rs.getString("student_code"),
                                    ru.getString("email"),
                                    ru.getTimestamp("created_at")
                            );
                        }
                    }
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Student selectByName(String name) {
        // tìm theo student_code hoặc name; dùng name theo interface
        try {
            String sel = "SELECT * FROM students WHERE name=?";
            try (PreparedStatement p = conn.prepareStatement(sel)) {
                p.setString(1,name);
                ResultSet rs = p.executeQuery();
                if (rs.next()) {
                    int uid = rs.getInt("user_id");
                    String su = "SELECT * FROM users WHERE user_id=?";
                    try (PreparedStatement pu = conn.prepareStatement(su)) {
                        pu.setInt(1, uid);
                        ResultSet ru = pu.executeQuery();
                        if (ru.next()) {
                            return new Student(
                                    rs.getInt("student_id"),
                                    uid,
                                    rs.getInt("group_id"),
                                    ru.getString("username"),
                                    ru.getString("password"),
                                    rs.getString("name"),
                                    rs.getString("student_code"),
                                    ru.getString("email"),
                                    ru.getTimestamp("created_at")
                            );
                        }
                    }
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Student> selectAll() {
        ArrayList<Student> list = new ArrayList<>();
        String sel = "SELECT * FROM students";
        try (PreparedStatement p = conn.prepareStatement(sel);
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                int uid = rs.getInt("user_id");
                String su = "SELECT * FROM users WHERE user_id=?";
                try (PreparedStatement pu = conn.prepareStatement(su)) {
                    pu.setInt(1,uid);
                    ResultSet ru = pu.executeQuery();
                    if (ru.next()) {
                        list.add(new Student(
                                rs.getInt("student_id"),
                                uid,
                                rs.getInt("group_id"),
                                ru.getString("username"),
                                ru.getString("password"),
                                rs.getString("name"),
                                rs.getString("student_code"),
                                ru.getString("email"),
                                ru.getTimestamp("created_at")
                        ));
                    }
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<Student> selectByCondition(String cond) {
        return null;  // không dùng
    }
    public int selectByUserId(int userId) {
        String sql = "SELECT * FROM students WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("student_id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }
}
