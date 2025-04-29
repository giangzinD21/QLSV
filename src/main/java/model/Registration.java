package model;

public class Registration {
    private int registrationId;
    private int studentId;
    private int classId;

    public Registration(int registrationId, int studentId, int classId) {
        this.registrationId = registrationId;
        this.studentId = studentId;
        this.classId = classId;
    }

    public int getRegistrationId() { return registrationId; }
    public int getStudentId() { return studentId; }
    public int getClassId() { return classId; }
}
