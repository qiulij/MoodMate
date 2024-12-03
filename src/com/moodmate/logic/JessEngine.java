package com.moodmate.logic;

import com.moodmate.database.DatabaseConnection;
import jess.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

public class JessEngine {

    public static void main(String[] args) {
        try {
            // Initialize Jess engine
            Rete engine = new Rete();
            engine.reset();

            // Load the templates, data, and rules
            engine.batch("src/com/moodmate/logic/templates.clp");
            engine.batch("src/com/moodmate/logic/data.clp");
            engine.batch("src/com/moodmate/logic/rules.clp");

            // GUI Setup
            JFrame frame = new JFrame("User Input Validation");
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(4, 2));

            // Input fields
            JLabel usernameLabel = new JLabel("Username:");
            JTextField usernameField = new JTextField();
            JLabel passwordLabel = new JLabel("Password:");
            JPasswordField passwordField = new JPasswordField();

            JButton validateButton = new JButton("Validate & Submit");
            JLabel resultLabel = new JLabel("Result: ");

            // Add components to the frame
            frame.add(usernameLabel);
            frame.add(usernameField);
            frame.add(passwordLabel);
            frame.add(passwordField);
            frame.add(validateButton);
            frame.add(resultLabel);

            // Add action listener for validation
            validateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        // Retrieve input from the GUI
                        String username = usernameField.getText();
                        String password = new String(passwordField.getPassword());
                        engine.reset();

                        // Assert user-input fact
                        Fact userInput = new Fact("user-input", engine);
                        userInput.setSlotValue("username", new Value(username, RU.STRING));
                        userInput.setSlotValue("password", new Value(password, RU.STRING));
                        engine.assertFact(userInput);

                        // Run the rules
                        engine.run();

                        // Retrieve validation result
                        Iterator<?> facts = engine.listFacts();
                        while (facts.hasNext()) {
                            Fact fact = (Fact) facts.next();
                            if (fact.getName().equals("MAIN::validation-result")) {
                                String message = fact.getSlotValue("message").stringValue(null);
                                boolean isValid = fact.getSlotValue("valid").equals(Funcall.TRUE);

                                // Update result in GUI
                                resultLabel.setText("Result: " + message);
                                if (isValid) {
                                    // Insert valid data into the database
                                    if (DatabaseConnection.insertUser(username, password)) {
                                        JOptionPane.showMessageDialog(frame, "Success: " + message + " (Data saved to database)");
                                    } else {
                                        JOptionPane.showMessageDialog(frame, "Validation succeeded, but database insertion failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Error: " + message, "Validation Failed", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }

                    } catch (JessException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error in Jess processing: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            // Display the frame
            frame.setVisible(true);

        } catch (JessException ex) {
            ex.printStackTrace();
        }
    }
}
