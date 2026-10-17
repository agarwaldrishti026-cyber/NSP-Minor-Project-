package dao;

import database.DBConnection;
import model.Registration;
import java.sql.*;
import java.util.ArrayList;

public class RegistrationDAO {

    public int saveRegistrationAndGetId(Registration r) {
        String sql = "INSERT INTO registrations " +
                "(student_name,roll_number,department,year,event_type,event_name,event_date,mobile,email,registration_password) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setValues(ps, r);
            ps.setString(10, r.getRegistrationPassword());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void setValues(PreparedStatement ps, Registration r) throws SQLException {
        ps.setString(1, r.getStudentName());
        ps.setString(2, r.getRollNumber());
        ps.setString(3, r.getDepartment());
        ps.setString(4, r.getYear());
        ps.setString(5, r.getEventType());
        ps.setString(6, r.getEventName());
        ps.setDate(7, r.getEventDate());
        ps.setString(8, r.getMobile());
        ps.setString(9, r.getEmail());
    }

    public ArrayList<Registration> getAllRegistrations() {
        ArrayList<Registration> list = new ArrayList<>();
        String sql = "SELECT * FROM registrations ORDER BY registration_id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public ArrayList<Registration> searchRegistration(String rollNumber) {
        ArrayList<Registration> list = new ArrayList<>();
        String sql = "SELECT * FROM registrations WHERE roll_number LIKE ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + rollNumber + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Registration getUserRegistration(int id, String password) {
        String sql = "SELECT * FROM registrations WHERE registration_id=? AND registration_password=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    public boolean updateRegistration(Registration r) {
        String sql = "UPDATE registrations SET student_name=?,roll_number=?,department=?,year=?,event_type=?,event_name=?,event_date=?,mobile=?,email=? WHERE registration_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            setValues(ps, r);
            ps.setInt(10, r.getRegistrationId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean updateUserRegistration(Registration r, String password) {
        String sql = "UPDATE registrations SET student_name=?,roll_number=?,department=?,year=?,event_type=?,event_name=?,event_date=?,mobile=?,email=? WHERE registration_id=? AND registration_password=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            setValues(ps, r);
            ps.setInt(10, r.getRegistrationId());
            ps.setString(11, password);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteRegistration(int id) {
        String sql = "DELETE FROM registrations WHERE registration_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteUserRegistration(int id, String password) {
        String sql = "DELETE FROM registrations WHERE registration_id=? AND registration_password=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, password);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private Registration map(ResultSet rs) throws SQLException {
        Registration r = new Registration();
        r.setRegistrationId(rs.getInt("registration_id"));
        r.setStudentName(rs.getString("student_name"));
        r.setRollNumber(rs.getString("roll_number"));
        r.setDepartment(rs.getString("department"));
        r.setYear(rs.getString("year"));
        r.setEventType(rs.getString("event_type"));
        r.setEventName(rs.getString("event_name"));
        r.setEventDate(rs.getDate("event_date"));
        r.setMobile(rs.getString("mobile"));
        r.setEmail(rs.getString("email"));
        r.setRegistrationPassword(rs.getString("registration_password"));
        return r;
    }
}
