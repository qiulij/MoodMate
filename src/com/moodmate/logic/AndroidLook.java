package com.moodmate.logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

public abstract class AndroidLook extends JFrame {
	protected static final int FRAME_WIDTH = 360;
	protected static final int FRAME_HEIGHT = 780;
	protected static final int STATUS_ICON_SIZE = 15;
	protected static final int STATUS_BAR_HEIGHT = 30;
	protected static final int NAV_BAR_HEIGHT = 30;

    protected JPanel statusBar;
    protected JPanel navigationBar;
    protected JPanel appArea;

    // Navigation stack to keep track of visited pages
    public static final Stack<AndroidLook> navigationStack = new Stack<>();

    public AndroidLook() {
        // Set up the frame
        setTitle("MoodMate");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLayout(new BorderLayout());
        setResizable(false);

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

        add(statusBar, BorderLayout.NORTH);

        // -------- Content Area --------
        appArea = new JPanel();
        appArea.setBackground(Color.WHITE);
        appArea.setLayout(null);
        add(appArea, BorderLayout.CENTER);

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
                JOptionPane.showMessageDialog(AndroidLook.this, "Recent functionality coming soon!");
            }
        });
        gbc.gridx = 2;
        navigationBar.add(recentButton, gbc);

        add(navigationBar, BorderLayout.SOUTH);

        setVisible(true);
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        return sdf.format(new Date());
    }

    private void navigateBack() {
        if (!navigationStack.isEmpty()) {
            AndroidLook previousPage = navigationStack.pop();
            previousPage.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "No previous page!");
        }
    }


    private void navigateToHome() {
        JOptionPane.showMessageDialog(this, "Navigating to Home!");
        dispose();
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
}
