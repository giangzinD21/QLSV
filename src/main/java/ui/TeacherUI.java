package ui;

import dao.GradeDAO;
import dao.GroupScheduleDAO;
import dao.NotificationDAO;
import dao.TeacherDAO;
import model.Notification;
import model.Teacher;
import model.TeacherSchedule;

import java.util.List;
import java.util.Scanner;


public class TeacherUI {
    private static Scanner scanner = new Scanner(System.in);
    private static final TeacherDAO teacherDAO = new TeacherDAO();
    private static final GroupScheduleDAO csDao = new GroupScheduleDAO();

    public static void showMenu(int userId) {
        try {
            int teacherId = teacherDAO.selectByUserId(userId);
            while (true) {
                System.out.println("1. Xem lịch dạy");
                System.out.println("2. Nhận thông báo");
                System.out.println("0. Đăng xuất");
                System.out.print("Chọn chức năng: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 0:
                        return;
                    case 1:
                        viewTeachingSchedule(teacherId);
                        break;
                    case 2:
                        showNotificationsTeacher(teacherId);
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ!");
                }
            }
        }
        catch (Exception e){
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
    private static void viewTeachingSchedule(int teacherId) {
        List<TeacherSchedule> list = csDao.selectByTeacher(teacherId);
        if (list.isEmpty()) {
            System.out.println(">> Chưa có lịch dạy nào.");
            return;
        }
        System.out.printf("%-3s | %-10s | %-25s | %-3s | %-11s | %s%n",
                "ID", "GROUP", "COURSE", "DAY", "TIMESLOT", "ROOM");
        System.out.println("-------------------------------------------------------------------------------------");
        for (TeacherSchedule ts : list) {
            System.out.println(ts);
        }
        System.out.println("-------------------------------------------------------------------------------------");
    }
    private static void showNotificationsTeacher(int teacherId) {
        List<Notification> inbox = new NotificationDAO().selectInbox("teacher", teacherId);
        if (inbox.isEmpty()) {
            System.out.println(">> Chưa có thông báo.");
            return;
        }
        System.out.println("\n--- HỘP THƯ THÔNG BÁO ---");
        for (Notification n : inbox) {
            System.out.println(n);
        }
    }

}
