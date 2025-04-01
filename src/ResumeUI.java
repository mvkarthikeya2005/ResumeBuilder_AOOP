import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JTabbedPane;
import java.awt.Cursor;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JOptionPane;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JSeparator;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.Box;

public class ResumeUI extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;
    private JTextField textField_6;
    private JTextField textField_7;
    private JTextField textField_8;
    private JTextField textField_9;
    private JTextField textField_11;
    private JTextField textField_12;
    private JTextField textField_13;
    private JTextField textField_10;
    private JTextField textField_14;
    private JLabel lblWorkExperience;
    private JTextField textField_15;
    private JLabel lblCompanyName;
    private JLabel label;
    private JLabel lblDesignation;
    private JLabel lblDesignation_1;
    private JTextField textField_16;
    private JTextField textField_17;
    private JTextField textField_18;
    private JLabel lblQualifications;
    private JLabel lblCollegeuniversity;
    private JTextField textField_19;
    private JTextField textField_20;
    private JLabel lblSchool;
    private JLabel lblOtherQualifications;
    private JTextField textField_21;
    private JLabel lblImagePreview;
    private JPanel currentPanel = null;
    private Color activeButtonColor = new Color(41, 128, 185);
    private Color inactiveButtonColor = new Color(100, 100, 100, 100);

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
                    ResumeUI frame = new ResumeUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public ResumeUI() {
        setTitle("Resume Builder - Create Your Resume");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 800);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(240, 245, 250));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        setLocationRelativeTo(null);
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(44, 62, 80));
        
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 245, 250));
        
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(new Color(52, 73, 94));
        
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addComponent(headerPanel, GroupLayout.DEFAULT_SIZE, 1174, Short.MAX_VALUE)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addComponent(sidebarPanel, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, 948, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addComponent(headerPanel, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addComponent(sidebarPanel, GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                        .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)))
        );
        
        CardLayout cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);
        
        // Create all panels for the card layout
        JPanel personalInfoPanel = createPersonalInfoPanel();
        JPanel skillsPanel = createSkillsPanel();
        JPanel educationPanel = createEducationPanel();
        JPanel workExperiencePanel = createWorkExperiencePanel();
        JPanel previewPanel = createPreviewPanel();
        
        mainPanel.add(personalInfoPanel, "personal");
        mainPanel.add(skillsPanel, "skills");
        mainPanel.add(educationPanel, "education");
        mainPanel.add(workExperiencePanel, "workexp");
        mainPanel.add(previewPanel, "preview");
        
        currentPanel = personalInfoPanel;
        
        // Show the personal info panel initially
        cardLayout.show(mainPanel, "personal");
        
        // Create sidebar with navigation buttons
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        
        JLabel lblUserIcon = new JLabel("");
        lblUserIcon.setAlignmentX(CENTER_ALIGNMENT);
        lblUserIcon.setPreferredSize(new Dimension(80, 80));
        lblUserIcon.setHorizontalAlignment(SwingConstants.CENTER);
        lblUserIcon.setForeground(Color.WHITE);
        lblUserIcon.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        // Try to load image or use placeholder
        try {
            lblUserIcon.setIcon(new ImageIcon(ResumeUI.class.getResource("/resources/user_icon.png")));
        } catch (Exception e) {
            lblUserIcon.setText("User");
        }
        
        JPanel userPanel = new JPanel();
        userPanel.setPreferredSize(new Dimension(220, 120));
        userPanel.setMaximumSize(new Dimension(220, 120));
        userPanel.setOpaque(false);
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        
        userPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        userPanel.add(lblUserIcon);
        
        String username = LoginPage.getLoggedInUsername();
        JLabel lblUsername = new JLabel(username);
        
        lblUsername.setAlignmentX(CENTER_ALIGNMENT);
        lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userPanel.add(lblUsername);
        
        sidebarPanel.add(userPanel);
        
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(100, 100, 100));
        separator.setMaximumSize(new Dimension(220, 1));
        sidebarPanel.add(separator);
        
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Navigation buttons
        NavButton personalInfoBtn = createNavButton("Personal Information", "personal", mainPanel, cardLayout);
        personalInfoBtn.setBackground(activeButtonColor);
        
        NavButton skillsBtn = createNavButton("Skills", "skills", mainPanel, cardLayout);
        NavButton educationBtn = createNavButton("Education", "education", mainPanel, cardLayout);
        NavButton workExpBtn = createNavButton("Work Experience", "workexp", mainPanel, cardLayout);
        NavButton previewBtn = createNavButton("Preview & Create", "preview", mainPanel, cardLayout);
        
        sidebarPanel.add(personalInfoBtn);
        sidebarPanel.add(skillsBtn);
        sidebarPanel.add(educationBtn);
        sidebarPanel.add(workExpBtn);
        sidebarPanel.add(Box.createVerticalGlue());
        sidebarPanel.add(previewBtn);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Header
        JLabel lblResume = new JLabel("Resume Builder");
        lblResume.setForeground(Color.WHITE);
        lblResume.setFont(new Font("Segoe UI", Font.BOLD, 20));
        
        RoundButton btnLogout = new RoundButton("Logout");
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(new Color(231, 76, 60, 180));
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(ResumeUI.this, 
                        "Are you sure you want to logout? Any unsaved changes will be lost.", 
                        "Confirm Logout", JOptionPane.YES_NO_OPTION);
                
                if (response == JOptionPane.YES_OPTION) {
                    Login login = new Login();
                    login.setVisible(true);
                    dispose();
                }
            }
        });
        
        GroupLayout gl_headerPanel = new GroupLayout(headerPanel);
        gl_headerPanel.setHorizontalGroup(
            gl_headerPanel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_headerPanel.createSequentialGroup()
                    .addGap(20)
                    .addComponent(lblResume)
                    .addPreferredGap(ComponentPlacement.RELATED, 881, Short.MAX_VALUE)
                    .addComponent(btnLogout, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                    .addGap(20))
        );
        gl_headerPanel.setVerticalGroup(
            gl_headerPanel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_headerPanel.createSequentialGroup()
                    .addGap(10)
                    .addGroup(gl_headerPanel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblResume)
                        .addComponent(btnLogout, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(14, Short.MAX_VALUE))
        );
        headerPanel.setLayout(gl_headerPanel);
        contentPane.setLayout(gl_contentPane);
    }
    
    // Create Personal Information Panel
    private JPanel createPersonalInfoPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 245, 250));
        
        JLabel lblPersonalInformation = new JLabel("Personal Information");
        lblPersonalInformation.setHorizontalAlignment(SwingConstants.LEFT);
        lblPersonalInformation.setForeground(new Color(44, 62, 80));
        lblPersonalInformation.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        JLabel lblFirstName = new JLabel("First Name");
        lblFirstName.setForeground(new Color(60, 60, 60));
        lblFirstName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(new LineBorder(new Color(180, 180, 180)));
        textField.setColumns(10);
        
        JLabel lblSurname = new JLabel("Surname");
        lblSurname.setForeground(new Color(60, 60, 60));
        lblSurname.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        textField_1 = new JTextField();
        textField_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField_1.setBorder(new LineBorder(new Color(180, 180, 180)));
        textField_1.setColumns(10);
        
        JLabel lblAddress = new JLabel("Address Line 1");
        lblAddress.setForeground(new Color(60, 60, 60));
        lblAddress.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        textField_2 = new JTextField();
        textField_2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField_2.setBorder(new LineBorder(new Color(180, 180, 180)));
        textField_2.setColumns(10);
        
        JLabel lblAddressLine = new JLabel("Address Line 2");
        lblAddressLine.setForeground(new Color(60, 60, 60));
        lblAddressLine.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        textField_3 = new JTextField();
        textField_3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField_3.setBorder(new LineBorder(new Color(180, 180, 180)));
        textField_3.setColumns(10);
        
        JLabel lblPincode = new JLabel("Pincode");
        lblPincode.setForeground(new Color(60, 60, 60));
        lblPincode.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        textField_4 = new JTextField();
        textField_4.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField_4.setBorder(new LineBorder(new Color(180, 180, 180)));
        textField_4.setColumns(10);
        
        JLabel lblNationality = new JLabel("Nationality");
        lblNationality.setForeground(new Color(60, 60, 60));
        lblNationality.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        textField_5 = new JTextField();
        textField_5.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField_5.setBorder(new LineBorder(new Color(180, 180, 180)));
        textField_5.setColumns(10);
        
        JLabel lblDateOfBirth = new JLabel("Date of Birth");
        lblDateOfBirth.setForeground(new Color(60, 60, 60));
        lblDateOfBirth.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        textField_6 = new JTextField();
        textField_6.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField_6.setBorder(new LineBorder(new Color(180, 180, 180)));
        textField_6.setColumns(10);
        
        JLabel lblPhoneNo = new JLabel("Phone Number");
        lblPhoneNo.setForeground(new Color(60, 60, 60));
        lblPhoneNo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        textField_7 = new JTextField();
        textField_7.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField_7.setBorder(new LineBorder(new Color(180, 180, 180)));
        textField_7.setColumns(10);
        
        JLabel lblEmail = new JLabel("Email Address");
        lblEmail.setForeground(new Color(60, 60, 60));
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        textField_8 = new JTextField();
        textField_8.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField_8.setBorder(new LineBorder(new Color(180, 180, 180)));
        textField_8.setColumns(10);
        
        lblImagePreview = new JLabel("");
        lblImagePreview.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagePreview.setBorder(new LineBorder(new Color(180, 180, 180)));
        lblImagePreview.setBackground(Color.WHITE);
        lblImagePreview.setOpaque(true);
        
        RoundButton btnBrowse = new RoundButton("Upload Photo");
        btnBrowse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBrowse.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnBrowse.setForeground(Color.WHITE);
        btnBrowse.setBackground(new Color(41, 128, 185));
        btnBrowse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                FileBrowser fb = new FileBrowser();
                fb.setVisible(true);
                fb.dispose();
                if(fb.getS()!=null) {
                    lblImagePreview.setIcon(new ImageIcon(fb.getS()));
                }
            }
        });
        
        JLabel lblPhoto = new JLabel("Profile Photo");
        lblPhoto.setForeground(new Color(60, 60, 60));
        lblPhoto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JLabel lblRequiredFields = new JLabel("* Required fields");
        lblRequiredFields.setForeground(new Color(231, 76, 60));
        lblRequiredFields.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        
        JLabel lblInstructions = new JLabel("<html>This information will appear at the top of your resume. Make sure to provide accurate contact details.</html>");
        lblInstructions.setForeground(new Color(100, 100, 100));
        lblInstructions.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addGap(30)
                    .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                            .addComponent(lblPersonalInformation)
                            .addPreferredGap(ComponentPlacement.RELATED, 560, Short.MAX_VALUE)
                            .addComponent(lblRequiredFields)
                            .addGap(30))
                        .addGroup(gl_panel.createSequentialGroup()
                            .addComponent(lblInstructions, GroupLayout.PREFERRED_SIZE, 800, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(118, Short.MAX_VALUE))
                        .addGroup(gl_panel.createSequentialGroup()
                            .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                .addComponent(lblFirstName)
                                .addComponent(textField, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblAddress)
                                .addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblPincode)
                                .addComponent(textField_4, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblDateOfBirth)
                                .addComponent(textField_6, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblEmail)
                                .addComponent(textField_8, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE))
                            .addGap(30)
                            .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                .addComponent(lblSurname)
                                .addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblAddressLine)
                                .addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblNationality)
                                .addComponent(textField_5, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblPhoneNo)
                                .addComponent(textField_7, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE))
                            .addGap(30)
                            .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                .addComponent(lblPhoto)
                                .addComponent(lblImagePreview, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnBrowse, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                            .addGap(30))))
        );
        gl_panel.setVerticalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addGap(30)
                    .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblPersonalInformation)
                        .addComponent(lblRequiredFields))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(lblInstructions, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                    .addGap(30)
                    .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                            .addComponent(lblPhoto)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(lblImagePreview, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.UNRELATED)
                            .addComponent(btnBrowse, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                        .addGroup(gl_panel.createSequentialGroup()
                            .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                                .addComponent(lblFirstName)
                                .addComponent(lblSurname))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                                .addComponent(textField, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                .addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                            .addGap(18)
                            .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                                .addComponent(lblAddress)
                                .addComponent(lblAddressLine))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                                .addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                .addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                            .addGap(18)
                            .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                                .addComponent(lblPincode)
                                .addComponent(lblNationality))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                                .addComponent(textField_4, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                .addComponent(textField_5, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                            .addGap(18)
                            .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                                .addComponent(lblDateOfBirth)
                                .addComponent(lblPhoneNo))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                                .addComponent(textField_6, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                .addComponent(textField_7, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                            .addGap(18)
                            .addComponent(lblEmail)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(textField_8, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(98, Short.MAX_VALUE))
        );
        panel.setLayout(gl_panel);
        
        return panel;
    }
    
    // Create Skills Panel
    private JPanel createSkillsPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 245, 250));
        
        JLabel lblSkills = new JLabel("Skills");
        lblSkills.setForeground(new Color(44, 62, 80));
        lblSkills.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        JLabel lblInstructions = new JLabel("<html>List your skills with their proficiency levels. Include technical skills, soft skills, and any certifications you have.</html>");
        lblInstructions.setForeground(new Color(100, 100, 100));
        lblInstructions.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JLabel lblSkill1 = new JLabel("Skill Category 1");
        lblSkill1.setForeground(new Color(60, 60, 60));
        lblSkill1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        textField_10 = new JTextField();
        textField_10.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField_10.setBorder(new LineBorder(new Color(180, 180, 180)));
        textField_10.setColumns(10);
        
        JLabel lblDescription1 = new JLabel("Description 1");
        lblDescription1.setForeground(new Color(60, 60, 60));
        lblDescription1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        textField_9 = new JTextField();
        textField_9.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField_9.setBorder(new LineBorder(new Color(180, 180, 180)));
        textField_9.setColumns(10);
        
        JLabel lblSkill2 = new JLabel("Skill Category 2");
        lblSkill2.setForeground(new Color(60, 60, 60));
        lblSkill2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        textField_11 = new JTextField();
        textField_11.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField_11.setBorder(new LineBorder(new Color(180, 180, 180)));
        textField_11.setColumns(10);
        
        JLabel lblDescription2 = new JLabel("Description 2");
        lblDescription2.setForeground(new Color(60, 60, 60));
        lblDescription2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        textField_13 = new JTextField();
        textField_13.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField_13.setBorder(new LineBorder(new Color(180, 180, 180)));
        textField_13.setColumns(10);
        
        JLabel lblSkill3 = new JLabel("Skill Category 3");
        lblSkill3.setForeground(new Color(60, 60, 60));
        lblSkill3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        textField_12 = new JTextField();
        textField_12.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField_12.setBorder(new LineBorder(new Color(180, 180, 180)));
        textField_12.setColumns(10);
        
        JLabel lblDescription3 = new JLabel("Description 3");
        lblDescription3.setForeground(new Color(60, 60, 60));
        lblDescription3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        textField_14 = new JTextField();
        textField_14.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField_14.setBorder(new LineBorder(new Color(180, 180, 180)));
        textField_14.setColumns(10);
        
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addGap(30)
                    .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblSkills)
                        .addComponent(lblInstructions, GroupLayout.PREFERRED_SIZE, 800, GroupLayout.PREFERRED_SIZE)
                        .addGroup(gl_panel.createSequentialGroup()
                            .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                .addComponent(lblSkill1)
                                .addComponent(textField_10, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblSkill2)
                                .addComponent(textField_11, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblSkill3)
                                .addComponent(textField_12, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                            .addGap(30)
                            .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                .addComponent(lblDescription1)
                                .addComponent(textField_9, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblDescription2)
                                .addComponent(textField_13, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblDescription3)
                                .addComponent(textField_14, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(88, Short.MAX_VALUE))
        );
        gl_panel.setVerticalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addGap(30)
                    .addComponent(lblSkills)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(lblInstructions, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                    .addGap(30)
                    .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblSkill1)
                        .addComponent(lblDescription1))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(textField_10, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                        .addComponent(textField_9, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                    .addGap(30)
                    .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblSkill2)
                        .addComponent(lblDescription2))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(textField_11, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                        .addComponent(textField_13, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                    .addGap(30)
                    .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblSkill3)
                        .addComponent(lblDescription3))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(textField_12, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                        .addComponent(textField_14, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(293, Short.MAX_VALUE))
        );
        panel.setLayout(gl_panel);
        
        return panel;
    }
    
    // Create Education Panel
    private JPanel createEducationPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 245, 250));
        
        lblQualifications = new JLabel("Education & Qualifications");
        lblQualifications.setForeground(new Color(44, 62, 80));
        lblQualifications.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        JLabel lblInstructions = new JLabel("<html>List your educational background, starting with the most recent. Include degrees, institutions, and graduation dates.</html>");
        lblInstructions.setForeground(new Color(100, 100, 100));
        lblInstructions.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        lblCollegeuniversity = new JLabel("College/University");
        lblCollegeuniversity.setForeground(new Color(60, 60, 60));
        lblCollegeuniversity.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        textField_19 = new JTextField();
        textField_19.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField_19.setBorder(new LineBorder(new Color(180, 180, 180)));
        textField_19.setColumns(10);
        
        lblSchool = new JLabel("School");
        lblSchool.setForeground(new Color(60, 60, 60));
        lblSchool.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        textField_20 = new JTextField();
        textField_20.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField_20.setBorder(new LineBorder(new Color(180, 180, 180)));
        textField_20.setColumns(10);
        
        lblOtherQualifications = new JLabel("Other Qualifications");
        lblOtherQualifications.setForeground(new Color(60, 60, 60));
        lblOtherQualifications.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        textField_21 = new JTextField();
        textField_21.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField_21.setBorder(new LineBorder(new Color(180, 180, 180)));
        textField_21.setColumns(10);
        
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addGap(30)
                    .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblQualifications)
                        .addComponent(lblInstructions, GroupLayout.PREFERRED_SIZE, 800, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblCollegeuniversity)
                        .addComponent(textField_19, GroupLayout.PREFERRED_SIZE, 800, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSchool)
                        .addComponent(textField_20, GroupLayout.PREFERRED_SIZE, 800, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblOtherQualifications)
                        .addComponent(textField_21, GroupLayout.PREFERRED_SIZE, 800, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(118, Short.MAX_VALUE))
        );
        gl_panel.setVerticalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addGap(30)
                    .addComponent(lblQualifications)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(lblInstructions, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                    .addGap(30)
                    .addComponent(lblCollegeuniversity)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(textField_19, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                    .addGap(30)
                    .addComponent(lblSchool)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(textField_20, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                    .addGap(30)
                    .addComponent(lblOtherQualifications)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(textField_21, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(293, Short.MAX_VALUE))
        );
        panel.setLayout(gl_panel);
        
        return panel;
    }
    
    // Create Work Experience Panel
    private JPanel createWorkExperiencePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 245, 250));
        
        lblWorkExperience = new JLabel("Work Experience");
        lblWorkExperience.setForeground(new Color(44, 62, 80));
        lblWorkExperience.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        JLabel lblInstructions = new JLabel("<html>List your work experience, starting with the most recent. Include company names, positions, and responsibilities.</html>");
        lblInstructions.setForeground(new Color(100, 100, 100));
        lblInstructions.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        lblCompanyName = new JLabel("Company Name 1");
        lblCompanyName.setForeground(new Color(60, 60, 60));
        lblCompanyName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        textField_15 = new JTextField();
        textField_15.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField_15.setBorder(new LineBorder(new Color(180, 180, 180)));
        textField_15.setColumns(10);
        
        lblDesignation = new JLabel("Designation 1");
        lblDesignation.setForeground(new Color(60, 60, 60));
        lblDesignation.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        textField_16 = new JTextField();
        textField_16.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField_16.setBorder(new LineBorder(new Color(180, 180, 180)));
        textField_16.setColumns(10);
        
        label = new JLabel("Company Name 2");
        label.setForeground(new Color(60, 60, 60));
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        textField_18 = new JTextField();
        textField_18.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField_18.setBorder(new LineBorder(new Color(180, 180, 180)));
        textField_18.setColumns(10);
        
        lblDesignation_1 = new JLabel("Designation 2");
        lblDesignation_1.setForeground(new Color(60, 60, 60));
        lblDesignation_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        textField_17 = new JTextField();
        textField_17.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField_17.setBorder(new LineBorder(new Color(180, 180, 180)));
        textField_17.setColumns(10);
        
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addGap(30)
                    .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblWorkExperience)
                        .addComponent(lblInstructions, GroupLayout.PREFERRED_SIZE, 800, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblCompanyName)
                        .addComponent(textField_15, GroupLayout.PREFERRED_SIZE, 800, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblDesignation)
                        .addComponent(textField_16, GroupLayout.PREFERRED_SIZE, 800, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label)
                        .addComponent(textField_18, GroupLayout.PREFERRED_SIZE, 800, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblDesignation_1)
                        .addComponent(textField_17, GroupLayout.PREFERRED_SIZE, 800, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(118, Short.MAX_VALUE))
        );
        gl_panel.setVerticalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addGap(30)
                    .addComponent(lblWorkExperience)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(lblInstructions, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                    .addGap(30)
                    .addComponent(lblCompanyName)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(textField_15, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                    .addGap(30)
                    .addComponent(lblDesignation)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(textField_16, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                    .addGap(30)
                    .addComponent(label)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(textField_18, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                    .addGap(30)
                    .addComponent(lblDesignation_1)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(textField_17, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(227, Short.MAX_VALUE))
        );
        panel.setLayout(gl_panel);
        
        return panel;
    }
    
    // Create Preview Panel
    private JPanel createPreviewPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 245, 250));
        
        JLabel lblPreview = new JLabel("Preview & Create Resume");
        lblPreview.setForeground(new Color(44, 62, 80));
        lblPreview.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        JLabel lblInstructions = new JLabel("<html>Review your resume information below. When you're ready, click 'Create Resume' to generate your resume file.</html>");
        lblInstructions.setForeground(new Color(100, 100, 100));
        lblInstructions.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JPanel previewContainer = new JPanel();
        previewContainer.setBackground(Color.WHITE);
        previewContainer.setBorder(new LineBorder(new Color(200, 200, 200)));
        
        RoundButton btnCreateResume = new RoundButton("Create Resume");
        btnCreateResume.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCreateResume.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCreateResume.setForeground(Color.WHITE);
        btnCreateResume.setBackground(new Color(41, 128, 185));
        btnCreateResume.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileWriterInput fwi = new FileWriterInput();
                String s = getTextField().getText().toString();
                String s1 = getTextField_1().getText().toString();
                String s2 = getTextField_2().getText().toString();
                String s3 = getTextField_3().getText().toString();
                String s4 = getTextField_4().getText().toString();
                String s5 = getTextField_5().getText().toString();
                String s6 = getTextField_6().getText().toString();
                String s7 = getTextField_7().getText().toString();
                String s8 = getTextField_8().getText().toString();
                String s9 = getTextField_9().getText().toString();
                String s10 = getTextField_10().getText().toString();
                String s11 = getTextField_11().getText().toString();
                String s12 = getTextField_12().getText().toString();
                String s13 = getTextField_13().getText().toString();
                String s14 = getTextField_14().getText().toString();
                String s15 = getTextField_15().getText().toString();
                String s16 = getTextField_16().getText().toString();
                String s17 = getTextField_17().getText().toString();
                String s18 = getTextField_18().getText().toString();
                String s19 = getTextField_19().getText().toString();
                String s20 = getTextField_20().getText().toString();
                String s21 = getTextField_21().getText().toString();
                
                fwi.add(s,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15,s16,s17,s18,s19,s20,s21);
                
                JOptionPane.showMessageDialog(ResumeUI.this, 
                        "Resume created successfully! File saved as: " + s + s1 + ".txt", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // Add preview content to container
        previewContainer.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        previewContainer.add(scrollPane, BorderLayout.CENTER);
        
        JPanel previewContent = new JPanel();
        previewContent.setBackground(Color.WHITE);
        scrollPane.setViewportView(previewContent);
        previewContent.setLayout(new BoxLayout(previewContent, BoxLayout.Y_AXIS));
        
        // Header with name and contact
        JPanel headerPreview = new JPanel();
        headerPreview.setBackground(new Color(245, 245, 245));
        headerPreview.setMaximumSize(new Dimension(32767, 100));
        headerPreview.setPreferredSize(new Dimension(10, 100));
        previewContent.add(headerPreview);
        headerPreview.setLayout(new BorderLayout(0, 0));
        
        JLabel lblPreviewName = new JLabel("Full Name");
        lblPreviewName.setHorizontalAlignment(SwingConstants.CENTER);
        lblPreviewName.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPreview.add(lblPreviewName, BorderLayout.CENTER);
        
        JLabel lblPreviewContact = new JLabel("Email | Phone | Address");
        lblPreviewContact.setHorizontalAlignment(SwingConstants.CENTER);
        lblPreviewContact.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        headerPreview.add(lblPreviewContact, BorderLayout.SOUTH);
        
        // Update preview when button is clicked
        RoundButton btnUpdatePreview = new RoundButton("Update Preview");
        btnUpdatePreview.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnUpdatePreview.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnUpdatePreview.setForeground(new Color(255, 255, 255));
        btnUpdatePreview.setBackground(new Color(52, 152, 219));
        btnUpdatePreview.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Update name and contact info
                String fullName = textField.getText() + " " + textField_1.getText();
                lblPreviewName.setText(fullName.trim().isEmpty() ? "Full Name" : fullName);
                
                String contactInfo = textField_8.getText() + " | " + textField_7.getText() + " | " + textField_2.getText();
                lblPreviewContact.setText(contactInfo.startsWith(" | ") ? "Email | Phone | Address" : contactInfo);
                
                // Update all preview sections
                // Find and update the Skills section
                for (Component comp : previewContent.getComponents()) {
                    if (comp instanceof JPanel) {
                        JPanel sectionPanel = (JPanel) comp;
                        for (Component c : sectionPanel.getComponents()) {
                            if (c instanceof JLabel && ((JLabel)c).getText().equals("Skills")) {
                                // We found the Skills section, now update its content
                                for (Component textComp : sectionPanel.getComponents()) {
                                    if (textComp instanceof JTextPane) {
                                        StringBuilder skillsContent = new StringBuilder();
                                        if (!textField_10.getText().trim().isEmpty()) {
                                            skillsContent.append("• ").append(textField_10.getText()).append(": ")
                                                .append(textField_9.getText()).append("\n");
                                        }
                                        if (!textField_11.getText().trim().isEmpty()) {
                                            skillsContent.append("• ").append(textField_11.getText()).append(": ")
                                                .append(textField_13.getText()).append("\n");
                                        }
                                        if (!textField_12.getText().trim().isEmpty()) {
                                            skillsContent.append("• ").append(textField_12.getText()).append(": ")
                                                .append(textField_14.getText());
                                        }
                                        
                                        if (skillsContent.length() == 0) {
                                            skillsContent.append("• Skill 1: Description\n• Skill 2: Description\n• Skill 3: Description");
                                        }
                                        
                                        ((JTextPane)textComp).setText(skillsContent.toString());
                                    }
                                }
                            } else if (c instanceof JLabel && ((JLabel)c).getText().equals("Education")) {
                                // Update Education section
                                for (Component textComp : sectionPanel.getComponents()) {
                                    if (textComp instanceof JTextPane) {
                                        StringBuilder educationContent = new StringBuilder();
                                        if (!textField_19.getText().trim().isEmpty()) {
                                            educationContent.append("• College/University: ").append(textField_19.getText()).append("\n");
                                        }
                                        if (!textField_20.getText().trim().isEmpty()) {
                                            educationContent.append("• School: ").append(textField_20.getText()).append("\n");
                                        }
                                        if (!textField_21.getText().trim().isEmpty()) {
                                            educationContent.append("• Other: ").append(textField_21.getText());
                                        }
                                        
                                        if (educationContent.length() == 0) {
                                            educationContent.append("• College/University\n• School\n• Other qualifications");
                                        }
                                        
                                        ((JTextPane)textComp).setText(educationContent.toString());
                                    }
                                }
                            } else if (c instanceof JLabel && ((JLabel)c).getText().equals("Work Experience")) {
                                // Update Work Experience section
                                for (Component textComp : sectionPanel.getComponents()) {
                                    if (textComp instanceof JTextPane) {
                                        StringBuilder workContent = new StringBuilder();
                                        if (!textField_15.getText().trim().isEmpty()) {
                                            workContent.append("• ").append(textField_15.getText());
                                            if (!textField_16.getText().trim().isEmpty()) {
                                                workContent.append(": ").append(textField_16.getText());
                                            }
                                            workContent.append("\n");
                                        }
                                        if (!textField_18.getText().trim().isEmpty()) {
                                            workContent.append("• ").append(textField_18.getText());
                                            if (!textField_17.getText().trim().isEmpty()) {
                                                workContent.append(": ").append(textField_17.getText());
                                            }
                                        }
                                        
                                        if (workContent.length() == 0) {
                                            workContent.append("• Company 1: Designation\n• Company 2: Designation");
                                        }
                                        
                                        ((JTextPane)textComp).setText(workContent.toString());
                                    }
                                }
                            }
                        }
                    }
                }
                
                // Show success message
                JOptionPane.showMessageDialog(ResumeUI.this, 
                        "Preview updated successfully!", 
                        "Preview Updated", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // Sections for preview
        addSectionToPreview(previewContent, "Skills", "• Skill 1: Description\n• Skill 2: Description\n• Skill 3: Description");
        addSectionToPreview(previewContent, "Education", "• College/University\n• School\n• Other qualifications");
        addSectionToPreview(previewContent, "Work Experience", "• Company 1: Designation\n• Company 2: Designation");
        
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addGap(30)
                    .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                            .addComponent(btnUpdatePreview, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED, 438, Short.MAX_VALUE)
                            .addComponent(btnCreateResume, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE))
                        .addComponent(lblPreview)
                        .addComponent(lblInstructions, GroupLayout.PREFERRED_SIZE, 800, GroupLayout.PREFERRED_SIZE)
                        .addComponent(previewContainer, GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE))
                    .addGap(118))
        );
        gl_panel.setVerticalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addGap(30)
                    .addComponent(lblPreview)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(lblInstructions, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                    .addGap(20)
                    .addComponent(previewContainer, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                    .addGap(20)
                    .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(btnCreateResume, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUpdatePreview, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                    .addGap(30))
        );
        panel.setLayout(gl_panel);
        
        return panel;
    }
    
    // Helper method to add a section to the preview panel
    private void addSectionToPreview(JPanel container, String title, String content) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setBackground(Color.WHITE);
        sectionPanel.setMaximumSize(new Dimension(32767, 150));
        sectionPanel.setPreferredSize(new Dimension(10, 150));
        container.add(sectionPanel);
        sectionPanel.setLayout(new BorderLayout(0, 0));
        
        JLabel lblSectionTitle = new JLabel(title);
        lblSectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblSectionTitle.setBorder(new EmptyBorder(10, 10, 5, 10));
        sectionPanel.add(lblSectionTitle, BorderLayout.NORTH);
        
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setText(content);
        textPane.setBorder(new EmptyBorder(5, 20, 10, 10));
        textPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sectionPanel.add(textPane, BorderLayout.CENTER);
        
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(200, 200, 200));
        sectionPanel.add(separator, BorderLayout.SOUTH);
    }
    
    // Create a navigation button for the sidebar
    private NavButton createNavButton(String text, String cardName, JPanel cardPanel, CardLayout layout) {
        NavButton btn = new NavButton(text);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                layout.show(cardPanel, cardName);
                // Reset all button colors
                for (Component c : btn.getParent().getComponents()) {
                    if (c instanceof NavButton) {
                        ((NavButton) c).setBackground(inactiveButtonColor);
                    }
                }
                // Highlight the selected button
                btn.setBackground(activeButtonColor);
            }
        });
        return btn;
    }
    
    // Custom navigation button for the sidebar
    class NavButton extends JButton {
        public NavButton(String text) {
            super(text);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
            setBackground(inactiveButtonColor);
            setBorderPainted(false);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setHorizontalAlignment(SwingConstants.LEFT);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setMaximumSize(new Dimension(220, 40));
            setPreferredSize(new Dimension(220, 40));
            
            // Add padding
            setBorder(new EmptyBorder(0, 20, 0, 0));
            
            // Add hover effect
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (getBackground() != activeButtonColor) {
                        setBackground(new Color(100, 100, 100, 150));
                    }
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    if (getBackground() != activeButtonColor) {
                        setBackground(inactiveButtonColor);
                    }
                }
            });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(getBackground());
            g2.fillRect(0, 0, getWidth(), getHeight());
            
            super.paintComponent(g);
            g2.dispose();
        }
    }
    
    // Custom round button
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
    
    // Getters for text fields
    public JTextField getTextField() {
        return textField;
    }

    public JTextField getTextField_1() {
        return textField_1;
    }

    public JTextField getTextField_2() {
        return textField_2;
    }

    public JTextField getTextField_3() {
        return textField_3;
    }

    public JTextField getTextField_4() {
        return textField_4;
    }

    public JTextField getTextField_5() {
        return textField_5;
    }

    public JTextField getTextField_6() {
        return textField_6;
    }

    public JTextField getTextField_7() {
        return textField_7;
    }

    public JTextField getTextField_8() {
        return textField_8;
    }

    public JTextField getTextField_9() {
        return textField_9;
    }

    public JTextField getTextField_11() {
        return textField_11;
    }

    public JTextField getTextField_12() {
        return textField_12;
    }

    public JTextField getTextField_13() {
        return textField_13;
    }

    public JTextField getTextField_10() {
        return textField_10;
    }

    public JTextField getTextField_14() {
        return textField_14;
    }

    public JTextField getTextField_15() {
        return textField_15;
    }

    public JTextField getTextField_16() {
        return textField_16;
    }

    public JTextField getTextField_17() {
        return textField_17;
    }

    public JTextField getTextField_18() {
        return textField_18;
    }

    public JTextField getTextField_19() {
        return textField_19;
    }

    public JTextField getTextField_20() {
        return textField_20;
    }

    public JTextField getTextField_21() {
        return textField_21;
    }
}
