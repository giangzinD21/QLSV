package ui;

import dao.ClassGroupDAO;
import model.ClassGroup;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class ManageClassGroupUI {
    private static final Scanner sc = new Scanner(System.in);
    private static final ClassGroupDAO classGroupDAO = new ClassGroupDAO();

    public static void showMenu() {
        do {
            System.out.println("\n==================== QUẢN LÝ LỚP HÀNH CHÍNH ====================");
            System.out.println("1. Xem danh sách lớp");
            System.out.println("2. Thêm lớp");
            System.out.println("3. Sửa lớp");
            System.out.println("4. Xóa lớp");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(">> Vui lòng nhập số.");
                continue;
            }
            switch (choice) {
                case 1:
                    viewAll();
                    break;
                case 2:
                    addClassGroup();
                    break;
                case 3:
                    updateClassGroup();
                    break;
                case 4:
                    deleteClassGroup();
                    break;
                case 0:
                    System.out.println(">> Kết thúc quản lý lớp hành chính.");
                    return;
                default:
                    System.out.println(">> Lựa chọn không hợp lệ.");
            }
        } while (true);
    }

    public static void viewAll() {
        List<ClassGroup> list = classGroupDAO.selectAll();
        if (list.isEmpty()) {
            System.out.println(">> Hiện chưa có lớp hành chính nào.");
            return;
        }
        System.out.printf("%-5s | %-15s | %-25s | %s%n", "ID", "MÃ LỚP", "TÊN LỚP", "CREATED_AT");
        System.out.println("-------------------------------------------------------------------");
        for (ClassGroup g : list) {
            String created = g.getCreatedAt() != null ? g.getCreatedAt().toString() : "";
            System.out.printf("%-5d | %-15s | %-25s | %s%n",
                    g.getGroupId(), g.getGroupCode(), g.getGroupName(), created);
        }
        System.out.println("-------------------------------------------------------------------");
        System.out.println(">> Tổng số: " + list.size() + " lớp.");
    }

    private static void addClassGroup() {
        try {
            System.out.println("\n==================== THÊM LỚP HÀNH CHÍNH MỚI ====================");
            System.out.print("Nhập mã lớp: ");
            String code = sc.nextLine().trim();

            System.out.print("Nhập tên lớp: ");
            String name = sc.nextLine().trim();

            ClassGroup g = new ClassGroup();
            g.setGroupCode(code);
            g.setGroupName(name);
            g.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            ClassGroup existing = classGroupDAO.selectByName(code);
            if( existing != null )  classGroupDAO.add(g);
            else System.out.println("Mã lớp đã tồn tại");
        } catch (Exception e) {
            System.out.println(">> Lỗi khi thêm lớp: " + e.getMessage());
        }
    }

    private static void updateClassGroup() {
        try {
            System.out.println("\n==================== CẬP NHẬT LỚP HÀNH CHÍNH ====================");
            System.out.print("Nhập ID lớp cần sửa: ");
            int id;
            try{
                id = Integer.parseInt(sc.nextLine().trim());
            }
            catch (NumberFormatException e) {
                System.out.println(">> Lỗi: ID phải là số nguyên. Hãy thử lại.");
                return;
            }

            ClassGroup g = classGroupDAO.selectById(id);
            if (g == null) {
                System.out.println(">> Không tìm thấy lớp với ID " + id);
                return;
            }

            System.out.print("Mã lớp mới (ENTER để giữ \"" + g.getGroupCode() + "\"): ");
            String code = sc.nextLine().trim();
            if (!code.isEmpty()) g.setGroupCode(code);

            System.out.print("Tên lớp mới (ENTER để giữ \"" + g.getGroupName() + "\"): ");
            String name = sc.nextLine().trim();
            if (!name.isEmpty()) g.setGroupName(name);

            classGroupDAO.update(g);
        } catch (NumberFormatException e) {
            System.out.println(">> ID không hợp lệ.");
        } catch (Exception e) {
            System.out.println(">> Lỗi khi cập nhật: " + e.getMessage());
        }
    }

    private static void deleteClassGroup() {
        try {
            System.out.println("\n==================== XÓA LỚP HÀNH CHÍNH ====================");
            System.out.print("Nhập ID lớp cần xóa: ");
            int id = Integer.parseInt(sc.nextLine().trim());

            ClassGroup g = classGroupDAO.selectById(id);
            if (g == null) {
                System.out.println(">> Không tìm thấy lớp với ID " + id);
                return;
            }

            System.out.print(">> Xác nhận xóa? (y/n): ");
            if (!sc.nextLine().trim().equalsIgnoreCase("y")) {
                System.out.println(">> Hủy xóa.");
                return;
            }

            classGroupDAO.delete(g);
            System.out.println(">> Xóa lớp thành công.");
        } catch (NumberFormatException e) {
            System.out.println(">> ID không hợp lệ.");
        } catch (Exception e) {
            System.out.println(">> Lỗi khi xóa lớp: " + e.getMessage());
        }
    }
}
