package model;

import java.sql.Timestamp;

public class Admin {
    private int adminId, userId;
    private String name, username, password, email;
    private boolean superAdmin;
    private Timestamp createdAt;
    public Admin() {}
    public Admin(int userId, int adminId, String username, String password, String name, boolean superAdmin, String email, Timestamp createdAt ) {
        this.userId = userId;
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.superAdmin = superAdmin;
        this.email = email;
        this.createdAt = createdAt;
    }
    public Admin(int userId, int adminId, String username, String password, String name, boolean superAdmin, String email) {
        this.userId = userId;
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.superAdmin = superAdmin;
        this.email = email;
    }
    public Admin(int adminId, String username, String password, String name, String email, boolean superAdmin) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.superAdmin = superAdmin;
    }
    public Admin(int adminId, String username, String password, String name, boolean superAdmin) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.superAdmin = superAdmin;
    }
    public Admin(int adminId, String name, boolean superAdmin) {
        this.adminId = adminId;
        this.username = username;
        this.name = name;
        this.superAdmin = superAdmin;
    }
    public Admin(String username, String password, String name, boolean superAdmin, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.superAdmin = superAdmin;
        this.email = email;
    }
    public int getAdminId() {
        return adminId;
    }
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean isSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        this.superAdmin = superAdmin;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    @Override
    public String toString() {
        return String.format("ID=%d | %s | %s | %s | %s | %s | %s",
                adminId, username, name, email, password, superAdmin, createdAt);
    }
}
