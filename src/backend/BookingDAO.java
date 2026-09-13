package backend;

import java.sql.*;

public class BookingDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/vehicle_pool?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Risaf@123";

    public boolean bookRide(int rideId, int riderId, String dropPoint, int seatsRequested) {
        String checkSql  = "SELECT available_seats, driver_id FROM rides WHERE ride_id = ? FOR UPDATE";
        String updateSql = "UPDATE rides SET available_seats = available_seats - ? WHERE ride_id = ?";
        String insertSql = "INSERT INTO bookings (ride_id, rider_id, drop_point, seats_booked) VALUES (?, ?, ?, ?)";

        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
            con.setAutoCommit(false); // start transaction

            int currentSeats;
            int driverId;
            try (PreparedStatement checkPs = con.prepareStatement(checkSql)) {
                checkPs.setInt(1, rideId);
                ResultSet rs = checkPs.executeQuery();
                if (!rs.next()) {
                    con.rollback();
                    return false; // ride doesn't exist
                }
                currentSeats = rs.getInt("available_seats");
                driverId = rs.getInt("driver_id");
            }

            if (driverId == riderId) {
                con.rollback();
                return false; // can't book your own ride
            }

            if (currentSeats < seatsRequested) {
                con.rollback();
                return false; // not enough seats
            }

            try (PreparedStatement updatePs = con.prepareStatement(updateSql)) {
                updatePs.setInt(1, seatsRequested);
                updatePs.setInt(2, rideId);
                updatePs.executeUpdate();
            }

            try (PreparedStatement insertPs = con.prepareStatement(insertSql)) {
                insertPs.setInt(1, rideId);
                insertPs.setInt(2, riderId);
                insertPs.setString(3, dropPoint);
                insertPs.setInt(4, seatsRequested);
                insertPs.executeUpdate();
            }

            con.commit(); // all steps succeeded, save permanently
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            return false;
        } finally {
            if (con != null) {
                try { con.setAutoCommit(true); con.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }
    }
}