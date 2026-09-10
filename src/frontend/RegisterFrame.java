package frontend;

import javax.swing.*;
import java.awt.*;
import backend.UserDAO;

public class RegisterFrame extends JFrame {

    private JTextField nameField = new JTextField(20);
    private JTextField emailField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);
    private JTextField phoneField = new JTextField(20);
    private JTextField roleField = new JTextField(20);
    private JTextField deptField = new JTextField(20);

    private UserDAO userDAO = new UserDAO();

    public RegisterFrame() {

        setTitle("Register - Vehicle Pool System");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Updated rows from 7 to 8 for the navigation button row
        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));

        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);

        panel.add(new JLabel("Role:"));
        panel.add(roleField);

        panel.add(new JLabel("Department:"));
        panel.add(deptField);

        // Row 7: Register Button
        panel.add(new JLabel("")); // spacer to align button in right column
        JButton registerBtn = new JButton("Register");
        panel.add(registerBtn);

        // Row 8: Back to Login Navigation
        JButton backToLoginBtn = new JButton("Already have an account? Login");
        panel.add(new JLabel("")); // spacer to keep grid aligned
        panel.add(backToLoginBtn);

        // Action Listeners
        registerBtn.addActionListener(e -> handleRegister());

        backToLoginBtn.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        add(panel);
    }

    private void handleRegister() {

        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String phone = phoneField.getText();
        String role = roleField.getText();
        String department = deptField.getText();

        boolean success = userDAO.registerUser(
            name,
            email,
            password,
            phone,
            role,
            department
        );

        if (success) {
            JOptionPane.showMessageDialog(
                this,
                "Registered successfully!"
            );
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Registration failed. Email may already exist."
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
            new RegisterFrame().setVisible(true)
        );
    }
}