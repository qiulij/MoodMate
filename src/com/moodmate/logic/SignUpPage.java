package com.moodmate.logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SignUpPage extends BasePage {

    // Constants for easy adjustments
    private static final int PADDING_X = 60; // Horizontal padding for fields
    private static final int FIELD_HEIGHT = 30; // Height for the input fields and button

    public SignUpPage() {
        super();

        // Add background image
        JLabel backgroundLabel = new JLabel(new ImageIcon("assets/images/background.png"));
        backgroundLabel.setBounds(0, 0, contentArea.getWidth(), contentArea.getHeight());
        contentArea.add(backgroundLabel);
        backgroundLabel.setLayout(null);

        // Title Label
        JLabel titleLabel = new JLabel("Sign Up", SwingConstants.CENTER);
        titleLabel.setFont(new Font(customFont, Font.BOLD, 24));
        titleLabel.setBounds(PADDING_X, 50, contentArea.getWidth() - 2 * PADDING_X, 40);
        backgroundLabel.add(titleLabel);

        // Username Field
        JTextField usernameField = createInputField("Username", 120);
        backgroundLabel.add(usernameField);

        // Email Field
        JTextField emailField = createInputField("Email", 180);
        backgroundLabel.add(emailField);

        // Password Field
        JPasswordField passwordField = createPasswordField("Password", 240);
        backgroundLabel.add(passwordField);

        // Confirm Password Field
        JPasswordField confirmPasswordField = createPasswordField("Confirm Password", 300);
        backgroundLabel.add(confirmPasswordField);

        // Sign Up Button (same width and height as input fields)
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(PADDING_X, 380, contentArea.getWidth() - 2 * PADDING_X, FIELD_HEIGHT + 30);
        signUpButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 0, true));
//        signUpButton.setBackground(Color.decode("#45C78A"));
        signUpButton.setBackground(customGreen);
        signUpButton.setOpaque(true);
        signUpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
        signUpButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String confirmPassword = new String(confirmPasswordField.getPassword()).trim();

            // Validate inputs
            if (username.isEmpty() || username.equals("Username") ||
                email.isEmpty() || email.equals("Email") ||
                password.isEmpty() || password.equals("Password") ||
                confirmPassword.isEmpty() || confirmPassword.equals("Confirm Password")) {

                JOptionPane.showMessageDialog(
                    this,
                    "All fields are required. Please fill in all the details.",
                    "Input Required",
                    JOptionPane.WARNING_MESSAGE
                );
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match. Please confirm your password correctly.",
                    "Password Mismatch",
                    JOptionPane.WARNING_MESSAGE
                );
            } else {
                // Proceed to UserProfilePage if inputs are valid
                JOptionPane.showMessageDialog(this, "Sign-up successful!");
                addToNavigationStack();
                new UserProfilePage();
                dispose();
            }
        });

        backgroundLabel.add(signUpButton);
    }

    // Method to create input fields with placeholders
    private JTextField createInputField(String placeholder, int yPosition) {
        JTextField field = new JTextField(placeholder);
        field.setBounds(PADDING_X, yPosition, contentArea.getWidth() - 2 * PADDING_X, FIELD_HEIGHT);
        field.setFont(new Font(customFont, Font.PLAIN, 14));
        field.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 0, true)); 
        // Placeholder behavior
        field.setForeground(Color.GRAY);
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });
        
        return field;
    }

    // Method to create password fields with placeholders
    private JPasswordField createPasswordField(String placeholder, int yPosition) {
        JPasswordField field = new JPasswordField(placeholder);
        field.setBounds(PADDING_X, yPosition, contentArea.getWidth() - 2 * PADDING_X, FIELD_HEIGHT);
        field.setFont(new Font(customFont, Font.PLAIN, 14));
        field.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 0, true)); 
        // Placeholder behavior
        field.setEchoChar((char) 0); // Show the placeholder text
        field.setForeground(Color.GRAY);
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (String.valueOf(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                    field.setEchoChar('*');
                }
            }
            public void focusLost(FocusEvent e) {
                if (String.valueOf(field.getPassword()).isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                    field.setEchoChar((char) 0);
                }
            }
        });
        
        return field;
    }

    public static void main(String[] args) {
        new SignUpPage();
    }
}
