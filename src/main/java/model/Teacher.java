package model;

import java.sql.Timestamp;

public class Teacher {
    private int teacherId;
    private int userId;
    private String username;
    private String password;
    private String name;
    private String email;
    private Timestamp createdAt;

    public Teacher() {}

    /** Dùng khi load từ DB (full) */
    public Teacher(int teacherId, int userId,
                   String username, String password,
                   String name, String email,
                   Timestamp createdAt) {
        this.teacherId = teacherId;
        this.userId      = userId;
        this.username    = username;
        this.password    = password;
        this.name        = name;
        this.email       = email;
        this.createdAt   = createdAt;
    }

    /** Dùng khi add mới (chưa có IDs) */
    public Teacher(String username, String password,
                   String name, String email,
                   Timestamp createdAt) {
        this.username  = username;
        this.password  = password;
        this.name      = name;
        this.email     = email;
        this.createdAt = createdAt;
    }

    // --- Getters & Setters ---
    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return String.format("ID=%d, username=%s, name=%s, email=%s, createdAt=%s",
                teacherId, username, name, email,
                createdAt!=null?createdAt.toString():"");
    }
}
