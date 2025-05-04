// ui/ManageGradeUI.java
package ui;

import dao.GradeDAO;
import model.Grade;
import java.util.List;
import java.util.Scanner;

public class ManageGradeUI {
    private static final Scanner sc = new Scanner(System.in);
    private static final GradeDAO gradeDAO = new GradeDAO();
    public static void showMenu() {
        int choice;
        do {
            System.out.println("\n==== QUẢN LÝ ĐIỂM SỐ ====");
            System.out.println("1. Xem danh sách điểm");
            System.out.println("2. Thêm điểm");
            System.out.println("3. Sửa điểm");
            System.out.println("4. Xóa điểm");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");
            choice = Integer.parseInt(sc.nextLine());
            switch(choice) {
                case 1:
                    viewAll();
                    break;
                case 2:
                    addGrade();
                    break;
                case 3:
                    updateGrade();
                    break;
                case 4:
                    deleteGrade();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("❌ Lựa chọn sai.");
            }
        } while(choice != 0);
    }

    private static void viewAll() {
        List<Grade> list = gradeDAO.selectAll();
        if (list.isEmpty()) {
            System.out.println(">> Chưa có điểm nào.");
            return;
        }
        System.out.printf("%-15s | %-15s | %-15s | %-15s | %s%n",
                "ID GRADE","ID CLASS","ID COURSE","ID STUDENT","GRADE VALUE");
        System.out.println("-----------------------------------------------------------------------------------");
        for (Grade g : list) {
            System.out.printf("%-15d | %-15d | %-15d | %-15d | %.2f%n",
                    g.getGradeId(),
                    g.getGroupId(),
                    g.getCourseId(),
                    g.getStudentId(),
                    g.getGradeValue());
        }
    }

    private static void addGrade() {
        try {
            System.out.print("Group ID: ");
            int gid = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Course ID: ");
            int cid = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Student ID: ");
            int sid = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Grade (0-10): ");
            double val = Double.parseDouble(sc.nextLine().trim());
            gradeDAO.add(new Grade(gid, cid, sid, val));
        } catch (Exception e) {
            System.out.println("❌ Lỗi nhập liệu: " + e.getMessage());
        }
    }

    private static void updateGrade() {
        try {
            System.out.print("Nhập Grade ID: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            Grade g = gradeDAO.selectById(id);
            if (g == null) {
                System.out.println("❌ Không tìm thấy.");
                return;
            }
            System.out.printf("Grade cũ (%.2f), nhập mới: ", g.getGradeValue());
            String in = sc.nextLine().trim();
            if (!in.isEmpty()) {
                g.setGradeValue(Double.parseDouble(in));
                System.out.println("Cập nhật điểm thành " + in);
                gradeDAO.update(g);
            }
        } catch (Exception e) {
            System.out.println("❌ Lỗi cập nhật: " + e.getMessage());
        }
    }

    private static void deleteGrade() {
        try {
            System.out.print("Nhập Grade ID: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Xác nhận (y/n)? ");
            if (sc.nextLine().equalsIgnoreCase("y")) {
                gradeDAO.delete(id);
            }
        } catch (Exception e) {
            System.out.println("❌ Lỗi xóa: " + e.getMessage());
        }
    }
}
