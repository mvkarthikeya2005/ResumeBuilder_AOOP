import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;
import java.util.Properties;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class AdminPanel extends JFrame {
    private JPanel contentPane;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JTextField nameField;
    private JTextField usernameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JPasswordField passwordField;

    // Predefined admin credentials
    private static final String ADMIN_USERNAME = "root";
    private static final String ADMIN_PASSWORD = "root";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                AdminPanel frame = new AdminPanel();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public AdminPanel() {
        setTitle("Resume Builder - Admin Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 800);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        setLocationRelativeTo(null);

        // Header panel with gradient background
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(44, 62, 80), w, h, new Color(52, 73, 94));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        headerPanel.setPreferredSize(new Dimension(1200, 60));
        contentPane.add(headerPanel, BorderLayout.NORTH);
        headerPanel.setLayout(new BorderLayout(0, 0));

        JLabel lblTitle = new JLabel("Admin Panel");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setBorder(new EmptyBorder(0, 20, 0, 0));
        headerPanel.add(lblTitle, BorderLayout.WEST);

        JPanel headerButtonsPanel = new JPanel();
        headerButtonsPanel.setOpaque(false);
        headerPanel.add(headerButtonsPanel, BorderLayout.EAST);

        RoundButton btnLogout = new RoundButton("Logout");
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(new Color(231, 76, 60));
        btnLogout.addActionListener(e -> {
            Login login = new Login();
            login.setVisible(true);
            dispose();
        });
        headerButtonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        headerButtonsPanel.add(btnLogout);

        // Main content panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 245, 250));
        contentPane.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new BorderLayout(0, 0));

        // Create left panel for user table
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(240, 245, 250));
        leftPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        leftPanel.setPreferredSize(new Dimension(700, 700));

        // Create right panel for user edit form
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(240, 245, 250));
        rightPanel.setBorder(new EmptyBorder(15, 0, 15, 15));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(700);
        splitPane.setDividerSize(5);
        splitPane.setEnabled(false);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Set up left panel (user table)
        leftPanel.setLayout(new BorderLayout(0, 0));

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(240, 245, 250));
        searchPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        leftPanel.add(searchPanel, BorderLayout.NORTH);
        searchPanel.setLayout(new BorderLayout(0, 0));

        JLabel lblUsers = new JLabel("User  Management");
        lblUsers.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblUsers.setForeground(new Color(44, 62, 80));
        searchPanel.add(lblUsers, BorderLayout.WEST);

        JPanel searchInputPanel = new JPanel();
        searchInputPanel.setBackground(new Color(240, 245, 250));
        searchPanel.add(searchInputPanel, BorderLayout.EAST);

        JLabel lblSearch = new JLabel("Search:");
        lblSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchInputPanel.add(lblSearch);

        searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(200, 30));
        searchInputPanel.add(searchField);

        RoundButton btnSearch = new RoundButton("Search");
        btnSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnSearch.setBackground(new Color(52, 152, 219));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            if (!searchText.isEmpty()) {
                LoadUser_DataWithSearch(searchText);
            } else {
                LoadUser_Data();
            }
        });
        searchInputPanel.add(btnSearch);

        // Create table panel
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(new LineBorder(new Color(200, 200, 200)));
        leftPanel.add(tablePanel, BorderLayout.CENTER);
        tablePanel.setLayout(new BorderLayout(0, 0));

        // Initialize table
        String[] columnNames = {"ID", "Full Name", "Username", "Email", "Phone"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        userTable = new JTable(tableModel);
        userTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userTable.setRowHeight(30);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        userTable.getTableHeader().setBackground(new Color(52, 73, 94));
        userTable.getTableHeader().setForeground(Color.WHITE);

        userTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && userTable.getSelectedRow() != -1) {
                int row = userTable.getSelectedRow();
                nameField.setText(userTable.getValueAt(row, 1).toString());
                usernameField.setText(userTable.getValueAt(row, 2).toString());
                emailField.setText(userTable.getValueAt(row, 3).toString());
                phoneField.setText(userTable.getValueAt(row, 4).toString());
                passwordField.setText(""); // Don't show password for security
            }
        });

        JScrollPane scrollPane = new JScrollPane(userTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add action buttons under the table
        JPanel actionPanel = new JPanel();
        actionPanel.setBackground(new Color(240, 245, 250));
        actionPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        leftPanel.add(actionPanel, BorderLayout.SOUTH);

        RoundButton btnRefresh = new RoundButton("Refresh");
        btnRefresh.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnRefresh.setBackground(new Color(46, 204, 113));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.addActionListener(e -> LoadUser_Data());

        RoundButton btnDelete = new RoundButton("Delete User");
        btnDelete.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnDelete.setBackground(new Color(231, 76, 60));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.addActionListener(e -> {
            int row = userTable.getSelectedRow();
            if (row != -1) {
                int userId = Integer.parseInt(userTable.getValueAt(row, 0).toString());
                int confirm = JOptionPane.showConfirmDialog(
                        AdminPanel.this,
                        "Are you sure you want to delete this user?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    deleteUser(userId);
                }
            } else {
                JOptionPane.showMessageDialog(AdminPanel.this,
                        "Please select a user to delete.",
                        "No Selection",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        actionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        actionPanel.add(btnRefresh);
        actionPanel.add(btnDelete);

        // Set up right panel (user form)
        rightPanel.setLayout(new BorderLayout(0, 0));

        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(20, 20, 20, 20)
        ));
        rightPanel.add(formPanel, BorderLayout.CENTER);

        // Layout for the form
        GridBagLayout gbl_formPanel = new GridBagLayout();
        gbl_formPanel.columnWidths = new int[]{0, 0};
        gbl_formPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_formPanel.columnWeights = new double[]{0.0, 1.0};
        gbl_formPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
        formPanel.setLayout(gbl_formPanel);

        JLabel lblUser_Details = new JLabel("User  Details");
        lblUser_Details.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblUser_Details.setForeground(new Color(44, 62, 80));
        GridBagConstraints gbc_lblUser_Details = new GridBagConstraints();
        gbc_lblUser_Details.gridwidth = 2;
        gbc_lblUser_Details.insets = new Insets(0, 0, 20, 0);
        gbc_lblUser_Details.anchor = GridBagConstraints.WEST;
        gbc_lblUser_Details.gridx = 0;
        gbc_lblUser_Details.gridy = 0;
        formPanel.add(lblUser_Details, gbc_lblUser_Details);

        JLabel lblName = new JLabel("Full Name:");
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        GridBagConstraints gbc_lblName = new GridBagConstraints();
        gbc_lblName.anchor = GridBagConstraints.WEST;
        gbc_lblName.insets = new Insets(0, 0, 10, 10);
        gbc_lblName.gridx = 0;
        gbc_lblName.gridy = 1;
        formPanel.add(lblName, gbc_lblName);

        nameField = new JTextField();
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        GridBagConstraints gbc_nameField = new GridBagConstraints();
        gbc_nameField.insets = new Insets(0, 0, 10, 0);
        gbc_nameField.fill = GridBagConstraints.HORIZONTAL;
        gbc_nameField.gridx = 1;
        gbc_nameField.gridy = 1;
        formPanel.add(nameField, gbc_nameField);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        GridBagConstraints gbc_lblUsername = new GridBagConstraints();
        gbc_lblUsername.anchor = GridBagConstraints.WEST;
        gbc_lblUsername.insets = new Insets(0, 0, 10, 10);
        gbc_lblUsername.gridx = 0;
        gbc_lblUsername.gridy = 2;
        formPanel.add(lblUsername, gbc_lblUsername);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        GridBagConstraints gbc_usernameField = new GridBagConstraints();
        gbc_usernameField.insets = new Insets(0, 0, 10, 0);
        gbc_usernameField.fill = GridBagConstraints.HORIZONTAL;
        gbc_usernameField.gridx = 1;
        gbc_usernameField.gridy = 2;
        formPanel.add(usernameField, gbc_usernameField);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        GridBagConstraints gbc_lblEmail = new GridBagConstraints();
        gbc_lblEmail.anchor = GridBagConstraints.WEST;
        gbc_lblEmail.insets = new Insets(0, 0, 10, 10);
        gbc_lblEmail.gridx = 0;
        gbc_lblEmail.gridy = 3;
        formPanel.add(lblEmail, gbc_lblEmail);

        emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        GridBagConstraints gbc_emailField = new GridBagConstraints();
        gbc_emailField.insets = new Insets(0, 0, 10, 0);
        gbc_emailField.fill = GridBagConstraints.HORIZONTAL;
        gbc_emailField.gridx = 1;
        gbc_emailField.gridy = 3;
        formPanel.add(emailField, gbc_emailField);

        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        GridBagConstraints gbc_lblPhone = new GridBagConstraints();
        gbc_lblPhone.anchor = GridBagConstraints.WEST;
        gbc_lblPhone.insets = new Insets(0, 0, 10, 10);
        gbc_lblPhone.gridx = 0;
        gbc_lblPhone.gridy = 4;
        formPanel.add(lblPhone, gbc_lblPhone);

        phoneField = new JTextField();
        phoneField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        GridBagConstraints gbc_phoneField = new GridBagConstraints();
        gbc_phoneField.insets = new Insets(0, 0, 10, 0);
        gbc_phoneField.fill = GridBagConstraints.HORIZONTAL;
        gbc_phoneField.gridx = 1;
        gbc_phoneField.gridy = 4;
        formPanel.add(phoneField, gbc_phoneField);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        GridBagConstraints gbc_lblPassword = new GridBagConstraints();
        gbc_lblPassword.anchor = GridBagConstraints.WEST;
        gbc_lblPassword.insets = new Insets(0, 0, 20, 10);
        gbc_lblPassword.gridx = 0;
        gbc_lblPassword.gridy = 5;
        formPanel.add(lblPassword, gbc_lblPassword);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        GridBagConstraints gbc_passwordField = new GridBagConstraints();
        gbc_passwordField.insets = new Insets(0, 0, 20, 0);
        gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
        gbc_passwordField.gridx = 1;
        gbc_passwordField.gridy = 5;
        formPanel.add(passwordField, gbc_passwordField);

        // Action buttons for the form
        JPanel formButtonPanel = new JPanel();
        formButtonPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc_formButtonPanel = new GridBagConstraints();
        gbc_formButtonPanel.gridwidth = 2;
        gbc_formButtonPanel.fill = GridBagConstraints.BOTH;
        gbc_formButtonPanel.gridx = 0;
        gbc_formButtonPanel.gridy = 6;
        formPanel.add(formButtonPanel, gbc_formButtonPanel);

        RoundButton btnUpdateUser = new RoundButton("Update User");
        btnUpdateUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnUpdateUser.setBackground(new Color(41, 128, 185));
        btnUpdateUser.setForeground(Color.WHITE);
        btnUpdateUser.addActionListener(e -> {
            int row = userTable.getSelectedRow();
            if (row != -1) {
                int userId = Integer.parseInt(userTable.getValueAt(row, 0).toString());
                updateUser(userId);
            } else {
                JOptionPane.showMessageDialog(AdminPanel.this,
                        "Please select a user to update.",
                        "No Selection",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        formButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
        formButtonPanel.add(btnUpdateUser);

        RoundButton btnAddUser = new RoundButton("Add New User");
        btnAddUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnAddUser.setBackground(new Color(46, 204, 113));
        btnAddUser.setForeground(Color.WHITE);
        btnAddUser.addActionListener(e -> addNewUser());
        formButtonPanel.add(btnAddUser);

        RoundButton btnClearForm = new RoundButton("Clear Form");
        btnClearForm.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnClearForm.setBackground(new Color(149, 165, 166));
        btnClearForm.setForeground(Color.WHITE);
        btnClearForm.addActionListener(e -> {
            clearForm();
            userTable.clearSelection();
        });
        formButtonPanel.add(btnClearForm);

        // Load user data
        LoadUser_Data();
    }

    // Load user data from database
    private void LoadUser_Data() {
        tableModel.setRowCount(0); // Clear the table

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String url = DBConfig.getConnectionURL();
            String user = DBConfig.getUsername();
            String password = DBConfig.getPassword();

            // Create properties with SSL settings to avoid warnings
            Properties props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", password);
            props.setProperty("useSSL", "false");
            props.setProperty("allowPublicKeyRetrieval", "true");

            conn = DriverManager.getConnection(url, props);
            String sql = "SELECT id, fullname, username, email, phone FROM signup";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("fullname"));
                row.add(rs.getString("username"));
                row.add(rs.getString("email"));
                row.add(rs.getString("phone"));
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading user data: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { }
            try { if (stmt != null) stmt.close(); } catch (Exception e) { }
            try { if (conn != null) conn.close(); } catch (Exception e) { }
        }
    }

    // Load user data with search criteria
    private void LoadUser_DataWithSearch(String searchText) {
        tableModel.setRowCount(0); // Clear the table

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String url = DBConfig.getConnectionURL();
            String user = DBConfig.getUsername();
            String password = DBConfig.getPassword();

            // Create properties with SSL settings to avoid warnings
            Properties props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", password);
            props.setProperty("useSSL", "false");
            props.setProperty("allowPublicKeyRetrieval", "true");

            conn = DriverManager.getConnection(url, props);
            String sql = "SELECT id, fullname, username, email, phone FROM signup " +
                    "WHERE fullname LIKE ? OR username LIKE ? OR email LIKE ? OR phone LIKE ?";
            stmt = conn.prepareStatement(sql);
            String searchPattern = "%" + searchText + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            stmt.setString(4, searchPattern);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("fullname"));
                row.add(rs.getString("username"));
                row.add(rs.getString("email"));
                row.add(rs.getString("phone"));
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error searching user data: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { }
            try { if (stmt != null) stmt.close(); } catch (Exception e) { }
            try { if (conn != null) conn.close(); } catch (Exception e) { }
        }
    }

    // Delete user by ID
    private void deleteUser(int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String url = DBConfig.getConnectionURL();
            String user = DBConfig.getUsername();
            String password = DBConfig.getPassword();

            // Create properties with SSL settings to avoid warnings
            Properties props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", password);
            props.setProperty("useSSL", "false");
            props.setProperty("allowPublicKeyRetrieval", "true");

            conn = DriverManager.getConnection(url, props);
            String sql = "DELETE FROM signup WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            int result = stmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this,
                        "User deleted successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                LoadUser_Data();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to delete user.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error deleting user: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) { }
            try { if (conn != null) conn.close(); } catch (Exception e) { }
        }
    }

    // Update user
    private void updateUser(int userId) {
        if (!validateForm()) {
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String url = DBConfig.getConnectionURL();
            String user = DBConfig.getUsername();
            String password = DBConfig.getPassword();

            // Create properties with SSL settings to avoid warnings
            Properties props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", password);
            props.setProperty("useSSL", "false");
            props.setProperty("allowPublicKeyRetrieval", "true");

            conn = DriverManager.getConnection(url, props);

            // If password is provided, update it too
            String sql;
            if (passwordField.getPassword().length > 0) {
                sql = "UPDATE signup SET fullname = ?, username = ?, email = ?, phone = ?, password = ? WHERE id = ?";
            } else {
                sql = "UPDATE signup SET fullname = ?, username = ?, email = ?, phone = ? WHERE id = ?";
            }

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nameField.getText().trim());
            stmt.setString(2, usernameField.getText().trim());
            stmt.setString(3, emailField.getText().trim());
            stmt.setString(4, phoneField.getText().trim());

            if (passwordField.getPassword().length > 0) {
                stmt.setString(5, new String(passwordField.getPassword()));
                stmt.setInt(6, userId);
            } else {
                stmt.setInt(5, userId);
            }

            int result = stmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this,
                        "User updated successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                LoadUser_Data();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to update user.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error updating user: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) { }
            try { if (conn != null) conn.close(); } catch (Exception e) { }
        }
    }

    // Add new user
    private void addNewUser() {
        if (!validateForm()) {
            return;
        }

        if (passwordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this,
                    "Password is required for new users.",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String url = DBConfig.getConnectionURL();
            String user = DBConfig.getUsername();
            String password = DBConfig.getPassword();

            // Create properties with SSL settings to avoid warnings
            Properties props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", password);
            props.setProperty("useSSL", "false");
            props.setProperty("allowPublicKeyRetrieval", "true");

            conn = DriverManager.getConnection(url, props);
            String sql = "INSERT INTO signup (fullname, username, email, phone, password) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nameField.getText().trim());
            stmt.setString(2, usernameField.getText().trim());
            stmt.setString(3, emailField.getText().trim());

            // Validate phone number is numeric
            try {
                long phoneNum = Long.parseLong(phoneField.getText().trim());
                stmt.setLong(4, phoneNum);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Phone number must be numeric.",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            stmt.setString(5, new String(passwordField.getPassword()));

            int result = stmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this,
                        "User added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                LoadUser_Data();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to add user.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error adding user: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) { }
            try { if (conn != null) conn.close(); } catch (Exception e) { }
        }
    }

    // Clear the form fields
    private void clearForm() {
        nameField.setText("");
        usernameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        passwordField.setText("");
    }

    // Validate the form fields
    private boolean validateForm() {
        if (nameField.getText().trim().isEmpty() ||
            usernameField.getText().trim().isEmpty() ||
            emailField.getText().trim().isEmpty() ||
            phoneField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields.",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // Custom round button class
    class RoundButton extends JButton {
        public RoundButton(String label) {
            super(label);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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