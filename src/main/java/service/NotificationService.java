package service;

import dao.NotificationDAO;
import model.Notification;

import java.util.ArrayList;

public class NotificationService {
    private final NotificationDAO dao = new NotificationDAO();

    public void addNotification(int senderId, String senderRole, String message) {
        Notification noti = new Notification(senderId, senderRole, message);
        dao.add(noti);
    }

    public void updateNotification(int id, String newMessage) {
        Notification noti = new Notification(id);
        noti.setMessage(newMessage);
        dao.update(noti);
    }

    public void deleteNotification(int id) {
        dao.delete(new Notification(id));
    }

    public Notification getNotificationById(int id) {
        return dao.selectById(new Notification(id));
    }

    public ArrayList<Notification> getAllNotifications() {
        return dao.selectAll();
    }

    public ArrayList<Notification> getNotificationsBySender(int senderId, String senderRole) {
        Notification condition = new Notification(senderId, senderRole, "");
        return dao.selectByCondition(condition);
    }
}
