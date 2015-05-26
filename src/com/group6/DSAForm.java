package com.group6;

import com.group6.entities.*;
import com.group6.manager.ConnectionManager;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
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

        final ArrayList<IEntity> peopleSearched = new ArrayList<IEntity>();
        final ArrayList<IEntity> offencesSearched = new ArrayList<IEntity>();
        final ArrayList<IEntity> arrestsSearched = new ArrayList<IEntity>();

        final DefaultListModel<String> peopleSearchModel = new DefaultListModel<String>();
        peopleSearchList.setModel(peopleSearchModel);

        final DefaultListModel<String> offenceSearchModel = new DefaultListModel<String>();
        offenceListList.setModel(offenceSearchModel);

        final DefaultListModel<String> arrestSearchModel = new DefaultListModel<String>();
        arrestsList.setModel(arrestSearchModel);

        final ArrayList<IEntity> stationsSearched = new ArrayList<IEntity>();
        final ArrayList<IEntity> prisonsSearched = new ArrayList<IEntity>();

        final DefaultListModel<String> stationSearchModel = new DefaultListModel<String>();
        stationsList.setModel(stationSearchModel);

        final DefaultListModel<String> prisonSearchModel = new DefaultListModel<String>();
        prisonListList.setModel(prisonSearchModel);

        //==============================================================================================================
        // Login
        //==============================================================================================================
        // Login to system
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                user.username = usernameField.getText();
                user.password = new String(passwordField.getPassword());

                int result = ConnectionManager.get(user);

                if ( result == 1 ) {
                    String type = user.type;

                    if ( type.equals("OFFICER") ) {
                        tabbedPane.setEnabledAt(1, true);
                        tabbedPane.setSelectedIndex(1);
                    } else if ( type.equals("COMMISSIONER")) {
                        tabbedPane.setEnabledAt(3, true);
                        tabbedPane.setSelectedIndex(3);
                    } else if ( type.equals("ADMIN")) {
                        tabbedPane.setEnabledAt(5, true);
                        tabbedPane.setSelectedIndex(5);
                    } else {
                        new ErrorForm("You have no privileges");
                    }
                } else if ( result == 0 ) {
                    new ErrorForm("Wrong username or password");
                }
            }
        });

        // Logout, by clicking the login tab again to reset everything
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if ( tabbedPane.getSelectedIndex() == 0 ) {
                    tabbedPane.setEnabledAt(1, false);
                    tabbedPane.setEnabledAt(2, false);
                    tabbedPane.setEnabledAt(3, false);
                    tabbedPane.setEnabledAt(4, false);
                    tabbedPane.setEnabledAt(5, false);

                    peopleSearched.clear();
                    offencesSearched.clear();
                    arrestsSearched.clear();
                    stationsSearched.clear();
                    prisonsSearched.clear();

                    peopleSearchModel.clear();
                    offenceSearchModel.clear();
                    arrestSearchModel.clear();
                    stationSearchModel.clear();
                    prisonSearchModel.clear();

                    peopleCurrentLabel.setText("Current Person: ");
                    peopleNameField.setText("");
                    peopleBirthDateField.setText("");
                    peopleStreetField.setText("");
                    peoplePostCodeField.setText("");
                    peopleCityField.setText("");
                    peopleGenderField.setText("");

                    offenceDateField.setText("");
                    offencePostcodeField.setText("");
                    offenceDescriptionArea.setText("");

                    arrestDateField.setText("");
                    arrestPostcodeField.setText("");
                    arrestEvidenceArea.setText("");

                    stationPhoneNumberField.setText("");
                    stationStreetField.setText("");
                    stationPostcodeField.setText("");
                    stationCityField.setText("");
                    stationChiefIDField.setText("");
                    stationFrequencyField.setText("");

                    prisonPhoneNumberField.setText("");
                    prisonStreetField.setText("");
                    prisonPostcodeField.setText("");
                    prisonCityField.setText("");
                    prisonCapacityField.setText("");
                    prisonOpenHourField.setText("");
                    prisonCloseHourField.setText("");

                    adminInputArea.setText("");
                    adminOutputArea.setText("");
                }
            }
        });

        //==============================================================================================================
        // Officer
        //==============================================================================================================
        // Search on Name and StreetAddress
        peopleSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Person person = new Person();
                person.name = peopleNameField.getText();
                person.streetAddress = peopleStreetField.getText();

                DefaultListModel<String> model = (DefaultListModel<String>)peopleSearchList.getModel();
                model.clear();

                ArrayList<IEntity> entities = ConnectionManager.getAll(person);
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

                    ArrayList<IEntity> offenceEntities = ConnectionManager.getAll(offence);
                    offencesSearched.clear();
                    offencesSearched.addAll(offenceEntities);
                    offenceDateField.setText("");
                    offencePostcodeField.setText("");
                    offenceDescriptionArea.setText("");

                    Arrest arrest = new Arrest();
                    arrest.personID = currentPerson.personID;

                    ArrayList<IEntity> arrestEntities = ConnectionManager.getAll(arrest);
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

        peopleUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Person person = null;
                try {
                    person = (Person)peopleSearched.get(peopleSearchList.getSelectedIndex());

                    person.name = peopleNameField.getText();
                    person.birthDate = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(peopleBirthDateField.getText()).getTime());
                    person.streetAddress = peopleStreetField.getText();
                    person.postcode = Integer.parseInt(peoplePostCodeField.getText());
                    person.city = peopleCityField.getText();
                    person.gender = peopleGenderField.getText();

                    if ( ConnectionManager.update(person) ) {
                        currentPerson = person;

                        peopleCurrentLabel.setText("Current Person: " + currentPerson.name);
                        peopleSearchButton.doClick();
                    }
                } catch (Exception ex) {

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
        // List all stations
        stationSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Station station = new Station();

                DefaultListModel<String> model = (DefaultListModel<String>)stationsList.getModel();
                model.clear();

                ArrayList<IEntity> entities = ConnectionManager.getAll(station);
                stationsSearched.clear();
                stationsSearched.addAll(entities);

                for ( IEntity entity : entities ) {
                    model.addElement(((Station)entity).streetAddress + ", " + ((Station) entity).city);
                }
            }
        });

        // Fill in fields when selected from list
        stationsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if ( !e.getValueIsAdjusting() )
                    return;

                Station station = (Station)stationsSearched.get(stationsList.getSelectedIndex());

                stationPhoneNumberField.setText("" + station.phoneNumber);
                stationStreetField.setText(station.streetAddress);
                stationPostcodeField.setText("" + station.postcode);
                stationCityField.setText(station.city);
                stationChiefIDField.setText("" + station.chiefID);
                stationFrequencyField.setText("" + String.format("%.2f", station.radioFrequency));
            }
        });

        // Update the station info
        stationUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Station station = null;
                try {
                    station = (Station) stationsSearched.get(stationsList.getSelectedIndex());

                    station.phoneNumber = Long.parseLong(stationPhoneNumberField.getText());
                    station.streetAddress = stationStreetField.getText();
                    station.postcode = Integer.parseInt(stationPostcodeField.getText());
                    station.city = stationCityField.getText();
                    station.chiefID = Integer.parseInt(stationChiefIDField.getText());
                    station.radioFrequency = Float.parseFloat(stationFrequencyField.getText());

                    if ( ConnectionManager.update(station) ) {
                        stationPhoneNumberField.setText("");
                        stationStreetField.setText("");
                        stationPostcodeField.setText("");
                        stationCityField.setText("");
                        stationChiefIDField.setText("");
                        stationFrequencyField.setText("");

                        stationSearchButton.doClick();
                    }
                } catch (Exception ex) {

                }
            }
        });

        // List all prisons
        prisonSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Prison prison = new Prison();

                DefaultListModel<String> model = (DefaultListModel<String>)prisonListList.getModel();
                model.clear();

                ArrayList<IEntity> entities = ConnectionManager.getAll(prison);
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
                if ( !e.getValueIsAdjusting() )
                    return;

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

        prisonUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Prison prison = null;
                try {
                    prison = (Prison)prisonsSearched.get(prisonListList.getSelectedIndex());

                    prison.phoneNumber = Long.parseLong(prisonPhoneNumberField.getText());
                    prison.streetAddress = prisonStreetField.getText();
                    prison.postcode = Integer.parseInt(prisonPostcodeField.getText());
                    prison.city = prisonCityField.getText();
                    prison.capacity = Integer.parseInt(prisonCapacityField.getText());
                    prison.openHour = prisonOpenHourField.getText();
                    prison.closeHour = prisonCloseHourField.getText();

                    if ( ConnectionManager.update(prison) ) {
                        prisonPhoneNumberField.setText("");
                        prisonStreetField.setText("");
                        prisonPostcodeField.setText("");
                        prisonCityField.setText("");
                        prisonCapacityField.setText("");
                        prisonOpenHourField.setText("");
                        prisonCloseHourField.setText("");

                        prisonSearchButton.doClick();
                    }
                } catch (Exception ex) {

                }
            }
        });

        //==============================================================================================================
        // Admin
        //==============================================================================================================
        // Send query to the database
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
