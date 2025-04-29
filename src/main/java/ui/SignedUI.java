package ui;
import dao.AdminDAO;
import dao.StudentDAO;
import model.Student;
import java.util.List;
import java.util.Scanner;

public class SignedUI {
    private static Scanner scanner = new Scanner(System.in);
    public static void SignedMananger() throws Exception {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Vui lòng chọn phương thức đăng nhập:");
            System.out.println("1. Đăng nhập cho admin");
            System.out.println("2. Đăng nhập cho teacher");
            System.out.println("3. Đăng nhập cho student");
            System.out.println("Lựa chọn của bạn là:");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Lựa chọn không hợp lệ.");
                continue;
            }
            switch (choice) {
                case 1:
                    signedAdmin();
                    break;
                case 2:
                    signedTeacher();
                    break;
                case 3:
                    signedStudent();
                    break;
                default:
                    System.out.println("Vui lòng chọn lại");
                    break;
            }
        }
    }
    public static String[] getUserCredentials(){
        System.out.print("Tài khoản: ");
        String userInput = scanner.nextLine().toLowerCase().trim();
        System.out.print("Mật khẩu: ");
        String password = scanner.nextLine().trim();
        return new String[]{userInput, password};
    }
    private static void signedAdmin() throws Exception {
        AdminDAO adminDAO = new AdminDAO();
        String[] account = getUserCredentials();
        adminDAO.getCheckAdmin(account[0], account[1]);
    }
    private static void signedTeacher(){
        String[] account = getUserCredentials();
    }
    private static void signedStudent(){
        StudentDAO studentDAO = new StudentDAO();
        String[] account = getUserCredentials();
        studentDAO.getCheckStudent(account[0], account[1]);
    }
}
