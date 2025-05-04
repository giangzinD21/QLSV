package service;

import dao.UserDAO;
import model.User;

import java.util.Objects;

public class CheckUserService {
    private final UserDAO userDAO = new UserDAO();

    public User login(String username, String password) {
        User user = userDAO.findUserByUsername(username);
        if (user == null) {
            System.out.println("Tài khoản không tồn tại.");
            return null;
        }

        if (!Objects.equals(user.getPassword(), password)) {
            System.out.println("Sai mật khẩu.");
            return null;
        }

        System.out.println("Đăng nhập thành công với vai trò: " + (
                user.getRoleId() == 1 ? "Siêu quản lý" :
                        user.getRoleId() == 2 ? "Quản lý" :
                                user.getRoleId() == 3 ? "Giảng viên" :
                                        user.getRoleId() == 4 ? "Sinh viên" :
                                        "Không xác định"
        ));
        return user;
    }
}
