import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import components.GradientPanel;
import components.RoundButton;
import components.RoundPanel;
import util.UIConstants;

public class Login extends JFrame {
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Start with the LandingPage instead of Login
                    LandingPage landingPage = new LandingPage();
                    landingPage.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Login() {
        setTitle("Resume Builder - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        setLocationRelativeTo(null);
        
        // No longer using undecorated mode to avoid opacity issues
        // We'll use the system window decorations
        
        contentPane = new GradientPanel(UIConstants.PRIMARY_DARK_COLOR, UIConstants.PRIMARY_COLOR, 
                                       GradientPanel.GradientType.DIAGONAL);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        
        // Main content layout with two sides
        contentPane.setLayout(new GridLayout(1, 2, 20, 0));
        
        // Left side - App info and image
        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        contentPane.add(leftPanel);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        
        leftPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        
        JLabel lblResumeBuilder = new JLabel("RESUME BUILDER");
        lblResumeBuilder.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblResumeBuilder.setHorizontalAlignment(SwingConstants.CENTER);
        lblResumeBuilder.setForeground(UIConstants.TEXT_WHITE);
        lblResumeBuilder.setFont(new Font("Segoe UI", Font.BOLD, 32));
        leftPanel.add(lblResumeBuilder);
        
        JLabel lblTagline = new JLabel("Create Professional Resumes in Minutes");
        lblTagline.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTagline.setHorizontalAlignment(SwingConstants.CENTER);
        lblTagline.setForeground(UIConstants.TEXT_WHITE);
        lblTagline.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        leftPanel.add(lblTagline);
        
        leftPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        
        JLabel lblImageContainer = new JLabel("");
        lblImageContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblImageContainer.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Try to load image, use a placeholder if not found
        try {
            ImageIcon originalIcon = new ImageIcon(Login.class.getResource("/resources/resume_icon.png"));
            if (originalIcon.getIconWidth() == -1) {
                lblImageContainer.setText("Resume Builder");
                lblImageContainer.setForeground(UIConstants.TEXT_WHITE);
                lblImageContainer.setFont(new Font("Segoe UI", Font.BOLD, 24));
            } else {
                lblImageContainer.setIcon(originalIcon);
            }
        } catch (Exception e) {
            lblImageContainer.setText("Resume Builder");
            lblImageContainer.setForeground(UIConstants.TEXT_WHITE);
            lblImageContainer.setFont(new Font("Segoe UI", Font.BOLD, 24));
        }
        
        leftPanel.add(lblImageContainer);
        
        leftPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        
        // Back to landing page button
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setOpaque(false);
        backButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(backButtonPanel);
        
        RoundButton btnBack = new RoundButton("Back to Home");
        btnBack.setFont(UIConstants.REGULAR_FONT);
        btnBack.setBackground(new Color(255, 255, 255, 100));
        btnBack.setPreferredSize(new Dimension(150, 40));
        btnBack.addActionListener(e -> {
            // Create a new landing page - undecorated
            LandingPage landingPage = new LandingPage();
            landingPage.setVisible(true);
            dispose();
        });
        backButtonPanel.add(btnBack);
        
        // Right side - Login buttons
        RoundPanel rightPanel = new RoundPanel(20);
        rightPanel.setBackground(UIConstants.BG_PANEL);
        contentPane.add(rightPanel);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        
        rightPanel.add(Box.createVerticalGlue());
        
        JLabel lblWelcome = new JLabel("Welcome Back");
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblWelcome.setForeground(UIConstants.TEXT_DARK);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 28));
        rightPanel.add(lblWelcome);
        
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JLabel lblChoose = new JLabel("Please choose an option to continue");
        lblChoose.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblChoose.setForeground(UIConstants.TEXT_MEDIUM);
        lblChoose.setFont(UIConstants.REGULAR_FONT);
        rightPanel.add(lblChoose);
        
        rightPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(buttonPanel);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
        
        RoundButton btnLogin = new RoundButton("Login");
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setForeground(UIConstants.TEXT_WHITE);
        btnLogin.setBackground(UIConstants.PRIMARY_COLOR);
        btnLogin.setPreferredSize(new Dimension(180, 50));
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoginPage lp = new LoginPage();
                lp.setVisible(true);
                dispose();
            }
        });
        buttonPanel.add(btnLogin);
        
        RoundButton btnSignUp = new RoundButton("Sign Up");
        btnSignUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignUp.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSignUp.setForeground(UIConstants.TEXT_WHITE);
        btnSignUp.setBackground(UIConstants.SECONDARY_COLOR);
        btnSignUp.setPreferredSize(new Dimension(180, 50));
        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                SignUp sp = new SignUp();
                sp.setVisible(true);
                dispose();
            }
        });
        buttonPanel.add(btnSignUp);
        
        rightPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        
        // Replace direct admin access with admin login button
        RoundButton btnAdminLogin = new RoundButton("Admin Login");
        btnAdminLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAdminLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAdminLogin.setForeground(UIConstants.TEXT_WHITE);
        btnAdminLogin.setBackground(UIConstants.ACCENT_COLOR);
        btnAdminLogin.setPreferredSize(new Dimension(150, 40));
        btnAdminLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAdminLogin.addActionListener(e -> {
            AdminLogin adminLogin = new AdminLogin();
            adminLogin.setVisible(true);
            dispose();
        });
        
        rightPanel.add(btnAdminLogin);
        
        rightPanel.add(Box.createVerticalGlue());
        
        // Don't use fade-in animation for decorated frames
        // Instead, just make the frame visible
        setVisible(true);
    }
}
