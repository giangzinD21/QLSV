package service;

import dao.StudentDAO;
import model.Student;

import java.util.List;

public class StudentService {
    private StudentDAO studentDAO = new StudentDAO();

    public void registerStudent(String name, String student_code, String email, String password) {
        Student student = new Student();
        studentDAO.add(student);
        System.out.println("Đã đăng ký sinh viên: " + name);
    }

    public void listStudents() {
        List<Student> students = studentDAO.selectAll();
        if (students.isEmpty()) {
            System.out.println("Không có sinh viên nào!");
        } else {
            students.forEach(System.out::println);
        }
    }
}
