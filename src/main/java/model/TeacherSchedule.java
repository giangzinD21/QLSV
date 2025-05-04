package model;

public class TeacherSchedule {
    private int scheduleId;
    private String groupCode;
    private String courseName;
    private String dayOfWeek;
    private String timeslot;
    private String room;

    public TeacherSchedule(int scheduleId, String groupCode, String courseName,
                           String dayOfWeek, String timeslot, String room) {
        this.scheduleId = scheduleId;
        this.groupCode  = groupCode;
        this.courseName = courseName;
        this.dayOfWeek  = dayOfWeek;
        this.timeslot   = timeslot;
        this.room       = room;
    }

    public int getScheduleId() { return scheduleId; }
    public String getGroupCode()  { return groupCode; }
    public String getCourseName() { return courseName; }
    public String getDayOfWeek()  { return dayOfWeek; }
    public String getTimeslot()   { return timeslot; }
    public String getRoom()       { return room; }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public void setRoom(String room) {
        this.room = room;
    }
    @Override
    public String toString() {
        return String.format("%-3d | %-10s | %-25s | %-3s | %-11s | %s",
                scheduleId, groupCode, courseName, dayOfWeek, timeslot, room);
    }
}
