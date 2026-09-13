package frontend;

import backend.BookingDAO;
import backend.RideDAO;
import model.Ride;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchRideFrame extends JFrame {

    private JComboBox<String> pickupPointBox = new JComboBox<>(new String[]{
            "Resource Block", "Research Square", "Central Complex", "Cafe", "Canteen",
            "Open Base", "Divisional Block A", "Divisional Block B", "Civil Block",
            "DC", "Boys Hostel", "Girls Hostel", "Main Gate"
    });
    private JList<Ride> resultsList = new JList<>();
    private JTextField dropPointField = new JTextField(15);
    private JSpinner seatsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

    private User currentUser;
    private RideDAO rideDAO = new RideDAO();
    private BookingDAO bookingDAO = new BookingDAO();
    private List<Ride> currentResults;

    public SearchRideFrame(User currentUser) {
        this.currentUser = currentUser;

        setTitle("Search & Book a Ride");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // -- top: pickup point search bar --
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Pickup Point:"));
        topPanel.add(pickupPointBox);
        JButton searchBtn = new JButton("Search");
        topPanel.add(searchBtn);
        add(topPanel, BorderLayout.NORTH);

        // -- center: results list --
        resultsList.setVisibleRowCount(8);
        add(new JScrollPane(resultsList), BorderLayout.CENTER);

        // -- bottom: booking controls --
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(new JLabel("Your Drop Point:"));
        bottomPanel.add(dropPointField);
        bottomPanel.add(new JLabel("Seats:"));
        bottomPanel.add(seatsSpinner);
        JButton bookBtn = new JButton("Book Selected Ride");
        bottomPanel.add(bookBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> handleSearch());
        bookBtn.addActionListener(e -> handleBook());
    }

    private void handleSearch() {
        String pickupPoint = (String) pickupPointBox.getSelectedItem();
        currentResults = rideDAO.searchRides(pickupPoint);

        resultsList.setListData(currentResults.toArray(new Ride[0]));

        if (currentResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No rides found for this pickup point.");
        }
    }

    private void handleBook() {
        int selectedIndex = resultsList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a ride from the list first.");
            return;
        }

        String dropPoint = dropPointField.getText().trim();
        if (dropPoint.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your drop point.");
            return;
        }

        int seats = (Integer) seatsSpinner.getValue();
        Ride selectedRide = currentResults.get(selectedIndex);

        boolean success = bookingDAO.bookRide(
                selectedRide.getRideId(), currentUser.getUserId(), dropPoint, seats
        );

        if (success) {
            JOptionPane.showMessageDialog(this, "Booking confirmed!");
            handleSearch(); // refresh the list so seat counts update
        } else {
            JOptionPane.showMessageDialog(this,
                    "Booking failed. Not enough seats, or you can't book your own ride.");
        }
    }
}