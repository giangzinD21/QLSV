package model;

import java.sql.Timestamp;

public class ClassGroup {
    private int groupId;
    private String groupCode;
    private String groupName;
    private Timestamp createdAt;

    public ClassGroup() {}

    public ClassGroup(int groupId, String groupCode, String groupName, Timestamp createdAt) {
        this.groupId   = groupId;
        this.groupCode = groupCode;
        this.groupName = groupName;
        this.createdAt = createdAt;
    }

    public ClassGroup(String groupCode, String groupName) {
        this.groupCode = groupCode;
        this.groupName = groupName;
    }

    // getters/setters...
    public int getGroupId() { return groupId; }
    public void setGroupId(int id) { this.groupId = id; }

    public String getGroupCode() { return groupCode; }
    public void setGroupCode(String c) { this.groupCode = c; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String n) { this.groupName = n; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp t) { this.createdAt = t; }

    @Override
    public String toString() {
        return String.format("ID=%d | %s - %s | Created=%s",
                groupId, groupCode, groupName, createdAt);
    }
}
