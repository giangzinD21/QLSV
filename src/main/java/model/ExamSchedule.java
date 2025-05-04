package model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class ExamSchedule {
    private int examId;
    private int classId;
    private Integer courseId;    // course có thể null
    private Date examDate;
    private Time startTime;
    private Time endTime;
    private String location;
    private Timestamp createdAt;

    public ExamSchedule() {}

    // Full constructor
    public ExamSchedule(int examId, int classId, int courseId,
                        Date examDate, Time startTime, Time endTime,
                        String location, Timestamp createdAt) {
        this.examId    = examId;
        this.classId   = classId;
        this.courseId  = courseId;
        this.examDate  = examDate;
        this.startTime = startTime;
        this.endTime   = endTime;
        this.location  = location;
        this.createdAt = createdAt;
    }

    // Constructor for insert (no examId, no createdAt)
    public ExamSchedule(int classId, int courseId,
                        Date examDate, Time startTime, Time endTime,
                        String location) {
        this(0, classId, courseId, examDate, startTime, endTime, location, null);
    }

    // Getters & Setters…
    public int getExamId() { return examId; }
    public void setExamId(int examId) { this.examId = examId; }

    public int getClassId() { return classId; }
    public void setClassId(int classId) { this.classId = classId; }

    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }

    public Date getExamDate() { return examDate; }
    public void setExamDate(Date examDate) { this.examDate = examDate; }

    public Time getStartTime() { return startTime; }
    public void setStartTime(Time startTime) { this.startTime = startTime; }

    public Time getEndTime() { return endTime; }
    public void setEndTime(Time endTime) { this.endTime = endTime; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return String.format(
                "ID=%d | classId=%d | courseId=%s | date=%s | %s-%s | %s",
                examId,
                classId,
                courseId,
                examDate,
                startTime, endTime,
                location
        );
    }
}
