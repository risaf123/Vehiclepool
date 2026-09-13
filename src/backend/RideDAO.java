package backend;

import model.Ride;
import java.util.List;
import java.util.ArrayList;
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

    public List<Ride> searchRides(String pickupPoint) {
        List<Ride> rides = new ArrayList<>();

        String sql = "SELECT r.ride_id, r.driver_id, u.name AS driver_name, r.vehicle_id, v.vehicle_no, " +
                     "r.source, r.destination, r.pickup_point, r.ride_date, r.ride_time, " +
                     "r.available_seats, r.price_per_seat " +
                     "FROM rides r " +
                     "JOIN users u ON r.driver_id = u.user_id " +
                     "JOIN vehicles v ON r.vehicle_id = v.vehicle_id " +
                     "WHERE r.pickup_point = ? AND r.available_seats > 0 AND r.status = 'SCHEDULED'";

        try (Connection con = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pickupPoint);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                rides.add(new Ride(
                    rs.getInt("ride_id"),
                    rs.getInt("driver_id"),
                    rs.getString("driver_name"),
                    rs.getInt("vehicle_id"),
                    rs.getString("vehicle_no"),
                    rs.getString("source"),
                    rs.getString("destination"),
                    rs.getString("pickup_point"),
                    rs.getString("ride_date"),
                    rs.getString("ride_time"),
                    rs.getInt("available_seats"),
                    rs.getDouble("price_per_seat")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rides;
    }
}