package ui;

import dao.GradeDAO;
import dao.StudentDAO;
import model.Grade;
import model.Student;

import java.util.ArrayList;
import java.util.Scanner;

public class GradeUI {
    private static final Scanner scanner = new Scanner(System.in);
    private final GradeDAO gradeDAO = new GradeDAO();
    private final StudentDAO studentDAO = new StudentDAO();

    public static void showMenu() {
        GradeUI gradeUI = new GradeUI();

        while (true) {
            System.out.println("\nQuản lý điểm sinh viên");
            System.out.println("1. ➕ Thêm điểm");
            System.out.println("2. 📝 Cập nhật điểm");
            System.out.println("3. ❌ Xóa điểm");
            System.out.println("4. 📋 Xem tất cả điểm");
            System.out.println("5. 🔍 Tìm điểm theo tên sinh viên");
            System.out.println("6. 🔎 Tìm điểm theo ID sinh viên");
            System.out.println("7. 🚪 Thoát");

            System.out.print("👉 Nhập lựa chọn: ");
            String input = scanner.nextLine().trim();

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Vui lòng nhập một số nguyên hợp lệ.");
                continue;
            }

            switch (choice) {
                case 1 -> gradeUI.addGrade();
                case 2 -> gradeUI.updateGrade();
                case 3 -> gradeUI.deleteGrade();
                case 4 -> gradeUI.showGrades();
                case 5 -> gradeUI.searchGradeByName();
                case 6 -> gradeUI.searchGradeByStudentId();
                case 7 -> {
                    System.out.println("👋 Thoát chương trình.");
                    return;
                }
                default -> System.out.println("❌ Lựa chọn không hợp lệ!");
            }
        }
    }

    private void validateGradeValue(float value) {
        if (value < 0 || value > 10) {
            throw new IllegalArgumentException("❌ Điểm phải nằm trong khoảng từ 0 đến 10.");
        }
    }

    private void validateId(int id, String fieldName) {
        if (id <= 0) {
            throw new IllegalArgumentException("❌ " + fieldName + " phải là số nguyên dương.");
        }
    }

    private void validateGradeExists(Grade grade) {
        if (grade == null) {
            throw new IllegalArgumentException("❌ Không tìm thấy bản ghi điểm tương ứng.");
        }
    }

    private void addGrade() {
        try {
            int gradeId = getIdInput("Nhập ID điểm: ", "ID điểm");
            int studentId = getIdInput("Nhập ID sinh viên: ", "ID sinh viên");
            Student student = studentDAO.selectById(new Student(studentId, null, null, null, null));
            if (student == null) {
                System.out.println("❌ ID sinh viên không tồn tại.");
                return;
            }

            int classId = getIdInput("Nhập ID lớp học: ", "ID lớp học");
            float gradeValue = getGradeInput("Nhập điểm (0-10): ");

            Grade grade = new Grade(gradeId, studentId, classId, gradeValue);
            gradeDAO.add(grade);
            System.out.println("✅ Đã thêm điểm thành công!");
        } catch (NumberFormatException e) {
            System.out.println("❌ Định dạng số không hợp lệ.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Đã xảy ra lỗi khi thêm điểm.");
        }
    }

    private void updateGrade() {
        try {
            int gradeId = getIdInput("Nhập mã điểm cần cập nhật: ", "Mã điểm");

            Grade grade = new Grade(gradeId, 0, 0, 0);
            grade = gradeDAO.selectById(grade);
            validateGradeExists(grade);

            float newGrade = getGradeInput("Nhập điểm mới (0-10): ");
            grade.setGrade(newGrade);
            gradeDAO.update(grade);
            System.out.println("✅ Đã cập nhật điểm thành công!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteGrade() {
        try {
            int gradeId = getIdInput("Nhập mã điểm cần xóa: ", "Mã điểm");
            Grade grade = new Grade(gradeId, 0, 0, 0);
            grade = gradeDAO.selectById(grade);
            validateGradeExists(grade);
            gradeDAO.delete(grade);
            System.out.println("✅ Đã xóa điểm thành công!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void showGrades() {
        ArrayList<Grade> grades = gradeDAO.selectAll();
        if (grades.isEmpty()) {
            System.out.println("⚠️ Không có bản ghi điểm nào.");
        } else {
            System.out.println("📋 Danh sách điểm:");
            for (Grade grade : grades) {
                System.out.println(grade);
            }
        }
    }

    private void searchGradeByName() {
        System.out.print("Nhập tên sinh viên: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("❌ Tên sinh viên không được để trống.");
            return;
        }

        try {
            Grade grade = gradeDAO.selectByName(name);
            validateGradeExists(grade);
            System.out.println("🔎 Điểm của sinh viên " + name + ": " + grade.getGrade());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void searchGradeByStudentId() {
        try {
            int studentId = getIdInput("Nhập ID sinh viên: ", "ID sinh viên");
            Student student = studentDAO.selectById(new Student(studentId, null, null, null, null));
            if (student == null) {
                System.out.println("❌ Không tìm thấy sinh viên với ID đã nhập.");
                return;
            }

            Grade grade = gradeDAO.selectById(new Grade(student.getStudentId(), 0, 0, 0));
            validateGradeExists(grade);
            System.out.println("🔎 Điểm của sinh viên ID " + studentId + ": " + grade.getGrade());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private int getIdInput(String prompt, String fieldName) {
        System.out.print(prompt);
        int id = Integer.parseInt(scanner.nextLine().trim());
        validateId(id, fieldName);
        return id;
    }

    private float getGradeInput(String prompt) {
        System.out.print(prompt);
        float value = Float.parseFloat(scanner.nextLine().trim());
        validateGradeValue(value);
        return value;
    }

    public static void main(String[] args) {
        showMenu();
    }
}
