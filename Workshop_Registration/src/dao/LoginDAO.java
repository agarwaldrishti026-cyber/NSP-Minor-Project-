package dao;

import database.DBConnection;
import java.sql.*;

public class LoginDAO {
    public boolean login(String username, String password) {
        String sql = "SELECT * FROM admins WHERE username=? AND password=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        String check = "SELECT * FROM admins WHERE username=? AND password=?";
        String update = "UPDATE admins SET password=? WHERE username=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(check)) {
            ps.setString(1, username);
            ps.setString(2, oldPassword);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return false;
            }
            try (PreparedStatement up = con.prepareStatement(update)) {
                up.setString(1, newPassword);
                up.setString(2, username);
                return up.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
