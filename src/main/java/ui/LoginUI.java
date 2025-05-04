package ui;
import dao.AdminDAO;
import dao.StudentDAO;
import model.User;
// Ensure no conflicting imports of User from other packages exist
import service.CheckUserService;

import java.util.Scanner;

public class LoginUI {
    public static void LoginUser() throws Exception {
        Scanner sc = new Scanner(System.in);
        CheckUserService checkUser= new CheckUserService();
        User loggedInUser = null;
        System.out.println("\n------------- Đăng nhập -------------");
        boolean continueLogin = true;

        while (continueLogin) {
            System.out.print("Tên đăng nhập: ");
            String username = sc.nextLine();

            System.out.print("Mật khẩu: ");
            String password = sc.nextLine();

            try {
                loggedInUser = checkUser.login(username, password);
                if(loggedInUser != null) {
                    System.out.println("Đăng nhập thành công!");
                    CheckRole(loggedInUser);
                }
                else{
                    System.out.println("Đăng nhập thất bại. Tên đăng nhập hoặc mật khẩu không đúng.");
                    System.out.print("Bạn có muốn thử lại không? (y/n): ");
                    String input = sc.nextLine();
                    if (!input.equalsIgnoreCase("y")) {
                        continueLogin = false; // Người dùng không muốn thử lại, thoát vòng lặp
                    }
                }
            } catch( Exception e) {
                System.out.println(e.getMessage());
            }
        }
        sc.close(); // sau vòng lặp
    }


    private static void CheckRole(User loggedInUser) throws Exception {
        switch (loggedInUser.getRoleId()) {
            case 1: //super_admin
                System.out.println(">> Chào mừng super admin !");
                SuperAdminUI.showMenu(loggedInUser.getUserId());
                break;
            case 2: //admin
                System.out.println("Chào mừng admin " + loggedInUser.getUsername() + "!");
                AdminUI.showMenu(loggedInUser.getUserId());
                break;
            case 3: //teacher
                System.out.println("Chào mừng giảng viên " + loggedInUser.getUsername() + "!");
                TeacherUI.showMenu(loggedInUser.getUserId());
                break;
            case 4: //student
                System.out.println("Chào mừng sinh viên " + loggedInUser.getUsername() + "!");
                StudentUI.showMenu(loggedInUser.getUserId());
                break;
            default:
                System.out.println("Không tìm thấy vai trò của user. Vui lòng đăng nhập lại");
        }
    }
}
