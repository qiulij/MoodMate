package com.moodmate.GUI;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.Hashtable;

public class RealTimeSuggestionPage extends BaseHomePage {

    private static final int PADDING_X = 30; // Horizontal padding for fields
    private static final int FIELD_HEIGHT = 30; // Height for the input fields
    private static final int PIC_SIZE = 100; // Height for the input fields   
    private static final int MARGIN = 20; // Vertical margin between components

    int contentWidth = contentArea.getWidth();

    public RealTimeSuggestionPage() {
        super();

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null); // Absolute positioning

        int currentY = 20; // Start Y position for components

        // Title Label
        JLabel titleLabel = new JLabel("Monitor Your Nutrition", SwingConstants.CENTER);
        titleLabel.setFont(new Font(customFont, Font.BOLD, 20));
        titleLabel.setBounds(PADDING_X, currentY, contentWidth - 2 * PADDING_X, FIELD_HEIGHT);
        contentPanel.add(titleLabel);

        currentY += FIELD_HEIGHT + MARGIN;


        JLabel title2Label = new JLabel("Monitor Your Nutrition", SwingConstants.CENTER);
        title2Label.setFont(new Font(customFont, Font.BOLD, 20));
        title2Label.setBounds(PADDING_X, currentY, contentWidth - 2 * PADDING_X, FIELD_HEIGHT);
        contentPanel.add(title2Label);

        currentY += FIELD_HEIGHT + MARGIN;


        // Macronutrient Sliders
 
     

        String[] suggestionsText = {
            "Prepare and enjoy a balanced meal to nourish your body and mind.",
            "Do a quick stretch or light exercise to energize your body.",
            "Look in the mirror and remind yourself of something you like about yourself.",
            "Take a short power nap or create a cozy environment for better sleep.",
            "Step outside to enjoy the fresh air or take a moment to appreciate the current weather."
        };
        String[] suggestionIcons = {
            "food.png", "physical_activity.png", "self_image.png", "sleep.png", "weather.png",
        };

      
        for (int i = 0; i < suggestionsText.length; i++) {
            // Container Panel
            JPanel container = new JPanel();
            container.setLayout(null);
            container.setBounds(PADDING_X, currentY, contentWidth - 2 * PADDING_X, PIC_SIZE + FIELD_HEIGHT + 10);

            // Icon Label;
            String path =  "assets/images/suggestions/" + suggestionIcons[i];
            JLabel iconLabel = new JLabel(new ImageIcon(path));
            iconLabel.setBounds(0, 0, PIC_SIZE, PIC_SIZE);
            container.add(iconLabel);

            JLabel textLabel = new JLabel("<html>" + suggestionsText[i] + "</html>");
            textLabel.setFont(new Font(customFont, Font.PLAIN, 16));
            textLabel.setBounds(PIC_SIZE + 10, 0, contentWidth - PIC_SIZE - PADDING_X * 2, PIC_SIZE );
            container.add(textLabel);
       
            // Add container to content panel
            contentPanel.add(container);

            currentY += PIC_SIZE + FIELD_HEIGHT + MARGIN;
        }


        currentY += MARGIN;

        // Next Button
        JButton nextButton = new JButton("Next");
        nextButton.setBounds(PADDING_X, currentY, contentWidth - 2 * PADDING_X, FIELD_HEIGHT + 10);
        nextButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 0, true));
        nextButton.setBackground(customGreen);
        nextButton.setOpaque(true);
        nextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        nextButton.addActionListener(e -> {
            addToNavigationStack();
            new HomePage();
            dispose();
        });

        contentPanel.add(nextButton);
        currentY += FIELD_HEIGHT + MARGIN;

        contentPanel.setPreferredSize(new Dimension(contentWidth, currentY + 100));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        contentArea.add(scrollPane, BorderLayout.CENTER);
    }

   

    public static void main(String[] args) {
        new RealTimeSuggestionPage();
    }
}
