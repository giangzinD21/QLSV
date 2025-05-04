package ui;

import dao.AdminDAO;
import model.Admin;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class ManageAdminUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AdminDAO adminDAO = new AdminDAO();

    public static void showMenu() {
        do {
            System.out.println("\n==================== QUẢN LÝ ADMIN ====================");
            System.out.println("1. Xem danh sách Admin");
            System.out.println("2. Thêm Admin");
            System.out.println("3. Sửa Admin");
            System.out.println("4. Xóa Admin");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(">> Vui lòng nhập số.");
                choice = -1;
            }

            switch (choice) {
                case 1:
                    viewAdmins();
                    break;
                case 2:
                    addAdmin();
                    break;
                case 3:
                    updateAdmin();
                    break;
                case 4:
                    deleteAdmin();
                    break;
                case 0:
                    System.out.println(">> Quay lại menu trước.");
                    return;
                default:
                    System.out.println(">> Lựa chọn không hợp lệ.");
            }
        } while (true);
    }

    private static void viewAdmins() {
        try {
            List<Admin> admins = adminDAO.selectAll();
            if (admins.isEmpty()) {
                System.out.println(">> Chưa có Admin nào trong hệ thống.");
                return;
            }
            System.out.println("\n==================== DANH SÁCH ADMIN ====================");
            System.out.printf("%-5s | %-15s | %-20s | %-5s | %-25s | %s%n", "ID", "USERNAME", "HỌ TÊN", "SUPER", "EMAIL", "CREATED_AT");
            System.out.println("---------------------------------------------------------------------------------------------");
            for (Admin a : admins) {
                String created = a.getCreatedAt() != null ? a.getCreatedAt().toString() : "";
                System.out.printf("%-5d | %-15s | %-20s | %-5s | %-25s | %s%n",
                        a.getAdminId(), a.getUsername(), a.getName(), a.isSuperAdmin(), a.getEmail(), created);
            }
            System.out.println("---------------------------------------------------------------------------------------------");
            System.out.println(">> Tổng số Admin: " + admins.size());
        } catch (Exception e) {
            System.out.println(">> Lỗi khi lấy danh sách Admin: " + e.getMessage());
        }
    }

    private static void addAdmin() {
        try {
            System.out.println("\n==================== THÊM ADMIN MỚI ====================");
            System.out.print("Username: ");
            String username = scanner.nextLine().trim();
            if (username.isEmpty()) return;
            System.out.print("Mật khẩu: ");
            String password = scanner.nextLine().trim();
            if (password.isEmpty()) return;
            System.out.print("Họ và tên: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) return;
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty()) return;
            System.out.print("Là Super Admin? (true/false): ");
            String sup = scanner.nextLine().trim().toLowerCase();
            if (sup.isEmpty()) return;
            boolean isSuper;
            if (sup.equals("y") || sup.equals("true")) {
                isSuper = true;
            } else if (sup.equals("n") || sup.equals("false")) {
                isSuper = false;
            } else {
                System.out.println(">> Lỗi: Vui lòng nhập y/n hoặc true/false.");
                return;
            }
            System.out.println();
            Admin admin = new Admin();
            admin.setUsername(username);
            admin.setPassword(password);
            admin.setName(name);
            admin.setEmail(email);
            admin.setSuperAdmin(isSuper);
            admin.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            adminDAO.add(admin);

        } catch (Exception e) {
            System.out.println(">> Lỗi khi thêm Admin: " + e.getMessage());
        }
    }

    private static void updateAdmin() {
        try {
            System.out.println("\n==================== CẬP NHẬT ADMIN ====================");
            System.out.print("Nhập admin_id cần sửa: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            Admin existing = adminDAO.selectById(id);
            if (existing == null) {
                System.out.println(">> Không tìm thấy Admin.");
                return;
            }

            System.out.print("Username mới (ENTER để giữ): ");
            String username = scanner.nextLine().trim();
            if (!username.isEmpty()) existing.setUsername(username);

            System.out.print("Mật khẩu mới (ENTER để giữ): ");
            String password = scanner.nextLine().trim();
            if (!password.isEmpty()) existing.setPassword(password);

            System.out.print("Họ và tên mới (ENTER để giữ): ");
            String name = scanner.nextLine().trim();
            if (!name.isEmpty()) existing.setName(name);

            System.out.print("Email mới (ENTER để giữ): ");
            String email = scanner.nextLine().trim();
            if (!email.isEmpty()) existing.setEmail(email);

            System.out.print("Super Admin? (true/false, ENTER để giữ): ");
            String sup = scanner.nextLine().trim();
            if (!sup.isEmpty()){
                if (sup.equals("y") || sup.equals("true")) {
                    existing.setSuperAdmin(true);
                } else if (sup.equals("n") || sup.equals("false")) {
                    existing.setSuperAdmin(false);
                } else {
                    System.out.println(">> Lỗi: Vui lòng nhập y/n hoặc true/false.");
                    return;
                }
            }

            adminDAO.update(existing);
            System.out.println(">> Cập nhật Admin thành công!");
        } catch (Exception e) {
            System.out.println(">> Lỗi khi cập nhật Admin: " + e.getMessage());
        }
    }

    private static void deleteAdmin() {
        try {
            System.out.println("\n==================== XÓA ADMIN ====================");
            System.out.print("Nhập admin_id cần xóa: ");
            int id;
            try{
                id = Integer.parseInt(scanner.nextLine().trim());
            }
            catch (NumberFormatException e){
                System.out.println(">> Lỗi: ID phải là số nguyên. Hãy thử lại.");
                return;
            }
            System.out.print(">> Bạn có chắc chắn muốn xóa? (y/n): ");
            String confirm = scanner.nextLine().trim();
            if (!confirm.equalsIgnoreCase("y")) {
                System.out.println(">> Đã hủy.");
                return;
            }
            Admin existing = adminDAO.selectById(id);
            if (existing == null) {
                System.out.println(">> Không tìm thấy Admin với ID=" + id);
                return;
            }
            System.out.println(existing);
            adminDAO.delete(existing);
        } catch (Exception e) {
            System.out.println(">> Lỗi khi xóa Admin: " + e.getMessage());
        }
    }
}
