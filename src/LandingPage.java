import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.geom.*;
import components.GradientPanel;
import components.RoundButton;
import components.RoundPanel;
import util.UIConstants;
import util.PathManager;
import java.awt.image.BufferedImage;
import java.io.File;

public class LandingPage extends JFrame {
    private JPanel contentPane;
    private Timer animationTimer;
    private float animationProgress = 0f;
    private boolean darkMode = false;
    private CardLayout cardLayout;
    private JPanel mainContent;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LandingPage frame = new LandingPage();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LandingPage() {
        setTitle("Resume Builder - Welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 700);
        setLocationRelativeTo(null);

        // Create gradient background
        contentPane = new GradientPanel(UIConstants.PRIMARY_DARK_COLOR, UIConstants.PRIMARY_COLOR,
                GradientPanel.GradientType.DIAGONAL);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = createHeaderPanel();
        contentPane.add(headerPanel, BorderLayout.NORTH);

        // Content panel with card layout for transitions
        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);
        mainContent.setOpaque(false);
        contentPane.add(mainContent, BorderLayout.CENTER);

        // Add home panel to card layout
        JPanel mainPanel = createMainPanel();
        mainContent.add(mainPanel, "home");

        // Add viewer intro panel to card layout
        JPanel viewerIntroPanel = createViewerIntroPanel();
        mainContent.add(viewerIntroPanel, "viewer");

        // Default view is home
        cardLayout.show(mainContent, "home");

        // Create the created_resumes directory if it doesn't exist
        File createdResumesDir = new File(PathManager.getCreatedResumesPath());
        if (!createdResumesDir.exists()) {
            createdResumesDir.mkdirs();
        }

        // Start entrance animation
        startEntranceAnimation();
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 80));
        headerPanel.setLayout(new BorderLayout());

        JLabel logoLabel = new JLabel("RESUME BUILDER");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setBorder(new EmptyBorder(20, 30, 20, 0));
        headerPanel.add(logoLabel, BorderLayout.WEST);

        // Navigation buttons
        JPanel navButtons = new JPanel();
        navButtons.setOpaque(false);
        navButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton homeBtn = createNavButton("Home");
        homeBtn.addActionListener(e -> cardLayout.show(mainContent, "home"));

        JButton viewerBtn = createNavButton("Resume Viewer");
        viewerBtn.addActionListener(e -> cardLayout.show(mainContent, "viewer"));

        JButton themeToggle = new JButton();
        themeToggle.setIcon(createThemeIcon());
        themeToggle.setBorderPainted(false);
        themeToggle.setContentAreaFilled(false);
        themeToggle.setFocusPainted(false);
        themeToggle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        themeToggle.setToolTipText("Toggle Theme");
        themeToggle.addActionListener(e -> toggleTheme());

        JButton loginBtn = createNavButton("Login");
        loginBtn.addActionListener(e -> openLoginPage());

        JButton signupBtn = createNavButton("Sign Up");
        signupBtn.addActionListener(e -> openSignUpPage());

        navButtons.add(homeBtn);
        navButtons.add(Box.createHorizontalStrut(15));
        navButtons.add(viewerBtn);
        navButtons.add(Box.createHorizontalStrut(15));
        navButtons.add(themeToggle);
        navButtons.add(Box.createHorizontalStrut(15));
        navButtons.add(loginBtn);
        navButtons.add(Box.createHorizontalStrut(10));
        navButtons.add(signupBtn);
        navButtons.add(Box.createHorizontalStrut(30));

        headerPanel.add(navButtons, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new GridLayout(1, 2, 20, 0));

        // Left panel - hero content
        JPanel heroPanel = new JPanel();
        heroPanel.setOpaque(false);
        heroPanel.setLayout(new BoxLayout(heroPanel, BoxLayout.Y_AXIS));
        heroPanel.setBorder(new EmptyBorder(50, 50, 50, 20));

        JLabel titleLabel = new JLabel("Create Professional");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Resumes in Minutes");
        subtitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descriptionLabel = new JLabel("<html><body style='width: 400px'>" +
                "Stand out from the crowd with our professional resume builder. " +
                "Create, customize, and download your perfect resume in just a few clicks." +
                "</body></html>");
        descriptionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        descriptionLabel.setForeground(new Color(240, 240, 255));
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionLabel.setBorder(new EmptyBorder(20, 0, 30, 0));

        // Feature list with icons
        JPanel featurePanel = new JPanel();
        featurePanel.setOpaque(false);
        featurePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        featurePanel.setLayout(new BoxLayout(featurePanel, BoxLayout.Y_AXIS));

        featurePanel.add(createFeatureItem("Easy-to-use interface with step-by-step process"));
        featurePanel.add(Box.createVerticalStrut(15));
        featurePanel.add(createFeatureItem("Professional templates with customizable sections"));
        featurePanel.add(Box.createVerticalStrut(15));
        featurePanel.add(createFeatureItem("Export to various formats including PDF"));

        RoundButton getStartedBtn = new RoundButton("Get Started");
        getStartedBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        getStartedBtn.setForeground(Color.WHITE);
        getStartedBtn.setBackground(UIConstants.ACCENT_COLOR);
        getStartedBtn.setFocusPainted(false);
        getStartedBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        getStartedBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        getStartedBtn.setPreferredSize(new Dimension(200, 50));
        getStartedBtn.setMaximumSize(new Dimension(200, 50));
        getStartedBtn.addActionListener(e -> openLoginPage());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonPanel.add(getStartedBtn);

        heroPanel.add(titleLabel);
        heroPanel.add(subtitleLabel);
        heroPanel.add(descriptionLabel);
        heroPanel.add(featurePanel);
        heroPanel.add(Box.createVerticalStrut(30));
        heroPanel.add(buttonPanel);

        // Right panel - preview image
        JPanel previewPanel = new JPanel();
        previewPanel.setOpaque(false);
        previewPanel.setLayout(new BoxLayout(previewPanel, BoxLayout.Y_AXIS));
        previewPanel.setBorder(new EmptyBorder(50, 20, 50, 50));

        JLabel previewImage = new JLabel();
        ImageIcon resumePreviewIcon = createResumePreviewImage();
        previewImage.setIcon(resumePreviewIcon);
        previewImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel imageContainer = new JPanel();
        imageContainer.setOpaque(false);
        imageContainer.add(previewImage);

        previewPanel.add(Box.createVerticalGlue());
        previewPanel.add(imageContainer);
        previewPanel.add(Box.createVerticalGlue());

        // Add panels to main panel
        mainPanel.add(heroPanel);
        mainPanel.add(previewPanel);

        return mainPanel;
    }

    private JPanel createViewerIntroPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBorder(new EmptyBorder(20, 50, 30, 50));

        JLabel titleLabel = new JLabel("Resume Viewer");
        titleLabel.setFont(UIConstants.HEADER_FONT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Main content panel with description and preview
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 30, 0));
        contentPanel.setOpaque(false);

        // Left side - description
        RoundPanel descPanel = new RoundPanel(20, new Color(255, 255, 255, 40));
        descPanel.setLayout(new BoxLayout(descPanel, BoxLayout.Y_AXIS));
        descPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel descTitle = new JLabel("View & Manage Your Resumes");
        descTitle.setFont(UIConstants.SUBHEADER_FONT);
        descTitle.setForeground(Color.WHITE);
        descTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descText = new JLabel("<html><body style='width: 400px'>" +
                "Our Resume Viewer provides a convenient way to access and manage all your created resumes. " +
                "Features include:" +
                "<ul>" +
                "<li>Quick preview of all saved resumes</li>" +
                "<li>Search functionality to find specific resumes</li>" +
                "<li>Export options for different formats (PDF, DOCX)</li>" +
                "<li>Print functionality for physical copies</li>" +
                "<li>Dark mode support for comfortable viewing</li>" +
                "</ul>" +
                "</body></html>");
        descText.setFont(UIConstants.REGULAR_FONT);
        descText.setForeground(new Color(220, 220, 220));
        descText.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundButton launchBtn = new RoundButton("Launch Resume Viewer");
        launchBtn.setFont(UIConstants.BOLD_FONT);
        launchBtn.setBackground(UIConstants.ACCENT_COLOR);
        launchBtn.setForeground(Color.WHITE);
        launchBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        launchBtn.setMaximumSize(new Dimension(200, 40));
        launchBtn.addActionListener(e -> openResumeViewer());

        descPanel.add(descTitle);
        descPanel.add(Box.createVerticalStrut(15));
        descPanel.add(descText);
        descPanel.add(Box.createVerticalStrut(30));
        descPanel.add(launchBtn);

        // Right side - preview image
        RoundPanel previewPanel = new RoundPanel(20, new Color(255, 255, 255, 40));
        previewPanel.setLayout(new BorderLayout());
        previewPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Create a preview image of the viewer
        JLabel previewImage = new JLabel();
        previewImage.setIcon(createViewerPreviewImage());
        previewImage.setHorizontalAlignment(SwingConstants.CENTER);

        previewPanel.add(previewImage, BorderLayout.CENTER);

        contentPanel.add(descPanel);
        contentPanel.add(previewPanel);

        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(210, 210, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.WHITE);
            }
        });

        return button;
    }

    private JPanel createFeatureItem(String text) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(createCheckIcon());

        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        textLabel.setForeground(new Color(240, 240, 255));

        panel.add(iconLabel);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(textLabel);

        return panel;
    }

    private void startEntranceAnimation() {
        animationTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (animationProgress >= 1.0f) {
                    animationTimer.stop();
                    return;
                }

                animationProgress += 0.05f;
                float alpha = Math.min(1.0f, animationProgress);

                // Apply animation effects
                Window window = SwingUtilities.getWindowAncestor(contentPane);
                if (window instanceof RootPaneContainer) {
                    JRootPane rootPane = ((RootPaneContainer) window).getRootPane();
                    rootPane.getContentPane().setBackground(new Color(1f, 1f, 1f, alpha));
                }

                repaint();
            }
        });
        animationTimer.start();
    }

    private void toggleTheme() {
        darkMode = !darkMode;
        // Apply theme change logic here
        // This would update colors across the application

        // For now, just show a message
        JOptionPane.showMessageDialog(this,
                "Theme toggling functionality will be implemented in a future update.",
                "Coming Soon",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void openLoginPage() {
        Login loginPage = new Login();
        loginPage.setVisible(true);
        dispose();
    }

    private void openSignUpPage() {
        SignUp signUpPage = new SignUp();
        signUpPage.setVisible(true);
        dispose();
    }

    private void openResumeViewer() {
        ResumeViewer viewer = new ResumeViewer();
        viewer.setVisible(true);
        // Don't dispose this window, just make viewer visible
    }

    private ImageIcon createThemeIcon() {
        BufferedImage image = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw moon/sun icon
        if (darkMode) {
            // Sun icon
            g2d.setColor(Color.WHITE);
            g2d.fillOval(2, 2, 20, 20);
        } else {
            // Moon icon
            g2d.setColor(Color.WHITE);
            g2d.fillOval(4, 4, 16, 16);
            g2d.setColor(UIConstants.PRIMARY_DARK_COLOR);
            g2d.fillOval(8, 2, 16, 16);
        }

        g2d.dispose();
        return new ImageIcon(image);
    }

    private ImageIcon createCheckIcon() {
        BufferedImage image = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw check icon
        g2d.setColor(UIConstants.ACCENT_COLOR);
        g2d.fillOval(0, 0, 20, 20);

        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawLine(5, 10, 9, 14);
        g2d.drawLine(9, 14, 15, 7);

        g2d.dispose();
        return new ImageIcon(image);
    }

    private ImageIcon createResumePreviewImage() {
        // Create a stylized resume preview image
        BufferedImage image = new BufferedImage(400, 500, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Paper background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 400, 500);

        // Add shadow effect
        g2d.setColor(new Color(0, 0, 0, 30));
        g2d.fillRect(5, 5, 400, 500);

        // Header area
        g2d.setColor(UIConstants.PRIMARY_COLOR);
        g2d.fillRect(0, 0, 400, 80);

        // Name in header
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 24));
        g2d.drawString("John Doe", 20, 45);

        // Contact info
        g2d.setColor(new Color(80, 80, 80));
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        g2d.drawString("Email: john.doe@example.com", 20, 100);
        g2d.drawString("Phone: (123) 456-7890", 20, 120);

        // Content sections
        g2d.setColor(new Color(60, 60, 60));
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
        g2d.drawString("EXPERIENCE", 20, 160);

        g2d.setColor(new Color(180, 180, 180));
        g2d.drawLine(20, 165, 380, 165);

        // Experience content - just gray lines to simulate text
        g2d.setColor(new Color(120, 120, 120));
        for (int i = 0; i < 4; i++) {
            g2d.fillRect(20, 180 + (i * 15), 360, 8);
        }

        g2d.setColor(new Color(60, 60, 60));
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
        g2d.drawString("EDUCATION", 20, 260);

        g2d.setColor(new Color(180, 180, 180));
        g2d.drawLine(20, 265, 380, 265);

        // Education content - just gray lines to simulate text
        g2d.setColor(new Color(120, 120, 120));
        for (int i = 0; i < 3; i++) {
            g2d.fillRect(20, 280 + (i * 15), 360, 8);
        }

        g2d.setColor(new Color(60, 60, 60));
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
        g2d.drawString("SKILLS", 20, 340);

        g2d.setColor(new Color(180, 180, 180));
        g2d.drawLine(20, 345, 380, 345);

        // Skills content - skill bars
        for (int i = 0; i < 4; i++) {
            g2d.setColor(new Color(120, 120, 120));
            g2d.fillRect(20, 360 + (i * 25), 100, 15);

            g2d.setColor(UIConstants.ACCENT_COLOR);
            g2d.fillRect(130, 360 + (i * 25), (int) (Math.random() * 150) + 100, 15);
        }

        g2d.dispose();
        return new ImageIcon(image);
    }

    private ImageIcon createViewerPreviewImage() {
        // Create a stylized viewer preview image
        BufferedImage image = new BufferedImage(400, 300, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // App window with rounded corners
        g2d.setColor(new Color(240, 245, 250));
        g2d.fill(new RoundRectangle2D.Double(0, 0, 400, 300, 10, 10));

        // Left panel - list of resumes
        g2d.setColor(new Color(225, 230, 235));
        g2d.fillRect(10, 40, 120, 250);

        // Top menu bar
        g2d.setColor(new Color(52, 152, 219));
        g2d.fillRect(0, 0, 400, 30);

        // Search field
        g2d.setColor(Color.WHITE);
        g2d.fillRect(15, 45, 110, 25);

        // Document list items
        g2d.setColor(Color.WHITE);
        for (int i = 0; i < 5; i++) {
            g2d.fillRoundRect(15, 80 + (i * 35), 100, 30, 5, 5);
        }

        // Main content area with text
        g2d.setColor(Color.WHITE);
        g2d.fillRect(140, 40, 250, 250);

        // Simulate text lines
        g2d.setColor(new Color(180, 180, 180));
        for (int i = 0; i < 12; i++) {
            g2d.fillRect(150, 50 + (i * 20), 230, 5);
        }

        // Simulate formatting toolbar
        g2d.setColor(new Color(240, 240, 240));
        g2d.fillRect(140, 40, 250, 30);

        // Menu titles
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 12));
        g2d.drawString("File", 20, 20);
        g2d.drawString("View", 50, 20);
        g2d.drawString("Help", 80, 20);

        g2d.dispose();
        return new ImageIcon(image);
    }
}
