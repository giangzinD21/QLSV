package ui;

import dao.TeacherDAO;
import model.Teacher;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class ManageTeacherUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TeacherDAO teacherDAO = new TeacherDAO();

    public static void showMenu() {
        int choice = -1;
        do {
            System.out.println("\n==================== QUẢN LÝ GIẢNG VIÊN ====================");
            System.out.println("1. Xem danh sách giảng viên");
            System.out.println("2. Thêm giảng viên");
            System.out.println("3. Sửa giảng viên");
            System.out.println("4. Xóa giảng viên");
            System.out.println("5. Tìm giảng viên theo ID");
            System.out.println("6. Tìm giảng viên theo email");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(">> Vui lòng nhập số.");
                choice = -1;
            }

            switch (choice) {
                case 1:
                    viewTeachers();
                    break;
                case 2:
                    addTeacher();
                    break;
                case 3:
                    updateTeacher();
                    break;
                case 4:
                    deleteTeacher();
                    break;
                case 5:
                    searchById();
                    break;
                case 6:
                    searchByEmail();
                    break;
                case 0:
                    System.out.println(">> Kết thúc chương trình quản lý giảng viên.");
                    break;
                default:
                    if (choice != 0) System.out.println(">> Lựa chọn không hợp lệ.");            }
        } while (choice != 0);
    }

    private static void viewTeachers() {
        List<Teacher> list = teacherDAO.selectAll();
        if (list.isEmpty()) {
            System.out.println(">> Hiện chưa có giảng viên nào.");
            return;
        }
        System.out.printf("%-5s | %-15s | %-20s | %-25s | %s%n", "ID", "USERNAME", "HỌ TÊN", "EMAIL", "CREATED_AT");
        System.out.println("----------------------------------------------------------------------------");
        for (Teacher t : list) {
            String created = t.getCreatedAt() != null ? t.getCreatedAt().toString() : "";
            System.out.printf("%-5d | %-15s | %-20s | %-25s | %s%n",
                    t.getTeacherId(), t.getUsername(), t.getName(), t.getEmail(), created);
        }
        System.out.println("----------------------------------------------------------------------------");
        System.out.println(">> Tổng số: " + list.size() + " giảng viên.");
    }

    private static void addTeacher() {
        try {
            System.out.println("\n==================== THÊM GIẢNG VIÊN MỚI ====================");
            System.out.print("Nhập username: ");
            String username = scanner.nextLine().trim();
            System.out.print("Nhập mật khẩu: ");
            String password = scanner.nextLine().trim();
            System.out.print("Nhập họ và tên: ");
            String name = scanner.nextLine().trim();
            System.out.print("Nhập email: ");
            String email = scanner.nextLine().trim();

            Teacher t = new Teacher(username, password, name, email, new Timestamp(System.currentTimeMillis()));
            teacherDAO.add(t);
        } catch (Exception e) {
            System.out.println(">> Lỗi khi thêm giảng viên: " + e.getMessage());
        }

    }

    private static void updateTeacher() {
        try {
            System.out.println("\n==================== CẬP NHẬT GIẢNG VIÊN ====================");
            System.out.print("Nhập teacher_id cần sửa: ");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(">> ID không hợp lệ.");
                return;
            }
            Teacher t = teacherDAO.selectById(id);
            if (t == null) {
                System.out.println(">> Không tìm thấy giảng viên.");
                return;
            }
            System.out.print("Username mới (ENTER để giữ): ");
            String u = scanner.nextLine().trim();
            if (!u.isEmpty()) t.setUsername(u);

            System.out.print("Mật khẩu mới (ENTER để giữ): ");
            String p = scanner.nextLine().trim();
            if (!p.isEmpty()) t.setPassword(p);

            System.out.print("Họ và tên mới (ENTER để giữ): ");
            String n = scanner.nextLine().trim();
            if (!n.isEmpty()) t.setName(n);

            System.out.print("Email mới (ENTER để giữ): ");
            String e = scanner.nextLine().trim();
            if (!e.isEmpty()) t.setEmail(e);

            teacherDAO.update(t);
            System.out.println(">> Cập nhật thành công.");
        }
        catch(Exception e) {
            System.out.println(" >> Lỗi khi cập nhật giảng viên: " + e.getMessage());
        }
    }

    private static void deleteTeacher() {
        try {
            System.out.println("\n==================== XÓA GIẢNG VIÊN ====================");
            System.out.print("Nhập teacher_id cần xóa: ");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(">> ID không hợp lệ.");
                return;
            }
            System.out.print(">> Bạn có chắc chắn muốn xóa? (y/n): ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                System.out.println(">> Hủy xóa.");
                return;
            }
            Teacher existing = teacherDAO.selectById(id);
            if(existing == null) System.out.println(">> Không tìm thấy giảng viên");
            else teacherDAO.delete(existing);

        }
        catch (Exception e) {
            System.out.println(">> Lỗi khi xóa giảng viên: " + e.getMessage());
        }
    }

    private static void searchById() {
        System.out.print("Nhập teacher_id cần tìm: ");
        int id;
        try { id = Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println(">> ID không hợp lệ."); return; }
        Teacher t = teacherDAO.selectById(id);
        if (t == null) System.out.println(">> Không tìm thấy.");
        else System.out.println(t);
    }

    private static void searchByEmail() {
        System.out.print("Nhập email cần tìm: ");
        String email = scanner.nextLine().trim();
        List<Teacher> list = teacherDAO.selectByCondition(email);
        if (list.isEmpty()) System.out.println(">> Không tìm thấy giảng viên với email này.");
        else{
            System.out.println(list.size());
            for (Teacher t : list) {
                String created = t.getCreatedAt() != null ? t.getCreatedAt().toString() : "";
                System.out.printf("%-5d | %-15s | %-20s | %-25s | %s%n",
                        t.getTeacherId(), t.getUsername(), t.getName(), t.getEmail(), created);
            }
        }
    }
}
