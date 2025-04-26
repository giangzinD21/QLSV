package model;

public class Grade {
    private String courseName;
    private int credits;
    private float grade;

    public Grade(String courseName, int credits, float grade) {
        this.courseName = courseName;
        this.credits = credits;
        this.grade = grade;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }
}
