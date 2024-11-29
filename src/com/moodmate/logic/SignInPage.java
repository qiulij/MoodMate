package com.moodmate.logic;

import javax.swing.*;
import java.awt.*;

public class SignInPage extends BasePage {

    public SignInPage() {
        super();

        // Add background image
        JLabel backgroundLabel = new JLabel(new ImageIcon("assets/images/background.png"));
        backgroundLabel.setBounds(0, 0, contentArea.getWidth(), contentArea.getHeight());
        contentArea.add(backgroundLabel);
        backgroundLabel.setLayout(null);

        // Title Label
        JLabel titleLabel = new JLabel("Sign In", SwingConstants.CENTER);
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

        // Sign In Button
        JButton signInButton = new JButton("Sign In");
        signInButton.setBounds((contentArea.getWidth() - 120) / 2, 260, 120, 40);
        signInButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Sign-in successful!");
            addToNavigationStack();
            new WelcomePage();
            dispose();
        });
        backgroundLabel.add(signInButton);
    }

    public static void main(String[] args) {
        new SignInPage();
    }
}
