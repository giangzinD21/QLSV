package model;

public class Class {
    private String classId;
    private String courseId;
    private String teacherId;
    private int maxStudents;

    public Class(String classId, String courseId, String teacherId, int maxStudents) {
        this.classId = classId;
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.maxStudents = maxStudents;
    }

    // Getters & Setters
    public String getClassId() { return classId; }
    public void setClassId(String classId) { this.classId = classId; }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }

    public int getMaxStudents() { return maxStudents; }
    public void setMaxStudents(int maxStudents) { this.maxStudents = maxStudents; }
}
