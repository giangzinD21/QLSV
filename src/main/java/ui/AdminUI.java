package ui;

import dao.AdminDAO;

import java.util.Scanner;

public class AdminUI {
    private static Scanner scanner = new Scanner(System.in);
    private static AdminDAO adminDAO = new AdminDAO();
    public static void showMenu(int userId) {
        int adminId = adminDAO.selectByUserId(userId);
        while (true) {
            System.out.println("\n==== ADMIN MENU ====");
            System.out.println("1. Quản lý Sinh viên");
            System.out.println("2. Quản lý Giảng viên");
            System.out.println("3. Quản lý Lớp hành chính");
            System.out.println("4. Quản lý Lịch lớp hành chính");
            System.out.println("5. Quản lý Môn học");
            System.out.println("6. Quản lý lịch thi");
            System.out.println("7. Quản lý Điểm số");
            System.out.println("8. Quản lý thông báo");
            System.out.println("0. Đăng xuất");
            System.out.print("Chọn chức năng: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Lựa chọn không hợp lệ.");
                continue;
            }
            try {
                switch (choice) {
                    case 0:
                        System.out.println(">> Đăng xuất thành công!");
                        return;
                    case 1:
                        ManageStudentUI.showMenu();
                        break;
                    case 2:
                        ManageTeacherUI.showMenu();
                        break;
                    case 3:
                        ManageClassGroupUI.showMenu();
                        break;
                    case 4:
                        ManageGroupScheduleUI.showMenu();
                        return;
                    case 5:
                        CourseUI.showMenu();
                        return;
                    case 6:
                        ManageExamScheduleUI.showMenu();
                        return;
                    case 7:
                        ManageGradeUI.showMenu();
                        return;
                    case 8:
                        NotificationUI.showMenu(adminId);
                        break;
                    default:
                        System.out.println("❌ Lựa chọn không hợp lệ!");
                }
            }catch (Exception ex) {
            System.out.println("❌ Đã có lỗi: " + ex.getMessage());
            }
        }
    }
}
