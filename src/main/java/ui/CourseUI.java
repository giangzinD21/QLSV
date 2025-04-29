package ui;

import dao.CourseDAO;
import model.Course;

import java.util.ArrayList;
import java.util.Scanner;

public class CourseUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CourseDAO courseDAO = new CourseDAO();

    public static void showMenu() {
        while (true) {
            System.out.println("\nChọn chức năng:");
            System.out.println("1. Thêm môn học");
            System.out.println("2. Sửa môn học");
            System.out.println("3. Xóa môn học");
            System.out.println("4. Xem tất cả môn học");
            System.out.println("5. Tìm môn học theo ID");
            System.out.println("6. Tìm môn học theo tên hoặc code");
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
                case 1 -> addCourse();
                case 2 -> updateCourse();
                case 3 -> deleteCourse();
                case 4 -> showCourses();
                case 5 -> searchCourseById();
                case 6 -> selectByCondition();
                case 7 -> {
                    System.out.println("👋 Thoát chương trình.");
                    return;
                }
                default -> System.out.println("❌ Lựa chọn không hợp lệ! Vui lòng thử lại.");
            }
        }
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

    private static void addCourse() {
        int courseId;
        String courseCode;
        String courseName;
        int credits;

        while (true) {
            try {
                System.out.print("Nhập ID môn học: ");
                courseId = Integer.parseInt(scanner.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("❌ ID không hợp lệ.");
            }
        }

        while (true) {
            try {
                System.out.print("Nhập mã môn học: ");
                courseCode = scanner.nextLine().trim();
                validateNotEmpty(courseCode, "Mã môn học");
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Nhập tên môn học: ");
                courseName = scanner.nextLine().trim();
                validateNotEmpty(courseName, "Tên môn học");
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Nhập số tín chỉ: ");
                credits = Integer.parseInt(scanner.nextLine().trim());
                validateCredits(credits);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        Course course = new Course(courseId, courseCode, courseName, credits);
        courseDAO.add(course);
        System.out.println("✅ Môn học đã được thêm thành công!");
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

        Course course = courseDAO.findById(courseId);
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
        Course course = null;
        while (true) {
            try {
                System.out.print("Nhập ID hoặc tên môn học cần xóa: ");
                String input = scanner.nextLine().trim();
                validateNotEmpty(input, "Thông tin xóa");

                if (input.matches("\\d+")) {
                    int courseId = Integer.parseInt(input);
                    course = courseDAO.findById(courseId);
                } else {
                    Course temp = new Course();
                    temp.setCourseName(input);
                    ArrayList<Course> courses = courseDAO.selectByCondition(temp);
                    if (!courses.isEmpty()) {
                        System.out.println("📋 Danh sách môn học tìm thấy:");
                        for (Course c : courses) {
                            System.out.println(c);
                        }
                        System.out.print("Nhập ID môn học muốn xóa: ");
                        int id = Integer.parseInt(scanner.nextLine().trim());
                        course = courseDAO.findById(id);
                    }
                }

                validateCourseExists(course);
                courseDAO.delete(course);
                System.out.println("✅ Môn học đã được xóa!");
                return;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void showCourses() {
        ArrayList<Course> courses = courseDAO.selectAll();
        if (courses.isEmpty()) {
            System.out.println("⚠️ Không có môn học nào.");
            return;
        }

        System.out.println("📋 Danh sách môn học:");
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    private static void searchCourseById() {
        while (true) {
            try {
                System.out.print("Nhập ID môn học cần tìm: ");
                int id = Integer.parseInt(scanner.nextLine().trim());

                Course course = new Course();
                course.setCourseId(id);
                Course result = courseDAO.selectById(course);
                validateCourseExists(result);

                System.out.println("🔎 Thông tin môn học:");
                System.out.println(result);
                break;

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void selectByCondition() {
        while (true) {
            try {
                System.out.print("Nhập tên hoặc mã môn học cần tìm: ");
                String keyword = scanner.nextLine().trim();
                validateNotEmpty(keyword, "Từ khóa tìm kiếm");

                Course course = new Course();
                course.setCourseName(keyword);
                course.setCourseCode(keyword);

                ArrayList<Course> courses = courseDAO.selectByCondition(course);
                if (courses.isEmpty()) {
                    throw new IllegalArgumentException("❌ Không tìm thấy môn học nào!");
                }

                System.out.println("📋 Danh sách môn học tìm thấy:");
                for (Course c : courses) {
                    System.out.println(c);
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
