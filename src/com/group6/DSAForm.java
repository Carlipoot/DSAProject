package com.group6;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSAForm extends JFrame {

    public DSAForm() {
        super();

        setContentPane(mainPanel);
        pack();

        setTitle("Police Database System");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add event handlers
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ErrorForm error = new ErrorForm("There is no sql connection yet dummy!");
            }
        });
    }

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
    private JPanel sideArmPanel;
    private JPanel dogsPanel;
    private JPanel peopleDetailsPanel;
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
    private JScrollPane peopleSearchScrollPane;
    private JTabbedPane peopleTabbedPane;
    private JPanel peoplePanel;
    private JPanel peopleCurrentInfo;
    private JLabel peopleCurrentLabel;
    private JPanel offencePanel;
    private JLabel offenceDetailsLabel;
    private JLabel offenceListLabel;
    private JList offenceListList;
    private JTextField offenceDateField;
    private JTextField offencePostcodeField;
    private JLabel offenceDateLabel;
    private JLabel offencePostcodeLabel;
    private JLabel offenceDescriptionLabel;
    private JTextArea offenceDescriptionArea;
    private JButton offenceAddButton;
    private JPanel arrestsPanel;
    private JLabel arrestDetailsLabel;
    private JLabel arrestsLabel;
    private JList arrestsList;
    private JLabel arrestDateLabel;
    private JLabel arrestPostcodeLabel;
    private JLabel arrestEvidenceLabel;
    private JTextField arrestDateField;
    private JTextField arrestPostcodeField;
    private JTextArea arrestEvidenceArea;
    private JButton arrestAddButton;

}
