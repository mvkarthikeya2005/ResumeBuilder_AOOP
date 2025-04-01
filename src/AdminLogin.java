import components.GradientPanel;
import components.RoundButton;
import components.RoundPanel;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import util.UIConstants;

public class AdminLogin extends JFrame {
    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    /**
     * Create the frame.
     */
    public AdminLogin() {
        setTitle("Resume Builder - Admin Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        setLocationRelativeTo(null);
        
        contentPane = new GradientPanel(UIConstants.PRIMARY_DARK_COLOR, UIConstants.PRIMARY_COLOR, 
                                       GradientPanel.GradientType.DIAGONAL);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        
        // Center panel with login form
        RoundPanel centerPanel = new RoundPanel(20);
        centerPanel.setBackground(UIConstants.BG_PANEL);
        contentPane.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        
        // Add some padding
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel lblTitle = new JLabel("Admin Login");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(UIConstants.TEXT_DARK);
        lblTitle.setFont(UIConstants.HEADER_FONT);
        centerPanel.add(lblTitle);
        
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Username field
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblUsername.setFont(UIConstants.REGULAR_FONT);
        centerPanel.add(lblUsername);
        
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        usernameField = new JTextField();
        usernameField.setFont(UIConstants.REGULAR_FONT);
        usernameField.setMaximumSize(new Dimension(300, 35));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(usernameField);
        
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Password field
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPassword.setFont(UIConstants.REGULAR_FONT);
        centerPanel.add(lblPassword);
        
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        passwordField = new JPasswordField();
        passwordField.setFont(UIConstants.REGULAR_FONT);
        passwordField.setMaximumSize(new Dimension(300, 35));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(passwordField);
        
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Login button
        RoundButton btnLogin = new RoundButton("Login");
        btnLogin.setFont(UIConstants.BOLD_FONT);
        btnLogin.setBackground(UIConstants.PRIMARY_COLOR);
        btnLogin.setForeground(UIConstants.TEXT_WHITE);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(200, 40));
        btnLogin.addActionListener(e -> validateLogin());
        centerPanel.add(btnLogin);
        
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Back button
        RoundButton btnBack = new RoundButton("Back");
        btnBack.setFont(UIConstants.REGULAR_FONT);
        btnBack.setBackground(new Color(200, 200, 200));
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setMaximumSize(new Dimension(100, 30));
        btnBack.addActionListener(e -> {
            Login login = new Login();
            login.setVisible(true);
            dispose();
        });
        centerPanel.add(btnBack);
        
        // Handle Enter key press
        getRootPane().setDefaultButton(btnLogin);
    }
    
    /**
     * Validate admin login credentials
     */
    private void validateLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        // Check credentials (in a real app, this would be more secure)
        if ("root".equals(username) && "root".equals(password)) {
            AdminPanel adminPanel = new AdminPanel();
            adminPanel.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Invalid username or password",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE
            );
            passwordField.setText("");
        }
    }
}
