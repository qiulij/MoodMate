package com.moodmate.logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SignInPage extends BasePage {

    // Constants for easy adjustments
    private static final int PADDING_X = 60; // Horizontal padding for fields
    private static final int FIELD_HEIGHT = 30; // Height for the input fields and button

    public SignInPage() {
        super();

        // Add background image
        JLabel backgroundLabel = new JLabel(new ImageIcon("assets/images/background.png"));
        backgroundLabel.setBounds(0, 0, contentArea.getWidth(), contentArea.getHeight());
        contentArea.add(backgroundLabel);
        backgroundLabel.setLayout(null);

        // Title Label
        JLabel titleLabel = new JLabel("Sign In", SwingConstants.CENTER);
        titleLabel.setFont(new Font(customFont, Font.BOLD, 24));
        titleLabel.setBounds(PADDING_X, 50, contentArea.getWidth() - 2 * PADDING_X, 40);
        backgroundLabel.add(titleLabel);

        JLabel welcomeText = new JLabel("Welcome back :)", SwingConstants.CENTER);
        welcomeText.setFont(new Font(customFont, Font.PLAIN,14));
        welcomeText.setBounds(30, 150, contentArea.getWidth() - 60, 40);
        backgroundLabel.add(welcomeText);

        JLabel welcomeText2 = new JLabel("please enter your user name and password", SwingConstants.CENTER);
        welcomeText2.setFont(new Font(customFont, Font.PLAIN,14));
        welcomeText2.setBounds(30, 180, contentArea.getWidth() - 60, 40);
        backgroundLabel.add(welcomeText2);

        
        
        // Username Field
        JTextField usernameField = createInputField("Username", 250);
        backgroundLabel.add(usernameField);

       
        // Password Field
        JPasswordField passwordField = createPasswordField("Password", 300);
        backgroundLabel.add(passwordField);

        
        // Sign In Button (same width and height as input fields)
        JButton signInButton = new JButton("Sign In");
        signInButton.setBounds(PADDING_X, 380, contentArea.getWidth() - 2 * PADDING_X, FIELD_HEIGHT + 30);
        signInButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 0, true));
//        signInButton.setBackground(Color.decode("#45C78A"));
        signInButton.setBackground(customGreen);
        signInButton.setOpaque(true);
        signInButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
        signInButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            // Check if the user has entered valid input
            if (username.equals("Username") || username.isEmpty() ||
                password.equals("Password") || password.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Please enter both username and password.",
                    "Input Required",
                    JOptionPane.WARNING_MESSAGE
                );
            } else {
                // Proceed if inputs are valid
                JOptionPane.showMessageDialog(this, "Sign-in successful!");
                addToNavigationStack();
                new WelcomePage();
                dispose();
            }
        });
        backgroundLabel.add(signInButton);
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
        new SignInPage();
    }
}