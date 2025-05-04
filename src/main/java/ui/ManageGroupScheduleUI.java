// ui/ManageGroupScheduleUI.java
package ui;

import dao.GroupScheduleDAO;
import model.GroupSchedule;

import java.sql.Time;
import java.util.List;
import java.util.Scanner;

public class ManageGroupScheduleUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final GroupScheduleDAO dao = new GroupScheduleDAO();

    public static void showMenu() {
        int choice;
        do {
            System.out.println("\n==== QUẢN LÝ LỊCH HỌC ====");
            System.out.println("1. Xem tất cả lịch");
            System.out.println("2. Thêm lịch");
            System.out.println("3. Sửa lịch");
            System.out.println("4. Xóa lịch");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    viewAll();
                    break;
                case 2:
                    addSchedule();
                    break;
                case 3:
                    updateSchedule();
                    break;
                case 4:
                    deleteSchedule();
                    break;
                case 0:
                    System.out.println("Quay lại menu trước.");
                    return;
                default:
                    System.out.println("❌ Lựa chọn sai.");
                    break;
            }
        } while (choice != 0);
    }

    public static void viewAll() {
        List<GroupSchedule> list = dao.selectAll();
        if (list.isEmpty()) {
            System.out.println(">> Hiện chưa có lịch học nào.");
            return;
        }
        System.out.println("\n--- Xem danh sách lịch học ---");
        System.out.println("ID | Group | Course | Teacher | Day | Start - End | Room");
        System.out.println("----------------------------------------------------------");
        for (GroupSchedule gs : list) {
            System.out.printf("%-3d| %-6d| %-7d| %-8d| %-4s| %s - %s | %s%n",
                    gs.getScheduleId(),
                    gs.getGroupId(),
                    gs.getCourseId(),
                    gs.getTeacherId(),
                    gs.getDayOfWeek(),
                    gs.getStartTime().toString().substring(0, 5),
                    gs.getEndTime().toString().substring(0, 5),
                    gs.getRoom());
        }
    }

    private static void addSchedule() {
        try {
            System.out.println("\n--- Thêm lịch học ---");
            System.out.print("Group ID: ");
            int gid = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Course ID: ");
            int cid = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Teacher ID: ");
            int tid = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Ngày (MON/TUE/...): ");
            String day = scanner.nextLine().trim();

            System.out.print("Giờ bắt đầu (HH:MM): ");
            Time st = Time.valueOf(scanner.nextLine().trim() + ":00");

            System.out.print("Giờ kết thúc (HH:MM): ");
            Time et = Time.valueOf(scanner.nextLine().trim() + ":00");

            System.out.print("Phòng: ");
            String room = scanner.nextLine().trim();

            dao.add(new GroupSchedule(gid, cid, tid, day, st, et, room));
        } catch (Exception e) {
            System.out.println("❌ Lỗi dữ liệu nhập: " + e.getMessage());
        }
    }

    private static void updateSchedule() {
        try {
            System.out.println("\n--- Cập nhật lịch học ---");
            System.out.print("Nhập Schedule ID cần sửa: ");
            int sid = Integer.parseInt(scanner.nextLine().trim());
            GroupSchedule existing = dao.selectById(sid);
            if (existing == null) {
                System.out.println("❌ Không tìm thấy lịch với ID " + sid);
                return;
            }

            System.out.print("Group ID (ENTER để giữ " + existing.getGroupId() + "): ");
            String in = scanner.nextLine().trim();
            if (!in.isEmpty()) existing.setGroupId(Integer.parseInt(in));

            System.out.print("Course ID (ENTER để giữ " + existing.getCourseId() + "): ");
            in = scanner.nextLine().trim();
            if (!in.isEmpty()) existing.setCourseId(Integer.parseInt(in));

            System.out.print("Teacher ID (ENTER để giữ " + existing.getTeacherId() + "): ");
            in = scanner.nextLine().trim();
            if (!in.isEmpty()) existing.setTeacherId(Integer.parseInt(in));

            System.out.print("Ngày (ENTER để giữ " + existing.getDayOfWeek() + "): ");
            in = scanner.nextLine().trim();
            if (!in.isEmpty()) existing.setDayOfWeek(in.toUpperCase());

            System.out.print("Giờ bắt đầu (HH:MM) (ENTER để giữ " + existing.getStartTime() + "): ");
            in = scanner.nextLine().trim();
            if (!in.isEmpty()) existing.setStartTime(Time.valueOf(in + ":00"));

            System.out.print("Giờ kết thúc (HH:MM) (ENTER để giữ " + existing.getEndTime() + "): ");
            in = scanner.nextLine().trim();
            if (!in.isEmpty()) existing.setEndTime(Time.valueOf(in + ":00"));

            System.out.print("Phòng (ENTER để giữ " + existing.getRoom() + "): ");
            in = scanner.nextLine().trim();
            if (!in.isEmpty()) existing.setRoom(in);

            dao.update(existing);
            System.out.println("✅ Cập nhật lịch học thành công!");
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi cập nhật: " + e.getMessage());
        }
    }

    private static void deleteSchedule() {
        try {
            System.out.println("\n--- Xóa lịch học ---");
            System.out.print("Nhập Schedule ID cần xóa: ");
            int sid = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Xác nhận xóa? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (!confirm.equals("y")) {
                System.out.println(">> Đã hủy.");
                return;
            }
            GroupSchedule existing = new GroupSchedule();
            existing.setScheduleId(sid);
            dao.delete(existing);
            System.out.println("✅ Xóa lịch học thành công!");
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi xóa: " + e.getMessage());
        }
    }
}
