import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.*;

public class SignUp extends JFrame {
    private JPanel contentPane;
    private JTextField fullnameField;
    private JTextField usernameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        EventQueue.invokeLater(() -> {
            try {
                SignUp frame = new SignUp();
                frame.setVisible(true);
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public SignUp() {
        setTitle("Resume Builder - Sign Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        
        // Set the custom gradient background for the main content pane
        contentPane = new GradientPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        setLocationRelativeTo(null);
        
        // Create a panel for the signup form with a semi-transparent white background
        JPanel signupPanel = new JPanel();
        signupPanel.setBackground(new Color(255, 255, 255, 220));
        signupPanel.setPreferredSize(new Dimension(450, 500));
        
        // Use GroupLayout to position the signup panel within contentPane
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        contentPane.setLayout(gl_contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(signupPanel)
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(signupPanel)
        );
        
        // Create the UI components for the form
        JLabel lblHeader = new JLabel("CREATE ACCOUNT");
        lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
        lblHeader.setForeground(new Color(44, 62, 80));
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 28));
        
        JLabel lblSubtitle = new JLabel("Fill in your details to get started");
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitle.setForeground(new Color(100, 100, 100));
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JLabel lblFullName = new JLabel("Full Name");
        lblFullName.setForeground(new Color(60, 60, 60));
        lblFullName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fullnameField = new JTextField();
        fullnameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fullnameField.setBorder(new LineBorder(new Color(180, 180, 180)));
        
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setForeground(new Color(60, 60, 60));
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField = new JTextField();
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBorder(new LineBorder(new Color(180, 180, 180)));
        
        JLabel lblEmail = new JLabel("Email Id");
        lblEmail.setForeground(new Color(60, 60, 60));
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setBorder(new LineBorder(new Color(180, 180, 180)));
        
        JLabel lblPhone = new JLabel("Phone No.");
        lblPhone.setForeground(new Color(60, 60, 60));
        lblPhone.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        phoneField = new JTextField();
        phoneField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        phoneField.setBorder(new LineBorder(new Color(180, 180, 180)));
        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(new Color(60, 60, 60));
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(new LineBorder(new Color(180, 180, 180)));
        
        JLabel lblConfirmPassword = new JLabel("Confirm Password");
        lblConfirmPassword.setForeground(new Color(60, 60, 60));
        lblConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        confirmPasswordField.setBorder(new LineBorder(new Color(180, 180, 180)));
        
        // Create custom round buttons for Register and Back
        RoundButton btnRegister = new RoundButton("Register");
        btnRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setBackground(new Color(41, 128, 185));
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Validate that all fields are filled
                if (fullnameField.getText().isEmpty() || usernameField.getText().isEmpty() ||
                    emailField.getText().isEmpty() || phoneField.getText().isEmpty() ||
                    passwordField.getPassword().length == 0 || confirmPasswordField.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(contentPane, "Please fill in all fields", "Registration Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validate that the passwords match
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(contentPane, "Passwords do not match", "Registration Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Save the user registration (assuming SignUpDb is defined)
                SignUpDb s = new SignUpDb();
                s.database(fullnameField.getText(), usernameField.getText(), 
                           emailField.getText(), phoneField.getText(), password);
                
                JOptionPane.showMessageDialog(contentPane, "Registration successful! You can now login.", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Open next UI (e.g., ResumeUI) and close this frame
                ResumeUI rui = new ResumeUI();
                rui.setVisible(true);
                dispose();
            }
        });
        
        RoundButton btnBack = new RoundButton("Back");
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnBack.setForeground(new Color(60, 60, 60));
        btnBack.setBackground(new Color(240, 240, 240));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Navigate back to login (assuming Login is defined)
                Login login = new Login();
                login.setVisible(true);
                dispose();
            }
        });
        
        JLabel lblLogin = new JLabel("Already have an account? Login");
        lblLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogin.setForeground(new Color(41, 128, 185));
        lblLogin.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginPage loginPage = new LoginPage();
                loginPage.setVisible(true);
                dispose();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                lblLogin.setText("<html><u>Already have an account? Login</u></html>");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                lblLogin.setText("Already have an account? Login");
            }
        });
        
        // Set up GroupLayout for the signup panel (simpler version)
        GroupLayout layout = new GroupLayout(signupPanel);
        signupPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(lblHeader)
                .addComponent(lblSubtitle)
                .addComponent(lblFullName)
                .addComponent(fullnameField, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblUsername)
                .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblEmail)
                .addComponent(emailField, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblPhone)
                .addComponent(phoneField, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblPassword)
                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblConfirmPassword)
                .addComponent(confirmPasswordField, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE)
                .addGroup(
                    layout.createSequentialGroup()
                        .addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                        .addGap(30)
                        .addComponent(btnRegister, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                )
                .addComponent(lblLogin)
        );
        
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(lblHeader)
                .addComponent(lblSubtitle)
                .addComponent(lblFullName)
                .addComponent(fullnameField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblUsername)
                .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblEmail)
                .addComponent(emailField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblPhone)
                .addComponent(phoneField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblPassword)
                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblConfirmPassword)
                .addComponent(confirmPasswordField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRegister, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                )
                .addComponent(lblLogin)
        );
    }
    
    // Custom gradient panel for a modern background look
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, new Color(41, 128, 185), w, h, new Color(44, 62, 80));
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
            g2d.dispose();
        }
    }
    
    // Custom round button with rounded corners
    class RoundButton extends JButton {
        public RoundButton(String label) {
            super(label);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (getModel().isPressed()) {
                g2.setColor(getBackground().darker());
            } else if (getModel().isRollover()) {
                g2.setColor(getBackground().brighter());
            } else {
                g2.setColor(getBackground());
            }
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
            super.paintComponent(g);
            g2.dispose();
        }
    }
}
