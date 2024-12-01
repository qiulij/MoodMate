package com.moodmate.logic;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.*;

public class HomePage extends BaseHomePage {
	

    public HomePage() {
        super();

//        JLabel backgroundLabel = new JLabel();
        JLabel backgroundLabel = new JLabel(new ImageIcon("assets/images/background.png"));
        backgroundLabel.setBounds(0, 0, contentArea.getWidth(), contentArea.getHeight());
        contentArea.add(backgroundLabel);

        JLabel welcomeLabel = new JLabel("Welcome to MoodMate", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font(customFont, Font.BOLD, 24));
        welcomeLabel.setBounds(30, 50, contentArea.getWidth() - 60, 40);
        backgroundLabel.add(welcomeLabel);

        JLabel welcomeText = new JLabel("Do you have an account?", SwingConstants.CENTER);
        welcomeText.setFont(new Font(customFont, Font.PLAIN,14));
        welcomeText.setBounds(30, 150, contentArea.getWidth() - 60, 40);
        backgroundLabel.add(welcomeText);

        
        
        JButton startButton = new JButton("Begin Now");
        startButton.setBounds((contentArea.getWidth() - 120) / 2, 200, 120, 40);
        startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 0, true));
        startButton.setBackground(customGreen);
        startButton.setOpaque(true);

        startButton.addActionListener(e -> {
            addToNavigationStack();
            new EFTPage();
            dispose();
        }); 
        backgroundLabel.add(startButton);
    }

    public static void main(String[] args) {
        new HomePage();
    }
}
