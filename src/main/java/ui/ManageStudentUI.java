package ui;

import dao.StudentDAO;
import model.Student;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class ManageStudentUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentDAO dao = new StudentDAO();

    public static void showMenu() {
        do {
            System.out.println("\n==================== QUẢN LÝ SINH VIÊN ====================");
            System.out.println("1. Xem danh sách sinh viên");
            System.out.println("2. Thêm sinh viên");
            System.out.println("3. Sửa sinh viên");
            System.out.println("4. Xóa sinh viên");
            System.out.println("5. Tìm theo tên");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(">> Vui lòng nhập số.");
                choice = -1;
            }

            switch (choice) {
                case 1:
                    viewStudents();
                    break;
                case 2:
                    addStudent();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    findByName();
                    break;
                case 0:
                    System.out.println(">> Kết thúc quản lý sinh viên.");
                    return;
                default:
                    System.out.println(">> Lựa chọn không hợp lệ.");
                    break;
            }
        } while (true);
    }

    private static void viewStudents() {
        List<Student> list = dao.selectAll();
        if (list.isEmpty()) {
            System.out.println(">> Chưa có sinh viên nào.");
            return;
        }
        System.out.printf("%-5s | %-15s | %-20s | %-10s | %-15s | %-20s | %s%n",
                "ID","USERNAME","HỌ TÊN","MÃ LỚP","MÃ SV","EMAIL","CREATED_AT");
        System.out.println("--------------------------------------------------------------------------------");
        for (Student s : list) {
            String created = s.getCreatedAt()!=null ? s.getCreatedAt().toString() : "";
            System.out.printf("%-5d | %-15s | %-20s | %-10s | %-15s | %-20s | %s%n",
                    s.getStudentId(),
                    s.getUsername(),
                    s.getName(),
                    s.getGroupId(),
                    s.getStudentCode(),
                    s.getEmail(),
                    created);
        }
    }

    private static void addStudent() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Mật khẩu: ");
        String password = scanner.nextLine().trim();
        System.out.print("Họ và tên: ");
        String name = scanner.nextLine().trim();
        System.out.print("Mã lớp: ");
        int groupCode = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Mã sinh viên: ");
        String code = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        Student s = new Student(
                username, password, name, groupCode, code, email,
                new Timestamp(System.currentTimeMillis())
        );
        dao.add(s);
    }

    private static void updateStudent() {
        System.out.print("Nhập student_id cần sửa: ");
        int id;
        try { id = Integer.parseInt(scanner.nextLine().trim()); }
        catch (Exception e) {
            System.out.println(">> ID không hợp lệ."); return;
        }
        Student s = dao.selectById(id);
        if (s==null) {
            System.out.println(">> Không tìm thấy sinh viên."); return;
        }

        System.out.print("Username mới (ENTER để giữ): ");
        String u = scanner.nextLine().trim(); if (!u.isEmpty()) s.setUsername(u);

        System.out.print("Mật khẩu mới (ENTER để giữ): ");
        String p = scanner.nextLine().trim(); if (!p.isEmpty()) s.setPassword(p);

        System.out.print("Họ tên mới (ENTER để giữ): ");
        String n = scanner.nextLine().trim(); if (!n.isEmpty()) s.setName(n);

        System.out.print("ID lớp mới (ENTER để giữ): ");
        int g = Integer.parseInt(scanner.nextLine().trim());
        if (g!=s.getGroupId()) s.setGroupId(g);

        System.out.print("Mã SV mới (ENTER để giữ): ");
        String c = scanner.nextLine().trim(); if (!c.isEmpty()) s.setStudentCode(c);

        System.out.print("Email mới (ENTER để giữ): ");
        String e = scanner.nextLine().trim(); if (!e.isEmpty()) s.setEmail(e);

        dao.update(s);
    }

    private static void deleteStudent() {
        try{
            System.out.print("Nhập student_id cần xóa: ");
            int id;
            try { id = Integer.parseInt(scanner.nextLine().trim()); }
            catch (Exception e) {
                System.out.println(">> ID không hợp lệ."); return;
            }
            System.out.print(">> Xác nhận xóa? (y/n): ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                System.out.println(">> Đã hủy.");
                return;
            }
            Student existing = dao.selectById(id);
            if(existing==null) System.out.println(">> Không tìm thấy sinh viên");
            else    dao.delete(existing);
        }
        catch (Exception e) {
            System.out.println(">> Lỗi khi xóa sinh viên: " + e.getMessage());
        }
    }

    private static void findByName() {
        System.out.print("Nhập tên muốn tìm: ");
        String name = scanner.nextLine().trim();
        Student s = dao.selectByName(name);
        if (s==null) System.out.println(">> Không tìm thấy sinh viên theo tên");
        else System.out.println(s);
    }
}
