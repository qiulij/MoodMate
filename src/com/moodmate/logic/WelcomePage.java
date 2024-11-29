package com.moodmate.logic;

import java.awt.Font;

import javax.swing.*;

public class WelcomePage extends BasePage {

    public WelcomePage() {
        super();

//        JLabel backgroundLabel = new JLabel();
        JLabel backgroundLabel = new JLabel(new ImageIcon("assets/images/background_welcome.png"));
        backgroundLabel.setBounds(0, 0, contentArea.getWidth(), contentArea.getHeight());
        contentArea.add(backgroundLabel);

        JLabel welcomeLabel = new JLabel("Welcome to MoodMate", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
        welcomeLabel.setBounds(30, 50, contentArea.getWidth() - 60, 40);
        backgroundLabel.add(welcomeLabel);

        JButton signInButton = new JButton("Sign In");
        signInButton.setBounds((contentArea.getWidth() - 120) / 2, 200, 120, 40);
        signInButton.addActionListener(e -> {
            addToNavigationStack();
            new SignInPage();
            dispose();
        });
        backgroundLabel.add(signInButton);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds((contentArea.getWidth() - 120) / 2, 260, 120, 40);
        signUpButton.addActionListener(e -> {
            addToNavigationStack();
            new SignUpPage();
            dispose();
        });
        backgroundLabel.add(signUpButton);
    }

    public static void main(String[] args) {
        new WelcomePage();
    }
}
