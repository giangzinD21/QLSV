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

    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return String.format("Registration [ID=%d, StudentID=%d, ClassID=%d]", registrationId, studentId, classId);
    }
}
