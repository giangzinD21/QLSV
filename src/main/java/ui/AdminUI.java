package ui;

<<<<<<< HEAD
=======
import dao.StudentDAO;
>>>>>>> origin/huyle
import java.util.Scanner;
import ui.ClassUI;

public class AdminUI {
    private static Scanner scanner = new Scanner(System.in);

    public static void indexAdmins() {
        while (true) {
            System.out.println("1. Quản lý thông tin sinh viên1 (Thêm, sửa, xóa, xem danh sách)");
            System.out.println("2. Quản lý giảng viên (Thêm, sửa, xóa, xem danh sách)");
            System.out.println("3. Quản lý lớp học (Thêm, sửa, xóa, xem danh sách)");
            System.out.println("4. Quản lý lịch học (Thêm, sửa, xóa, xem danh sách)");
            System.out.println("5. Quản lý môn học (Thêm, sửa, xóa, xem danh sách)");
            System.out.println("6. Gửi thông báo cho sinh viên (Thêm, sửa, xóa, xem danh sách)");
            System.out.println("7. Đăng xuất");
            System.out.print("Nhập lựa chọn của bạn: ");
<<<<<<< HEAD
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Lựa chọn không hợp lệ.");
                continue;
            }
=======
            int choice = scanner.nextInt();
            scanner.nextLine();

>>>>>>> origin/huyle
            switch (choice) {
                case 1:
                    // TODO: Quản lý sinh viên
                    break;
                case 2:
                    // TODO: Quản lý giảng viên
                    break;
                case 3:
                    break;
                case 4:
                    return;
                case 5:
                    return;
                case 6:
                    return;
                case 7:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}
