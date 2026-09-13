package frontend;

import backend.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;

public class DriverDetailsFrame extends JFrame {

    private JTextField dobField = new JTextField(15);       // format: YYYY-MM-DD
    private JTextField licenseField = new JTextField(15);
    private User currentUser;
    private UserDAO userDAO = new UserDAO();

    public DriverDetailsFrame(User currentUser) {
        this.currentUser = currentUser;

        setTitle("Driver Details Required");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));
        panel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        panel.add(dobField);
        panel.add(new JLabel("License Number:"));
        panel.add(licenseField);

        JButton saveBtn = new JButton("Save & Continue");
        panel.add(new JLabel(""));
        panel.add(saveBtn);

        saveBtn.addActionListener(e -> handleSave());

        add(panel);
    }

    private void handleSave() {
        String dob = dobField.getText().trim();
        String license = licenseField.getText().trim();

        if (dob.isEmpty() || license.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Both fields are required.");
            return;
        }

        boolean saved = userDAO.saveDriverDetails(currentUser.getUserId(), dob, license);
        if (!saved) {
            JOptionPane.showMessageDialog(this, "Failed to save details. Check date format.");
            return;
        }

        // Re-fetch the user so currentUser now has the updated DOB/license in memory
        User updatedUser = new User(
                currentUser.getUserId(), currentUser.getName(), currentUser.getEmail(),
                currentUser.getRole(), dob, license
        );

        if (userDAO.isEligibleToDrive(updatedUser)) {
            JOptionPane.showMessageDialog(this, "You're eligible! Opening Post Ride form...");
            new PostRideFrame(updatedUser).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Sorry, you must be 18 or older to post a ride.");
            dispose();
        }
    }
}