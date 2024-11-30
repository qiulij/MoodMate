package com.moodmate.logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

public abstract class BasePage extends JFrame {
    // Constants for layout sizes
    private static final int FRAME_WIDTH = 360;
    private static final int FRAME_HEIGHT = 780;
    private static final int STATUS_ICON_SIZE = 15;
    private static final int STATUS_BAR_HEIGHT = 30;
    private static final int NAV_BAR_HEIGHT = 30;
    private static final int TOP_PANEL_HEIGHT = 40;
    private static final int ICON_SIZE = 20;
    private static final int MENU_BAR_WIDTH = 200;

    private boolean isMenuBarOpen = false; // Track the state of the menu bar

    // Navigation stack to keep track of visited pages
    private static final Stack<BasePage> navigationStack = new Stack<>();

    protected JPanel statusBar;
    protected JPanel navigationBar;
    protected JPanel contentArea;
    protected JPanel topPanel;
    private JPanel menuBar;
    private JLayeredPane layeredPane;
    
    public Color customGreen = new Color(69, 199, 138);
    public Color customYellow = new Color(255, 221, 128);
    public Color customRed= new Color(255, 82, 82);
    

    public BasePage() {
        // Set up the main frame
        setTitle("MoodMate");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLayout(new BorderLayout());
        setResizable(false);

        // -------- Container Panel for Status Bar and Top Panel --------
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        containerPanel.setPreferredSize(new Dimension(FRAME_WIDTH, STATUS_BAR_HEIGHT + TOP_PANEL_HEIGHT));

        // -------- Status Bar --------
        statusBar = new JPanel();
        statusBar.setPreferredSize(new Dimension(FRAME_WIDTH, STATUS_BAR_HEIGHT));
        statusBar.setBackground(Color.LIGHT_GRAY);
        statusBar.setLayout(new BorderLayout());
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding

        JLabel timeLabel = new JLabel();
        timeLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        Timer timer = new Timer(1000, e -> timeLabel.setText(getCurrentTime()));
        timer.start();
        statusBar.add(timeLabel, BorderLayout.WEST);

        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new BoxLayout(iconPanel, BoxLayout.X_AXIS));
        iconPanel.setBackground(Color.LIGHT_GRAY);

        iconPanel.add(resizeIcon("assets/images/wifi.png", STATUS_ICON_SIZE, STATUS_ICON_SIZE));
        iconPanel.add(Box.createHorizontalStrut(5));
        iconPanel.add(resizeIcon("assets/images/bluetooth.png", STATUS_ICON_SIZE, STATUS_ICON_SIZE));
        iconPanel.add(Box.createHorizontalStrut(5));
        iconPanel.add(resizeIcon("assets/images/signal.png", STATUS_ICON_SIZE, STATUS_ICON_SIZE));
        iconPanel.add(Box.createHorizontalStrut(5));
        iconPanel.add(resizeIcon("assets/images/battery.png", STATUS_ICON_SIZE, STATUS_ICON_SIZE));

        statusBar.add(iconPanel, BorderLayout.EAST);

        // Add the statusBar to the containerPanel
        containerPanel.add(statusBar, BorderLayout.NORTH);

        // -------- Top Panel --------
        topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(FRAME_WIDTH, TOP_PANEL_HEIGHT));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        // Add Logo (Left)
        JLabel logoLabel = resizeIconMaintainAspect("assets/images/logo.png", ICON_SIZE);
        if (logoLabel != null) {
            topPanel.add(logoLabel, BorderLayout.WEST);
        } else {
            System.out.println("Logo image not found: assets/images/logo.png");
        }

        // Add Profile Icon (Right) with Menu Action
        JLabel profileIcon = resizeIcon("assets/images/profile.png", ICON_SIZE, ICON_SIZE);
        if (profileIcon != null) {
            profileIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            profileIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    toggleMenuBar(); // Trigger sidebar toggle on click
                }
            });
            topPanel.add(profileIcon, BorderLayout.EAST);
        } else {
            System.out.println("Profile icon image not found: assets/images/profile.png");
        }

        // Add the topPanel to the containerPanel
        containerPanel.add(topPanel, BorderLayout.SOUTH);

        // Add the containerPanel to the NORTH of the frame
        add(containerPanel, BorderLayout.NORTH);

        // -------- Layered Pane for Overlay --------
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT - STATUS_BAR_HEIGHT - NAV_BAR_HEIGHT));
        add(layeredPane, BorderLayout.CENTER);

        // -------- Content Area --------
        contentArea = new JPanel();
        contentArea.setBackground(Color.WHITE);
        contentArea.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT - STATUS_BAR_HEIGHT - NAV_BAR_HEIGHT);
        contentArea.setLayout(new BorderLayout());
        layeredPane.add(contentArea, JLayeredPane.DEFAULT_LAYER);

        // -------- Sliding Menu Bar --------
        menuBar = new JPanel();
        menuBar.setBackground(Color.LIGHT_GRAY);
        menuBar.setBounds(FRAME_WIDTH, 0, MENU_BAR_WIDTH, FRAME_HEIGHT - STATUS_BAR_HEIGHT - NAV_BAR_HEIGHT);
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.Y_AXIS));

        // Add some test content to the menu bar
        menuBar.add(new JLabel("Menu Item 1"));
        menuBar.add(Box.createVerticalStrut(10)); // Spacing between items
        menuBar.add(new JLabel("Menu Item 2"));
        menuBar.add(Box.createVerticalStrut(10));
        menuBar.add(new JLabel("Menu Item 3"));

        // Add the menuBar to the JLayeredPane on the PALETTE_LAYER
        layeredPane.add(menuBar, JLayeredPane.PALETTE_LAYER);

        // -------- Navigation Bar --------
        navigationBar = new JPanel();
        navigationBar.setBackground(Color.LIGHT_GRAY);
        navigationBar.setPreferredSize(new Dimension(FRAME_WIDTH, NAV_BAR_HEIGHT));
        navigationBar.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 20, 0, 20);

        JLabel backButton = resizeIcon("assets/images/back.png", STATUS_ICON_SIZE, STATUS_ICON_SIZE);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                navigateBack();
            }
        });
        gbc.gridx = 0;
        navigationBar.add(backButton, gbc);

        JLabel homeButton = resizeIcon("assets/images/home.png", STATUS_ICON_SIZE, STATUS_ICON_SIZE);
        homeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        homeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                navigateToHome();
            }
        });
        gbc.gridx = 1;
        navigationBar.add(homeButton, gbc);

        JLabel recentButton = resizeIcon("assets/images/recent.png", STATUS_ICON_SIZE, STATUS_ICON_SIZE);
        recentButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        recentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(BasePage.this, "Recent functionality coming soon!");
            }
        });
        gbc.gridx = 2;
        navigationBar.add(recentButton, gbc);

        add(navigationBar, BorderLayout.SOUTH);

        // Revalidate and repaint
        layeredPane.revalidate();
        layeredPane.repaint();
        setVisible(true);
    }

    public void setContent(JPanel newContent) {
        // Clear the current content
        contentArea.removeAll();

        // Set the new content
        contentArea.add(newContent, BorderLayout.CENTER);

        // Revalidate and repaint the content area to apply changes
        contentArea.revalidate();
        contentArea.repaint();
    }

    
    
    private void toggleMenuBar() {
        int startX = isMenuBarOpen ? FRAME_WIDTH - MENU_BAR_WIDTH : FRAME_WIDTH;
        int endX = isMenuBarOpen ? FRAME_WIDTH : FRAME_WIDTH - MENU_BAR_WIDTH;

        new Thread(() -> {
            try {
                if (!isMenuBarOpen) { // Slide in
                    for (int x = startX; x >= endX; x -= 10) {
                        menuBar.setBounds(x, 0, MENU_BAR_WIDTH, FRAME_HEIGHT - STATUS_BAR_HEIGHT - NAV_BAR_HEIGHT);
                        menuBar.repaint();
                        Thread.sleep(10);
                    }
                } else { // Slide out
                    for (int x = startX; x <= endX; x += 10) {
                        menuBar.setBounds(x, 0, MENU_BAR_WIDTH, FRAME_HEIGHT - STATUS_BAR_HEIGHT - NAV_BAR_HEIGHT);
                        menuBar.repaint();
                        Thread.sleep(10);
                    }
                }
                isMenuBarOpen = !isMenuBarOpen; // Toggle the state
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void navigateBack() {
        if (!navigationStack.isEmpty()) {
            BasePage previousPage = navigationStack.pop();
            previousPage.setVisible(true);
            dispose();
        } else {
            new WelcomePage();
            dispose();
        }
    }

    private void navigateToHome() {
        new WelcomePage();
        dispose();
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        return sdf.format(new Date());
    }

    protected void addToNavigationStack() {
        if (navigationStack.isEmpty() || navigationStack.peek() != this) {
            navigationStack.push(this);
        }
    }

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

    private JLabel resizeIconMaintainAspect(String path, int targetHeight) {
        try {
            ImageIcon originalIcon = new ImageIcon(path);
            int originalWidth = originalIcon.getIconWidth();
            int originalHeight = originalIcon.getIconHeight();

            int targetWidth = (int) ((double) originalWidth / originalHeight * targetHeight);
            Image resizedImage = originalIcon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(resizedImage));
        } catch (Exception e) {
            System.out.println("Icon not found at: " + path);
            return new JLabel("X");
        }
    }
}
