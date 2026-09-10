package frontend;
import model.User;
import backend.UserDAO;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField emailField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);
    private UserDAO userDAO = new UserDAO();

    public LoginFrame() {
        setTitle("Login - Vehicle Pool System");
        setSize(350, 230);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Updated rows from 3 to 4 to accommodate the new button row
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        
        // Row 1: Email
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        
        // Row 2: Password
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        // Row 3: Login Button
        panel.add(new JLabel("")); // Empty cell to push Login button to the right column
        JButton loginBtn = new JButton("Login");
        panel.add(loginBtn);

        // Row 4: Register Navigation
        JButton goToRegisterBtn = new JButton("New user? Register here");
        panel.add(new JLabel("")); // Empty cell to keep grid aligned
        panel.add(goToRegisterBtn);

        // Action Listeners
        loginBtn.addActionListener(e -> handleLogin());

        goToRegisterBtn.addActionListener(e -> {
            new RegisterFrame().setVisible(true);
            dispose(); // closes the current login window
        });

        add(panel);
    }

   private void handleLogin() {
    String email = emailField.getText();
    String password = new String(passwordField.getPassword());

    User user = userDAO.login(email, password);

    if (user != null) {
        JOptionPane.showMessageDialog(this, "Welcome, " + user.getName() + "!");
        new DashboardFrame(user).setVisible(true);
        dispose();
    } else {
        JOptionPane.showMessageDialog(this, "Invalid email or password.");
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}