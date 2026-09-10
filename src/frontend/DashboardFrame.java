package frontend;

import model.User;
import javax.swing.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame(User currentUser) {
        setTitle("Dashboard - " + currentUser.getName());
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new java.awt.GridLayout(3, 1, 10, 10));

        JLabel welcome = new JLabel("Welcome, " + currentUser.getName() + "!", SwingConstants.CENTER);
        JButton postRideBtn = new JButton("Post a Ride");
        JButton searchRideBtn = new JButton("Search / Book a Ride");

        panel.add(welcome);
        panel.add(postRideBtn);
        panel.add(searchRideBtn);

        // We'll wire these buttons up once PostRideFrame / SearchRideFrame exist
        add(panel);
    }
}