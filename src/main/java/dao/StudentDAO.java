package dao;

import model.Student;
import ui.StudentUI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class StudentDAO implements InterfaceDAO<Student> {
    public void getCheckStudent(String studentEmail, String studentPassword) {
        String sql = "SELECT * FROM students WHERE email= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentEmail);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) { // Phải có rs.next() trước khi lấy dữ liệu từ ResultSet
                if (rs.getString("email").equals(studentEmail) && rs.getString("password").equals(studentPassword)) {
<<<<<<< HEAD
                    int studentId = rs.getInt("student_id"); // hoặc rs.getInt("id") tùy theo tên cột
                    StudentUI.indexStudents(studentId); // ✅ Truyền đúng tham số
=======
                    StudentUI.showMenu();
>>>>>>> origin/lamhoang
                } else {
                    System.out.println("Wrong email or password");
                }
            } else {
                System.out.println("User not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Student student) {
        String sql = "INSERT INTO students (student_id, name, student_code, email, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, student.getStudentId());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getStudentCode());
            stmt.setString(4, student.getEmail());
            stmt.setString(5, student.getPassword());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Student added successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void update(Student student) {
        String sql = "UPDATE students SET name = ?, student_code = ?, email = ?, password = ? WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Thiết lập các tham số cho PreparedStatement
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getStudentCode());
            stmt.setString(3, student.getEmail());
            stmt.setString(4, student.getPassword());
            stmt.setInt(5,student.getStudentId()); // Sử dụng studentId để tìm sinh viên cần cập nhật

            // Thực thi câu lệnh update
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("No student found with the given ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void delete(Student student) {
        String sql = "DELETE FROM students WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Thiết lập tham số cho PreparedStatement
            stmt.setInt(1, student.getStudentId()); // Sử dụng studentId để xác định sinh viên cần xóa

            // Thực thi câu lệnh delete
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("No student found with the given ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
<<<<<<< HEAD
    public Student selectById(String id) {
        return null;
=======
    public Student selectById(Student student) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        Student result = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Thiết lập tham số cho PreparedStatement
            stmt.setInt(1, student.getStudentId()); // Sử dụng studentId để tìm sinh viên

            // Thực thi câu lệnh SELECT và lấy kết quả
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Lấy thông tin sinh viên từ ResultSet và tạo đối tượng Student
                    result = new Student(
                            rs.getInt("student_id"),
                            rs.getString("name"),
                            rs.getString("student_code"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
>>>>>>> origin/lamhoang
    }


    @Override
    public Student selectByName(String name) {
        String sql = "SELECT * FROM students WHERE name = ?";
        Student result = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Thiết lập tham số cho PreparedStatement
            stmt.setString(1, name); // Sử dụng tên để tìm sinh viên

            // Thực thi câu lệnh SELECT và lấy kết quả
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Lấy thông tin sinh viên từ ResultSet và tạo đối tượng Student
                    result = new Student(
                            rs.getInt("student_id"),
                            rs.getString("name"),
                            rs.getString("student_code"),
                            rs.getString("email"),
                            rs.getString("password")
                            // Nếu có thêm trường khác, bạn cần thêm vào constructor
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result; // Trả về đối tượng sinh viên nếu tìm thấy, nếu không trả về null
    }


    @Override
    public ArrayList<Student> selectAll() {
        String sql = "SELECT * FROM students";
        ArrayList<Student> students = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Duyệt qua tất cả các dòng kết quả trả về
            while (rs.next()) {
                // Thêm đối tượng Student vào danh sách students
                students.add(new Student(
                        rs.getInt("student_id"), // Lấy student_id từ ResultSet
                        rs.getString("name"), // Lấy name từ ResultSet
                        rs.getString("student_code"), // Lấy student_code từ ResultSet
                        rs.getString("email"), // Lấy email từ ResultSet
                        rs.getString("password") // Lấy password từ ResultSet
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students; // Trả về danh sách sinh viên
    }


    @Override
    public ArrayList<Student> selectByCondition(Student student) {
        String sql = "SELECT * FROM students WHERE email LIKE ?";
        ArrayList<Student> students = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Thiết lập điều kiện tìm kiếm theo email
            stmt.setString(1, "%" + student.getEmail() + "%"); // Tìm kiếm email chứa giá trị input

            ResultSet rs = stmt.executeQuery();

            // Duyệt qua tất cả các dòng kết quả trả về
            while (rs.next()) {
                // Thêm đối tượng Student vào danh sách students
                students.add(new Student(
                        rs.getInt("student_id"), // Lấy student_id từ ResultSet
                        rs.getString("name"), // Lấy name từ ResultSet
                        rs.getString("student_code"), // Lấy student_code từ ResultSet
                        rs.getString("email"), // Lấy email từ ResultSet
                        rs.getString("password") // Lấy password từ ResultSet
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students; // Trả về danh sách sinh viên tìm được
    }



}
