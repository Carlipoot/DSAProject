package com.group6;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSAForm extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel mainPanel;
    private JPanel officerPanel;
    private JPanel captainPanel;
    private JPanel commissionerPanel;
    private JPanel courtPanel;
    private JPanel adminPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel loginPanel;
    private JTabbedPane officerTabbedPane;
    private JPanel evidencePanel;
    private JPanel dogsPanel;
    private JPanel peoplePanel;
    private JTextField peopleNameField;
    private JLabel peopleNameLabel;
    private JLabel peopleBirthDateLabel;
    private JTextField peopleBirthDateField;
    private JLabel peopleStreetLabel;
    private JTextField peopleStreetField;
    private JTextField peoplePostCodeField;
    private JLabel peoplePostCodeLabel;
    private JTextField peopleCityField;
    private JLabel peopleCityLabel;
    private JTextField peopleGenderField;
    private JLabel peopleGenderLabel;
    private JLabel peopleDetailsLabel;
    private JLabel peopleSearchLabel;
    private JButton peopleSearchButton;
    private JButton peopleAddButton;
    private JButton peopleUpdateButton;
    private JLabel loginLoginLabel;
    private JList peopleSearchList;
    private JLabel usernameLabel;
    private JLabel passwordLabel;

    public DSAForm() {
        super();

        setContentPane(mainPanel);
        pack();

        setTitle("Police Database System");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add event handlers
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ErrorForm error = new ErrorForm("There is no sql connection yet dummy!");
            }
        });
    }

}
