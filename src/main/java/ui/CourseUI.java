package ui;

import dao.CourseDAO;
import model.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CourseUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CourseDAO courseDAO = new CourseDAO();

    public static void showMenu() {
        int choice;
        do {
            System.out.println("\n==================== QUẢN LÝ MÔN HỌC ====================");
            System.out.println("1. Xem danh sách môn học");
            System.out.println("2. Thêm môn học");
            System.out.println("3. Sửa môn học");
            System.out.println("4. Xóa môn học");
            System.out.println("5. Tìm môn học theo code hoặc tên");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(">> Vui lòng nhập số.");
                choice = -1;
            }

            switch (choice) {
                case 1 -> viewCourses();
                case 2 -> addCourse();
                case 3 -> updateCourse();
                case 4 -> deleteCourse();
                case 5 -> findCourse();
                case 0 -> System.out.println(">> Quay lại menu chính.");
                default -> System.out.println(">> Lựa chọn không hợp lệ.");
            }
        } while (choice != 0);
    }

    // ======== VALIDATION =========
    private static void validateNotEmpty(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ " + fieldName + " không được để trống.");
        }
    }

    private static void validateCredits(int credits) {
        if (credits < 1) {
            throw new IllegalArgumentException("❌ Số tín chỉ phải là số nguyên lớn hơn 0.");
        }
    }

    private static void validateCourseExists(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("❌ Môn học không tồn tại.");
        }
    }
    // =============================
    private static void viewCourses() {
        List<Course> list = courseDAO.selectAll();
        if (list.isEmpty()) {
            System.out.println(">> Chưa có môn học nào.");
            return;
        }
        System.out.printf("%-5s | %-10s | %-30s | %s%n", "ID", "CODE", "NAME", "CREDITS");
        System.out.println("-------------------------------------------------------------");
        for (Course c : list) {
            System.out.printf("%-5d | %-10s | %-30s | %d%n",
                    c.getCourseId(), c.getCourseCode(), c.getCourseName(), c.getCredits());
        }
    }
    private static void addCourse() {
        System.out.print("Mã môn học: ");
        String code = scanner.nextLine().trim();
        System.out.print("Tên môn học: ");
        String name = scanner.nextLine().trim();
        System.out.print("Số tín chỉ: ");
        int cr = Integer.parseInt(scanner.nextLine().trim());

        Course c = new Course(code, name, cr);
        courseDAO.add(c);
    }

    private static void updateCourse() {
        int courseId;
        while (true) {
            try {
                System.out.print("Nhập ID môn học cần sửa: ");
                courseId = Integer.parseInt(scanner.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("❌ ID không hợp lệ.");
            }
        }

        Course course = courseDAO.selectById(courseId);
        try {
            validateCourseExists(course);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("Nhập mã môn học mới (hiện tại: " + course.getCourseCode() + "): ");
        String code = scanner.nextLine().trim();
        if (!code.isEmpty()) {
            try {
                validateNotEmpty(code, "Mã môn học");
                course.setCourseCode(code);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.print("Nhập tên môn học mới (hiện tại: " + course.getCourseName() + "): ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) {
            try {
                validateNotEmpty(name, "Tên môn học");
                course.setCourseName(name);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.print("Nhập số tín chỉ mới (hiện tại: " + course.getCredits() + "): ");
        String creditsInput = scanner.nextLine().trim();
        if (!creditsInput.isEmpty()) {
            try {
                int credits = Integer.parseInt(creditsInput);
                validateCredits(credits);
                course.setCredits(credits);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        courseDAO.update(course);
        System.out.println("✅ Môn học đã được cập nhật!");
    }
    private static void deleteCourse() {
        System.out.print("Nhập course_id cần xóa: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Xác nhận xóa? (y/n): ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.println("Đã hủy.");
            return;
        }
        Course c = courseDAO.selectById(id);
        courseDAO.delete(c);
    }

    private static void findCourse() {
        System.out.print("Nhập code hoặc tên: ");
        String key = scanner.nextLine().trim();
        Course c = courseDAO.selectByName(key);
        if (c == null) System.out.println(">> Không tìm thấy.");
        else System.out.println(c);
    }
}
