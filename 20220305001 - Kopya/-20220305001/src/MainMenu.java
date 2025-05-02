// MainMenu.java
import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("ToolInventory Main Menu");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(Color.RED);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Make your Selection To Login", SwingConstants.CENTER);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setForeground(Color.WHITE);
        panel.add(label);

        JButton bossButton = new JButton("Login Boss");
        JButton employeeButton = new JButton("Login Employee");

        bossButton.setBackground(Color.WHITE);
        employeeButton.setBackground(Color.WHITE);

        bossButton.setAlignmentX(CENTER_ALIGNMENT);
        employeeButton.setAlignmentX(CENTER_ALIGNMENT);

        bossButton.addActionListener(e -> showLoginScreen("BOSS"));
        employeeButton.addActionListener(e -> showLoginScreen("EMPLOYEE"));

        panel.add(Box.createVerticalStrut(10));
        panel.add(bossButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(employeeButton);

        add(panel);
        setVisible(true);
    }

    private void showLoginScreen(String role) {
        JFrame loginFrame = new JFrame(role + " Login");
        loginFrame.setSize(300, 150);
        loginFrame.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.RED);

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.WHITE);
        panel.add(loginButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if ("yakuza".equals(username) && "1234".equals(password)) {
                loginFrame.dispose();
                dispose(); // MainMenu'yu kapat
                if ("BOSS".equals(role)) {
                    new BossPanel(this);
                } else {
                    new EmployeePanel();
                }
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginFrame.add(panel);
        loginFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
