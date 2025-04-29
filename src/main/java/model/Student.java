package model;

public class Student {
    private int studentId;
    private String name;
    private String studentCode;
    private String email;
    private String password;


    public Student() {
    }

    public Student(int studentId, String name, String studentCode, String email, String password) {
        this.studentId = studentId;
        this.name = name;
        this.studentCode = studentCode;
        this.email = email;
        this.password = password;
    }

    public Student(int studentId) {
        this.studentId = studentId;

    }

    public Student(String name, String studentCode, String email, String password) {
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", studentCode='" + studentCode + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +

                '}';
    }
}
