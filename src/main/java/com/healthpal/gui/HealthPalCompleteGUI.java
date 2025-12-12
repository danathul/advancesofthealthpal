package com.healthpal.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HealthPalCompleteGUI extends JFrame {

    // 🎨 Modern Color Palette
    private final Color PRIMARY = new Color(99, 102, 241);
    private final Color SECONDARY = new Color(236, 72, 153); // pink for sidebar hover
    private final Color SUCCESS = new Color(16, 185, 129);
    private final Color WARNING = new Color(245, 158, 11);
    private final Color DANGER = new Color(239, 68, 68);
    private final Color INFO = new Color(59, 130, 246);
    private final Color DARK = new Color(30, 30, 30);
    private final Color LIGHT = new Color(249, 250, 251);
    private final Color WHITE = Color.WHITE;

    // Components
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextArea logArea;
    private JTextField emailField;
    private JPasswordField passField;
    private Integer currentUserId = 1;
    private String currentUserRole = "PATIENT";

    // HTTP Client
    private final String BASE_URL = "http://localhost:8081/api/v1";
    private final HttpClient client;

    public HealthPalCompleteGUI() {
        setTitle("HealthPal - Complete Healthcare Platform");
        setSize(1400, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);

        client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        addWindowDragListener();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);

        createLoginScreen();
        createDashboardScreen();

        cardLayout.show(mainPanel, "LOGIN");
    }

    // ==================== CUSTOM COMPONENTS ====================
    class GradientPanel extends JPanel {
        private Color c1, c2;

        public GradientPanel(Color c1, Color c2) {
            this.c1 = c1;
            this.c2 = c2;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            GradientPaint gp = new GradientPaint(0, 0, c1, 0, getHeight(), c2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    class RoundedPanel extends JPanel {
        private int radius;
        private Color bg;

        public RoundedPanel(int radius, Color bg) {
            this.radius = radius;
            this.bg = bg;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(bg);
            g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
            super.paintComponent(g);
        }
    }

    // ==================== LOGIN SCREEN ====================
    private void createLoginScreen() {
        GradientPanel loginPanel = new GradientPanel(PRIMARY, SECONDARY);
        loginPanel.setLayout(new GridBagLayout());

        JButton closeBtn = createIconButton("✕", e -> System.exit(0));
        closeBtn.setForeground(WHITE);
        closeBtn.setBackground(new Color(255, 255, 255, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(15, 0, 0, 15);
        gbc.weightx = 1;
        loginPanel.add(closeBtn, gbc);

        RoundedPanel card = new RoundedPanel(25, WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(50, 60, 50, 60));
        card.setPreferredSize(new Dimension(450, 580));

        JLabel icon = new JLabel("💖", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 72));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(icon);

        card.add(Box.createVerticalStrut(20));

        JLabel title = new JLabel("Welcome to HealthPal", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(DARK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(title);

        card.add(Box.createVerticalStrut(10));

        JLabel subtitle = new JLabel("Your Complete Healthcare Platform", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(new Color(100, 100, 100));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(subtitle);

        card.add(Box.createVerticalStrut(40));

        JLabel emailLabel = new JLabel("Email Address");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        emailLabel.setForeground(DARK);
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(emailLabel);

        card.add(Box.createVerticalStrut(8));

        emailField = new JTextField("ahmad@example.com");
        styleModernField(emailField);
        emailField.setMaximumSize(new Dimension(330, 45));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(emailField);

        card.add(Box.createVerticalStrut(25));

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passLabel.setForeground(DARK);
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(passLabel);

        card.add(Box.createVerticalStrut(8));

        passField = new JPasswordField("password123");
        styleModernField(passField);
        passField.setMaximumSize(new Dimension(330, 45));
        passField.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(passField);

        card.add(Box.createVerticalStrut(35));

        JButton loginBtn = createModernButton("Sign In", PRIMARY, WHITE);
        loginBtn.setMaximumSize(new Dimension(330, 50));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.addActionListener(e -> performLogin());
        card.add(loginBtn);

        card.add(Box.createVerticalStrut(15));

        JButton regBtn = createModernButton("Create Account", SECONDARY, WHITE);
        regBtn.setMaximumSize(new Dimension(330, 50));
        regBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        regBtn.addActionListener(e -> showRegisterDialog());
        card.add(regBtn);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 1;
        loginPanel.add(card, gbc);

        mainPanel.add(loginPanel, "LOGIN");
    }

    // ==================== DASHBOARD SCREEN ====================
    private void createDashboardScreen() {
        GradientPanel dashboard = new GradientPanel(
                new Color(240, 242, 255),
                new Color(250, 245, 255)
        );
        dashboard.setLayout(new BorderLayout());

        // Header
        JPanel header = createHeader();
        dashboard.add(header, BorderLayout.NORTH);

        // Sidebar + Content
        JPanel contentArea = new JPanel(new BorderLayout(0, 0));
        contentArea.setOpaque(false);

        JComponent sidebarScroll = createSidebar(); // returns JScrollPane
        contentArea.add(sidebarScroll, BorderLayout.WEST);

        JPanel mainContent = new JPanel(new BorderLayout(10, 10));
        mainContent.setOpaque(false);
        mainContent.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel statsPanel = createStatsPanel();
        mainContent.add(statsPanel, BorderLayout.NORTH);

        JPanel logPanel = createLogPanel();
        mainContent.add(logPanel, BorderLayout.CENTER);

        contentArea.add(mainContent, BorderLayout.CENTER);
        dashboard.add(contentArea, BorderLayout.CENTER);

        mainPanel.add(dashboard, "DASHBOARD");
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(20, 30, 20, 30));

        JPanel headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        headerLeft.setOpaque(false);

        JLabel logo = new JLabel("💖 HealthPal Platform");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logo.setForeground(PRIMARY);
        headerLeft.add(logo);

        header.add(headerLeft, BorderLayout.WEST);

        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        headerRight.setOpaque(false);

        JLabel userLabel = new JLabel("User ID: " + currentUserId);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(DARK);
        headerRight.add(userLabel);

        JButton logoutBtn = createModernButton("Logout", DANGER, WHITE);
        logoutBtn.addActionListener(e -> cardLayout.show(mainPanel, "LOGIN"));

        JButton closeBtn = createIconButton("✕", e -> System.exit(0));
        closeBtn.setBackground(DANGER);
        closeBtn.setForeground(WHITE);

        headerRight.add(logoutBtn);
        headerRight.add(closeBtn);
        header.add(headerRight, BorderLayout.EAST);

        return header;
    }

    /**
     * Creates a scrollable sidebar (JScrollPane) containing the sidebar buttons and sections.
     * The returned component is the JScrollPane itself to be added to the main layout (WEST).
     */
    /**
     * Creates a scrollable sidebar (JScrollPane) containing the sidebar buttons and sections.
     * The returned component is the JScrollPane itself to be added to the main layout (WEST).
     */
    private JComponent createSidebar() {
        // Create a container panel that will hold all sidebar content
        JPanel sidebarContainer = new JPanel();
        sidebarContainer.setLayout(new BoxLayout(sidebarContainer, BoxLayout.Y_AXIS));
        sidebarContainer.setBackground(WHITE);
        sidebarContainer.setBorder(new EmptyBorder(20, 10, 20, 10));

        String[][] features = {
                {"👤 PROFILE", "Profile"},
                {"📅 CONSULTATIONS", null},
                {"Book Appointment", "bookAppointment"},
                {"View Appointments", "viewAppointments"},
                {"Send Message", "sendMessage"},
                {"🗣️ Translate Text", "translate"},
                {"💬 Support Groups", "supportGroups"},
                {"💰 SPONSORSHIP", null},
                {"View Cases", "viewCases"},
                {"Donate", "donate"},
                {"Medicine Requests", "medicineRequests"},
                {"Equipment", "equipment"},
                {"📚 CONTENT", null},
                {"Health Guides", "healthGuides"},
                {"Webinars", "webinars"},
                {"Workshops", "workshops"},
                {"Medical Missions", "missions"},
                {"🌍 PUBLIC HEALTH", null},
                {"Weather & Health", "weather"},
                {"Health Alerts", "alerts"}
        };

        for (String[] feature : features) {
            if (feature[1] == null) {
                // Section header
                JLabel sectionLabel = new JLabel(feature[0]);
                sectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
                sectionLabel.setForeground(new Color(100, 100, 100));
                sectionLabel.setBorder(new EmptyBorder(15, 10, 5, 10));
                sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                sidebarContainer.add(sectionLabel);
            } else {
                // Feature button
                JButton btn = createSidebarButton(feature[0], feature[1]);
                btn.setAlignmentX(Component.LEFT_ALIGNMENT);
                sidebarContainer.add(btn);
                sidebarContainer.add(Box.createVerticalStrut(5));
            }
        }

        // Add some padding at the bottom so the last item isn't cut off
        sidebarContainer.add(Box.createVerticalStrut(20));

        // Wrap in a scroll pane
        JScrollPane scroll = new JScrollPane(sidebarContainer);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(WHITE);

        // Set fixed width, but allow height to be flexible
        scroll.setPreferredSize(new Dimension(280, 0));
        scroll.setMinimumSize(new Dimension(280, 0));

        // Smooth scrolling
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.getVerticalScrollBar().setBlockIncrement(50);

        // Style the scrollbar
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        scroll.getVerticalScrollBar().setBackground(new Color(240, 240, 240));

        return scroll;
    }
    private JButton createSidebarButton(String text, String action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(DARK);
        btn.setBackground(LIGHT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(260, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(10, 15, 10, 15));
        btn.setOpaque(true);

        // store original colors
        Color origBg = btn.getBackground();
        Color origFg = btn.getForeground();

        // Hover behavior: only for sidebar buttons -> pink background + white text
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(SECONDARY);
                btn.setForeground(WHITE);
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(origBg);
                btn.setForeground(origFg);
            }
        });

        btn.addActionListener(e -> executeAction(action));

        return btn;
    }

    private void executeAction(String action) {
        switch (action) {
            case "Profile": showProfile(); break;
            case "bookAppointment": showBookAppointmentDialog(); break;
            case "viewAppointments": viewAppointments(); break;
            case "sendMessage": showSendMessageDialog(); break;
            case "translate": showTranslateDialog(); break;
            case "supportGroups": showSupportGroupsDialog(); break;
            case "viewCases": viewTreatmentCases(); break;
            case "donate": showDonateDialog(); break;
            case "medicineRequests": showMedicineRequestDialog(); break;
            case "equipment": viewEquipment(); break;
            case "healthGuides": viewHealthGuides(); break;
            case "webinars": viewWebinars(); break;
            case "workshops": viewWorkshops(); break;
            case "missions": viewMissions(); break;
            case "weather": showWeatherDialog(); break;
            case "alerts": viewHealthAlerts(); break;
        }
    }

    private JPanel createStatsPanel() {
        JPanel stats = new JPanel(new GridLayout(1, 4, 15, 0));
        stats.setOpaque(false);
        stats.setPreferredSize(new Dimension(0, 120));

        addStatCard(stats, "📅", "Appointments", "12", PRIMARY);
        addStatCard(stats, "💰", "Donations", "5", SUCCESS);
        addStatCard(stats, "📚", "Guides", "24", INFO);
        addStatCard(stats, "🌍", "Active Alerts", "3", WARNING);

        return stats;
    }

    private void addStatCard(JPanel parent, String icon, String label, String value, Color color) {
        RoundedPanel card = new RoundedPanel(15, WHITE);
        card.setLayout(new BorderLayout(10, 10));
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        iconLabel.setForeground(color);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(DARK);

        JLabel labelLabel = new JLabel(label);
        labelLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        labelLabel.setForeground(new Color(100, 100, 100));

        textPanel.add(valueLabel);
        textPanel.add(labelLabel);

        card.add(iconLabel, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);

        parent.add(card);
    }

    private JPanel createLogPanel() {
        RoundedPanel logPanel = new RoundedPanel(20, WHITE);
        logPanel.setLayout(new BorderLayout());
        logPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel logTitle = new JLabel("Activity Log");
        logTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        logTitle.setForeground(DARK);
        logPanel.add(logTitle, BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        logArea.setBackground(LIGHT);
        logArea.setForeground(DARK);
        logArea.setText("✨ Welcome! System ready...\n");
        logArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        logPanel.add(scroll, BorderLayout.CENTER);

        return logPanel;
    }

    // ==================== FEATURE IMPLEMENTATIONS ====================
    private void showProfile() {
        sendRequest("/auth/profile/" + currentUserId, "GET", null, null);
    }

    private void showBookAppointmentDialog() {
        JDialog dialog = createStyledDialog("Book Appointment", 500, 450);
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(WHITE);

        JTextField doctorIdField = new JTextField("1");
        JTextField dateTimeField = new JTextField("2025-12-25T14:30:00");
        JComboBox<String> modeBox = new JComboBox<>(new String[]{"VIDEO", "AUDIO", "CHAT"});
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"PHYSICAL", "MENTAL"});
        JTextField notesField = new JTextField("Routine checkup");

        panel.add(new JLabel("Doctor ID:"));
        panel.add(doctorIdField);
        panel.add(new JLabel("Date & Time:"));
        panel.add(dateTimeField);
        panel.add(new JLabel("Mode:"));
        panel.add(modeBox);
        panel.add(new JLabel("Type:"));
        panel.add(typeBox);
        panel.add(new JLabel("Notes:"));
        panel.add(notesField);

        JButton submitBtn = createModernButton("Book Appointment", PRIMARY, WHITE);
        submitBtn.addActionListener(e -> {
            String json = String.format(
                    "{\"patientId\":%d,\"doctorId\":%s,\"dateTime\":\"%s\",\"mode\":\"%s\",\"type\":\"%s\",\"notes\":\"%s\"}",
                    currentUserId, doctorIdField.getText(), dateTimeField.getText(),
                    modeBox.getSelectedItem(), typeBox.getSelectedItem(), notesField.getText()
            );
            sendRequest("/consultations/appointments", "POST", json, null);
            dialog.dispose();
        });

        panel.add(new JLabel());
        panel.add(submitBtn);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void viewAppointments() {
        String[] columns = {"ID", "Doctor", "Date", "Status", "Mode"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        showTableDialog("My Appointments", model, 700, 400);

        model.addRow(new Object[]{1, "Dr. Smith", "2025-12-25", "PENDING", "VIDEO"});
        model.addRow(new Object[]{2, "Dr. Jones", "2025-12-26", "APPROVED", "AUDIO"});
    }

    private void showSendMessageDialog() {
        JDialog dialog = createStyledDialog("Send Message", 450, 300);
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(WHITE);

        JTextField apptIdField = new JTextField("1");
        JTextArea contentArea = new JTextArea(3, 20);
        contentArea.setLineWrap(true);

        panel.add(new JLabel("Appointment ID:"));
        panel.add(apptIdField);
        panel.add(new JLabel("Message:"));
        panel.add(new JScrollPane(contentArea));

        JButton sendBtn = createModernButton("Send Message", PRIMARY, WHITE);
        sendBtn.addActionListener(e -> {
            String json = String.format(
                    "{\"appointmentId\":%s,\"senderId\":%d,\"content\":\"%s\"}",
                    apptIdField.getText(), currentUserId, contentArea.getText()
            );
            sendRequest("/consultations/messages", "POST", json, null);
            dialog.dispose();
        });

        panel.add(new JLabel());
        panel.add(sendBtn);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showTranslateDialog() {
        JDialog dialog = createStyledDialog("Translate Text", 450, 300);
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(WHITE);

        JTextArea inputArea = new JTextArea(5, 30);
        inputArea.setLineWrap(true);
        JTextArea outputArea = new JTextArea(5, 30);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);

        JPanel langPanel = new JPanel(new FlowLayout());
        langPanel.setBackground(WHITE);
        JComboBox<String> fromLang = new JComboBox<>(new String[]{"ar", "en"});
        JComboBox<String> toLang = new JComboBox<>(new String[]{"en", "ar"});
        langPanel.add(new JLabel("From:"));
        langPanel.add(fromLang);
        langPanel.add(new JLabel("To:"));
        langPanel.add(toLang);

        JButton translateBtn = createModernButton("Translate", PRIMARY, WHITE);
        translateBtn.addActionListener(e -> {
            String json = String.format(
                    "{\"text\":\"%s\",\"sourceLang\":\"%s\",\"targetLang\":\"%s\"}",
                    inputArea.getText(), fromLang.getSelectedItem(), toLang.getSelectedItem()
            );
            sendRequest("/consultations/translate", "POST", json, response -> {
                SwingUtilities.invokeLater(() -> outputArea.setText(response));
            });
        });

        panel.add(new JLabel("Input Text:"), BorderLayout.NORTH);
        panel.add(new JScrollPane(inputArea), BorderLayout.CENTER);
        panel.add(langPanel, BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(WHITE);
        bottomPanel.add(translateBtn, BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        dialog.add(panel, BorderLayout.NORTH);
        dialog.add(bottomPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private void showSupportGroupsDialog() {
        JDialog dialog = createStyledDialog("Create Support Group", 450, 300);
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(WHITE);

        JTextField nameField = new JTextField();
        JTextArea descArea = new JTextArea(3, 20);
        descArea.setLineWrap(true);

        panel.add(new JLabel("Group Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(descArea));

        JButton createBtn = createModernButton("Create Group", SUCCESS, WHITE);
        createBtn.addActionListener(e -> {
            String json = String.format(
                    "{\"name\":\"%s\",\"description\":\"%s\",\"createdBy\":%d}",
                    nameField.getText(), descArea.getText(), currentUserId
            );
            sendRequest("/consultations/support-groups", "POST", json, null);
            dialog.dispose();
        });

        panel.add(new JLabel());
        panel.add(createBtn);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void viewTreatmentCases() {
        sendRequest("/sponsorship-resources/cases", "GET", null, null);
    }

    private void showDonateDialog() {
        JDialog dialog = createStyledDialog("Make Donation", 450, 350);
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(WHITE);

        JTextField caseIdField = new JTextField();
        JTextField amountField = new JTextField();
        JCheckBox anonymousBox = new JCheckBox("Anonymous");
        JTextField messageField = new JTextField();

        panel.add(new JLabel("Case ID:"));
        panel.add(caseIdField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(new JLabel("Anonymous:"));
        panel.add(anonymousBox);
        panel.add(new JLabel("Message:"));
        panel.add(messageField);

        JButton donateBtn = createModernButton("Donate", SUCCESS, WHITE);
        donateBtn.addActionListener(e -> {
            String json = String.format(
                    "{\"caseId\":%s,\"donorId\":%d,\"amount\":%s,\"anonymous\":%b,\"messageToPatient\":\"%s\"}",
                    caseIdField.getText(), currentUserId, amountField.getText(),
                    anonymousBox.isSelected(), messageField.getText()
            );
            sendRequest("/sponsorship-resources/donations", "POST", json, null);
            dialog.dispose();
        });

        panel.add(new JLabel());
        panel.add(donateBtn);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showMedicineRequestDialog() {
        JDialog dialog = createStyledDialog("Request Medicine", 450, 400);
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(WHITE);

        JTextField medNameField = new JTextField();
        JTextField qtyField = new JTextField();
        JTextField dosageField = new JTextField();
        JTextField locationField = new JTextField();
        JTextField phoneField = new JTextField();

        panel.add(new JLabel("Medicine Name:"));
        panel.add(medNameField);
        panel.add(new JLabel("Quantity:"));
        panel.add(qtyField);
        panel.add(new JLabel("Dosage:"));
        panel.add(dosageField);
        panel.add(new JLabel("Location:"));
        panel.add(locationField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);

        JButton requestBtn = createModernButton("Request", WARNING, WHITE);
        requestBtn.addActionListener(e -> {
            String json = String.format(
                    "{\"patientId\":%d,\"medicineName\":\"%s\",\"quantity\":%s,\"dosage\":\"%s\",\"location\":\"%s\",\"phone\":\"%s\"}",
                    currentUserId, medNameField.getText(), qtyField.getText(),
                    dosageField.getText(), locationField.getText(), phoneField.getText()
            );
            sendRequest("/sponsorship-resources/medicine-requests", "POST", json, null);
            dialog.dispose();
        });

        panel.add(new JLabel());
        panel.add(requestBtn);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void viewEquipment() {
        sendRequest("/sponsorship-resources/equipment", "GET", null, null);
    }

    private void viewHealthGuides() {
        sendRequest("/content/guides", "GET", null, null);
    }

    private void viewWebinars() {
        sendRequest("/content/webinars", "GET", null, null);
    }

    private void viewWorkshops() {
        sendRequest("/content/workshops", "GET", null, null);
    }

    private void viewMissions() {
        sendRequest("/content/missions", "GET", null, null);
    }

    private void showWeatherDialog() {
        String city = JOptionPane.showInputDialog(this, "Enter City Name:");
        if (city != null && !city.trim().isEmpty()) {
            sendRequest("/external/weather/" + city, "GET", null, null);
        }
    }

    private void viewHealthAlerts() {
        sendRequest("/alerts", "GET", null, null);
    }

    private void showRegisterDialog() {
        JDialog dialog = createStyledDialog("Create Account", 500, 550);
        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(WHITE);

        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JTextField phoneField = new JTextField();
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"PATIENT", "DOCTOR", "DONOR"});
        JTextField ageField = new JTextField();
        JComboBox<String> genderBox = new JComboBox<>(new String[]{"M", "F", "Other"});
        JTextField specializationField = new JTextField();

        panel.add(new JLabel("Full Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Role:"));
        panel.add(roleBox);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Gender:"));
        panel.add(genderBox);
        panel.add(new JLabel("Specialization:"));
        panel.add(specializationField);

        JButton registerBtn = createModernButton("Register", SUCCESS, WHITE);
        registerBtn.addActionListener(e -> {
            String json = String.format(
                    "{\"name\":\"%s\",\"email\":\"%s\",\"password\":\"%s\",\"phone\":\"%s\"," +
                            "\"role\":\"%s\",\"age\":%s,\"gender\":\"%s\",\"specialization\":\"%s\"}",
                    nameField.getText(), emailField.getText(), new String(passField.getPassword()),
                    phoneField.getText(), roleBox.getSelectedItem(),
                    ageField.getText().isEmpty() ? "null" : ageField.getText(),
                    genderBox.getSelectedItem(), specializationField.getText()
            );
            sendRequest("/auth/register", "POST", json, null);
            dialog.dispose();
        });

        panel.add(new JLabel());
        panel.add(registerBtn);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    // ==================== HELPER METHODS ====================
    private JDialog createStyledDialog(String title, int width, int height) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(width, height);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(PRIMARY, 2));
        return dialog;
    }

    private void showTableDialog(String title, DefaultTableModel model, int width, int height) {
        JDialog dialog = createStyledDialog(title, width, height);
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane scroll = new JScrollPane(table);
        dialog.add(scroll);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(WHITE);
        JButton closeBtn = createModernButton("Close", DANGER, WHITE);
        closeBtn.addActionListener(e -> dialog.dispose());
        btnPanel.add(closeBtn);

        dialog.add(btnPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void performLogin() {
        String email = emailField.getText();
        String pass = new String(passField.getPassword());
        String json = String.format("{\"email\":\"%s\", \"password\":\"%s\"}", email, pass);

        sendRequest("/auth/login", "POST", json, response -> {
            // a very simple success check; in a real app parse JSON and check token/status
            if (response != null && response.contains("\"id\"")) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "✅ Login Successful!");
                    cardLayout.show(mainPanel, "DASHBOARD");
                });
            } else {
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(this, "❌ Login Failed!"));
            }
        });
    }

    private void sendRequest(String endpoint, String method, String jsonBody,
                             java.util.function.Consumer<String> callback) {
        if (logArea != null) logArea.append("⏳ " + method + " " + endpoint + "\n");

        new Thread(() -> {
            try {
                HttpRequest.Builder builder = HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL + endpoint));

                if ("POST".equals(method) || "PUT".equals(method)) {
                    builder.method(method, HttpRequest.BodyPublishers.ofString(jsonBody))
                            .header("Content-Type", "application/json");
                } else {
                    builder.GET();
                }

                HttpResponse<String> response = client.send(builder.build(),
                        HttpResponse.BodyHandlers.ofString());
                String body = response.body();

                SwingUtilities.invokeLater(() -> {
                    if (logArea != null) {
                        logArea.append("✅ Status: " + response.statusCode() + "\n");
                        logArea.append("📦 " + (body.length() > 200 ?
                                body.substring(0, 200) + "..." : body) + "\n");
                        logArea.append("─────────────────────\n");
                    }
                    if (callback != null) callback.accept(body);
                });

            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    if (logArea != null) logArea.append("❌ Error: " + e.getMessage() + "\n");
                });
            }
        }).start();
    }

    private JButton createModernButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bg.darker());
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(bg);
            }
        });

        return btn;
    }

    private JButton createIconButton(String text, ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setPreferredSize(new Dimension(45, 45));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(action);
        return btn;
    }

    private void styleModernField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219), 2),
                new EmptyBorder(10, 15, 10, 15)
        ));
        field.setBackground(LIGHT);
        field.setForeground(DARK);
    }

    private void addWindowDragListener() {
        final Point[] mouseDownCompCoords = new Point[1];

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords[0] = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                setLocation(currCoords.x - mouseDownCompCoords[0].x,
                        currCoords.y - mouseDownCompCoords[0].y);
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            HealthPalCompleteGUI gui = new HealthPalCompleteGUI();
            gui.setVisible(true);
        });
    }
}
