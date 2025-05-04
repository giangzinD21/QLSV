package ui;

import dao.NotificationDAO;
import model.Notification;

import java.util.List;
import java.util.Scanner;

public class NotificationUI {
    private static final Scanner sc = new Scanner(System.in);
    private static final NotificationDAO dao = new NotificationDAO();

    public static void showMenu(int adminId) {
        do {
            System.out.println("\n==== QUẢN LÝ THÔNG BÁO ====");
            System.out.println("1. Xem danh sách thông báo đã gửi");
            System.out.println("2. Gửi thông báo mới");
            System.out.println("3. Sửa thông báo");
            System.out.println("4. Xóa thông báo");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("❌ Lựa chọn không hợp lệ!");
                continue;
            }

            switch (choice) {
                case 1:
                    viewAll(adminId);
                    break;
                case 2:
                    sendNotification(adminId);
                    break;
                case 3:
                    updateNotification(adminId);
                    break;
                case 4:
                    deleteNotification(adminId);
                    break;
                case 0:
                    System.out.println(">> Quay lại menu trước.");
                    return;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ!");
            }
        } while (true);
    }
    private static void viewAll(int adminId) {
        List<Notification> list = dao.selectAllBySender(adminId);
        if (list.isEmpty()) {
            System.out.println(">> Chưa có thông báo nào.");
            return;
        }
        System.out.printf("%-5s | %-8s | %-10s | %-25 | %s%n",
                "ID", "TARGET", "TARGET_ID", "CREATED_AT", "MESSAGE");
        System.out.println("---------------------------------------------------------------------");
        for (Notification n : list) {
            String created = n.getCreatedAt() != null ? n.getCreatedAt().toString() : "";
            String tid = n.getTargetId() != null ? n.getTargetId().toString() : "";
            System.out.printf("%-5d | %-8s | %-10s | %-25s | %s%n",
                    n.getNotificationId(),
                    n.getTargetType(),
                    tid,
                    created,
                    n.getMessage());
        }
    }

    private static void sendNotification(int adminId) {
        System.out.println("\n--- GỬI THÔNG BÁO MỚI ---");
        System.out.print("Gửi cho (all/teacher/student): ");
        String targetType = sc.nextLine().trim().toLowerCase();

        Integer targetId = null;
        if ("teacher".equals(targetType) || "student".equals(targetType)) {
            System.out.printf("Nhập ID %s: ", targetType);
            try {
                targetId = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ ID không hợp lệ. Hủy gửi.");
                return;
            }
        } else if (!"all".equals(targetType)) {
            System.out.println("❌ Loại nhận không hợp lệ. Hủy gửi.");
            return;
        }

        System.out.print("Nội dung: ");
        String message = sc.nextLine().trim();
        if (message.isEmpty()) {
            System.out.println("❌ Nội dung rỗng. Hủy gửi.");
            return;
        }

        Notification n = new Notification(adminId, targetType, targetId, message);
        dao.add(n);
    }

    private static void updateNotification(int adminId) {
        System.out.println("\n--- SỬA THÔNG BÁO ---");
        System.out.print("Nhập Notification ID: ");
        int nid;
        try {
            nid = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ ID không hợp lệ.");
            return;
        }
        Notification n = dao.selectById(nid);
        if (n == null || n.getSenderId() != adminId) {
            System.out.println("❌ Không tìm thấy hoặc bạn không có quyền sửa.");
            return;
        }
        System.out.println("Thông báo hiện tại: " + n.getMessage());
        System.out.print("Nhập nội dung mới (ENTER để giữ): ");
        String msg = sc.nextLine().trim();
        if (!msg.isEmpty()) {
            n.setMessage(msg);
            dao.update(n);
        } else {
            System.out.println(">> Đã hủy sửa.");
        }
    }
    private static void deleteNotification(int adminId) {
        System.out.println("\n--- XÓA THÔNG BÁO ---");
        System.out.print("Nhập Notification ID: ");
        int nid;
        try {
            nid = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ ID không hợp lệ.");
            return;
        }
        Notification n = dao.selectById(nid);
        if (n == null || n.getSenderId() != adminId) {
            System.out.println("❌ Không tìm thấy hoặc bạn không có quyền xóa.");
            return;
        }
        System.out.print("Xác nhận xóa? (y/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            dao.delete(nid);
        } else {
            System.out.println(">> Đã hủy xóa.");
        }
    }
}
