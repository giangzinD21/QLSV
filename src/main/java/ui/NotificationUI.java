package ui;

import model.Notification;
import dao.NotificationDAO;

import java.util.ArrayList;
import java.util.Scanner;

public class NotificationUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final NotificationDAO dao = new NotificationDAO();

    public static void run(int currentUserId, String currentRole) {
        while (true) {
            System.out.println("\n--- Quản lý Thông báo ---");
            System.out.println("1. Thêm thông báo");
            System.out.println("2. Cập nhật thông báo");
            System.out.println("3. Xóa thông báo");
            System.out.println("4. Xem tất cả thông báo");
            System.out.println("5. Thoát");
            System.out.print("Chọn: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> addNotification(currentUserId, currentRole);
                    case 2 -> updateNotification();
                    case 3 -> deleteNotification();
                    case 4 -> showAllNotifications();
                    case 5 -> { return; }
                    default -> System.out.println("Lựa chọn không hợp lệ!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số.");
            }
        }
    }

    private static void addNotification(int senderId, String senderRole) {
        while (true) {
            try {
                System.out.print("Nhập nội dung thông báo: ");
                String msg = scanner.nextLine().trim();
                validateMessage(msg);

                Notification noti = new Notification(senderId, senderRole, msg);
                dao.add(noti);
                System.out.println("Đã gửi thông báo.");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void updateNotification() {
        while (true) {
            try {
                System.out.print("Nhập ID thông báo cần sửa: ");
                String idStr = scanner.nextLine().trim();
                int id = validateNotificationId(idStr);

                System.out.print("Nhập nội dung mới: ");
                String msg = scanner.nextLine().trim();
                validateMessage(msg);

                Notification updated = new Notification(id);
                updated.setMessage(msg);
                dao.update(updated);
                System.out.println("Đã cập nhật thông báo.");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void deleteNotification() {
        while (true) {
            try {
                System.out.print("Nhập ID thông báo cần xóa: ");
                String idStr = scanner.nextLine().trim();
                int id = validateNotificationId(idStr);

                Notification toDelete = new Notification(id);
                dao.delete(toDelete);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void showAllNotifications() {
        ArrayList<Notification> list = dao.selectAll();
        if (list.isEmpty()) {
            System.out.println("Không có thông báo nào.");
        } else {
            System.out.println("Danh sách thông báo:");
            for (Notification n : list) {
                System.out.println("ID: " + n.getId() + " | [" + n.getCreatedAt() + "] " +
                        n.getSenderRole().toUpperCase() + " (ID: " + n.getSenderId() + "): " +
                        n.getMessage());
            }
        }
    }

    // Exception
    private static void validateMessage(String msg) {
        if (msg.isEmpty()) {
            throw new IllegalArgumentException("Nội dung thông báo không được để trống.");
        }
    }

    private static int validateNotificationId(String input) {
        if (input.isEmpty()) throw new IllegalArgumentException("ID không được để trống.");
        try {
            int id = Integer.parseInt(input);
            if (id < 0) throw new IllegalArgumentException("ID không được âm.");
            return id;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID phải là số nguyên hợp lệ.");
        }
    }
}
