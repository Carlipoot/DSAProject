package com.group6;

import com.group6.entities.*;
import com.group6.manager.ConnectionManager;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DSAForm extends JFrame {

    Person currentPerson = null;

    public DSAForm() {
        super();

        setContentPane(mainPanel);
        pack();

        setTitle("Police Database System");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //==============================================================================================================
        // Login
        //==============================================================================================================
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Fix this lol
                ErrorForm error = new ErrorForm("There is no sql connection yet dummy!");
            }
        });

        //==============================================================================================================
        // Officer
        //==============================================================================================================
        final ArrayList<IEntity> peopleSearched = new ArrayList<IEntity>();
        final ArrayList<IEntity> offencesSearched = new ArrayList<IEntity>();
        final ArrayList<IEntity> arrestsSearched = new ArrayList<IEntity>();

        DefaultListModel<String> peopleSearchModel = new DefaultListModel<String>();
        peopleSearchList.setModel(peopleSearchModel);

        DefaultListModel<String> offenceSearchModel = new DefaultListModel<String>();
        offenceListList.setModel(offenceSearchModel);

        DefaultListModel<String> arrestSearchModel = new DefaultListModel<String>();
        arrestsList.setModel(arrestSearchModel);

        // Search on Name and StreetAddress
        peopleSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Person person = new Person();
                person.name = peopleNameField.getText();
                person.streetAddress = peopleStreetField.getText();

                DefaultListModel<String> model = (DefaultListModel<String>)peopleSearchList.getModel();
                model.clear();

                ArrayList<IEntity> entities = ConnectionManager.getAll(person, Person.class);
                peopleSearched.clear();
                peopleSearched.addAll(entities);

                for ( IEntity entity : entities ) {
                    model.addElement(((Person)entity).name);
                }
            }
        });

        // Select from list to update ALL fields/tabs for that person
        peopleSearchList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if ( e.getValueIsAdjusting() ) {
                    currentPerson = (Person)peopleSearched.get(peopleSearchList.getSelectedIndex());

                    peopleCurrentLabel.setText("Current Person: " + currentPerson.name);
                    peopleNameField.setText(currentPerson.name);
                    peopleBirthDateField.setText(currentPerson.birthDate.toString());
                    peopleStreetField.setText(currentPerson.streetAddress);
                    peoplePostCodeField.setText("" + currentPerson.postcode);
                    peopleCityField.setText(currentPerson.city);
                    peopleGenderField.setText(currentPerson.gender);

                    Offence offence = new Offence();
                    offence.personID = currentPerson.personID;

                    ArrayList<IEntity> offenceEntities = ConnectionManager.getAll(offence, Offence.class);
                    offencesSearched.clear();
                    offencesSearched.addAll(offenceEntities);
                    offenceDateField.setText("");
                    offencePostcodeField.setText("");
                    offenceDescriptionArea.setText("");

                    Arrest arrest = new Arrest();
                    arrest.personID = currentPerson.personID;

                    ArrayList<IEntity> arrestEntities = ConnectionManager.getAll(arrest, Arrest.class);
                    arrestsSearched.clear();
                    arrestsSearched.addAll(arrestEntities);
                    arrestDateField.setText("");
                    arrestPostcodeField.setText("");
                    arrestEvidenceArea.setText("");
                }
            }
        });

        // Update tab when selected
        peopleTabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if ( currentPerson != null && currentPerson.personID >= 0 ) {
                    // Offences
                    if ( peopleTabbedPane.getSelectedIndex() == 1 ) {
                        DefaultListModel<String> model = (DefaultListModel<String>)offenceListList.getModel();
                        model.clear();

                        for ( IEntity entity : offencesSearched ) {
                            model.addElement(((Offence)entity).date.toString());
                        }
                    }
                    // Arrests
                    else if ( peopleTabbedPane.getSelectedIndex() == 2 ) {
                        DefaultListModel<String> model = (DefaultListModel<String>)arrestsList.getModel();
                        model.clear();

                        for ( IEntity entity : arrestsSearched ) {
                            model.addElement(((Arrest)entity).date.toString());
                        }
                    }
                }
            }
        });

        // Select from list to update fields for that offence
        offenceListList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    Offence offence = (Offence)offencesSearched.get(offenceListList.getSelectedIndex());

                    offenceDateField.setText(offence.date.toString());
                    offencePostcodeField.setText("" + offence.postcode);
                    offenceDescriptionArea.setText(offence.description);
                }
            }
        });

        // Select from list to update fields for that offence
        arrestsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    Arrest arrest = (Arrest)arrestsSearched.get(arrestsList.getSelectedIndex());

                    arrestDateField.setText(arrest.date.toString());
                    arrestPostcodeField.setText("" + arrest.postcode);
                    arrestEvidenceArea.setText(arrest.evidence);
                }
            }
        });

        // TODO Code for update and insert


        //==============================================================================================================
        // Commissioner
        //==============================================================================================================
        final ArrayList<IEntity> stationsSearched = new ArrayList<IEntity>();
        final ArrayList<IEntity> prisonsSearched = new ArrayList<IEntity>();

        DefaultListModel<String> stationSearchModel = new DefaultListModel<String>();
        stationsList.setModel(stationSearchModel);

        DefaultListModel<String> prisonSearchModel = new DefaultListModel<String>();
        prisonListList.setModel(prisonSearchModel);

        // List all stations
        stationSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Station station = new Station();

                DefaultListModel<String> model = (DefaultListModel<String>)stationsList.getModel();
                model.clear();

                ArrayList<IEntity> entities = ConnectionManager.getAll(station, Station.class);
                stationsSearched.clear();
                stationsSearched.addAll(entities);

                for ( IEntity entity : entities ) {
                    model.addElement(((Station)entity).streetAddress);
                }
            }
        });

        // Fill in fields when selected from list
        stationsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Station station = (Station)stationsSearched.get(stationsList.getSelectedIndex());

                stationPhoneNumberField.setText("" + station.phoneNumber);
                stationStreetField.setText(station.streetAddress);
                stationPostcodeField.setText("" + station.postcode);
                stationCityField.setText(station.city);
                stationChiefIDField.setText("" + station.chiefID);
                stationFrequencyField.setText("" + String.format("%.2f", station.radioFrequency));
            }
        });

        // List all prisons
        prisonSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Prison prison = new Prison();

                DefaultListModel<String> model = (DefaultListModel<String>)prisonListList.getModel();
                model.clear();

                ArrayList<IEntity> entities = ConnectionManager.getAll(prison, Prison.class);
                prisonsSearched.clear();
                prisonsSearched.addAll(entities);

                for ( IEntity entity : entities ) {
                    model.addElement(((Prison)entity).streetAddress);
                }
            }
        });

        // Fill in fields when selected from list
        prisonListList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Prison prison = (Prison)prisonsSearched.get(prisonListList.getSelectedIndex());

                prisonPhoneNumberField.setText("" + prison.phoneNumber);
                prisonStreetField.setText(prison.streetAddress);
                prisonPostcodeField.setText("" + prison.postcode);
                prisonCityField.setText(prison.city);
                prisonCapacityField.setText("" + prison.capacity);
                prisonOpenHourField.setText(prison.openHour);
                prisonCloseHourField.setText(prison.closeHour);
            }
        });

        //==============================================================================================================
        // Admin
        //==============================================================================================================
        adminSendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = adminInputArea.getText();
                adminOutputArea.setText(ConnectionManager.send(input));
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
    private JLabel adminInputLabel;
    private JTextArea adminInputArea;
    private JLabel amindOutputLabel;
    private JTextArea adminOutputArea;
    private JButton adminSendButton;
    private JTabbedPane commisionerTabbedPane;
    private JPanel stationPanel;
    private JPanel prisonPanel;
    private JLabel stationDetailsLabel;
    private JLabel stationsLabel;
    private JTextField stationPhoneNumberField;
    private JList stationsList;
    private JLabel stationPhoneNumberLabel;
    private JLabel stationStreetLabel;
    private JTextField stationStreetField;
    private JLabel stationPostcodeLabel;
    private JTextField stationPostcodeField;
    private JLabel stationCityLabel;
    private JTextField stationCityField;
    private JLabel stationChiefIDLabel;
    private JTextField stationChiefIDField;
    private JLabel stationFrequencyLabel;
    private JTextField stationFrequencyField;
    private JButton stationAddButton;
    private JButton stationUpdateButton;
    private JTextField prisonPhoneNumberField;
    private JTextField prisonStreetField;
    private JTextField prisonPostcodeField;
    private JTextField prisonCityField;
    private JTextField prisonCapacityField;
    private JTextField prisonOpenHourField;
    private JTextField prisonCloseHourField;
    private JButton prisonAddButton;
    private JButton prisonUpdateButton;
    private JList prisonListList;
    private JLabel prisonDetailsLabel;
    private JLabel prisonsLabel;
    private JLabel prisonPhoneNumberLabel;
    private JLabel prisonStreetLabel;
    private JLabel prisonPostcodeLabel;
    private JLabel prisonCityLabel;
    private JLabel prisonCapacityLabel;
    private JLabel prisonCloseHourLabel;
    private JLabel prisonOpenHourLabel;
    private JButton prisonSearchButton;
    private JButton stationSearchButton;

}
