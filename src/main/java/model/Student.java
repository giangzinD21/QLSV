// model/Student.java
package model;

import java.sql.Timestamp;

public class Student {
    private int studentId;
    private int userId;
    private int groupId;
    private String username;
    private String password;
    private String name;
    private String studentCode;
    private String email;
    private Timestamp createdAt;

    public Student() {}

    /** Dùng khi load từ DB */
    public Student(int studentId, int userId, int groupId,
                   String username, String password,
                   String name, String studentCode,
                   String email, Timestamp createdAt) {
        this.studentId   = studentId;
        this.userId      = userId;
        this.groupId     = groupId;
        this.username    = username;
        this.password    = password;
        this.name        = name;
        this.studentCode = studentCode;
        this.email       = email;
        this.createdAt   = createdAt;
    }

    /** Dùng khi thêm mới (chưa có IDs) */
    public Student(String username, String password,
                   String name, int groupId, String studentCode,
                   String email, Timestamp createdAt) {
        this.username    = username;
        this.password    = password;
        this.name        = name;
        this.groupId     = groupId;
        this.studentCode = studentCode;
        this.email       = email;
        this.createdAt   = createdAt;
    }

    // --- Getters & Setters ---
    public int getStudentId()      { return studentId; }
    public void setStudentId(int id){ this.studentId = id; }

    public int getUserId()         { return userId; }
    public void setUserId(int id)  { this.userId = id; }

    public String getUsername()    { return username; }
    public void setUsername(String u){ this.username = u; }

    public String getPassword()    { return password; }
    public void setPassword(String p){ this.password = p; }

    public String getName()        { return name; }
    public void setName(String n)  { this.name = n; }

    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String c){ this.studentCode = c; }

    public String getEmail()       { return email; }
    public void setEmail(String e) { this.email = e; }

    public Timestamp getCreatedAt(){ return createdAt; }
    public void setCreatedAt(Timestamp t){ this.createdAt = t; }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return String.format("ID=%d, username=%s, name=%s, code=%s, email=%s, createdAt=%s",
                studentId, username, name, studentCode, email,
                createdAt!=null?createdAt.toString():"");
    }
}
