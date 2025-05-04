package ui;

import dao.*;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentDAO studentDAO = new StudentDAO();
    private static final GroupScheduleDAO csDao = new GroupScheduleDAO();
    public static void showMenu(int userId) {
        int studentId = studentDAO.selectByUserId(userId);
        while (true) {
            System.out.println("1. Xem lịch học");
            System.out.println("2. Xem lịch thi cuối kì");
            System.out.println("3. Xem kết quả học tập");
            System.out.println("4. Xem thông báo");
            System.out.println("0. Đăng xuất");
            System.out.println("Chọn chức năng: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Lựa chọn không hợp lệ.");
                continue;
            }
            switch (choice) {
                case 0:
                    return;
                case 1:
                    viewSchedule(studentId);
                    break;
                case 2:
                    viewExamSchedule(studentId);
                    break;
                case 3:
                    viewTranscript(studentId);
                    break;
                case 4:
                    showNotificationsStudent(studentId);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
    private static void viewSchedule(int studentId) {

        List<ClassSchedule> list = csDao.selectByStudent(studentId);
        if (list.isEmpty()) {
            System.out.println(">> Chưa có lịch học.");
            return;
        }
        System.out.printf("%-3s | %-30s | %-4s | %-11s | %-10s | %s%n",
                "ID", "COURSE", "DAY", "TIMESLOT", "ROOM", "TEACHER");
        System.out.println("-------------------------------------------------------------------------------------");
        for (ClassSchedule cs : list) {
            System.out.println(cs);
        }
        System.out.println("-------------------------------------------------------------------------------------");
    }
    private static void viewExamSchedule(int studentId) {
        // 1) Lấy thông tin group_id của student
        Student s = studentDAO.selectById(studentId);
        if (s == null || s.getGroupId() == 0) {
            System.out.println(">> Không tìm thấy lớp của sinh viên hoặc chưa được xếp lớp.");
            return;
        }
        int groupId = s.getGroupId();

        // 2) Gọi DAO
        List<ExamScheduleStudent> exams = new ExamScheduleDAO().selectByGroup(groupId);
        if (exams.isEmpty()) {
            System.out.println(">> Chưa có lịch thi cuối kỳ.");
            return;
        }

        // 3) In ra
        System.out.printf("%-5s | %-25s | %-10s | %-11s | %s%n",
                "ID", "COURSE", "DATE", "TIMESLOT", "LOCATION");
        System.out.println("-----------------------------------------------------------------------");
        for (ExamScheduleStudent ex : exams) {
            System.out.println(ex);
        }
        System.out.println("-----------------------------------------------------------------------");
    }
    private static void viewTranscript(int studentId) {
        // 1) Lấy danh sách điểm
        List<StudentGrade> grades = new GradeDAO().selectByStudent(studentId);
        if (grades.isEmpty()) {
            System.out.println(">> Chưa có điểm nào.");
            return;
        }

        // 2) In bảng tiêu đề
        System.out.printf("%-5s | %-25s | %-7s | %s%n",
                "ID", "COURSE", "CREDITS", "GRADE");
        System.out.println("---------------------------------------------------------");

        // 3) In từng dòng và tính tổng tín chỉ, tổng (grade*credits)
        int totalCredits = 0;
        double weightedSum = 0.0;
        for (StudentGrade sg : grades) {
            System.out.println(sg);
            double index;
            if(sg.getGradeValue() >= 9)   index = 4.0;
            else if(sg.getGradeValue() >= 8.5) index = 3.7;
            else if(sg.getGradeValue() >= 8) index = 3.5;
            else if(sg.getGradeValue() >= 7) index = 3;
            else if(sg.getGradeValue() >= 6.5) index = 2.5;
            else if(sg.getGradeValue() >= 6) index = 2;
            else if(sg.getGradeValue() >= 5.5) index = 1.5;
            else if(sg.getGradeValue() >= 5) index = 1;
            else index = 0;
            totalCredits += sg.getCredits();
            weightedSum  += index * sg.getCredits();
        }
        System.out.println("---------------------------------------------------------");

        // 4) Tính GPA (nếu totalCredits >0)
        double gpa = totalCredits > 0 ? (weightedSum / totalCredits) : 0.0;
        System.out.printf(">> Tổng tín chỉ: %d, GPA: %.2f%n", totalCredits, gpa);
    }
    private static void showNotificationsStudent(int studentId) {
        List<Notification> inbox = new NotificationDAO().selectInbox("student", studentId);
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
