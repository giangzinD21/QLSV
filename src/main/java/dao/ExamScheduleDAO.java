package dao;

import model.ExamSchedule;
import model.ExamScheduleStudent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamScheduleDAO implements InterfaceDAO<ExamSchedule> {
    private final Connection conn = DatabaseConnection.getConnection();

    // Kiểm tra xung đột môn cho lớp, loại trừ chính exam này
    private boolean hasClassCourseConflict(int classId, int courseId,
                                           boolean isUpdate, int excludeExamId) {
        if (courseId <= 0) return false;
        String sql = """
       SELECT COUNT(*) 
         FROM exam_schedule
        WHERE group_id = ?
          AND course_id = ?
     """;
        if (isUpdate) {
            sql += " AND exam_id <> ?";
        }
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, classId);
            ps.setInt(2, courseId);
            if (isUpdate) {
                ps.setInt(3, excludeExamId);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi kiểm tra trùng môn thi cho lớp: " + e.getMessage());
        }
        return true;  // mặc định coi như trùng
    }
    // Kiểm tra trùng khung giờ cho cùng một lớp (loại trừ chính bản ghi nếu update)
    private boolean hasTimeConflictForClass(int classId, Date date,
                                            Time st, Time et,
                                            boolean isUpdate, int excludeExamId) {
        String sql = """
            SELECT COUNT(*) 
              FROM exam_schedule 
             WHERE group_id = ? 
               AND exam_date = ? 
               AND NOT (end_time <= ? OR start_time >= ?)
        """;
        if (isUpdate) {
            sql += " AND exam_id <> ?";
        }
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, classId);
            ps.setDate(2, date);
            ps.setTime(3, st);
            ps.setTime(4, et);
            if (isUpdate) {
                ps.setInt(5, excludeExamId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi kiểm tra trùng giờ thi cho lớp: " + e.getMessage());
        }
        return true;
    }
    // Kiểm tra trùng phòng thi (nếu bạn muốn đảm bảo phòng không trùng)
    private boolean hasRoomConflict(String location, Date date,
                                    Time st, Time et,
                                    boolean isUpdate, int excludeExamId) {
        if (location == null || location.isBlank()) return false;
        String sql = """
            SELECT COUNT(*) 
              FROM exam_schedule 
             WHERE location = ? 
               AND exam_date = ? 
               AND NOT (end_time <= ? OR start_time >= ?)
        """;
        if (isUpdate) {
            sql += " AND exam_id <> ?";
        }
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, location);
            ps.setDate(2, date);
            ps.setTime(3, st);
            ps.setTime(4, et);
            if (isUpdate) {
                ps.setInt(5, excludeExamId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi kiểm tra trùng phòng thi: " + e.getMessage());
        }
        return true;
    }
    @Override
    public void add(ExamSchedule ex) {
        // 1) kiểm tra lớp – môn thi
        if (hasClassCourseConflict(ex.getClassId(), ex.getCourseId(), false, ex.getExamId())) {
            System.out.println("❌ Lớp " + ex.getClassId() + " đã có lịch thi môn này.");
            return;
        }
        // 2) kiểm tra giờ thi trùng cho lớp
        if (hasTimeConflictForClass(ex.getClassId(), ex.getExamDate(), ex.getStartTime(), ex.getEndTime(), false, 0)) {
            System.out.println("❌ Lớp " + ex.getClassId() + " đã có ca thi trùng khung giờ.");
            return;
        }
        // 3) kiểm tra phòng thi
        if (hasRoomConflict(ex.getLocation(), ex.getExamDate(), ex.getStartTime(), ex.getEndTime(), false, 0)) {
            System.out.println("❌ Phòng " + ex.getLocation() + " đã được sử dụng cho ca thi khác trùng giờ.");
            return;
        }
        String sql = """
            INSERT INTO exam_schedule
              (group_id, course_id, exam_date, start_time, end_time, location)
            VALUES (?,?,?,?,?,?)
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ex.getClassId());
            ps.setInt(2, ex.getCourseId());
            ps.setDate(3, ex.getExamDate());
            ps.setTime(4, ex.getStartTime());
            ps.setTime(5, ex.getEndTime());
            ps.setString(6, ex.getLocation());
            ps.executeUpdate();
            System.out.println("✅ Thêm lịch thi thành công.");
        } catch (SQLException e) {
            System.out.println("❌ Lỗi thêm lịch thi: " + e.getMessage());
        }
    }

    @Override
    public void update(ExamSchedule ex) {
        // 1) kiểm tra lớp – môn thi (nếu muốn loại trừ chính exam này,
        //    bạn có thể thêm param excludeExamId giống phần giờ, nhưng đơn giản ở đây tạm dừng)
        if (hasClassCourseConflict(ex.getClassId(), ex.getCourseId(),true, ex.getExamId())) {
            System.out.println("❌ Lớp " + ex.getClassId() + " đã có lịch thi môn này.");
            return;
        }
        // 2) kiểm tra giờ thi trùng cho lớp (loại trừ chính exam_id)
        if (hasTimeConflictForClass(ex.getClassId(), ex.getExamDate(), ex.getStartTime(), ex.getEndTime(), true, ex.getExamId())) {
            System.out.println("❌ Lớp " + ex.getClassId() + " đã có ca thi trùng khung giờ.");
            return;
        }
        // 3) kiểm tra phòng thi
        if (hasRoomConflict(ex.getLocation(), ex.getExamDate(), ex.getStartTime(), ex.getEndTime(),true, ex.getExamId())) {
            System.out.println("❌ Phòng " + ex.getLocation() + " đã được sử dụng trùng giờ.");
            return;
        }
        String sql = """
            UPDATE exam_schedule
               SET group_id=?, course_id=?, exam_date=?, start_time=?, end_time=?, location=?
             WHERE exam_id=?
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ex.getClassId());
            ps.setInt(2, ex.getCourseId());
            ps.setDate(3, ex.getExamDate());
            ps.setTime(4, ex.getStartTime());
            ps.setTime(5, ex.getEndTime());
            ps.setString(6, ex.getLocation());
            ps.setInt(7, ex.getExamId());
            int updated = ps.executeUpdate();
            if (updated > 0) System.out.println("✅ Cập nhật lịch thi thành công.");
            else System.out.println("❌ Không tìm thấy exam_id để cập nhật.");
        } catch (SQLException e) {
            System.out.println("❌ Lỗi cập nhật lịch thi: " + e.getMessage());
        }
    }

    @Override
    public void delete(ExamSchedule ex) {
        String sql = "DELETE FROM exam_schedule WHERE exam_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ex.getExamId());
            int del = ps.executeUpdate();
            if (del > 0) System.out.println("✅ Xóa lịch thi thành công.");
            else System.out.println("❌ Không tìm thấy exam_id để xóa.");
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi xóa lịch thi: " + e.getMessage());
        }
    }

    @Override
    public ExamSchedule selectById(int id) {
        String sql = "SELECT * FROM exam_schedule WHERE exam_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ExamSchedule(
                        rs.getInt("exam_id"),
                        rs.getInt("group_id"),
                        rs.getInt("course_id"),
                        rs.getDate("exam_date"),
                        rs.getTime("start_time"),
                        rs.getTime("end_time"),
                        rs.getString("location"),
                        rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi lấy lịch thi: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<ExamSchedule> selectAll() {
        ArrayList<ExamSchedule> list = new ArrayList<>();
        String sql = "SELECT * FROM exam_schedule";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new ExamSchedule(
                        rs.getInt("exam_id"),
                        rs.getInt("group_id"),
                        rs.getInt("course_id"),
                        rs.getDate("exam_date"),
                        rs.getTime("start_time"),
                        rs.getTime("end_time"),
                        rs.getString("location"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi lấy danh sách lịch thi: " + e.getMessage());
        }
        return list;
    }

    @Override
    public ExamSchedule selectByName(String cond) { return null; }
    @Override
    public ArrayList<ExamSchedule> selectByCondition(String cond) { return new ArrayList<>(); }

    public List<ExamScheduleStudent> selectByGroup(int groupId) {
        List<ExamScheduleStudent> list = new ArrayList<>();
        String sql = """
            SELECT es.exam_id,
                   c.course_name,
                   es.exam_date,
                   CONCAT(
                     TIME_FORMAT(es.start_time, '%H:%i'),
                     '-', 
                     TIME_FORMAT(es.end_time,   '%H:%i')
                   ) AS timeslot,
                   es.location
              FROM exam_schedule es
              JOIN courses      c  ON es.course_id = c.course_id
             WHERE es.group_id = ?
             ORDER BY es.exam_date, es.start_time
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, groupId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ExamScheduleStudent(
                            rs.getInt("exam_id"),
                            rs.getString("course_name"),
                            rs.getDate("exam_date"),
                            rs.getString("timeslot"),
                            rs.getString("location")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi lấy lịch thi: " + e.getMessage());
        }
        return list;
    }
}
