package service;

import dao.GradeDAO;
import model.Grade;

import java.util.ArrayList;

public class GradeService {
    private final GradeDAO gradeDAO = new GradeDAO();

    public ArrayList<Grade> getGrades(int studentId, String semester) {
        return gradeDAO.getGradesByStudentAndSemester(studentId, semester);
    }

    public float calculateGPA(ArrayList<Grade> grades) {
        float totalScore = 0;
        int totalCredits = 0;
        for (Grade g : grades) {
            totalScore += g.getGrade() * g.getCredits();
            totalCredits += g.getCredits();
        }
        return totalCredits == 0 ? 0 : totalScore / totalCredits;
    }

    public String classifyGPA(float gpa) {
        if (gpa >= 8.5) return "Xuất sắc";
        else if (gpa >= 7.0) return "Khá";
        else if (gpa >= 5.5) return "Trung bình";
        else if (gpa >= 4.0) return "Yếu";
        else return "Kém";
    }
}
