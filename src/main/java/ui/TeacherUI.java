package ui;

import dao.TeacherDAO;
import model.Teacher;

import java.util.ArrayList;
import java.util.Scanner;

public class TeacherUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TeacherDAO teacherDAO = new TeacherDAO();

    public static void showMenu() {
        while (true) {
            System.out.println("\nChọn chức năng:");
            System.out.println("1. Thêm giáo viên");
            System.out.println("2. Sửa giáo viên");
            System.out.println("3. Xóa giáo viên");
            System.out.println("4. Xem tất cả giáo viên");
            System.out.println("5. Tìm giáo viên theo ID");
            System.out.println("6. Tìm giáo viên theo email");
            System.out.println("7. Thoát");

            System.out.print("👉 Nhập lựa chọn: ");
            String input = scanner.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Lựa chọn không hợp lệ. Vui lòng nhập số.");
                continue;
            }

            switch (choice) {
                case 1 -> addTeacher();
                case 2 -> updateTeacher();
                case 3 -> deleteTeacher();
                case 4 -> showTeachers();
                case 5 -> searchTeacherById();
                case 6 -> searchTeacherByEmail();
                case 7 -> {
                    System.out.println("👋 Thoát chương trình.");
                    return;
                }
                default -> System.out.println("❌ Lựa chọn không hợp lệ! Vui lòng thử lại.");
            }
        }
    }

    private static void validateNotEmpty(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ " + fieldName + " không được để trống.");
        }
    }

    private static void validateEmail(String email) {
        if (!email.matches("^\\S+@ptit\\.edu\\.vn$")) {
            throw new IllegalArgumentException("❌ Email không hợp lệ (phải có đuôi @ptit.edu.vn).");
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

    private static void validateTeacherExists(Teacher teacher) {
        if (teacher == null) {
            throw new IllegalArgumentException("❌ Giáo viên không tồn tại.");
        }
    }

    private static void addTeacher() {
        int teacherId;
        String name, teacherCode, email, password;

        while (true) {
            try {
                System.out.print("Nhập ID giáo viên: ");
                teacherId = Integer.parseInt(scanner.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("❌ ID không hợp lệ.");
            }
        }

        while (true) {
            try {
                System.out.print("Nhập tên giáo viên: ");
                name = scanner.nextLine().trim();
                validateNotEmpty(name, "Tên giáo viên");
                validateName(name);
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

        Teacher teacher = new Teacher(teacherId, name, email, password);
        teacherDAO.add(teacher);
        System.out.println("✅ Giáo viên đã được thêm thành công!");
    }

    private static void updateTeacher() {
        int teacherId;
        while (true) {
            try {
                System.out.print("Nhập ID giáo viên cần sửa: ");
                teacherId = Integer.parseInt(scanner.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("❌ ID không hợp lệ.");
            }
        }

        Teacher teacher = teacherDAO.selectById(new Teacher(teacherId));
        try {
            validateTeacherExists(teacher);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("Nhập tên mới (hiện tại: " + teacher.getName() + "): ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) {
            try {
                validateName(name);
                teacher.setName(name);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.print("Nhập email mới (hiện tại: " + teacher.getEmail() + "): ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) {
            try {
                validateEmail(email);
                teacher.setEmail(email);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.print("Nhập mật khẩu mới (hiện tại: " + teacher.getPassword() + "): ");
        String password = scanner.nextLine().trim();
        if (!password.isEmpty()) {
            try {
                validatePassword(password);
                teacher.setPassword(password);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        teacherDAO.update(teacher);
        System.out.println("✅ Giáo viên đã được cập nhật!");
    }

    private static void deleteTeacher() {
        while (true) {
            try {
                System.out.print("Nhập ID giáo viên cần xóa: ");
                int id = Integer.parseInt(scanner.nextLine().trim());
                Teacher teacher = teacherDAO.selectById(new Teacher(id));
                validateTeacherExists(teacher);
                teacherDAO.delete(teacher);
                System.out.println("✅ Giáo viên đã được xóa!");
                return;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void showTeachers() {
        ArrayList<Teacher> teachers = teacherDAO.selectAll();
        if (teachers.isEmpty()) {
            System.out.println("⚠️ Không có giáo viên nào.");
            return;
        }

        System.out.println("📋 Danh sách giáo viên:");
        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
    }

    private static void searchTeacherById() {
        while (true) {
            try {
                System.out.print("Nhập ID giáo viên cần tìm: ");
                int id = Integer.parseInt(scanner.nextLine().trim());
                Teacher result = teacherDAO.selectById(new Teacher(id));
                validateTeacherExists(result);
                System.out.println("🔎 Thông tin giáo viên:");
                System.out.println(result);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void searchTeacherByEmail() {
        while (true) {
            try {
                System.out.print("Nhập email của giáo viên: ");
                String email = scanner.nextLine().trim();
                Teacher teacher = new Teacher();
                teacher.setEmail(email);

                ArrayList<Teacher> teachers = teacherDAO.selectByCondition(teacher);
                if (teachers.isEmpty()) {
                    throw new IllegalArgumentException("❌ Không tìm thấy giáo viên với email này!");
                }

                System.out.println("📋 Danh sách giáo viên tìm thấy:");
                for (Teacher t : teachers) {
                    System.out.println(t);
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
}
