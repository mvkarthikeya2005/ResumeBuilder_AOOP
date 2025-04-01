// package resumebuilder; // Uncomment and adjust if needed

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;

// Add these imports for PDF functionality
import java.awt.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import java.io.FileOutputStream;

public class ResumeViewer extends JFrame {
    private JTextPane textPane;
    private JList<String> resumeList;
    private DefaultListModel<String> listModel;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private File currentDirectory;
    private JTextField searchField;
    private JComboBox<String> fontSelector;
    private JComboBox<Integer> fontSizeSelector;
    private Timer fadeInTimer;

    // Define color scheme constants
    private final Color LIGHT_BACKGROUND = new Color(225, 235, 245); // Light blue
    private final Color LIGHT_PANEL_BACKGROUND = new Color(210, 225, 240); // Slightly darker blue for panels
    private final Color LIGHT_TEXT = new Color(20, 30, 50); // Dark navy text

    private final Color DARK_BACKGROUND = new Color(35, 45, 65); // Dark blue
    private final Color DARK_PANEL_BACKGROUND = new Color(45, 55, 75); // Slightly lighter for panels
    private final Color DARK_TEXT = new Color(210, 225, 245); // Light blue text

    public ResumeViewer() {
        super("Enhanced Resume Viewer");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Use created_resumes directory as the default directory
        currentDirectory = new File(PathManager.getCreatedResumesPath());
        if (!currentDirectory.exists()) {
            currentDirectory.mkdirs();
        }

        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setupMenuBar();
        setupMainLayout();
        loadResumeFiles();

        // Apply default light blue theme
        applyTheme(false);

        // Center on screen
        setLocationRelativeTo(null);
        setVisible(true);

        // Initial animation
        fadeInApplication();
    }

    private void fadeInApplication() {
        setOpacity(0.0f);
        fadeInTimer = new Timer(20, new ActionListener() {
            float opacity = 0.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                opacity += 0.05f;
                if (opacity > 1.0f) {
                    opacity = 1.0f;
                    fadeInTimer.stop();
                }
                setOpacity(opacity);
            }
        });
        fadeInTimer.start();
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem openItem = new JMenuItem("Open Resume", KeyEvent.VK_O);
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        openItem.addActionListener(e -> openResumeFile());

        JMenuItem exportPdfItem = new JMenuItem("Export to PDF", KeyEvent.VK_E);
        exportPdfItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
        exportPdfItem.addActionListener(e -> exportToPDF());

        JMenuItem printItem = new JMenuItem("Print", KeyEvent.VK_P);
        printItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        printItem.addActionListener(e -> printResume());

        JMenuItem exitItem = new JMenuItem("Exit", KeyEvent.VK_X);
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(openItem);
        fileMenu.add(exportPdfItem);
        fileMenu.add(printItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // View Menu
        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic(KeyEvent.VK_V);

        JMenuItem darkModeItem = new JMenuItem("Toggle Dark Mode", KeyEvent.VK_D);
        darkModeItem.addActionListener(e -> toggleDarkMode());

        viewMenu.add(darkModeItem);

        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);

        JMenuItem aboutItem = new JMenuItem("About", KeyEvent.VK_A);
        aboutItem.addActionListener(e -> showAboutDialog());

        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private void setupMainLayout() {
        // Main split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(250);
        splitPane.setOneTouchExpandable(true);

        // Change the divider appearance
        splitPane.setDividerSize(6);

        // Left panel (file browser)
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Search field
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Search"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filterResumeList(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filterResumeList(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filterResumeList(); }
        });

        searchPanel.add(searchField, BorderLayout.CENTER);
        leftPanel.add(searchPanel, BorderLayout.NORTH);

        // Resume list
        listModel = new DefaultListModel<>();
        resumeList = new JList<>(listModel);
        resumeList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        resumeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resumeList.setCellRenderer(new ResumeListCellRenderer());
        resumeList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && resumeList.getSelectedIndex() != -1) {
                loadSelectedResume();
            }
        });

        JScrollPane listScrollPane = new JScrollPane(resumeList);
        listScrollPane.setBorder(BorderFactory.createTitledBorder("Available Resumes"));
        leftPanel.add(listScrollPane, BorderLayout.CENTER);

        // Right panel (resume viewer with card layout for animations)
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        // Text pane for formatted viewing
        textPane = new JTextPane();
        textPane.setEditable(false);

        // Style document
        StyleContext sc = new StyleContext();
        DefaultStyledDocument doc = new DefaultStyledDocument(sc);
        textPane.setDocument(doc);

        // Set default font
        textPane.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane textScrollPane = new JScrollPane(textPane);
        textScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Formatting toolbar
        JPanel formattingPanel = new JPanel();
        formattingPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Font selector
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();
        fontSelector = new JComboBox<>(fontNames);
        fontSelector.setSelectedItem("Arial");
        fontSelector.addActionListener(e -> updateFontStyle());

        // Font size selector
        Integer[] fontSizes = {10, 12, 14, 16, 18, 20, 22, 24, 28, 32, 36, 40, 48};
        fontSizeSelector = new JComboBox<>(fontSizes);
        fontSizeSelector.setSelectedItem(14);
        fontSizeSelector.addActionListener(e -> updateFontStyle());

        formattingPanel.add(new JLabel("Font: "));
        formattingPanel.add(fontSelector);
        formattingPanel.add(new JLabel("Size: "));
        formattingPanel.add(fontSizeSelector);

        // Add PDF Export Button with icon
        JButton pdfButton = new JButton("Export to PDF");
        pdfButton.setIcon(createPDFIcon());
        pdfButton.addActionListener(e -> exportToPDF());
        pdfButton.setToolTipText("Save resume as PDF document");

        // Add some spacing
        formattingPanel.add(Box.createHorizontalStrut(20));
        formattingPanel.add(new JSeparator(JSeparator.VERTICAL));
        formattingPanel.add(Box.createHorizontalStrut(10));
        formattingPanel.add(pdfButton);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(formattingPanel, BorderLayout.NORTH);
        rightPanel.add(textScrollPane, BorderLayout.CENTER);

        cardPanel.add(rightPanel, "viewer");

        // Welcome panel
        JPanel welcomePanel = createWelcomePanel();
        cardPanel.add(welcomePanel, "welcome");
        cardLayout.show(cardPanel, "welcome");

        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(cardPanel);

        getContentPane().add(splitPane);
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Set background to match theme
        panel.setBackground(LIGHT_PANEL_BACKGROUND);

        JLabel titleLabel = new JLabel("Welcome to Resume Viewer");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(LIGHT_TEXT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel instructionLabel = new JLabel("<html><div style='text-align: center;'>Select a resume from the list on the left<br>or use the File menu to open a specific resume.</div></html>");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionLabel.setForeground(LIGHT_TEXT);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create a document icon directly
        ImageIcon icon = createPlaceholderIcon();
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(imageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(instructionLabel);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private ImageIcon createPDFIcon() {
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Enable anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw PDF icon
        g2d.setColor(new Color(200, 50, 50));
        g2d.fillRect(0, 0, 16, 16);

        // PDF letters
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 9));
        g2d.drawString("PDF", 1, 11);

        g2d.dispose();
        return new ImageIcon(image);
    }

    private ImageIcon createPlaceholderIcon() {
        // Create a larger, more visually appealing document icon
        BufferedImage image = new BufferedImage(100, 120, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Enable anti-aliasing for smoother drawing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw document shape - light blue gradient background
        GradientPaint backgroundGradient = new GradientPaint(
            0, 0, new Color(240, 240, 255), 
            0, 120, new Color(220, 220, 240)
        );
        g2d.setPaint(backgroundGradient);
        g2d.fillRect(5, 5, 90, 110);

        // Document border
        g2d.setColor(new Color(100, 100, 180));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(5, 5, 90, 110);

        // Corner fold
        int[] xPoints = {75, 95, 95};
        int[] yPoints = {5, 5, 25};
        g2d.setColor(new Color(200, 200, 220));
        g2d.fillPolygon(xPoints, yPoints, 3);
        g2d.setColor(new Color(100, 100, 180));
        g2d.drawPolyline(xPoints, yPoints, 3);

        // Text lines
        g2d.setColor(new Color(60, 60, 100));
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(20, 30, 80, 30);
        g2d.drawLine(20, 45, 80, 45);
        g2d.drawLine(20, 60, 80, 60);
        g2d.drawLine(20, 75, 65, 75);
        g2d.drawLine(20, 90, 75, 90);

        // "CV" text
        Font cvFont = new Font("Arial", Font.BOLD, 24);
        g2d.setFont(cvFont);
        g2d.setColor(new Color(70, 70, 120, 180));
        g2d.drawString("CV", 40, 105);

        g2d.dispose();
        return new ImageIcon(image);
    }

    private void exportToPDF() {
        if (textPane.getDocument().getLength() == 0) {
            JOptionPane.showMessageDialog(this,
                "No resume content to export!",
                "Export Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String selectedFileName = resumeList.getSelectedValue();
        String defaultName = (selectedFileName != null) ?
            selectedFileName.replace(".txt", ".pdf") : "resume.pdf";

        // Create a File object with the default path in the created_resumes directory
        File defaultFile = new File(currentDirectory, defaultName);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save PDF As");
        fileChooser.setSelectedFile(defaultFile);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "PDF Documents", "pdf"));
        fileChooser.setCurrentDirectory(currentDirectory);

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            // Ensure file has .pdf extension
            if (!selectedFile.getName().toLowerCase().endsWith(".pdf")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".pdf");
            }

            // Check if file exists
            if (selectedFile.exists()) {
                result = JOptionPane.showConfirmDialog(this,
                    "File already exists. Do you want to replace it?",
                    "File Exists", JOptionPane.YES_NO_OPTION);
                if (result != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            // Store final reference to the selected file for inner class access
            final File pdfFile = selectedFile;

            // Show progress indicator
            final JDialog progressDialog = new JDialog(this, "Exporting PDF", true);
            JProgressBar progressBar = new JProgressBar();
            progressBar.setIndeterminate(true);
            progressBar.setStringPainted(true);
            progressBar.setString("Creating PDF file...");

            JPanel progressPanel = new JPanel(new BorderLayout(10, 10));
            progressPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            progressPanel.add(new JLabel("Please wait while exporting PDF..."), BorderLayout.NORTH);
            progressPanel.add(progressBar, BorderLayout.CENTER);

            progressDialog.getContentPane().add(progressPanel);
            progressDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            progressDialog.setSize(300, 100);
            progressDialog.setLocationRelativeTo(this);

            // Start export in background thread to keep UI responsive
            SwingWorker<Boolean, Void> exportWorker = new SwingWorker<Boolean, Void>() {
                @Override
                protected Boolean doInBackground() {
                    return printToPDF(pdfFile);
                }

                @Override
                protected void done() {
                    progressDialog.dispose();
                    try {
                        if (get()) {
                            JOptionPane.showMessageDialog(ResumeViewer.this,
                                "PDF file was successfully created:\n" + pdfFile.getAbsolutePath(),
                                "PDF Created", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(ResumeViewer.this,
                            "Error creating PDF: " + e.getMessage(),
                            "Export Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };

            exportWorker.execute();
            progressDialog.setVisible(true); // Will block until disposed in done()
        }
    }

    private boolean printToPDF(File outputFile) {
        try {
            // Create a print job
            PrinterJob job = PrinterJob.getPrinterJob();

            // Create a final reference to textPane for inner class access
            final JTextPane finalTextPane = textPane;

            job.setPrintable(new Printable() {
                public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
                    if (pageIndex > 0) {
                        return Printable.NO_SUCH_PAGE;
                    }

                    Graphics2D g2d = (Graphics2D) graphics;
                    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                    // Scale to fit the page if needed
                    double scaleX = pageFormat.getImageableWidth() / finalTextPane.getWidth();
                    double scaleY = pageFormat.getImageableHeight() / finalTextPane.getHeight();
                    double scale = Math.min(scaleX, scaleY);

                    if (scale < 1.0) {
                        g2d.scale(scale, scale);
                    }

                    // Apply formatting and print content
                    finalTextPane.print(g2d);
                    return Printable.PAGE_EXISTS;
                }
            });

            // Set PDF properties
            PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
            attributes.add(new Destination(outputFile.toURI()));
            attributes.add(OrientationRequested.PORTRAIT);
            attributes.add(MediaSizeName.NA_LETTER);

            // Print to PDF
            job.print(attributes);
            return true;
        } catch (PrinterException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void loadResumeFiles() {
        // Clear existing list
        listModel.clear();
        
        // Load files from the created_resumes directory
        File[] files = currentDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (files != null && files.length > 0) {
            for (File file : files) {
                listModel.addElement(file.getName());
            }
            resumeList.setSelectedIndex(0);
            // Show the first resume after loading
            if (resumeList.getSelectedValue() != null) {
                loadSelectedResume();
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "No resume files found in the created_resumes directory.\nUse File > Open to select a resume.",
                "No Resumes Found", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void loadSelectedResume() {
        String selectedFileName = resumeList.getSelectedValue();
        if (selectedFileName == null) return;

        File selectedFile = new File(currentDirectory, selectedFileName);
        loadResumeFile(selectedFile);
    }

    private void loadResumeFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            // Format the text
            textPane.setText("");
            StyledDocument doc = textPane.getStyledDocument();
            doc.insertString(0, content.toString(), null);

            // Apply formatting based on text structure
            applyBasicFormatting(doc, content.toString());

            // Show the viewer with animation
            animateCardTransition("viewer");

            // Update window title
            setTitle("Enhanced Resume Viewer - " + file.getName());

        } catch (IOException | BadLocationException e) {
            JOptionPane.showMessageDialog(this, 
                "Error reading the file: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void applyBasicFormatting(StyledDocument doc, String text) {
        // This is a simple formatting example, can be expanded
        try {
            // Style for headings (assume uppercase text blocks are headings)
            Style headingStyle = doc.addStyle("heading", null);
            StyleConstants.setFontSize(headingStyle, 18);
            StyleConstants.setBold(headingStyle, true);

            // Style for subheadings
            Style subheadingStyle = doc.addStyle("subheading", null);
            StyleConstants.setFontSize(subheadingStyle, 16);
            StyleConstants.setItalic(subheadingStyle, true);
            StyleConstants.setBold(subheadingStyle, true);

            // Find and format headings
            String[] lines = text.split("\n");
            int pos = 0;

            for (String line : lines) {
                int lineLength = line.length();

                // Simple heuristics for style application
                if (lineLength > 0) {
                    if (line.equals(line.toUpperCase()) && !line.trim().isEmpty()) {
                        // Uppercase lines are likely headers
                        doc.setCharacterAttributes(pos, lineLength, headingStyle, false);
                    } else if (line.trim().endsWith(":")) {
                        // Lines ending with : are likely subheadings
                        doc.setCharacterAttributes(pos, lineLength, subheadingStyle, false);
                    }
                }
                pos += lineLength + 1; // +1 for the newline
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void animateCardTransition(String cardName) {
        Timer timer = new Timer(20, new ActionListener() {
            private float alpha = 0.0f;
            private String targetCard = cardName;

            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += 0.1f;
                if (alpha >= 1.0f) {
                    cardLayout.show(cardPanel, targetCard);
                    ((Timer)e.getSource()).stop();
                }
            }
        });
        timer.start();
    }

    private void filterResumeList() {
        String searchText = searchField.getText().toLowerCase();
        List<String> filteredItems = new ArrayList<>();

        // Save selected item
        String selectedItem = resumeList.getSelectedValue();

        // Clear and refill the list model
        listModel.clear();

        File[] files = currentDirectory.listFiles((dir, name) -> 
            name.toLowerCase().endsWith(".txt") && name.toLowerCase().contains(searchText));

        if (files != null) {
            for (File file : files) {
                listModel.addElement(file.getName());
                filteredItems.add(file.getName());
            }
        }

        // Restore selection if possible
        if (selectedItem != null && filteredItems.contains(selectedItem)) {
            resumeList.setSelectedValue(selectedItem, true);
        } else if (!listModel.isEmpty()) {
            resumeList.setSelectedIndex(0);
        }
    }

    private void openResumeFile() {
        JFileChooser fileChooser = new JFileChooser(currentDirectory);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Text Files", "txt"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            // Don't change the current directory anymore, stay in created_resumes
            loadResumeFile(selectedFile);

            // Add to list if not already there
            String fileName = selectedFile.getName();
            if (!listContains(fileName)) {
                listModel.addElement(fileName);
                resumeList.setSelectedValue(fileName, true);
            }
        }
    }

    private boolean listContains(String fileName) {
        for (int i = 0; i < listModel.size(); i++) {
            if (listModel.getElementAt(i).equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    private void printResume() {
        try {
            textPane.print();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error printing: " + e.getMessage(),
                "Print Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void toggleDarkMode() {
        boolean isDarkMode = textPane.getBackground().equals(DARK_BACKGROUND);
        applyTheme(!isDarkMode);
    }

    private void applyTheme(boolean isDarkMode) {
        Color bgColor, panelBgColor, textColor;

        if (isDarkMode) {
            bgColor = DARK_BACKGROUND;
            panelBgColor = DARK_PANEL_BACKGROUND;
            textColor = DARK_TEXT;
        } else {
            bgColor = LIGHT_BACKGROUND;
            panelBgColor = LIGHT_PANEL_BACKGROUND;
            textColor = LIGHT_TEXT;
        }

        // Apply colors to components
        getContentPane().setBackground(bgColor);

        // Text pane colors
        textPane.setBackground(bgColor);
        textPane.setForeground(textColor);

        // List colors
        resumeList.setBackground(panelBgColor);
        resumeList.setForeground(textColor);

        // Search field
        searchField.setBackground(bgColor);
        searchField.setForeground(textColor);
        searchField.setCaretColor(textColor);

        // Find all panels and update their colors
        updateComponentsRecursively(this, bgColor, panelBgColor, textColor);
    }

    private void updateComponentsRecursively(Container container, Color bgColor, Color panelBgColor, Color textColor) {
        for (Component c : container.getComponents()) {
            if (c instanceof JPanel) {
                c.setBackground(panelBgColor);
            } else {
                c.setBackground(bgColor);

                if (c instanceof JLabel) {
                    ((JLabel) c).setForeground(textColor);
                }

                if (c instanceof JButton) {
                    ((JButton) c).setForeground(textColor);
                }
            }

            if (c instanceof Container) {
                updateComponentsRecursively((Container) c, bgColor, panelBgColor, textColor);
            }
        }
    }

    private void updateFontStyle() {
        String fontName = (String)fontSelector.getSelectedItem();
        Integer fontSize = (Integer)fontSizeSelector.getSelectedItem();

        if (fontName != null && fontSize != null) {
            Font newFont = new Font(fontName, Font.PLAIN, fontSize);
            textPane.setFont(newFont);
        }
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
            "Enhanced Resume Viewer\n" +
            "Version 1.0\n\n" +
            "A user-friendly application for viewing and managing resumes.\n" +
            "© 2023 Resume Builder Team",
            "About Resume Viewer", JOptionPane.INFORMATION_MESSAGE);
    }

    // Custom renderer for the resume list
    private class ResumeListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);

            label.setIcon(new ImageIcon(createDocumentIcon()));
            label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            return label;
        }

        private Image createDocumentIcon() {
            BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, 16, 16);
            g2d.setColor(Color.GRAY);
            g2d.drawRect(0, 0, 15, 15);
            g2d.drawLine(3, 5, 13, 5);
            g2d.drawLine(3, 8, 13, 8);
            g2d.drawLine(3, 11, 10, 11);
            g2d.dispose();
            return image;
        }
    }

    public static void main(String[] args) {
        // Use EventQueue for thread safety
        EventQueue.invokeLater(() -> new ResumeViewer());
    }
}