package service;

import dao.ClassDAO;
import dao.InterfaceDAO;
import model.Class;

import java.util.ArrayList;

public class ClassService {
    private InterfaceDAO<Class> classDAO = new ClassDAO();

    public ArrayList<Class> getAllClasses() {
        return classDAO.selectAll();
    }

    public void createClass(String classId, String courseId, String teacherId, int maxStudents) {
        Class c = new Class(classId, courseId, teacherId, maxStudents);
        classDAO.add(c);
    }

    public void removeClass(String classId) {
        Class c = new Class(classId, null, null, 0);
        classDAO.delete(c);
    }

    public void updateClass(String classId, String courseId, String teacherId, int maxStudents) {
        Class c = new Class(classId, courseId, teacherId, maxStudents);
        classDAO.update(c);
    }

    public Class getClassById(String classId) {
        return classDAO.selectById(Integer.parseInt(classId));
    }

    public ArrayList<Class> getClassesByTeacherId(String teacherId) {
        return classDAO.selectByCondition(teacherId);
    }

    public Class getClassByName(String name) {
        return classDAO.selectByName(name);
    }
}
