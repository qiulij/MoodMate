package com.moodmate.GUI;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class NotificationSettingPage extends BasePage {

    private static final int PADDING_X = 60; // Horizontal padding for fields
    private static final int FIELD_HEIGHT = 30; // Height for the input fields
    private static final int MARGIN = 20; // Vertical margin between components
    int contentWidth = contentArea.getWidth();

    public NotificationSettingPage() {
        super();

        // Create a contentPanel for all components
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null); // Absolute positioning
        contentPanel.setBackground(customBackgroundColor);

        // Add background image (it's just a visual element, so we add it to contentPanel)
        JLabel backgroundLabel = new JLabel(new ImageIcon("assets/images/background.png"));
        backgroundLabel.setBounds(0, 0, contentWidth, contentArea.getHeight());
        contentPanel.add(backgroundLabel);
        backgroundLabel.setLayout(null);

        int currentY = 20; // Start Y position for components

        // Title Label
        JLabel titleLabel = new JLabel(" Notification Settings", SwingConstants.LEFT);
        titleLabel.setFont(new Font(customFont, Font.BOLD, 20));
        titleLabel.setBounds(PADDING_X, currentY, contentWidth - 2 * PADDING_X, FIELD_HEIGHT);
        contentPanel.add(titleLabel);

        currentY += FIELD_HEIGHT + MARGIN;

        // Informational Text
        JLabel infoLabel = new JLabel("<html><div>"
                + "<b>Why Set Your Emotion Check-in Frequency?</b><br><br>"
                + "Tracking your emotions regularly helps you understand and manage how you feel.<br>"
                + "You only need to tell us the rate each 5 key feelings: Joy, Sadness, Anger, Fear, and Disgust.<br><br>"
                + "<ul>"
                + "<li><b>Understand your emotions</b></li>"
                + "<li><b>Spot patterns over time</b></li>"
                + "<li><b>Receive support to feel balanced</b></li>"
                + "</ul>"
                + "Choose a frequency that works for you and start your journey to emotional well-being!"
                + "</div></html>");
        infoLabel.setFont(new Font(customFont, Font.PLAIN, 14));
        infoLabel.setBounds(PADDING_X, currentY, contentWidth - 2 * PADDING_X, 300);
        contentPanel.add(infoLabel);

        currentY += 300 + MARGIN;

        // Radio Buttons for Frequency Selection
        JLabel frequencyLabel = new JLabel("Check-in Frequency:");
        frequencyLabel.setFont(new Font(customFont, Font.BOLD, 16));
        frequencyLabel.setBounds(PADDING_X, currentY, contentWidth - 2 * PADDING_X, FIELD_HEIGHT);
        contentPanel.add(frequencyLabel);

        currentY += FIELD_HEIGHT + MARGIN;

        JRadioButton oneHourButton = new JRadioButton("1 Hour");
        JRadioButton twoHourButton = new JRadioButton("2 Hours");
        JRadioButton threeHourButton = new JRadioButton("3 Hours");

        ButtonGroup frequencyGroup = new ButtonGroup();
        frequencyGroup.add(oneHourButton);
        frequencyGroup.add(twoHourButton);
        frequencyGroup.add(threeHourButton);

        oneHourButton.setBounds(PADDING_X, currentY, 100, FIELD_HEIGHT);
        twoHourButton.setBounds(PADDING_X + 110, currentY, 100, FIELD_HEIGHT);
        threeHourButton.setBounds(PADDING_X + 220, currentY, 100, FIELD_HEIGHT);

        contentPanel.add(oneHourButton);
        contentPanel.add(twoHourButton);
        contentPanel.add(threeHourButton);

        currentY += FIELD_HEIGHT + MARGIN;

        // Next Button
        JButton nextButton = new JButton("Next");
        nextButton.setBounds(PADDING_X, currentY, contentWidth - 2 * PADDING_X, FIELD_HEIGHT + 10);
        nextButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 0, true));
        nextButton.setBackground(customGreen);
        nextButton.setOpaque(true);
        nextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        

        nextButton.addActionListener(e -> {
            // Check if any radio button is selected
            if (oneHourButton.isSelected() || twoHourButton.isSelected() || threeHourButton.isSelected()) {
                addToNavigationStack();
                // new HomePage(); // Uncomment if you want to navigate to a different page
                dispose();
            } else {
                // Show a warning dialog if no option is selected
                JOptionPane.showMessageDialog(
                    contentPanel,
                    "Please select a frequency before proceeding.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        });

        contentPanel.add(nextButton);

        currentY += MARGIN;

        contentPanel.setPreferredSize(new Dimension(contentWidth, currentY + 100));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        contentArea.add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new NotificationSettingPage();
    }
}
