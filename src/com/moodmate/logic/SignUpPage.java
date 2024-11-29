package com.moodmate.logic;

import javax.swing.*;
import java.awt.*;

public class SignUpPage extends BasePage {

    public SignUpPage() {
        super();

        // Add background image
        JLabel backgroundLabel = new JLabel(new ImageIcon("assets/images/background.png"));
        backgroundLabel.setBounds(0, 0, contentArea.getWidth(), contentArea.getHeight());
        contentArea.add(backgroundLabel);
        backgroundLabel.setLayout(null);

        // Title Label
        JLabel titleLabel = new JLabel("Sign Up", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
        titleLabel.setBounds(30, 50, contentArea.getWidth() - 60, 40);
        backgroundLabel.add(titleLabel);

        // Username Label and Field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(30, 120, 100, 30);
        backgroundLabel.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(140, 120, contentArea.getWidth() - 170, 30);
        backgroundLabel.add(usernameField);

        // Password Label and Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 180, 100, 30);
        backgroundLabel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(140, 180, contentArea.getWidth() - 170, 30);
        backgroundLabel.add(passwordField);

        // Confirm Password Label and Field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(30, 240, 150, 30);
        backgroundLabel.add(confirmPasswordLabel);

        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(190, 240, contentArea.getWidth() - 220, 30);
        backgroundLabel.add(confirmPasswordField);

        // Sign Up Button
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds((contentArea.getWidth() - 120) / 2, 320, 120, 40);
        signUpButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Sign-up successful!");
            addToNavigationStack();
            new WelcomePage();
            dispose();
        });
        backgroundLabel.add(signUpButton);
    }

    public static void main(String[] args) {
        new SignUpPage();
    }
}
