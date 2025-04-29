package dao;

import model.Registration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAO implements InterfaceDAO<Registration> {

    @Override
    public void add(Registration registration) {
        String sql = "INSERT INTO registrations (student_id, class_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, registration.getStudentId());
            stmt.setInt(2, registration.getClassId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Registration registration) {
        String sql = "UPDATE registrations SET student_id = ?, class_id = ? WHERE registration_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, registration.getStudentId());
            stmt.setInt(2, registration.getClassId());
            stmt.setInt(3, registration.getRegistrationId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Registration registration) {
        String sql = "DELETE FROM registrations WHERE registration_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, registration.getRegistrationId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Registration selectById(Registration registration) {
        String sql = "SELECT * FROM registrations WHERE registration_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, registration.getRegistrationId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Registration(
                        rs.getInt("registration_id"),
                        rs.getInt("student_id"),
                        rs.getInt("class_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Registration selectByName(String name) {
        // Assuming `name` is the student name
        String sql = "SELECT * FROM registrations WHERE student_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Registration(
                        rs.getInt("registration_id"),
                        rs.getInt("student_id"),
                        rs.getInt("class_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Registration> selectAll() {
        ArrayList<Registration> registrations = new ArrayList<>();
        String sql = "SELECT * FROM registrations";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                registrations.add(new Registration(
                        rs.getInt("registration_id"),
                        rs.getInt("student_id"),
                        rs.getInt("class_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrations;
    }

    @Override
    public ArrayList<Registration> selectByCondition(Registration registration) {
        ArrayList<Registration> registrations = new ArrayList<>();
        String sql = "SELECT * FROM registrations WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, registration.getStudentId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                registrations.add(new Registration(
                        rs.getInt("registration_id"),
                        rs.getInt("student_id"),
                        rs.getInt("class_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrations;
    }
}
