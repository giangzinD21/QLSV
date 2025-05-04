package ui;

import dao.ExamScheduleDAO;
import model.ExamSchedule;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class ManageExamScheduleUI {
    private static final Scanner sc = new Scanner(System.in);
    private static final ExamScheduleDAO dao = new ExamScheduleDAO();

    public static void showMenu() {
        int choice;
        do {
            System.out.println("\n==== QUẢN LÝ LỊCH THI ====");
            System.out.println("1. Xem danh sách lịch thi");
            System.out.println("2. Thêm lịch thi");
            System.out.println("3. Sửa lịch thi");
            System.out.println("4. Xóa lịch thi");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> viewAll();
                case 2 -> addExam();
                case 3 -> updateExam();
                case 4 -> deleteExam();
                case 0 -> System.out.println("Quay lại menu trước.");
                default -> System.out.println("❌ Lựa chọn sai.");
            }
        } while (choice != 0);
    }

    private static void viewAll() {
        List<ExamSchedule> list = dao.selectAll();
        if (list.isEmpty()) {
            System.out.println(">> Chưa có lịch thi nào.");
            return;
        }
        System.out.printf("%-5s | %-7s | %-7s | %-10s | %-5s | %-5s | %s%n",
                "ID","CLASS","COURSE","DATE","START","END","ROOM");
        System.out.println("------------------------------------------------------------");
        for (ExamSchedule e : list) {
            String date  = e.getExamDate().toString();
            String start = e.getStartTime()!=null?e.getStartTime().toString().substring(0,5):"";
            String end   = e.getEndTime()!=null?e.getEndTime().toString().substring(0,5):"";
            System.out.printf("%-5d | %-7d | %-7s | %-10s | %-5s | %-5s | %s%n",
                    e.getExamId(),
                    e.getClassId(),
                    e.getCourseId()==null?"":e.getCourseId(),
                    date, start, end, e.getLocation());
        }
    }

    private static void addExam() {
        try {
            System.out.println("\n--- Thêm lịch thi ---");
            System.out.print("Class ID: ");
            int classId = Integer.parseInt(sc.nextLine());

            System.out.print("Course ID: ");
            String cIn = sc.nextLine().trim();
            int courseId = Integer.parseInt(cIn);

            System.out.print("Ngày thi (yyyy-MM-dd): ");
            Date date = Date.valueOf(sc.nextLine());

            System.out.print("Giờ bắt đầu (HH:mm): ");
            Time st = Time.valueOf(sc.nextLine()+":00");

            System.out.print("Giờ kết thúc (HH:mm): ");
            Time et = Time.valueOf(sc.nextLine()+":00");

            System.out.print("Địa điểm: ");
            String loc = sc.nextLine().trim();

            dao.add(new ExamSchedule(classId, courseId, date, st, et, loc));
        } catch (Exception e) {
            System.out.println("❌ Lỗi nhập dữ liệu: "+ e.getMessage());
        }
    }

    private static void updateExam() {
        try {
            System.out.println("\n--- Sửa lịch thi ---");
            System.out.print("Nhập exam_id: ");
            int id = Integer.parseInt(sc.nextLine());
            ExamSchedule ex = dao.selectById(id);
            if (ex==null) {
                System.out.println("❌ Không tìm thấy.");
                return;
            }

            System.out.print("Class ID ("+ex.getClassId()+"): ");
            String in = sc.nextLine().trim();
            if (!in.isEmpty()) ex.setClassId(Integer.parseInt(in));

            System.out.print("Course ID ("+ ex.getCourseId()+"): ");
            in = sc.nextLine().trim();
            if (!in.isEmpty()) ex.setCourseId(Integer.parseInt(in));

            System.out.print("Ngày thi ("+ex.getExamDate()+"): ");
            in = sc.nextLine().trim();
            if (!in.isEmpty()) ex.setExamDate(Date.valueOf(in));

            System.out.print("Giờ bắt đầu ("+ex.getStartTime()+"): ");
            in = sc.nextLine().trim();
            if (!in.isEmpty()) ex.setStartTime(Time.valueOf(in+":00"));

            System.out.print("Giờ kết thúc ("+ex.getEndTime()+"): ");
            in = sc.nextLine().trim();
            if (!in.isEmpty()) ex.setEndTime(Time.valueOf(in+":00"));

            System.out.print("Địa điểm ("+ex.getLocation()+"): ");
            in = sc.nextLine().trim();
            if (!in.isEmpty()) ex.setLocation(in);

            dao.update(ex);
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi cập nhật: "+e.getMessage());
        }
    }

    private static void deleteExam() {
        try {
            System.out.println("\n--- Xóa lịch thi ---");
            System.out.print("Nhập exam_id: ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Xác nhận (y/n)? ");
            if (!sc.nextLine().equalsIgnoreCase("y")) {
                System.out.println(">> Hủy xóa.");
                return;
            }
            ExamSchedule ex = new ExamSchedule();
            ex.setExamId(id);
            dao.delete(ex);
        } catch (Exception e) {
            System.out.println("❌ Lỗi xóa: "+ e.getMessage());
        }
    }
}
