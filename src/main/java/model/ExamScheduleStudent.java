package model;

import java.sql.Date;

public class ExamScheduleStudent {
    private int examId;
    private String courseName;
    private Date examDate;
    private String timeslot;
    private String location;

    public ExamScheduleStudent(int examId, String courseName,
                               Date examDate, String timeslot,
                               String location) {
        this.examId     = examId;
        this.courseName = courseName;
        this.examDate   = examDate;
        this.timeslot   = timeslot;
        this.location   = location;
    }

    public int getExamId()        { return examId; }
    public String getCourseName() { return courseName; }
    public Date getExamDate()     { return examDate; }
    public String getTimeslot()   { return timeslot; }
    public String getLocation()   { return location; }

    @Override
    public String toString() {
        return String.format("%-5d | %-25s | %-10s | %-11s | %s",
                examId, courseName,
                examDate.toString(),
                timeslot, location);
    }
}
