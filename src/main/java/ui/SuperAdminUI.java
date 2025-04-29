package ui;

import java.util.Scanner;

import static ui.ManageAdminUI.manageAdmin;
import ui.ClassUI;
import ui.NotificationUI;

public class SuperAdminUI {
    private static Scanner scanner = new Scanner(System.in);
//    private static StudentDAO studentDAO = new StudentDAO();

    public static void indexSuperAdmins() throws Exception {
        while (true) {
            System.out.println("1. Quản lý thông tin Admin (Thêm, sửa, xóa, xem danh sách");
            System.out.println("2. Quản lý thông tin sinh viên (Thêm, sửa, xóa, xem danh sách)");
            System.out.println("3. Quản lý giảng viên (Thêm, sửa, xóa, xem danh sách)");
            System.out.println("4. Quản lý lớp học (Thêm, sửa, xóa, xem danh sách)");
            System.out.println("5. Quản lý lịch học (Thêm, sửa, xóa, xem danh sách)");
            System.out.println("6. Quản lý khóa học (Thêm, sửa, xóa, xem danh sách)");
            System.out.println("7. Gửi thông báo cho sinh viên (Thêm, sửa, xóa, xem danh sách)");
            System.out.println("8. Đăng xuất");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Lựa chọn không hợp lệ.");
                continue;
            }
            switch (choice) {
                case 1:
                    manageAdmin();
                    break;
                case 2:

                    break;
                case 3:
                    break;
                case 4:
<<<<<<< HEAD

=======
                    ClassUI.quanLyLopHoc(); // ✅ Gọi giao diện quản lý lớp học
>>>>>>> origin/huyle
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
<<<<<<< HEAD

=======
                    NotificationUI.run(1, "admin"); // Truyền ID thật và vai trò thật của người gửi
>>>>>>> origin/huyle
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}
