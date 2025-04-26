package service;

import dao.TeacherDAO;
import model.Teacher;

import java.util.List;

public class TeacherService {
    private TeacherDAO teacherDAO = new TeacherDAO();

    public void addTeacher(String name, String email) {
        Teacher teacher = new Teacher(name, email);
        teacherDAO.add(teacher);
        System.out.println("Đã thêm giảng viên: " + name);
    }

    public void listTeachers() {
        List<Teacher> teachers = teacherDAO.selectAll();
        if (teachers.isEmpty()) {
            System.out.println("Không có giảng viên nào!");
        } else {
            teachers.forEach(System.out::println);
        }
    }

    public void updateTeacher(int id, String newName, String newEmail) {
        Teacher teacher = teacherDAO.selectById(String.valueOf(id));
        if (teacher == null) {
            System.out.println("Không tìm thấy giảng viên.");
            return;
        }
        teacher.setName(newName);
        teacher.setEmail(newEmail);
        teacherDAO.update(teacher);
        System.out.println("Đã cập nhật thông tin giảng viên.");
    }

    public void deleteTeacher(int id) {
        Teacher teacher = teacherDAO.selectById(String.valueOf(id));
        if (teacher == null) {
            System.out.println("Không tìm thấy giảng viên.");
            return;
        }
        teacherDAO.delete(teacher);
        System.out.println("Đã xóa giảng viên.");
    }
}
