package frontend;

import backend.RideDAO;
import backend.VehicleDAO;
import model.User;

import javax.swing.*;
import java.awt.*;

public class PostRideFrame extends JFrame {

    private JTextField vehicleNoField = new JTextField(15);
    private JComboBox<String> vehicleTypeBox = new JComboBox<>(new String[]{"CAR", "BIKE", "VAN"});
    private JTextField capacityField = new JTextField(15);
    private JTextField sourceField = new JTextField(15);
    private JTextField destinationField = new JTextField(15);
    private JComboBox<String> pickupPointBox = new JComboBox<>(new String[]{
            "Resource Block", "Research Square", "Central Complex", "Cafe", "Canteen",
            "Open Base", "Divisional Block A", "Divisional Block B", "Civil Block",
            "DC", "Boys Hostel", "Girls Hostel", "Main Gate"
    });
    private JTextField dateField = new JTextField(15);   // YYYY-MM-DD
    private JTextField timeField = new JTextField(15);   // HH:MM
    private JTextField seatsField = new JTextField(15);
    private JTextField fareField = new JTextField(15);

    private User currentUser;
    private VehicleDAO vehicleDAO = new VehicleDAO();
    private RideDAO rideDAO = new RideDAO();

    public PostRideFrame(User currentUser) {
        this.currentUser = currentUser;

        setTitle("Post a Ride");
        setSize(420, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(11, 2, 6, 6));
        panel.add(new JLabel("Vehicle Number:")); panel.add(vehicleNoField);
        panel.add(new JLabel("Vehicle Type:"));   panel.add(vehicleTypeBox);
        panel.add(new JLabel("Capacity:"));       panel.add(capacityField);
        panel.add(new JLabel("Source:"));         panel.add(sourceField);
        panel.add(new JLabel("Destination:"));    panel.add(destinationField);
        panel.add(new JLabel("Pickup Point:"));   panel.add(pickupPointBox);
        panel.add(new JLabel("Date (YYYY-MM-DD):")); panel.add(dateField);
        panel.add(new JLabel("Time (HH:MM):"));   panel.add(timeField);
        panel.add(new JLabel("Available Seats:"));panel.add(seatsField);
        panel.add(new JLabel("Fare per Seat (Rs):"));panel.add(fareField);

        JButton postBtn = new JButton("Post Ride");
        panel.add(new JLabel(""));
        panel.add(postBtn);

        postBtn.addActionListener(e -> handlePostRide());

        add(panel);
    }

    private void handlePostRide() {
        try {
            String vehicleNo = vehicleNoField.getText().trim();
            String vehicleType = (String) vehicleTypeBox.getSelectedItem();
            int capacity = Integer.parseInt(capacityField.getText().trim());
            String source = sourceField.getText().trim();
            String destination = destinationField.getText().trim();
            String pickupPoint = (String) pickupPointBox.getSelectedItem();
            String date = dateField.getText().trim();
            String time = timeField.getText().trim();
            int seats = Integer.parseInt(seatsField.getText().trim());
            double fare = Double.parseDouble(fareField.getText().trim());

            if (vehicleNo.isEmpty() || source.isEmpty() || destination.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
                return;
            }

            if (seats > capacity) {
                JOptionPane.showMessageDialog(this, "Available seats cannot exceed vehicle capacity.");
                return;
            }

            if (seats <= 0 || capacity <= 0 || fare <= 0) {
                JOptionPane.showMessageDialog(this, "Seats, capacity, and fare must be greater than zero.");
                return;
            }

            java.time.LocalDate rideDate = java.time.LocalDate.parse(date);
            if (rideDate.isBefore(java.time.LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "Ride date cannot be in the past.");
                return;
            }

            int vehicleId = vehicleDAO.findOrCreateVehicle(currentUser.getUserId(), vehicleNo, vehicleType, capacity);
            if (vehicleId == -1) {
                JOptionPane.showMessageDialog(this, "Failed to save vehicle details.");
                return;
            }

            boolean posted = rideDAO.postRide(currentUser.getUserId(), vehicleId, source, destination,
                    pickupPoint, date, time, seats, fare);

            if (posted) {
                JOptionPane.showMessageDialog(this, "Ride posted successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to post ride.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Capacity, seats, and fare must be valid numbers.");
        } catch (java.time.format.DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Date must be in YYYY-MM-DD format.");
        }
    }
}