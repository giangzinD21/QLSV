package model;

public class StudentGrade {
    private int gradeId;
    private String courseName;
    private int credits;
    private double gradeValue;

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public double getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(double gradeValue) {
        this.gradeValue = gradeValue;
    }

    public StudentGrade(int gradeId, String courseName,
                        int credits, double gradeValue) {
        this.gradeId    = gradeId;
        this.courseName = courseName;
        this.credits    = credits;
        this.gradeValue = gradeValue;
    }


    @Override
    public String toString() {
        return String.format("%-5d | %-25s | %-7d | %.2f",
                gradeId, courseName, credits, gradeValue);
    }
}
