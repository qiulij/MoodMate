package com.moodmate.logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BasePage extends JFrame {
    // Constants for layout sizes
    private static final int FRAME_WIDTH = 360; // Width of the main frame
    private static final int FRAME_HEIGHT = 780; // Height of the main frame

    private static final int STATUS_ICON_SIZE = 15; // Size of status bar icons

    // Constants for bar heights
    private static final int STATUS_BAR_HEIGHT = 30;
    private static final int NAV_BAR_HEIGHT = 30;

    // Constants for top panel dimensions
    private static final int TOP_PANEL_HEIGHT = 40; // Reduced height
    private static final int ICON_SIZE = 20; // Profile icon size

    // Constants for menu bar
    private static final int MENU_BAR_WIDTH = 200; // Width of the sliding menu bar

    protected JPanel statusBar;
    protected JPanel navigationBar;
    protected JLayeredPane layeredPane; // For overlay effect
    protected JPanel contentArea;
    protected JPanel topPanel;
    private JPanel menuBar; // Sliding menu bar

    public BasePage() {
        // Set up the main frame
        setTitle("MoodMate - Android Look");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT); // Use constants for frame dimensions
        setLayout(new BorderLayout());
        setResizable(false); // Prevent resizing

        // -------- Status Bar --------
        statusBar = new JPanel();
        statusBar.setPreferredSize(new Dimension(FRAME_WIDTH, STATUS_BAR_HEIGHT)); // Use constant for height
        statusBar.setBackground(Color.LIGHT_GRAY); // Set a light background
        statusBar.setLayout(new BorderLayout());

        // Time Label (Left)
        JLabel timeLabel = new JLabel();
        timeLabel.setForeground(Color.BLACK);
        timeLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        timeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusBar.add(timeLabel, BorderLayout.WEST);

        // Icons Panel (Right)
        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new BoxLayout(iconPanel, BoxLayout.X_AXIS));
        iconPanel.setBackground(Color.LIGHT_GRAY);

        // Add icons with resizing
        iconPanel.add(resizeIcon("assets/images/wifi.png", STATUS_ICON_SIZE, STATUS_ICON_SIZE));
        iconPanel.add(Box.createHorizontalStrut(5));
        iconPanel.add(resizeIcon("assets/images/bluetooth.png", STATUS_ICON_SIZE, STATUS_ICON_SIZE));
        iconPanel.add(Box.createHorizontalStrut(5));
        iconPanel.add(resizeIcon("assets/images/signal.png", STATUS_ICON_SIZE, STATUS_ICON_SIZE));
        iconPanel.add(Box.createHorizontalStrut(5));
        iconPanel.add(resizeIcon("assets/images/battery.png", STATUS_ICON_SIZE, STATUS_ICON_SIZE));

        statusBar.add(iconPanel, BorderLayout.EAST);

        // Timer to update time
        Timer timer = new Timer(1000, e -> timeLabel.setText(getCurrentTime()));
        timer.start();

        // -------- Top Panel --------
        topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(FRAME_WIDTH, TOP_PANEL_HEIGHT)); // Reduced height
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // Padding: 10px left and right

        // Add Logo (Left)
        JLabel logoLabel = resizeIconMaintainAspect("assets/images/logo.png", ICON_SIZE); // Dynamically resize
        topPanel.add(logoLabel, BorderLayout.WEST);

        // Add Profile Icon (Right) with Menu Action
        JLabel profileIcon = resizeIcon("assets/images/profile.png", ICON_SIZE, ICON_SIZE);
        profileIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        profileIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleMenuBar();
            }
        });
        topPanel.add(profileIcon, BorderLayout.EAST);

        // -------- Container Panel --------
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        containerPanel.setPreferredSize(new Dimension(FRAME_WIDTH, STATUS_BAR_HEIGHT + TOP_PANEL_HEIGHT));

        // Add statusBar and topPanel to the container
        containerPanel.add(statusBar, BorderLayout.NORTH);
        containerPanel.add(topPanel, BorderLayout.SOUTH);

        // Add the container panel to the NORTH of the frame
        add(containerPanel, BorderLayout.NORTH);

        // -------- Layered Pane for Overlay --------
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT - STATUS_BAR_HEIGHT - NAV_BAR_HEIGHT - TOP_PANEL_HEIGHT));
        add(layeredPane, BorderLayout.CENTER);

        // -------- Content Area --------
        contentArea = new JPanel();
        contentArea.setBackground(Color.WHITE);
        contentArea.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT - STATUS_BAR_HEIGHT - NAV_BAR_HEIGHT - TOP_PANEL_HEIGHT);
        contentArea.setLayout(new BorderLayout());
        layeredPane.add(contentArea, JLayeredPane.DEFAULT_LAYER);

        // -------- Sliding Menu Bar --------
        menuBar = new JPanel();
        menuBar.setBackground(Color.LIGHT_GRAY);
        menuBar.setBounds(FRAME_WIDTH, 0, MENU_BAR_WIDTH, contentArea.getHeight()); // Dynamically set height
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.Y_AXIS));
        menuBar.add(new JLabel("Menu Item 1"));
        menuBar.add(new JLabel("Menu Item 2"));
        menuBar.add(new JLabel("Menu Item 3"));
        layeredPane.add(menuBar, JLayeredPane.PALETTE_LAYER); // Add to the top layer

        // -------- Navigation Bar --------
        navigationBar = new JPanel();
        navigationBar.setBackground(Color.LIGHT_GRAY);
        navigationBar.setPreferredSize(new Dimension(FRAME_WIDTH, NAV_BAR_HEIGHT)); // Use constant for height
        navigationBar.setLayout(new BoxLayout(navigationBar, BoxLayout.X_AXIS));

        // Add navigation buttons
        JLabel backButton = resizeIcon("assets/images/back.png", STATUS_ICON_SIZE, STATUS_ICON_SIZE);
        navigationBar.add(Box.createHorizontalGlue());
        navigationBar.add(backButton);
        navigationBar.add(Box.createHorizontalStrut(50));

        JLabel homeButton = resizeIcon("assets/images/home.png", STATUS_ICON_SIZE, STATUS_ICON_SIZE);
        navigationBar.add(homeButton);
        navigationBar.add(Box.createHorizontalStrut(50));

        JLabel recentButton = resizeIcon("assets/images/recent.png", STATUS_ICON_SIZE, STATUS_ICON_SIZE);
        navigationBar.add(recentButton);
        navigationBar.add(Box.createHorizontalGlue());

        add(navigationBar, BorderLayout.SOUTH);
    }

    // Method to toggle menu bar visibility
    private void toggleMenuBar() {
        int contentAreaHeight = contentArea.getHeight(); // Dynamically get content area height

        // Check if the menu is open
        if (menuBar.getBounds().x == FRAME_WIDTH) {
            // Slide the menu into view
            menuBar.setBounds(FRAME_WIDTH - MENU_BAR_WIDTH, 0, MENU_BAR_WIDTH, contentAreaHeight);
        } else {
            // Slide the menu out of view
            menuBar.setBounds(FRAME_WIDTH, 0, MENU_BAR_WIDTH, contentAreaHeight);
        }
        menuBar.repaint();
    }

    // Method to get current time in "h:mm a" format
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        return sdf.format(new Date());
    }

    // Utility method to resize icons
    private JLabel resizeIcon(String path, int width, int height) {
        try {
            ImageIcon originalIcon = new ImageIcon(path);
            Image resizedImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(resizedImage));
        } catch (Exception e) {
            System.out.println("Icon not found at: " + path);
            return new JLabel("X");
        }
    }

    // Utility method to resize icons while maintaining aspect ratio
    private JLabel resizeIconMaintainAspect(String path, int targetHeight) {
        try {
            ImageIcon originalIcon = new ImageIcon(path);
            int originalWidth = originalIcon.getIconWidth();
            int originalHeight = originalIcon.getIconHeight();

            // Calculate the new width based on the target height
            int targetWidth = (int) ((double) originalWidth / originalHeight * targetHeight);

            // Resize the image while maintaining aspect ratio
            Image resizedImage = originalIcon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(resizedImage));
        } catch (Exception e) {
            System.out.println("Icon not found at: " + path);
            return new JLabel("X"); // Fallback if the icon doesn't load
        }
    }
}
