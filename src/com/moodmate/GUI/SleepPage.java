package com.moodmate.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Date;

public class SleepPage extends BaseHomePage {

    private static final int PADDING_X = 40; // Horizontal padding for fields
    private static final int FIELD_HEIGHT = 30; // Height for input fields
    private static final int MARGIN = 10; // Vertical margin between components

    public SleepPage() {
        super();

        // Set the background of the page to an image
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null); // Absolute positioning

        int currentY = 20; // Start Y position for components

        // Title Label
        JLabel titleLabel = new JLabel("Let's Discover Together", SwingConstants.CENTER);
        titleLabel.setFont(new Font(customFont, Font.BOLD, 20));
        titleLabel.setBounds(PADDING_X, currentY, contentArea.getWidth() - 2 * PADDING_X, FIELD_HEIGHT);
        contentPanel.add(titleLabel);

        currentY += FIELD_HEIGHT + MARGIN * 2;

        // Ask if the user is sleepy
        JLabel sleepyLabel = new JLabel("Are you sleepy?", SwingConstants.CENTER);
        sleepyLabel.setFont(new Font(customFont, Font.BOLD, 18));
        sleepyLabel.setBounds(PADDING_X, currentY, contentArea.getWidth() - 2 * PADDING_X, FIELD_HEIGHT);
        contentPanel.add(sleepyLabel);

        currentY += FIELD_HEIGHT + MARGIN;
        int buttonWidth = 100;
        int buttonHeight = 100;
        int gap = 20; // Gap between buttons
        int totalWidth = (2 * buttonWidth) + gap;
        int startX = (contentArea.getWidth() - totalWidth) / 2;

        // Radio buttons with pictures
        JRadioButton yesButton = new JRadioButton(new ImageIcon("assets/images/sleep_yes.png"));
        JRadioButton noButton = new JRadioButton(new ImageIcon("assets/images/sleep_no.png"));

        ButtonGroup sleepyGroup = new ButtonGroup();
        sleepyGroup.add(yesButton);
        sleepyGroup.add(noButton);

        yesButton.setBounds(startX, currentY, buttonWidth, buttonHeight);
        noButton.setBounds(startX + buttonWidth + gap, currentY, buttonWidth, buttonHeight);

        contentPanel.add(yesButton);
        contentPanel.add(noButton);

        currentY += 100 + MARGIN;

        // Panel for additional questions if "Yes" is selected
        JPanel sleepDetailsPanel = new JPanel();
        sleepDetailsPanel.setLayout(new GridLayout(6, 1, MARGIN, MARGIN)); // Padding and layout
        int panelPadding = 10;
        sleepDetailsPanel.setBorder(new EmptyBorder(panelPadding, panelPadding, panelPadding, panelPadding)); // Padding around the panel
        sleepDetailsPanel.setVisible(false); // Initially hidden
        sleepDetailsPanel.setBounds(
            PADDING_X,
            currentY,
            contentArea.getWidth() - 2 * PADDING_X,
            FIELD_HEIGHT * 10
        );

        // Sleep quality question
        JLabel qualityLabel = new JLabel("Rate the quality of your sleep:", SwingConstants.LEFT);
        qualityLabel.setFont(new Font(customFont, Font.PLAIN, 16));
        sleepDetailsPanel.add(qualityLabel);

        // Radio buttons for sleep quality with text labels
        JRadioButton veryGoodButton = new JRadioButton("Very Good");
        JRadioButton fairlyGoodButton = new JRadioButton("Fairly Good");
//        JRadioButton needsImprovementButton = new JRadioButton("Needs Improvement");
        JRadioButton needsImprovementButton = new JRadioButton("Could be better");
        JRadioButton poorButton = new JRadioButton("Poor");

        ButtonGroup qualityGroup = new ButtonGroup();
        qualityGroup.add(veryGoodButton);
        qualityGroup.add(fairlyGoodButton);
        qualityGroup.add(needsImprovementButton);
        qualityGroup.add(poorButton);

        JPanel qualityPanel = new JPanel(new GridLayout(2, 2, MARGIN, MARGIN));
        qualityPanel.add(veryGoodButton);
        qualityPanel.add(fairlyGoodButton);
        qualityPanel.add(needsImprovementButton);
        qualityPanel.add(poorButton);

        sleepDetailsPanel.add(qualityPanel);

        // Sleep and wake time inputs
        JLabel sleepTimeLabel = new JLabel("Time you went to sleep:");
        sleepTimeLabel.setFont(new Font(customFont, Font.PLAIN, 16));
        sleepDetailsPanel.add(sleepTimeLabel);

        JSpinner sleepTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor sleepEditor = new JSpinner.DateEditor(sleepTimeSpinner, "hh:mm a");
        sleepTimeSpinner.setEditor(sleepEditor);
        sleepDetailsPanel.add(sleepTimeSpinner);

        JLabel wakeTimeLabel = new JLabel("Time you woke up:");
        wakeTimeLabel.setFont(new Font(customFont, Font.PLAIN, 16));
        sleepDetailsPanel.add(wakeTimeLabel);

        JSpinner wakeTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor wakeEditor = new JSpinner.DateEditor(wakeTimeSpinner, "hh:mm a");
        wakeTimeSpinner.setEditor(wakeEditor);
        sleepDetailsPanel.add(wakeTimeSpinner);

        contentPanel.add(sleepDetailsPanel);

        // Listener for "Yes" button to show details panel
        yesButton.addActionListener(e -> sleepDetailsPanel.setVisible(true));
        noButton.addActionListener(e -> sleepDetailsPanel.setVisible(false));

        currentY += sleepDetailsPanel.getHeight() + MARGIN;

        // Next Button
        JButton nextButton = new JButton("Next");

        nextButton.setBounds(PADDING_X, currentY, contentArea.getWidth() - 2 * PADDING_X, FIELD_HEIGHT + 10);
        nextButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1, true));
        nextButton.setBackground(customGreen); // Custom green color
        nextButton.setOpaque(true);
        nextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        nextButton.addActionListener(e -> {
       	 addToNavigationStack();
            new PhysicalActivityPage();
            dispose();
       	
           // Handle navigation to the next page
       });

        contentPanel.add(nextButton);

        contentPanel.setPreferredSize(new Dimension(contentArea.getWidth(), currentY + FIELD_HEIGHT + 40));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        contentArea.add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SleepPage::new);
    }
}
