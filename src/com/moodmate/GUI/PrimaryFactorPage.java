package com.moodmate.GUI;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

public class PrimaryFactorPage extends BaseHomePage {

    private static final int PADDING_X = 40; // Horizontal padding for fields
    private static final int FIELD_HEIGHT = 30; // Height for input fields
    private static final int MARGIN = 10; // Vertical margin between components
    private static final int CORNER_RADIUS = 25; // Rounded corner radius

    public PrimaryFactorPage() {
        super();

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null); // Absolute positioning

        int currentY = 20; // Start Y position for components

        // Title Label
        JLabel titleLabel = new JLabel("Let's Discover together", SwingConstants.CENTER);
        titleLabel.setFont(new Font(customFont, Font.BOLD, 20));
        titleLabel.setBounds(PADDING_X, currentY, contentArea.getWidth() - 2 * PADDING_X, FIELD_HEIGHT);
        contentPanel.add(titleLabel);

        currentY += FIELD_HEIGHT + MARGIN;

        // Did anything happen that triggered you? Yes/No question
        JLabel triggerQuestionLabel = new JLabel("Did anything happen that triggered you?", SwingConstants.LEFT);
        triggerQuestionLabel.setFont(new Font(customFont, Font.PLAIN, 16));
        triggerQuestionLabel.setBounds(PADDING_X, currentY, contentArea.getWidth() - 2 * PADDING_X, FIELD_HEIGHT);
        contentPanel.add(triggerQuestionLabel);

        currentY += FIELD_HEIGHT + MARGIN;

        JRadioButton yesButton = new JRadioButton("Yes");
        JRadioButton noButton = new JRadioButton("No");
        yesButton.setBounds(PADDING_X, currentY, 60, FIELD_HEIGHT);
        noButton.setBounds(PADDING_X + 80, currentY, 60, FIELD_HEIGHT);

        ButtonGroup triggerGroup = new ButtonGroup();
        triggerGroup.add(yesButton);
        triggerGroup.add(noButton);

        contentPanel.add(yesButton);
        contentPanel.add(noButton);

        currentY += FIELD_HEIGHT + 2 * MARGIN;

        // Self-image questions title
        JLabel selfImageTitle = new JLabel("Let me know how you feel about yourself", SwingConstants.CENTER);
        selfImageTitle.setFont(new Font(customFont, Font.BOLD, 18));
        selfImageTitle.setBounds(PADDING_X, currentY, contentArea.getWidth() - 2 * PADDING_X, FIELD_HEIGHT);
        contentPanel.add(selfImageTitle);

        currentY += FIELD_HEIGHT + MARGIN;

        // Self-image questions with rating options
        String[] questions = {
                "1. On the whole, I am satisfied with myself.",
                "2. At times I think I am no good at all.",
                "3. I feel that I have a number of good qualities.",
                "4. I am able to do things as well as most other people.",
                "5. I feel I do not have much to be proud of.",
                "6. I certainly feel useless at times.",
                "7. I feel that I'm a person of worth.",
                "8. I wish I could have more respect for myself.",
                "9. All in all, I am inclined to think that I am a failure.",
                "10. I take a positive attitude toward myself."
        };

        for (String question : questions) {
            JLabel questionLabel = new JLabel("<html>" + question + "</html>", SwingConstants.LEFT);
            questionLabel.setFont(new Font(customFont, Font.PLAIN, 14));
            questionLabel.setBounds(PADDING_X, currentY, contentArea.getWidth() - 2 * PADDING_X, FIELD_HEIGHT * 2);
            contentPanel.add(questionLabel);

            currentY += FIELD_HEIGHT * 2 + MARGIN;

            // Rating scale with vertically stacked buttons
            JPanel ratingPanel = new JPanel();
            ratingPanel.setLayout(new GridLayout(2, 2, 10, 5)); // 2 rows, 2 columns
            ratingPanel.setBounds(PADDING_X, currentY, contentArea.getWidth() - 2 * PADDING_X, FIELD_HEIGHT * 2);

            String[] ratings = {"Strongly Agree",  "Disagree","Agree", "Strongly Disagree"};
            ButtonGroup ratingGroup = new ButtonGroup();
            for (String rating : ratings) {
                JRadioButton ratingButton = new JRadioButton(rating);
                ratingButton.setFont(new Font(customFont, Font.PLAIN, 12));
                ratingGroup.add(ratingButton);
                ratingPanel.add(ratingButton);
            }

            contentPanel.add(ratingPanel);
            currentY += FIELD_HEIGHT * 2 + MARGIN;
        }

        // Next Button
        JButton nextButton = new JButton("Next");
        nextButton.setBounds(PADDING_X, currentY, contentArea.getWidth() - 2 * PADDING_X, FIELD_HEIGHT + 10);
        nextButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        nextButton.setBackground(customGreen); // Custom green color
        nextButton.setOpaque(true);
        nextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        nextButton.addActionListener(e -> {
            // Validation: Check if all questions are answered
            Component[] components = contentPanel.getComponents();
            boolean allAnswered = true;

            // Loop through the components to find ButtonGroups and check selection
            for (Component component : components) {
                if (component instanceof JPanel) {
                    JPanel ratingPanel = (JPanel) component;
                    ButtonGroup group = new ButtonGroup();

                    // Add buttons in the panel to a temporary ButtonGroup
                    for (Component button : ratingPanel.getComponents()) {
                        if (button instanceof JRadioButton) {
                            group.add((JRadioButton) button);
                        }
                    }

                    // Check if a button is selected in this group
                    if (group.getSelection() == null) {
                        allAnswered = false;
                        break;
                    }
                }
            }

            if (!allAnswered) {
                // Show an error message if any question is unanswered
                JOptionPane.showMessageDialog(
                    this,
                    "<html><body style='width: 150px;'>Please answer all the questions before proceeding.</body></html>",
                    "Incomplete Form",
                    JOptionPane.WARNING_MESSAGE
                );
                return; // Exit the action if validation fails
            }

            // Proceed if all questions are answered
            String message = "<html><body style='width: 150px;'>" +
                    "In this app, we want to help you understand why you are experiencing this feeling.<br>" +
                    "Do you want to take a quick test?" +
                    "</body></html>";

            String[] options = {"OK", "Exit"};
            int choice = JOptionPane.showOptionDialog(
                this, // Parent component
                message,
                "Self-help ", // Title of the dialog
                JOptionPane.YES_NO_OPTION, // Option type
                JOptionPane.INFORMATION_MESSAGE, // Message type
                null, // Icon (null for default)
                options, // Button text
                options[0] // Default button
            );

            // Handle the user's choice
            if (choice == 1) { // "Exit" option selected
                new HomePage();
                dispose(); // Close the current page
            } else if (choice == 0) { // "OK" option selected
                addToNavigationStack();
                new SleepPage();
                dispose(); // Close the current page
            }
        });


        contentPanel.add(nextButton);
        currentY += FIELD_HEIGHT + MARGIN;

        contentPanel.setPreferredSize(new Dimension(contentArea.getWidth(), currentY + 100));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        contentArea.add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PrimaryFactorPage::new);
    }
}
