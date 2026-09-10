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

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt()); // ← ADD THIS LINE

        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, hashedPassword);   // ← CHANGED: was "password", now "hashedPassword"
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
                return new User(
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("role")
                );
            }
        }
        return null; // wrong password, or no such email

    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}



















}



