package ui;

import dao.AdminDAO;

import java.sql.SQLException;
import java.util.Scanner;

public class SuperAdminUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AdminDAO adminDAO = new AdminDAO();
    public static void showMenu(int userId) {
        int adminId = adminDAO.selectByUserId(userId);
        while (true) {
            System.out.println("\n==== SUPERADMIN MENU ====");
            System.out.println("1. Quản lý Admin");
            System.out.println("2. Quản lý Sinh viên");
            System.out.println("3. Quản lý Giảng viên");
            System.out.println("4. Quản lý Lớp hành chính");
            System.out.println("5. Quản lý Lịch học lớp hành chính");
            System.out.println("6. Quản lý Môn học");
            System.out.println("7. Quản lý lịch thi");
            System.out.println("8. Quản lý Điểm số");
            System.out.println("9. Quản lý thông báo");
            System.out.println("0. Đăng xuất");
            System.out.print("Chọn chức năng: ");
            int choice;
            try {
                choice = Integer.parseInt( scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Lựa chọn không hợp lệ, vui lòng nhập số.");
                continue;
            }

            try {
                switch (choice) {
                    case 0:
                        System.out.println(">> Đăng xuất thành công!");
                        return;
                    case 1:
                        ManageAdminUI.showMenu();
                        break;
                    case 2:
                        ManageStudentUI.showMenu();
                        break;
                    case 3:
                        ManageTeacherUI.showMenu();
                        break;
                    case 4:
                        ManageClassGroupUI.showMenu();
                        break;
                    case 5:
                        ManageGroupScheduleUI.showMenu();
                        break;
                    case 6:
                        CourseUI.showMenu();
                        break;
                    case 7:
                        ManageExamScheduleUI.showMenu();
                        break;
                    case 8:
                        ManageGradeUI.showMenu();
                        break;
                    case 9:
                        NotificationUI.showMenu(adminId);
                        break;
                    default:
                        System.out.println("❌ Lựa chọn không hợp lệ!");
                        break;
                }
            }catch (Exception ex) {
                System.out.println("❌ Đã có lỗi: " + ex.getMessage());
            }
        }
    }
}
