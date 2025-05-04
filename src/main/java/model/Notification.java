package model;

import java.sql.Timestamp;

public class Notification {
    private int notificationId;
    private int senderId;        // admin_id
    private String targetType;   // "all", "teacher", "student"
    private Integer targetId;    // teacher_id hoặc student_id, hoặc null
    private String message;
    private Timestamp createdAt;

    public Notification() {}

    // Constructor thêm mới
    public Notification(int senderId, String targetType, Integer targetId, String message) {
        this.senderId   = senderId;
        this.targetType = targetType;
        this.targetId   = targetId;
        this.message    = message;
    }
    // Constructor đầy đủ khi load từ DB
    public Notification(int notificationId, int senderId, String targetType,
                        Integer targetId, String message, Timestamp createdAt) {
        this.notificationId = notificationId;
        this.senderId       = senderId;
        this.targetType     = targetType;
        this.targetId       = targetId;
        this.message        = message;
        this.createdAt      = createdAt;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    @Override
    public String toString() {
        return String.format("ID=%d | Sender(Admin)=%d | Target=%s/%s | Msg=%s | At=%s",
                notificationId,
                senderId,
                targetType,
                (targetId!=null? targetId.toString() : "null"),
                message,
                createdAt!=null? createdAt.toString() : ""
        );
    }
}
