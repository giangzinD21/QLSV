// model/GroupSchedule.java
package model;

import java.sql.Time;
import java.sql.Timestamp;

public class GroupSchedule {
    private int scheduleId;
    private int groupId;
    private int courseId;      // mới
    private int teacherId;     // mới
    private String dayOfWeek;
    private Time startTime;
    private Time endTime;
    private String room;
    private Timestamp createdAt;

    public GroupSchedule() {}
    // Constructor đầy đủ
    public GroupSchedule(int scheduleId, int groupId, int courseId, int teacherId,
                         String dayOfWeek, Time startTime, Time endTime,
                         String room, Timestamp createdAt) {
        this.scheduleId = scheduleId;
        this.groupId    = groupId;
        this.courseId   = courseId;
        this.teacherId  = teacherId;
        this.dayOfWeek  = dayOfWeek;
        this.startTime  = startTime;
        this.endTime    = endTime;
        this.room       = room;
        this.createdAt  = createdAt;
    }

    // Constructor dùng cho thêm mới
    public GroupSchedule(int groupId, int courseId, int teacherId,
                         String dayOfWeek, Time startTime, Time endTime, String room) {
        this(0, groupId, courseId, teacherId, dayOfWeek, startTime, endTime, room, null);
    }

    // getter / setter cho tất cả
    // ...

    @Override
    public String toString() {
        return String.format("%d | G%d | C%d | T%d | %s %s-%s | %s",
                scheduleId, groupId, courseId, teacherId,
                dayOfWeek, startTime, endTime, room);
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
