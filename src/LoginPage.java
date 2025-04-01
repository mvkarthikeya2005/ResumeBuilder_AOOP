import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginPage extends JFrame {
    private static LoginPage instance; // for static accessors if needed
    private JPanel contentPane;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnLogin;
    private JButton btnBack;
    private String loggedInUsername; // Store the logged in username

    // Static getters for use in other classes (if needed)
    public static JTextField getTextField() {
        return instance != null ? instance.textField : null;
    }
    public static JPasswordField getPasswordField() {
        return instance != null ? instance.passwordField : null;
    }
    
    // Get logged in username
    public static String getLoggedInUsername() {
        return instance != null ? instance.loggedInUsername : "User";
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable(){
            public void run() {
                try {
                    LoginPage frame = new LoginPage();
                    frame.setVisible(true);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LoginPage() {
        instance = this; // assign instance for static access
        setTitle("Resume Builder - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        contentPane = new GradientPanel();
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(contentPane);
        setLocationRelativeTo(null);

        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(new Color(255, 255, 255, 220));

        JLabel lblLoginPage = new JLabel("LOGIN");
        lblLoginPage.setHorizontalAlignment(SwingConstants.CENTER);
        lblLoginPage.setForeground(new Color(44, 62, 80));
        lblLoginPage.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JLabel lblSubtitle = new JLabel("Enter your credentials to continue");
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitle.setForeground(new Color(100, 100, 100));
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setForeground(new Color(60, 60, 60));
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(new LineBorder(new Color(180,180,180)));
        textField.setColumns(10);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(new Color(60, 60, 60));
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(new LineBorder(new Color(180,180,180)));

        btnLogin = new RoundButton("Login");
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(new Color(41,128,185));
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = textField.getText().trim();
                String password = new String(passwordField.getPassword());
                
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane, 
                        "Please enter both username and password", 
                        "Login Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Check for admin credentials
                if (username.equals("root") && password.equals("root")) {
                    JOptionPane.showMessageDialog(contentPane, 
                        "Admin login successful!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Open admin panel
                    AdminPanel adminPanel = new AdminPanel();
                    adminPanel.setVisible(true);
                    dispose();
                    return;
                }
                
                // Regular user login
                boolean valid = validateLogin(username, password);
                if (valid) {
                    JOptionPane.showMessageDialog(contentPane, 
                        "Login Successful!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Store logged in username
                    loggedInUsername = username;
                    
                    // Open the ResumeUI
                    ResumeUI resumeUI = new ResumeUI();
                    resumeUI.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(contentPane, 
                        "Invalid username or password", 
                        "Login Failed", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnBack = new RoundButton("Back");
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnBack.setForeground(new Color(60,60,60));
        btnBack.setBackground(new Color(240,240,240));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                login.setVisible(true);
                dispose();
            }
        });

        JLabel lblOr = new JLabel("Don't have an account?");
        lblOr.setHorizontalAlignment(SwingConstants.RIGHT);
        lblOr.setForeground(new Color(100, 100, 100));
        lblOr.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JLabel lblSignUp = new JLabel("Sign Up");
        lblSignUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblSignUp.setForeground(new Color(41,128,185));
        lblSignUp.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblSignUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SignUp signUp = new SignUp();
                signUp.setVisible(true);
                dispose();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                lblSignUp.setText("<html><u>Sign Up</u></html>");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                lblSignUp.setText("Sign Up");
            }
        });

        // Layout configuration using GroupLayout
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(200)
                    .addComponent(loginPanel, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(176, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(50)
                    .addComponent(loginPanel, GroupLayout.PREFERRED_SIZE, 380, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(32, Short.MAX_VALUE))
        );
        contentPane.setLayout(gl_contentPane);

        GroupLayout gl_loginPanel = new GroupLayout(loginPanel);
        gl_loginPanel.setHorizontalGroup(
            gl_loginPanel.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(gl_loginPanel.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_loginPanel.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblLoginPage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSubtitle, GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                        .addGroup(gl_loginPanel.createSequentialGroup()
                            .addGap(40)
                            .addGroup(gl_loginPanel.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblUsername)
                                .addComponent(textField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(lblPassword)
                                .addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addGroup(gl_loginPanel.createSequentialGroup()
                                    .addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                                    .addGap(20)
                                    .addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
                                .addGroup(GroupLayout.Alignment.TRAILING, gl_loginPanel.createSequentialGroup()
                                    .addComponent(lblOr)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblSignUp)
                                    .addGap(68)))
                            .addGap(40)))
                    .addContainerGap())
        );
        gl_loginPanel.setVerticalGroup(
            gl_loginPanel.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(gl_loginPanel.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblLoginPage)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(lblSubtitle)
                    .addGap(25)
                    .addComponent(lblUsername)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(textField, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                    .addGap(18)
                    .addComponent(lblPassword)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                    .addGap(30)
                    .addGroup(gl_loginPanel.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                    .addGap(18)
                    .addGroup(gl_loginPanel.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblOr)
                        .addComponent(lblSignUp))
                    .addContainerGap(30, Short.MAX_VALUE))
        );
        loginPanel.setLayout(gl_loginPanel);
    }
    
    // Validate user login against database
    private boolean validateLogin(String username, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            // Use DBConfig to get connection details
            String url = DBConfig.getConnectionURL();
            String dbUser = DBConfig.getUsername();
            String dbPassword = DBConfig.getPassword();
            
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            
            // Query to check user credentials
            String sql = "SELECT * FROM signup WHERE username = ? AND password = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            
            return rs.next(); // Returns true if credentials are valid
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Database connection error: " + e.getMessage(), 
                "Login Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            // Clean up resources
            try { if (rs != null) rs.close(); } catch (Exception e) { }
            try { if (ps != null) ps.close(); } catch (Exception e) { }
            try { if (conn != null) conn.close(); } catch (Exception e) { }
        }
    }

    // Custom gradient background panel
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, new Color(41,128,185), w, h, new Color(44,62,80));
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }
    }

    // Custom round button class
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
