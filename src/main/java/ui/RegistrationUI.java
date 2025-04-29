package ui;

import model.Registration;
import service.RegistrationService;

import java.util.List;
import java.util.Scanner;

public class RegistrationUI {
    private static Scanner scanner = new Scanner(System.in);
    private static RegistrationService registrationService = new RegistrationService();
    private static int currentStudentId;

    public static void showRegistrationMenu(int studentIdFromLogin) {
        currentStudentId = studentIdFromLogin;

        while (true) {
            System.out.println("\n--- Đăng ký môn học ---");
            System.out.println("1. Xem kết quả đăng ký môn học");
            System.out.println("2. Thoát");
            System.out.print("Nhập lựa chọn của bạn: ");

            String input = scanner.nextLine();
            try {
                validateMenuInput(input);
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1 -> xemKetQuaDangKy();
                    case 2 -> { return; }
                    default -> System.out.println("Lựa chọn không hợp lệ.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Lỗi: " + e.getMessage());
            }
        }
    }

    private static void xemKetQuaDangKy() {
        List<Registration> list = registrationService.getByStudentId(currentStudentId);
        if (list.isEmpty()) {
            System.out.println("Bạn chưa đăng ký môn học nào.");
        } else {
            System.out.println("Danh sách lớp bạn đã đăng ký:");
            for (Registration r : list) {
                System.out.println("- Mã lớp: " + r.getClassId());
            }
        }
    }

    private static void validateMenuInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Lựa chọn không được để trống.");
        }
        try {
            Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Lựa chọn phải là một số nguyên.");
        }
    }
}
