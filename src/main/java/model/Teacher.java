package model;

public class Teacher {
    private int teacherId;
    private String name;
    private String email;
    private String password;

    public Teacher() {
    }

    public Teacher(int teacherId, String name, String email, String password) {
        this.teacherId = teacherId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Teacher(int teacherId) {
        this.teacherId = teacherId;
    }

    public Teacher(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +

                '}';
    }
}
