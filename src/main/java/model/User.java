package model;

import java.sql.Timestamp;

public class User {
    private int userId;
    private String username;
    private String password;
    private int roleId;
    private String email;
    private Timestamp createdAt;

    public User() {
    }

    public User(int userId, String username, String password, int roleId, String email, Timestamp createdAt) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.email = email;
        this.createdAt = createdAt;
    }

    public User(int userId, String username, String password, int roleId, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.email = email;
    }
    public User(String username, String password, int roleId, String email, Timestamp createdAt) {
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.email = email;
        this.createdAt = createdAt;
    }
    public User(String username, String password, int roleId, String email) {
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.email = email;
    }

    // Getters và Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
