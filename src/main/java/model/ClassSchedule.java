package model;

public class ClassSchedule {
    private int scheduleId;      // ID của bản ghi lịch (nếu cần)
    private String courseName;   // Tên môn
    private String dayOfWeek;    // Thứ (MON, TUE…)
    private String timeSlot;     // Khung giờ, ví dụ “08:00-10:00”
    private String room;         // Phòng học
    private String teacherName;  // Tên giảng viên

    public ClassSchedule(int scheduleId,
                         String courseName,
                         String dayOfWeek,
                         String timeSlot,
                         String room,
                         String teacherName) {
        this.scheduleId  = scheduleId;
        this.courseName  = courseName;
        this.dayOfWeek   = dayOfWeek;
        this.timeSlot    = timeSlot;
        this.room        = room;
        this.teacherName = teacherName;
    }

    // getters
    public int getScheduleId()   { return scheduleId; }
    public String getCourseName(){ return courseName; }
    public String getDayOfWeek() { return dayOfWeek; }
    public String getTimeSlot()  { return timeSlot; }
    public String getRoom()      { return room; }
    public String getTeacherName(){ return teacherName; }

    @Override
    public String toString() {
        return String.format("%-3d | %-30s | %-4s | %-11s | %-10s | %s",
                scheduleId, courseName, dayOfWeek, timeSlot, room, teacherName);
    }
}
