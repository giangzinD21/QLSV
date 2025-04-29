package ui;

import model.Class;
import service.ClassService;

import java.util.ArrayList;
import java.util.Scanner;

public class ClassUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ClassService classService = new ClassService();

    public static void quanLyLopHoc() {
        showMenu();
    }

    public static void showMenu() {
        while (true) {
            System.out.println("\n===== QUẢN LÝ LỚP HỌC =====");
            System.out.println("1. Thêm lớp học");
            System.out.println("2. Cập nhật lớp học");
            System.out.println("3. Xóa lớp học");
            System.out.println("4. Xem danh sách lớp học");
            System.out.println("5. Thoát");

            try {
                System.out.print(" Nhập lựa chọn của bạn: ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> addClass();
                    case 2 -> updateClass();
                    case 3 -> deleteClass();
                    case 4 -> showAllClasses();
                    case 5 -> {
                        System.out.println(" Thoát quản lý lớp học.");
                        return;
                    }
                    default -> System.out.println(" Lựa chọn không hợp lệ. Vui lòng thử lại.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Vui lòng nhập số nguyên hợp lệ.");
            }
        }
    }

    // ===== VALIDATION INPUT =====

    private static String getNonEmptyInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                validateNonEmpty(input);
                return input;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static int getValidInteger(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return validateInteger(input);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void validateNonEmpty(String input) throws Exception {
        if (input == null || input.isBlank()) {
            throw new Exception("Trường này không được để trống.");
        }
    }

    private static int validateInteger(String input) throws Exception {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new Exception("Vui lòng nhập số nguyên hợp lệ.");
        }
    }

    private static void validatePositiveInteger(String input) throws Exception {
        try {
            int value = Integer.parseInt(input);
            if (value < 0) {
                throw new Exception("Giá trị phải là số nguyên không âm.");
            }
        } catch (NumberFormatException e) {
            throw new Exception("Vui lòng nhập số nguyên hợp lệ.");
        }
    }

    private static void validateClassId(String classId) throws Exception {
        if (classId.trim().isEmpty()) {
            throw new Exception("Mã lớp không được để trống.");
        }
        validatePositiveInteger(classId);
    }

    private static void validateCourseId(String courseId) throws Exception {
        if (courseId.trim().isEmpty()) {
            throw new Exception("Mã môn học không được để trống.");
        }
        validatePositiveInteger(courseId);
    }

    private static void validateTeacherId(String teacherId) throws Exception {
        if (teacherId.trim().isEmpty()) {
            throw new Exception("Mã giảng viên không được để trống.");
        }
        validatePositiveInteger(teacherId);
    }

    private static int validateMaxStudents(String input) throws Exception {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new Exception("Số sinh viên tối đa phải là số nguyên.");
        }
    }

    // ===== CHỨC NĂNG =====

    private static void addClass() {
        System.out.println("\n--- Thêm lớp học mới ---");

        String classId;
        while (true) {
            classId = getNonEmptyInput("Nhập mã lớp: ");
            try {
                validateClassId(classId);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        String courseId;
        while (true) {
            courseId = getNonEmptyInput("Nhập mã môn học: ");
            try {
                validateCourseId(courseId);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        String teacherId;
        while (true) {
            teacherId = getNonEmptyInput("Nhập mã giảng viên: ");
            try {
                validateTeacherId(teacherId);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        int maxStudents;
        while (true) {
            try {
                maxStudents = getValidInteger("Nhập số sinh viên tối đa: ");
                validateMaxStudents(String.valueOf(maxStudents));
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        classService.createClass(classId, courseId, teacherId, maxStudents);
        System.out.println(" Thêm lớp học thành công.");
    }

    private static void updateClass() {
        System.out.println("\n--- Cập nhật lớp học ---");

        String classId;
        while (true) {
            classId = getNonEmptyInput("Nhập mã lớp cần cập nhật: ");
            try {
                validateClassId(classId);
                Class existing = classService.getClassById(classId);
                if (existing == null) {
                    System.out.println(" Không tìm thấy lớp với mã: " + classId);
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        String courseId;
        while (true) {
            courseId = getNonEmptyInput("Nhập mã môn học mới: ");
            try {
                validateCourseId(courseId);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        String teacherId;
        while (true) {
            teacherId = getNonEmptyInput("Nhập mã giảng viên mới: ");
            try {
                validateTeacherId(teacherId);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        int maxStudents;
        while (true) {
            try {
                maxStudents = getValidInteger("Nhập số sinh viên tối đa mới: ");
                validateMaxStudents(String.valueOf(maxStudents));
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        classService.updateClass(classId, courseId, teacherId, maxStudents);
        System.out.println(" Cập nhật lớp học thành công.");
    }

    private static void deleteClass() {
        System.out.println("\n--- Xóa lớp học ---");

        String classId;
        while (true) {
            classId = getNonEmptyInput("Nhập mã lớp cần xóa: ");
            try {
                validateClassId(classId);
                Class existing = classService.getClassById(classId);
                if (existing == null) {
                    System.out.println(" Không tìm thấy lớp với mã: " + classId);
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        classService.removeClass(classId);
        System.out.println(" Xóa lớp học thành công.");
    }

    private static void showAllClasses() {
        System.out.println("\n--- Danh sách lớp học ---");
        ArrayList<Class> classList = classService.getAllClasses();
        if (classList.isEmpty()) {
            System.out.println(" Không có lớp học nào.");
        } else {
            for (Class cls : classList) {
                System.out.println("Mã lớp: " + cls.getClassId() +
                        " | Mã môn học: " + cls.getCourseId() +
                        " | Mã giảng viên: " + cls.getTeacherId() +
                        " | SV tối đa: " + cls.getMaxStudents());
            }
        }
    }
}
