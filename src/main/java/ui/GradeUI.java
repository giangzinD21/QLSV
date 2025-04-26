package ui;

import model.Grade;
import service.GradeService;

import java.util.ArrayList;
import java.util.Scanner;

public class GradeUI {
    private final GradeService gradeService = new GradeService();

    public void showStudentTranscript(int studentId) {
        Scanner sc = new Scanner(System.in);
        System.out.print("🔍 Nhập học kỳ muốn xem (ví dụ: 2024-HK1): ");
        String semester = sc.nextLine();

        ArrayList<Grade> grades = gradeService.getGrades(studentId, semester);

        if (grades.isEmpty()) {
            System.out.println("⚠️ Không có kết quả cho học kỳ này.");
            return;
        }

        System.out.println("\n📚 Kết quả học tập học kỳ " + semester + ":");
        for (Grade g : grades) {
            System.out.printf(" - %s: %.2f điểm (%d tín chỉ)%n",
                    g.getCourseName(), g.getGrade(), g.getCredits());
        }

        float gpa = gradeService.calculateGPA(grades);
        System.out.printf("\n🎯 GPA học kỳ: %.2f%n", gpa);
        System.out.println("🏆 Xếp loại học lực: " + gradeService.classifyGPA(gpa));
    }
}
