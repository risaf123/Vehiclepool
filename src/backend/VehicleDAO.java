package backend;

import java.sql.*;

public class VehicleDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/vehicle_pool?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Risaf@123";

    /**
     * Returns the vehicle_id for this vehicle number if the user already owns it.
     * If not, inserts a new vehicle row and returns the new vehicle_id.
     * Returns -1 on failure.
     */
    public int findOrCreateVehicle(int userId, String vehicleNo, String vehicleType, int capacity) {
        String findSql = "SELECT vehicle_id FROM vehicles WHERE user_id = ? AND vehicle_no = ?";

        try (Connection con = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD)) {

            try (PreparedStatement findPs = con.prepareStatement(findSql)) {
                findPs.setInt(1, userId);
                findPs.setString(2, vehicleNo);
                ResultSet rs = findPs.executeQuery();
                if (rs.next()) {
                    return rs.getInt("vehicle_id"); // already exists, reuse it
                }
            }

            String insertSql = "INSERT INTO vehicles (user_id, vehicle_no, vehicle_type, capacity) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertPs = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insertPs.setInt(1, userId);
                insertPs.setString(2, vehicleNo);
                insertPs.setString(3, vehicleType);
                insertPs.setInt(4, capacity);
                insertPs.executeUpdate();

                ResultSet keys = insertPs.getGeneratedKeys();
                if (keys.next()) {
                    return keys.getInt(1); // newly created vehicle_id
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}