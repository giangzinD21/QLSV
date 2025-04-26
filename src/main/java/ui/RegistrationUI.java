package ui;

import service.RegistrationService;

import java.util.Scanner;

public class RegistrationUI{
    private final RegistrationService registrationService = new RegistrationService();
    private final Scanner scanner = new Scanner(System.in);

    public void showMenu(int studentId) {
        while (true) {
            System.out.println("\n---- MENU ĐĂNG KÝ HỌC ----");
            System.out.println("1. Xem thời khóa biểu");
            System.out.println("2. Đăng ký lớp học");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> handleViewSchedule(studentId);
                case "2" -> handleRegister(studentId);
                case "0" -> {
                    System.out.println("Thoát.");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

    private void handleViewSchedule(int studentId) {
        System.out.print("Nhập học kỳ (VD: 2024-2025-HK2): ");
        String semester = scanner.nextLine();
        registrationService.viewSchedule(studentId, semester);
    }

    private void handleRegister(int studentId) {
        System.out.print("Nhập mã lớp cần đăng ký (class_id): ");
        int classId = Integer.parseInt(scanner.nextLine());
        registrationService.registerClass(studentId, classId);
    }
}
