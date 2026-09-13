package backend;

import model.User;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/vehicle_pool?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Risaf@123";

    public boolean registerUser(String name, String email, String password, String phone, String role, String department) {

        String sql = "INSERT INTO users (name,email,password,phone,role,department) VALUES (?,?,?,?,?,?)";

        try (Connection con = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt()); 

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, hashedPassword); 
            ps.setString(4, phone);
            ps.setString(5, role);
            ps.setString(6, department);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection con = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                if (BCrypt.checkpw(password, storedHash)) {
                    // UPDATED: Now uses the 6-argument constructor
                    return new User(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("date_of_birth"),
                        rs.getString("license_number")
                    );
                }
            }
            return null; // wrong password, or no such email

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean saveDriverDetails(int userId, String dob, String license) {
        String sql = "UPDATE users SET date_of_birth = ?, license_number = ? WHERE user_id = ?";

        try (Connection con = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dob);
            ps.setString(2, license);
            ps.setInt(3, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isEligibleToDrive(User user) {
        if (user.getDateOfBirth() == null || user.getLicenseNumber() == null
            || user.getLicenseNumber().trim().isEmpty()) {
            return false;
        }

        java.time.LocalDate dob = java.time.LocalDate.parse(user.getDateOfBirth());
        int age = java.time.Period.between(dob, java.time.LocalDate.now()).getYears();

        return age >= 18;
    }
}