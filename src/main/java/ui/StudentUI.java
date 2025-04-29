package ui;

import dao.StudentDAO;
import model.Student;
import java.util.List;
import java.util.Scanner;
import ui.RegistrationUI;

public class StudentUI {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentDAO studentDAO = new StudentDAO();

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
            }
        }
    }

    private static void addStudent() {
        System.out.print("Nhập tên: ");
        String name = scanner.nextLine();
        System.out.println("Nhập mã sinh viên");
        String student_code = scanner.nextLine().trim();
        System.out.print("Nhập email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Nhập mật khẩu: ");
        String password = scanner.nextLine().trim();

        Student student = new Student(name,student_code, email, password);
        studentDAO.add(student);
        System.out.println("Đã thêm sinh viên!");
    }

    private static void showStudents() {
        List<Student> students = studentDAO.selectAll();
        for (Student s : students) {
            System.out.println(s);
        }
    }

}
