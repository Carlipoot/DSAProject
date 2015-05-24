package com.group6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorForm extends JFrame {

    private ErrorForm instance;

    public ErrorForm(String message) {
        super();

        instance = this;

        setContentPane(mainPanel);
        pack();

        setTitle("Error Message");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        errorMessageArea.setText(message);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                instance.dispose();
            }
        });
    }

    private JPanel mainPanel;
    private JPanel errorPanel;
    private JLabel errorLabel;
    private JButton okButton;
    private JTextArea errorMessageArea;

}
