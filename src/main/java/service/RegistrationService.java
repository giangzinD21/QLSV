package service;

import dao.RegistrationDAO;
import model.Registration;

import java.util.List;

public class RegistrationService {
    private RegistrationDAO dao = new RegistrationDAO();

    public List<Registration> getByStudentId(int studentId) {
        // Tạo đối tượng Registration để truyền vào phương thức selectByCondition
        Registration condition = new Registration(0, studentId, 0); // studentId được truyền vào, registrationId và classId để mặc định là 0
        return dao.selectByCondition(condition); // Gọi phương thức selectByCondition để lấy các đăng ký theo studentId
    }
}
