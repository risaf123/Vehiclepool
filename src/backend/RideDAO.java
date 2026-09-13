package backend;

import java.sql.*;

public class RideDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/vehicle_pool?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Risaf@123";

    public boolean postRide(int driverId, int vehicleId, String source, String destination,
                             String pickupPoint, String rideDate, String rideTime,
                             int availableSeats, double pricePerSeat) {

        String sql = "INSERT INTO rides (driver_id, vehicle_id, source, destination, pickup_point, " +
                     "ride_date, ride_time, available_seats, price_per_seat) VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection con = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, driverId);
            ps.setInt(2, vehicleId);
            ps.setString(3, source);
            ps.setString(4, destination);
            ps.setString(5, pickupPoint);
            ps.setString(6, rideDate);
            ps.setString(7, rideTime);
            ps.setInt(8, availableSeats);
            ps.setDouble(9, pricePerSeat);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}