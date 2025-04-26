package model;

public class ClassSchedule {
    private int classId;
    private String courseName;
    private String schedule;
    private String semester;
    private String teacherName;

    public ClassSchedule(int classId, String courseName, String schedule, String semester, String teacherName) {
        this.classId = classId;
        this.courseName = courseName;
        this.schedule = schedule;
        this.semester = semester;
        this.teacherName = teacherName;
    }

    public int getClassId() {
        return classId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getSemester() {
        return semester;
    }

    public String getTeacherName() {
        return teacherName;
    }

    @Override
    public String toString() {
        return String.format("Class ID: %d | Course: %s | Teacher: %s | Schedule: %s | Semester: %s",
                classId, courseName, teacherName, schedule, semester);
    }
}
