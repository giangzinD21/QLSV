package ui;

import dao.StudentDAO;
import model.Student;

import java.util.ArrayList;
import java.util.Scanner;
import ui.RegistrationUI;

public class StudentUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentDAO studentDAO = new StudentDAO();

<<<<<<< HEAD
    public static void indexStudents(int studentId) {
        while (true) {
            System.out.println("1. Đăng ký môn học");
            System.out.println("2. Xem kết quả học tập (Xem điểm, xếp loại học lực)");
            System.out.println("3. Xem kết quả đăng ký môn học");
            System.out.println("4. Xem thông báo từ giảng viên/quản trị viên");
            System.out.println("5. Đăng xuất");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Lựa chọn không hợp lệ.");
                continue;
            }
            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
<<<<<<< HEAD
                    break;
=======
                    RegistrationUI.showRegistrationMenu(studentId);                    break;
>>>>>>> origin/huyle
                case 4:
                    return;
                case 5:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
=======
    public static void showMenu() {
        while (true) {
            System.out.println("\nChọn chức năng:");
            System.out.println("1. Thêm sinh viên");
            System.out.println("2. Sửa sinh viên");
            System.out.println("3. Xóa sinh viên");
            System.out.println("4. Xem tất cả sinh viên");
            System.out.println("5. Tìm sinh viên theo ID");
            System.out.println("6. Tìm sinh viên theo email");
            System.out.println("7. Thoát");

            System.out.print("\uD83D\uDC49 Nhập lựa chọn: ");
            String input = scanner.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Lựa chọn không hợp lệ. Vui lòng nhập số.");
                continue;
            }

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> updateStudent();
                case 3 -> deleteStudent();
                case 4 -> showStudents();
                case 5 -> searchStudentById();
                case 6 -> searchStudentByEmail();
                case 7 -> {
                    System.out.println("\uD83D\uDC4B Thoát chương trình.");
                    return;
                }
                default -> System.out.println("❌ Lựa chọn không hợp lệ! Vui lòng thử lại.");
>>>>>>> origin/lamhoang
            }
        }
    }

    // ======== VALIDATION =========
    private static void validateNotEmpty(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ " + fieldName + " không được để trống.");
        }
    }

    private static void validateEmail(String email) {
        if (!email.matches("^\\S+@stu\\.ptit\\.edu\\.vn$")) {
            throw new IllegalArgumentException("❌ Email không hợp lệ (phải có đuôi @stu.ptit.edu.vn).");
        }
    }

    private static void validatePassword(String password) {
        if (password.length() < 4) {
            throw new IllegalArgumentException("❌ Mật khẩu phải có ít nhất 4 ký tự.");
        }
    }

    private static void validateName(String name) {
        if (!name.matches("^[\\p{L} ]+$")) {
            throw new IllegalArgumentException("❌ Tên không được chứa số hoặc ký tự đặc biệt.");
        }
    }

    private static void validateStudentExists(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("❌ Sinh viên không tồn tại.");
        }
    }
    // =============================

    private static void addStudent() {
        int studentId;
        String name, studentCode, email, password;

        while (true) {
            try {
                System.out.print("Nhập ID sinh viên: ");
                studentId = Integer.parseInt(scanner.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("❌ ID không hợp lệ.");
            }
        }

        while (true) {
            try {
                System.out.print("Nhập tên sinh viên: ");
                name = scanner.nextLine().trim();
                validateNotEmpty(name, "Tên sinh viên");
                validateName(name);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Nhập mã sinh viên: ");
                studentCode = scanner.nextLine().trim();
                validateNotEmpty(studentCode, "Mã sinh viên");
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Nhập email: ");
                email = scanner.nextLine().trim();
                validateEmail(email);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Nhập mật khẩu: ");
                password = scanner.nextLine().trim();
                validatePassword(password);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        Student student = new Student(studentId, name, studentCode, email, password);
        studentDAO.add(student);
        System.out.println("✅ Sinh viên đã được thêm thành công!");
    }

    private static void updateStudent() {
        int studentId;
        while (true) {
            try {
                System.out.print("Nhập ID sinh viên cần sửa: ");
                studentId = Integer.parseInt(scanner.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("❌ ID không hợp lệ.");
            }
        }

        Student student = studentDAO.selectById(new Student(studentId));
        try {
            validateStudentExists(student);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("Nhập tên mới (hiện tại: " + student.getName() + "): ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) {
            try {
                validateNotEmpty(name, "Tên sinh viên");
                validateName(name);
                student.setName(name);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.print("Nhập email mới (hiện tại: " + student.getEmail() + "): ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) {
            try {
                validateEmail(email);
                student.setEmail(email);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.print("Nhập mã sinh viên mới (hiện tại: " + student.getStudentCode() + "): ");
        String code = scanner.nextLine().trim();
        if (!code.isEmpty()) {
            try {
                validateNotEmpty(code, "Mã sinh viên");
                student.setStudentCode(code);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.print("Nhập mật khẩu mới (hiện tại: " + student.getPassword() + "): ");
        String password = scanner.nextLine().trim();
        if (!password.isEmpty()) {
            try {
                validatePassword(password);
                student.setPassword(password);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        studentDAO.update(student);
        System.out.println("✅ Sinh viên đã được cập nhật!");
    }

    private static void deleteStudent() {
        while (true) {
            try {
                System.out.print("Nhập ID sinh viên cần xóa: ");
                int id = Integer.parseInt(scanner.nextLine().trim());
                Student student = studentDAO.selectById(new Student(id));
                validateStudentExists(student);
                studentDAO.delete(student);
                System.out.println("✅ Sinh viên đã được xóa!");
                return;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void showStudents() {
        ArrayList<Student> students = studentDAO.selectAll();
        if (students.isEmpty()) {
            System.out.println("⚠️ Không có sinh viên nào.");
            return;
        }

        System.out.println("📋 Danh sách sinh viên:");
        for (Student student : students) {
            System.out.println(student);
        }
    }

<<<<<<< HEAD
=======
    private static void searchStudentById() {
        while (true) {
            try {
                System.out.print("Nhập ID sinh viên cần tìm: ");
                int id = Integer.parseInt(scanner.nextLine().trim());
                Student result = studentDAO.selectById(new Student(id));
                validateStudentExists(result);
                System.out.println("🔎 Thông tin sinh viên:");
                System.out.println(result);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void searchStudentByEmail() {
        while (true) {
            try {
                System.out.print("Nhập email của sinh viên: ");
                String email = scanner.nextLine().trim();
                Student student = new Student();
                student.setEmail(email);

                ArrayList<Student> students = studentDAO.selectByCondition(student);
                if (students.isEmpty()) {
                    throw new IllegalArgumentException("❌ Không tìm thấy sinh viên với email này!");
                }

                System.out.println("📋 Danh sách sinh viên tìm thấy:");
                for (Student st : students) {
                    System.out.println(st);
                }
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        showMenu();
    }
>>>>>>> origin/lamhoang
}
