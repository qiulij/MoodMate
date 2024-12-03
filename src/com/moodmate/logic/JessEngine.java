package com.moodmate.logic;
import jess.*;
import com.moodmate.database.DatabaseConnection;
import com.moodmate.logic.User;

import jess.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List; 
import java.util.Iterator;


public class JessEngine {

    public static void main(String[] args) {
        try {
            // Initialize Jess engine
            Rete engine = new Rete();
            engine.reset();

            // Load the templates, data, and rules
            //engine.batch("src/com/moodmate/logic/templates.clp");
           // engine.batch("src/com/moodmate/logic/data.clp");
            //engine.batch("src/com/moodmate/logic/rules.clp");
           // engine.batch("src/com/moodmate/logic/rule_signin.clp");
            //engine.batch("src/com/moodmate/logic/templates_weather.clp");
            engine.batch("src/com/moodmate/logic/rules_weather.clp");
            engine.run();
            
            
           /* List<User> users = DatabaseConnection.fetchAllUsers();
            for (User user : users) {
                Fact userRecord = new Fact("user-record", engine);
                userRecord.setSlotValue("username", new Value(user.getUsername(), RU.STRING));
                userRecord.setSlotValue("password", new Value(user.getPassword(), RU.STRING));
                engine.assertFact(userRecord);
            }*/

            // GUI Setup
            /*JFrame frame = new JFrame("User Input Validation");
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(4, 2));

            // Input fields
            JLabel usernameLabel = new JLabel("Username:");
            JTextField usernameField = new JTextField();
            //JLabel emailLabel = new JLabel("Email:");
            //JTextField emailField = new JTextField();
            JLabel passwordLabel = new JLabel("Password:");
            JPasswordField passwordField = new JPasswordField();

            JButton validateButton = new JButton("Signin");
            JLabel resultLabel = new JLabel("Result: ");

            // Add components to the frame
            frame.add(usernameLabel);
            frame.add(usernameField);
            //frame.add(emailLabel);
            //frame.add(emailField);
            frame.add(passwordLabel);
            frame.add(passwordField);
            frame.add(validateButton);
            frame.add(resultLabel);*/

            // Add action listener for validation
            /*validateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        // Retrieve input from the GUI
                        String username = usernameField.getText().trim();
                        String password = new String(passwordField.getPassword()).trim();
                        //String email = emailField.getText();
                        Fact signInInput = new Fact("sign-in-input", engine);
                        signInInput.setSlotValue("username", new Value(username, RU.STRING));
                        signInInput.setSlotValue("password", new Value(password, RU.STRING));
                        engine.assertFact(signInInput);
                        
                     // Print facts for debugging
                        //Iterator<?> facts = engine.listFacts();
                        //while (facts.hasNext()) {
                          //  Fact fact = (Fact) facts.next();
                            //System.out.println("Fact: " + fact);
                        //}
                  
                        //engine.reset();
                        engine.run();
             
                     // Check the result
                        Iterator<?> factsResult = engine.listFacts();
                        while (factsResult.hasNext()) {
                            Fact fact = (Fact) factsResult.next();
                            
                            if (fact.getName().equals("MAIN::sign-in-result")) {
                            	engine.retract(fact);
                                String message = fact.getSlotValue("message").stringValue(null);
                                boolean isValid = fact.getSlotValue("valid").equals(Funcall.TRUE);

                                //resultLabel.setText(message);
                                if (isValid) {
                                    JOptionPane.showMessageDialog(frame, "Sign-In Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }*/
                            

                        // Assert user-input fact
                       // Fact userInput = new Fact("user-input", engine);
                        //userInput.setSlotValue("username", new Value(username, RU.STRING));
                        //userInput.setSlotValue("email", new Value(email, RU.STRING));
                        //userInput.setSlotValue("password", new Value(password, RU.STRING));
                        //engine.assertFact(userInput);
                        
                        // Run the rules
                      
                    

                        // Retrieve validation result
                        //Iterator<?> facts = engine.listFacts();
                        //boolean isValidUsernamePassword = false;
                        //boolean isValidPasswordPolicy = false;
                        //boolean isValidEmail = false;
                        //String message = "";
                        //while (facts.hasNext()) {
                          //  Fact fact = (Fact) facts.next();
                            //if (fact.getName().equals("MAIN::validation-result")) {
                              //  message = fact.getSlotValue("message").stringValue(null);
                                //isValidUsernamePassword = fact.getSlotValue("valid").equals(Funcall.TRUE);
                            //}
                            //if (fact.getName().equals("MAIN::password-validation-result")) {
                              //  if (fact.getSlotValue("valid").equals(Funcall.TRUE)) {
                                //    isValidPasswordPolicy = true;
                                //} else {
                                  //  message = "Password does not meet the policy requirements!";
                                //}
                            //}
                            //if (fact.getName().equals("MAIN::email-validation-result")) {
                              //  if (fact.getSlotValue("valid").equals(Funcall.TRUE)) {
                                //    isValidEmail = true;
                                //} else {
                                  //  message = "Email does not meet the standard requirements!";
                                //}
                            //}
             //           }
              

                                // Update result in GUI
                 //               resultLabel.setText("Result: " + message);
               //                 if (isValidUsernamePassword && isValidPasswordPolicy && isValidEmail) {
                                    // Insert valid data into the database
                   //                 if (DatabaseConnection.insertUser(username, password, email)) {
                     //                   JOptionPane.showMessageDialog(frame, "Success: User registered successfully");
                       //             } else {
                         //               JOptionPane.showMessageDialog(frame, "Validation failed , Name already exists in database. Enter the username Again", "Database Error", JOptionPane.ERROR_MESSAGE);
                           //         }
                             //   } else {
                                   // JOptionPane.showMessageDialog(frame, "Error: " + message, "Validation Failed. Username already exists in the database.", JOptionPane.ERROR_MESSAGE);
                               // }
                            
                        

                      //  }
                    }
                    
 catch (JessException ex) {
                        ex.printStackTrace();
                       // JOptionPane.showMessageDialog(frame, "Error during Sign-In: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        //JOptionPane.showMessageDialog(frame, "Error in Jess processing: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
          //  });

            // Display the frame
           // frame.setVisible(true);

       // } catch (JessException ex) {
          //  ex.printStackTrace();
       // }
   // }
}
