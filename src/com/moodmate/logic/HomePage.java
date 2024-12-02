package com.moodmate.logic;

import java.awt.*;

import javax.swing.*;

public class HomePage extends BasePage {
    public HomePage() {
        super();

        // Add welcome message to content area
        JLabel welcomeLabel = new JLabel("Welcome to MoodMate!");
        welcomeLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentArea.add(welcomeLabel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomePage homePage = new HomePage();
            homePage.setVisible(true);
        });
    }
}
