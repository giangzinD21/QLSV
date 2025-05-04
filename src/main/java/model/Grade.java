// model/Grade.java
package model;

import java.sql.Timestamp;

public class Grade {
    private int gradeId;
    private int groupId;       // Lớp hành chính
    private int courseId;      // Môn học
    private int studentId;     // Sinh viên
    private double gradeValue; // Điểm
    private Timestamp createdAt;

    public Grade() {}

    /** Constructor cho thêm mới (gradeId = 0) */
    public Grade(int groupId, int courseId, int studentId, double gradeValue) {
        this.groupId = groupId;
        this.courseId = courseId;
        this.studentId = studentId;
        this.gradeValue = gradeValue;
    }

    /** Constructor đầy đủ */
    public Grade(int gradeId, int groupId, int courseId, int studentId,
                 double gradeValue, Timestamp createdAt) {
        this.gradeId = gradeId;
        this.groupId = groupId;
        this.courseId = courseId;
        this.studentId = studentId;
        this.gradeValue = gradeValue;
        this.createdAt = createdAt;
    }

    // --- Getters & Setters ---
    public int getGradeId()        { return gradeId; }
    public void setGradeId(int id) { this.gradeId = id; }

    public int getGroupId()         { return groupId; }
    public void setGroupId(int g)   { this.groupId = g; }

    public int getCourseId()         { return courseId; }
    public void setCourseId(int c)   { this.courseId = c; }

    public int getStudentId()         { return studentId; }
    public void setStudentId(int s)   { this.studentId = s; }

    public double getGradeValue()            { return gradeValue; }
    public void setGradeValue(double gv)     { this.gradeValue = gv; }

    public Timestamp getCreatedAt()           { return createdAt; }
    public void setCreatedAt(Timestamp ts)    { this.createdAt = ts; }

    @Override
    public String toString() {
        return String.format("ID=%d | Grp=%d | Crs=%d | Std=%d | Grade=%.2f | %s",
                gradeId, groupId, courseId, studentId, gradeValue,
                createdAt != null ? createdAt.toString() : "");
    }
}
