package model;

public class Grade {
    private int gradeId;
    private int studentId;
    private int classId;
    private float grade;

    // Constructor
    public Grade(int gradeId, int studentId, int classId, float grade) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.classId = classId;
        this.grade = grade;
    }

    // Getters and Setters
    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "gradeId=" + gradeId +
                ", studentId=" + studentId +
                ", classId=" + classId +
                ", grade=" + grade +
                '}';
    }
}