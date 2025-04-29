package model;

import java.sql.Timestamp;

public class Notification {
    private int id;
    private int senderId;
    private String senderRole;
    private String message;
    private Timestamp createdAt;

    public Notification(int id, int senderId, String senderRole, String message, Timestamp createdAt) {
        this.id = id;
        this.senderId = senderId;
        this.senderRole = senderRole;
        this.message = message;
        this.createdAt = createdAt;
    }

    public Notification(int senderId, String senderRole, String message) {
        this.senderId = senderId;
        this.senderRole = senderRole;
        this.message = message;
    }

    public Notification(int id) {
        this.id = id;
    }

    // Getters & Setters
    public int getId() { return id; }
    public int getSenderId() { return senderId; }
    public String getSenderRole() { return senderRole; }
    public String getMessage() { return message; }
    public Timestamp getCreatedAt() { return createdAt; }

    public void setId(int id) { this.id = id; }
    public void setSenderId(int senderId) { this.senderId = senderId; }
    public void setSenderRole(String senderRole) { this.senderRole = senderRole; }
    public void setMessage(String message) { this.message = message; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "[" + createdAt + "] " + senderRole.toUpperCase() +
                " (ID: " + senderId + "): " + message;
    }
}
