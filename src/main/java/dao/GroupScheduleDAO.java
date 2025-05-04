// dao/GroupScheduleDAO.java
package dao;

import model.ClassSchedule;
import model.GroupSchedule;
import model.TeacherSchedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupScheduleDAO implements InterfaceDAO<GroupSchedule> {
    private Connection conn = DatabaseConnection.getConnection();

    // Kiểm tra trùng giờ cho cùng giáo viên và ngày
    public boolean hasTeacherConflict(int teacherId, String day,
                               Time st, Time et) {
        String sql = """
            SELECT COUNT(*) FROM group_schedule
             WHERE teacher_id=? AND day_of_week=?
               AND NOT (end_time<=? OR start_time>=?)
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teacherId);
            ps.setString(2, day);
            ps.setTime(3, st);
            ps.setTime(4, et);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi kiểm tra trùng giờ: " + e.getMessage());
        }
        return true; // mặc định coi là có trùng
    }

    // Kiểm tra xem cùng một group có bị trùng khung giờ (cùng ngày và không (end<=start_new OR start>=end_new))
    private boolean hasGroupConflict(int groupId, String day,
                                     Time st, Time et) {
        String sql = """
        SELECT COUNT(*) FROM group_schedule
         WHERE group_id=? AND day_of_week=?
           AND NOT (end_time<=? OR start_time>=?)
    """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, groupId);
            ps.setString(2, day);
            ps.setTime(3, st);
            ps.setTime(4, et);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi kiểm tra trùng giờ cho lớp: " + e.getMessage());
        }
        return true;
    }
    /**
     * Kiểm tra xem cùng một group có bị trùng khung giờ trừ chính id đấy ra
     * (cùng ngày và không (end<=start_new OR start>=end_new))
     */
    private boolean hasGroupUpdateConflict(int scheduleId, int groupId, String day,
                                     Time st, Time et) {
        String sql = """
        SELECT COUNT(*) FROM group_schedule
        WHERE group_id=? AND day_of_week=?
        AND schedule_id <> ? -- loại trừ chính lịch học này
        AND NOT (end_time<=? OR start_time>=?)
    """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, groupId);
            ps.setString(2, day);
            ps.setInt(3, scheduleId);
            ps.setTime(4, st);
            ps.setTime(5, et);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi kiểm tra trùng giờ cho lớp: " + e.getMessage());
        }
        return true;
    }
    public boolean hasTeacherUpdateConflict(int scheduleId, int teacherId, String day,
                                      Time st, Time et) {
        String sql = """
            SELECT COUNT(*) FROM group_schedule
            WHERE teacher_id=? AND day_of_week=?
            AND schedule_id <> ? -- loại trừ chính lịch học này
            AND NOT (end_time<=? OR start_time>=?)
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teacherId);
            ps.setString(2, day);
            ps.setInt(3, scheduleId);
            ps.setTime(4, st);
            ps.setTime(5, et);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi kiểm tra trùng giờ: " + e.getMessage());
        }
        return true; // mặc định coi là có trùng
    }
    @Override
    public void add(GroupSchedule gs) {
        // 1) Kiểm trùng giờ với teacher
        if (hasTeacherConflict(gs.getTeacherId(), gs.getDayOfWeek(),
                gs.getStartTime(), gs.getEndTime())) {
            System.out.println("❌ Thời gian bị trùng với một lịch đã có của giảng viên.");
            return;
        }
        // 2) Kiểm trùng giờ với group
        if (hasGroupConflict(gs.getGroupId(), gs.getDayOfWeek(), gs.getStartTime(), gs.getEndTime())) {
            System.out.println("❌ Lớp " + gs.getGroupId() + " đã có môn khác trùng giờ.");
            return;
        }
        String sql = """
            INSERT INTO group_schedule
              (group_id, course_id, teacher_id, day_of_week, start_time, end_time, room)
            VALUES (?,?,?,?,?,?,?)
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, gs.getGroupId());
            ps.setInt(2, gs.getCourseId());
            ps.setInt(3, gs.getTeacherId());
            ps.setString(4, gs.getDayOfWeek());
            ps.setTime(5, gs.getStartTime());
            ps.setTime(6, gs.getEndTime());
            ps.setString(7, gs.getRoom());
            ps.executeUpdate();
            System.out.println("✅ Thêm lịch thành công.");
        } catch (SQLException e) {
            System.out.println("❌ Lỗi thêm lịch: " + e.getMessage());
        }
    }

    @Override
    public void update(GroupSchedule gs) {
        // 1) Kiểm trùng giờ với teacher
        if (hasTeacherUpdateConflict(gs.getScheduleId(),gs.getTeacherId(), gs.getDayOfWeek(),
                gs.getStartTime(), gs.getEndTime())) {
            System.out.println("❌ Thời gian bị trùng với một lịch đã có của giảng viên.");
            return;
        }
        // 2) Kiểm trùng giờ với group
        if (hasGroupUpdateConflict(gs.getScheduleId(), gs.getGroupId(), gs.getDayOfWeek(), gs.getStartTime(), gs.getEndTime())) {
            System.out.println("❌ Lớp " + gs.getGroupId() + " đã có môn khác trùng giờ.");
            return;
        }
        String sql = """
            UPDATE group_schedule
               SET group_id=?, course_id=?, teacher_id=?, day_of_week=?, start_time=?, end_time=?, room=?
             WHERE schedule_id=?
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, gs.getGroupId());
            ps.setInt(2, gs.getCourseId());
            ps.setInt(3, gs.getTeacherId());
            ps.setString(4, gs.getDayOfWeek());
            ps.setTime(5, gs.getStartTime());
            ps.setTime(6, gs.getEndTime());
            ps.setString(7, gs.getRoom());
            ps.setInt(8, gs.getScheduleId());
            ps.executeUpdate();
            System.out.println("✅ Cập nhật lịch thành công.");
        } catch (SQLException e) {
            System.out.println("❌ Lỗi cập nhật lịch: " + e.getMessage());
        }
    }

    @Override
    public void delete(GroupSchedule gs) {
        String sql = "DELETE FROM group_schedule WHERE schedule_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, gs.getScheduleId());
            ps.executeUpdate();
            System.out.println("✅ Xóa lịch thành công.");
        } catch (SQLException e) {
            System.out.println("❌ Lỗi xóa lịch: " + e.getMessage());
        }
    }

    @Override
    public GroupSchedule selectById(int id) {
        String sql = "SELECT * FROM group_schedule WHERE schedule_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new GroupSchedule(
                        rs.getInt("schedule_id"),
                        rs.getInt("group_id"),
                        rs.getInt("course_id"),
                        rs.getInt("teacher_id"),
                        rs.getString("day_of_week"),
                        rs.getTime("start_time"),
                        rs.getTime("end_time"),
                        rs.getString("room"),
                        rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi lấy lịch: " + e.getMessage());
        }
        return null;
    }

    @Override
    public GroupSchedule selectByName(String name) {
        return null;
    }

    @Override
    public ArrayList<GroupSchedule> selectAll() {
        ArrayList<GroupSchedule> list = new ArrayList<>();
        String sql = "SELECT * FROM group_schedule";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new GroupSchedule(
                        rs.getInt("schedule_id"),
                        rs.getInt("group_id"),
                        rs.getInt("course_id"),
                        rs.getInt("teacher_id"),
                        rs.getString("day_of_week"),
                        rs.getTime("start_time"),
                        rs.getTime("end_time"),
                        rs.getString("room"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi lấy danh sách lịch: " + e.getMessage());
        }
        return list;
    }

    @Override
    public ArrayList<GroupSchedule> selectByCondition(String cond) {
        return new ArrayList<>();
    }
    public List<ClassSchedule> selectByStudent(int studentId) {
        List<ClassSchedule> scheduleList = new ArrayList<>();
        String sql = """
        SELECT gs.schedule_id,
               c.course_name,
               gs.day_of_week,
               CONCAT(
                 TIME_FORMAT(gs.start_time, '%H:%i'),
                 '-', 
                 TIME_FORMAT(gs.end_time,   '%H:%i')
               ) AS timeslot,
               gs.room,
               t.name AS teacher_name
          FROM students s
          JOIN group_schedule gs ON s.group_id = gs.group_id
          JOIN courses c        ON gs.course_id = c.course_id
          JOIN teachers t       ON gs.teacher_id = t.teacher_id
         WHERE s.student_id = ?
         ORDER BY
           FIELD(gs.day_of_week, 'MON','TUE','WED','THU','FRI','SAT','SUN'),
           gs.start_time
        """;


        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    scheduleList.add(new ClassSchedule(
                            rs.getInt("schedule_id"),
                            rs.getString("course_name"),
                            rs.getString("day_of_week"),
                            rs.getString("timeslot"),
                            rs.getString("room"),
                            rs.getString("teacher_name")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi lấy lịch học sinh viên: " + e.getMessage());
        }
        return scheduleList;
    }
    public List<TeacherSchedule> selectByTeacher(int teacherId) {
        List<TeacherSchedule> list = new ArrayList<>();
        // Lệnh ORDER BY đảm bảo tuần tự: MON→TUE→…→SUN, rồi trong ngày theo start_time
        String sql = """
        SELECT gs.schedule_id,
               cg.group_code,
               c.course_name,
               gs.day_of_week,
               CONCAT(
                 TIME_FORMAT(gs.start_time, '%H:%i'),
                 '-', 
                 TIME_FORMAT(gs.end_time,   '%H:%i')
               ) AS timeslot,
               gs.room
          FROM group_schedule gs
          JOIN class_groups   cg ON gs.group_id   = cg.group_id
          JOIN courses        c  ON gs.course_id  = c.course_id
         WHERE gs.teacher_id = ?
         ORDER BY
           FIELD(gs.day_of_week, 'MON','TUE','WED','THU','FRI','SAT','SUN'),
           gs.start_time
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teacherId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new TeacherSchedule(
                            rs.getInt("schedule_id"),
                            rs.getString("group_code"),
                            rs.getString("course_name"),
                            rs.getString("day_of_week"),
                            rs.getString("timeslot"),
                            rs.getString("room")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi lấy lịch dạy của giảng viên: " + e.getMessage());
        }
        return list;
    }
}
