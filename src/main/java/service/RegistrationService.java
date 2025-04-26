package service;

import dao.RegistrationDAO;
import model.ClassSchedule;
import model.Registration;

import java.util.List;

public class RegistrationService {
    private final RegistrationDAO registrationDAO = new RegistrationDAO();

    public boolean registerClass(int studentId, int classId) {
        if (registrationDAO.exists(studentId, classId)) {
            System.out.println("Bạn đã đăng ký lớp học này rồi.");
            return false;
        }

        int count = registrationDAO.countStudentsInClass(classId);
        int max = registrationDAO.getMaxStudents(classId);

        if (count >= max) {
            System.out.println("Lớp học đã đầy.");
            return false;
        }

        Registration registration = new Registration(0, studentId, classId);
        registrationDAO.add(registration);
        System.out.println("Đăng ký thành công.");
        return true;
    }

    public void viewSchedule(int studentId, String semester) {
        List<ClassSchedule> schedules = registrationDAO.getScheduleByStudent(studentId, semester);
        if (schedules.isEmpty()) {
            System.out.println("Không có lớp học nào trong học kỳ này.");
            return;
        }

        System.out.println("----- Thời khóa biểu -----");
        for (ClassSchedule schedule : schedules) {
            System.out.println(schedule);
        }
    }
}
