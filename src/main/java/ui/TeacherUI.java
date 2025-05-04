package ui;

import java.util.Scanner;


public class TeacherUI {
    private static Scanner scanner = new Scanner(System.in);
//    private static StudentDAO studentDAO = new StudentDAO();

    public static void manageTeachers() {
        while (true) {
            System.out.println("1. Quản lý khóa học (Thêm, sửa, xóa, xem danh sách)");
            System.out.println("2. Quản lý lớp học (Thêm, sửa, xóa, xem danh sách)");
            System.out.println("3. Nhập điểm và đánh giá sinh viên (Thêm, sửa, xóa, xem điểm)");
            System.out.println("4. Quản lý lịch học (Thêm, sửa, xóa, xem danh sách)");
            System.out.println("5. Gửi thông báo cho sinh viên (Thêm, sửa, xóa, xem danh sách)");
            System.out.println("6. Đăng xuất");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    break;
                case 2:
                    ClassUI.quanLyLopHoc();
                    break;
                case 3:
                    return;
                case 4:
                    return;
                case 5:
                    return;
                case 6:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}
